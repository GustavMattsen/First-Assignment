package dao.impl;

import dao.TodoItemDAO;
import model.TodoItem;
import database.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TodoItemDAOJDBC implements TodoItemDAO {

    @Override
    public void persist(TodoItem todoItem) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, todoItem.getTitle());
            ps.setString(2, todoItem.getDescription());
            if (todoItem.getDeadLine() != null) {
                ps.setDate(3, Date.valueOf(todoItem.getDeadLine()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBoolean(4, todoItem.isDone());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    todoItem.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TodoItem findById(int id) {
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item WHERE todo_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    return new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            null // skip assignee for now (beginner style)
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Collection<TodoItem> findAll() {
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date d = rs.getDate("deadline");
                LocalDate ld = (d == null ? null : d.toLocalDate());

                result.add(new TodoItem(
                        rs.getInt("todo_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        ld,
                        rs.getBoolean("done"),
                        null
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findAllByDoneStatus(boolean done) {
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item WHERE done = ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, done);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    result.add(new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByTitleContains(String title) {
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item WHERE title LIKE ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    result.add(new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByPersonId(int personId) {
        // Beginner version: ignore the assignee_id foreign key
        return new ArrayList<>();
    }

    @Override
    public Collection<TodoItem> findByDeadlineBefore(LocalDate date) {
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item WHERE deadline < ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    result.add(new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByDeadlineAfter(LocalDate date) {
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item WHERE deadline > ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    result.add(new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

