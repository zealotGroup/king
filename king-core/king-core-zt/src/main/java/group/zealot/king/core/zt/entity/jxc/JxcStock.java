package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 进销存--库存
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"goodsId"})})
public class JxcStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;
    @Column(length = 20)
    private BigDecimal size;//库存数

    /**
     * 非数据库字段
     */
    @Transient
    private String goodsName;
    @Transient
    private String goodsSizeUnitName;
}
