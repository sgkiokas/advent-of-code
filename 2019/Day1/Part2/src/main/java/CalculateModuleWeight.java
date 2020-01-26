import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalculateModuleWeight {

    private static InputStream readFile(String fileName) {
        InputStream file = CalculateModuleWeight.class.getClassLoader().getResourceAsStream(fileName);
        return file;
    }

    public static void main(String[] args) {
        InputStream weights = readFile("weights.properties");
        String result = new BufferedReader(new InputStreamReader(weights))
                                          .lines().collect(Collectors.joining("\n"));

        List<Integer> weightList = Arrays.asList(result.split("\\r?\\n"))
                                         .stream()
                                         .map(w -> Integer.parseInt(w.trim()))
                                         .collect(Collectors.toList());

        List<Double> moduleFuelRequired;
        double totalModuleFuel = 0;
        for(int weight : weightList) {
            List<Double> weightArray = new ArrayList<>();
            moduleFuelRequired = calculateWeight(weight, weightArray);
            Optional<Double> fuel = moduleFuelRequired.stream().reduce((accumulator, currentAccumulator) -> accumulator + currentAccumulator);
            totalModuleFuel += fuel.get().doubleValue() - weight;
        }

        System.out.println(totalModuleFuel);

    }

    private static List<Double> calculateWeight(double weight, List<Double> weightArray) {
        if (weight <= 0) {
            return weightArray;
        } else {
            weightArray.add(weight);
            return calculateWeight(Math.floor(weight / 3) - 2, weightArray);
        }
    }
}
