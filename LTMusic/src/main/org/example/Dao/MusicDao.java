package org.example.Dao;

import org.example.Utils.DBUtils;
import org.example.models.Music;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDao {
    //查询所有歌曲信息
    public List<Music> findMusic() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Music> musicList = new ArrayList<>();

        try {
            connection = DBUtils.getConnection();
            String sql = "select * from music";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Music music = new Music();
                DBUtils.newInstance(music, rs);
                musicList.add(music);
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取音乐资源异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return musicList;
    }

    //通过id查询一首歌曲
    public Music findMusicByID(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Music music = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from music where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                music = new Music();
                DBUtils.newInstance(music, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询一首音乐异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return music;
    }

    //通过关键字进行模糊查询
    public List<Music> fuzzySearch(String key) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Music> musicList = new ArrayList<>();
        key = "%" + key + "%";

        try {
            connection = DBUtils.getConnection();
            String sql = "select * from music where title like ? or singer like ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, key);
            ps.setString(2, key);
            rs = ps.executeQuery();
            while (rs.next()) {
                Music music = new Music();
                DBUtils.newInstance(music, rs);
                musicList.add(music);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return musicList;
    }

    /**
     * 返回值大于0则插入成功
     *
     * @param music
     * @return
     */
    public int insertMusic(Music music) {
        //上传音乐
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "insert into music(title,singer,upload_time,url,uid) values(?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, music.getTitle());
            ps.setString(2, music.getSinger());
            ps.setString(3, music.getUpload_time());
            ps.setString(4, music.getUrl());
            ps.setInt(5, music.getUid());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("音乐上传异常！" + e);
        } finally {
            DBUtils.close(connection, ps);
        }
    }


    public boolean deleteMusicById(int id, Music music) {
        //删除音乐
        Connection connection = null;
        PreparedStatement ps = null;
        if (music != null) {
            try {
                connection = DBUtils.getConnection();
                connection.setAutoCommit(false);//开启事务
                String sql = "delete from music where id = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                if (ps.executeUpdate() == 1) {
                    //删除库中的音乐，继续删除用户喜欢的音乐（资源下架了）
                    if (findLoveMusicOnDel(id)) {//找到被收藏的音乐，存在即删除
                        removeLoveMusicOnDel(id);
                    }
                    //删除服务器本地资源
                    File file = new File("F:\\MyProject\\MyPorject\\LTMusic\\src\\main\\webapp" + music.getUrl() + ".mp3");
                    if (file.delete() || !file.exists()) {
                        connection.commit();//数据库和本地资源均删除成功
                        return true;
                    } else {//本地资源删除失败，开始回滚
                        connection.rollback();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("删除音乐异常！" + e);
            } finally {
                DBUtils.close(connection, ps);
            }
        }
        return false;
    }

    public boolean deleteMusicById(String[] values) {
        //删除音乐
        Connection connection = null;
        PreparedStatement ps = null;
        if (values != null) {
            try {
                connection = DBUtils.getConnection();
                connection.setAutoCommit(false);//开启事务
                for (String value : values) {//循环删除被选中音乐资源
                    int id = Integer.parseInt(value);
                    Music music = findMusicByID(id);
                    if (music != null) {//查询数据库，没有则不跳过
                        String sql = "delete from music where id = ?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, id);
                        if (ps.executeUpdate() == 1) {
                            //删除库中的音乐，继续删除用户喜欢的音乐（资源下架了）
                            if (findLoveMusicOnDel(id)) {//找到被收藏的音乐，存在即删除
                                removeLoveMusicOnDel(id);
                            }
                            //删除服务器本地资源
                            File file = new File("F:\\MyProject\\MyPorject\\LTMusic\\src\\main\\webapp" + music.getUrl() + ".mp3");
                            if (!file.delete() || file.exists()) {
                                connection.rollback();//本地资源删除失败，开始回滚
                            }
                        }
                    }
                }
                connection.commit();
                return true;
            } catch (Exception e) {
                throw new RuntimeException("删除选中音乐异常！" + e);
            } finally {
                DBUtils.close(connection, ps);
            }
        }
        return false;
    }

    private int removeLoveMusicOnDel(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int ret = 0;
        try {
            connection = DBUtils.getConnection();
            String sql = "delete from love where music_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ret = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("移除用户喜爱音乐异常！" + e);
        } finally {
            DBUtils.close(connection, ps);
        }
        return ret;
    }

    private boolean findLoveMusicOnDel(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            String sql = "select * from love where music_id =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("查找喜爱音乐异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return false;
    }

//    public Music getMusic(String )

    /*public static void main(String[] args) {
//        System.out.println(fuzzySearch("不易"));;

//        System.out.println(fuzzySearch("盛"));
//        for (Music music:findMusic()){
//            System.out.println(music);
//        }
        Music mv = new Music();
        mv.setTitle("芬芳一生");
        mv.setSinger("毛不易");
        mv.setUpload_time("2021_5_29");
        mv.setUrl("/Music");
        mv.setUid(2);
        System.out.println(insertMusic(mv));
//        System.out.println("id为1："+findMusicByID(1));
//        System.out.println("查询所有："+findMusic());
//        System.out.println("模糊查询："+fuzzySearch("不易"));
//        System.out.println("查询喜欢："+findLoveMusicOnDel(2));
//        System.out.println(deleteMusicById(2));
    }*/
}
