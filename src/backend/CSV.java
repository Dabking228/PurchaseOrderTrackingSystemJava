package backend;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.Map;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import data.*;

// last time we have one load function per class, and each class has a toString() function. this is much less feasible and elegant now that we have so many tables. so instead i'm using reflection.

public class CSV {
    static String dataDir = "data/";

    public static <T extends BaseItem> void save(Map<String, T> objects, String fileName) {
        try {
            Path dir = Paths.get(dataDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Path file = dir.resolve(fileName);
            if (!Files.exists(file)) {
                Files.createFile(file);
            }

            BufferedWriter writer = Files.newBufferedWriter(file);
            if (!objects.isEmpty()) {
                for (Map.Entry<String, T> entry : objects.entrySet()) {

                    StringBuilder csvString = new StringBuilder();
                    T object = entry.getValue();
                    Class<?> clazz = object.getClass();

                    while (clazz != null) {
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            csvString.append(field.get(object)).append(",");
                        }
                        clazz = clazz.getSuperclass();
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

    public static <T extends BaseItem> void load(Map<String, T> hashMap, String filePath, Class<T> clazz) {
        try {
            Path file = Paths.get(dataDir + filePath);
            BufferedReader reader = Files.newBufferedReader(file);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Constructor<?>[] constructors = clazz.getConstructors();
                Constructor<T> matchingConstructor = null;

                // FIXME tons of weird behaviors here
                // Find the constructor that matches the number of parameters
                for (Constructor<?> constructor : constructors) {
                    if (constructor.getParameterCount() == data.length) {
                        Class<?>[] parameterTypes = constructor.getParameterTypes();
                        if (parameterTypes[parameterTypes.length - 1].equals(String.class)) {
                            matchingConstructor = (Constructor<T>) constructor;
                            break;
                        }
                    }
                }

                if (matchingConstructor != null) {
                    // Convert data array to Object array
                    Parameter[] parameterTypes = matchingConstructor.getParameters();
                    Object[] parameters = new Object[data.length];
                    System.arraycopy(data, 0, parameters, 0, data.length);

                    for (int i = 0; i < data.length; i++) {
                        parameters[i] = convertStringToType(data[i], parameterTypes[i].getType());
                    }

                    // Create a new instance using the matching constructor
                    T object = matchingConstructor.newInstance(parameters);
                    hashMap.put(object.getId(), object);
                } else {
                    System.out.println("No matching constructor found for: " + clazz.getName());
                }
            }

            reader.close();

        } catch (IOException | ReflectiveOperationException e) {
            System.out.println("File not created yet: " + filePath);
        }
    }

    private static Object convertStringToType(String value, Class<?> type) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);

        } else if (type == Role.class) {
            return Role.valueOf(value);
        } else if (type == Status.class) {
            return Status.valueOf(value);

        } else if (type == Date.class) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            try {
                return dateFormat.parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format: " + value, e);
            }

        } else if (type == BigDecimal.class) {
            return new BigDecimal(value);
        }

        throw new IllegalArgumentException("Unsupported parameter type: " + type.getName());
    }

    public static Path createOrRetrieve(String directory, String filename) throws IOException {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
        return Paths.get(directory + File.separatorChar + filename);
    }
}
