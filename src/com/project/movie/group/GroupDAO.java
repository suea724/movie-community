package com.project.movie.group;

import com.project.movie.DBUtil;
import com.project.movie.dto.PostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupDAO {

    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    public GroupDAO() {
        conn = DBUtil.open();
    }

    public List<String> taglist() {
        try {

            String sql = "select hashtag from tblHashTag";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            ArrayList<String> list = new ArrayList<String>();

            while (rs.next()) {
                list.add(rs.getString("hashtag"));
            }

            return list;


        } catch (Exception e) {
            System.out.println("GroupDAO.taglist");
            e.printStackTrace();
        }

        return null;
    }

    public int add(PostDTO dto) {

        try {
            String sql = "insert into tblpost values (seqPost.nextVal, ?, ?, default, default, default, default, 3, ?)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getId());

            return pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("GroupDAO.add");
            e.printStackTrace();
        }

        return 0;



    }

    public int addPostGroup(String maxSeq, String group) {

        try {

            String sql = "insert into tblpostgroup values (seqpostgroup.nextVal, ?, ?)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, maxSeq);
            pstat.setString(2, group);

            return pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("GroupDAO.addPostGroup");
            e.printStackTrace();
        }

        return 0;
    }

    public String maxSeq() {

        try {

            String sql = "select max(seq) as seq from tblPost";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                return rs.getString("seq");
            }



        } catch (Exception e) {
            System.out.println("GroupDAO.maxSeq");
            e.printStackTrace();
        }

        return null;
    }

    public String getHashTagSeq(String tag) {

        try {

            String sql = "select seq from tblHashTag where hashtag = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, tag);

            rs = pstat.executeQuery();

            if (rs.next()) {
                return rs.getString("seq");
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.getHashTagSeq");
            e.printStackTrace();
        }

        return null;
    }

    public void addTagging(String maxSeq, String hseq) {

        try {

            String sql = "insert into tblposthash values(seqposthash.nextVal, ?, ?)";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, maxSeq);
            pstat.setString(2, hseq);

            pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("GroupDAO.addTagging");
            e.printStackTrace();
        }

    }

    public List<PostDTO> groupList(Map<String, String> map) {

        try {

            String where = "";
            String sql = "";

            if (map.get("tag") == null) {
                if (map.get("isSearch").equals("y")) {
                    where = String.format("and gp.%s like '%%%s%%'", map.get("column"), map.get("word"));
                }

                sql = String.format("select * from (select gp.*, rownum as rnum from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq where pg.gseq = %s %s) where rnum between %s and %s", map.get("group"), where, map.get("begin"), map.get("end"));
            } else {
                sql = String.format("select * from (select gp.*, rownum as rnum from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq inner join tblPostHash ph on gp.seq = ph.pseq inner join tblHashTag ht on ph.hseq = ht.seq where pg.gseq = %s and ht.hashtag = %s) where rnum between %s and %s", map.get("group"), map.get("tag"), map.get("begin"), map.get("end"));

            }

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            List<PostDTO> list = new ArrayList<>();

            while (rs.next()) {
                PostDTO dto = new PostDTO();
                dto.setNickname(rs.getString("nickname"));
                dto.setSeq(rs.getString("seq"));
                dto.setTitle(rs.getString("title"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setCommentcount(rs.getString("commentcount"));


                list.add(dto);
            }

            return list;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupList");
            e.printStackTrace();
        }

        return null;
    }

    public int getTotalCount(Map<String, String> map) {

        try {

            String where = "";
            String sql = "";

            if (map.get("tag") == null) {
                if (map.get("isSearch").equals("y")) {
                    where = String.format("and gp.%s like '%%%s%%'", map.get("column"), map.get("word"));
                }

                sql = String.format("select count(*) as count from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq where pg.gseq = %s %s", map.get("group"), where);
            } else {
                sql = sql = String.format("select count(*) as count from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq inner join tblPostHash ph on gp.seq = ph.pseq inner join tblHashTag ht on ph.hseq = ht.seq where pg.gseq = %s and ht.hashtag = %s", map.get("group"), map.get("tag"));
            }

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                return Integer.parseInt(rs.getString("count"));
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.getTotalCount");
            e.printStackTrace();
        }

        return 0;
    }

    public int idCheck(String group, String id) {

        try {

            String sql = "select count(*) as cnt from tblusergroup where id = ? and gseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, group);

            rs = pstat.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }


        } catch (Exception e) {
            System.out.println("GroupDAO.idCheck");
            e.printStackTrace();
        }


        return 0;
    }

    public PostDTO GroupView(String seq) {

        try {

            String sql = "select p.*, (select nickname from tblUser where id = p.id) as nickname from tblPost p where p.seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            PostDTO dto = new PostDTO();

            if (rs.next()) {
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setGood(rs.getString("good"));
                dto.setBad(rs.getString("bad"));

                return dto;
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.GroupView");
            e.printStackTrace();
        }

        return null;
    }

    public void updateReadcount(String seq) {

        try {

            String sql = "update tblPost set readcount = readcount + 1 where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("GroupDAO.updateReadcount");
            e.printStackTrace();
        }

    }

    public List<String> GroupViewTag(String seq) {

        try {

            String sql = "select ph.*, (select hashtag from tblHashTag where seq = ph.hseq) as tag from tblposthash ph where ph.pseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            List<String> list = new ArrayList<>();

            while (rs.next()) {
                list.add(rs.getString("tag"));
            }

            return list;



        } catch (Exception e) {
            System.out.println("GroupDAO.GroupViewTag");
            e.printStackTrace();
        }

        return null;
    }
}
