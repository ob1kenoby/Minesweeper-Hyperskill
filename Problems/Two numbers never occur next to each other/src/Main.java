import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        byte size = scanner.nextByte();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        boolean notOccur = true;
        int met = -1;
        for (int n: array) {
            if (n == a || n == b) {
                if (met < 0) {
                    met = n;
                } else if (met == a && n == b || met == b && n == a) {
                    notOccur = false;
                } else {
                    met = -1;
                }
            } else {
                met = -1;
            }
        }
        System.out.println(notOccur);
    }
}
