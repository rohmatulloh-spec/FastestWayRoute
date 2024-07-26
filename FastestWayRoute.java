import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FastestWayRoute {
        // Input pertama merupakan gambar peta gang dimana posisi Anne dan rumah Januari berada
        // Symbol # merupakan tembok yang tidak bisa dilewati oleh Anne
        // Symbol * merupakan posisi rumah Januari
        // Symbol ^ merupakan posisi Anne sekarang
        // Kata OK diketik jika gambar peta sudah selesai di buat


        // test case
        // #######################
        // #                    *#
        // ############ ##########
        // #       #     #       #
        // # ######### ###########
        // #                     #
        // # #####################
        // #                 ^   #
        // #######################

        // output
        // 17 kiri
        // 2 atas
        // 10 kanan
        // 2 atas
        // 1 kanan
        // 2 atas
        // 9 kanan
        // 9 langkah
    public static void main(String[] args) {
        

        Scanner input = new Scanner(System.in);
        
        // Input
        LinkedList<String> inputLines = new LinkedList<>();
        while (true) {
            String line = input.nextLine();
            if (line.equals("OK")) {
                break;
            }
            inputLines.add(line);
        }
        input.close();

        int numRows = inputLines.size();
        int numCols = inputLines.get(0).length();
        String[][] route = new String[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                route[i][j] = String.valueOf(inputLines.get(i).charAt(j));
            }
        }

        // Cetak peta gang
        // for (int i = 0; i < route.length; i++) {
        //     for (int j = 0; j < route[i].length; j++) {
        //         System.out.print(route[i][j] );
        //     }
        //     System.out.println();
        // }

        // Mencari posisi awal (Anne) dan tujuan (rumah Januari)
        int startX = 0; 
        int startY = 0;
        int endX = 0;
        int endY = 0;
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route[i].length; j++) {
                if (route[i][j].equals("^")) {
                    startX = i;
                    startY = j;
                }
                if (route[i][j].equals("*")) {
                    endX = i;
                    endY = j;
                }
            }
        }

        // Gerakan yang mungkin (kanan, bawah, kiri, atas)
        int move[][] = {
            {0, 1},   // kanan
            {1, 0},   // bawah
            {0, -1},  // kiri
            {-1, 0}   // atas
        };

        String[] direction = {"kanan", "bawah", "kiri", "atas"};

        // Menggunakan BFS untuk mencari jalur terpendek
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        boolean[][] visited = new boolean[route.length][route[0].length];
        visited[startX][startY] = true;

        int[][] parent = new int[route.length * route[0].length][2];
        for (int[] row : parent) {
            row[0] = -1;
            row[1] = -1;
        }

        boolean found = false;
        int[] current = null;

        while (!queue.isEmpty()) {
            current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                found = true;
                break;
            }

            for (int i = 0; i < move.length; i++) {
                int newX = x + move[i][0];
                int newY = y + move[i][1];

                if (newX >= 0 && newX < route.length && newY >= 0 && newY < route[0].length && 
                    !route[newX][newY].equals("#") && !visited[newX][newY]) {
                    
                    queue.add(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    parent[newX * route[0].length + newY] = new int[]{x, y};
                }
            }
        }

        // Cetak jalur terpendek
        if (found) {

            LinkedList<int[]> path = new LinkedList<>();
            while (current != null && (current[0] != startX || current[1] != startY)) {
                path.addFirst(current);
                current = parent[current[0] * route[0].length + current[1]];
            }
            path.addFirst(new int[]{startX, startY});

            String prevDirection = "";
            int stepCount = 0;
            int totalStep = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                int[] step = path.get(i);
                int[] nextStep = path.get(i + 1);
                int dx = nextStep[0] - step[0];
                int dy = nextStep[1] - step[1];

                String moveDirection = "";
                for (int j = 0; j < move.length; j++) {
                    if (move[j][0] == dx && move[j][1] == dy) {
                        moveDirection = direction[j];
                        break;
                    }
                }

                if (moveDirection.equals(prevDirection)) {
                    stepCount++;
                } else {
                    if (!prevDirection.equals("")) {
                        System.out.println(stepCount + " " + prevDirection);
                    }
                    prevDirection = moveDirection;
                    stepCount = 1;
                }
            }

          
            if (!prevDirection.equals("")) {
                System.out.println(stepCount + " " + prevDirection);
                totalStep += stepCount;
            }
            System.out.println(totalStep + " " +"langkah");

        } else {
            System.out.println("tidak ada jalan");
        }
    }
}
