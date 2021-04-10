package dao;

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
        int result = -1;
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
}
