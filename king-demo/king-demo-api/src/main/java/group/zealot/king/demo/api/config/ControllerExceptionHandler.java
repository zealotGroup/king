package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 控制器错误处理器，从控制器抛出的异常被它拦截。
 * 可以在此处封装错误信息，以友好的方式返回给前端
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JSONObject exceptionHandler(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            logger.error("NoHandlerFoundException " + e.getMessage());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpStatus.NOT_FOUND.value());
            jsonObject.put("msg", "not found service , check url");
            return jsonObject;
        } else {
            logger.error("Exception", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonObject.put("msg", e.getMessage());
            return jsonObject;
        }
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     */
    @ResponseBody
    @ExceptionHandler(value = BaseRuntimeException.class)
    public JSONObject baseRuntimeExceptionHandler(RuntimeException e) {
        if (e instanceof BaseRuntimeException) {
            logger.error("BaseRuntimeException" + e.getMessage());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpStatus.SERVICE_UNAVAILABLE.value());
            jsonObject.put("msg", e.getMessage());
            return jsonObject;
        } else {
            logger.error("RuntimeException", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpStatus.SERVICE_UNAVAILABLE.value());
            jsonObject.put("msg", "RuntimeException , call system admin");
            return jsonObject;
        }
    }
}
