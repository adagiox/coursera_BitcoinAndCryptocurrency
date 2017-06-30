// Block Chain should maintain only limited block nodes to satisfy the functions
// You should not have all the blocks added to the block chain in memory 
// as it would cause a memory overflow.

import java.util.ArrayList;

public class BlockChain {
    // Block will contain a hash to the previous block
    
    public static final int CUT_OFF_AGE = 10;
    public Block maxHeightBlock; // Reference to the block at maxHeight
    public ArrayList<Block> blockChain;
    public BlockHandler blockHandler;
    public TxHandler txHandler;
    public TransactionPool currentTXPool;
    public UTXOPool maxUTXOPool;
    /**
     * create an empty block chain with just a genesis block. Assume {@code genesisBlock} is a valid
     * block
     */
    public BlockChain(Block genesisBlock) {
        // IMPLEMENT THIS
        // Set the pv block hash to null
        blockChain = new ArrayList<Block>();
        blockChain.add(genesisBlock);
        maxHeightBlock = genesisBlock;
    }

    /** Get the maximum height block */
    public Block getMaxHeightBlock() {
        // get all blocks by at maxHeight
        // return the oldest if more than one
        return maxHeightBlock;
    }

    /** Get the UTXOPool for mining a new block on top of max height block */
    public UTXOPool getMaxHeightUTXOPool() {
        // IMPLEMENT THIS
        // get the corresponding UTXOPool for the longest chain
        return maxUTXOPool;
    }

    /** Get the transaction pool to mine a new block */
    public TransactionPool getTransactionPool() {
        return currentTXPool;
    }

    /**
     * Add {@code block} to the block chain if it is valid. For validity, all transactions should be
     * valid and block should be at {@code height > (maxHeight - CUT_OFF_AGE)}.
     * 
     * <p>
     * For example, you can try creating a new block over the genesis block (block height 2) if the
     * block chain height is {@code <= CUT_OFF_AGE + 1}. 
     * As soon as {@code height > CUT_OFF_AGE + 1}, you cannot create a new block at height 2.
     * 
     * @return true if block is successfully added
     */
    public boolean addBlock(Block block) {
        // IMPLEMENT THIS
        // If genesis block, return false
        // If the block is deeper than maxHeight - CUT_OFF_AGE
        //      return false
        // If tx's are valid and block is at height > (maxHeight - CUT_OFF_AGE)
        //      add block
        // Update maxHeight if necessary
        
        // Check for the height of the prev block hash in the blockchain up to depth of maxHeight - CUT_OFF_AGE
        // I
        if (block.getPrevBlockHash() == getMaxHeightBlock.getHash())
        {
            for (Transaction tx : block.getTransactions())
            {
                if (tx.isValidTx() == false)
                    return false;
            }
            blockChain.add(block);
            maxHeight++;
            return true;
        }
        return false;
    }

    /** Add a transaction to the transaction pool */
    public void addTransaction(Transaction tx) {
        currentTXPool.addTransaction(tx);
    }
}