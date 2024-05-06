import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Dictionary dict = null;
        WordSolver solver = null;

        try {
            dict = new Dictionary("words.txt");
            solver = new WordSolver(dict);
        } catch (Exception e) {
            System.out.println("Failed to load dictionary");
        }
        
        System.out.println("Welcome to Word Ladder Solver!");
        System.out.println("Available algorithms: ");
        System.out.println("1. UCS (Uniform Cost Search)");
        System.out.println("2. GBFS (Greedy Best First Search)");
        System.out.println("3. Astar (A*)");
        System.out.println();
        System.out.print("Semua input akan otomatis diubah menjadi huruf kecil");     
        
        String start, end;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("\nMasukkan kata awal: ");
            start = scanner.nextLine().toLowerCase();
            if (!dict.dictionary.contains(start)) {
                System.out.println("Kata '" + start + "' tidak tersedia di dalam kamus.");
            }
        } while (!dict.dictionary.contains(start));
        
        do {
            System.out.print("Masukkan kata akhir: ");
            end = scanner.nextLine().toLowerCase();
            if (!dict.dictionary.contains(end)) {
                System.out.println("Kata '" + end + "' tidak tersedia di dalam kamus.");
            } else if (start.length() != end.length()) {
                System.out.println("Panjang kata tidak sama.");
            }
        } while (!dict.dictionary.contains(end) || start.length() != end.length());
        
        int choice;
        do {
            System.out.print("\nPilih algoritma (1. UCS, 2. GBFS, 3. Astar): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan salah, Silahkan ulangi masukkan");
                choice = 0;
            }
        } while (choice < 1 || choice > 3);
        
        String algorithm;
        List<String> path = null;
        long startTime = System.currentTimeMillis();
        switch (choice) {
            case 1:
                algorithm = "ucs";
                path = solver.solve(start, end, algorithm, dict);
                break;
            case 2:
                algorithm = "gbfs";
                path = solver.solve(start, end, algorithm, dict);
                break;
            case 3:
                algorithm = "a*";
                path = solver.solve(start, end, algorithm, dict);
                break;
        }

        long endTime = System.currentTimeMillis();
        
        if (solver.getVisitedNodesCount() == 0) {
            System.out.println("No Solution");
        } else {
            System.out.println("Hasil path: ");
            int size = path.size();
            for (int i = 0; i < size; i++) {
                String word = path.get(i);
                System.out.println(word);
            }
            System.out.println("\nSolusi ditemukan dalam " + (endTime - startTime) + " milidetik.");
            System.out.println("Jumlah node solusi: " + size);
            System.out.println("Jumlah node yang dikunjungi: " + solver.getVisitedNodesCount());
        }
        scanner.close();
    }    
}