import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreea on 10/14/2017.
 */
public class RadixNode {
    private String prefix;
    private List<Integer> positions;
    private boolean hasValues;
    private List<RadixNode> children;

    public RadixNode(String prefix) {
        this.prefix = prefix;
        this.hasValues = false;
    }

    public RadixNode(String prefix, Integer position) {
        this.prefix = prefix;
        this.hasValues = true;
        getPositions().add(position);
    }

    public List<RadixNode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<RadixNode>();
        }
        return this.children;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setHasValues(boolean hasValues) {
        this.hasValues = hasValues;
        if (!hasValues) {
            this.positions = null;
        }
    }

    public List<Integer> getPositions() {
        if (this.positions == null) {
            this.positions = new ArrayList<Integer>();
        }
        return this.positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public boolean hasValue() {
        return hasValues;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setChildren(List<RadixNode> children) {
        this.children = children;
    }
}
