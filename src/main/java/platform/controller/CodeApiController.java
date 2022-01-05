package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.Code;
import platform.CodeService;

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
        String id = codeService.addCode(code);
        Map<String,String> idMap = new HashMap<>();
        idMap.put("id", id);
        return idMap;
    }

    @GetMapping(path = "/code/{id}", produces = "application/json;charset=UTF-8")
    public Code getApiCode(@PathVariable String id) {
        Optional<Code> currentCode = codeService.getCode(id);

        // Checks if code is exists with id, if not throw exception
        if(!currentCode.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

        return currentCode.get();
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
