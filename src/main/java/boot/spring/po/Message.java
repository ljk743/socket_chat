package boot.spring.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Message {

    //发送者name
    private String from;
    //接收者name
    private String to;
    //发送的文本
    private String text;
    //文件url
    private String files;
    //发送时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
