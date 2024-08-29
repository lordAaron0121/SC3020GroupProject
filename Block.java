import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Block implements Serializable {
    private List<GameRecord> records;
    private int blockSize;
    private int maxRecords;

    public Block(int blockSize) {
        this.blockSize = blockSize;
        this.records = new ArrayList<>();
        this.maxRecords = blockSize / calculateRecordSize(); // Calculate how many records can fit in a block
    }

    public int calculateRecordSize() {
        // Calculate the approximate size of a single record in bytes
        int size = 10 + Integer.BYTES * 4 + Double.BYTES * 3; 
        return size;
    }

    public int getMaxRecords() {
        return this.maxRecords;
    }

    public boolean addRecord(GameRecord record) {
        if (records.size() < maxRecords) {
            records.add(record);
            return true;
        }
        return false;
    }

    public List<GameRecord> getRecords() {
        return records;
    }
}
