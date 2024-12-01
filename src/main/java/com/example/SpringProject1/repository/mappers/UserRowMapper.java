package com.example.SpringProject1.repository.mappers;

import com.example.SpringProject1.domain.task.Task;
import com.example.SpringProject1.domain.user.Role;
import com.example.SpringProject1.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {
    @SneakyThrows
    public static User mapRow(ResultSet rs){
        Set<Role> roles = new HashSet<>();
        while(rs.next()){
            roles.add(Role.valueOf(rs.getString("user_user_role")));
        }
        rs.beforeFirst();
        List<Task> tasks = TaskRowMapper.mapRows(rs);
        rs.beforeFirst();
        if(rs.next()){
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("user_name"));
            user.setUsername(rs.getString("user_username"));
            user.setPassword(rs.getString("user_password"));
            user.setRoles(roles);
            user.setTasks(tasks);
            return user;
        }
        return null;
    }
}
