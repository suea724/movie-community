package com.project.movie.main;

import com.project.movie.DBUtil;
import com.project.movie.dto.PostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
}
