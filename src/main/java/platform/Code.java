package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String title;
    private LocalDateTime date;

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    public Code(){
    }

    public Code(String code){
        this.title = "";
        this.date = LocalDateTime.now();
    }

    public Code(String code, String title, LocalDateTime date) {
        this.code = code;
        this.title = title;
        this.date = date;
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
        return date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
