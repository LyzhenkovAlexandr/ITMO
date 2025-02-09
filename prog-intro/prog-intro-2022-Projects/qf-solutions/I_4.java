import java.util.Scanner;

public class I_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        int xl = Integer.MAX_VALUE;
        int yl = 1000000000;
        int xr = -1000000000;
        int yr = -1000000000;

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            String[] arr = line.split(" ");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int h = Integer.parseInt(arr[2]);

            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }

        int xout = (xl + xr) / 2;
        int yout = (yl + yr) / 2;
        int h1 = Math.max(xout - xl, yout - yl);
        int h2 = Math.max(xr - xout, yr - yout);
        int hout = Math.max(h1, h2);
//        int hout = (int) Math.ceil(((float) Math.max(xr - xl, yr - yl)) / 2);
        System.out.println(xout + " " + yout + " " + hout);
    }
}
