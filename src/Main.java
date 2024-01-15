import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// Declaratie pentru x
        int[] x = new int[5];

        for (int i = 0; i < x.length; i++) {

            x[i] = scanner.nextInt();

        }

        for (int i = 0; i < x.length; i++) {

            System.out.print(x[i]);

        }

    }
}
