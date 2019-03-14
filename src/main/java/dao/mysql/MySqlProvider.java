package dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mihail on 11/9/18.
 */
public class MySqlProvider {

    private static Connection connection;

    public static final String dbUSER = "root";
    public static final String dbPASSWORD = "xxxxxxxx";
    public static final String BASEURL = "jdbc:mysql://localhost:3306/nicofog?useUnicode=yes&characterEncoding=UTF-8";

    protected static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(BASEURL, dbUSER, dbPASSWORD);

            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
            } catch (SQLException e) {
                System.out.println("connection error");
            }
        }
        return connection;
    }
}
