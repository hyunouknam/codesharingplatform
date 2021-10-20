package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CodeController {

    private final String title = "Code";

    @Autowired
    private CodeService codeService;

    public CodeController() {
    }

    @PostMapping(path = "/api/code/new")
    @ResponseBody
    public Map<String,String> postCode(@RequestBody Code code){
        Code newCode = new Code(code.getCode(), title, LocalDateTime.now(), code.getViews(), code.getTime());
        if(code.getViews() > 0 || code.getTime() > 0) {
            newCode.setSecret(true);
        } else {
            newCode.setSecret(false);
        }
        codeService.addCode(newCode);
        Map<String,String> idMap = new HashMap<>();
        idMap.put("id", newCode.getId());
        return idMap;
    }

    @GetMapping(path = "/api/code/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Code getApiCode(@PathVariable String id) {
        return codeService.getCode(id).get();
    }

    @GetMapping(path = "/code/{id}")
    public String getHtmlCode(@PathVariable String id, Model model) {
        List<Code> currentList = new ArrayList<>();
        Optional.ofNullable(codeService.getCode(id)).ifPresent(x -> currentList.add(x.get()));

        if(currentList.get(0).isSecret()) {
            int timeBetween = (int) (Duration.between(currentList.get(0).getDate(), LocalDateTime.now()).getSeconds());
            int timeLeft = currentList.get(0).getTime() - timeBetween;

            currentList.get(0).setViews(currentList.get(0).getViews() - 1);

            if(timeLeft <= 0 || currentList.get(0).getViews() == 0) {
                codeService.deleteCode(currentList.get(0).getId());
            } else {
                codeService.updateCode(currentList.get(0));
            }

            model.addAttribute("time", timeLeft);
        }

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
    public String getNewCode(Model model) {
        model.addAttribute("title", "Create");
        return "codeSubmitView";
    }

}
