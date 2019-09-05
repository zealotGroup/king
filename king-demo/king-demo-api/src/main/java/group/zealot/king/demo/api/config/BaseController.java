package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.zt.mif.entity.BaseEntity;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.core.zt.mif.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

public abstract class BaseController<E extends BaseEntity, P extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    abstract protected BaseService<E,P> getBaseService();

    @RequestMapping("list")
    protected JSONObject list(Integer page, Integer limit, E e) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(page, "page为空");
                Funcation.AssertNotNull(limit, "limit为空");

                PageRequest<E> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                pageRequest.setFilters(e);

                Page<E> page = getBaseService().pageQuery(pageRequest);
                getBaseService().formater(page.getList());

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", page.toJSONArray());
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("get")
    protected JSONObject get(P id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");

                E vo = getBaseService().getById(id);
                getBaseService().formater(vo);
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("del")
    protected JSONObject del(P id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                E old = getBaseService().getById(id);
                Funcation.AssertNotNull(old, "该ID数据不存在");

                getBaseService().del(id, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("recover")
    protected JSONObject recover(P id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                E old = getBaseService().getById(id);
                Funcation.AssertNotNull(old, "该ID数据不存在");
                if (NumberUtil.equals(old.getIsDelete(), Constants.DELETE_N)) {
                    throw new BaseRuntimeException("此ID数据未删除，不能恢复");
                }
                getBaseService().recover(id, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("realDel")
    protected JSONObject realDel(P id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                E old = getBaseService().getById(id);
                Funcation.AssertNotNull(old, "该ID数据不存在");
                getBaseService().realDel(id);
            }
        }.result();
    }
}
