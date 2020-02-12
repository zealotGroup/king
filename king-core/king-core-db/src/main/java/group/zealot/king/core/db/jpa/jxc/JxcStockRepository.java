package group.zealot.king.core.db.jpa.jxc;

import group.zealot.king.core.zt.entity.jxc.JxcStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JxcStockRepository extends JpaRepository<JxcStock, Long> {
}
