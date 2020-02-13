package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcLable;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcLableService;


@RestController
@RequestMapping("/jxc/lable")
public class LableController extends BaseController<JxcLable, Long> {

    @RequestMapping("add")
    public JSONObject add(String name) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name为空");
            }

            @Override
            protected void dosomething() {
                JxcLable vo = new JxcLable();
                vo.setName(name);
                vo = jxcLableService.insert(vo);

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
                JxcLable vo = jxcLableService.getById(id);
                vo.setName(name);
                jxcLableService.update(vo);
            }
        }.result();
    }
}
