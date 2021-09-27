package platform;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeController {

    private final String title = "Code";
    private final String codeData = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private final Code code = new Code(codeData, title);

    public CodeController() {
    }

    @GetMapping(path = "/api/code", produces = "application/json;charset=UTF-8")
    public Code getApiCode() {
        return code;
    }

    @GetMapping(path = "/code", produces = "text/html")
    public ResponseEntity<String> getHtmlCode() {
        return ResponseEntity.ok()
                .body("<title>" + code.getTitle() + "</title>"
                        + "<pre>" + code.getCode() + "</pre>");
    }

}
