package boot.spring.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class FileMessage {
    private String from;
    private String to;
    private String content;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String type;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
