public class SumDouble {
    public static void main(String[] args) {
        double sum = 0;
        for (String arg : args) {
            char[] arr = arg.toCharArray();
            StringBuilder helper = new StringBuilder();
            for (char c : arr) {
                if (Character.isWhitespace(c)) {
                    if (!helper.toString().equals("")) {
                        sum += Double.parseDouble(helper.toString());
                        helper = new StringBuilder();
                    }
                } else {
                    helper.append(c);
                }
            }
            if (!helper.toString().equals("")) {
                sum += Double.parseDouble(helper.toString());
            }
        }
        System.out.println(sum);
    }
}