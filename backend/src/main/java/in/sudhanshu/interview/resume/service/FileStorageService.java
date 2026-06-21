package in.sudhanshu.interview.resume.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FileStorageService {
    private static final String UPLOAD_DIR = "uploads/resumes";

    public Path getUploadPath() throws IOException {
        Path path = Paths.get(UPLOAD_DIR);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        return path;
    }
}
