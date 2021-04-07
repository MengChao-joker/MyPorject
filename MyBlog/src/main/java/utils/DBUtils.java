package utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtils {
    /*
    * 通用数据库操作类；
    * 1.对外提供connect对象
    * 2.对外提供统一的关闭方法
    *
    * */
    private static MysqlDataSource dataSource = null;
    /*
    * 对外提供统一的connection对象
    * */
    public static Connection getConnect() throws SQLException {
        if(dataSource==null){
            //首次调用，先初始化
            dataSource = new MysqlDataSource();
            dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/myblog?charactionEncoding=utf-8&useSSl=true");
            dataSource.setUser("root");
            dataSource.setPassword("052499");
        }
        return dataSource.getConnection();
    }
    //提供统一的关闭方法
    public static void close(Connection connection,
                             PreparedStatement statement,
                             ResultSet resultSet) throws SQLException {
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
