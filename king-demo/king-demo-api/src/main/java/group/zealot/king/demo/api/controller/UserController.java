package group.zealot.king.demo.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
