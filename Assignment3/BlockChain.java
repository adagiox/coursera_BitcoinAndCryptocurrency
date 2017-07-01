// Block Chain should maintain only limited block nodes to satisfy the functions
// You should not have all the blocks added to the block chain in memory 
// as it would cause a memory overflow.

import java.util.ArrayList;
import java.util.HashMap;

public class BlockChain {
    // Block will contain a hash to the previous block
    
    // Storing full blocks in the blockStore and updating blockStore on adding new blocks
    // All blocks are stored by their hash in the blockChain
    
    public static final int CUT_OFF_AGE = 10;
    public ArrayList<ArrayList<Block>> blockStore; // Blocks
    public HashMap<byte[], byte[]> blockChain; // HashMap Which is a map of the current blocks hash and value of the previous hash
    public BlockHandler blockHandler;   //      i.e. HashMap<CurrentHash, PrevHash>;
    public TxHandler txHandler;
    public TransactionPool currentTXPool;
    public UTXOPool maxUTXOPool;
    /**
     * create an empty block chain with just a genesis block. Assume {@code genesisBlock} is a valid
     * block
     */
    public BlockChain(Block genesisBlock) {
        // IMPLEMENT THIS
       
        blockChain = new HashMap<byte[], byte[]>();
        blockStore = new ArrayList<ArrayList<Block>>();
        blockHandler = new BlockHandler(this);
        
        // ** TODO ** Set up the genesis block correctly before adding
        blockChain.put(genesisBlock.getHash(), null);
        blockStore.add(new ArrayList<Block>());
        blockStore.get(0).add(genesisBlock);
    }

    /** Get the maximum height block */
    public Block getMaxHeightBlock() {
        // returns the oldest block at the max height of the blockchain
        return blockStore.get(blockStore.size() - 1).get(0);
    }

    /** Get the UTXOPool for mining a new block on top of max height block */
    public UTXOPool getMaxHeightUTXOPool() {
        // get the corresponding UTXOPool for the longest chain
        return maxUTXOPool;
    }

    /** Get the transaction pool to mine a new block */
    public TransactionPool getTransactionPool() {
        return currentTXPool;
    }

    // Add a block to the blockstore
    // if blockstore is larger than CUT_OFF_AGE, trim the bottom blocks
    // block is valid
    public boolean addBlockStore(Block block)
    {
        int height = 1;
        for (ArrayList<Block> list : blockStore)
        {
            for (Block b : list)
            {
                if (b.getHash() == block.getPrevBlockHash())
                {
                    if (height == blockStore.size())
                        blockStore.add(new ArrayList<Block>());
                    ArrayList<Block> addB = blockStore.get(height);
                    addB.add(block);
                    if (blockStore.size() == 11)
                        blockStore.remove(0);
                    return true;
                }
                height++;
            }
        }
        return false;
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
        // Send block to process block to add coinbase tx and finalize
        if (block.getPrevBlockHash() == null)
            return false;
        
        // Validate TX
        txHandler = new TxHandler(maxUTXOPool);
        
        // Add to blockStore
        if (this.addBlockStore(block) != true)
            return false;
        
        // Add to Block to HashMap
        
        return false;
    }

    /** Add a transaction to the transaction pool */
    public void addTransaction(Transaction tx) {
        currentTXPool.addTransaction(tx);
    }
}