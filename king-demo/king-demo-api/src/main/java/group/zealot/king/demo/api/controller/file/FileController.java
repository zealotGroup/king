package group.zealot.king.demo.api.controller.file;

import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static group.zealot.king.core.zt.dbif.Services.adminPictureService;

@RestController
@RequestMapping("/file")
public class FileController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "picture")
    public void getPicture(@ZTValid(NotNull = true) Long id, @ZTValid(NotNull = true) String name, HttpServletResponse response) {
        new ResultTemple(response) {
            @Override
            protected void dosomething() {
                AdminPicture vo = new AdminPicture();
                vo.setId(id);
                vo.setName(name);
                vo = adminPictureService.get(vo);
                if (vo != null) {
                    response.setContentType("image/jpeg");
                    try {
                        OutputStream out = response.getOutputStream();
                        out.write(vo.getBytes());
                        out.flush();
                        //关闭响应输出流
                        out.close();
                    } catch (IOException e) {
                        throw new BaseRuntimeException(e);
                    }
                } else {
                    throw new BaseRuntimeException(ServiceCode.FILE_ERROR);
                }
            }
        }.result();
    }
}
