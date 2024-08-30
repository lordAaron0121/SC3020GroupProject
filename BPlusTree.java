import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BPlusTree {
    private int maxDegree;
    private BPlusTreeNode root;

    public BPlusTree(int maxDegree) {
        this.maxDegree = maxDegree;
        this.root = new BPlusTreeNode(true); // Initially, root is a leaf node
    }

    public void insert(double key, GameRecord record) {
        BPlusTreeNode currentNode = root;

        // Split the root if necessary
        if (currentNode.getKeys().size() == maxDegree) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getChildren().add(currentNode);
            splitChild(newRoot, 0, currentNode);
            root = newRoot;
        }

        insertNonFull(root, key, record);
    }

    private void splitChild(BPlusTreeNode parent, int childIndex, BPlusTreeNode child) {
        int midIndex = maxDegree / 2;
        BPlusTreeNode sibling = new BPlusTreeNode(child.isLeaf());

        parent.getKeys().add(childIndex, child.getKeys().get(midIndex));
        parent.getChildren().add(childIndex + 1, sibling);

        sibling.getKeys().addAll(child.getKeys().subList(midIndex + 1, child.getKeys().size()));
        child.getKeys().subList(midIndex, child.getKeys().size()).clear();

        if (!child.isLeaf()) {
            sibling.getChildren().addAll(child.getChildren().subList(midIndex + 1, child.getChildren().size()));
            child.getChildren().subList(midIndex + 1, child.getChildren().size()).clear();
        } else {
            sibling.getRecords().addAll(child.getRecords().subList(midIndex + 1, child.getRecords().size()));
            child.getRecords().subList(midIndex + 1, child.getRecords().size()).clear();
        }
    }

    private void insertNonFull(BPlusTreeNode node, double key, GameRecord record) {
        if (node.isLeaf()) {
            int pos = Collections.binarySearch(node.getKeys(), key);
            if (pos < 0) pos = -pos - 1;

            node.getKeys().add(pos, key);
            node.getRecords().add(pos, record);
        } else {
            int pos = Collections.binarySearch(node.getKeys(), key);
            if (pos < 0) pos = -pos - 1;

            BPlusTreeNode child = node.getChildren().get(pos);

            if (child.getKeys().size() == maxDegree) {
                splitChild(node, pos, child);
                if (key > node.getKeys().get(pos)) {
                    child = node.getChildren().get(pos + 1);
                }
            }
            insertNonFull(child, key, record);
        }
    }

    public List<GameRecord> search(double key) {
        return search(root, key);
    }

    private List<GameRecord> search(BPlusTreeNode node, double key) {
        if (node.isLeaf()) {
            int pos = Collections.binarySearch(node.getKeys(), key);
            if (pos >= 0) {
                return List.of(node.getRecords().get(pos));
            } else {
                return new ArrayList<>();
            }
        } else {
            int pos = Collections.binarySearch(node.getKeys(), key);
            if (pos < 0) pos = -pos - 1;
            return search(node.getChildren().get(pos), key);
        }
    }

    // New methods to retrieve B+ Tree statistics

    // Get the max degree (parameter n)
    public int getMaxDegree() {
        return maxDegree;
    }

    // Get the number of nodes in the B+ Tree
    public int getNodeCount() {
        return countNodes(root);
    }

    // Helper method to count nodes recursively
    private int countNodes(BPlusTreeNode node) {
        if (node == null) return 0;
        int count = 1; // Count the current node
        if (!node.isLeaf()) {
            for (BPlusTreeNode child : node.getChildren()) {
                count += countNodes(child);
            }
        }
        return count;
    }

    // Get the height (number of levels) of the B+ Tree
    public int getHeight() {
        int height = 0;
        BPlusTreeNode node = root;
        while (node != null) {
            height++;
            if (!node.isLeaf()) {
                node = node.getChildren().get(0); // Go to the first child
            } else {
                break;
            }
        }
        return height;
    }

    // Get the keys of the root node
    public List<Double> getRootKeys() {
        return root.getKeys();
    }

    // Inner class representing a node in the B+ Tree
    private class BPlusTreeNode {
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
}