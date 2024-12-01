package com.example.SpringProject1.repository.impl;

import com.example.SpringProject1.domain.exception.ResourceMappingException;
import com.example.SpringProject1.domain.user.Role;
import com.example.SpringProject1.domain.user.User;
import com.example.SpringProject1.repository.DataSourceConfig;
import com.example.SpringProject1.repository.UserRepository;
import com.example.SpringProject1.repository.mappers.UserRowMapper;
import com.example.SpringProject1.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;
    private final String FIND_BY_ID = "SELECT u.id as user_id,\n" +
            "       u.name as user_name,\n" +
            "       u.username as user_username,\n" +
            "       u.password as user_password,\n" +
            "       ur.role as user_role_role,\n" +
            "       t.id as task_id,\n" +
            "       t.title as task_title,\n" +
            "       t.description as task_discription,\n" +
            "       t.expiration_date as task_expiration_date\n" +
            "\n" +
            "FROM users u\n" +
            "    LEFT JOIN users_roles ur on ur.user_id = u.id\n" +
            "    LEFT JOIN users_tasks ut on u.id = ut.user_id\n" +
            "    LEFT JOIN tasks t on ut.task_id = t.id\n" +
            "WHERE u.id = ?\n";

    private final String FIND_BY_USERNAME = "SELECT u.id as user_id,\n" +
            "       u.name as user_name,\n" +
            "       u.username as user_username,\n" +
            "       u.password as user_password,\n" +
            "       ur.role as user_role_role,\n" +
            "       t.id as task_id,\n" +
            "       t.title as task_title,\n" +
            "       t.description as task_discription,\n" +
            "       t.expiration_date as task_expiration_date\n" +
            "\n" +
            "FROM users u\n" +
            "    LEFT JOIN users_roles ur on ur.user_id = u.id\n" +
            "    LEFT JOIN users_tasks ut on u.id = ut.user_id\n" +
            "    LEFT JOIN tasks t on ut.task_id = t.id\n" +
            "WHERE u.username = ?\n";

    private final String UPDATE = "UPDATE users\n" +
            "SET name = ?,\n" +
            "    username = ?,\n" +
            "    password = ?\n" +
            "WHERE id = ?";
    private final String CREATE = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
    private final String INSERT_USER_ROLE = "INSERT INTO users_roles (user_id, role) values (?, ?)";
    private final String DELETE = "DELETE FROM users WHERE id = ?";
    private final String IS_TASK_OWNER = "SELECT exists(\n" +
            "    SELECT 1\n" +
            "    FROM users_tasks\n" +
            "    WHERE user_id = ?\n" +
            "    AND task_id = ?\n" +
            ")";

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        }catch (SQLException e){
            throw new ResourceMappingException("Exeprion while finding user by id");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        }catch (SQLException e){
            throw new ResourceMappingException("Exception while finding user by username");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Exception while updating user");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Exception while creating user");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, role.name());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Exception while inserting  user role");
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(IS_TASK_OWNER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        }catch (SQLException e){
            throw new ResourceMappingException("Exception while checking if user is task owner");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Exception while checking if user is task owner");
        }
    }
}
