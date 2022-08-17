package hierCluster;

public class leafnode extends node {
    private final String name;
    private int idx;

    public leafnode(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public double getDistance() {
        return 0.0;
    }

    @Override
    public int getIdx() {
        return idx;
    }

    @Override
    public int getNum() {
        return idx;
    }
}
