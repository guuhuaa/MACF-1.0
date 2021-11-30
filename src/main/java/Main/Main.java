package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.Fasta;
import msa.ClusterAlign;
import msa.centerAlign;
import msa.treeAlign;

public class Main {
    private static String mode;
    private static String infile;
    private static String outfile;

    public static void main(String[] args) throws IOException {
        parse(args);
        print_args();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print("[" + sdf.format(new Date()) + "] ");
        System.out.println("Reading data");
        String[][] res = Fasta.readFasta(infile);
        System.out.println("[" + sdf.format(new Date()) + "] Done.");
        String[] labels = res[0];
        String[] strs = Fasta.countInfo(res[1]);
        switch (mode) {
        case "tree":
            treeAlign talign = new treeAlign(strs, "cluster", false);
            String[] strsTal = talign.getStrsAlign();
            Fasta.writeFasta(strsTal, labels, outfile, true);
            break;
        case "center":
            centerAlign calign = new centerAlign(strs, "fmindex");
            String[] strsCal = calign.getStrsAlign();
            Fasta.writeFasta(strsCal, labels, outfile, true);
            break;
        case "mix":
            ClusterAlign clalign = new ClusterAlign(strs, "t", "t2");
            String[] strsClal = clalign.getStrsAlign();
            Fasta.writeFasta(strsClal, labels, outfile, true);
            break;
        default:
            args_help();
            throw new IllegalArgumentException("unkown mode: " + mode);
        }
    }

    private static void parse(String[] args) throws IOException {
        // 比对方式选择 -m
        // 输入文件位置 -i
        // 输出文件位置 -o
        if (args.length == 0 || args.length > 6) {
            args_help();
            System.exit(0);
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-m") && args.length > i + 1) {
                if (args[i + 1].equalsIgnoreCase("center") || args[i + 1].equalsIgnoreCase("tree")
                        || args[i + 1].equalsIgnoreCase("mix")) {
                    mode = args[++i].toLowerCase();
                } else {
                    args_help();
                    throw new IllegalArgumentException("unknown mode: " + args[i + 1]);
                }
            } else if (args[i].equals("-i") && args.length > i + 1) {
                infile = args[++i];
            } else if (args[i].equals("-o") && args.length > i + 1) {
                outfile = args[++i];
            } else {
                args_help();
                System.exit(0);
            }
        }

        if (infile == null) {
            args_help();
            throw new IllegalArgumentException("infile path is null");
        } else if (outfile == null) {
            args_help();
            throw new IllegalArgumentException("outfile path is null");
        } else if (mode == null) {
            mode = "mix";
        }
        try (Writer write = new FileWriter(outfile)) {}
    }

    private static void args_help() {
        System.out.println("\nusage: java -jar " + " [-m] mode [-i] path [-o] path");
        System.out.println();
        System.out.println("  necessary arguments: ");
        System.out.println("    -i  Input file path (nucleotide sequences in fasta format)");
        System.out.println("    -o  Output file path");
        System.out.println();
        System.out.println("  optional arguments: ");
        System.out.println("    -m  three align option (default mode: Mix)");
        System.out.println("         1. Tree   more accurate but slower");
        System.out.println("         2. Mix    less accurate but faster");
        System.out.println();
    }

    private static void print_args() {
        System.out.println("\n**");
        System.out.println("** mode: " + mode);
        System.out.println("** infile: " + infile);
        System.out.println("** outfile: " + outfile);
        System.out.println("**");
        System.out.println();
    }
}
