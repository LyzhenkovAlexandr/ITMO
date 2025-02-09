
package search;

public class BinarySearch {
//    Pred: a[i] >= a[i + 1]
//    Post: min r : a[r] <= x && r ∈ [0: a.length - 1]
    private static int iterativeBinSearch(int x, int[] a) {
        int l = -1;
//        l = -1
        int r = a.length;
//        I: a[l'] > x >= a[r'] && -1 <= l <= l' < r' <= r && r >= 0
        while (r - l > 1) {
//            I && r' - l' > 1 && l' < (r' + l') / 2 < r'
            int m = (l + r) / 2;
//            I && r' - l' > 1 && l' < m < r'
            if (a[m] <= x) {
//                I && r' - l' > 1 && l' < m < r' && a[m] <= x
                r = m;
//                I && r' - l' > 1 && l' < m < r' && a[m] <= x && r' = m
            } else {
//                I && r' - l' > 1 && l' < m < r' && a[m] > x
                l = m;
//                I && r' - l' > 1 && l' < m < r' && a[m] > x && l' = m
            }
        }
//        I && r - l <= 1 ->
//        r = min(i): a[i] <= x
        return r;
    }

//    Pred: a[i] >= a[i + 1] && -1 <= l < r <= a.length
//    Post: R = min r : a[r] <= x && r ∈ [0: a.length - 1]
    private static int recursiveBinSearch(int x, int[] a, int l, int r) {
//        1 <= l < r <= a.length
        if (r - l <= 1) {
//            1 <= l < r <= a.length && r - l <= 1
            return r;
//            R = r && a[r] <= x && 0 <= r <= a.length
        }
//        l < (r + l) / 2 < r
        int m = (l + r) / 2;
//        l < m < r
        if (a[m] <= x) {
//            l < m < r && a[m] <= x
            return recursiveBinSearch(x, a, l, m);
//            R = recursiveBinSearch(x, a, l, m)
        } else {
//            l < m < r && a[m] > x
            return recursiveBinSearch(x, a, m, r);
//            R = recursiveBinSearch(x, a, m, r)
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        int summa = 0;
        for (int i = 1; i < args.length; i++) {
            int num = Integer.parseInt(args[i]);
            arr[i - 1] = num;
            summa += num;
        }

        if (summa % 2 == 0) {
            System.out.println(recursiveBinSearch(x, arr, -1, arr.length));
        } else {
            System.out.println(iterativeBinSearch(x, arr));
        }
    }
}