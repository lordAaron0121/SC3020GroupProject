import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFile {
    private File file;
    private int blockSize;

    public DatabaseFile(String fileName, int blockSize) {
        this.file = new File(fileName);
        this.blockSize = blockSize;
    }

    public void saveBlock(Block block) {
        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(block);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Block> loadAllBlocks() {
        List<Block> blocks = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Block block = (Block) ois.readObject();
                    blocks.add(block);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return blocks;
    }
}
