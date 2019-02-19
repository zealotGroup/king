package group.zealot.king.demo.api;


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
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e) {
        ResultJson resultJson = ResultJsonFactory.create();
        if (e instanceof NoHandlerFoundException) {
            logger.error("NoHandlerFoundException " + e.getMessage());
            resultJson.set(ServiceCode.NOT_FOUND);
            return resultJson.toJSONString();
        } else {
            logger.error("Exception", e);
            resultJson.set(ServiceCode.EXCEPTION);
            return resultJson.toJSONString();
        }
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     */
    @ResponseBody
    @ExceptionHandler(value = BaseRuntimeException.class)
    public String baseRuntimeExceptionHandler(RuntimeException e) {
        ResultJson resultJson = ResultJsonFactory.create();
        if (e instanceof BaseRuntimeException) {
            logger.error("BaseRuntimeException" + e.getMessage());
            resultJson.setCode(ServiceCode.EXCEPTION.code());
            resultJson.setMsg(e.getMessage());
            return resultJson.toJSONString();
        } else {
            logger.error("RuntimeException", e);
            resultJson.set(ServiceCode.EXCEPTION_RUNNTIME);
            return resultJson.toJSONString();
        }
    }
}