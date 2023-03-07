import java.util.*;
import java.io.*;

public class Node {
    int[] state;
    Node parent;
    String action;

    public Node(int[] state, Node parent, String action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
    }
}

class StackFrontier {
    Stack<Node> frontier;

    public StackFrontier() {
        frontier = new Stack<Node>();
    }

    public void add(Node node) {
        frontier.push(node);
    }

    public boolean containsState(int[] state) {
        for (Node node : frontier) {
            if (Arrays.equals(node.state, state)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    public Node remove() {
        if (isEmpty()) {
            throw new RuntimeException("empty frontier");
        }
        return frontier.pop();
    }
}

public class Maze {
    int height;
    int width;
    int[] start;
    int[] goal;
    boolean[][] walls;

    public Maze(String filename) throws FileNotFoundException, IOException {
        // read maze from file and set height and width of maze
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder contents = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            contents.append(line + "\n");
        }
        reader.close();

        // validate start and goal
        if (contents.toString().indexOf("A") == -1) {
            throw new RuntimeException("maze must have exactly one start point");
        }
        if (contents.toString().indexOf("B") == -1) {
            throw new RuntimeException("maze must have exactly one goal");
        }

        // determine height and width of maze
        this.height = contents.toString().split("\n").length;
        this.width = contents.toString().split("\n")[0].length();

        // keep track of walls
        this.walls = new boolean[height][width];
        String[] lines = contents.toString().split("\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = lines[i].charAt(j);
                if (c == 'A') {
                    this.start = new int[] {i, j};
                    this.walls[i][j] = false;
                } else if (c == 'B') {
                    this.goal = new int[] {i, j};
                    this.walls[i][j] = false;
                } else if (c == ' ') {
                    this.walls[i][j] = false;
                } else {
                    this.walls[i][j] = true;
                }
            }
        }
    }

    public boolean inBounds(int[] id) {
        int i = id[0];
        int j = id[1];
        return 0 <= i && i < height && 0 <= j && j < width;
    }

    public boolean passable(int[] id) {
        return !walls[id[0]][id[1]];
    }

    public List<int[]> neighbors(int[] id) {
        int i = id[0];
        int j = id[1];
        List<int[]> results = new ArrayList<int[]>();
        int[][] directions = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        for (int[] direction : directions) {
            int[] neighbor = {i + direction[0], j + direction[1]};
            if (inBounds(neighbor) && pass
