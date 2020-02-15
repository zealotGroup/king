package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import group.zealot.king.core.zt.entity.jxc.enums.PurchaseSalesTypeEnum;
import group.zealot.king.core.zt.entity.jxc.enums.UnitTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--单位换算
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class JxcUnit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 20)
    private Long vsId;//比对单位ID
    @Column(length = 20)
    private Integer size;//本单位 值
    @Column(length = 20)
    private Integer vsSize;//比对单位 值
    @Column(length = 2)
    private UnitTypeEnum type;//类型（数量、价格、重量）

}
