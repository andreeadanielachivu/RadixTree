/**
 * Created by Andreea on 10/15/2017.
 */
public class Utils {

    public static int largestPrefixLength(String a, String b) {
        int len = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                break;
            }
            len++;
        }
        return len;
    }

    public static <V> void dumpTree(RadixTree tree) {
        dumpTree(tree.root, "");
    }


    static <V> void dumpTree(RadixNode node, String outputPrefix) {
        if(node.hasValue())
            System.out.format("%s{%s : %s}%n", outputPrefix, node.getPrefix(), node.getPositions());
        else
            System.out.format("%s{%s}%n", outputPrefix, node.getPrefix(), node.getPositions());

        for(RadixNode child : node.getChildren())
            dumpTree(child, outputPrefix + "\t");
    }
}
