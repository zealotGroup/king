package group.zealot.king.demo.api.controller.admin;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.adminLableService;


@RestController
@RequestMapping("/admin/lable")
public class LableController extends BaseController<AdminLable, Long> {

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotBlank = true) String name) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                AdminLable vo = new AdminLable();
                vo.setName(name);
                vo = adminLableService.insert(vo);

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
                AdminLable vo = adminLableService.getById(id);
                vo.setName(name);
                adminLableService.update(vo);
            }
        }.result();
    }
}
