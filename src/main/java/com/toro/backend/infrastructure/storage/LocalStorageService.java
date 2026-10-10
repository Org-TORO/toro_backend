package com.toro.backend.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toro.backend.infrastructure.exception.ServerException;

@Service
public class LocalStorageService {

    private static volatile String baseStorageUrl = StoragePath.DEV_BASE_STORAGE_URL;

    private final Path uploadRoot;

    public LocalStorageService(Environment environment) {
        this.uploadRoot = Paths.get(StoragePath.LOCAL_STORAGE_BASE_FOLDER).toAbsolutePath().normalize();
        configureBaseStorageUrl(environment);
    }

    public static void configureBaseStorageUrl(Environment environment) {
        baseStorageUrl = isDevelopment(environment)
                ? StoragePath.DEV_BASE_STORAGE_URL
                : "StoragePath.PROD_BASE_STORAGE_URL";
    }

    public static String getAbsoluteUploadUrl(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return filePath;
        }

        if (isAbsoluteUri(filePath)) {
            return filePath;
        }

        String normalizedPath = normalize(filePath);
        return trimTrailingSlash(baseStorageUrl) + "/uploads/" + normalizedPath;
    }

    public String save(MultipartFile file, String folder) {
        return save(file, folder, null);
    }

    public String save(MultipartFile file, String folder, String fileNamePrefix) {
        if (file == null) {
            throw new IllegalArgumentException("File is required.");
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }

        String normalizedFolder = normalize(folder);
        Path directory = resolveInsideUploadRoot(normalizedFolder);
        String fileName = generateFileName(file.getOriginalFilename(), fileNamePrefix);
        Path physicalPath = directory.resolve(fileName).normalize();

        if (!physicalPath.startsWith(directory)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        try {
            Files.createDirectories(directory);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, physicalPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new ServerException("Failed to save file.");
        }

        return normalizedFolder.isBlank()
                ? fileName
                : normalizedFolder + "/" + fileName;
    }

    public String update(MultipartFile file, String existingFilePath, String folder) {
        return update(file, existingFilePath, folder, null);
    }

    public String update(MultipartFile file, String existingFilePath, String folder, String fileNamePrefix) {
        delete(existingFilePath);
        return save(file, folder, fileNamePrefix);
    }

    public boolean delete(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return false;
        }

        try {
            Path physicalPath = resolveInsideUploadRoot(normalize(filePath));

            if (!Files.exists(physicalPath)) {
                return false;
            }

            Files.delete(physicalPath);
            deleteEmptyDirectories(physicalPath.getParent());

            return true;
        } catch (IOException | SecurityException | IllegalArgumentException ex) {
            return false;
        }
    }

    private static boolean isDevelopment(Environment environment) {
        return environment == null || Arrays.stream(environment.getActiveProfiles())
                .anyMatch(profile -> 
                    profile.equalsIgnoreCase("dev") 
                    || profile.equalsIgnoreCase("local"));
    }

    private static String generateFileName(String originalFileName, String prefix) {
        String extension = getExtension(originalFileName);
        String guid = UUID.randomUUID().toString().replace("-", "");

        if (prefix == null || prefix.isBlank()) {
            return guid + extension;
        }

        String normalizedPrefix = normalize(prefix).replace("/", "_");
        return normalizedPrefix + "_" + guid + extension;
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace('\\', '/')
                .replaceAll("^/+", "")
                .replaceAll("/+$", "")
                .trim();
    }

    private static String getExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isBlank()) {
            return "";
        }

        String normalizedFileName = originalFileName.replace('\\', '/');
        int slashIndex = normalizedFileName.lastIndexOf('/');
        String fileName = slashIndex >= 0 ? normalizedFileName.substring(slashIndex + 1) : normalizedFileName;
        int dotIndex = fileName.lastIndexOf('.');

        return dotIndex >= 0 ? fileName.substring(dotIndex) : "";
    }

    private static boolean isAbsoluteUri(String filePath) {
        try {
            URI uri = new URI(filePath);
            return uri.isAbsolute() && uri.getScheme() != null && uri.getScheme().startsWith("http");
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    private static String trimTrailingSlash(String value) {
        return value.replaceAll("/+$", "");
    }

    private Path resolveInsideUploadRoot(String relativePath) {
        Path resolvedPath = relativePath.isBlank()
                ? uploadRoot
                : uploadRoot.resolve(relativePath.replace('/', java.io.File.separatorChar));

        Path normalizedPath = resolvedPath.toAbsolutePath().normalize();

        if (!normalizedPath.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Invalid storage path.");
        }

        return normalizedPath;
    }

    private void deleteEmptyDirectories(Path directory) throws IOException {
        Path currentDirectory = directory;

        while (currentDirectory != null
                && currentDirectory.startsWith(uploadRoot)
                && !currentDirectory.equals(uploadRoot)) {
            try (var entries = Files.list(currentDirectory)) {
                if (entries.findAny().isPresent()) {
                    break;
                }
            }

            Files.delete(currentDirectory);
            currentDirectory = currentDirectory.getParent();
        }
    }
}
