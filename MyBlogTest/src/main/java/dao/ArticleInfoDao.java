package dao;

import models.ArticleInfo;
import models.ArticleInfoVO;
import models.Favorite;
import utils.DBUtils;
import utils.RespUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleInfoDao {
    //获取个人文章列表
    public List<ArticleInfo> getArtList(int uid, int curpage, int psize) throws SQLException {
        List<ArticleInfo> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from articleinfo where uid = ? limit ?,?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, uid);
        statement.setInt(2, (curpage - 1) * psize);
        statement.setInt(3, psize);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setId(resultSet.getInt("id"));
            articleInfo.setTitle(resultSet.getString("title"));
            articleInfo.setCreatetime(resultSet.getDate("createtime"));
            list.add(articleInfo);
        }
        DBUtils.close(connection, statement, resultSet);
        return list;
    }

    //共享文章列表
    public List<ArticleInfoVO> getAllArtList(int curpage, int psize) throws SQLException {
        List<ArticleInfoVO> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select a.*,u.username from articleinfo a left join userinfo u on a.uid=u.id limit ?,?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, (curpage - 1) * psize);
        statement.setInt(2, psize);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleInfoVO articleInfoVO = new ArticleInfoVO();
            articleInfoVO.setUsername(resultSet.getString("username"));
            articleInfoVO.setId(resultSet.getInt("id"));
            articleInfoVO.setTitle(resultSet.getString("title"));
            articleInfoVO.setCreatetime(resultSet.getDate("createtime"));
            list.add(articleInfoVO);
        }
        DBUtils.close(connection, statement, resultSet);
        return list;
    }

    //获取个人文章详情
    public ArticleInfoVO getArtDetail(int id) throws SQLException {
        Connection connection = DBUtils.getConnect();
        String sql = "select a.*,u.username from articleinfo a left join userinfo u " +
                "on a.uid=u.id where a.id =?";
        String sql2 = "update articleinfo set rcount = rcount+1 where id =?";
        PreparedStatement statement = connection.prepareStatement(sql);
        PreparedStatement statement1 = connection.prepareStatement(sql2);
        statement.setInt(1, id);
        statement1.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        statement1.executeUpdate();
        ArticleInfoVO articleInfoVO = new ArticleInfoVO();
        if (resultSet.next()) {
            articleInfoVO.setUsername(resultSet.getString("username"));
            articleInfoVO.setTitle(resultSet.getString("title"));
            articleInfoVO.setContent(resultSet.getString("content"));
            articleInfoVO.setCreatetime(resultSet.getDate("createtime"));
            articleInfoVO.setRcount(resultSet.getInt("rcount"));
        }
        DBUtils.close(connection, statement, resultSet);
        return articleInfoVO;
    }

    public int upArticle(String title, String content, int id, int uid) throws SQLException {
        Connection connection = DBUtils.getConnect();
        String sql = "update articleinfo set title=? ,content=? where uid = ? and id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, uid);
        statement.setInt(4, id);
        int result = statement.executeUpdate();
        DBUtils.close(connection, statement, null);
        return result;
    }

    //删除文章
    public int delArt(int id, int uid) throws SQLException {
        Connection connection = DBUtils.getConnect();
        String sql = "delete from articleinfo where id = ? and uid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setInt(2, uid);
        int result = statement.executeUpdate();
        DBUtils.close(connection, statement, null);
        return result;
    }

    //添加文章
    public int addArt(String title, String content, int uid) throws SQLException {
        Connection connection = DBUtils.getConnect();
        String sql = "insert into articleinfo(title,content,uid,rcount) values(?,?,?,1)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, uid);
        int result = statement.executeUpdate();
        DBUtils.close(connection, statement, null);
        return result;
    }

    public int logOut(int uid) throws SQLException {
        Connection connection = DBUtils.getConnect();
        String sql = "delete from articleinfo where uid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        int result = 0;
        result = statement.executeUpdate();
        DBUtils.close(connection,statement,null);
        return result;
    }

    public String getUserName(int uid) throws SQLException {
        String name = "";
        Connection connection = DBUtils.getConnect();
        String sql = "select username from userinfo where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            name = resultSet.getString("username");
        }
        return name;
    }

    public List<Favorite> getFavoList(int uid) throws SQLException {
        List<Favorite> list = new ArrayList<>();
        Connection connection = DBUtils.getConnect();
        String sql = "select * from favorite where uid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            Favorite favorite = new Favorite();
            favorite.setUsername(resultSet.getString("username"));
            favorite.setTitle(resultSet.getString("title"));
            favorite.setCreatetime(resultSet.getDate("createtime"));
            list.add(favorite);
        }
        return list;
    }
}

