package com.project.movie.group.recruit;

import com.project.movie.DBUtil;
import com.project.movie.dto.GroupRequestDTO;
import com.project.movie.dto.RecruitmentPostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class RecruitDAO {

    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;


    public RecruitDAO() {
        conn = DBUtil.open();
    }


    //Add서블릿 > dto > 글쓰기
    public int add(RecruitmentPostDTO dto) {

        try {


            String sql = "insert into tblRecruit (seq, title, content, regdate, readcount, id, gseq) values (seqRecruit.nextVal, ?, ?, default, default, ?, ?)";

            //insert into tblRecruit(seq, title, content, regdate, readcount, id, gseq) values (seqRecruit.nextVal, '액션에 미친 사람들 모집합니다. ', '액션 좋아시는 분들 함께 놀아요', default, default, 'chanjin', 1);

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getId());
            pstat.setString(4, dto.getGseq());

            return pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("RecruitDAO.add");
            e.printStackTrace();
        }

        return 0;
    }

    //List 서블릿 목록 주세요
    public ArrayList<RecruitmentPostDTO> list(HashMap<String, String> map) {

        try {

            String where = "";

            if (map.get("isSearch").equals("y")) {
                where = String.format("where %s like '%%%s%%'", map.get("column"), map.get("word"));
            }

            //select r.*, u.nickname from tblRecruit r inner join tblUser u on u.id = r. id " + where + " order by seq des
            //select * from (select r.*, rownum as rnum from tblRecruit r inner join tblUser u on u.id = r.id where  nickname like '%민초%') where rnum between 0 and 10;

            String sql = String.format("select * from (select a.*, rownum as rnum from (select r.*, u.nickname from tblRecruit r inner join tblUser u on u.id = r.id %s order by r.seq desc) a) where rnum between %s and %s", where, map.get("begin"), map.get("end"));

            //select * from (select a.*, rownum as rnum from (select r.*, u.nickname from tblRecruit r inner join tblUser u on u.id = r.id %s order by r.seq desc) a) where rnum between %s and %s;
            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            ArrayList<RecruitmentPostDTO> list = new ArrayList<RecruitmentPostDTO>();

            while (rs.next()) {

                RecruitmentPostDTO dto = new RecruitmentPostDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setTitle(rs.getString("title"));
                dto.setId(rs.getString("id"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setNickname(rs.getString("nickname"));

                list.add(dto);
            }


            return list;


        } catch (Exception e) {
            System.out.println("RecruitDAO.list");
            e.printStackTrace();
        }



        return null;

    }


    //사용자가 그룹장인 그룹 리스트 가져오기
    public ArrayList<GroupDTO> grouplist(String id) {

        try {


            String sql = "select name, seq from tblGroup where id = ?";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, id);

            rs = pstat.executeQuery();

            ArrayList<GroupDTO> glist = new ArrayList<GroupDTO>();

            while (rs.next()) {

                GroupDTO dto = new GroupDTO();

                dto.setGseq(rs.getString("seq"));
                dto.setName(rs.getString("name"));

                glist.add(dto);
            }

            return glist;


        } catch(Exception e) {
            System.out.println("RecruitDAO.grouplist");
            e.printStackTrace();

        }

        return null;


    }



    public RecruitmentPostDTO get(String seq) {

        try {



            String sql = "select tblRecruit.*, (select name from tblUser where id = tblRecruit.id) as name, (select nickname from tblUser where id = tblRecruit.id) as nickname" +
                    " from tblRecruit where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            RecruitmentPostDTO dto = new RecruitmentPostDTO();

            if (rs.next()) {

                dto.setSeq(rs.getString("seq"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setId(rs.getString("id"));
                dto.setGseq(rs.getString("gseq"));
                dto.setNickname(rs.getString("nickname"));

            }


            return dto;


        } catch(Exception e) {
            System.out.println("RecruitDAO.get");
            e.printStackTrace();

        }

        return null;

    }

    //View 서블릿 > seq > 조회수 증가
    public void updateReadcount(String seq) {

        try {


            String sql = "update tblRecruit set readcount = readcount + 1 where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            pstat.executeUpdate();

        } catch(Exception e) {
            System.out.println("RecruitDAO.updateReadcount");
            e.printStackTrace();

        }


    }


    public int edit(RecruitmentPostDTO dto) {

        try {

            String sql = "update tblRecruit set title = ?, content = ?, gseq = ? where seq = ?";
            //update tblRecruit set title = '추리 영화 함께 보는 모임 인원 모집합니다.', content = '추리 영화 좋아하는 누구나 환영ㅎㅎ', gseq = 23 where seq = 5;

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, dto.getTitle());
            pstat.setString(2, dto.getContent());
            pstat.setString(3, dto.getGseq());
            pstat.setString(4, dto.getSeq());

            return pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("RecruitDAO.edit");
            e.printStackTrace();
        }

        return 0;

    }

    public int del(String seq) {

            try {

                String sql = "delete from tblRecruit where seq = ?";

                pstat = conn.prepareStatement(sql);

                pstat.setString(1, seq);

                System.out.println(seq);

                return pstat.executeUpdate();


            } catch(Exception e) {
                System.out.println("RecruitDAO.del");
                e.printStackTrace();

            }

            return 0;

    }


    public int request(HashMap<String, String> map) {

        try {



            String sql = "insert into tblGroupRequest(seq, id, gseq, regdate) values (seqGroupRequest.nextVal, ?, ?, default)";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, map.get("id"));
            pstat.setString(2, map.get("gseq"));

            return pstat.executeUpdate();


        } catch(Exception e) {
            System.out.println("RecruitDAO.request");
            e.printStackTrace();

        }

        return 0;

    }


    public String checkApply(HashMap<String, String> map) {

        try {

            //select  case when count(*) = 0 then 'n' else 'y' end as apply from tblGroupRequest where id = 'hong' and gseq = 1;
            String sql = "select  case when count(*) = 0 then 'n' else 'y' end as apply from tblGroupRequest where id = ? and gseq = ?";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, map.get("id"));
            pstat.setString(2, map.get("gseq"));


            rs = pstat.executeQuery();

            if (rs.next()) {

                return rs.getString("apply");
            }




        } catch(Exception e) {
            System.out.println("RecruitDAO.checkApply");
            e.printStackTrace();

        }

        return null;

    }

    public int getTotalCount(HashMap<String, String> map) {

        try {

            String where = "";

            if (map.get("isSearch").equals("y")) {
                where = String.format(" where %s like '%%%s%%'"
                        , map.get("column")
                        , map.get("word"));
            }

            //select count(*) as cnt from tblRecruit r inner join tblUser u on u.id = r. id where nickname like '%고길동%';
            String sql = "select count(*) as cnt from tblRecruit r inner join tblUser u on u.id = r. id " + where;

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("cnt");
            }


        } catch (Exception e) {
            System.out.println("RecruitDAO.getTotalCount");
            e.printStackTrace();
        }

        return 0;

    }
}


























