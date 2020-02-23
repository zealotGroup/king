package group.zealot.king.core.zt.entity.admin;

import group.zealot.king.core.zt.entity.BaseEntity;
import group.zealot.king.core.zt.entity.admin.enums.UnitTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--单位换算
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "type"})})
public class AdminUnit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 2)
    private UnitTypeEnum type;//类型（计量、计价）
}
