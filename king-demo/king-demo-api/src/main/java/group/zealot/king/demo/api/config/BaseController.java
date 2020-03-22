package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

public abstract class BaseController<E extends BaseEntity, P extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public BaseService<E, P> baseService;

    @RequestMapping("list")
    protected JSONObject list(@ZTValid(NotNull = true) Integer page, @ZTValid(NotNull = true) Integer limit, E e) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                PageRequest<E> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                pageRequest.setFilters(e);

                Page<E> page = baseService.pageQuery(pageRequest);
                baseService.formater(page.getList());

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", page.toJSONArray());
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("get")
    protected JSONObject get(@ZTValid(NotNull = true) P id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                E vo = baseService.getById(id);
                baseService.formater(vo);
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("del")
    protected JSONObject del(@ZTValid(NotNull = true) P id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                baseService.deleteById(id);
            }
        }.result();
    }
}
