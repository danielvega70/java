import java.util.ArrayList;
import java.util.List;

public class LogicProblem {
    public static void main(String[] args) {
        String[] colors = {"red", "blue", "green", "yellow"};
        List<Symbol> symbols = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (String color : colors) {
                symbols.add(new Symbol(color + i));
            }
        }
        And knowledge = new And();

        // Each color has a position.
        for (String color : colors) {
            List<Symbol> colorPositions = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                colorPositions.add(new Symbol(color + i));
            }
            knowledge.add(new Or(colorPositions));
        }

        // Only one position per color.
        for (String color : colors) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i != j) {
                        knowledge.add(new Implication(
                                new Symbol(color + i),
                                new Not(new Symbol(color + j))
                        ));
                    }
                }
            }
        }

        // Only one color per position.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < colors.length; j++) {
                for (int k = j + 1; k < colors.length; k++) {
                    knowledge.add(new Implication(
                            new Symbol(colors[j] + i),
                            new Not(new Symbol(colors[k] + i))
                    ));
                }
            }
        }

        // Add the solution as a disjunction of conjunctions.
        List<Symbol> solutionSymbols = new ArrayList<>();
        solutionSymbols.add(new Symbol("red0"));
        solutionSymbols.add(new Symbol("blue1"));
        solutionSymbols.add(new Not(new Symbol("green2")));
        solutionSymbols.add(new Not(new Symbol("yellow3")));
        knowledge.add(new Or(new And(solutionSymbols)));

        // Remove some solutions that are inconsistent with the knowledge.
        knowledge.add(new And(
                new Not(new Symbol("blue0")),
                new Not(new Symbol("red1")),
                new Not(new Symbol("green2")),
                new Not(new Symbol("yellow3"))
        ));

        // Find all symbols that satisfy the knowledge.
        List<Symbol> solutions = new ArrayList<>();
        for (Symbol symbol : symbols) {
            if (model_check(knowledge, symbol)) {
                solutions.add(symbol);
            }
        }

        // Print the solutions, if any.
        if (!solutions.isEmpty()) {
            for (Symbol symbol : solutions) {
                System.out.println(symbol);
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    // The model_check function is assumed to be implemented elsewhere.
    private static boolean model_check(And knowledge, Symbol symbol) {
        // Implementation not shown.
        return false;
    }
}
