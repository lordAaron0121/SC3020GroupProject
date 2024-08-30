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
        // Assuming the following:
        // GAME_DATE_EST: String, 10 characters (UTF-16, 2 bytes per character) = 20 bytes
        // TEAM_ID_home: int = 4 bytes
        // PTS_home: int = 4 bytes
        // FG_PCT_home: double = 8 bytes
        // FT_PCT_home: double = 8 bytes
        // FG3_PCT_home: double = 8 bytes
        // AST_home: int = 4 bytes
        // REB_home: int = 4 bytes
        // HOME_TEAM_WINS: int = 4 bytes
    
        int sizeOfDate = 10 * 2; // 10 characters * 2 bytes per character
        int sizeOfInt = Integer.BYTES; // 4 bytes for each integer
        int sizeOfDouble = Double.BYTES; // 8 bytes for each double
    
        // Total size is the sum of sizes of all fields
        return sizeOfDate + 5 * sizeOfInt + 3 * sizeOfDouble;
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
