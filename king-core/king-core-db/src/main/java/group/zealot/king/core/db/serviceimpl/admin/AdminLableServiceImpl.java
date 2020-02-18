package group.zealot.king.core.db.serviceimpl.admin;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.admin.AdminLableService;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class AdminLableServiceImpl extends BaseServiceImpl<AdminLable, Long> implements AdminLableService {

    @Override
    protected org.springframework.data.domain.Page<AdminLable> pageQuery(AdminLable e, org.springframework.data.domain.PageRequest pageable) {
        ExampleMatcher likeMatcher = addLike(getMatcher(), "name");
        return jpaRepository.findAll(Example.of(e, likeMatcher), pageable);
    }
}
