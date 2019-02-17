package group.zealot.king.demo.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping
    public JSONObject index() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("123", "123");
        return jsonObject;
    }
}
