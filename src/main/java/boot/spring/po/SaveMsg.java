package boot.spring.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

// Defines the Message class to represent the structure of messages in the application.
public class SaveMsg {

    // Sender's name
    private String from;
    // Receiver's name
    private String to;
    // The text content of the message
    private String text;
    // File URL, if any files are associated with the message
    private String files;
    // The time the message was sent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public SaveMsg() {
    }

    // Getter method for files
    public String getFiles() {
        return files;
    }

    public SaveMsg(String from) {
        this.from = from;
    }

    // Setter method for files
    public void setFiles(String files) {
        this.files = files;
    }

    // Getter method for the sender's name
    public String getFrom() {
        return from;
    }

    // Setter method for the sender's name
    public void setFrom(String from) {
        this.from = from;
    }

    // Getter method for the receiver's name
    public String getTo() {
        return to;
    }

    // Setter method for the receiver's name
    public void setTo(String to) {
        this.to = to;
    }

    // Getter method for the text content of the message
    public String getText() {
        return text;
    }

    // Setter method for the text content of the message
    public void setText(String text) {
        this.text = text;
    }

    // Getter method for the date the message was sent
    public Date getDate() {
        return date;
    }

    // Setter method for the date the message was sent
    public void setDate(Date date) {
        this.date = date;
    }

}
