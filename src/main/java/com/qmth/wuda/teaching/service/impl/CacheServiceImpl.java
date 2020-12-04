package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.entity.TBUser;
import com.qmth.wuda.teaching.entity.TEStudent;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.CacheService;
import com.qmth.wuda.teaching.service.TBUserService;
import com.qmth.wuda.teaching.service.TEStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: ehcache 服务实现类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/27
 */
@Service
public class CacheServiceImpl implements CacheService {
    private final static Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

//    /**
//     * 添加用户缓存
//     *
//     * @param userId
//     * @return
//     */
//    @Override
//    @Cacheable(value = "user:oauth:cache", key = "#p0")
//    public AuthDto addAccountCache(Long userId) {
//        AuthDto authDto = null;
//        try {
//            TBUser user = tbUserService.getById(userId);
//            if (Objects.isNull(user)) {
//                throw new BusinessException(ExceptionResultEnum.USER_NO);
//            }
//            //查询用户角色和权限
//            QueryWrapper<TBUserRole> uWrapper = new QueryWrapper<>();
//            uWrapper.lambda().eq(TBUserRole::getUserId, user.getId());
//            List<TBUserRole> tbUserRoleList = tbUserRoleService.list(uWrapper);
//            if (Objects.nonNull(tbUserRoleList)) {
//                TBOrg tbOrg = Objects.isNull(redisUtil.getOrg(user.getOrgId())) ? tbOrgService.getById(user.getOrgId()) : (TBOrg) redisUtil.getOrg(user.getOrgId());
//                //根据角色名查权限
//                Set<String> roleCodes = tbUserRoleList.stream().map(s -> s.getRoleCode()).collect(Collectors.toSet());
//                QueryWrapper<TBRolePrivilege> rpWrapper = new QueryWrapper<>();
//                rpWrapper.lambda().in(TBRolePrivilege::getRoleCode, roleCodes);
//                List<TBRolePrivilege> tbRolePrivilegeList = tbRolePrivilegeService.list(rpWrapper);
//                Set<Long> privilegeIds = tbRolePrivilegeList.stream().map(s -> s.getPrivilegeId()).collect(Collectors.toSet());
//                QueryWrapper<TBPrivilege> pWrapper = new QueryWrapper<>();
//                pWrapper.lambda().in(TBPrivilege::getId, privilegeIds).eq(TBPrivilege::getType, SystemConstant.LINK);
//                List<TBPrivilege> tbPrivilegeList = tbPrivilegeService.list(pWrapper);
//                authDto = new AuthDto(roleCodes, tbPrivilegeList.stream().map(s -> s.getUrl()).collect(Collectors.toSet()), tbOrg);
//            }
//        } catch (Exception e) {
//            log.error("请求出错", e);
//            throw new BusinessException("添加用户缓存失败");
//        }
//        return authDto;
//    }

//    /**
//     * 删除用户缓存
//     *
//     * @param userId
//     */
//    @Override
//    @CacheEvict(value = "user:oauth:cache", key = "#p0")
//    public void removeAccountCache(Long userId) {
//
//    }
}
