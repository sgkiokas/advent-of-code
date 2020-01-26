import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalculateModuleWeight {
    public CalculateModuleWeight() {
    }

    private static InputStream readFile(String fileName) {
        InputStream file = CalculateModuleWeight.class.getClassLoader().getResourceAsStream(fileName);
        return file;
    }

    public static void main(String[] args) {
        InputStream weights = readFile("weights.properties");
        String result = (String)(new BufferedReader(new InputStreamReader(weights))).lines().collect(Collectors.joining("\n"));
        List<Integer> weightList = (List)Arrays.asList(result.split("\\r?\\n")).stream().map((w) -> {
            return Integer.parseInt(w.trim());
        }).collect(Collectors.toList());
        double totalModuleFuel = 0.0D;

        int weight;
        Optional fuel;
        for(Iterator var7 = weightList.iterator(); var7.hasNext(); totalModuleFuel += (Double)fuel.get() - (double)weight) {
            weight = (Integer)var7.next();
            List<Double> weightArray = new ArrayList();
            List<Double> moduleFuelRequired = calculateWeight((double)weight, weightArray);
            fuel = moduleFuelRequired.stream().reduce((accumulator, currentAccumulator) -> {
                return accumulator + currentAccumulator;
            });
        }

        System.out.println(totalModuleFuel);
    }

    private static List<Double> calculateWeight(double weight, List<Double> weightArray) {
        if (weight <= 0.0D) {
            return weightArray;
        } else {
            weightArray.add(weight);
            return calculateWeight(Math.floor(weight / 3.0D) - 2.0D, weightArray);
        }
    }
}
