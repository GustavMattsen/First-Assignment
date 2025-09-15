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
        // insert a new to-do row into the table
        String sql = "INSERT INTO todo_item (title, description, deadline, done) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, todoItem.getTitle());
            ps.setString(2, todoItem.getDescription());

            // if deadline is null then set NULL in db
            if (todoItem.getDeadLine() != null) {
                ps.setDate(3, Date.valueOf(todoItem.getDeadLine()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBoolean(4, todoItem.isDone());

            // run the insert
            ps.executeUpdate();

            // get auto generated id back
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
        // find a to-do item by its id
        String sql = "SELECT todo_id, title, description, deadline, done FROM todo_item WHERE todo_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date d = rs.getDate("deadline");
                    LocalDate ld = (d == null ? null : d.toLocalDate());

                    // return a new TodoItem object
                    return new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            ld,
                            rs.getBoolean("done"),
                            null // not handling assignee for now
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
        // get all todos from the table
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
        // search all todos that match the done value
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
        // search todos where the title has the given string
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
        // not implemented, just return empty list for now
        return new ArrayList<>();
    }

    @Override
    public Collection<TodoItem> findByDeadlineBefore(LocalDate date) {
        // get all todos with deadline before a date
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
        // get all todos with deadline after a date
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
        // delete a to-do item by id
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

