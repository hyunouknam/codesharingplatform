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

    private List<Code> codeList = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(1);

    @Autowired
    private CodeService codeService;

    public CodeController() {
    }

    @PostMapping(path = "/api/code/new")
    @ResponseBody
    public Map<String,String> postCode(@RequestBody Code code){
        codeService.addCode(new Code(code.getCode(), title, LocalDateTime.now().format(formatter), counter.get()));
        Map<String,String> idMap = new HashMap<>();
        idMap.put("id", String.valueOf(counter.get()));
        counter.getAndIncrement();
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
        if(codeList.size() > 10){
            model.addAttribute("codes", reverseList(codeList.subList((codeList.size()-10), codeList.size())));
        }else{
            model.addAttribute("codes", reverseList(codeList));
        }
        model.addAttribute("title", "Latest");
        return "codeView";
    }

    @GetMapping(path = "/api/code/latest", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Code> getLatestApiCode() {
        if(codeList.size() > 10){
            return reverseList(codeList.subList((codeList.size()-10), codeList.size()));
        }else{
            return reverseList(codeList);
        }
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public ResponseEntity<String> getNewCode() {
        return ResponseEntity.ok()
                .body("<title>" + "Create" + "</title>"
                        + "<textarea id=\"code_snippet\">" + "..." + "</textarea>"
                        + "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">" + "Submit" + "</button>");
    }

    public List<Code> reverseList(List<Code> list){
        List<Code> revList = new ArrayList<>();
        for(int i = list.size()-1;i >= 0;i--){
            revList.add(list.get(i));
        }
        return revList;
    }
}
