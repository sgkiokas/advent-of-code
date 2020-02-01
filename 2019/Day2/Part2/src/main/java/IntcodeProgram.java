import java.awt.event.TextEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class IntcodeProgram {

    private static InputStream readFile(String fileName) {
        InputStream file = IntcodeProgram.class.getClassLoader().getResourceAsStream(fileName);
        return file;
    }

    public static void main(String[] args) {
        // read the input data
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

        // put the following codes in the original map in order to properly initialize it as per the instructions
        opcodeMap.put(1, 12);
        opcodeMap.put(2, 2);

        for (int noun = 0; noun <= 99; noun ++) {
            for(int verb = 0; verb <= 99; verb ++) {
                int executionResult = exdcuteComibation(opcodeMap, noun, verb);
                if(executionResult == 19690720) {
                    System.out.printf("Solution: %s", 100 * noun + verb);;
                    return;
                }

            }
        }
    }

    private static int exdcuteComibation(Map<Integer, Integer> opcodes, int noun, int verb) {
        Map<Integer, Integer> tempOpcodeMap = new HashMap<>(opcodes);
        tempOpcodeMap.put(1, noun);
        tempOpcodeMap.put(2, verb);

        tempOpcodeMap = restoreComputer(tempOpcodeMap);
        return tempOpcodeMap.get(0);
    }


    private static Map<Integer, Integer> restoreComputer(Map<Integer, Integer> intcodeProgram) {
        int resultPosition, firstNumberPosition, secondNumberPosition, firstNumber, secondNumber;
        boolean haltProgram = false;

        for(int i = 0; i < intcodeProgram.size() ; i += 4) {
            if (haltProgram) {
                break;
            }

            switch (intcodeProgram.get(i)) {
                case 1:
                    // addition
                    firstNumberPosition = intcodeProgram.get(i + 1);
                    secondNumberPosition = intcodeProgram.get(i + 2);

                    firstNumber = intcodeProgram.get(firstNumberPosition);
                    secondNumber = intcodeProgram.get(secondNumberPosition);

                    resultPosition = intcodeProgram.get(i + 3);
                    intcodeProgram.put(resultPosition, firstNumber + secondNumber);

                    break;
                case 2:
                    // multiplication
                    firstNumberPosition = intcodeProgram.get(i + 1);
                    secondNumberPosition = intcodeProgram.get(i + 2);

                    firstNumber = intcodeProgram.get(firstNumberPosition);
                    secondNumber = intcodeProgram.get(secondNumberPosition);

                    resultPosition = intcodeProgram.get(i + 3);
                    intcodeProgram.put(resultPosition, firstNumber * secondNumber);

                    break;
                case 99:
                    // error - halt the program
                    haltProgram = true;
                    break;
            }
        }

        return intcodeProgram;
    }
}