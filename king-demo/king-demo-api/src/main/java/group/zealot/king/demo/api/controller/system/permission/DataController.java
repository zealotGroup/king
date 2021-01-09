package group.zealot.king.demo.api.controller.system.permission;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.system.SysData;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.sysDataService;


@RestController
@RequestMapping("/system/permission/data")
public class DataController extends BaseController<SysData, Long> {

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotBlank = true) String name) {
        return new ResultTemple() {
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
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                SysData vo = sysDataService.getById(id);
                vo.setName(name);
                sysDataService.update(vo);
            }
        }.result();
    }
}
