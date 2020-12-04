package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TBUser;
import com.qmth.wuda.teaching.dao.TBUserMapper;
import com.qmth.wuda.teaching.service.TBUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBUserServiceImpl extends ServiceImpl<TBUserMapper, TBUser> implements TBUserService {

}
