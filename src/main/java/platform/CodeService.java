package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public void addCode(Code code) {
        codeRepository.save(code);
    }

    public Optional<Code> getCode(Integer id) {
        return codeRepository.findById(id);
    }

    public long getCount() {
        return codeRepository.count();
    }

    public List<Code> getAll() {
        return codeRepository.findAllByOrderByIdDesc();
    }

    public List<Code> getLatestTen() {
        return codeRepository.findTop10ByOrderByIdDesc();
    }
}
