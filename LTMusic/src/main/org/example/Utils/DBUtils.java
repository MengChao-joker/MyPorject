package org.example.Utils;





import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.models.MV;
import org.example.models.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    //数据库操作：获取数据库连接；关闭数据库
    private static volatile MysqlDataSource dataSource= null;
    private static final String username = "root";
    private static final String password = "052499";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/ltmusic?" +
            "characterEncoding=UTF-8&mysqlEncoding=UTF-8";
    private static MysqlDataSource getDataSource(){//懒汉单例模式
        if (dataSource==null) {
            //双重校验保证程序效率且防止指令重排序
            /**
             * volatile
             * 1.防止操作系统优化，在寄存器中读取dataSource,降低效率
             * 2.防止指令重排序，new实例：a.分配内存空间
             *                        b.初始化
             *                        c.将此引用执行实例
             *       (指令重排序可能会导致此顺序变为a,c,b，从而导致返回了一个空对象)
             */
            synchronized (Object.class) {
                //加锁保证多线程安全
                if(dataSource==null){
                    dataSource = new MysqlDataSource();
                    dataSource.setUser(username);
                    dataSource.setPassword(password);
                    dataSource.setUrl(url);
                    dataSource.setUseSSL(false);
                    dataSource.setCharacterEncoding("UTF-8");
                }
            }
        }
        return dataSource;
    }
    public static Connection getConnection() {
        //获取数据库连接
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败！"+e);
        }
    }

    public static void close(Connection c, PreparedStatement ps, ResultSet rs){
        try {
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            if(c!=null) c.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭数据库失败！"+e);
        }
    }
    public static void close(Connection c,PreparedStatement ps){
        close(c,ps,null);
    }
    public static void newInstance(Music music,ResultSet rs) throws SQLException {
        //从结果集中获取一个实例的相关属性，并设置相关属性创建一个实例
        music.setId(rs.getInt("id"));
        music.setTitle(rs.getString("title"));
        music.setSinger(rs.getString("singer"));
        music.setUpload_time(rs.getString("upload_time"));
        music.setUid(rs.getInt("uid"));
        music.setUrl(rs.getString("url"));
    }
    public static void newInstance(MV mv,ResultSet rs) throws SQLException {
        //从结果集中获取一个实例的相关属性，并设置相关属性创建一个实例
        mv.setId(rs.getInt("id"));
        mv.setTitle(rs.getString("title"));
        mv.setSinger(rs.getString("singer"));
        mv.setUpload_time(rs.getString("upload_time"));
        mv.setUid(rs.getInt("uid"));
        mv.setUrl(rs.getString("url"));
    }


}
