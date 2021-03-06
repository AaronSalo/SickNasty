package com.sicknasty.persistence.sql;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.MessageException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Message;
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
import java.util.ArrayList;

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
        return DriverManager.getConnection("jdbc:hsqldb:file:" + this.path + ";shutdown=true", "SA", "");
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

            // the reason for that is because BEFORE getting entered into the database, these
            // values will get checked in their respective places before coming to the DB
            // so the name will never be a dupe of another, password will be correct length, etc
            throw new DBGenericException(e);
        }
    }

    @Override
    public ArrayList<String> getAllUsers() {
        ArrayList<String> usernames = new ArrayList<String>();

        try {
            // init the connection
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "SELECT username FROM Users"
            );

            // get the result of the SELECT
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                usernames.add(result.getString(1));
            }
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }

        return usernames;
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
                "SELECT username FROM Users WHERE username = ? LIMIT 1"
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
    public void deleteUser(User user) {
        try {
            // create connection
            Connection db = this.getConnection();

            // delete a user with username
            PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM Users WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) throws DBUsernameExistsException, DBUsernameNotFoundException, ChangeUsernameException {
        // try getting the user, if it fails it will throw DBUsernameNotFoundException, which we pass up
        User usr = this.getUser(oldUsername);

        // try to change username, see if we fail any constraints
        usr.changeUsername(newUsername);
        //update
        try {
            // this will update the username of the user
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "UPDATE Users SET username = ? WHERE username = ? LIMIT 1"
            );
            stmt.setString(1,newUsername);
            stmt.setString(2, oldUsername);

            // same as deleteUser, will return 1 or 0 rows affected
            if (stmt.executeUpdate() == 1) {
				PagePersistenceHSQLDB pageDB = new PagePersistenceHSQLDB(this.path);

				pageDB.changeName(oldUsername, newUsername);
			}
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new DBUsernameExistsException(newUsername);
            } else {
                throw new DBGenericException(e);
            }
        }
    }

    @Override
    public void updatePassword(User user, String password) throws DBUsernameNotFoundException, PasswordErrorException {
        // try getting the user, if it fails it will throw DBUsernameNotFoundException, which we pass up
        User usr = this.getUser(user.getUsername());

        // try changing password, if it fails, throws PasswordErrorException and we pass that up
        usr.changePassword(password);

        try {
            // this will update the username of the user
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "UPDATE Users SET password = ? WHERE username = ? LIMIT 1"
            );
            stmt.setString(1, password);
            stmt.setString(2, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }

    @Override
    public ArrayList<Message> getMessages(User user1, User user2) {
        ArrayList<Message> rtnMessage = new ArrayList<Message>();

        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                 "SELECT * FROM Messages " +
                     "WHERE " +
                         "(sender = ? AND receiver = ?) " +
                         "OR " +
                         "(sender = ? AND receiver = ?) " +
                     "ORDER BY time_sent ASC"
            );
            stmt.setString(1, user1.getUsername());
            stmt.setString(2, user2.getUsername());
            stmt.setString(3, user2.getUsername());
            stmt.setString(4, user1.getUsername());

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                User sender = this.getUser(result.getString("sender"));
                User receiver = this.getUser(result.getString("receiver"));

                String message = result.getString("message");

                Message msg = new Message(
                    message,
                    sender,
                    receiver
                );
                msg.setTimeSent(result.getLong("time_sent"));

                rtnMessage.add(msg);
            }
        } catch (SQLException | DBUsernameNotFoundException | MessageException e) {
            throw new DBGenericException(e);
        }

        return rtnMessage;
    }

    @Override
    public void addMessage(Message message) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "INSERT INTO Messages VALUES(?, ?, ?, ?, NULL)"
            );

            String senderUsername = message.getMessenger().getUsername();
            String recvUsername = message.getReceiver().getUsername();

            stmt.setString(1, senderUsername);
            stmt.setString(2, recvUsername);
            stmt.setString(3, message.getMsg());
            stmt.setLong(4, message.getTimeSent());

            stmt.execute();
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }
}
