package group.zealot.king.core.db.serviceimpl.admin;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.admin.AdminPictureService;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;


@Service
public class AdminPictureServiceImpl extends BaseServiceImpl<AdminPicture, Long> implements AdminPictureService {

    @Override
    protected ExampleMatcher getMatcher() {
        return addLike(super.getMatcher(), "name");
    }

    @Override
    public void formater(AdminPicture adminPicture) {
        adminPicture.setBytes(null);
    }
}
