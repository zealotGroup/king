package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 进销存--采购记录
 */
@Getter
@Setter
@Entity
@Table
public class JxcPurchase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;//商品ID
    @Column(length = 20)
    private Long supplierId;//供应商ID
    @Column(length = 20)
    private Long orderId;//订单ID
    @Column(length = 20)
    private BigDecimal price;//总价格
    @Column(length = 20)
    private BigDecimal size;//数量

    @Transient
    private String supplierName;
    @Transient
    private String goodsName;
    @Transient
    private BigDecimal goodsPrice;
    @Transient
    private String goodsPriceUnitName;
    @Transient
    private String goodsSizeUnitName;

    @Transient
    private String startTime;
    @Transient
    private String endTime;

    public void setStartTime(String startTime) {
        if (StringUtil.isNotEmpty(startTime)) {
            this.startTime = startTime.replace("T", " ");
        }

    }

    public void setEndTime(String endTime) {
        if (StringUtil.isNotEmpty(endTime)) {
            this.endTime = endTime.replace("T", " ");
        }
    }
}
