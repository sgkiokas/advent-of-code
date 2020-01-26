import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        Optional<Double> fuelRequired = weightList.stream()
                .map(weight -> {
                    return Math.floor(weight / 3) - 2;
                })
                .reduce((accumulator, currentValue) -> accumulator + currentValue);

        System.out.println(fuelRequired);

    }
}
