package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.model.Code;
import platform.persistence.CodeRepository;
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
        Optional<Code> code = codeRepository.findById(id);

        // Logic happens if present, if not skips to return
        if(code.isPresent()){
            Code currentCode = code.get();

            // Check if current code has views restriction
            if(currentCode.isViewRestriction()) {

                // Decrement views by 1
                currentCode.setViews(currentCode.getViews() - 1);

                if(currentCode.getViews() < 0) {
                    deleteCode(currentCode.getId());
                    currentCode = null;
                } else {
                    updateCode(currentCode);
                }
            }

            // Time restriction HAS to go after views as it will update more than once due to duplicating update
            // if time restriction ran before views restriction
            if(currentCode.isTimeRestriction()) {
                // timeLeft is how much time there is left until code disappears
                int timeLeft = util.getTimeDifference(currentCode.getTime(), currentCode.getDate());

                //currentCode.setTime(timeLeft);

                if(timeLeft <= 0) {
                    deleteCode(currentCode.getId());
                    currentCode = null;
                } else {
                    updateCode(currentCode);
                    // Set time to current time left
                    currentCode.setTime(timeLeft);
                }
            }

            code = Optional.of(currentCode);
        }

        return code;
    }

    public long getCount() {
        return codeRepository.count();
    }

    public List<Code> getLatestTen() {
        return codeRepository.findTop10ByViewRestrictionFalseAndTimeRestrictionFalseOrderByDateDesc();
    }

}
