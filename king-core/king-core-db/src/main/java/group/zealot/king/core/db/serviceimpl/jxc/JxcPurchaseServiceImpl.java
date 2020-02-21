package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcPurchaseService;
import group.zealot.king.core.zt.entity.jxc.JxcPurchase;
import org.springframework.stereotype.Service;

@Service
public class JxcPurchaseServiceImpl extends BaseServiceImpl<JxcPurchase, Long> implements JxcPurchaseService {
    @Override
    public Page<JxcPurchase> pageQuery(PageRequest<JxcPurchase> pageRequest) {
        return baseMapper.pageQuery(pageRequest);
    }

}
