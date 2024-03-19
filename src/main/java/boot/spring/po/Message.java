package boot.spring.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

// Defines the Message class to represent the structure of messages in the application.
public class Message {

    // Sender's name
    private String from;
    // Receiver's name
    private String to;
    // The text content of the message
    private String text;
    // File URL, if any files are associated with the message
    private String files;
    // The time the message was sent
    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // Specifies the date format for serialization and deserialization
    private Date date;

    // Getter method for files
    public String getFiles() {
        return files;
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
