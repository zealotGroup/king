package group.zealot.king.demo.api.controller.admin;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.adminPictureService;


@RestController
@RequestMapping("/admin/picture")
public class PictureController extends BaseController<AdminPicture, Long> {

    @RequestMapping("add")
    public JSONObject add(String name, Long phoneNumber, String address) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name 为空");
            }

            @Override
            protected void dosomething() {
                AdminPicture vo = new AdminPicture();
                vo.setName(name);
                vo = adminPictureService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, Long phoneNumber, String address) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id 为空");
                Funcation.AssertNotNull(name, "name 为空");
            }

            @Override
            protected void dosomething() {
                AdminPicture vo = adminPictureService.getById(id);
                vo.setName(name);
                adminPictureService.update(vo);
            }
        }.result();
    }
}
