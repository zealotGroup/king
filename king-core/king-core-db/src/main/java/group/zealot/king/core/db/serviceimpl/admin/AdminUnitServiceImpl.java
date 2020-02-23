package group.zealot.king.core.db.serviceimpl.admin;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.admin.AdminUnitService;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class AdminUnitServiceImpl extends BaseServiceImpl<AdminUnit, Long> implements AdminUnitService {

    @Override
    protected org.springframework.data.domain.Page<AdminUnit> pageQuery(AdminUnit e, org.springframework.data.domain.PageRequest pageable) {
        ExampleMatcher likeMatcher = addLike(getMatcher(), "name");
        return jpaRepository.findAll(Example.of(e, likeMatcher), pageable);
    }
}
