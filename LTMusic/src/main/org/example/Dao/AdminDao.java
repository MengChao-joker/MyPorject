package org.example.Dao;

import org.example.Utils.DBUtils;
import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    //登录校验
    public User login(String username, String password) {
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            String sql = "select * from admin where username=? and password=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAge(rs.getInt("age"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("登陆信息查询异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return user;
    }

    //注册校验
    public boolean reg(User user) {
        if(findUser(user)){
            return false;
        }
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "insert into admin(username,password) values(?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("用户注册异常！"+e);
        } finally {
            DBUtils.close(connection,ps);
        }
    }

    //判断注册用户是否已存在
    public boolean findUser(User user) {
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from admin where username = ? and password = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("查询用户异常" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }

    }

    //注销用户验证
    public boolean logout(String username){
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "select * from admin where username = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            if(ps.executeQuery()!=null){
                String sql2 = "delete from admin where username = ?";
                ps = connection.prepareStatement(sql2);
                ps.setString(1,username);
                return ps.executeUpdate()>0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtils.close(connection,ps);
        }
        return false;//用户不存在
    }

    /*public static void main(String[] args) {
        System.out.println(reg("jmc", "0524"));
//        System.out.println(login("jmc", "0524"));
//        System.out.println(findUser("jmc"));
//        System.out.println(logout("jmc"));
    }*/
}
