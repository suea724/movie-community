package com.project.movie.main;

import com.project.movie.DBUtil;
import com.project.movie.dto.CommentDTO;
import com.project.movie.dto.PostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MainDAO {
    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    public MainDAO(){
        conn = DBUtil.open();
    }
    public ArrayList<String> taglist() {

        try {

            String sql = "select hashtag from tblHashTag order by hashtag asc";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            ArrayList<String> list = new ArrayList<String>();

            while (rs.next()) {
                list.add(rs.getString("hashtag"));
            }

            return list;


        } catch (Exception e) {
            System.out.println("BoardDAO.taglist");
            e.printStackTrace();
        }

        return null;
    }

    public int add(PostDTO dto) {
        try{
            String sql = "insert into tblPost (seq, title, content, regdate, readcount, good, bad, type, id) " +
                    "values (seqPost.nextVal, ?, ?, default, default, default, default, ?, ?)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getType());
            pstat.setString(4, dto.getId());

            return pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMaxSeq() {
        try{
            String sql = "select max(seq) as maxseq from tblPost";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            if(rs.next()) {
                return rs.getInt("maxseq");
            }
        } catch (Exception e){

        }

        return 0;
    }

    public String getHashTagSeq(String tag) {
        try {
            String sql = "select seq from tblHashTag where hashtag = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, tag);

            rs = pstat.executeQuery();

            if(rs.next()) {
                return rs.getString("seq");
            }



        } catch (Exception e) {

        }

        return null;
    }

    public void addHashTag(String hseq, int maxSeq) {
        try {
            String sql = "insert into tblPostHash (seq, pseq, hseq) values (seqPostHash.nextVal, ?, ?)";

            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, maxSeq);
            pstat.setString(2, hseq);
            pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PostDTO> list(HashMap<String, String> map) {
        try {
            String where = "";
            String sql = "";

            if(map.get("isSearch").equals("y")) {
                where = String.format("where %s like '%%%s%%'", map.get("column"), map.get("word"));
            }

            if(map.get("type").equals("0")) {

                sql = String.format("select * from (select a.*, rownum as rnum from vwMain a %s) where rnum between %s and %s",
                        where, map.get("begin"), map.get("end"));
            } else if (map.get("type").equals("1")) {
                if(where.equals("")) {

                    sql = String.format("select * from (select a.*, rownum as rnum from vwMain a %s where type = 1) where rnum between %s and %s",
                            where, map.get("begin"), map.get("end"));
                } else {
                    sql = String.format("select * from (select a.*, rownum as rnum from vwMain a %s and type = 1) where rnum between %s and %s",
                            where, map.get("begin"), map.get("end"));
                }
            } else if (map.get("type").equals("2")) {
                if(where.equals("")) {

                    sql = String.format("select * from (select a.*, rownum as rnum from vwMain a %s where type = 2) where rnum between %s and %s",
                            where, map.get("begin"), map.get("end"));
                } else {
                    sql = String.format("select * from (select a.*, rownum as rnum from vwMain a %s and type = 2) where rnum between %s and %s",
                            where, map.get("begin"), map.get("end"));
                }
            }

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);
            ArrayList<PostDTO> list = new ArrayList<PostDTO>();

            while (rs.next()) {
                PostDTO dto = new PostDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setTitle(rs.getString("title"));
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setCommentcount(rs.getString("commentcount"));

                list.add(dto);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalCount(HashMap<String, String> map) {
        try{
            String where = "";

            if(map.get("isSearch").equals("y")) {
                where = String.format("where %s like '%%%s%%'", map.get("column"), map.get("world"));
            }

            String sql = "select count(*) as cnt from vwMain " + where;

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if(rs.next()) {
                return rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public PostDTO getView(PostDTO tempdto) {
        try {
            String sql = "select tblPost.*, (select nickname from tblUser where id = tblPost.id) as nickname from tblPost where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, tempdto.getSeq());

            rs = pstat.executeQuery();

            PostDTO dto = new PostDTO();

            if(rs.next()) {
                dto.setSeq(rs.getString("seq"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcount(rs.getString("readcount"));
            }
            return dto;

        } catch (Exception e) {
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
            e.printStackTrace();
        }
    }

    public ArrayList<String> getHashTag(String seq) {
        try {
            String sql = "select hashtag from tblHashTag where seq = (select hseq from tblPostHash where pseq = ?)";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);
            rs = pstat.executeQuery();

            ArrayList<String> list = new ArrayList<>();

            while(rs.next()) {
                //System.out.println(rs.getString("hashtag"));
                list.add(rs.getString("hashtag"));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delHashTag(String seq) {
        try {
            String sql = "delete from tblPostHash where pseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);
            pstat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delComment(String seq) {
        try{
            String sql = "delete from tblComment where pseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);
            pstat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int delContent(String seq) {
        try {
            String sql = "delete from tblPost where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);
            return pstat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updatePost(PostDTO dto) {
        try {
            String sql = "update tblPost set title = ?, content = ?, type = ? where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getType());
            pstat.setString(4, dto.getSeq());

            return pstat.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int addComment(CommentDTO dto) {
        try {
            String sql = "insert into tblComment (seq, content, regdate, pseq, id) values (seqComment.nextVal, ?, default, ?, ?)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getContent());
            pstat.setString(2, dto.getPseq());
            pstat.setString(3, dto.getId());

            return pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public CommentDTO getComment() {
        try {
            //방금 작성한 댓글 찾기
            String sql = "select tblComment.*, (select nickname from tblUser where id = tblComment.id) as nickname from tblComment where seq = (select max(seq) from tblComment)";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            CommentDTO dto = new CommentDTO();

            if(rs.next()) {
                dto.setSeq(rs.getString("seq"));
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setPseq(rs.getString("pseq"));
                dto.setContent(rs.getString("content"));
            }
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CommentDTO> listComment(String seq) {
        try {
            String sql = "select tblComment.*, (select nickname from tblUser where id = tblComment.id) as nickname from tblComment where pseq = ? order by seq desc";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);
            rs = pstat.executeQuery();

            ArrayList<CommentDTO> clist = new ArrayList<>();

            while(rs.next()) {
                CommentDTO dto = new CommentDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setContent(rs.getString("content"));
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setRegdate(rs.getString("regdate"));

                clist.add(dto);
            }
            //System.out.println(clist);
            return clist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int editComment(CommentDTO dto) {
        try {
            String sql = "update tblComment set content = ? where seq = ?";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getContent());
            pstat.setString(2, dto.getSeq());

            return pstat.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delCommentAjax(String seq) {
        try {
            String sql = "delete from tblComment where seq = ?";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, seq);

            return pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
