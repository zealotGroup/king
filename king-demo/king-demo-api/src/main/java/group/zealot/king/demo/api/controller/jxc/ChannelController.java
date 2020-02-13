package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcChannel;
import group.zealot.king.core.zt.entity.jxc.PurchaseSalesTypeEnum;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcChannelService;


@RestController
@RequestMapping("/jxc/channel")
public class ChannelController extends BaseController<JxcChannel, Long> {

    @RequestMapping("add")
    public JSONObject add(String name, PurchaseSalesTypeEnum type, String remark) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name 为空");
                Funcation.AssertNotNull(type, "type 为空");
            }

            @Override
            protected void dosomething() {
                JxcChannel vo = new JxcChannel();
                vo.setName(name);
                vo.setType(type);
                vo.setRemark(remark);
                vo = jxcChannelService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String remark) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
            }

            @Override
            protected void dosomething() {
                JxcChannel vo = jxcChannelService.getById(id);
                vo.setRemark(remark);
                jxcChannelService.update(vo);
            }
        }.result();
    }
}
