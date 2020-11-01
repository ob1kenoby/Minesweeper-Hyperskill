import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            int number = scanner.nextInt();
            if (number % 2 == 0) {
                deque.offerFirst(number);
            } else {
                deque.offerLast(number);
            }
        }
        deque.forEach(System.out::println);
    }
}
