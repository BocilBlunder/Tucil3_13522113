import java.util.ArrayList;
import java.util.List;

public class Graph {
    private Dictionary dictionary;

    public Graph(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char oldChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != oldChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.isValid(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = oldChar;
        }
        return neighbors;
    }
}
