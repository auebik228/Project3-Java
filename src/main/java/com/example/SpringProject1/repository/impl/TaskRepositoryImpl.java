package com.example.SpringProject1.repository.impl;

import com.example.SpringProject1.domain.exception.ResourceMappingException;
import com.example.SpringProject1.domain.task.Task;
import com.example.SpringProject1.repository.DataSourceConfig;
import com.example.SpringProject1.repository.TaskRepository;
import com.example.SpringProject1.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = "SELECT t.id as task_id,\n" + "       t.title           as task_title,\n" + "       t.description     as task_description,\n" + "       t.expiration_date as task_expiration_date,\n" + "       t.status          as task_status\n" + "FROM tasks t\n" + "WHERE id = ?";
    private final String FIND_ALL_BY_USER_ID = "SELECT t.id              as task_id,\n" + "       t.title           as task_title,\n" + "       t.description     as task_description,\n" + "       t.expiration_date as task_expiration_date,\n" + "       t.status          as task_status\n" + "FROM tasks t\n" + "JOIN users_tasks ut on t.id = ut.task_id\n" + "WHERE ut.user_id= ?";
    private final String ASSIGN = "INSERT INTO users_tasks(task_id,user_id) VALUES (?,?)";
    private final String DELETE = "DELETE FROM tasks WHERE id = ?";
    private final String UPDATE = "UPDATE tasks SET title = ?, description = ?, expiration_date = ?, status = ? WHERE id = ?";
    private final String CREATE = "INSERT INTO tasks (title,description,expiration_date,status) VALUES (?,?,?,?)";

    @Override
    public Optional<Task> findById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding task by id");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return TaskRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding all by id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN);
            preparedStatement.setLong(1, taskId);
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assigning task to user by id");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setString(3, String.valueOf(task.getExpirationDate()));
            }
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setLong(5, task.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating user");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setString(3, String.valueOf(task.getExpirationDate()));
            }
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating task");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting task");
        }
    }
}
