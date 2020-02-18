package group.zealot.king.core.db.serviceimpl.admin;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.admin.AdminPictureService;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class AdminPictureServiceImpl extends BaseServiceImpl<AdminPicture, Long> implements AdminPictureService {

    @Override
    protected org.springframework.data.domain.Page<AdminPicture> pageQuery(AdminPicture e, org.springframework.data.domain.PageRequest pageable) {
        ExampleMatcher likeMatcher = addLike(getMatcher(), "name");
        return jpaRepository.findAll(Example.of(e, likeMatcher), pageable);
    }
}
