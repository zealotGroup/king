package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.dbif.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

public abstract class BaseController<E, P extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    abstract protected BaseService<E, P> getBaseService();

    protected Long getLoginUserId() {
        return LoginUtil.getSysUserId();
    }

    @RequestMapping("list")
    protected JSONObject list(Integer page, Integer limit, E e) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(page, "page为空");
                Funcation.AssertNotNull(limit, "limit为空");
            }

            @Override
            protected void dosomething() {
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
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
            }

            @Override
            protected void dosomething() {
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
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
            }

            @Override
            protected void dosomething() {
                getBaseService().deleteById(id);
            }
        }.result();
    }
}
