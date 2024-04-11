import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    static final String URL = "jdbc:postgresql://localhost:5432/Westminster Kennel Club";
    static final String USER = "postgres";
    static final String PASS = "Celina$957";
 
public static Connection crearConexion() throws ClassNotFoundException, SQLException{
    Class.forName("org.postgresql.Driver");
    Connection conexion = DriverManager.getConnection(URL, USER, PASS);
    if (conexion != null){
        return conexion;
    }
    return null;
    }
}
