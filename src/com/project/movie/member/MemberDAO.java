package com.project.movie.member;

import com.project.movie.DBUtil;
import com.project.movie.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MemberDAO {

    Connection conn;
    PreparedStatement pstmt;
    Statement stmt;
    ResultSet rs;

    public MemberDAO() {
        conn = DBUtil.open();
    }

    public MemberDTO login(LoginDTO dto) {
        try {
            String sql = "select * from tblUser where id = ? and password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getId());
            pstmt.setString(2, dto.getPw());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                MemberDTO mdto = new MemberDTO();

                mdto.setId(rs.getString("id"));
                mdto.setName(rs.getString("name"));
                mdto.setPassword(rs.getString("password"));
                mdto.setNickname(rs.getString("nickname"));
                mdto.setTel(rs.getString("tel"));
                mdto.setPicture(rs.getString("picture"));
                mdto.setJoindate(rs.getString("joindate"));

                return mdto;
            }
            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return null;
    }

    public int checkId(String id) {
        try {
            String sql = "select count(*) as cnt from tblUser where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public int checkNickname(String nickname) {
        try {
            String sql = "select count(*) as cnt from tblUser where nickname = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public int getPostsCount(MemberDTO dto) {
        try {
            String sql = "select count(*) as cnt from tblPost p inner join tblUser u on p.id = u.id where u.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public int getCommentCount(MemberDTO dto) {
        try {
            String sql = "select count(*) as cnt from tblComment c inner join tblUser u on c.id = u.id where u.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public String findId(String name, String tel) {
        try {
            String sql = "select id from tblUser where name = ? and tel = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, tel);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("id");
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return null;
    }

    public int findMemberByPw(String id, String name, String tel) {
        try {
            String sql = "select count(*) as cnt from tblUser where name = ? and tel = ? and id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, tel);
            pstmt.setString(3, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public int updatePw(String id, String pw) {
        try {
            String sql = "update tblUser set password = ? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pw);
            pstmt.setString(2, id);

            pstmt.close();
            rs.close();

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public ArrayList<HashTagDTO> getGenres() {

        try {
            String sql = "select * from tblHashTag";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            ArrayList<HashTagDTO> list = new ArrayList<>();

            while (rs.next()) {
                HashTagDTO dto = new HashTagDTO();
                dto.setSeq(rs.getString("seq"));
                dto.setHashtag(rs.getString("hashtag"));

                list.add(dto);
            }

            stmt.close();
            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }

        return null;
    }

    public int del(String id) {
        try {
            String sql = "update tblUser set name = 'not used', nickname = 'not used', password = 'not used', tel = 'not used', picture = 'not used', joindate = sysdate where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            pstmt.close();
            rs.close();

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public int addMember(MemberDTO dto) {
        try {

            String sql = "";

            if (dto.getPicture() != null) { // 프로필 사진이 있는 경우
                sql = "insert into tblUser(id, name, nickname, password, tel, picture, joindate) values (?, ?, ?, ?, ?, ?, default)";

                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, dto.getId());
                pstmt.setString(2, dto.getName());
                pstmt.setString(3, dto.getNickname());
                pstmt.setString(4, dto.getPassword());
                pstmt.setString(5, dto.getTel());
                pstmt.setString(6, dto.getPicture());

            } else { // 프로필 사진이 없는 경우
                sql = "insert into tblUser(id, name, nickname, password, tel, picture, joindate) values (?, ?, ?, ?, ?, default, default)";

                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, dto.getId());
                pstmt.setString(2, dto.getName());
                pstmt.setString(3, dto.getNickname());
                pstmt.setString(4, dto.getPassword());
                pstmt.setString(5, dto.getTel());
            }

            pstmt.close();
            rs.close();

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public void addTag(String id, ArrayList<String> list) {
        try {
            String sql = "insert into tblUserHash values (seqUserHash.nextVal, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            for (String seq : list) {
                pstmt.setString(2, seq);
                pstmt.executeUpdate();
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
    }

    public ArrayList<String> getHashSeqList(ArrayList<HashTagDTO> list) {
        try {
            String sql = "select seq from tblHashTag where hashtag = ?";
            pstmt = conn.prepareStatement(sql);

            ArrayList<String> seqList = new ArrayList<>();

            for (HashTagDTO dto : list) {
                pstmt.setString(1, dto.getHashtag());
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    seqList.add(rs.getString("seq"));
                }
            }

            pstmt.close();
            rs.close();

            return seqList;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return null;
    }

    public ArrayList<String> getHashTagsById(String id) {
        try {
            String sql = "select  * from tblUserHash u inner join tblHashTag h on u.hseq = h.seq where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            ArrayList<String> tagList = new ArrayList<>();

            while (rs.next()) {
                tagList.add(rs.getString("hashtag"));
            }

            pstmt.close();
            rs.close();

            return tagList;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return null;

    }

    public int updateNickname(String nickname, String id) {
        try {
            String sql = "update tblUser set nickname = ? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            pstmt.setString(2, id);

            pstmt.close();
            rs.close();

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public int updateTel(String tel, String id) {
        try {
            String sql = "update tblUser set tel = ? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tel);
            pstmt.setString(2, id);

            pstmt.close();
            rs.close();

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }


    public ArrayList<MyPostsDTO> getMyPosts(String id, int page) {
        try {

            int begin = (page - 1) * 20 + 1;
            int end = page * 20;

            String sql = "select * from vwMyposts where id = ? and rnum between ? and ?";
            pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, String.valueOf(begin));
            pstmt.setString(3, String.valueOf(end));

            rs = pstmt.executeQuery();

            ArrayList<MyPostsDTO> list = new ArrayList<>();

            while (rs.next()) {

                MyPostsDTO dto = new MyPostsDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setType(rs.getString("type"));
                dto.setTitle(rs.getString("title"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcnt(rs.getString("readcount"));

                list.add(dto);
            }

            pstmt.close();
            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return null;

    }

    public int getTotalCount(String id) {
        try {
            String sql = "select count(*) as cnt from tblPost where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }

            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }

    public ArrayList<MyCommentsDTO> getMyComments(String id, int page) {

        try {
            int begin = (page - 1) * 20 + 1;
            int end = page * 20;

            String sql = "select * from (select rownum as rnum, t.* from (select c.seq, c.content, c.regdate, p.type, p.title, u.id  from tblComment c inner join tblUser u on c.id = u.id " +
                    "inner join tblPost p on c.pseq = p.seq where u.id = ? order by seq desc) t) where rnum between ? and ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, String.valueOf(begin));
            pstmt.setString(3, String.valueOf(end));
            rs = pstmt.executeQuery();

            ArrayList<MyCommentsDTO> list = new ArrayList<>();

            while (rs.next()) {

                MyCommentsDTO dto = new MyCommentsDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setType(rs.getString("type"));
                dto.setRegdate(rs.getString("regdate"));

                list.add(dto);
            }
            pstmt.close();
            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }

        return null;
    }

    public int getTotalComments(String id) {
        try {
            String sql = "select count(*) as cnt from tblComment c inner join tblUser u on c.id = u.id inner join tblPost p on c.pseq = p.seq where u.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("cnt"));
            }
            pstmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
        }
        return 0;
    }
}
