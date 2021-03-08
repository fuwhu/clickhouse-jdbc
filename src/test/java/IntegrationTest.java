import ru.yandex.clickhouse.ClickHouseDriver;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationTest {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "ru.yandex.clickhouse.ClickHouseDriver";
    static final String DB_URL = "jdbc:clickhouse://localhost:8123/system";

    //  Database credentials
    static final String USER = "default";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT count() as cnt from system.clusters";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int cnt  = rs.getInt("cnt");
                System.out.println("Cnt: " + cnt);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally{
            //finally block used to close resources
            try {
                if(stmt!=null)
                    stmt.close();
            } catch(SQLException ignored) {
            }// nothing we can do
            try {
                if(conn!=null)
                    conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}
