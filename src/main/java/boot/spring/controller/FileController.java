package boot.spring.controller;

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

@RestController
public class FileController {
    @Autowired
    LoginService loginService;

    @Value("${minio.access.name}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket.name}")
    private String bucketName;

    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("username") String username) {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";

            // 使用UUID生成文件名以确保唯一性
            String uniqueFileName = UUID.randomUUID().toString() + extension;
            // 构建文件的存储路径
            String objectName = String.format("chat/%s", uniqueFileName);

            // 上传文件到MinIO
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return minioUrl+"/"+bucketName+"/"+objectName;
        } catch (MinioException e) {
            return "上传失败: MinIO服务出现问题 - " + e.getMessage();
        } catch (IOException e) {
            return "上传失败: 文件读取错误 - " + e.getMessage();
        } catch (Exception e) {
            return "上传失败: 未知错误 - " + e.getMessage();
        }
    }
}
