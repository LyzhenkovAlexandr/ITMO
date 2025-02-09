import java.io.IOException;
import java.util.Arrays;


public class ReverseAbc {
    private static int convertToNum(String word) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (Character.getType(ch) == Character.DASH_PUNCTUATION)
                s.append(ch);
            else
                s.append(ch - 97);
        }
        return Integer.parseInt(String.valueOf(s));
    }

    private static StringBuilder convertToChar(String word) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (Character.getType(ch) == Character.DASH_PUNCTUATION)
                s.append(ch);
            else
                s.append((char) (ch + 49));
        }
        return s;
    }


    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            try {
                int[] lineArray = new int[10];
                int[] numArray = new int[10];
                int countNums = 0;
                int countLines = -1;


                while (sc.hasNextLine()) {
                    countLines += 1;
                    if (countLines >= lineArray.length) {
                        lineArray = Arrays.copyOf(lineArray, lineArray.length * 2);
                    }

                    Scanner str = new Scanner(sc.nextLine());
                    while (str.hasNextWord()) {
                        String word = str.nextWord();
                        int number = convertToNum(word);
                        numArray[countNums] = number;
                        countNums += 1;
                        if (countNums >= numArray.length) {
                            numArray = Arrays.copyOf(numArray, numArray.length * 2);
                        }
                        lineArray[countLines] += 1;
                    }
                }

                for (int i = countLines; i > -1; i--) {
                    for (int j = 0; j < lineArray[i]; j++) {
                        System.out.print(convertToChar(String.valueOf(numArray[countNums - 1])) + " ");
                        countNums -= 1;
                    }
                    System.out.println();
                }
            } finally {
                sc.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}