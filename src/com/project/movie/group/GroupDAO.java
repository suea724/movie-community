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
                    where = String.format("and %s like '%%%s%%'", map.get("column"), map.get("word"));
                }

                sql = String.format("select p.*, (select count(*) from tblComment where pseq = p.seq) as count, (select nickname from tblUser where id = p.id) as nickname from tblPost p inner join tblpostgroup pg on p.seq = pg.pseq where pg.gseq = %s %s", map.get("group"), where);
            } else {
                sql = String.format("select p.*, (select count(*) from tblComment where pseq = p.seq) as count, (select nickname from tblUser where id = p.id) as nickname from tblPost p inner join tblPostHash ph on p.seq = ph.pseq inner join tblHashTag ht on ph.hseq = ht.seq inner join tblPostGroup pg on pg.pseq = p.seq where ht.hashtag = %s and pg.gseq = %s", map.get("tag"), map.get("group"));

            }

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            List<PostDTO> list = new ArrayList<>();

            where (rs.next()) {
                PostDTO dto = new PostDTO();
                dto.set
            }

        } catch (Exception e) {
            System.out.println("GroupDAO.groupList");
            e.printStackTrace();
        }

        return null;
    }
}
