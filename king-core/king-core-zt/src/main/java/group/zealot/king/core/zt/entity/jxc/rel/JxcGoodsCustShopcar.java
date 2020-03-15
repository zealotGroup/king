package group.zealot.king.core.zt.entity.jxc.rel;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 进销存--微信购物车
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"goodsId", "custId"})})
public class JxcGoodsCustShopcar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;
    @Column(length = 20)
    private Long custId;
    @Column(length = 20)
    private BigDecimal size;
}
