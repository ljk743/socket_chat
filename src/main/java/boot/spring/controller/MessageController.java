package boot.spring.controller;

import boot.spring.po.SaveMsg;
import boot.spring.service.RedisService;
import boot.spring.utils.AESUtil;
import boot.spring.utils.StringSorterUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import boot.spring.service.WebSocketServer;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private WebSocketServer webSocketServer;

    @PostMapping("/savemessage")
    public void saveMessage(@RequestBody SaveMsg message) throws Exception {
        Long time = message.getDate().getTime();
        String messageJson = JSON.toJSONString(message);
        String emsg = AESUtil.encrypt(messageJson + time);
        redisService.saveMessage("From" + message.getFrom(), emsg);
        redisService.saveMessage("To" + message.getTo(), emsg);
    }

    @RequestMapping(value = "/restoremessages", method = RequestMethod.GET)
    @ResponseBody
    public void restoreMessage(@RequestParam("currentuser") String currentuser) throws Exception {
        List<String> messages = redisService.getMessage("From" + currentuser);
        redisService.getMessage("To" + currentuser);
        messages.addAll(redisService.getMessage("To" + currentuser));
        messages.addAll(redisService.getMessage("To-1"));
        List<String> sortedMessages = StringSorterUtil.sortStringsByLastDigits(messages);
        for (int i = 0; i < sortedMessages.size(); i++) {
            webSocketServer.sendInfo(currentuser,sortedMessages.get(i));
        }
    }

}
