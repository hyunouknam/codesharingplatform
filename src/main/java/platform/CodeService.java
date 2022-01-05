package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.util.Util;

import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private Util util;

    public String addCode(Code code) {
        // Make sure code with the same id does not exist
        String codeId = "";
        do{
            codeId = util.getUUID();
            if(!codeRepository.findById(codeId).isPresent()) {
                code.setId(codeId);
                break;
            }
        }while(true);

        // Update code's upload date
        code.setDate(util.getCurrentDate());

        // Check if code is private through count of views and/or time left
        if(code.getViews() > 0) {
            code.setViewRestriction(true);
        } else {
            code.setViewRestriction(false);
        }

        if(code.getTime() > 0) {
            code.setTimeRestriction(true);
        } else {
            code.setTimeRestriction(false);
        }

        codeRepository.save(code);

        return codeId;
    }

    public void updateCode(Code code) {
        codeRepository.save(code);
    }

    public void deleteCode(String id) {
        codeRepository.deleteById(id);
    }

    public Optional<Code> getCode(String id) {
        return codeRepository.findById(id);
    }

    public long getCount() {
        return codeRepository.count();
    }

    public List<Code> getAll() {
        return codeRepository.findTop10ByViewRestrictionFalseAndTimeRestrictionFalseOrderByDateDesc();
    }

    public List<Code> getLatestTen() {
        return codeRepository.findTop10ByViewRestrictionFalseAndTimeRestrictionFalseOrderByDateDesc();
    }

}
