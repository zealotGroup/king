package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcPurchaseSalesService;
import group.zealot.king.core.zt.entity.jxc.JxcPurchaseSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.jxcPurchaseSalesRepository;
import static group.zealot.king.core.db.mybatis.Mappers.jxcPurchaseSalesMapper;

@Service
public class JxcPurchaseSalesServiceImpl extends BaseServiceImpl<JxcPurchaseSales, Long> implements JxcPurchaseSalesService {
}
