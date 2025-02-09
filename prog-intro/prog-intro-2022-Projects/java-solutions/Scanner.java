import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.lang.Character.DASH_PUNCTUATION;

public class Scanner {
    private Reader inputString;
    private String line;
    private int index = 0;
    private StringBuilder str = new StringBuilder();
    private boolean isRead = false;
    private final char[] buffer = new char[4];
    private boolean theEnd = false;
    private int i = 0;
    private int r = 0;
    private static String sep;

    public Scanner(String line) throws IOException {
        this.line = line;
        sep = System.lineSeparator();
    }

    public Scanner(FileInputStream s) throws IOException {
        this.inputString = new InputStreamReader(s, StandardCharsets.UTF_8);
        sep = System.lineSeparator();
    }

    public Scanner(InputStream bufferedReader) throws IOException {
        this.inputString = new InputStreamReader(bufferedReader);
        sep = System.lineSeparator();
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
        if (sep.length() == 2) {
            return windows();
        } else if (sep.charAt(0) == 10) {
            return windows();
        } else {
            return macOld();
        }
    }

    private String macOld() throws IOException {
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
                if (sep != 13) {
                    string.append(buffer[i]);
                } else {
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

    private String windows() throws IOException {
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

    public boolean hasNextTrueWord() {
        for (int i = index; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (Character.isLetter(ch) || Character.toString(ch).equals("'") || Character.getType(ch) == DASH_PUNCTUATION) {
                str.append(Character.toLowerCase(ch));
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

    public String nextTrueWord() {
        String stroke = String.valueOf(str);
        str = new StringBuilder();
        return stroke;
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