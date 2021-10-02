package platform;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.List.*;

@Controller
public class CodeController {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private final String title = "Code";
    private final String codeData = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private Code code = new Code(codeData, title, LocalDateTime.now().format(formatter));

    private List<Code> codeList = new ArrayList<>();

    public CodeController() {
    }

    @PostMapping(path = "/api/code/new")
    @ResponseBody
    public Map<String,String> postCode(@RequestBody Code code){
        codeList.add(new Code(code.getCode(), "Code", LocalDateTime.now().format(formatter)));
        return Collections.emptyMap();
    }

    @GetMapping(path = "/api/code", produces = "application/json;charset=UTF-8")
    public Code getApiCode() {
        return code;
    }

    @GetMapping(path = "/code")
    public String getHtmlCode(Model model) {
        model.addAttribute("codes", codeList);
        return "codeView";
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public ResponseEntity<String> getNewCode() {
        return ResponseEntity.ok()
                .body("<title>" + "Create" + "</title>"
                        + "<textarea id=\"code_snippet\">" + "..." + "</textarea>"
                        + "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">" + "Submit" + "</button>");
    }
}
