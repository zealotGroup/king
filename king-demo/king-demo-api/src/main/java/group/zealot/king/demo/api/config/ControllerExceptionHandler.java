package group.zealot.king.demo.api.config;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JSONObject exceptionHandler(Exception e) {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                if (e instanceof NoHandlerFoundException) {
                    logger.error("NoHandlerFoundException " + e.getMessage());
                    resultJson.set(ServiceCode.NOT_FOUND);
                } else {
                    logger.error("Exception", e);
                    resultJson.set(ServiceCode.EXCEPTION);
                }
            }
        }.result();
    }

    /**
     * 拦截捕捉运行时异常
     */
    @ResponseBody
    @ExceptionHandler(value = BaseRuntimeException.class)
    public JSONObject baseRuntimeExceptionHandler(RuntimeException e) {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                if (e instanceof BaseRuntimeException) {
                    logger.error("BaseRuntimeException " + e.getMessage());
                    resultJson.setCode(ServiceCode.EXCEPTION_RUNNTIME.code());
                    resultJson.setMsg(e.getMessage());
                } else {
                    logger.error("RuntimeException", e);
                    resultJson.set(ServiceCode.EXCEPTION_RUNNTIME);
                }
            }
        }.result();
    }
}
