package github.d3d9_dll.minecraft.fabric.erek.models.slotmachine;

import java.util.HashMap;

public class Lines {

    public final String[][] lines;
    public final boolean isBonusGame;
    private final String[][] reals;

    public Lines(String[][] reals) {
        this.reals = reals;

        lines = new String[][]{
                getLine1(),
                getLine2(),
                getLine3(),
                getLine4(),
                getLine5(),
                getLine6(),
                getLine7(),
                getLine8(),
                getLine9(),
                getLine10()
        };

        int bonusGameSymbolCounter = 0;
        for (String[] strings : reals) {
            for (String string : strings) {
                if (string.equals("B")) {
                    bonusGameSymbolCounter++;
                    break;
                }
            }
        }

        isBonusGame = bonusGameSymbolCounter >= 5;
    }

    private String[] getLine1() {
        return new String[]{
                reals[0][1],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][1]
        };
    }

    private String[] getLine2() {
        return new String[]{
                reals[0][0],
                reals[1][0],
                reals[2][0],
                reals[3][0],
                reals[4][0]
        };
    }

    private String[] getLine3() {
        return new String[]{
                reals[0][2],
                reals[1][2],
                reals[2][2],
                reals[3][2],
                reals[4][2]
        };
    }

    private String[] getLine4() {
        return new String[]{
                reals[0][0],
                reals[1][0],
                reals[2][1],
                reals[3][2],
                reals[4][2]
        };
    }

    private String[] getLine5() {
        return new String[]{
                reals[0][2],
                reals[1][2],
                reals[2][1],
                reals[3][0],
                reals[4][0]
        };
    }

    private String[] getLine6() {
        return new String[]{
                reals[0][2],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][2]
        };
    }

    private String[] getLine7() {
        return new String[]{
                reals[0][0],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][0]
        };
    }

    private String[] getLine8() {
        return new String[]{
                reals[0][2],
                reals[1][1],
                reals[2][0],
                reals[3][1],
                reals[4][2]
        };
    }

    private String[] getLine9() {
        return new String[]{
                reals[0][0],
                reals[1][1],
                reals[2][2],
                reals[3][1],
                reals[4][0]
        };
    }

    private String[] getLine10() {
        return new String[]{
                reals[0][2],
                reals[1][1],
                reals[2][1],
                reals[3][1],
                reals[4][0]
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int lineNum = 1;
        sb.append("{");
        for (String[] line : lines) {
            sb.append(lineNum)
                    .append(": ");
            for (String lineSymbol : line) {
                sb.append(lineSymbol);
            }
            sb.append(", ");
            lineNum++;
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    public static class Matched {

        private final Lines lines;
        private final HashMap<Integer, Line> matchedLines;

        public Matched(Lines lines) {
            this.lines = lines;
            matchedLines = getMatchedLines();
        }

        public HashMap<Integer, Line> getLines() {
            return matchedLines;
        }

        private HashMap<Integer, Line> getMatchedLines() {
            HashMap<Integer, Line> lines = new HashMap<>();
            for (int line = 0; line < this.lines.lines.length; line++) {
                String lineStartsWith = "";
                String[] givenLine = this.lines.lines[line];
                int lineLength = 0;
                for (int symbol = 0; symbol < givenLine.length; symbol++) {
                    if (symbol == 0) {
                        lineStartsWith = givenLine[symbol];
                        lineLength++;
                        continue;
                    }
                    if (lineStartsWith.equals("B")) break;
                    if (givenLine[symbol].equals(lineStartsWith) || givenLine[symbol].equals("W")) {
                        lineLength++;
                    } else {
                        break;
                    }
                }
                if (lineLength >= 3) {
                    lines.put(line + 1, new Line(lineStartsWith, lineLength));
                }
            }
            return lines;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int lineNum = 0; lineNum < 10; lineNum++) {
                if (matchedLines.containsKey(lineNum + 1)) {
                    sb.append(lineNum + 1)
                            .append(": ")
                            .append(matchedLines.get(lineNum + 1))
                            .append(", ");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
            return sb.toString();
        }

        public static class Line {

            public final String symbol;
            public final int length;

            public Line(String symbol, int length) {
                this.symbol = symbol;
                this.length = length;
            }

            @Override
            public String toString() {
                return symbol + "x" + length;
            }
        }

    }

}
