package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcSupplier;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcSupplierService;


@RestController
@RequestMapping("/jxc/supplier")
public class SupplierController extends BaseController<JxcSupplier, Long> {

    @RequestMapping("add")
    public JSONObject add(String name, String phoneNumber, String address) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name 为空");
            }

            @Override
            protected void dosomething() {
                JxcSupplier vo = new JxcSupplier();
                vo.setName(name);
                vo.setPhoneNumber(phoneNumber);
                vo.setAddress(address);
                vo = jxcSupplierService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, String phoneNumber, String address) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id 为空");
                Funcation.AssertNotNull(name, "name 为空");
            }

            @Override
            protected void dosomething() {
                JxcSupplier vo = jxcSupplierService.getById(id);
                vo.setName(name);
                vo.setPhoneNumber(phoneNumber);
                vo.setAddress(address);
                jxcSupplierService.update(vo);
            }
        }.result();
    }
}
