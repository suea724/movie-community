package com.project.movie.group;

import com.project.movie.DBUtil;
import com.project.movie.dto.CommentDTO;
import com.project.movie.dto.GroupRequestDTO;
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
            conn = DBUtil.open();

            String sql = "select hashtag from tblHashTag";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            ArrayList<String> list = new ArrayList<String>();

            while (rs.next()) {
                list.add(rs.getString("hashtag"));
            }

            rs.close();
            stat.close();
            conn.close();

            return list;


        } catch (Exception e) {
            System.out.println("GroupDAO.taglist");
            e.printStackTrace();
        }

        return null;
    }

    public int add(PostDTO dto) {

        try {
            conn = DBUtil.open();
            String sql = "insert into tblpost values (seqPost.nextVal, ?, ?, default, default, default, default, 3, ?)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getId());

            int result = pstat.executeUpdate();

            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.add");
            e.printStackTrace();
        }

        return 0;



    }

    public int addPostGroup(String maxSeq, String group) {

        try {
            conn = DBUtil.open();

            String sql = "insert into tblpostgroup values (seqpostgroup.nextVal, ?, ?)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, maxSeq);
            pstat.setString(2, group);

            int result = pstat.executeUpdate();

            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.addPostGroup");
            e.printStackTrace();
        }

        return 0;
    }

    public String maxSeq() {

        try {
            conn = DBUtil.open();

            String sql = "select max(seq) as seq from tblPost";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                String result = rs.getString("seq");
                rs.close();
                stat.close();
                conn.close();
                return result;
            }



        } catch (Exception e) {
            System.out.println("GroupDAO.maxSeq");
            e.printStackTrace();
        }

        return null;
    }

    public String getHashTagSeq(String tag) {

        try {
            conn = DBUtil.open();

            String sql = "select seq from tblHashTag where hashtag = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, tag);

            rs = pstat.executeQuery();

            if (rs.next()) {
                String result = rs.getString("seq");
                rs.close();
                pstat.close();
                conn.close();
                return result;
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.getHashTagSeq");
            e.printStackTrace();
        }

        return null;
    }

    public void addTagging(String maxSeq, String hseq) {

        try {
            conn = DBUtil.open();

            String sql = "insert into tblposthash values(seqposthash.nextVal, ?, ?)";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, maxSeq);
            pstat.setString(2, hseq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.addTagging");
            e.printStackTrace();
        }

    }

    public List<PostDTO> groupList(Map<String, String> map) {

        try {
            conn = DBUtil.open();

            String where = "";
            String sql = "";

            if (map.get("tag") == null) {
                if (map.get("isSearch").equals("y")) {
                    where = String.format("and gp.%s like '%%%s%%'", map.get("column"), map.get("word"));
                }

                sql = String.format("select * from (select gp.*, rownum as rnum from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq where pg.gseq = %s %s) where rnum between %s and %s", map.get("group"), where, map.get("begin"), map.get("end"));
            } else {
                sql = String.format("select * from (select gp.*, rownum as rnum from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq inner join tblPostHash ph on gp.seq = ph.pseq inner join tblHashTag ht on ph.hseq = ht.seq where pg.gseq = %s and ht.hashtag = '%s') where rnum between %s and %s", map.get("group"), map.get("tag"), map.get("begin"), map.get("end"));

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
            rs.close();
            stat.close();
            conn.close();

            return list;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupList");
            e.printStackTrace();
        }

        return null;
    }

    public int getTotalCount(Map<String, String> map) {

        try {
            conn = DBUtil.open();

            String where = "";
            String sql = "";

            if (map.get("tag") == null) {
                if (map.get("isSearch").equals("y")) {
                    where = String.format("and gp.%s like '%%%s%%'", map.get("column"), map.get("word"));
                }

                sql = String.format("select count(*) as count from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq where pg.gseq = %s %s", map.get("group"), where);
            } else {
                sql = sql = String.format("select count(*) as count from vwGroupPost gp inner join tblPostGroup pg on gp.seq = pg.pseq inner join tblPostHash ph on gp.seq = ph.pseq inner join tblHashTag ht on ph.hseq = ht.seq where pg.gseq = %s and ht.hashtag = '%s'", map.get("group"), map.get("tag"));
            }

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                int result = Integer.parseInt(rs.getString("count"));
                rs.close();
                stat.close();
                conn.close();
                return result;
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.getTotalCount");
            e.printStackTrace();
        }

        return 0;
    }

    public int idCheck(String group, String id) {

        try {
            conn = DBUtil.open();

            String sql = "select count(*) as cnt from tblusergroup where id = ? and gseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, group);

            rs = pstat.executeQuery();

            if (rs.next()) {
                int result = Integer.parseInt(rs.getString("cnt"));
                rs.close();
                pstat.close();
                conn.close();
                return result;
            }


        } catch (Exception e) {
            System.out.println("GroupDAO.idCheck");
            e.printStackTrace();
        }


        return 0;
    }

    public PostDTO GroupView(String seq) {

        try {
            conn = DBUtil.open();

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
                dto.setSeq(rs.getString("seq"));

                rs.close();
                pstat.close();
                conn.close();

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
            conn = DBUtil.open();

            String sql = "update tblPost set readcount = readcount + 1 where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.updateReadcount");
            e.printStackTrace();
        }

    }

    public List<String> GroupViewTag(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "select ph.*, (select hashtag from tblHashTag where seq = ph.hseq) as tag from tblposthash ph where ph.pseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            List<String> list = new ArrayList<>();

            while (rs.next()) {
                list.add(rs.getString("tag"));
            }

            rs.close();
            pstat.close();
            conn.close();

            return list;



        } catch (Exception e) {
            System.out.println("GroupDAO.GroupViewTag");
            e.printStackTrace();
        }

        return null;
    }

    public int editCheck(String seq, String id) {

        try {
            conn = DBUtil.open();

            String sql = "select count(*) as cnt from tblPost where id = ? and seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, seq);

            rs = pstat.executeQuery();

            if (rs.next()) {
                int result = Integer.parseInt(rs.getString("cnt"));
                rs.close();
                pstat.close();
                conn.close();
                return result;
            }


        } catch (Exception e) {
            System.out.println("GroupDAO.idCheck");
            e.printStackTrace();
        }

        return 0;
    }

    public int groupEdit(PostDTO dto) {

        try {
            conn = DBUtil.open();

            String sql = "update tblPost set title = ?, content = ? where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getSeq());

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupEdit");
            e.printStackTrace();
        }

        return 0;
    }

    public int deleteHashTag(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblPostHash where pseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.deleteHashTag");
            e.printStackTrace();
        }

        return 0;
    }

    public String getId(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "select * from tblPost where seq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            if (rs.next()) {
                String result = rs.getString("id");
                rs.close();
                pstat.close();
                conn.close();
                return result;
            }


        } catch (Exception e) {
            System.out.println("GroupDAO.getId");
            e.printStackTrace();
        }

        return null;

    }

    public int groupDelete(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblPost where seq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            int result = pstat.executeUpdate();

            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupDelete");
            e.printStackTrace();
        }

        return 0;
    }

    public void groupDelete1(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblPostGroup where pseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.groupDelete1");
            e.printStackTrace();
        }

    }

    public void groupDelete2(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblPostHash where pseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.groupDelete2");
            e.printStackTrace();
        }
    }

    public void groupDelete3(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblComment where pseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.groupDelete3");
            e.printStackTrace();
        }

    }
    public void groupDelete4(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblGoodbad where pseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.groupDelete4");
            e.printStackTrace();
        }
    }

    public List<CommentDTO> getComment(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "select c.*, (select nickname from tblUser where id = c.id) as nickname from tblComment c where pseq = ? order by seq desc";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            List<CommentDTO> clist = new ArrayList<>();

            while (rs.next()) {
                CommentDTO dto = new CommentDTO();
                dto.setNickname(rs.getString("nickname"));
                dto.setContent(rs.getString("content"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setId(rs.getString("id"));
                dto.setSeq(rs.getString("seq"));

                clist.add(dto);
            }
            rs.close();
            pstat.close();
            conn.close();
            return clist;

        } catch (Exception e) {
            System.out.println("GroupDAO.getComment");
            e.printStackTrace();
        }

        return null;
    }

    public int groupAddComment(Map<String, String> map) {

        try {
            conn = DBUtil.open();

            String sql = "insert into tblComment values (seqComment.nextVal, ?, default, ?, ?)";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, map.get("content"));
            pstat.setString(2, map.get("pseq"));
            pstat.setString(3, map.get("id"));

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupAddComment");
            e.printStackTrace();
        }

        return 0;
    }

    public CommentDTO groupGetComment() {

        try {
            conn = DBUtil.open();

            String sql = "select c.*, (select nickname from tblUser where id = c.id) as nickname from tblComment c where seq = (select max(seq) from tblComment)";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            CommentDTO dto = new CommentDTO();

            if (rs.next()) {
                dto.setSeq(rs.getString("seq"));
                dto.setPseq(rs.getString("pseq"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setContent(rs.getString("content"));
                dto.setNickname(rs.getString("nickname"));
                dto.setId(rs.getString("id"));

                rs.close();
                stat.close();
                conn.close();

                return dto;
            }
        } catch (Exception e) {
            System.out.println("GroupDAO.groupGetComment");
            e.printStackTrace();
        }

        return null;

    }

    public int groupEditComment(String seq, String content) {

        try {
            conn = DBUtil.open();

            String sql = "update tblComment set content = ? where seq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, content);
            pstat.setString(2, seq);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupEditComment");
            e.printStackTrace();
        }

        return 0;
    }

    public int groupDelComment(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblComment where seq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.groupDelComment");
            e.printStackTrace();
        }

        return 0;
    }

    public void deleteGoodBad(String seq, String id) {
        try {
            conn = DBUtil.open();

            String sql = "delete from tblGoodBad where id = ? and pseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, seq);

            pstat.executeUpdate();

            pstat.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("GroupDAO.deleteGoodBad");
            e.printStackTrace();
        }
    }

    public int goodCount(String seq, String id) {

        try {
            conn = DBUtil.open();

            String sql = "insert into tblGoodBad values(seqGoodBad.nextVal, ?, ?, 1, 0)";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, seq);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.goodCount");
            e.printStackTrace();
        }

        return 0;
    }

    public int badCount(String seq, String id) {
        try {
            conn = DBUtil.open();

            String sql = "insert into tblGoodBad values(seqGoodBad.nextVal, ?, ?, 0, 1)";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, seq);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.badCount");
            e.printStackTrace();
        }

        return 0;
    }

    public List<Integer> getGoodBad(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "select (select count(*) as good from tblGoodBad where pseq = ? and good = 1) as good, (select count(*) as good from tblGoodBad where pseq = ? and bad = 1) as bad from dual";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);
            pstat.setString(2, seq);

            rs = pstat.executeQuery();
            List<Integer> list = new ArrayList<>();
            if (rs.next()) {
                list.add(Integer.parseInt(rs.getString("good")));
                list.add(Integer.parseInt(rs.getString("bad")));
            }

            rs.close();
            pstat.close();
            conn.close();
            return list;

        } catch (Exception e) {
            System.out.println("GroupDAO.getGoodBad");
            e.printStackTrace();
        }

        return null;
    }


    public String getGroupId(String group) {

        try {
            conn = DBUtil.open();

            String sql = "select id from tblGroup where seq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, group);

            rs = pstat.executeQuery();

            if (rs.next()) {
                String result = rs.getString("id");
                rs.close();
                pstat.close();
                conn.close();
                return result;
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.getGroupId");
            e.printStackTrace();
        }

        return null;
    }

    public List<GroupRequestDTO> getRequest(Map<String, String> map) {

        try {
            conn = DBUtil.open();

            String sql = "select * from (select a.*, rownum as rnum from (select gr.*, (select nickname from tblUser where id = gr.id) as nickname from tblgrouprequest gr order by seq desc) a) where rnum between ? and ? and gseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, map.get("begin"));
            pstat.setString(2, map.get("end"));
            pstat.setString(3, map.get("group"));
            rs = pstat.executeQuery();

            List<GroupRequestDTO> list = new ArrayList<>();

            int i = Integer.parseInt(map.get("begin"));
            while (rs.next()) {
                GroupRequestDTO dto = new GroupRequestDTO();
                dto.setSeq(rs.getString("seq"));
                dto.setId(rs.getString("id"));
                dto.setGseq(rs.getString("gseq"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setNickname(rs.getString("nickname"));
                dto.setNum(String.valueOf(i++));

                list.add(dto);
            }

            rs.close();
            pstat.close();
            conn.close();
            return list;

        } catch (Exception e) {
            System.out.println("GroupDAO.getRequest");
            e.printStackTrace();
        }

        return null;
    }

    public int getRequestCount(Map<String, String> map) {

        try {
            conn = DBUtil.open();

            String sql = "select count(*) as cnt from tblgrouprequest where gseq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, map.get("group"));
            rs = pstat.executeQuery();

            if (rs.next()) {
                int result = Integer.parseInt(rs.getString("cnt"));
                rs.close();
                pstat.close();
                conn.close();
                return result;
            }


        } catch (Exception e) {
            System.out.println("GroupDAO.getRequestCount");
            e.printStackTrace();
        }

        return 0;
    }

    public int deleteRequest(String seq) {

        try {
            conn = DBUtil.open();

            String sql = "delete from tblgrouprequest where seq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();
            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.deleteRequest");
            e.printStackTrace();
        }

        return 0;
    }

    public int addRequest(String group, String id) {

        try {
            conn = DBUtil.open();

            String sql = "insert into tblUserGroup values (seqUserGroup.nextVal, ?, ?)";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);
            pstat.setString(2, group);

            int result = pstat.executeUpdate();
            pstat.close();
            conn.close();

            return result;

        } catch (Exception e) {
            System.out.println("GroupDAO.deleteRequest");
            e.printStackTrace();
        }

        return 0;
    }
}
