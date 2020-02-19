package group.zealot.king.core.zt.dbif.service.jxc;

import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;

import java.util.List;

public interface JxcGoodsLableService extends BaseService<JxcGoodsLable, Long> {
    List<AdminLable> getLableList();
}
