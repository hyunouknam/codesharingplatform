package platform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import platform.Code;
import platform.CodeService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/api")
public class CodeApiController {

    private final String title = "Code";

    @Autowired
    private CodeService codeService;

    public CodeApiController() {
    }

    @PostMapping(path = "/code/new")
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

    @GetMapping(path = "/code/{id}", produces = "application/json;charset=UTF-8")
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

    @GetMapping(path = "/code/latest", produces = "application/json;charset=UTF-8")
    public List<Code> getLatestApiCode() {
        if(codeService.getCount() > 10){
            return codeService.getLatestTen();
        }else{
            return codeService.getAll();
        }
    }
}
