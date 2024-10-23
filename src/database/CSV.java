package database;

import java.io.*;
import java.lang.reflect.*;
import java.util.Map;

// TODO last time we have one load function per class, which is less feasible now that we have so many tables, so i'm trying to use reflection.
// and since if we're already using reflection on load(), we might as well use it on save(), and get rid of toString()

public class CSV {
    static String dataPath = "data/";

    // TODO test
    public static <T extends BaseItem> void save(Map<String, T> objects, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath + filePath))) {
            if (!objects.isEmpty()) {
                for (Map.Entry<String, T> entry : objects.entrySet()) {
                    T object = entry.getValue();
                    StringBuilder csvString = new StringBuilder();
                    Field[] fields = object.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        csvString.append(field.get(object)).append(",");
                    }
                    // Remove the trailing comma
                    if (csvString.length() > 0) {
                        csvString.setLength(csvString.length() - 1);
                    }

                    writer.write(csvString.toString());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // TODO test
    public static <T extends BaseItem> void load(Map<String, T> objects, String filePath, Class<T> clazz) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataPath + filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Constructor<T> constructor = clazz.getConstructor(String.class, String.class);
                T object = constructor.newInstance(data[0], data[1]);
                objects.put(data[0], object);
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
