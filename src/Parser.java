import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final String[] KEYWORDS = {
            "var", "while", "end", "if", "else", "print", "read", "for"
    };

    public static String translateToJava(String pseudoCode) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("import java.util.Scanner;\n\n");
        javaCode.append("public class Main {\n");
        javaCode.append("    public static void main(String[] args) {\n");
        javaCode.append("        Scanner scanner = new Scanner(System.in);\n");

        for (String line : pseudoCode.split("\n")) {
            line = line.trim();
            if (!line.isEmpty()) {
                javaCode.append(translateLine(line));
            }
        }

        javaCode.append("    }\n");
        javaCode.append("}\n");

        return javaCode.toString();
    }

    private static String translateLine(String line) {
        StringBuilder translatedLine = new StringBuilder();
        String comment = extractComment(line);
        line = removeCommentFromLine(line).trim();

        if (isVariableDeclaration(line)) {
            translatedLine.append(translateVariable(line));
        } else if (isControlStructure(line)) {
            translatedLine.append(translateControlStructures(line));
        } else if (isAssignment(line)) {
            translatedLine.append(translateAssignment(line));
        }

        if (!comment.isEmpty()) {
            if (translatedLine.length() > 0) {
                translatedLine.append(" ");
            }
            translatedLine.append(comment);
        }

        if (translatedLine.length() > 0) {
            translatedLine.append("\n");
        }

        return translatedLine.toString();
    }

    private static boolean isVariableDeclaration(String line) {
        return line.startsWith("var");
    }

    private static boolean isControlStructure(String line) {
        for (String keyword : KEYWORDS) {
            if (line.startsWith(keyword)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAssignment(String line) {
        return line.contains("=") && !line.startsWith("var");
    }

    private static String translateVariable(String line) {
        if (line.contains("var")) {
            if (line.contains("=")) {
                String variableName = line.substring(line.indexOf("var") + 4, line.indexOf("=")).trim();
                String initialValue = line.substring(line.indexOf("=") + 1).trim();
                return "int " + variableName + " = " + initialValue + ";\n";
            }

            if (line.contains("[")) {
                String variableName = line.substring(line.indexOf("var") + 4, line.indexOf("[")).trim();
                String arraySize = line.substring(line.indexOf("[") + 1, line.indexOf("]")).trim();
                return "int[] " + variableName + " = new int[" + arraySize + "];\n";
            }

            String variableName = line.substring(line.indexOf("var") + 4).trim();
            return "int " + variableName + ";\n";
        }
        return "";
    }

    private static String translateAssignment(String line) {
        Pattern pattern = Pattern.compile(".*=.*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String variableName = line.substring(0, line.indexOf("=")).trim();
            String value = line.substring(line.indexOf("=") + 1).trim();
            return variableName + " = " + value + ";\n";
        }
        return "";
    }

    private static String translateControlStructures(String line) {
        if (line.contains("while")) {
            return "while(" + line.substring(line.indexOf("while") + 6).trim() + ") {\n";
        }
        if (line.startsWith("for")) {
            return translateForLoop(line);
        }
        if (line.contains("if")) {
            return "if(" + line.substring(line.indexOf("if") + 3).trim() + ") {\n";
        }
        if (line.contains("else")) {
            return "} else {\n";
        }
        if (line.contains("end")) {
            return "}\n";
        }
        if (line.startsWith("print(")) {
            String formattedString = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();
            return "System.out.printf(" + formattedString + ");\n";
        }
        if (line.startsWith("print ") || line.equals("print")) {
            return "System.out.print(" + line.substring(line.indexOf("print") + 5).trim() + ");\n";
        }
        if (line.contains("read")) {
            return line.substring(line.indexOf("read") + 5).trim() + " = scanner.nextInt();\n";
        }
        return "";
    }

    private static String translateForLoop(String line) {
        Matcher matcher = Pattern.compile("for (\\w+) = (\\d+) (to|until) (.+)").matcher(line);
        if (matcher.find()) {
            String var = matcher.group(1);
            String start = matcher.group(2);
            String endCondition = matcher.group(4);
            String loopCondition = matcher.group(3).equals("to") ? var + " <= " + endCondition : endCondition;
            return "for (int " + var + " = " + start + "; " + loopCondition + "; " + var + "++) {\n";
        }
        return "";
    }

    private static String removeCommentFromLine(String line) {
        if (line.contains("#")) {
            return line.substring(0, line.indexOf("#"));
        }
        return line;
    }

    private static String extractComment(String line) {
        if (line.contains("#")) {
            String comment = line.substring(line.indexOf("#"));
            return "//" + comment.replaceFirst("#", "");
        }
        return "";
    }

}
