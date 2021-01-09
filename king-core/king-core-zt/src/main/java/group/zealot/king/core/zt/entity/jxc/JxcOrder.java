package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 进销存--订单记录
 */
@Getter
@Setter
@Entity
@Table
public class JxcOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long custId;
    @Column(length = 20)
    private BigDecimal price;//总价格
    @Column(length = 20)
    private Long priceUnitId;//价格单位

    /**
     * 0:已下单，未支付
     * 1:已支付，未发货
     * 2:已发货，未收货
     * 3:已收货，未确认
     * <p> 完结状态
     * 4:已确认（自动）
     * 5:已确认（手动）
     * 原状态+10:异常失败
     */
    @Column(length = 2)
    private Integer state;
}
