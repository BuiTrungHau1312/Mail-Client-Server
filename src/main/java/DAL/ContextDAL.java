package DAL;

import java.sql.Connection;

public class ContextDAL {
    private final Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public ContextDAL() {
        connection = Context.getInstance().getConnection();
    }
}
