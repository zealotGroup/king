package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.demo.api.config.ResultFul;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping
    public JSONObject index() {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                resultJson.set(ServiceCode.REQUEST_ERROR);
            }
        }.result();
    }
}
