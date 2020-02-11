package group.zealot.king.demo.api.config;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (e instanceof NoHandlerFoundException) {
                    logger.error("NoHandlerFoundException " + e.getMessage(), e);
                    resultJson.set(ServiceCode.NOT_FOUND);
                } else {
                    logger.error("Exception", e);
                    resultJson.set(ServiceCode.EXCEPTION);
                }
            }
        }.resultError();
    }

    /**
     * 拦截捕捉运行时异常
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public JSONObject baseRuntimeExceptionHandler(RuntimeException e) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (e instanceof BaseRuntimeException) {
                    logger.error("BaseRuntimeException", e);
                    if (((BaseRuntimeException) e).getServiceCode() != null) {
                        resultJson.set(((BaseRuntimeException) e).getServiceCode());
                        if (e.getMessage() != null) {
                            resultJson.setMsg(e.getMessage());
                        }
                    } else {
                        resultJson.set(ServiceCode.EXCEPTION_RUNNTIME);
                        if (e.getMessage() != null) {
                            resultJson.setMsg(e.getMessage());
                        }
                    }
                } else if (e instanceof MethodArgumentTypeMismatchException) {
                    logger.error("MethodArgumentTypeMismatchException", e);
                    resultJson.setCode(ServiceCode.REQUEST_ERROR.code());
                    resultJson.setMsg("参数类型错误");
                } else if (e instanceof DataIntegrityViolationException) {

                    logger.error("DataIntegrityViolationException", e);
                    resultJson.setCode(ServiceCode.REQUEST_ERROR.code());
                    resultJson.setMsg("数据完整性错误（主键冲突、必填项数据缺失等）");
                } else {
                    logger.error("RuntimeException", e);
                    resultJson.set(ServiceCode.EXCEPTION_RUNNTIME);
                }
            }
        }.resultError();
    }
}
