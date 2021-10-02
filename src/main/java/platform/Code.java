package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {
    private int id;
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String title;
    private String date;

    public Code(){
    }

    public Code(String code, String title, String date, int id) {
        this.code = code;
        this.title = title;
        this.date = date;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
