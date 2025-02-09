import java.util.Scanner;

public class I_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        int xl = 1000000000;
        int yl = 1000000000;
        int xr = 0;
        int yr = 0;

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            String[] arr = line.split(" ");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int h = Integer.parseInt(arr[2]);

            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yl, y + h);
        }

        int hout = (int) Math.ceil((float) Math.max(xr - xl, yr - yl) / 2);
        int xout = (int) ((float) (xl + xr) / 2);
        int yout = (int) ((float) (yl + yr) / 2);
        System.out.println(xout + " " + yout + " " + hout);
    }
}
