package boot.spring.controller;

import boot.spring.po.SaveMsg;
import boot.spring.service.RedisService;
import boot.spring.utils.AESUtil;
import boot.spring.utils.StringSorterUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private RedisService redisService;

    /**
     * Endpoint to save a message. The message along with its timestamp is encrypted before being saved.
     * It saves the encrypted message in two keys, one for the sender and one for the receiver.
     *
     * @param message The message object that contains the sender, receiver, content, and timestamp.
     * @throws Exception Throws an exception if the encryption process fails.
     */
    @PostMapping("/savemessage")
    public void saveMessage(@RequestBody SaveMsg message) throws Exception {
        // Sets the current date and time if the message date is not provided
        if (message.getDate() == null) {
            message.setDate(new Date());
        }
        Long time = message.getDate().getTime();
        // Converts the message object to a JSON string and appends the timestamp
        String messageJson = JSON.toJSONString(message);
        // Encrypts the message JSON string with the timestamp
        String emsg = AESUtil.encrypt(messageJson + time);
        // Saves the encrypted message under a key representing the sender
        redisService.saveMessage("From" + message.getFrom(), emsg);
        // Saves the same encrypted message under a key representing the receiver
        redisService.saveMessage("To" + message.getTo(), emsg);
    }

    /**
     * Endpoint to restore messages for a given user. It aggregates messages where the user is either
     * the sender or receiver, including messages from anonymous senders.
     * The messages are then sorted based on a specific criteria before being returned.
     *
     * @param currentuser The username of the current user for whom messages are to be restored.
     * @return A list of sorted messages for the user.
     * @throws Exception Throws an exception if the message retrieval or decryption process fails.
     */
    @RequestMapping(value = "/restoremessages", method = RequestMethod.GET)
    @ResponseBody
    public List<String> restoreMessage(@RequestParam("currentuser") String currentuser) throws Exception {
        // Logs the current user's username
        System.out.println(currentuser);
        // Retrieves messages where the current user is the sender
        List<String> messages = redisService.getMessage("From" + currentuser);
        // Adds to the list messages where the current user is the receiver
        messages.addAll(redisService.getMessage("To" + currentuser));
        // Additionally adds messages sent by anonymous senders
        messages.addAll(redisService.getMessage("FromAnonymous"));
        // Sorts the aggregated messages based on the numerical value of the last 13 characters
        List<String> sortedMessages = StringSorterUtil.sortStringsByLastDigits(messages);
        return sortedMessages;
    }

}
