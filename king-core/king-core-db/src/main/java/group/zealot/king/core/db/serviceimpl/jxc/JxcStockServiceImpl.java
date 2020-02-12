package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcStockService;
import group.zealot.king.core.zt.entity.jxc.JxcStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.jxcStockRepository;
import static group.zealot.king.core.db.mybatis.Mappers.jxcStockMapper;

@Service
public class JxcStockServiceImpl extends BaseServiceImpl<JxcStock, Long> implements JxcStockService {

    @Override
    protected BaseMapper<JxcStock, Long> getBaseMapper() {
        return jxcStockMapper;
    }

    @Override
    protected JpaRepository<JxcStock, Long> getJpaRepository() {
        return jxcStockRepository;
    }
}
