import java.util.List;

/**
 * This execution entry point class handles the parsing, indexing and lookup of words in a text.
 */
public class Index {

    /**
     * Execution entry point.
     *
     * @param args two strings representing the name of the file that contains the text to index,
     * and the name of the file containing words to lookup in the text.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -cp <classpath> Index <text file> <word file>");
            System.exit(1);
        }

        // TODO: replace with homework implementation:

        String word;

        FileParser textFile = new FileParser(args[0]);
        textFile.open();
        RadixTree radixTree = new RadixTree();
        int value = 0;
        while ((word = textFile.getNextWord()) != null) {
            radixTree.insert(word, value++);
        }
        textFile.close();

        //Utils.dumpTree(radixTree);

        FileParser prefixFile = new FileParser(args[1]);
        prefixFile.open();

        while ((word = prefixFile.getNextWord()) != null) {
            int numberOccurencesWord = radixTree.getNumberOfOccurencesOfWord(word);
            List<Integer> positionsOfWordOccurences = radixTree.getPositionsOfWordOccurences(word);
            print(numberOccurencesWord, positionsOfWordOccurences);
        }
        prefixFile.close();

    }

    private static void print(int numberOccurencesWord, List<Integer> positions) {
        System.out.print(numberOccurencesWord + " ");
        for (Integer position: positions) {
            System.out.print(position + " ");
        }
        System.out.print("\n");
    }
}
