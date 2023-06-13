package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glucacxe.usercenter.model.domain.User;
import generator.service.UserService;
import generator.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author DaVinci
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-06-12 22:48:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




