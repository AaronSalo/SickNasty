package com.sicknasty.persistence.sql;

import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.TextPost;
import com.sicknasty.objects.PicturePost;
import com.sicknasty.objects.VideoPost;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.sql.UserPersistenceHSQLDB;

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
    public Post getPostById(int id) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "SELECT * FROM Posts WHERE p_id = ? LIMIT 1"
            );
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return this.postBuilder(result);
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }

        return null;
    }

    @Override
    public ArrayList<Post> getPostsByPage(Page page, int limit, FILTER_BY filter, boolean accendingOrder) {
        try {
            Connection db = this.getConnection();
            
            String filterArg = "";
            switch (filter) {
                case PostPersistence.FILTER_BY.TIME_CREATED:
                    filterArg = "time_created";
                    break;
                case PostPersistence.FILTER_BY.AMOUNT_LIKES:
                    filterArg = "likes";
                    break;
                case PostPersistence.FILTER_BY.AMOUNT_DISLIKES:
                    filterArg = "dislikes";
                    break;
            }
            
            PreparedStatement stmt = db.prepareStatement(
                "SELECT * FROM Posts " + 
                "WHERE Posts.p_id IN " +
                    "(SELECT p_id FROM PagePosts WHERE PagePosts.pg_name = ?) " + 
                "ORDER BY " + filterArg + " " + (accendingOrder ? "ASC" : "DESC") + " LIMIT ?"
            );
            stmt.setString(1, page.getPageName());
            stmt.setInt(2, limit);
            
            ArrayList<Posts> retList = new ArrayList<Posts>();
            Result result = stmt.executeQuery();
            while (result.next()) {
                retList.add(this.postBuilder(result));
            }
            
            if (retList.size() > 0) return retList;
        } catch (SQLException e) {
            //TODO: do something lul
        }
        
        return null;
    }

    //TODO: i cant tell what kind of post it is
    @Override
    public boolean insertNewPost(Post post) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "SELECT p_id FROM Posts WHERE p_id = ? LIMIT 1"
            );
            stmt.setInt(1, post.getPostID());

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                //TODO: throw an exception or something lul
                return null;
            } else {
                stmt = db.prepareStatement(
                    "INSERT INTO Posts VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                );
                stmt.setString(1, post.getText());
                stmt.setString(2, post.getName());
                stmt.setString(3, "path"); //TODO: this is the path
                stmt.setInt(4, post.getNumberOfLikes());
                stmt.setInt(5, post.getNumberOfDislikes());
                stmt.setString(6, post.getUserId().getUsername());
                stmt.setInt(7, this.PICTURE_POST);
                stmt.executeUpdate();
                
                ResultSet postID = stmt.getGeneratedKeys();
                postID.next();
                
                stmt = db.prepareStatement(
                    "INSERT INTO PagePosts VALUES(?, ?)"
                );
                stmt.setInt(1, postID.getInt(1));
                stmt.setString(2, post.getPageId().getPageName());
                stmt.execute();
                
                return true;
            }
        } catch (SQLException e) {
            //TODO: do something lul
        }
        
        return false;
    }

    @Override
    public boolean deletePost(int id) {
        try {
            Connection db = this.getConnection();

            PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM Posts WHERE p_id = ? LIMIT 1"
            );
            stmt.setInt(1, id);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            //TODO: do something lul
        }
    
        return false;
    }

    @Override
    public boolean deletePost(Post post) {
        return this.deletePost(post.getPageID());
    }
    
    private Post postBuilder(ResultSet result) {
        UserPersistenceHSQLDB uSQL = new UserPersistenceHSQLDB("sicknasty");
        PagePersistenceHSQLDB pgSQL = new PagePersistenceHSQLDB("sicknasty");
            
        switch (result.getInt("type")) {
            case this.TEXT_POST:
                return new TextPost(
                    result.getString("text"),
                    uSQL.getUser(result.getString("creator_username")),
                    (long) result.getString("time_created"),
                    result.getString("likes"),
                    result.getString("dislikes"),
                    pgSQL.getPage(result.getString("pg_name"))
                );
            case this.PICTURE_POST:
                return new TextPost(
                    result.getString("text"),
                    uSQL.getUser(result.getString("creator_username")),
                    result.getString("media_path"),
                    (long) result.getString("time_created"),
                    result.getString("likes"),
                    result.getString("dislikes"),
                    pgSQL.getPage(result.getString("pg_name"))
                );
            case this.VIDEO_POST:
                return new TextPost(
                    result.getString("text"),
                    uSQL.getUser(result.getString("creator_username")),
                    0,
                    result.getString("media_path"),
                    (long) result.getString("time_created"),
                    result.getString("likes"),
                    result.getString("dislikes"),
                    pgSQL.getPage(result.getString("pg_name"))
                );
        }
    }
}
