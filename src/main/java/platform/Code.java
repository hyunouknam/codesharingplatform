package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code {
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String title;

    public Code(String code, String title) {
        this.code = code;
        this.title = title;
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

}
