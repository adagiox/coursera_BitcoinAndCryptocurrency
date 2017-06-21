import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {

    public int rounds;
    public Set<Transaction> compliantTX;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        rounds = numRounds;
        this.compliantTX = new HashSet<Transaction>();
    }

    public void setFollowees(boolean[] followees) {
        
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        // Pending Tx assigned at the beginning of the simulation which need to be distributed
        if (pendingTransactions.isEmpty() == true)
            return ;
        for (Transaction t : pendingTransactions)
            compliantTX.add(t);
    }

    public Set<Transaction> sendToFollowers() {
        // Send the node's current list of txs
        return compliantTX;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        // Recieve the candidate txs
        // Candidates contain a sender id and tx
        rounds--;
        if (rounds == 0)
        {
            // Possibly discard some txs at the end
        }
        for (Candidate c : candidates)
            compliantTX.add(c.tx);
    }
}
