package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysData;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.*;


@RestController
@RequestMapping("/data")
public class DataController extends BaseController<SysData, Long> {

    @Override
    protected BaseService<SysData, Long> getBaseService() {
        return sysDataService;
    }

    @RequestMapping("add")
    public JSONObject add(String name) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name为空");
            }

            @Override
            protected void dosomething() {
                SysData vo = new SysData();
                vo.setName(name);
                vo = sysDataService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name为空");
            }

            @Override
            protected void dosomething() {
                SysData vo = sysDataService.getById(id);
                vo.setName(name);
                sysDataService.update(vo);
            }
        }.result();
    }
}