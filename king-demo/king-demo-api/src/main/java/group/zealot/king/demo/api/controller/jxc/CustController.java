package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcCustService;


@RestController
@RequestMapping("/jxc/cust")
public class CustController extends BaseController<JxcCust, Long> {

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotBlank = true) String name, String phoneNumber, String address) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                JxcCust vo = new JxcCust();
                vo.setName(name);
                vo.setPhoneNumber(phoneNumber);
                vo.setAddress(address);
                vo = jxcCustService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name, String phoneNumber, String address) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                JxcCust vo = jxcCustService.getById(id);
                vo.setName(name);
                vo.setPhoneNumber(phoneNumber);
                vo.setAddress(address);
                jxcCustService.update(vo);
            }
        }.result();
    }
}
