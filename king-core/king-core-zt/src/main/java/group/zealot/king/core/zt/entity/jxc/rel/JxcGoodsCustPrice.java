package group.zealot.king.core.zt.entity.jxc.rel;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--商品客户价格
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"goodsId", "custId"})})
public class JxcGoodsCustPrice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;
    @Column(length = 20)
    private Long custId;
    @Column(nullable = false, length = 3)
    private Integer discountPercent;//百分制折扣 100无折扣
    @Column(length = 10)
    private Integer discountPrice;//优惠价格
    @Column(length = 10)
    private Integer salePrice;//绝对价格
    @Column(length = 20)
    private Long unitId;//单位ID（价格单位）

}
