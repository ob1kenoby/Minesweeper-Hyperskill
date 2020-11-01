import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = (scanner.nextLine().split("\\s"));
        int n = scanner.nextInt();
        int difference = Integer.MAX_VALUE;
        for (String number : input) {
            int checkDifference = Math.abs(n - Integer.parseInt(number));
            if (checkDifference < difference) {
                difference = checkDifference;
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (String number : input) {
            if (Math.abs(n - Integer.parseInt(number)) == difference) {
                result.add(Integer.parseInt(number));
            }
        }
        Collections.sort(result);
        for (Integer number : result) {
            System.out.print(number + " ");
        }
    }
}