package server.repository;

import utils.JdbcUtil;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserRepository implements IUserRepository {

    private JdbcUtil jdbcUtil;
    private static final Logger LOGGER = LogManager.getLogger(User.class);

    public UserRepository() {
        jdbcUtil = new JdbcUtil();
    }

    @Override
    public List<User> getAll() {
        String selectString = "select * from Users";
        List<User> users = new ArrayList<>();

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(username, password);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User create(User user) {
        String insertString = "insert into Users (username,password) values(?,?);";
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(insertString)) {
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.executeUpdate();
            LOGGER.debug("login - successful");
            return user;
        } catch (SQLException e) {
            LOGGER.debug("login - fail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(User entity, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findOne(String username) {
        String findString = "select * from Users where username=?;";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(findString)) {
            stm.setString(1, username);
            try (ResultSet result = stm.executeQuery()) {
                if (result.next()) {
                    String username_found = result.getString("username");
                    String password_found = result.getString("password");
                    User found = new User(username_found, password_found);
                    LOGGER.debug("user found");
                    return found;
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("user not found");
            e.printStackTrace();
        }
        return null;
    }

}