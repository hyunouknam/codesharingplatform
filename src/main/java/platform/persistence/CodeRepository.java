package platform.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.model.Code;

import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<Code, String> {
    public List<Code> findAllByOrderByIdDesc();
    public List<Code> findTop10ByOrderByIdDesc();
    public List<Code> findAllByViewRestrictionFalseAndTimeRestrictionFalseOrderByDateDesc();
    public List<Code> findTop10ByViewRestrictionFalseAndTimeRestrictionFalseOrderByDateDesc();
}