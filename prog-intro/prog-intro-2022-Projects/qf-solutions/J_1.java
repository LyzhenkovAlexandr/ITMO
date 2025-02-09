import java.io.*;
import java.nio.charset.StandardCharsets;


public class J_1 {
    public static void main(String[] args) throws IOException {
        int[][] arrayInt = new int[600][600];
        char[][] arrayChar = new char[600][600];
        int n = 0;
        int summa;

        Scanner scanner = new Scanner(System.in);
        String l = scanner.nextLine();
        Scanner sc = new Scanner(l);
        while (sc.hasNextInt())
            n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                arrayChar[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                summa = 0;
                for (int c = i + 1; c < j; c++) {
                    if (arrayInt[i][c] != 0) {
                        summa += (int) arrayChar[c][j] - 48;
                    }
                }
                if ((summa + 1) % 10 == (int) arrayChar[i][j] - 48) {
                    arrayInt[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arrayInt[i][j]);
            }
            System.out.println();
        }
    }


    private static class Scanner {
        private Reader inputString;
        private String line;
        private int index = 0;
        private StringBuilder str = new StringBuilder();
        private boolean isRead = false;
        private final char[] buffer = new char[20];
        private boolean theEnd = false;
        private int i = 0;
        private int r = 0;

        public Scanner(String line) throws IOException {
            this.line = line;
        }
        public Scanner(FileInputStream s) throws IOException {
            this.inputString = new InputStreamReader(s, StandardCharsets.UTF_8);
        }
        public Scanner(InputStream bufferedReader) throws IOException {
            this.inputString = new InputStreamReader(bufferedReader);
        }
        public boolean hasNextLine() throws IOException {
            line = readLine();
            isRead = true;
            return !theEnd;
        }
        public String nextLine() throws IOException {
            if (isRead) {
                isRead = false;
                return line;
            } else {
                return readLine();
            }
        }
        private int createBuffer() throws IOException {
            return inputString.read(buffer);
        }
        private String readLine() throws IOException {
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
        public boolean hasNextInt() {
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

        public int nextInt() {
            int num = Integer.parseInt(String.valueOf(str));
            str = new StringBuilder();
            return num;
        }
        public boolean hasNextWord() {
            return hasNextInt();
        }
        public String nextWord() {
            String stroke = String.valueOf(str);
            str = new StringBuilder();
            return stroke;
        }
        public void close() throws IOException {
            inputString.close();
        }
    }
}