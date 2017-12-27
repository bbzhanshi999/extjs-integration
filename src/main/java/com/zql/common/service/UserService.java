package com.zql.common.service;


import com.google.common.collect.Lists;
import com.zql.common.dao.RowMappers;
import com.zql.common.dao.SystemDao;
import com.zql.common.entity.*;
import com.zql.common.utils.LamdaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthStatus;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户服务
 * Created by Administrator on 2017/9/8.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private SystemDao systemDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = systemDao.findUserByUsername(username);
        voteGrantAuthroity(user);
        return user;
    }

    /**
     * 通过投票机制获取拥有权限，投票顺序为：用户，岗位，部门，群组
     * @param user
     */
    public void voteGrantAuthroity(User user) {
        List<RoleAuthStatus> groupRoleStatusList = Lists.newArrayList();
        List<RoleAuthStatus> postRoleStatusList = Lists.newArrayList();
        List<RoleAuthStatus> departRoleStatusList = Lists.newArrayList();
        List<Department> departments = systemDao.findDepartmentsByUserId(user.getId());
        List<Group> groups = systemDao.findGroupsByUserId(user.getId());
        List<Post> posts = systemDao.finaPostsByUserId(user.getId());
        user.setDepartments(departments);user.setPosts(posts);user.setGroups(groups);
        List<RoleAuthStatus> userRoleStatusList = systemDao.getJdbcTemplate().getJdbcOperations().query("select b.auth_code," +
                "a.status from sys_user_role a, sys_role b where a.role_id = b.id and a.user_id = ? and b.del_flag!=1", new RowMappers.AuthStatusMapper(), user.getId());
        for (Post post : posts) {
            List<RoleAuthStatus> subPostRoleStatusList = systemDao.getJdbcTemplate().getJdbcOperations().query("select b.auth_code," +
                    "a.status from sys_post_role a, sys_role b where a.role_id = b.id and a.post_id = ? and b.del_flag!=1", new RowMappers.AuthStatusMapper(), post.getId());
            postRoleStatusList.addAll(subPostRoleStatusList);
        }
        for (Department department : departments) {
            List<RoleAuthStatus> subDepartRoleStatusList = systemDao.getJdbcTemplate().getJdbcOperations().query("select b.auth_code," +
                    "a.status from sys_department_role a, sys_role b where a.role_id = b.id and a.depart_id = ? and b.del_flag!=1", new RowMappers.AuthStatusMapper(), department.getId());
            departRoleStatusList.addAll(subDepartRoleStatusList);
        }
        for (Group group : groups) {
            List<RoleAuthStatus> subGroupRoleStatusList = systemDao.getJdbcTemplate().getJdbcOperations().query("select b.auth_code," +
                    "a.status from sys_group_role a, sys_role b where a.role_id = b.id and a.group_id = ? and b.del_flag!=1", new RowMappers.AuthStatusMapper(), group.getId());
            groupRoleStatusList.addAll(subGroupRoleStatusList);
        }

        Map<String, List<RoleAuthStatus>> roleAuthMap = userRoleStatusList.stream().collect(Collectors.groupingBy(RoleAuthStatus::getStatus));
        List<RoleAuthStatus> grantedRoles,refuseRoles;
        if(roleAuthMap.get(RoleAuthStatus.GRANTED)!=null) grantedRoles = roleAuthMap.get(RoleAuthStatus.GRANTED);
        else grantedRoles = Lists.newArrayList();
        if(roleAuthMap.get(RoleAuthStatus.REFUSE)!=null) refuseRoles = roleAuthMap.get(RoleAuthStatus.REFUSE);
        else refuseRoles = Lists.newArrayList();

        postRoleStatusList = extractAuth(postRoleStatusList,grantedRoles,refuseRoles);
        roleAuthMap = postRoleStatusList.stream().collect(Collectors.groupingBy(RoleAuthStatus::getStatus));
        if(roleAuthMap.get(RoleAuthStatus.GRANTED)!=null)grantedRoles.addAll(roleAuthMap.get(RoleAuthStatus.GRANTED));
        if(roleAuthMap.get(RoleAuthStatus.REFUSE)!=null)  refuseRoles.addAll(roleAuthMap.get(RoleAuthStatus.REFUSE));
        refuseRoles = mergeAuthInSameLevel(grantedRoles,refuseRoles);

        departRoleStatusList = extractAuth(departRoleStatusList,grantedRoles,refuseRoles);
        roleAuthMap = departRoleStatusList.stream().collect(Collectors.groupingBy(RoleAuthStatus::getStatus));
        if(roleAuthMap.get(RoleAuthStatus.GRANTED)!=null)grantedRoles.addAll(roleAuthMap.get(RoleAuthStatus.GRANTED));
        if(roleAuthMap.get(RoleAuthStatus.REFUSE)!=null)  refuseRoles.addAll(roleAuthMap.get(RoleAuthStatus.REFUSE));
        refuseRoles = mergeAuthInSameLevel(grantedRoles,refuseRoles);


        groupRoleStatusList = extractAuth(groupRoleStatusList,grantedRoles,refuseRoles);
        roleAuthMap = groupRoleStatusList.stream().collect(Collectors.groupingBy(RoleAuthStatus::getStatus));
        if(roleAuthMap.get(RoleAuthStatus.GRANTED)!=null)grantedRoles.addAll(roleAuthMap.get(RoleAuthStatus.GRANTED));
        if(roleAuthMap.get(RoleAuthStatus.REFUSE)!=null)  refuseRoles.addAll(roleAuthMap.get(RoleAuthStatus.REFUSE));
        refuseRoles = mergeAuthInSameLevel(grantedRoles,refuseRoles);

        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        List<Role> roles = Lists.newArrayList();
        refuseRoles.forEach(roleAuthStatus ->{
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roleAuthStatus.getAuthCode()));
            //roles.add(systemDao.getRoleByAuth(roleAuthStatus.getAuthCode()));
        });
        grantedRoles.forEach(roleAuthStatus ->{
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roleAuthStatus.getAuthCode()));
            roles.add(systemDao.getRoleByAuth(roleAuthStatus.getAuthCode()));
        });


        List<Menu> menus = Lists.newArrayList();
        List<PageElement> pageElements = Lists.newArrayList();
        for (Role role : roles) {
            menus.addAll(role.getMenus());
            pageElements.addAll(role.getPageElements());
        }
        menus = menus.stream().filter(LamdaApi.distinctByKey(Menu::getAuthCode)).collect(Collectors.toList());
        pageElements = pageElements.stream().filter(LamdaApi.distinctByKey(PageElement::getAuthCode)).collect(Collectors.toList());
        menus.forEach(menu -> grantedAuthorities.add(new SimpleGrantedAuthority(menu.getAuthCode())));
        pageElements.forEach(el -> grantedAuthorities.add(new SimpleGrantedAuthority(el.getAuthCode())));
        user.setAuthorities(grantedAuthorities);
        user.setMenus(menus);
        refuseRoles.forEach(roleAuthStatus -> roles.add(systemDao.getRoleByAuth(roleAuthStatus.getAuthCode())));

        user.setRoles(roles);
    }


    private List<RoleAuthStatus> mergeAuthInSameLevel(List<RoleAuthStatus> grantedRoles, List<RoleAuthStatus> refuseRoles){
        for (RoleAuthStatus roleAuthStatus : grantedRoles) {
            refuseRoles = refuseRoles.stream().filter(roleAuthStatusRefuse -> !roleAuthStatusRefuse.getAuthCode().equals(roleAuthStatus.getAuthCode()))
                    .collect(Collectors.toList());
        }
        return refuseRoles;
    }
    private List<RoleAuthStatus> extractAuth(List<RoleAuthStatus> target,List<RoleAuthStatus> grantedRole, List<RoleAuthStatus> refuseRole){

        for (RoleAuthStatus roleAuthStatus : refuseRole) {
            target =  target.stream().filter(postRoleStatus -> !postRoleStatus.getAuthCode().equals(roleAuthStatus.getAuthCode()))
                    .collect(Collectors.toList());
        }
        for (RoleAuthStatus roleAuthStatus : grantedRole) {
            target =  target.stream().filter(postRoleStatus -> !postRoleStatus.getAuthCode().equals(roleAuthStatus.getAuthCode()))
                    .collect(Collectors.toList());
        }
        return  target;
    }
}
