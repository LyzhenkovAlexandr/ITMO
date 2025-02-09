import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReverseTranspose {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int maxLenght = 0;
        List<int[]> arrayList = new ArrayList<>();

        while (sc.hasNextLine()) {
            int countLenght = 0;

            Scanner str = new Scanner(sc.nextLine());
            int count = 0;
            int[] array = new int[1];
            while (str.hasNextInt()) {
                count += 1;
                if (count >= array.length) {
                    array = Arrays.copyOf(array, array.length * 2);
                }
                int number = str.nextInt();
                array[count - 1] = number;
                countLenght += 1;
            }
            array = Arrays.copyOf(array, count);
            arrayList.add(array);
            maxLenght = Math.max(maxLenght, countLenght);
        }

        for (int i = 0; i < maxLenght; i++) {
            for (int[] item : arrayList) {
                if (i >= item.length) {
                    continue;
                }
                System.out.print(item[i] + " ");
            }
            System.out.println();
        }
    }
}