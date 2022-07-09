package com.project.movie.member;

import com.project.movie.DBUtil;
import com.project.movie.dto.HashTagDTO;
import com.project.movie.dto.LoginDTO;
import com.project.movie.dto.MemberDTO;
import oracle.security.pki.util.EccCurveParameters;

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
                mdto.setNickname(rs.getString("nickname"));
                mdto.setTel(rs.getString("tel"));
                mdto.setPicture(rs.getString("picture"));
                mdto.setJoindate(rs.getString("joindate"));

                return mdto;
            }

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updatePw(String id, String pw) {
        try {
            String sql = "update tblUser set password = ? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pw);
            pstmt.setString(2, id);

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
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
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
