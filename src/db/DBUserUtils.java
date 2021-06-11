package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUserUtils extends DBConnection {

    static final Logger LOG = LoggerFactory.getLogger(DBUserUtils.class);

    public boolean insertUser(String userName, String userPassword) {
        LOG.debug(String.format("insertUser"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into user_worker(username, userpassword)" +
                    " values (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }
        return true;
    }

    public User getUser(String userName, String userPassword) {
        LOG.debug(String.format("getUser"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            String sql = "select id, username, userpassword from user_worker where username = ? and userpassword = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            Long id = rs.getLong("id");
            if(id != null) {
                user = new User(userName, userPassword);
                user.setUserId(id);
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }
        return user;
    }

}
