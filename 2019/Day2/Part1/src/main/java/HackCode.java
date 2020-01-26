import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class HackCode {

    private static InputStream readFile(String fileName) {
        InputStream file = HackCode.class.getClassLoader().getResourceAsStream(fileName);
        return file;
    }

    public static void main(String[] args) {
        InputStream weights = readFile("opcodes.properties");
        String result = new BufferedReader(new InputStreamReader(weights))
                                          .lines().collect(Collectors.joining("\n"));

        List<Integer> opcodeList = Arrays.asList(result.split(","))
                                         .stream()
                                         .map(code -> Integer.parseInt(code.trim()))
                                         .collect(Collectors.toList());

        Map<Integer, Integer> opcodeMap = new HashMap<>();

        // we convert the ArrayList to a HashMap in order to properly fetch the indexes of each opcode
        int index = 0;
        for (Integer opcode : opcodeList) {
            opcodeMap.put(index, opcode);
            index++;
        }

        // restore gravity assist program to "1202 program alarm"
        opcodeMap.put(1, 12);
        opcodeMap.put(2, 2);

        opcodeMap = restoreComputer(opcodeMap);

        // final answer
        System.out.println(opcodeMap.get(0));
    }

    private static Map<Integer, Integer> restoreComputer(Map<Integer, Integer> incodeProgram) {
        int resultPosition, firstNumberPosition, secondNumberPosition, firstNumber, secondNumber = 0;
        boolean haltProgram = false;
        for(int i = 0; i < incodeProgram.size() ; i += 4) {
            if (haltProgram) {
                break;
            }
            switch (incodeProgram.get(i)) {
                case 1:
                    // addition
                    firstNumberPosition = incodeProgram.get(i + 1);
                    secondNumberPosition = incodeProgram.get(i + 2);

                    firstNumber = incodeProgram.get(firstNumberPosition);
                    secondNumber = incodeProgram.get(secondNumberPosition);

                    resultPosition = incodeProgram.get(i + 3);
                    incodeProgram.put(resultPosition, firstNumber + secondNumber);
                    break;
                case 2:
                    // multiplication
                    firstNumberPosition = incodeProgram.get(i + 1);
                    secondNumberPosition = incodeProgram.get(i + 2);

                    firstNumber = incodeProgram.get(firstNumberPosition);
                    secondNumber = incodeProgram.get(secondNumberPosition);

                    resultPosition = incodeProgram.get(i + 3);
                    incodeProgram.put(resultPosition, firstNumber * secondNumber);
                    break;
                case 99:
                    // error - halt the program
                    haltProgram = true;
                    break;
                default:
                    // in any other case, just move on to the next code
                    break;
            }
    }

        return incodeProgram;
    }
}
