package com.sicknasty.persistence.sql;


import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBGenericException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserPersistenceHSQLDB implements UserPersistence {
    private String path;

    public UserPersistenceHSQLDB(String path) throws SQLException {
        // set the path an initialize the database tables
        this.path = path;

        HSQLDBInitializer.setupTables(this.getConnection());
    }

    /**
     * This will create a new Connection to the database. Once this object leaves scope, it will shutdown
     *
     * @return Connection to the database
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + this.path + "shutdown=true", "SA", "");
    }

    @Override
    public User getUser(String username) throws DBUsernameNotFoundException {
        try {
            // init the connection
            Connection db = this.getConnection();

            // prepare a statement to query
            // this will completely prevent SQL injections
            // note however that the stuff coming back out is not safe
            PreparedStatement stmt = db.prepareStatement(
                "SELECT * FROM Users WHERE username = ? LIMIT 1"
            );

            // bind the parameter as a string set to username
            stmt.setString(1, username);

            // get the result of the SELECT
            ResultSet result = stmt.executeQuery();

            // cycle result to the first (and only) row
            if (result.next()) {
                return new User(
                    result.getString("name"),
                    result.getString("username"),
                    result.getString("password")
                );
            } else {
                throw new DBUsernameNotFoundException(username);
            }
        } catch (SQLException
                | ChangeNameException
                | UserCreationException
                | ChangeUsernameException
                | PasswordErrorException e
        ) {
            // note that the reason I am shoving these into the generic runtime exception
            // is that the user related exceptions will (in theory) never be thrown
            throw new DBGenericException(e);
        }
    }

    @Override
    public User insertNewUser(User user) throws DBUsernameExistsException {
        String username = user.getUsername();

        try {
            // connect to db
            Connection db = this.getConnection();

            // we need to check for an existing user
            // attempting to insert will throw an exception, but it appears that "SQLException" is a catch-all for ALL exceptions
            PreparedStatement stmt = db.prepareStatement(
                "SELECT uid FROM Users WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, username);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                throw new DBUsernameExistsException(username);
            } else {
                // if we dont have a row, that means that there is no user
                stmt = db.prepareStatement(
                    "INSERT INTO Users VALUES(?, ?, ?)"
                );
                stmt.setString(1, username);
                stmt.setString(2, user.getName());
                stmt.setString(3, user.getPassword());
                stmt.execute();
            }
            
            return user;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // i dont think this will ever happen, but just in case
                // think of it as a second line of defence
                throw new DBUsernameExistsException(username);
            } else {
                throw new DBGenericException(e);
            }
        }
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            // create connection
            Connection db = this.getConnection();

            // delete a user with username
            PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM Users WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, user.getUsername());

            // executeUpdate() will return number of rows affected (will be 1 or 0)
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }

    @Override
    public boolean updateUsername(String oldUsername, String newUsername) throws DBUsernameExistsException {
        try {
            // this will update the username of the user
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "UPDATE Users SET username = ? WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, newUsername);
            stmt.setString(1, oldUsername);

            // same as deleteUser, will return 1 or 0 rows affected
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new DBUsernameExistsException(newUsername);
            } else {
                throw new DBGenericException(e);
            }
        }
    }
}
