import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Character.DASH_PUNCTUATION;

public class Wspp {
    private static void word(StringBuilder s, Map<String, List<Integer>> map, int countWord) {
        String line = s.toString();
        if (map.containsKey(line)) {
            map.get(line).add(countWord);
        } else {
            List<Integer> pos = new ArrayList<>();
            pos.add(countWord);
            map.put(line, pos);
        }
    }

    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(args[0]);
            Scanner reader = new Scanner(file);
            StringBuilder s = new StringBuilder();
            Map<String, List<Integer>> map = new LinkedHashMap<>();
            int countWord = 0;
            try {
                while (reader.hasNextLine()) {
                    Scanner str = new Scanner(reader.nextLine());

                    while (str.hasNextWord()) {
                        String word = str.nextWord();

                        for (int i = 0; i < word.length(); i++) {
                            char ch = word.charAt(i);
                            if (Character.isLetter(ch) || Character.toString(ch).equals("'") || Character.getType(ch) == DASH_PUNCTUATION) {
                                s.append(Character.toLowerCase(ch));
                                if (i == word.length() - 1) {
                                    countWord++;
                                    word(s, map, countWord);
                                    s = new StringBuilder();
                                }
                            } else if (s.length() != 0) {
                                countWord++;
                                word(s, map, countWord);
                                s = new StringBuilder();
                            }
                        }
                    }
                }
            } finally {
                reader.close();
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, List<Integer>> item : map.entrySet()) {
                    out.write(item.getKey() + " " + item.getValue().size() + " ");
                    List<Integer> arr = item.getValue();
                    for (int i = 0; i < arr.size() - 1; i++) {
                        out.write(arr.get(i) + " ");
                    }
                    out.write(arr.get(arr.size() - 1).toString());
                    out.newLine();
                }
            } finally {
                out.close();
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException" + e);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException" + e);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException" + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("SecurityException" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        }
    }
}