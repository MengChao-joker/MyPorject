package utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    private static MysqlDataSource dataSource =null;//获取数据源
    public static Connection getConnect() throws SQLException {
        //获取数据库连接
        if(dataSource==null) {
            //首次调用，先初始化
            dataSource = new MysqlDataSource();
            dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/blog?charactionEcoding=utf-8&useSSL=true");
            dataSource.setUser("root");
            dataSource.setPassword("052499");
        }
        return dataSource.getConnection();
    }
    
    public static void close(Connection connection, 
                      PreparedStatement statement, ResultSet resultSet) throws SQLException {
        if(resultSet!=null){
            resultSet.close();
        }
        if(statement!=null){
            statement.close();
        }
        if(connection!=null){
            connection.close();
        }    
    } 
}
