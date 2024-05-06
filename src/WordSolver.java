import java.util.*;

public class WordSolver {
    private Graph graph;
    private int visitedNodesCount;

    public WordSolver(Dictionary dictionary) {
        this.graph = new Graph(dictionary);
    }

    // Choose the algorithm to solve
    public List<String> solve(String start, String end, String algorithm, Dictionary dict) {
        visitedNodesCount = 0;
        if (!dict.dictionary.contains(start) || !dict.dictionary.contains(end)) {
            throw new IllegalArgumentException("Start or end word not in dictionary.");
        }
        switch (algorithm.toLowerCase()) {
            case "ucs":
                return uniformCostSearch(start, end);
            case "gbfs":
                return greedyBestFirstSearch(start, end);
            case "a*":
                return aStarSearch(start, end);
            default:
                throw new IllegalArgumentException("Unknown algorithm type.");
        }
    }

    // UCS Algorithm
    public List<String> uniformCostSearch(String start, String end) {
        PriorityQueue<Node> prioqueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Map<String, Integer> visitedCost = new HashMap<>();
        Set<String> allVisited = new HashSet<>(); // Track all visited nodes
        prioqueue.add(new Node(start, null, 0));
        visitedCost.put(start, 0);
    
        while (!prioqueue.isEmpty()) {
            Node current = prioqueue.poll();
            if (!allVisited.contains(current.word)){
                allVisited.add(current.word);
            }
    
            if (current.word.equals(end)) {
                visitedNodesCount = allVisited.size();
                return buildPath(current);
            }
    
            for (String neighbor : graph.getNeighbors(current.word)) {
                int newCost = current.cost + 1;
                if (!visitedCost.containsKey(neighbor) || visitedCost.get(neighbor) > newCost) {
                    visitedCost.put(neighbor, newCost);
                    prioqueue.add(new Node(neighbor, current, newCost));
                }
            }
        }
        
        return Collections.emptyList();
    }
    
    // GBFS Algorithm 1
    // public List<String> greedyBestFirstSearch(String start, String end) {
    //     PriorityQueue<Node> prioqueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
    //     Set<String> visited = new HashSet<>();
    //     Set<String> allVisited = new HashSet<>(); // Track all visited nodes
    //     prioqueue.add(new Node(start, null, 0, heuristic(start, end)));

    //     while (!prioqueue.isEmpty()) {
    //         Node current = prioqueue.poll();
    //         if (!allVisited.contains(current.word)){
    //             allVisited.add(current.word);
    //         }

    //         if (current.word.equals(end)) {
    //             visitedNodesCount = allVisited.size(); 
    //             return buildPath(current);
    //         }
            
    //         if (!visited.contains(current.word)) {
    //             visited.add(current.word);
    //             List<Node> neighbors = graph.getNeighbors(current.word).stream()
    //                 .filter(neighbor -> !visited.contains(neighbor))
    //                 .map(neighbor -> new Node(neighbor, current, current.cost + 1, heuristic(neighbor, end)))
    //                 .sorted(Comparator.comparingInt(n -> n.heuristic)) 
    //                 .collect(Collectors.toList());

    //             if (!neighbors.isEmpty()) {
    //                 prioqueue.add(neighbors.get(0)); 
    //             }
    //         }
    //     }

    //     return Collections.emptyList();
    // }
    
    // GBFS Algorithm 2
    public List<String> greedyBestFirstSearch(String start, String end) {
        PriorityQueue<Node> prioqueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        Set<String> visited = new HashSet<>();
        Set<String> allVisited = new HashSet<>(); // Track all visited nodes
        prioqueue.add(new Node(start, null, 0, heuristic(start, end)));
    
        while (!prioqueue.isEmpty()) {
            Node current = prioqueue.poll();
            if (!allVisited.contains(current.word)){
                allVisited.add(current.word);
            }
    
            if (current.word.equals(end)) {
                visitedNodesCount = allVisited.size();
                return buildPath(current);
            }
            
            if (!visited.contains(current.word)) {
                visited.add(current.word);
                for (String neighbor : graph.getNeighbors(current.word)) {
                    if (!visited.contains(neighbor)) {
                        prioqueue.add(new Node(neighbor, current, current.cost + 1, heuristic(neighbor, end)));
                    }
                }
            }
        }
    
        return Collections.emptyList();
    }

    // A* Algorithm
    public List<String> aStarSearch(String start, String end) {
        PriorityQueue<Node> prioqueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost + n.heuristic));
        Map<String, Integer> visitedCost = new HashMap<>();
        Set<String> allVisited = new HashSet<>(); // Track all visited nodes
        prioqueue.add(new Node(start, null, 0, heuristic(start, end)));
        visitedCost.put(start, 0);
    
        while (!prioqueue.isEmpty()) {
            Node current = prioqueue.poll();
            if (!allVisited.contains(current.word)){
                allVisited.add(current.word);
            }
    
            if (current.word.equals(end)) {
                visitedNodesCount = allVisited.size();
                return buildPath(current);
            }
    
            for (String neighbor : graph.getNeighbors(current.word)) {
                int newCost = current.cost + 1;
                if (!visitedCost.containsKey(neighbor) || visitedCost.get(neighbor) > newCost) {
                    visitedCost.put(neighbor, newCost);
                    prioqueue.add(new Node(neighbor, current, newCost, heuristic(neighbor, end)));
                }
            }
        }
    
        return Collections.emptyList();
    }
    
    // Heuristic based on Hamming Distance
    public int heuristic(String current, String end) {
        int distance = 0;
        for (int i = 0; i < current.length(); i++) {
            if (current.charAt(i) != end.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }
    
    public int getVisitedNodesCount() {
        return visitedNodesCount;
    }

    // Build the answer path
    public List<String> buildPath(Node endNode) {
        LinkedList<String> path = new LinkedList<>();
        for (Node n = endNode; n != null; n = n.parent) {
            path.addFirst(n.word);
        }
        return path;
    }
}