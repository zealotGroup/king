package group.zealot.king.core.db.serviceimpl.admin;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.admin.AdminLableService;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class AdminLableServiceImpl extends BaseServiceImpl<AdminLable, Long> implements AdminLableService {


    @Override
    protected ExampleMatcher getMatcher() {
        return addLike(super.getMatcher(), "name");
    }
}
