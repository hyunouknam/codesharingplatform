package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class CodeController {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private final String title = "Code";

    @Autowired
    private CodeService codeService;

    public CodeController() {
    }

    @PostMapping(path = "/api/code/new")
    @ResponseBody
    public Map<String,String> postCode(@RequestBody Code code){
        Code newCode = new Code(code.getCode(), title, LocalDateTime.now().format(formatter));
        codeService.addCode(newCode);
        Map<String,String> idMap = new HashMap<>();
        idMap.put("id", String.valueOf(newCode.getId()));
        return idMap;
    }

    @GetMapping(path = "/api/code/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Code getApiCode(@PathVariable int id) {
        return codeService.getCode(id).get();
    }

    @GetMapping(path = "/code/{id}")
    public String getHtmlCode(@PathVariable int id, Model model) {
        List<Code> currentList = new ArrayList<>();
        Optional.ofNullable(codeService.getCode(id)).ifPresent(x -> currentList.add(x.get()));

        model.addAttribute("title", "Code");
        model.addAttribute("codes", currentList);
        return "codeView";
    }

    @GetMapping(path = "/code/latest")
    public String getLatestHtmlCode(Model model) {
        if(codeService.getCount() > 10) {
            model.addAttribute("codes", codeService.getLatestTen());
        } else {
            model.addAttribute("codes", codeService.getAll());
        }

        model.addAttribute("title", "Latest");
        return "codeView";
    }

    @GetMapping(path = "/api/code/latest", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Code> getLatestApiCode() {
        if(codeService.getCount() > 10){
            return codeService.getLatestTen();
        }else{
            return codeService.getAll();
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
