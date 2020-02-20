package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcSalesService;
import group.zealot.king.core.zt.entity.jxc.JxcSales;
import org.springframework.stereotype.Service;

@Service
public class JxcSalesServiceImpl extends BaseServiceImpl<JxcSales, Long> implements JxcSalesService {
    @Override
    public Page<JxcSales> pageQuery(PageRequest<JxcSales> pageRequest) {
        return baseMapper.pageQuery(pageRequest);
    }
}
