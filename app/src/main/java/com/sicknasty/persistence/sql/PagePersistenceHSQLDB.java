package com.sicknasty.persistence.sql;

import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.UserPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagePersistenceHSQLDB implements PagePersistence {
    private static int PERSONAL_PAGE = 0;
    private static int COMMUNITY_PAGE = 1;

    private String path;

    public PagePersistenceHSQLDB(String path) {
        this.path = path;

        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Pages (" +
                            "pg_id INTEGER IDENTITY PRIMARY KEY," +
                            "name VARCHAR(32) NOT NULL UNIQUE," +
                            "creator_username VARCHAR(32) NOT NULL," +
                            "type TINYINT NOT NULL" +
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
    public Page getPage(String name) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "SELECT * FROM Pages WHERE name = ? LIMIT 1"
            );
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (result.first()) {
                UserPersistence userDB = new UserPersistenceHSQLDB("");

                User user = userDB.getUser(result.getString("creator_username"));

                if (user != null) {
                    if (result.getInt("type") == this.PERSONAL_PAGE) {
                        return new PersonalPage(user);
                    } else if (result.getInt("type") == this.COMMUNITY_PAGE) {
                        return new CommunityPage(result.getString("name"), user);
                    }
                }
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return null;
    }

    @Override
    public boolean insertNewPage(Page page) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "SELECT pg_id FROM Pages WHERE name = ? LIMIT 1"
            );
            stmt.setString(1, page.getPageName());

            ResultSet result = stmt.executeQuery();
            if (result.first()) {
                //TODO: throw an exception or something lul
                return false;
            } else {
                stmt = db.prepareStatement(
                        "INSERT INTO Page VALUES(NULL, ?, ?, ?)"
                );
                stmt.setString(1, page.getPageName());
                //TODO: fix me urgently
//                stmt.setString(2, page.get);
//                stmt.setString(3, "123");
//                stmt.execute();
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return false;
    }

    @Override
    public boolean deletePage(String name) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "DELETE FROM Pages WHERE name = ? LIMIT 1"
            );
            stmt.setString(1, name);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return false;
    }

    @Override
    public boolean deletePage(Page page) {
        return this.deletePage(page.getPageName());
    }
}
