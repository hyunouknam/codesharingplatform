package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public void addCode(Code code) {
        UUID uuid = UUID.randomUUID();
        code.setId(uuid.toString());
        codeRepository.save(code);
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
