package platform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import platform.Code;
import platform.CodeService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeViewController {

    @Autowired
    private CodeService codeService;

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

    @GetMapping(path = "/code/new", produces = "text/html")
    public String getNewCode(Model model) {
        model.addAttribute("title", "Create");
        return "codeSubmitView";
    }
}
