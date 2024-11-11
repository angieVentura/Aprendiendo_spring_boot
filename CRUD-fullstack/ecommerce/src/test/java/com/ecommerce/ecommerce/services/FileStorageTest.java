package com.ecommerce.ecommerce.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.mock;


public class FileStorageTest {


    private FileStorageService fileStorageService;
    private Path mockPath;


    @BeforeEach
    public void setUp() {
        mockPath = mock(Path.class);
        fileStorageService = new FileStorageService(mockPath);
    }


    @Test
    void testStoreFileSuccess() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.png",
                "image/png",
                "some-image-content".getBytes()
        );

        Path mockPath = mock(Path.class);

        when(mockPath.resolve(anyString())).thenReturn(Paths.get("test-path"));

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.copy(any(Path.class), any(), any())).thenReturn(null);

            String result = fileStorageService.storeFile(mockFile);

            assertNotNull(result);
            assertTrue(result.contains("test-image.png"));
        }
    }


    @Test
    void testStoreFileInvalidFileType() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-document.txt",
                "text/plain",
                "some-content".getBytes()
        );


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileStorageService.storeFile(mockFile);
        });


        assertEquals("Solo se permiten imagenes.", exception.getMessage());
    }


    @Test
    void testStoreFileExceedsMaxSize() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.png",
                "image/png",
                new byte[6 * 1024 * 1024]
        );


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileStorageService.storeFile(mockFile);
        });


        assertEquals("El archivo excede el tamanio maximo permitido.", exception.getMessage());
    }


    @Test
    void testStoreFileInvalidFileName() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "../invalid-name.png",
                "image/png",
                "some-image-content".getBytes()
        );


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileStorageService.storeFile(mockFile);
        });


        assertEquals("Nombre de archivo invalido: ../invalid-name.png", exception.getMessage());
    }


}
