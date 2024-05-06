public class Node {
    String word;
    Node parent;
    int cost;
    int heuristic;
    
    Node(String word, Node parent, int cost) {
        this.word = word;
        this.parent = parent;
        this.cost = cost;
    }
    
    Node(String word, Node parent, int cost, int heuristic) {
        this.word = word;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }
}