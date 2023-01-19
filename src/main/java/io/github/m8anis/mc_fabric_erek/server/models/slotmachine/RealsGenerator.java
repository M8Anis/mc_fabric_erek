/*
Copyright © 2022-2023 https://github.com/M8Anis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.m8anis.mc_fabric_erek.server.models.slotmachine;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("unused")
@Environment(EnvType.SERVER)
public class RealsGenerator {

    private static final Random random = new Random();

    private static final ArrayList<Integer> possiblyIndexesOfB = new ArrayList<>(
            Arrays.asList(
                    22, 52, 113, 133, 83, 115, 60, 65, 137, 129
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOfW = new ArrayList<>(
            Arrays.asList(
                    40, 43, 60, 95, 119, 122, 126, 134, 0, 74, 83, 84, 87, 102, 120
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOf1 = new ArrayList<>(
            Arrays.asList(
                    17, 25, 33, 35, 49, 56, 63, 66, 71, 91, 99, 103, 108, 111, 118, 135, 2, 13, 18, 21, 26, 28, 42, 50,
                    52, 54, 76, 88, 114, 115, 117, 123, 127, 4, 7, 8, 31, 39, 44, 51, 69, 78, 81, 90, 105, 130, 137, 14,
                    48, 65, 68, 92, 97, 112, 116, 136, 12, 64, 85, 104, 110, 113, 129, 29, 34, 72, 77, 98, 100, 125
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOf2 = new ArrayList<>(
            Arrays.asList(
                    0, 34, 48, 54, 55, 64, 10, 20, 26, 35, 39, 40, 42, 101, 106, 111, 120, 3, 5, 28, 46, 47, 85, 98,
                    104, 13, 22, 23, 41, 61, 62, 65, 14, 24, 38, 7, 94, 119, 21, 27, 30, 33, 50, 59, 67, 80, 84, 89,
                    116, 118, 36, 79, 25, 81, 1, 2, 12, 15, 52, 58, 122, 123, 124, 66, 71, 75, 97, 100, 129, 11, 31,
                    32, 45, 70, 88, 109, 114, 115
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOf3 = new ArrayList<>(
            Arrays.asList(
                    87, 117, 3, 11, 27, 31, 33, 14, 49, 58, 77, 112, 127, 1, 5, 59, 4, 23, 30, 39, 109, 28, 32, 83, 26,
                    45, 48, 81, 111, 9, 12, 38, 88, 13, 18, 25, 34, 94, 51, 90, 96, 36, 40, 136, 7, 29, 20, 44, 121, 21,
                    56, 46, 66, 43, 62, 65, 97, 8, 19, 22, 93, 10, 55, 73, 76, 15, 41, 102, 50, 75, 37, 6, 89, 63, 82,
                    91, 115, 17, 60, 99, 108, 0, 2, 69, 70, 78, 86, 68, 72, 74, 24
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOfA = new ArrayList<>(
            Arrays.asList(
                    25, 40, 115, 3, 4, 20, 35, 27, 84, 19, 36, 5, 0, 9, 57, 15, 86, 18, 7, 44, 14, 48, 26, 16, 59, 1,
                    105, 6, 8, 55, 96, 12, 45, 51, 32, 11, 61, 70, 30, 94, 60, 21, 71, 134, 97, 31, 28, 34, 2, 39, 62,
                    131, 66, 63, 103, 58, 49, 29, 33, 88, 46, 10, 79, 42, 41, 24, 22, 47, 76, 37, 126, 77, 53, 95, 13,
                    50, 74, 38, 82, 104, 89, 72, 112, 17, 106, 54, 65, 101, 83, 75, 121, 137, 67, 73
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOfK = new ArrayList<>(
            Arrays.asList(
                    41, 61, 85, 11, 48, 57, 95, 129, 99, 106, 114, 124, 131, 132, 134, 60, 137, 135, 22, 97, 101, 102,
                    109, 125, 64, 94, 123, 119, 120, 66, 110, 121, 128, 36, 55, 98, 40, 45, 53, 72, 74, 133, 59, 80, 90,
                    37, 127, 82, 86, 107, 126, 26, 68, 96, 122, 104, 73, 103, 1, 65, 49, 76, 105, 69, 111, 77, 93, 58,
                    38, 136, 83, 92, 23, 67, 27, 32, 47, 16, 17, 50, 89, 115, 118, 87, 0, 2, 29, 108, 43, 54, 21, 51,
                    52, 34, 113, 42, 117, 30, 112, 6, 63, 81, 84, 15, 13
            )
    );
    private static final ArrayList<Integer> possiblyIndexesOfQ = new ArrayList<>(
            Arrays.asList(
                    29, 79, 89, 90, 106, 121, 0, 72, 74, 92, 96, 97, 105, 117, 124, 21, 70, 108, 126, 127, 3, 13, 66,
                    67, 101, 2, 7, 26, 38, 41, 47, 86, 112, 118, 123, 130, 42, 78, 93, 94, 116, 17, 37, 50, 55, 57, 103,
                    135, 27, 52, 65, 113, 132, 12, 54, 87, 133, 23, 56, 83, 100, 114, 73, 88, 119, 71, 39, 64, 85, 102,
                    22, 35, 49, 75, 104, 6, 40, 48, 131, 91, 99, 109, 111, 120, 5, 68, 84, 95, 107, 44, 129, 20, 115,
                    16, 63, 134, 137, 43, 61, 125, 128, 59, 76, 77, 98
            )
    );

    @SuppressWarnings("unused")
    protected static void getReal() {

        int maxB = 2;
        int maxW = 2;
        int max1 = 14;
        int max2 = 17;
        int max3 = 22;
        int maxA = 19;
        int maxK = 24;
        int maxQ = 17;
        int realSymbols = 135;

        String[] real = new String[realSymbols];

        for (int x = 0; x < maxB; x++) {
            while (possiblyIndexesOfB.size() != 0) {
                int index = random.nextInt(possiblyIndexesOfB.size());
                int value = possiblyIndexesOfB.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOfB.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "B";
                    possiblyIndexesOfB.remove(index);
                    break;
                }
                possiblyIndexesOfB.remove(index);
            }
            if (possiblyIndexesOfB.size() == 0) System.out.println("probably B is not full");
        }

        for (int x = 0; x < maxW; x++) {
            while (possiblyIndexesOfW.size() != 0) {
                int index = random.nextInt(possiblyIndexesOfW.size());
                int value = possiblyIndexesOfW.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOfW.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "W";
                    possiblyIndexesOfW.remove(index);
                    break;
                }
                possiblyIndexesOfW.remove(index);
            }
            if (possiblyIndexesOfW.size() == 0) System.out.println("probably W is not full");
        }

        for (int x = 0; x < max1; x++) {
            while (possiblyIndexesOf1.size() != 0) {
                int index = random.nextInt(possiblyIndexesOf1.size());
                int value = possiblyIndexesOf1.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOf1.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "1";
                    possiblyIndexesOf1.remove(index);
                    break;
                }
                possiblyIndexesOf1.remove(index);
            }
            if (possiblyIndexesOf1.size() == 0) System.out.println("probably 1 is not full");
        }

        for (int x = 0; x < max2; x++) {
            while (possiblyIndexesOf2.size() != 0) {
                int index = random.nextInt(possiblyIndexesOf2.size());
                int value = possiblyIndexesOf2.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOf2.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "2";
                    possiblyIndexesOf2.remove(index);
                    break;
                }
                possiblyIndexesOf2.remove(index);
            }
            if (possiblyIndexesOf2.size() == 0) System.out.println("probably 2 is not full");
        }

        for (int x = 0; x < max3; x++) {
            while (possiblyIndexesOf3.size() != 0) {
                int index = random.nextInt(possiblyIndexesOf3.size());
                int value = possiblyIndexesOf3.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOf3.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "3";
                    possiblyIndexesOf3.remove(index);
                    break;
                }
                possiblyIndexesOf3.remove(index);
            }
            if (possiblyIndexesOf3.size() == 0) System.out.println("probably 3 is not full");
        }

        for (int x = 0; x < maxA; x++) {
            while (possiblyIndexesOfA.size() != 0) {
                int index = random.nextInt(possiblyIndexesOfA.size());
                int value = possiblyIndexesOfA.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOfA.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "A";
                    possiblyIndexesOfA.remove(index);
                    break;
                }
                possiblyIndexesOfA.remove(index);
            }
            if (possiblyIndexesOfA.size() == 0) System.out.println("probably A is not full");
        }

        for (int x = 0; x < maxK; x++) {
            while (possiblyIndexesOfK.size() != 0) {
                int index = random.nextInt(possiblyIndexesOfK.size());
                int value = possiblyIndexesOfK.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOfK.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "K";
                    possiblyIndexesOfK.remove(index);
                    break;
                }
                possiblyIndexesOfK.remove(index);
            }
            if (possiblyIndexesOfK.size() == 0) System.out.println("probably K is not full");
        }

        for (int x = 0; x < maxQ; x++) {
            while (possiblyIndexesOfQ.size() != 0) {
                int index = random.nextInt(possiblyIndexesOfQ.size());
                int value = possiblyIndexesOfQ.get(index);
                if (!(value <= real.length - 1)) {
                    possiblyIndexesOfQ.remove(index);
                    continue;
                }
                if (real[value] == null) {
                    real[value] = "Q";
                    possiblyIndexesOfQ.remove(index);
                    break;
                }
                possiblyIndexesOfQ.remove(index);
            }
            if (possiblyIndexesOfQ.size() == 0) System.out.println("probably Q is not full");
        }

        int bCounter = 0;
        int wCounter = 0;
        int oneCounter = 0;
        int twoCounter = 0;
        int threeCounter = 0;
        int aCounter = 0;
        int kCounter = 0;
        int qCounter = 0;
        int jCounter = 0;
        for (int i = 0; i < real.length; i++) {
            if (real[i] == null) {
                jCounter++;
                real[i] = "J";
            } else if (real[i].equals("B")) {
                bCounter++;
            } else if (real[i].equals("W")) {
                wCounter++;
            } else if (real[i].equals("1")) {
                oneCounter++;
            } else if (real[i].equals("2")) {
                twoCounter++;
            } else if (real[i].equals("3")) {
                threeCounter++;
            } else if (real[i].equals("A")) {
                aCounter++;
            } else if (real[i].equals("K")) {
                kCounter++;
            } else if (real[i].equals("Q")) {
                qCounter++;
            }
        }

        System.out.println(
                (jCounter == (realSymbols - maxQ - maxK - maxA - max3 - max2 - max1 - maxW - maxB))
                        && (qCounter == maxQ)
                        && (kCounter == maxK)
                        && (aCounter == maxA)
                        && (threeCounter == max3)
                        && (twoCounter == max2)
                        && (oneCounter == max1)
                        && (wCounter == maxW)
                        && (bCounter == maxB)
        );
        System.out.println(jCounter);

        System.out.println();

        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t");
        for (String realSymbol : real) {
            sb.append("\"").append(realSymbol).append("\", ");
        }
        sb.append("\n}");
        System.out.println(sb);
    }

}
