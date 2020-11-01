import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split("");
        Deque<String> brackets = new ArrayDeque<>();
        boolean balanced = false;
        for (String symbol : input) {
            if (symbol.matches("[({\\[]")) {
                brackets.offerLast(symbol);
            } else {
                balanced = areMatching(brackets.pollLast(), symbol);
                if (!balanced) {
                    break;
                }
            }
        }
        if (balanced) {
            balanced = brackets.isEmpty();
        }
        System.out.println(balanced);
    }

    private static boolean areMatching(String pollLast, String symbol) {
        return "(".equals(pollLast) && ")".equals(symbol) ||
                "{".equals(pollLast) && "}".equals(symbol) ||
                "[".equals(pollLast) && "]".equals(symbol);
    }
}