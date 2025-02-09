package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Md2Html {

    private static final Map<String, String> mapHtmlTags = Map.of(
            "*", "em",
            "_", "em",
            "__", "strong",
            "**", "strong",
            "`", "code",
            "--", "s",
            "++", "u"
    );

    private static int fixLattices(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) != '#') {
            return 0;
        }

        int i = 0;
        while (i < s.length() && s.charAt(i) == '#') {
            ++i;
        }
        if (i < s.length() && s.charAt(i) == ' ') {
            return i;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {

            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                while (line != null && !line.isEmpty()) {
                    sb.append(line);
                    line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        sb.append('\n');
                    }
                }

                int captionLevel = fixLattices(sb.toString());
                String tagName = (captionLevel == 0) ? "p" : "h" + captionLevel;
                StringBuilder hbuilder = new StringBuilder("<" + tagName + ">");

                Stack<String> stack = new Stack<>();
                int ind = (captionLevel > 0) ? captionLevel + 1 : 0;
                List<Integer> indicesWhereMayBeSingleSymbol = new ArrayList<>();
                while (ind < sb.length()) {
                    if (sb.charAt(ind) == '<') {
                        hbuilder.append("&lt;");
                    } else if (sb.charAt(ind) == '>') {
                        hbuilder.append("&gt;");
                    } else if (sb.charAt(ind) == '&') {
                        hbuilder.append("&amp;");
                    } else if (ind + 1 < sb.length() && mapHtmlTags.containsKey(sb.substring(ind, ind + 2))) {
                        if (stack.isEmpty() || !sb.substring(ind, ind + 2).equals(stack.peek())) {
                            stack.push(sb.substring(ind, ind + 2));
                            hbuilder.append("<").append(mapHtmlTags.get(sb.substring(ind, ind + 2))).append(">");
                        } else {
                            stack.pop();
                            hbuilder.append("</").append(mapHtmlTags.get(sb.substring(ind, ind + 2))).append(">");
                        }
                        ++ind;
                    } else if (ind + 1 < sb.length() && sb.charAt(ind) == '\\') {
                        hbuilder.append(sb.charAt(ind + 1));
                        ++ind;
                    } else if (mapHtmlTags.containsKey(sb.substring(ind, ind + 1))) {
                        if (stack.isEmpty() || !sb.substring(ind, ind + 1).equals(stack.peek())) {
                            stack.push(sb.substring(ind, ind + 1));
                            indicesWhereMayBeSingleSymbol.add(hbuilder.length());
                            hbuilder.append("<").append(mapHtmlTags.get(sb.substring(ind, ind + 1))).append(">");
                        } else {
                            stack.pop();
                            indicesWhereMayBeSingleSymbol.remove(indicesWhereMayBeSingleSymbol.size() - 1);
                            hbuilder.append("</").append(mapHtmlTags.get(sb.substring(ind, ind + 1))).append(">");
                        }
                    } else {
                        hbuilder.append(sb.charAt(ind));
                    }

                    ++ind;
                }

                // одиночные * или _
                for (int i = indicesWhereMayBeSingleSymbol.size() - 1; i >= 0; --i) {
                    int index = indicesWhereMayBeSingleSymbol.get(i);
                    String singleTag = stack.pop();
                    hbuilder.delete(index, index + 4);
                    hbuilder.insert(index, singleTag);
                }

                hbuilder.append("</").append(tagName).append(">");

                writer.write(hbuilder.toString());
                writer.newLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }
}

