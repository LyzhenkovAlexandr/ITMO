import java.util.Scanner;

public class A_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] arr = line.split(" ");
        float a = Integer.parseInt(arr[2]) - Integer.parseInt(arr[1]);
        float b = Integer.parseInt(arr[1]) - Integer.parseInt(arr[0]);
        System.out.println(2 * Math.ceil(a / b) + 1);
    }
}
