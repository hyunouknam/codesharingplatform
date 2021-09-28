package platform;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

@RestController
public class CodeController {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private final String title = "Code";
    private final String codeData = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private final Code code = new Code(codeData, title, LocalDateTime.now());

    public CodeController() {
    }

    @PostMapping(path = "/api/code/new")
    public Map<String,String> postCode(@RequestBody Code code){
        return Collections.emptyMap();
    }

    @GetMapping(path = "/api/code", produces = "application/json;charset=UTF-8")
    public Code getApiCode() {
        return code;
    }

    @GetMapping(path = "/code", produces = "text/html")
    public ResponseEntity<String> getHtmlCode() {
        return ResponseEntity.ok()
                .body("<title>" + code.getTitle() + "</title>"
                        + "<span id=\"load_date\">" + LocalDateTime.now().format(formatter) + "</span>"
                        + "<pre id=\"code+snippet\">" + code.getCode() + "</pre>");
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public ResponseEntity<String> getNewCode() {
        return ResponseEntity.ok()
                .body("<title>" + "Create" + "</title>"
                        + "<textarea id=\"code_snippet\">" + "..." + "</textarea>"
                        + "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">" + "Submit" + "</button>");
    }
}
