import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

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
        List<Integer> opcodeList = new ArrayList<>();
        opcodeList.add(1);
        opcodeList.add(0);
        opcodeList.add(0);
        opcodeList.add(3);
        opcodeList.add(1);
        opcodeList.add(1);
        opcodeList.add(2);
        opcodeList.add(3);

        opcodeList.forEach(code => System.out.println(code));

    }
}
