package group.zealot.king.demo.api.controller.admin;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static group.zealot.king.core.zt.dbif.Services.adminPictureService;


@RestController
@RequestMapping("/admin/picture")
public class PictureController extends BaseController<AdminPicture, Long> {

    @RequestMapping(value = "getPicture")
    public void getPicture(@ZTValid(NotNull = true) Long id, HttpServletResponse response) {
        new ResultTemple(response) {
            @Override
            protected void dosomething() {
                AdminPicture vo = adminPictureService.getById(id);
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
            }
        }.result();
    }

    @RequestMapping(value = "add")
    public JSONObject add(@ZTValid(NotBlank = true) String name, MultipartFile file) {
        return new ResultTemple() {
            byte[] bytes;

            @Override
            protected void verification() {
                try {
                    bytes = file.getBytes();
                } catch (IOException e) {
                    throw new BaseRuntimeException(e);
                }
            }

            @Override
            protected void dosomething() {
                AdminPicture vo = new AdminPicture();
                vo.setName(name);
                vo.setBytes(bytes);
                vo = adminPictureService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name, MultipartFile file) {
        return new ResultTemple() {
            byte[] bytes;

            @Override
            protected void verification() {
                try {
                    bytes = file.getBytes();
                } catch (IOException e) {
                    throw new BaseRuntimeException(e);
                }
            }

            @Override
            protected void dosomething() {
                AdminPicture vo = adminPictureService.getById(id);
                vo.setName(name);
                vo.setBytes(bytes);
                adminPictureService.update(vo);
            }
        }.result();
    }
}
