import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] array = scanner.nextLine().split("\\s");
        boolean result = true;
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[i-1]) < 0) {
                result = false;
                break;
            }
        }
        System.out.println(result);
    }
}