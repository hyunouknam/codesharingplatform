package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        int views = code.getViews() > 0 ? code.getViews() : 0;
        int time = code.getTime() > 0 ? code.getTime() : 0;

        Code newCode = new Code(code.getCode(), title, LocalDateTime.now(), views, time);

        if(views > 0) {
            newCode.setViewRestriction(true);
        } else {
            newCode.setViewRestriction(false);
        }

        if(time > 0) {
            newCode.setTimeRestriction(true);
        } else {
            newCode.setTimeRestriction(false);
        }

        codeService.addCode(newCode);
        Map<String,String> idMap = new HashMap<>();
        idMap.put("id", newCode.getId());
        return idMap;
    }

    @GetMapping(path = "/api/code/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Code getApiCode(@PathVariable String id) {
        Code currentCode;

        if(codeService.getCode(id).isPresent()) {
            currentCode = codeService.getCode(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

        if(currentCode.isTimeRestriction() || currentCode.isViewRestriction()) {
            int timeBetween = (int) (Duration.between(currentCode.getDate(), LocalDateTime.now()).getSeconds());
            int originalTime = currentCode.getTime();
            int timeLeft = originalTime - timeBetween;

            currentCode.setViews(currentCode.getViews() - 1);

            if((originalTime > 0 && timeLeft <= 0) || (currentCode.isViewRestriction() && currentCode.getViews() < 0)) {
                codeService.deleteCode(currentCode.getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
            } else {
                if(currentCode.getViews() <= 0) {
                    currentCode.setViews(0);
                }
                codeService.updateCode(currentCode);
                if(originalTime > 0) {
                    currentCode.setTime(timeLeft);
                }
            }
        }

        return currentCode;
    }

    @GetMapping(path = "/code/{id}")
    public String getHtmlCode(@PathVariable String id, Model model) {
        List<Code> currentList = new ArrayList<>();
        if(codeService.getCode(id).isPresent()){
            currentList.add(codeService.getCode(id).get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

        if(currentList.get(0).isTimeRestriction() || currentList.get(0).isViewRestriction()) {
            int timeBetween = (int) (Duration.between(currentList.get(0).getDate(), LocalDateTime.now()).getSeconds());
            int timeLeft = currentList.get(0).getTime() - timeBetween;

            currentList.get(0).setViews(currentList.get(0).getViews() - 1);

            if((currentList.get(0).getTime() > 0 && timeLeft <= 0) || (currentList.get(0).isViewRestriction() && currentList.get(0).getViews() < 0)) {
                codeService.deleteCode(currentList.get(0).getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
            } else {
                if(currentList.get(0).getViews() <= 0) {
                    currentList.get(0).setViews(0);
                }
                model.addAttribute("time", timeLeft);
                codeService.updateCode(currentList.get(0));
            }
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
