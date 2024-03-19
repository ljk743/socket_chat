package boot.spring.controller;

// Import necessary classes
import boot.spring.service.LoginService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

/**
 * REST controller for handling file upload requests.
 * This class uses MinIO, a high performance object storage service, for storing uploaded files.
 */
@RestController
public class FileController {
    // Autowire the LoginService
    @Autowired
    LoginService loginService;

    // Inject configuration properties for MinIO access
    @Value("${minio.access.name}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket.name}")
    private String bucketName;

    // Declare a MinioClient to interact with MinIO server
    private MinioClient minioClient;

    /**
     * Initializes the MinioClient with the specified MinIO server credentials.
     * This method is annotated with @PostConstruct to ensure it runs after the bean's properties have been set.
     */
    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * Handles file upload requests. This method receives a file and the username of the uploader.
     * The file is then uploaded to MinIO under a unique name within a specified bucket.
     *
     * @param file     The file to upload.
     * @param username The username of the person uploading the file.
     * @return A string message indicating the outcome of the upload operation.
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("username") String username) {
        try {
            // Retrieve the original file name
            String originalFileName = file.getOriginalFilename();
            // Extract the file extension
            String extension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";

            // Generate a unique file name using UUID to ensure uniqueness
            String uniqueFileName = UUID.randomUUID().toString() + extension;
            // Construct the object's storage path within the bucket
            String objectName = String.format("chat/%s", uniqueFileName);

            // Upload the file to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            // Return the URL to the uploaded file
            return minioUrl + "/" + bucketName + "/" + objectName;
        } catch (MinioException e) {
            // Return error message for MinIO exceptions
            return "Failed to upload: MinIO server Error - " + e.getMessage();
        } catch (IOException e) {
            // Return error message for IO exceptions
            return "Failed to upload: File reading Error - " + e.getMessage();
        } catch (Exception e) {
            // Return error message for all other exceptions
            return "Failed to upload: Unknown Errors - " + e.getMessage();
        }
    }
}
