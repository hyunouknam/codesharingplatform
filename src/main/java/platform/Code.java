package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Code {
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String title;

    private LocalDate date;

    public Code(String code, String title, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
