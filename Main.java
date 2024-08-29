import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    private static int recordCount = 0;
    private static int blockCount = 0;
    private static final int blockSize = 4096; // Example block size

    public static void main(String[] args) {
        int maxDegree = 4; // Example B+ Tree degree (can be adjusted)
        String inputFileName = "nba_games.txt"; // Your input file
            
        BPlusTree bPlusTree = new BPlusTree(maxDegree);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;

            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                GameRecord record = parseLineToGameRecord(line);
                
                // Check if the record is not null before using it
                if (record != null) {
                    bPlusTree.insert(record.getFgPctHome(), record);
                    recordCount++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Example search
        double searchKey = 0.484;
        List<GameRecord> results = bPlusTree.search(searchKey);

        if (!results.isEmpty()) {
            for (GameRecord record : results) {
                System.out.println("Found record: " + record);
            }
        } else {
            System.out.println("No record found with FG_PCT_home = " + searchKey);
        }

        // Report statistics, passing the BPlusTree object
        reportStatistics(bPlusTree);
    }

    private static GameRecord parseLineToGameRecord(String line) {
        String[] parts = line.split("\t"); // Assuming tab-separated values
    
        // Check if the expected number of fields is present
        if (parts.length < 9) {
            System.err.println("Skipping malformed line: " + line);
            return null; // or handle as needed
        }
    
        try {
            String gameDateEst = parts[0].trim();
            int teamIdHome = Integer.parseInt(parts[1].trim());
            int ptsHome = Integer.parseInt(parts[2].trim());
            double fgPctHome = Double.parseDouble(parts[3].trim());
            double ftPctHome = Double.parseDouble(parts[4].trim());
            double fg3PctHome = Double.parseDouble(parts[5].trim());
            int astHome = Integer.parseInt(parts[6].trim());
            int rebHome = Integer.parseInt(parts[7].trim());
            int homeTeamWins = Integer.parseInt(parts[8].trim());
    
            return new GameRecord(gameDateEst, teamIdHome, ptsHome, fgPctHome, ftPctHome, fg3PctHome, astHome, rebHome, homeTeamWins);
        } catch (NumberFormatException e) {
            System.err.println("Skipping line due to number format error: " + line);
            return null; // or handle as needed
        }
    }    

    private static void reportStatistics(BPlusTree bPlusTree) {
        int recordSize = getRecordSize();
        int recordsPerBlock = blockSize / recordSize;
        blockCount = (int) Math.ceil((double) recordCount / recordsPerBlock);

        System.out.println("Record size: " + recordSize + " bytes");
        System.out.println("Total number of records: " + recordCount);
        System.out.println("Number of records per block: " + recordsPerBlock);
        System.out.println("Total number of blocks: " + blockCount);
        System.out.println("B+ Tree statistics:");
        System.out.println("Parameter n (max degree): " + bPlusTree.getMaxDegree());
        System.out.println("Number of nodes: " + bPlusTree.getNodeCount());
        System.out.println("Number of levels: " + bPlusTree.getHeight());
        System.out.println("Root node keys: " + bPlusTree.getRootKeys());
    }

    private static int getRecordSize() {
        // Assuming a fixed size for each field in the record
        int sizeOfDate = 10 * 2; // Assuming 10 characters for date (UTF-16 = 2 bytes per char)
        int sizeOfInt = Integer.BYTES; // 4 bytes
        int sizeOfDouble = Double.BYTES; // 8 bytes

        return sizeOfDate + 6 * sizeOfInt + 3 * sizeOfDouble; // Sum of all fields
    }
}
