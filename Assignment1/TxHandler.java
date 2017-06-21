import java.util.ArrayList;
import java.util.Arrays;

public class TxHandler {

	/**
	 * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
	 * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
	 * constructor.
	 */

	private UTXOPool currentPool;

	/** Takes a UTXOPool object and create a copy using UTXOPool constructor*/
	public TxHandler(UTXOPool utxoPool) {
		// IMPLEMENT THIS
		currentPool = new UTXOPool(utxoPool);
	}

	/**
	 * @return true if:
	 * (1) all outputs claimed by {@code tx} are in the current UTXO pool, 
	 * (2) the signatures on each input of {@code tx} are valid, 
	 * (3) no UTXO is claimed multiple times by {@code tx},
	 * (4) all of {@code tx}s output values are non-negative, and
	 * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
	 *     values; and false otherwise.
	 */

	public boolean isValidTx(Transaction tx) {
		
		ArrayList<Transaction.Input> txInArr = tx.getInputs();
		ArrayList<Transaction.Output> txOutArr = tx.getOutputs();
		ArrayList<UTXO> utxoUsed = new ArrayList<UTXO>();
		double sumIn = 0;
		double sumOut = 0;
		int index = 0;
		// Check inputs of tx
		for (Transaction.Input txIn : txInArr)
		{
			// make a UTXO from tx and use contains() to find if in UTXOPool
			UTXO testUTXO = new UTXO(txIn.prevTxHash, txIn.outputIndex);
			if (currentPool.contains(testUTXO) == false)
				return false;
			for (UTXO ut : utxoUsed)
			{
				if (testUTXO.equals(ut))
					return false;
			}
			utxoUsed.add(testUTXO);
			// Get the txOuput from the pool
			Transaction.Output checkOut = currentPool.getTxOutput(testUTXO);
			// Public key, Message, Signature
			if (Crypto.verifySignature(checkOut.address, tx.getRawDataToSign(index), txIn.signature) == false)
				return false;
			// Sum tx inputs
			sumIn += checkOut.value;
			index++;
		}
		
		// Check outputs of tx
		for (Transaction.Output txOut : txOutArr)
		{
			if (txOut.value < 0)
				return false;
			sumOut += txOut.value;
		}
		
		if (sumIn < sumOut)
			return false;

		return true;
	}

	/**
	 * Handles each epoch by receiving an unordered array of proposed transactions, checking each
	 * transaction for correctness, returning a mutually valid array of accepted transactions, and
	 * updating the current UTXO pool as appropriate.
	 */
	public Transaction[] handleTxs(Transaction[] possibleTxs) {
		// IMPLEMENT THIS
		// Takes an array of possible tx's and returns an array of valid tx's

		// We need to look at each tx individually and check its validity before adding it to the array
		// Once adding it to the array we need to update the UTXOPool and move on to
		//    the next possible tx
		
		// Somehow need to check if a tx depends on another tx in the array

		// Create new array and add valid tx's
		ArrayList<Transaction> validTx = new ArrayList<Transaction>();
		int i = 0;
		// iterate through possibleTxs
		for (Transaction tx: possibleTxs)
		{
			if (isValidTx(tx) == true)
			{
				validTx.add(tx);
				// Update UTXOPool by removing each UTXO corresponding to a txouput from UTXOPool
				// for every tx input of tx
				// remove the corresponding utxo from the utxo pool
				for (Transaction.Input txIn : tx.getInputs())
				{
					// make a UTXO from the txIn
					UTXO rUTXO = new UTXO(txIn.prevTxHash, txIn.outputIndex);
					currentPool.removeUTXO(rUTXO);
				}
				i++;
			}
		}
		// Convert ArrayList to array to return
		Transaction[] validTxArr = validTx.toArray(new Transaction[i + 1]);
		
		return validTxArr;
	}
}
