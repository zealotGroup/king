package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcCustService;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class JxcCustServiceImpl extends BaseServiceImpl<JxcCust, Long> implements JxcCustService {

    @Override
    protected org.springframework.data.domain.Page<JxcCust> pageQuery(JxcCust e, org.springframework.data.domain.PageRequest pageable) {
        ExampleMatcher likeMatcher = addLike(getMatcher(), "name", "phoneNumber", "address");
        return jpaRepository.findAll(Example.of(e, likeMatcher), pageable);
    }
}
