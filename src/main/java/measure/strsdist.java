package measure;

public class strsdist {
    private final String[] strs;
    private final String mode;
    public int idxc = -1;
    private final double[][] dismatrix;

    /**
     * get 2D distance matrix
     * 
     * @param mode "kmer" "lcs"
     */
    public strsdist(String[] strs, String mode) {
        this.strs = strs;
        this.mode = mode.toLowerCase();
        dismatrix = getDist2D();
    }

    /**
     * get the distance2D
     * 
     * @return dist[][]
     */
    public double[][] getDismatrix2D() {
        if (idxc == -1)
            return dismatrix;
        else
            return null;
    }

    /**
     * 计算序列间两两之间的距离
     */
    private double[][] getDist2D() {
        if (mode.equals("kmer")) {
            kmer ker = new kmer(strs, 6);
            return ker.getDismatrix();
        } else if (mode.equals("lcs")) {
            lcs ls = new lcs(strs);
            return ls.getDismatrix();
        } else {
            throw new IllegalArgumentException("unkown mode: " + mode);
        }
    }
}
