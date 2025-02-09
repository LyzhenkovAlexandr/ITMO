import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class WsppCountPosition {
    private static void addInMap(String s, Map<String, Integer> map) {
        map.put(s, map.getOrDefault(s, 0) + 1);
    }

    private static void addMapArr(String s, Map<String, String> mapArr, int countLine, int countWord) {
        StringBuilder sb = new StringBuilder().append(countLine).append(":").append(countWord);
        mapArr.computeIfPresent(s, (key, value) -> value + " " + sb);
        mapArr.putIfAbsent(s, sb.toString());
    }

    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            Map<String, String> mapArr = new HashMap<>();
            Map<String, Integer> map = new LinkedHashMap<>();
            List<Map.Entry<String, Integer>> entries;
            int countLine = 0;
            try {
                while (reader.hasNextLine()) {
                    countLine++;
                    int countWord = 0;
                    String line = reader.nextLine();
                    Scanner sc = new Scanner(line);
                    while (sc.hasNextTrueWord()) {
                        String s = sc.nextTrueWord();
                        countWord++;
                        addInMap(s, map);
                        addMapArr(s, mapArr, countLine, countWord);
                    }
                }
                entries = new ArrayList<>(map.entrySet());
                entries.sort(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o1.getValue() - o2.getValue();
                    }
                });
            } finally {
                reader.close();
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, Integer> item : entries) {
                    out.write(item.getKey() + " " + item.getValue() + " " + mapArr.get(item.getKey()));
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