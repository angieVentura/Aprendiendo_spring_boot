package com.ecommerce.ecommerce.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio de subida de archivos", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if(!file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Solo se permiten imagenes.");
        }

        if(file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("El archivo excede el tamanio maximo permitido.");
        }
        try {
            if(fileName.contains("..")) {
                throw new RuntimeException("Nombre de archivo invalido: " + fileName);
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". Intenta de nuevo", ex);
        }
    }
}
