package dao;

import models.ArticleInfo;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleInfoDao {
    public List<ArticleInfo> getMyArtList(int uid) throws SQLException {
        List<ArticleInfo> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from articleinfo where uid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setId(resultSet.getInt("id"));
            articleInfo.setTitle(resultSet.getString("title"));
            articleInfo.setText(resultSet.getString("content"));
            articleInfo.setCreatetime(resultSet.getDate("createtime"));
            articleInfo.setUpdatetime(resultSet.getDate("updatetime"));
            articleInfo.setRcount(resultSet.getInt("rcount"));
            list.add(articleInfo);
        }
        return list;
    }

    public int delArticleByID(int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "delete from articleinfo where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        result = statement.executeUpdate()>0?1:0;
        return result;
    }

    public int addArticle(String title, String content, int uid) throws SQLException {
        int result = 0;
        Connection connection = DBUtils.getConnect();
        String sql = "insert into articleinfo(title,content,uid) values(?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,title);
        statement.setString(2,content);
        statement.setInt(3,uid);
        result = statement.executeUpdate();
        // 关闭数据库！！！！！！
        DBUtils.close(connection,statement,null);
        return result;
    }
//更新修改操作
    public int upArticle(String title, String content, int id, int uid) throws SQLException {
            int result = 0;
            Connection connection = DBUtils.getConnect();
            String sql = "update articleinfo set " +
                    "title=?,content=? where id=? and uid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,title);
            statement.setString(2,content);
            statement.setInt(3,id);
            statement.setInt(4,uid);
            result = statement.executeUpdate();
            DBUtils.close(connection,statement,null);
            return result;
    }
    //修改展示操作
    public ArticleInfo showArticle(int id) throws SQLException {
        ArticleInfo articleInfo = new ArticleInfo();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from articleinfo where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            articleInfo.setTitle(resultSet.getString("title"));
            articleInfo.setText(resultSet.getString("content"));
        }
        DBUtils.close(connection,statement,resultSet);
        return articleInfo;
    }
}
