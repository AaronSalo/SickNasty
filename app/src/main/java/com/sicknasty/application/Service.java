package com.sicknasty.application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.sicknasty.persistence.*;
import com.sicknasty.persistence.sql.PagePersistenceHSQLDB;
import com.sicknasty.persistence.sql.PostPersistenceHSQLDB;
import com.sicknasty.persistence.sql.UserPersistenceHSQLDB;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


/*******
 * @author aaron
 *
 * this class manages the database, and allows us to fetch the db from other classes with ease
 * We create a new data handler for user, page, and post, which hold the data for each respective
 * items. We then can ask this class to provide the data handlers for each, allowing for persistant
 * data
 */
public class Service {
    private static UserPersistence userData = null;
    private static PostPersistence postData = null;
    private static PagePersistence pageData = null;

    private static String dbPath = "";
    private static String TAG = "SERVICE";

    public static synchronized void initTestDatabase() {
        userData = null;
        postData = null;
        pageData = null;

        try {
            File tmpFile = File.createTempFile("sicknasty-", ".script");

            Log.d(TAG, "Creating temporary database at: " + tmpFile.toString());

            Service.setPathName(tmpFile.toString());
        } catch (IOException e) {
            Log.e(TAG, "Failed to create temporary file");
            Log.e(TAG, e.getMessage());
        }
    }

    public static void setPathName(String path) {
        Log.d(TAG, "Setting DB path to: " + path);
        Service.dbPath = path;

        try {
            Log.d(TAG, "Linking driver class");
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            Log.d(TAG, "Linked driver class");
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static synchronized UserPersistence getUserData() {
        if (userData == null) {
            userData = new UserPersistenceHSQLDB(Service.dbPath);
        }

        return userData;
    }

    public static synchronized PostPersistence getPostData() {
        if (postData == null) {
            postData = new PostPersistenceHSQLDB(Service.dbPath);
        }

        return postData;
    }

    public static synchronized PagePersistence getPageData() {
        if (pageData == null) {
            pageData = new PagePersistenceHSQLDB(Service.dbPath);
        }

        return pageData;
    }
}
