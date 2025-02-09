package search;

public class BinarySearchMax {
//    Pred: ∃!k ∈ N :∀i ∈ [0:k - 1) : a[i] < a[i + 1] &&
//                   ∀j ∈ [k: a.length) : a[j] < a[j + 1] < a[index] ∀index ∈ [0: k)
//    Post: R = max(a[i]) ∀i ∈ [0: a.length)
    private static int iterativeBinSearch(int[] a) {
        int l = 0;
//        l = 0
        int r = a.length;
//        I: a[l'] > a[l] && a[r'] < a[r] && 0 <= l <= l' < a.length && 0 < r' <= r <= a.length
        while (r - l > 1) {
//            I && r' - l' > 1 && l' < (r' + l') / 2 < r'
            int m = (l + r) / 2;
//            I && r' - l' > 1 && l' < m < r'
            if (a[m] > a[l]) {
//                I && r' - l' > 1 && l' < m < r' && a[m] > a[l']
                l = m;
//                I && r' - l' > 1 && l' < m < r' && a[m] > a[l'] && l' = m
            } else {
//                I && r' - l' > 1 && l' < m < r' && a[m] <= a[l']
                r = m;
//                I && r'' - l'' >= 1 && l'' < m <= r'' && a[m] <= a[l'] && r' = m
            }
//             r'' - l'' < r' - l'
        }
//        I && r' - l' <= 1 -> a[l'] == max(a[i]) ∀i ∈ [0: a.length)
        return a[l];
//        R = a[l']
    }


//    Pred: ∃!k ∈ N :∀i ∈ [l: k - 1) : a[i] < a[i + 1] &&
//                   ∀j ∈ [k: r] : a[j] < a[j + 1] < a[index] ∀index ∈ [l: k) &&
//                   l >= 0 && 0 <= r <= a.length && l <= a.length
//    Post: R = max(a[i]) ∀i ∈ [0: a.length)
    private static int recursiveBinSearch(int[] a, int l, int r) {
//        I: a[l'] > a[l] && a[r'] < a[r] && 0 <= l <= a.length && 0 <= r <= a.length
        if (r - l <= 1) {
//            I && r - l <= 1
            return a[l];
//            R = r && I && r - l <= 1
        }
//        I && l < (r + l) / 2 < r
        int m = (l + r) / 2;
//        I && l < m < r
        if (a[m] > a[l]) {
//            I && l < m < r && a[m] > a[l]
//             r - l > r - m
            return recursiveBinSearch(a, m, r);
//            R = recursiveBinSearch(a, m, r)
        } else {
//            I && l < m < r && a[m] <= a[l]
//            r - l > m - l
            return recursiveBinSearch(a, l, m);
//            R = recursiveBinSearch(a, l, m)
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[args.length];
        int summa = 0;
        for (int i = 0; i < args.length; i++) {
            int num = Integer.parseInt(args[i]);
            arr[i] = num;
            summa += num;
        }
        if (summa % 2 == 0) {
            System.out.println(recursiveBinSearch(arr, 0, arr.length));
        } else {
            System.out.println(iterativeBinSearch(arr));
        }
    }
}
