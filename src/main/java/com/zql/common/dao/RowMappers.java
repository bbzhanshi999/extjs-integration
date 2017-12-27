package com.zql.common.dao;

import com.zql.common.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/11/6.
 */
public class RowMappers {
    public static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setSexual(rs.getString("sexual"));
            user.setAddress(rs.getString("address"));
            user.setAge(rs.getInt("age"));
            user.setName(rs.getString("name"));
            user.setEname(rs.getString("ename"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setUseable(rs.getString("useable"));
            user.setIsGod(rs.getString("is_god"));
            BaseEntity.setCommonField(rs,user);
            return user;
        }
    }

    public static final class RoleMapper implements RowMapper<Role>{

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setAuthCode(rs.getString("auth_code"));
            role.setRoleName(rs.getString("role_name"));
            role.setDecription(rs.getString("decription"));
            BaseEntity.setCommonField(rs,role);
            return role;
        }
    }

    public static final class MenuMapper implements RowMapper<Menu>{

        @Override
        public Menu mapRow(ResultSet rs, int i) throws SQLException {
            Menu menu = new Menu();
            menu.setMenuName(rs.getString("menu_name"));
            menu.setAuthCode(rs.getString("auth_code"));
            menu.setUrl(rs.getString("url"));
            menu.setSort(rs.getInt("sort"));
            menu.setIconCls(rs.getString("iconCls"));
            menu.setType(rs.getString("type"));
            BaseEntity.setCommonField(rs,menu);
            TreeEntity.setTreeField(rs,menu);
            return menu;
        }
    }

    public static final class PageElMapper implements  RowMapper<PageElement>{

        @Override
        public PageElement mapRow(ResultSet rs, int i) throws SQLException {
            PageElement el = new PageElement();
            el.setAuthCode(rs.getString("auth_code"));
            el.setElName(rs.getString("el_name"));
            el.setMenuId(rs.getLong("menu_id"));
            BaseEntity.setCommonField(rs,el);
            TreeEntity.setCommonField(rs,el);
            return el;
        }
    }

    public static final class DepartmentMapper implements RowMapper<Department>{
        @Override
        public Department mapRow(ResultSet rs, int i) throws SQLException {
            Department department = new Department();
            department.setDepartCode(rs.getString("depart_code"));
            department.setDepartName(rs.getString("depart_name"));
            department.setDepartType(rs.getString("depart_type"));
            BaseEntity.setCommonField(rs,department);
            TreeEntity.setCommonField(rs,department);
            return department;
        }
    }

    public static final class PostMapper implements  RowMapper<Post>{

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();
            post.setPostName(rs.getString("post_name"));
            post.setDescription(rs.getString("description"));
            BaseEntity.setCommonField(rs,post);
            return post;
        }
    }

    public static final class GroupMapper implements  RowMapper<Group>{

        @Override
        public Group mapRow(ResultSet rs, int i) throws SQLException {
            Group group = new Group();
            group.setGroupName(rs.getString("group_name"));
            group.setDescription(rs.getString("description"));
            BaseEntity.setCommonField(rs,group);
            return group;
        }
    }

    public static final class AuthStatusMapper implements  RowMapper<RoleAuthStatus>{

        @Override
        public RoleAuthStatus mapRow(ResultSet rs, int i) throws SQLException {
            return new RoleAuthStatus(rs.getString("auth_code"),rs.getString("status"));
        }
    }
}
