package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcCustService;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class JxcCustServiceImpl extends BaseServiceImpl<JxcCust, Long> implements JxcCustService {

    @Override
    protected ExampleMatcher getMatcher() {
        return addLike(super.getMatcher(), "name", "phoneNumber", "address");
    }
}
