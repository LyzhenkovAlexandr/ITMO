import java.util.Scanner;

public class A_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] arr = line.split(" ");
        float a = Integer.parseInt(arr[2]) - Integer.parseInt(arr[1]);
        float b = Integer.parseInt(arr[1]) - Integer.parseInt(arr[0]);
        int c = (int) ( 2 * Math.ceil(a / b) + 1);
        System.out.println(c);
    }
}
