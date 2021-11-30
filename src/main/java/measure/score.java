package measure;

import java.util.HashMap;

import io.string;
import msa.centerAlign;
import sample.sampleStrings;

public class score {

    private final static int ms = 7, mis = -3;
    private final static int d = 15, e = 2;

    public static double sp(String A, String B) {
        int gap = 0;
        boolean state = false;
        long score = 0;
        assert A.length() == B.length();
        for (int i = 0; i < A.length(); i++) {
            if (A.charAt(i) == B.charAt(i)) {
                if (A.charAt(i) == '-')
                    gap++;
                else {
                    score += ms;
                    state = false;
                }
            } else if (A.charAt(i) == '-' || B.charAt(i) == '-') {
                score -= state ? e : d;
                state = true;
            } else {
                score += mis;
                state = false;
            }
        }
        return (double) score / ((A.length() - gap) * ms);
    }

    /**
     * compute the sps score in [0, 1]
     * 
     * @param strs
     * @return score
     */
    public static double sps(String[] strs) {
        long nums = strs.length, len = strs[0].length();
        double match = 0;
        for (int i = 0; i < len; i++) {
            long tempmatch = 0;
            String scren = (i + 1) + "/" + len;
            System.out.print(scren);
            HashMap<Character, Long> al = new HashMap<>();
            for (String str : strs) {
                char c = str.charAt(i);
                al.put(c, al.containsKey(c) ? al.get(c) + 1 : 1);
            }
            long numg = al.containsKey('-') ? al.get('-') : 0;
            long numn = al.containsKey('n') ? al.get('n') : 0;
            for (char c : al.keySet()) {
                if (c != '-')
                    tempmatch += (al.get(c) * (al.get(c) - 1) / 2);
            }
            tempmatch += (numn * (nums - numg - numn));
            match += ((double) tempmatch / (double) (nums * (nums - 1) / 2));
            System.out.print(string.repeat("\b", scren.length()));
        }
        return match / len;
    }


    /**
     * try to get a appropriate k
     * 
     * @param strs
     * @param sampled
     * @return k
     */
    public static int getK(String[] strs, boolean sampled) {
        if (!sampled) {
            sampleStrings spStrs = new sampleStrings();
            strs = spStrs.getSampleStrs(strs);
        }
        centerAlign cAlign = new centerAlign(strs, true);
        strs = cAlign.getStrsAlign();
        int nums = strs.length;
        int gap2 = 0;
        for (int i = 0; i < nums; i++) {
            for (int j = i; j < nums; j++) {
                gap2 += countGap(strs[i], strs[j]);
            }
        }
        gap2 /= (nums * nums / 2);
        return Math.max(gap2, 1);
    }

    private static int countGap(String A, String B) {
        int nums = 0, len = A.length();
        int lenA = 0, lenB = 0;
        for (int i = 0; i < len; i++) {
            if (A.charAt(i) == B.charAt(i) && A.charAt(i) == '-')
                nums++;
            if (A.charAt(i) != '-')
                lenA++;
            if (B.charAt(i) != '-')
                lenB++;
        }
        return Math.min(len - lenA - nums, len - lenB - nums);
    }
}
