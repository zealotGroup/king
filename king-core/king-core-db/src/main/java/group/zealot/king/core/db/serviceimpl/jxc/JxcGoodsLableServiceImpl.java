package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsLableService;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import org.springframework.stereotype.Service;

import java.util.List;

import static group.zealot.king.core.db.mybatis.Mappers.jxcGoodsLableMapper;

@Service
public class JxcGoodsLableServiceImpl extends BaseServiceImpl<JxcGoodsLable, Long> implements JxcGoodsLableService {
    @Override
    public List<AdminLable> getLableList() {
        return jxcGoodsLableMapper.getLableList();
    }
}
