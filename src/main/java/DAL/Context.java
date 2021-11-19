package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Context {
    private static final String URL = "jdbc:sqlite:db.db";
    private static Context instance;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private Context() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Context getInstance(){
        if(instance == null){
            instance = new Context();
        }
        return instance;
    }
}
