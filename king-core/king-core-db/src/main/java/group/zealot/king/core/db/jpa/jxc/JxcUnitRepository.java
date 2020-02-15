package group.zealot.king.core.db.jpa.jxc;

import group.zealot.king.core.zt.entity.jxc.JxcSales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JxcUnitRepository extends JpaRepository<JxcSales, Long> {
}
