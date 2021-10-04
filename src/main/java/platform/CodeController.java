package platform;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.List.*;

@Controller
public class CodeController {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private final String title = "Code";
    private final String codeData = "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}";

    private Code code = new Code(codeData, title, LocalDateTime.now().format(formatter), 34234);

    //private Map<Integer,Code> codeMap = new HashMap<>();
    private List<Code> codeList = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(1);

    public CodeController() {
    }

    @PostMapping(path = "/api/code/new")
    @ResponseBody
    public Map<String,String> postCode(@RequestBody Code code){
        codeList.add(new Code(code.getCode(), "Code", LocalDateTime.now().format(formatter), counter.get()));
        Map<String,String> idMap = new HashMap<>();
        idMap.put("id", String.valueOf(counter.get()));
        counter.getAndIncrement();
        return idMap;
    }

    @GetMapping(path = "/api/code/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Code getApiCode(@PathVariable int id) {
        Code currentCode = null;
        for(Code code: codeList){
            if(code.getId() == id){
                currentCode = code;
            }
        }
        return currentCode;
    }

    @GetMapping(path = "/code/{id}")
    public String getHtmlCode(@PathVariable int id, Model model) {
        List<Code> currentList = new ArrayList<>();
        for(Code code: codeList){
            if(code.getId() == id){
                currentList.add(code);
            }
        }
        model.addAttribute("title", "Code");
        model.addAttribute("codes", currentList);
        return "codeView";
    }

    @GetMapping(path = "/code/latest")
    public String getLatestHtmlCode(Model model) {
        if(codeList.size() > 10){
            model.addAttribute("codes", codeList.subList((codeList.size()-10), codeList.size()));
        }else{
            model.addAttribute("codes", codeList);
        }
        model.addAttribute("title", "Latest");
        return "codeView";
    }

    @GetMapping(path = "/api/code/latest", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Code> getApiCode() {
        if(codeList.size() > 10){
            return codeList.subList((codeList.size()-10), codeList.size());
        }else{
            return codeList;
        }
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public ResponseEntity<String> getNewCode() {
        return ResponseEntity.ok()
                .body("<title>" + "Create" + "</title>"
                        + "<textarea id=\"code_snippet\">" + "..." + "</textarea>"
                        + "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">" + "Submit" + "</button>");
    }
}
