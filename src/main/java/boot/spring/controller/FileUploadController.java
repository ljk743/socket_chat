// FileUploadController.java
package boot.spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import boot.spring.service.WebSocketServer;

@Controller
public class FileUploadController {

    @Autowired
    private WebSocketServer webSocketServer; // 自动注入WebSocket服务

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        // 这里是处理文件上传的逻辑
        try {
            if (!file.isEmpty()) {
                // 假设文件上传逻辑已经完成，我们得到了文件的URL
                String fileUrl = "http://127.0.0.1/ftp/files/" + file.getOriginalFilename();

                // 构建消息对象并发送WebSocket消息
                webSocketServer.sendInfo(username, "File uploaded successfully: " + fileUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "File upload failed";
        }
        return "File uploaded successfully";
    }
}
