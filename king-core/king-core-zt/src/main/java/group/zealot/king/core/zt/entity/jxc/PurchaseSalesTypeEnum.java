package group.zealot.king.core.zt.entity.jxc;

import lombok.Getter;

/**
 * @author zealot
 * @date 2020/2/12 12:28
 */
public enum PurchaseSalesTypeEnum {
    SALE("销货"), PURCHASE("进货");
    @Getter
    private String name;

    PurchaseSalesTypeEnum(String name) {
        this.name = name;
    }
}
