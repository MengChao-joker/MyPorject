package dao;

import models.Favorite;
import models.UserInfo;
import sun.security.pkcs11.Secmod;
import utils.DBUtils;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao {
    public int reg(UserInfo userInfo) throws SQLException {
        int result = 0;
        //获取连接-》数据库声明—》操作数据库-》返回结果给service层
        Connection connection = DBUtils.getConnect();
        String sql = "insert into userinfo(username,password) values(?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,userInfo.getUsername());
        statement.setString(2,userInfo.getPassword());
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    public UserInfo isLogin(UserInfo userInfo) throws SQLException {
        UserInfo result = new UserInfo();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from userinfo where username=? and password=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,userInfo.getUsername());
        statement.setString(2,userInfo.getPassword());
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
//            result.setUsername(resultSet.getString("username"));
//            result.setPassword(resultSet.getString("password"));
            result.setId(resultSet.getInt("id"));
        }
        DBUtils.close(connection,statement,resultSet);
        return result;
    }
//用户注销
    public int logOut(int uid) throws SQLException {
        int result = -1;
        Connection connection = DBUtils.getConnect();
        String sql = "delete from userinfo where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }
//判断用户重名
    public int isRepeat(UserInfo userInfo) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "select * from userinfo where username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,userInfo.getUsername());
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            result = -1;
        }
        DBUtils.close(connection,statement,resultSet);
        return result;
    }

    public int favorite(Favorite favorite,int uid,int id) throws SQLException {
        int result = -1;
        //获取数据库连接
        Connection connection = DBUtils.getConnect();
        String sql1 = "select * from favorite where uid = ? and id = ?";
        String sql = "insert into favorite(id,title,content,username,uid) values(?,?,?,?,?)";
        // 获取声明
        PreparedStatement statement = connection.prepareStatement(sql);
        PreparedStatement statement1 = connection.prepareStatement(sql1);
        statement1.setInt(1,uid);
        statement1.setInt(2,id);
        statement.setInt(1,id);
        statement.setString(2,favorite.getTitle());
        statement.setString(3,favorite.getContent());
        statement.setString(4,favorite.getUsername());
        statement.setInt(5,uid);
        //执行语句于数据库
        ResultSet resultSet = statement1.executeQuery();
        if(!resultSet.next()) {
            result = statement.executeUpdate();
        }
        DBUtils.close(connection, statement1, statement, resultSet);
        return result;
    }
}
