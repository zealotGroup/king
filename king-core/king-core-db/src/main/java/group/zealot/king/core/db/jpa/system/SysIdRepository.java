package group.zealot.king.core.db.jpa.system;

import group.zealot.king.core.zt.entity.system.SysId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zealot
 * @date 2019/11/23 11:52
 */
public interface SysIdRepository extends JpaRepository<SysId, Long> {
}
