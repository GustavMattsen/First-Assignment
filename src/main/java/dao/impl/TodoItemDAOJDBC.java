package dao.impl;

import dao.TodoItemDAO;
import model.Person;
import model.TodoItem;
import database.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TodoItemDAOJDBC implements TodoItemDAO {

    private PersonDAOJDBC personDao = new PersonDAOJDBC();

    @Override
    public void persist(TodoItem todoItem) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done, creator_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, todoItem.getTitle());
            ps.setString(2, todoItem.getDescription());
            if (todoItem.getDeadLine() != null) {
                ps.setDate(3, Date.valueOf(todoItem.getDeadLine()));
            } else {
                ps.setDate(3, null);
            }
            ps.setBoolean(4, todoItem.isDone());
            ps.setInt(5, todoItem.getCreator() == null ? 0 : todoItem.getCreator().getId());
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
        String sql = "SELECT id, title, description, deadline, done, creator_id FROM todo_item WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Person creator = null;
                    int creatorId = rs.getInt("creator_id");
                    if (creatorId > 0) creator = personDao.findById(creatorId);

                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    return new TodoItem(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            creator
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
        String sql = "SELECT id FROM todo_item";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findAllByDoneStatus(boolean done) {
        String sql = "SELECT id FROM todo_item WHERE done = ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, done);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByTitleContains(String title) {
        String sql = "SELECT id FROM todo_item WHERE title LIKE ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByPersonId(int personId) {
        String sql = "SELECT id FROM todo_item WHERE creator_id = ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByDeadlineBefore(java.time.LocalDate date) {
        String sql = "SELECT id FROM todo_item WHERE deadline < ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<TodoItem> findByDeadlineAfter(java.time.LocalDate date) {
        String sql = "SELECT id FROM todo_item WHERE deadline > ?";
        Collection<TodoItem> result = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(findById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM todo_item WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
