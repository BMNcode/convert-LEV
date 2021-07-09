package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public static Map<String, String> getLEV(String filePath) throws IOException {
        try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
            Map<String, String> resultMap = lines.collect(
                    Collectors.toMap(l -> Arrays.stream(l.split("\\t")).findFirst().get(),
                                     l -> Arrays.stream(l.split("\\t")).skip(1).collect(Collectors.joining())));
            return resultMap;
        }
    }

    public static Map<String, String> collectDublicate (Map<String, String> src) {
        HashMap<String, String> duplicatMap = new HashMap<>();
        Set<Map.Entry<String, String>> entrySet = src.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();

            if(duplicatMap.containsKey(value)) {
                duplicatMap.put(value, duplicatMap.get(value)+","+key);
            } else {
                duplicatMap.put(value, key);
            }
        }
        return duplicatMap;
    }

    public static String sortReference(String src) {
        return Arrays.stream(src.split(","))
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.joining(","));
    }

    public static void save (Map<String, String> src) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get("result.txt"), StandardCharsets.UTF_16,
                             StandardOpenOption.CREATE)) {

            for (Map.Entry<String, String> entry : src.entrySet()) {
                writer.write(entry.getValue() + "\t" + entry.getKey() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
