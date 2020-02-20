package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcSupplierService;
import group.zealot.king.core.zt.entity.jxc.JxcSupplier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class JxcSupplierServiceImpl extends BaseServiceImpl<JxcSupplier, Long> implements JxcSupplierService {

    @Override
    protected org.springframework.data.domain.Page<JxcSupplier> pageQuery(JxcSupplier e, org.springframework.data.domain.PageRequest pageable) {
        ExampleMatcher likeMatcher = addLike(getMatcher(), "name", "phoneNumber", "address");
        return jpaRepository.findAll(Example.of(e, likeMatcher), pageable);
    }

}
