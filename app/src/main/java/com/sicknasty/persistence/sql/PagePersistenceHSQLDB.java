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
    private final static int PERSONAL_PAGE = 0;
    private final static int COMMUNITY_PAGE = 1;

    private String path;

    public PagePersistenceHSQLDB(String path) {
        this.path = path;

        try {
            HSQLDBInitializer.setupTables(this.getConnection());
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
        return DriverManager.getConnection("jdbc:hsqldb:file:" + this.path + "shutdown=true", "SA", "");
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
            if (result.next()) {
                UserPersistence userDB = new UserPersistenceHSQLDB("sicknasty");

                User user = userDB.getUser(result.getString("creator_username"));

                if (user != null) {
                    switch (result.getInt("type")) {
                        case PERSONAL_PAGE:
                            return new PersonalPage(user);
                        case COMMUNITY_PAGE:
                            return new CommunityPage(result.getString("pg_name"), user);
                    }
                }
            }
            
            db.close();
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return null;
    }

    @Override
    //TODO:
    //PICKLE
    // i cant tell what is personal page and what is community page
    public boolean insertNewPage(Page page) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "SELECT pg_id FROM Pages WHERE name = ? LIMIT 1"
            );
            stmt.setString(1, page.getPageName());

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                //TODO: throw an exception or something lul
                return false;
            } else {
                stmt = db.prepareStatement(
                    "INSERT INTO Page VALUES(NULL, ?, ?, ?)"
                );
                stmt.setString(1, page.getPageName());
                stmt.setString(2, page.getCreator().getUsername());
                stmt.setInt(3, this.PERSONAL_PAGE);
                stmt.execute();
            }
            
            db.close();
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

            db.close();
            
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
