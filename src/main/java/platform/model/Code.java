package platform.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
public class Code {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String formattedDate;
    private int views;
    private int time;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean viewRestriction;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean timeRestriction;

    public Code(){
    }

    public Code(String code, String title, LocalDateTime date, int views, int time) {
        this.code = code;
        this.title = title;
        this.date = date;
        this.views = views;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int view) {
        this.views = view;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isViewRestriction() {
        return viewRestriction;
    }

    public void setViewRestriction(boolean viewRestriction) {
        this.viewRestriction = viewRestriction;
    }

    public boolean isTimeRestriction() {
        return timeRestriction;
    }

    public void setTimeRestriction(boolean timeRestriction) {
        this.timeRestriction = timeRestriction;
    }
}
