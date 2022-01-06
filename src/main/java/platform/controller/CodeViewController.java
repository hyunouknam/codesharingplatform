package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.model.Code;
import platform.service.CodeService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeViewController {

    @Autowired
    private CodeService codeService;

    @GetMapping(path = "/code/{id}")
    public String getHtmlCode(@PathVariable String id, Model model) {
        List<Code> currentList = new ArrayList<>();

        Code currentCode = codeService.getCode(id).get();
        currentList.add(currentCode);

        model.addAttribute("time", currentCode.getTime());
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
