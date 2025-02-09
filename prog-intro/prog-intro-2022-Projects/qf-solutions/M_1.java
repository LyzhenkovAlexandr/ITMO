import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class M_1 {
    private static Reader inputString;
    private static String line;
    private static int index = 0;
    private static StringBuilder str = new StringBuilder();
    private static boolean isRead = false;
    private static final char[] buffer = new char[20];
    private static boolean theEnd = false;
    private static int i = 0;
    private static int r = 0;

    public void Scanner(String l) throws IOException {
        line = l;
    }

    public void Scanner(FileInputStream s) throws IOException {
        inputString = new InputStreamReader(s, StandardCharsets.UTF_8);
    }

    public void Scanner(InputStream bufferedReader) throws IOException {
        inputString = new InputStreamReader(bufferedReader);
    }

    public static boolean hasNextLine() throws IOException {
        line = readLine();
        isRead = true;
        return !theEnd;
    }

    public static String nextLine() throws IOException {
        if (isRead) {
            isRead = false;
            return line;
        } else {
            return readLine();
        }
    }

    private static int createBuffer() throws IOException {
        return inputString.read(buffer);
    }

    private static String readLine() throws IOException {
        StringBuilder string = new StringBuilder();
        boolean ready = false;
        while (true) {
            if (r == -1 && string.length() != 0) {
                return string.toString();
            }
            if (r == -1) {
                theEnd = true;
                return null;
            }
            while (i < r) {
                int sep = buffer[i];
                if (sep != 13 && sep != 10) {
                    string.append(buffer[i]);
                } else if (sep == 10) {
                    i++;
                    ready = true;
                    break;
                }
                i++;
            }
            if (ready)
                break;
            i = 0;
            r = createBuffer();
        }
        return string.toString();
    }

    public static boolean hasNextInt() {
        for (int i = index; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (!Character.isWhitespace(ch)) {
                str.append(ch);
                index++;
                if (i + 1 == line.length() && str.length() != 0) {
                    return true;
                }
            } else if (str.length() != 0) {
                index++;
                return true;
            } else {
                index++;
            }
        }
        return false;
    }

    public static int nextInt() {
        int num = Integer.parseInt(String.valueOf(str));
        str = new StringBuilder();
        return num;
    }

    public static boolean hasNextWord() {
        return hasNextInt();
    }

    public static String nextWord() {
        String stroke = String.valueOf(str);
        str = new StringBuilder();
        return stroke;
    }

    public static void close() throws IOException {
        inputString.close();
    }


    public static void main(String[] args) throws IOException {
        int[] arr = new int[1_000_000_000];
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> hashMap = new HashMap<>();
        int k;

        String s = scanner.nextLine();
        int count = Integer.parseInt(s);
        for (int c = 0; c < count; c++) {
            hashMap.clear();
            k = 0;
            String p = scanner.nextLine();
            int n = Integer.parseInt(p);
            p = scanner.nextLine();
            Scanner sc = new Scanner(p);
            int q = 0;
            while (sc.hasNextInt()) {
                arr[q] = sc.nextInt();
                q++;
            }

            for (int i = n - 1; i > -1; i--) {
                hashMap.put(arr[i], hashMap.getOrDefault(arr[i], 0) + 1);
            }

            for (int i = 0; i < n; i++) {
                hashMap.put(arr[i], hashMap.get(arr[i]) - 1);
                for (int j = i - 1; j > -1; j--) {
                    int ak = 2 * arr[i] - arr[j];
                    k = hashMap.containsKey(ak) ? k + hashMap.get(ak) : k;
                }
            }
            System.out.println(k);
        }
    }
}