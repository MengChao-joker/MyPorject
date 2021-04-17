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

public class MVDao {
    //普通用户
    //查询所有mv信息
    public List<MV> findMV() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MV> mvList = new ArrayList<>();

        try {
            connection = DBUtils.getConnection();
            String sql = "select * from mv";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MV mv = new MV();
                DBUtils.newInstance(mv, rs);
                mvList.add(mv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取音乐资源异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return mvList;
    }

    //通过id查询一首歌曲
    public MV findMVByID(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MV mv = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from MV where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                mv = new MV();
                DBUtils.newInstance(mv, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询一首音乐异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return mv;
    }

    //通过关键字进行模糊查询
    public List<MV> fuzzySearch(String key) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MV> mvList = new ArrayList<>();
        key = "%" + key + "%";
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from MV where title like ? or singer like ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, key);
            ps.setString(2, key);
            rs = ps.executeQuery();
            while (rs.next()) {
                MV mv = new MV();
                DBUtils.newInstance(mv, rs);
                mvList.add(mv);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return mvList;
    }

    /**
     * 返回值大于0则插入成功
     *
     * @param music
     * @return
     */
    public int insertMV(MV mv) {
        //上传音乐
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "insert into mv(title,singer,upload_time,url,uid) values(?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, mv.getTitle());
            ps.setString(2, mv.getSinger());
            ps.setString(3, mv.getUpload_time());
            ps.setString(4, mv.getUrl());
            ps.setInt(5, mv.getUid());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("mv上传异常！" + e);
        } finally {
            DBUtils.close(connection, ps);
        }
    }


    /**
     * 用户只能删除自己的MV资源
     *
     * @param id
     * @return
     */
    public int deleteMVById(int id) {
        //删除音乐
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        String sql = "delete from mv where id = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                //删除库中的音乐，继续删除用户喜欢的音乐（资源下架了）
                if (findLoveMVOnDel(id)) {
                    return removeLoveMVOnDel(id) > 0 ? 1 : 0;
                }
                return 1;//没有用户喜欢此歌
            }
        } catch (SQLException e) {
            throw new RuntimeException("删除mv异常！" + e);
        }
        return 0;
    }

    private int removeLoveMVOnDel(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int ret = 0;
        try {
            connection = DBUtils.getConnection();
            String sql = "delete from love where mv_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ret = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("移除用户喜爱mv异常！" + e);
        } finally {
            DBUtils.close(connection, ps);
        }
        return ret;
    }

    private boolean findLoveMVOnDel(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            String sql = "select * from love where mv_id =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("查找喜爱mv异常！" + e);
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return false;
    }

    /*public void main(String[] args) {
        MV mv = new MV();
        mv.setTitle("消愁");
        mv.setSinger("毛不易");
        mv.setUpload_time("2021_5_29");
        mv.setUrl("/video");
        mv.setUid(2);
        System.out.println(insertMV(mv));

        System.out.println(findMVByID(2));
        System.out.println(findMV());
        System.out.println(fuzzySearch("愁"));
        System.out.println(deleteMVById(2));
        System.out.println(removeLoveMVOnDel(6));
        System.out.println(findLoveMVOnDel(1));
    }*/
}

