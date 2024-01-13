package dict;


import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Scanner;
/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * <p>
 * The file format has one keyword per line:
 * <pre>word:def</pre>
 * <p>
 * Note that an empty definition list is allowed (in which case the entry would have the form: <pre>word:</pre>
 *
 * @author talm
 */
public class InMemoryDictionary extends TreeMap<String, String> implements PersistentDictionary {
    private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
    private File dictionaryFile; 

    public InMemoryDictionary(File dictFile) {
        // TODO: Implement constructor
        super();
        this.dictionaryFile = dictFile;
        
        if (!dictionaryFile.exists()) {
            try {
                dictionaryFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
             }
        }
    }
    
    @Override
    public void open() throws IOException {
        // TODO Auto-generated method stub
        this.clear();
        
        try {  
            Scanner scanner = new Scanner(this.dictionaryFile); 
            String line;
            
            while (scanner.hasNextLine()) {
                
                line = scanner.nextLine();
                String[] wordDef = line.split(":",2);
                this.put(wordDef[0], wordDef[1]);
            }
            scanner.close();

        } catch (IOException e) {
                e.printStackTrace();
        }
    }  

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

        try {
            // file writer
            FileWriter fileWriter = new FileWriter(this.dictionaryFile);

            this.entrySet().forEach(entry -> {
                try {
                    String line = entry.getKey() + ":" + entry.getValue() + "\n";
                    fileWriter.write(line);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
