import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
    public Set<String> dictionary;

    public Dictionary(String dictionaryFile) throws IOException {
        loadDictionary(dictionaryFile);
    }

    private void loadDictionary(String file) throws IOException {
        dictionary = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toLowerCase());
            }
        }
    }

    public boolean isValid(String word) {
        return dictionary.contains(word.toLowerCase());
    }
}