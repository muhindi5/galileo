
/**
 * Provide database connection objects to servlets.
 */

package model.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProcessPipe;

/**
 *
 * @author Kelli
 */
public class DbConnection {
    
    /* database connection parameters */
    private static String connectionUrl = "jdbc:mysql://localhost:3306/ptrack";
    private static String user ="dbadmin";
    private static String pass ="12GreenEyes34+";
    /* driver class  name */
    private static final String driver = "com.mysql.jdbc.Driver";
    
    /* create connection to database and return the object */
    public static Connection getConnection(){
        Connection connection =null;
                try {
            Class.forName(driver);
            connection = DriverManager.getConnection(connectionUrl,user,pass);
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.FINE, "Connection created");
        } catch (SQLException   | ClassNotFoundException ex) {
            Logger.getLogger(ProcessPipe.class.getName()).log(Level.SEVERE, null, ex);
        }
                return connection;
    }
    
}
