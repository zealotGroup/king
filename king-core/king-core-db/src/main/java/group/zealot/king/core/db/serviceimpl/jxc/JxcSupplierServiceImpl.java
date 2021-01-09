package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcSupplierService;
import group.zealot.king.core.zt.entity.jxc.JxcSupplier;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class JxcSupplierServiceImpl extends BaseServiceImpl<JxcSupplier, Long> implements JxcSupplierService {

    @Override
    protected ExampleMatcher getMatcher() {
        return addLike(super.getMatcher(), "name", "phoneNumber", "address");
    }

}
