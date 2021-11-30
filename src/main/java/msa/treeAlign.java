package msa;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import hierCluster.clusterTree;
import hierCluster.guidetree;
import io.string;
import measure.*;
import psa.PSA;

public class treeAlign {
    private String[] straligned;
    private final String Treemode;
    private int[] orders;
    private final int num, kk;
    private final boolean silent, aligned;

    /**
     * Choose one Gen tree mode "nj" or "upgma" or "cluster" and choose silent or
     * output
     */
    public treeAlign(String[] strs, String treemode, boolean silent) {
        this.num = strs.length;
        this.silent = silent;
        this.aligned = false;
        this.Treemode = treemode;
        this.kk = score.getK(strs, false);
        Align(strs);
        reOrder();
    }

    /**
     * Choose one Gen tree mode "nj" or "upgma" or "cluster" and choose silent or
     * output
     */
    public treeAlign(String[] strs, String[] strsed, boolean aligned, int kk) {
        this.num = strs.length;
        this.silent = false;
        this.aligned = aligned;
        this.Treemode = "cluster";
        this.straligned = strsed;
        this.kk = kk;
        Align(strs);
        reOrder();
    }

    /**
     * To get the alignment results.
     */
    public String[] getStrsAlign() {
        return this.straligned;
    }

    public int getK() {
        return kk;
    }
 
    private int[] combineLabels(HashMap<Integer, int[]> labelsList, int l1, int l2) {
        int[] listL1 = l1 < num ? new int[] { l1 } : labelsList.get(l1);
        int[] listL2 = l2 < num ? new int[] { l2 } : labelsList.get(l2);
        int[] res = new int[listL1.length + listL2.length];
        System.arraycopy(listL1, 0, res, 0, listL1.length);
        System.arraycopy(listL2, 0, res, listL1.length, listL2.length);
        return res;
    }

    private void reOrder() {
        int i = 0;
        String[] temp = new String[num];
        for (int j : orders) {
            temp[j] = straligned[i++];
        }
        straligned = temp;
    }

    private int[] getLens(String[] strs) {
        int[] res = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            res[i] = strs[i].length();
        }
        return res;
    }

    private int[][] genTreeList(String[] strs) {
        if (!aligned) {
            guidetree gTree = new guidetree(strs, this.Treemode);
            return gTree.genTreeList(silent);
        } else {
            clusterTree cTree = new clusterTree(straligned, getLens(strs));
            return cTree.TreeList.toArray(new int[0][]);
        }
    }

    private String[] getStrsList(String[] strs, HashMap<Integer, String[]> strsList, int key) {
        return key < this.num ? new String[] { strs[key] } : strsList.remove(key);
    }


    private String[] profileAlign(String[] A, String[] B, char[] alphabet) {
        PSA psa;
        if (A.length == 1 && B.length == 1) {
            psa = new PSA(A[0], B[0]);
        } else {
            psa = new PSA(A, B, alphabet, kk);
        }
        return psa.getAlign();
    }

    private void Align(String[] strs) {
        // 1.build tree
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!silent)
            System.out.println("[" + sdf.format(new Date()) + "] building the " + Treemode + " tree");
        int[][] treeList = genTreeList(strs);
        HashMap<Integer, String[]> strsList = new HashMap<>();
        HashMap<Integer, int[]> labelsList = new HashMap<>();
        if (!silent)
            System.out.println("[" + sdf.format(new Date()) + "] Done.");
        // 2.align
        if (!silent)
            System.out.println("[" + sdf.format(new Date()) + "] aligning...");
        char[] alphabet = kmer.Counter(strs);
        int len = treeList.length, i = 0;
        for (int[] readyAlign : treeList) {
            String outToScreen = "  " + (i + 1) + " / " + len;
            if (!silent)
                System.out.print(outToScreen);
            String[] strsA = getStrsList(strs, strsList, readyAlign[0]);
            String[] strsB = getStrsList(strs, strsList, readyAlign[1]);
            strsList.put(readyAlign[2], profileAlign(strsA, strsB, alphabet));
            labelsList.put(readyAlign[2], combineLabels(labelsList, readyAlign[0], readyAlign[1]));
            labelsList.remove(readyAlign[0]);
            labelsList.remove(readyAlign[1]);

            i++;
            if (!silent)
                System.out.print(string.repeat("\b", outToScreen.length()));
        }
        this.orders = labelsList.get(treeList[treeList.length - 1][2]);
        this.straligned = strsList.get(treeList[treeList.length - 1][2]);
        if (!silent) 
            System.out.println("[" + sdf.format(new Date()) + "] Done.");
    }
}