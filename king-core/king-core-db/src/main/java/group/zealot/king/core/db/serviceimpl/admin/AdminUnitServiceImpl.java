package group.zealot.king.core.db.serviceimpl.admin;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.admin.AdminUnitService;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class AdminUnitServiceImpl extends BaseServiceImpl<AdminUnit, Long> implements AdminUnitService {

    @Override
    protected ExampleMatcher getMatcher() {
        return addLike(super.getMatcher(), "name");
    }
}
