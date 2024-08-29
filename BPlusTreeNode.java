import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BPlusTreeNode {
    private boolean isLeaf;
    private List<Double> keys;
    private List<BPlusTreeNode> children;
    private List<GameRecord> records; // Only leaf nodes will use this

    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = isLeaf ? null : new ArrayList<>();
        this.records = isLeaf ? new ArrayList<>() : null;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public List<Double> getKeys() {
        return keys;
    }

    public List<BPlusTreeNode> getChildren() {
        return children;
    }

    public List<GameRecord> getRecords() {
        return records;
    }
}
