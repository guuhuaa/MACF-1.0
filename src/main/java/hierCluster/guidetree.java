package hierCluster;

import measure.strsdist;

public class guidetree {
    private final String[] strs;
    private final String mode;

    /**
     * mode: "nj" or "upgma"
     */
    public guidetree(String[] strs, String mode) {
        this.strs = strs;
        this.mode = mode;
    }

    /**
     * gen an order list of the treealign
     */
    public int[][] genTreeList(boolean silent) {
        // compute similarity matrix
        if (mode.equals("cluster")) {
            clusterTree cTree = new clusterTree(strs);
            return cTree.TreeList.toArray(new int[0][]);
        }
        strsdist sdist = new strsdist(strs, "kmer");
        double[][] simMatrix = sdist.getDismatrix2D();
        if (mode.equals("upgma")) {
            // upgma htree = new upgma(simMatrix);
            effupgma htree = new effupgma(simMatrix);
            return htree.TreeList.toArray(new int[0][]);
        } else if (mode.equals("nj")) {
            NeighborJoining nJtree = new NeighborJoining(simMatrix, silent);
            return nJtree.TreeList.toArray(new int[0][]);
        } else {
            throw new IllegalArgumentException("unknown mode: " + mode);
        }
    }
}
