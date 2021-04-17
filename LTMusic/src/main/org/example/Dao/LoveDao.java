package org.example.Dao;

import org.example.Utils.DBUtils;
import org.example.models.MV;
import org.example.models.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoveDao {

    /**
     * 用户添加喜欢音乐
     * @param user_id
     * @param music_id
     * @return
     */
    public boolean insertLoveMusic(int user_id,int music_id){
        Connection connection = null;
        PreparedStatement ps = null;
        if(!findLoveMusic(user_id,music_id)) {
            try {
                connection = DBUtils.getConnection();
                String sql = "insert into love(type,user_id,music_id) values ('Music',?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, user_id);
                ps.setInt(2, music_id);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("添加喜爱音乐异常！" + e);
            } finally {
                DBUtils.close(connection, ps);
            }
        }else {
            return false;//已添加喜欢
        }
    }

    /**
     * 查找自己喜欢的音乐
     * @param user_id
     * @param music_id
     * @return
     */
    private boolean findLoveMusic(int user_id, int music_id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from love where user_id=? and music_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            ps.setInt(2,music_id);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.close(connection,ps,rs);
        }
    }

    /**
     * 移除喜欢的音乐
     * @param music_id
     * @return
     */
    public boolean removeLoveMusicById(int user_id,int music_id){
        Connection connection = null;
        PreparedStatement ps =null;
        try {
            connection = DBUtils.getConnection();
            String sql = "delete from love where music_id = ? and user_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,music_id);
            ps.setInt(2,user_id);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException("移除用户喜爱音乐异常！"+e);
        } finally {
            DBUtils.close(connection,ps);
        }
    }
    /**
     * 展示喜欢的音乐
     * @param user_id
     * @return
     */
    public List<Music> showLoveMusic(int user_id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Music> musicList = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from music where id in(select music_id from love where user_id= ?);";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            rs = ps.executeQuery();
            while(rs.next()){
                Music music = new Music();
                DBUtils.newInstance(music,rs);
                musicList.add(music);
            }
        } catch (SQLException e) {
            throw new RuntimeException("展示用户喜爱音乐异常"+e);
        } finally {
            DBUtils.close(connection,ps,rs);
        }
        return musicList;
    }

    //通过关键字进行模糊查询
    public List<Music> fuzzySearchMusic(int user_id,String key){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Music> musicList = new ArrayList<>();
        key = "%"+key+"%";

        try {
            connection = DBUtils.getConnection();
            String sql = "select l.music_id as id,title,singer,upload_time,url,uid from love as l,music m " +
                    "where l.music_id=m.id and l.user_id=? and (m.title like ? or m.singer like ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            ps.setString(2,key);
            ps.setString(3,key);
            rs = ps.executeQuery();
            while(rs.next()){
                Music music = new Music();
                DBUtils.newInstance(music,rs);
                musicList.add(music);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtils.close(connection,ps,rs);
        }
        return musicList;
    }




    /**
     * 用户添加喜欢MV
     * @param user_id
     * @param music_id
     * @return
     */
    public boolean insertLoveMV(int user_id,int mv_id){
        Connection connection = null;
        PreparedStatement ps = null;
        if(!findLoveMV(user_id,mv_id)) {
            try {
                connection = DBUtils.getConnection();
                String sql = "insert into love(type,user_id,mv_id) values ('MV',?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, user_id);
                ps.setInt(2, mv_id);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("添加喜爱MV异常！" + e);
            } finally {
                DBUtils.close(connection, ps);
            }
        }else {
            return false;//已添加喜欢
        }
    }

    /**
     * 查找自己喜欢的音乐
     * @param user_id
     * @param music_id
     * @return
     */
    private boolean findLoveMV(int user_id, int mv_id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from love where user_id=? and mv_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            ps.setInt(2,mv_id);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.close(connection,ps,rs);
        }
    }


    public boolean removeLoveMVById(int user_id,int mv_id){
        Connection connection = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "delete from love where mv_id = ? and user_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,mv_id);
            ps.setInt(2,user_id);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException("移除用户喜爱MV异常！"+e);
        } finally {
            DBUtils.close(connection,ps);
        }
    }


    /**
     * 展示喜欢的MV
     * @param user_id
     * @return
     */
    public List<MV> showLoveMV(int user_id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MV> mvList = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from mv where id in(select mv_id from love where user_id= ?);";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            rs = ps.executeQuery();
            while(rs.next()){
                MV mv = new MV();
                DBUtils.newInstance(mv,rs);
                mvList.add(mv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("展示用户喜爱MV异常"+e);
        } finally {
            DBUtils.close(connection,ps,rs);
        }
        return mvList;
    }

    //通过关键字进行模糊查询
    public List<MV> fuzzySearchMV(int user_id,String key){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MV> mvList = new ArrayList<>();
        key = "%"+key+"%";

        try {
            connection = DBUtils.getConnection();
            String sql = "select l.mv_id as id,title,singer,upload_time,url,uid from love as l,mv m " +
                    "where l.mv_id=m.id and l.user_id=? and m.title like ? or m.singer like ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user_id);
            ps.setString(2,key);
            ps.setString(3,key);
            rs = ps.executeQuery();
            while(rs.next()){
                MV mv = new MV();
                DBUtils.newInstance(mv,rs);
                mvList.add(mv);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtils.close(connection,ps,rs);
        }
        return mvList;
    }

    public void main(String[] args) {
        System.out.println(insertLoveMV(1, 1));
        System.out.println(showLoveMV(1));
        System.out.println(fuzzySearchMV(1,"毛"));
//        System.out.println(removeLoveMVById(1,1));
    }


}
