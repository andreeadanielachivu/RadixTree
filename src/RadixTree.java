import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Andreea on 10/14/2017.
 */
public class RadixTree {
    RadixNode root;

    public RadixTree() {
        root = new RadixNode("");
    }

    public void insert(String word, int value) {
        RadixNode currentNode = root;
        RadixNode parentNode = null;
        String prefix = word;

        while (!currentNode.getChildren().isEmpty()) {
            int maxPrefixLength = Integer.MIN_VALUE;
            for (RadixNode child : currentNode.getChildren()) {
                int largestPrefixLength = Utils.largestPrefixLength(prefix, child.getPrefix());
                if (largestPrefixLength > maxPrefixLength) {
                    maxPrefixLength = largestPrefixLength;
                    parentNode = currentNode;
                    currentNode = child;
                }
            }
            prefix = prefix.substring(maxPrefixLength);
            if (maxPrefixLength == 0) {
                RadixNode newRadixNode = new RadixNode(prefix, value);
                parentNode.getChildren().add(newRadixNode);
                if (parentNode.getPositions().isEmpty()) {
                    parentNode.setHasValues(false);
                }
                break;
            } else if (maxPrefixLength < currentNode.getPrefix().length()) {
                String leftOverPrefix = currentNode.getPrefix().substring(maxPrefixLength);
                RadixNode leftNode = new RadixNode(leftOverPrefix);
                leftNode.getPositions().addAll(currentNode.getPositions());
                leftNode.setHasValues(currentNode.hasValue());
                leftNode.getChildren().addAll(currentNode.getChildren());

                currentNode.setPrefix(currentNode.getPrefix().substring(0, maxPrefixLength));
                currentNode.getChildren().clear();
                currentNode.getChildren().add(leftNode);

                if (maxPrefixLength == word.length()) {
                    currentNode.setHasValues(true);
                    currentNode.getPositions().clear();
                    currentNode.getPositions().add(value);
                } else {
                    RadixNode newRadixNode = new RadixNode(prefix, value);
                    currentNode.getChildren().add(newRadixNode);
                    currentNode.setHasValues(false);
                }
                break;
            } else if (maxPrefixLength == currentNode.getPrefix().length()) {
                if (prefix.isEmpty()) {
                    currentNode.getPositions().add(value);
                    currentNode.setHasValues(true);
                    break;
                } else if (!prefix.isEmpty() && currentNode.getChildren().isEmpty()){
                    RadixNode radixNode = new RadixNode(prefix, value);
                    currentNode.getChildren().add(radixNode);
                    break;
                }
            }
        }

        if (root.getChildren().isEmpty()) {
            RadixNode newRadixNode = new RadixNode(word, value);
            root.getChildren().add(newRadixNode);
            root.setHasValues(false);
        }
    }

    public int getNumberOfOccurencesOfWord(String word) {
        RadixNode currentNode = root;
        String prefix = word;
        int numberOccurences = 0;
        int largestPrefixLength = 0;

        while (!prefix.isEmpty()) {
            for (RadixNode child : currentNode.getChildren()) {
                largestPrefixLength = Utils.largestPrefixLength(prefix, child.getPrefix());
                if (largestPrefixLength > 0) {
                    if (child.getPrefix().compareTo(prefix.substring(0, largestPrefixLength)) == 0 ||
                            (child.getPrefix().startsWith(prefix.substring(0, largestPrefixLength)) && prefix.length() == largestPrefixLength)) {
                        prefix = prefix.substring(largestPrefixLength);
                        currentNode = child;
                        break;
                    } else {
                        return 0;
                    }
                }
            }
            if (prefix.equals(word) || largestPrefixLength == 0 || (!prefix.isEmpty() && currentNode.getChildren().isEmpty())) {
                return 0;
            }
        }

        if (currentNode.hasValue()) {
            numberOccurences += currentNode.getPositions().size();
        }
        Queue<RadixNode> radixNodes = new LinkedList<>();
        radixNodes.addAll(currentNode.getChildren());
        while (!radixNodes.isEmpty()) {
            RadixNode radixNode = radixNodes.poll();
            if (radixNode.hasValue()) {
                numberOccurences += radixNode.getPositions().size();
            }
            radixNodes.addAll(radixNode.getChildren());
        }
        return numberOccurences;
    }

    public List<Integer> getPositionsOfWordOccurences(String word) {
        RadixNode currentNode = root;
        String prefix = word;
        List<Integer> positions = new ArrayList<>();
        int largestPrefixLength = 0;

        while (!prefix.isEmpty()) {
            for (RadixNode child : currentNode.getChildren()) {
                largestPrefixLength = Utils.largestPrefixLength(prefix, child.getPrefix());
                if (largestPrefixLength > 0) {
                    if (child.getPrefix().compareTo(prefix.substring(0, largestPrefixLength)) == 0 ||
                            (child.getPrefix().startsWith(prefix.substring(0, largestPrefixLength)) && prefix.length() == largestPrefixLength)) {
                        prefix = prefix.substring(largestPrefixLength);
                        currentNode = child;
                        break;
                    } else {
                        return positions;
                    }
                }
            }
            if (prefix.equals(word) || largestPrefixLength == 0 || (!prefix.isEmpty() && currentNode.getChildren().isEmpty())) {
                return positions;
            }
        }

        if (currentNode.hasValue()) {
            positions.addAll(currentNode.getPositions());
        }
        Queue<RadixNode> radixNodes = new LinkedList<>();
        radixNodes.addAll(currentNode.getChildren());
        while (!radixNodes.isEmpty()) {
            RadixNode radixNode = radixNodes.poll();
            if (radixNode.hasValue()) {
                positions.addAll(radixNode.getPositions());
            }
            radixNodes.addAll(radixNode.getChildren());
        }
        return positions;
    }
}
