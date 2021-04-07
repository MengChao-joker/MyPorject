package dao;

import models.UserInfo;
import utils.DBUtils;
import utils.ResultJSONUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//userinfo 的增删改查
public class UserInfoDao {
    public boolean add(UserInfo userInfo) throws SQLException {
        boolean res = false;
        //正常的参数
        if(userInfo.getUsename()!=null&&userInfo.getPassword()!=null){
            Connection connection = DBUtils.getConnect();
            String sql = "insert into userinfo(username,password) values(?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userInfo.getUsename());
            statement.setString(2,userInfo.getPassword());
            res = statement.executeUpdate()>=1?true:false;
            //关闭连接
            DBUtils.close(connection,statement,null);
        }
        System.out.println(res);
        return res;
    }
    //登录验证
    public boolean isLogin(UserInfo userInfo) throws SQLException {
        boolean result = false;
        if(userInfo.getUsename()!=null&&
        userInfo.getPassword()!=null&&
        !userInfo.getPassword().equals("")&&
        !userInfo.getUsename().equals("")){
            Connection connection = DBUtils.getConnect();
            String sql = "select * from myblog where username=?"+" and password=? and state=1";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userInfo.getUsename());
            statement.setString(2,userInfo.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = true;
            }
            DBUtils.close(connection,statement,resultSet);
        }
        return result;
    }

    public UserInfo getUserInfo(UserInfo userInfo) throws SQLException {
        UserInfo user = new UserInfo();
        //非空校验
        if(userInfo.getUsename()==null||userInfo.getPassword()==null){
            return null;
        }else {
            Connection connection = DBUtils.getConnect();
            String sql = "select * from userinfo where username=?" + "and password=? and state=1";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userInfo.getUsename());
            statement.setString(2, userInfo.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //获取并设置id
                user.setId(resultSet.getInt("id"));
                user.setUsename(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
            DBUtils.close(connection, statement, resultSet);
            return user;
        }
    }
}
