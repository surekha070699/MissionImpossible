package org.example.Helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.example.constants.FileType;
import org.example.constants.ResourceName;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.io.FilenameFilter;

@UtilityClass
public class FileReader {
    private static final Logger LOGGER = LogManager.getLogger(FileReader.class);
    private static final String DRIVER_DIR = "driver/";
    private static final String TEMPLATES_DIR = "templates/";
    private static final String TEST_DATA_DIR = "testData/";
    private static final String API_TEST_DATA_DIR = "apiTestData/";
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";
    public static final String TEST_DATA_DOWNLOAD_PATH = "src/test/resources/TestData/downloadedFiles";
    private static final String JSON_DATA_PATH = "src/main/resources/templates/";

    public static <T> Object loadDriverFile(ResourceName resourceName, Class<T> containerClass) {
        return load(resourceName, containerClass, DRIVER_DIR, FileType.YAML);
    }

    public static <T> Object loadTemplate(ResourceName resourceName, Class<T> containerClass) {
        return load(resourceName, containerClass, TEMPLATES_DIR, FileType.JSON);
    }

    public static <T> Object loadTestData(ResourceName resourceName, Class<T> containerClass) {
        return load(resourceName, containerClass, TEST_DATA_DIR, FileType.JSON);
    }

    public static <T> Object loadAPITestData(ResourceName resourceName, Class<T[][]> containerClass) {
        return deserializeToObject(resourceName, containerClass, API_TEST_DATA_DIR, FileType.YAML);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object load(ResourceName resourceName, Class containerClass, String directory, FileType fileType) {
        String resourceFile = resourceName.toString() + fileType.toString();
        try (InputStream templateStream = FileReader.class.getResourceAsStream("/" + directory + resourceFile)) {
            if (templateStream == null)
                throw new IOException("Invalid Resource Name: " + resourceName);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(templateStream, containerClass);
        } catch (IOException e) {
            LOGGER.error("Unable to load the fie: [{}]", e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> getMapFromJson(ResourceName resourceName) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(
                    new File(Paths.get(TEST_DATA_PATH, resourceName.toString()).toAbsolutePath() + ".json"),
                    new TypeReference<>() {
                    });
        } catch (Exception exception) {
            LOGGER.info(String.format("There is an error to read file - %s. Exception: %s", resourceName, exception));
        }
        return jsonMap;
    }

    public static <T> Object[][] loadTemplates(ResourceName resourceName, Class<T[][]> convertType) {
        return deserializeToObject(resourceName, convertType, TEMPLATES_DIR, FileType.JSON);
    }

    private static <T> Object[][] deserializeToObject(ResourceName resourceName, Class<T[][]> convertType, String directory, FileType fileType) {
        String resourceFile = resourceName.toString() + fileType.toString();
        try (InputStream templateStream = FileReader.class.getResourceAsStream("/" + directory + resourceFile)) {
            if (templateStream == null)
                throw new IOException("Invalid Resource Name: " + resourceName);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            return mapper.readValue(templateStream, convertType);
        } catch (Exception exception) {
            LOGGER.info(String.format("There is an error to read file - %s. Exception: %s", resourceName, exception));
        }
        return new String[0][0];
    }

    public static String getResourcePath(ResourceName name) {
        return Paths.get(TEST_DATA_PATH, name.toString()).toAbsolutePath() + FileType.XML.toString();
    }

    public static String getResourcePathForXLSC(ResourceName name, String filePath) {
        return Paths.get(filePath, name.toString()).toAbsolutePath() + FileType.XLSX.toString();
    }

    public static String getResourcePathForCSV(ResourceName name) {
        return Paths.get(TEST_DATA_PATH, name.toString()).toAbsolutePath() + FileType.CSV.toString();
    }

    public static String getResourcePathForJSON(String fileName) {
        return Paths.get(JSON_DATA_PATH, fileName).toAbsolutePath().toString() + FileType.JSON;
    }

    public static String getUserSystemDownloadedPath() {
        String home = System.getProperty("user.home");
        return home + "/Downloads/";
    }

    public static void removeFile(ResourceName name, String directory) {
        File file = new File(getResourcePath(name, directory));
        if (file.exists()) {
            LOGGER.info("Deleting File  {}", name);
            try {
                Files.delete(file.toPath());
                Assert.assertTrue(Files.notExists(file.toPath()), "File not deleted");
            } catch (IOException exception) {
                LOGGER.info(String.format("Error is there while deleting file - %s. Exception is : %s", name, exception));
            }
        }
    }

    public static void removeFile(String fileName) {
        File file = new File((Paths.get(TEST_DATA_PATH, fileName)).toString());
        if (file.exists()) {
            LOGGER.info("Deleting File  {}", fileName);
            try {
                Files.delete(file.toPath());
                Assert.assertTrue(Files.notExists(file.toPath()), "File couldn't deleted");
            } catch (IOException exception) {
                LOGGER.info(String.format("There is an error to delete file - %s. Exception is : %s", fileName, exception));
            }
        }
    }

    public static String getResourcePath(ResourceName name, String directory) {
        switch (directory) {
            case "downloadedFiles":
                return getResourcePathForXLSC(name, TEST_DATA_DOWNLOAD_PATH);
            case "testData":
                return getResourcePathForXLSC(name, TEST_DATA_PATH);
            case "systemDownloadedFiles":
                return getResourcePathForCSV(name);
            default:
                return getResourcePathForXLSC(name, TEST_DATA_DIR);
        }
    }

    public static void removeFiles(ResourceName name, String directory) {
        File directoryPath = new File(directory);
        FilenameFilter fileFilter = (path, file) -> file.startsWith(name.toString());
        String[] filesList = directoryPath.list(fileFilter);
        assert filesList != null;
        for (String fileName : filesList) {
            File file = new File(directory + fileName);
            try {
                if ((file.getName().startsWith(name.toString()))) {
                    LOGGER.info("Deleting File {}", name);
                    Files.delete(file.toPath());
                    Assert.assertTrue(Files.notExists(file.toPath()), "File is not deleted");
                }
            } catch (IOException exception) {
                LOGGER.info(String.format("Error while deleting the file - %s. Exception is : %s", fileName, exception));
            }
        }
    }

    public static long fileSize(ResourceName name) {
        File file = new File(getResourcePath(name, TEST_DATA_DIR));
        return file.length();
    }

    public String serializeObjectToJson(Object object) {
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            LOGGER.error("Unable to serialize object to JSON: [{}]", e.getMessage());
        }
        return jsonString;
    }

    public static String getTextFilePath(ResourceName name) {
        return Paths.get(TEST_DATA_PATH, name.toString()).toAbsolutePath() + FileType.TXT.toString();
    }
}

