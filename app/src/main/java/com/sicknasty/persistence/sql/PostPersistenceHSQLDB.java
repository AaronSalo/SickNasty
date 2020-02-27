package com.sicknasty.persistence.sql;

import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.persistence.PostPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostPersistenceHSQLDB implements PostPersistence {
    private static int TEXT_POST = 0;
    private static int PICTURE_POST = 1;
    private static int VIDEO_POST = 2;

    private String path;

    public PostPersistenceHSQLDB(String path) {
        this.path = path;

        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Posts (" +
                            "p_id INTEGER IDENTITY PRIMARY KEY," +
                            "pg_id INTEGER NOT NULL," +
                            "text VARCHAR(1024) NOT NULL," +
                            "media_path VARCHAR(1024)," +
                            "likes INTEGER NOT NULL," +
                            "dislikes INTEGER NOT NULL," +
                            "creator_username VARCHAR(32) NOT NULL," +
                            "time_created FLOAT NOT NULL" +
                            "type TINYINT NOT NULL" +
                            ");" +
                        "CREATE TABLE IF NOT EXISTS PagePosts (" +
                            "p_id INTEGER NOT NULL," +
                            "pg_id INTEGER NOT NULL," +
                            "" +
                            "FOREIGN KEY(p_id) REFERENCES Posts(p_id) ON CASCADE DELETE," +
                            "FOREIGN KEY(pg_id) REFERENCES Pages(pg_id) ON CASCADE DELETE" +
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
    public Post getPostById(int id) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                    "SELECT * FROM Posts WHERE p_id = ? LIMIT 1"
            );
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if (result.first()) {
                if (result.getInt("type") == this.TEXT_POST) {
//                    return new TextPost(
//                        result.getString("text"),
//                            result.getString("text"),
//                            result.getString("text"),
//                            result.getString("text"),
//                            result.getString("text"),
//                            result.getString("text")
//                    );
                }
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return null;
    }

    @Override
    public ArrayList<Post> getPostsByPage(Page page, int limit, FILTER_BY filter, boolean accendingOrder) {
        return null;
    }

    @Override
    public boolean insertNewPost(Post post) {
        return false;
    }

    @Override
    public boolean deletePost(int id) {
        return false;
    }

    @Override
    public boolean deletePost(Post post) {
        return false;
    }
}
