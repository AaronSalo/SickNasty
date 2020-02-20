package com.sicknasty.persistence.sql;

import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPersistenceHSQLDB implements UserPersistence {
    private String path;

    public UserPersistenceHSQLDB(String path) {
        this.path = path;

        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Users (" +
                            "username VARCHAR(32) PRIMARY KEY," +
                            "name VARCHAR(32) NOT NULL," +
                            "password VARCHAR(128) NOT NULL" +
                            ")");
            stmt.execute();
        } catch (SQLException e) {
            //TODO: do something lul
        }
    }

    /**
     * This will create a new Connection to the database. Once this object leaves scope, it will shutdown
     *
     * @return Connection to the database
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + this.path, "SA", "");
    }

    @Override
    public User getUser(String username) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
              "SELECT * FROM Users WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, username);

            ResultSet result = stmt.executeQuery();
            if (result.first()) {
                return new User(
                    result.getString("name"),
                    result.getString("username"),
                    result.getString("password")
                );
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return null;
    }

    @Override
    public User insertNewUser(User user) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "SELECT uid FROM Users WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, user.getUsername());

            ResultSet result = stmt.executeQuery();
            if (result.first()) {
                //TODO: throw an exception or something lul
                return null;
            } else {
                stmt = db.prepareStatement(
                    "INSERT INTO Users VALUES(?, ?, ?)"
                );
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getName());
                stmt.setString(3, "123");
                stmt.execute();
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return null;
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM Users WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, user.getUsername());

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return false;
    }

    @Override
    public boolean updateUsername(String old, String newOne) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "UPDATE Users SET username = ? WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, newOne);
            stmt.setString(1, old);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return false;
    }
}
