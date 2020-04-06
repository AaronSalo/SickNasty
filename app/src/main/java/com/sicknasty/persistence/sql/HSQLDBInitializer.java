package com.sicknasty.persistence.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class HSQLDBInitializer {
    static void setupTables(Connection db) throws SQLException {
        // get the database metadata to search for a table named USERS
        // we will use the result at the end of the script (after creating tables)
        DatabaseMetaData meta = db.getMetaData();
        ResultSet result = meta.getTables(null, null, "USERS", null);

        PreparedStatement stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS Users (" +
                "username VARCHAR(32) PRIMARY KEY," +
                "name VARCHAR(32) NOT NULL," +
                "password VARCHAR(128) NOT NULL" +
            ")"
        );
        stmt.execute();

        stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS Posts (" +
                "p_id INTEGER IDENTITY PRIMARY KEY," +
                "text VARCHAR(1024) NOT NULL," +
                "media_path VARCHAR(1024)," +
                "likes INTEGER NOT NULL," +
                "dislikes INTEGER NOT NULL," +
                "creator_username VARCHAR(32) NOT NULL," +
                "time_created BIGINT NOT NULL," +
                "FOREIGN KEY(creator_username) REFERENCES Users(username) ON DELETE CASCADE" +
            ")"
        );
        stmt.execute();

        stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS Pages (" +
                "pg_name VARCHAR(32) NOT NULL PRIMARY KEY," +
                "creator_username VARCHAR(32) NOT NULL," +
                "type TINYINT NOT NULL" +
            ")"
        );
        stmt.execute();

        stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS PagePosts (" +
                "p_id INTEGER NOT NULL," +
                "pg_name VARCHAR(32) NOT NULL," +
                "FOREIGN KEY(p_id) REFERENCES POSTS(p_id) ON DELETE CASCADE," +
                "FOREIGN KEY(pg_name) REFERENCES Pages(pg_name) ON DELETE CASCADE" +
            ")"
        );
        stmt.execute();

        stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS PageFollowers (" +
                "username VARCHAR(32) NOT NULL," +
                "pg_name VARCHAR(32) NOT NULL," +
                "FOREIGN KEY(username) REFERENCES Users(username)" +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE," +
                "FOREIGN KEY(pg_name) REFERENCES Pages(pg_name)" +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE" +
            ")"
        );
        stmt.execute();

        stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS Messages (" +
                "sender VARCHAR(32) NOT NULL," +
                "receiver VARCHAR(32) NOT NULL," +
                "message VARCHAR(1024) NOT NULL," +
                "time_sent BIGINT NOT NULL," +
                "time_seen BIGINT," +
                "FOREIGN KEY(sender) REFERENCES Users(username)" +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE," +
                "FOREIGN KEY(receiver) REFERENCES Users(username)" +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE" +
            ")"
        );
        stmt.execute();

        stmt = db.prepareStatement(
            "CREATE TABLE IF NOT EXISTS Comments (" +
                "commenter VARCHAR(32) NOT NULL," +
                "p_id INTEGER NOT NULL," +
                "contents VARCHAR(1024) NOT NULL," +
                "time_created BIGINT NOT NULL," +
                "FOREIGN KEY(commenter) REFERENCES Users(username)" +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE," +
                "FOREIGN KEY(p_id) REFERENCES POSTS(p_id) ON DELETE CASCADE" +
            ")"
            
        );
        stmt.execute();

        // this means that before creating tables, there wasnt a table named USERS already
        // insert some user data
        if (!result.next()) {
            //////////////////////////////
            // bob and his page + post
            stmt = db.prepareStatement(
                "INSERT INTO Users VALUES('notbob123', 'UncleBob', 'bobsgreatpass')"
            );
            stmt.execute();

            stmt = db.prepareStatement(
                "INSERT INTO Pages VALUES('notbob123', 'notbob123', 0)"
            );
            stmt.execute();

            stmt = db.prepareStatement(
                "INSERT INTO Posts VALUES(0, 'Uncle Bob hates computer science', 'test', 0, 0, 'notbob123', ?)"
            );
            stmt.setLong(1, System.currentTimeMillis());
            stmt.execute();

            stmt = db.prepareStatement(
                "INSERT INTO PagePosts VALUES(0, 'notbob123')"
            );
            stmt.execute();

            //////////////////////////////
            // engineer and his page + post
            stmt = db.prepareStatement(
                "INSERT INTO Users VALUES('texanwits', 'TheEngineer', 'ihatespies')"
            );
            stmt.execute();

            stmt = db.prepareStatement(
                "INSERT INTO Pages VALUES('texanwits', 'texanwits', 0)"
            );
            stmt.execute();

            stmt = db.prepareStatement(
                "INSERT INTO Posts VALUES(1, 'HEAVY LOAD COMING THROUGH', 'test', 0, 0, 'texanwits', ?)"
            );
            stmt.setLong(1, System.currentTimeMillis());
            stmt.execute();

            stmt = db.prepareStatement(
                "INSERT INTO PagePosts VALUES(1, 'texanwits')"
            );
            stmt.execute();
        }
    }
}
