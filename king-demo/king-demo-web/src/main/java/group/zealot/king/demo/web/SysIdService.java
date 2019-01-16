package group.zealot.king.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysIdService {

    @Autowired
    private SysIdDao sysIdDao;

    public Integer getId() {
        SysId sysId = new SysId();
        sysId.setInsertTime(new Date());
        sysIdDao.insert(sysId);
        return sysId.getId();
    }
}
