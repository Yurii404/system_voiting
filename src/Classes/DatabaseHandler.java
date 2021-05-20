package Classes;

import java.sql.*;

public class DatabaseHandler extends Config {
    Connection dbConnection;

    public   Connection getDbConnection() throws ClassNotFoundException,SQLException{

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

}
