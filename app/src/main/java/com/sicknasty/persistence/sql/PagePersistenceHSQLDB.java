package com.sicknasty.persistence.sql;

import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.InvalidCommunityPageNameException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBGenericException;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PagePersistenceHSQLDB implements PagePersistence {
    // note that the reason this is here is because of how the pages are saved in the database
    // a more detailed explanation is in insertNewPage() of this class
    // but basically: we either violate DRY in the db or we have to use instanceof in code
    // we chose to use instanceof
    private final static int PERSONAL_PAGE = 0;
    private final static int COMMUNITY_PAGE = 1;

    private String path;

    public PagePersistenceHSQLDB(String path) throws SQLException {
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
    public Page getPage(String name) throws DBPageNameNotFoundException {
        try {
            // create connection
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "SELECT * FROM Pages WHERE pg_name = ? LIMIT 1"
            );
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                // this doesnt feel right but it seems to be fine?
                // query our other class to get the user
                UserPersistence userDB = new UserPersistenceHSQLDB(this.path);

                User user = userDB.getUser(result.getString("creator_username"));

                if (user != null) {
                    // use our private "enum" to create the page
                    // again, more detailed explanation in insertNewPage() of this class
                    switch (result.getInt("type")) {
                        case PERSONAL_PAGE:
                            return new PersonalPage(user);
                        case COMMUNITY_PAGE:
                            return new CommunityPage(user, name);
                    }
                }
            }
        } catch (SQLException | DBUsernameNotFoundException | InvalidCommunityPageNameException e) {
            throw new DBGenericException(e);
        }

        throw new DBPageNameNotFoundException(name);
    }

    @Override
    public void insertNewPage(Page page) throws DBPageNameExistsException {
        try {
            Connection db = this.getConnection();

            // check to see if the page exists first
            PreparedStatement stmt = db.prepareStatement(
                "SELECT pg_name FROM Pages WHERE pg_name = ? LIMIT 1"
            );
            stmt.setString(1, page.getPageName());

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                throw new DBPageNameExistsException(page.getPageName());
            } else {
                // insert new page
                stmt = db.prepareStatement(
                    "INSERT INTO Pages VALUES(?, ?, ?)"
                );
                stmt.setString(1, page.getPageName());
                stmt.setString(2, page.getCreator().getUsername());

				/*
					So here, I am using instanceof. If we were to not use instanceof, we would
					either have to have two difference tables that contain the same data
					or we would have to keep a variable that keeps track of what kind of Page it is
					in the Page object itself.

					If we decided to have two tables, we would be violating DRY.
					If we had a variable to keep a "magic" value, we would still be dependent on an if/else.
					If we used instanceof, we would have to depend on if/else.

					Either way, we lose.
				*/
                if (page instanceof PersonalPage) {
                    stmt.setInt(3, this.PERSONAL_PAGE);
                } else {
                    stmt.setInt(3, this.COMMUNITY_PAGE);
                }

                stmt.execute();
            }
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }

    @Override
    public void deletePage(String name) {
        try {
            // deletes a page. :|

            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM Pages WHERE pg_name = ? LIMIT 1"
            );
            stmt.setString(1, name);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }

    @Override
    public void deletePage(Page page) {
        this.deletePage(page.getPageName());
    }

    @Override
    public void addFollower(Page page, User user) throws DBUserAlreadyFollowingException {
        String pageName = page.getPageName();
        String username = user.getUsername();

        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "SELECT * FROM PageFollowers WHERE username = ? AND pg_name = ? LIMIT 1"
            );
            stmt.setString(1, username);
            stmt.setString(2, pageName);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                throw new DBUserAlreadyFollowingException(username, pageName);
            } else {
                stmt = db.prepareStatement(
                    "INSERT INTO PageFollowers VALUES (?, ?)"
                );
                stmt.setString(1, username);
                stmt.setString(2, pageName);

                stmt.execute();
                page.addFollower(user);
            }
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }
    }

	@Override
	public void changeName(String oldName, String newName) {
		try {
			Connection db = this.getConnection();

			PreparedStatement stmt = db.prepareStatement(
				"UPDATE Pages SET pg_name = ? WHERE pg_name = ? LIMIT 1"
			);
			stmt.setString(1, newName);
			stmt.setString(2, oldName);	

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBGenericException(e);
		}
	}

    @Override
    public ArrayList<String> getAllCommunityPageNames() {
        ArrayList<String> returnNames = new ArrayList<String>();

        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "SELECT pg_name FROM Pages WHERE type = 1"
            );

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                String pageName = result.getString(1);

                returnNames.add(pageName);
            }
        } catch (SQLException e) {
            throw new DBGenericException(e);
        }

        return returnNames;
    }
}
