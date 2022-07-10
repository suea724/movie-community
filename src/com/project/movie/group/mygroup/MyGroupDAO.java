package com.project.movie.group.mygroup;

import com.project.movie.DBUtil;
import com.project.movie.dto.GroupDTO;
import com.project.movie.dto.HashTagDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MyGroupDAO {

    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    public MyGroupDAO() {
        conn = DBUtil.open();
    }


    public String checkGroupName(String gname) {

        try {

            GroupDTO dto = new GroupDTO();

            String sql = "select count(*) as cnt from TBLGROUP where name = \'" + gname + "\'";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            while (rs.next()) {

                if (!rs.getString("cnt").equals("0")) {
                    System.out.println("0");
                    return "0";
                } else {
                    System.out.println("1");
                    return "1";
                }
            }

        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }


        return "1";
    }


    public String getHashTagSeq(String tag) {

        try {
            //여기 셀렉트 기존꺼?
            String sql = "select seq  from tblHashTag where hashtag = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, tag);
            rs = pstat.executeQuery();

            if (rs.next()) {
                return rs.getString("seq");
            }

        } catch (Exception e) {
            System.out.println("MyGroupDAO_getHashTagSeq");
            e.printStackTrace();
        }

        return null;

    }

    //CreateGroup  > 그룹번호 알아오기
    public String getSeq() {
        try {

            String sql = "select max(seq) as seq from tblGroup";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                return rs.getString("seq");
            }


        } catch (Exception e) {
            System.out.println("MyGroupDAO_getSeq");
            e.printStackTrace();

        }

        return null;
    }


    //CreateGroup > 태그 목록 가져오기
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
            System.out.println("MyGroupDAO_taglist");
            e.printStackTrace();

        }
        return null;
    }

    public int addTagging(HashMap<String, String> map) {

        try {

            String sql = "insert into tblGroupHash (seq, gseq, hseq) values (seqGroupHash.nextVal, ?, ?)";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, map.get("gseq"));
            pstat.setString(2, map.get("hseq"));

            return pstat.executeUpdate();

        } catch (Exception e) {
            System.out.println("MyGroupDAO_addTagging");
            e.printStackTrace();

        }

        return 0;
    }

    //CreateGroup > 그룹 추가
    public int add(GroupDTO gdto) {

        try {

            String sql = "insert into tblGroup (seq, name, info, recruitment, regdate, id) values (seqGroup.nextVal, ?, ?, ?, default, ?)";


            pstat = conn.prepareStatement(sql);
            pstat.setString(1, gdto.getName());
            pstat.setString(2, gdto.getInfo());
            pstat.setString(3, gdto.getRecruitment());
            pstat.setString(4, gdto.getId());

            return pstat.executeUpdate();


        } catch (Exception e) {
            System.out.println("MyGroupDAO_add");
            e.printStackTrace();

        }

        return 0;

    }

    public ArrayList<GroupDTO> list(String id) {

        try {


            String sql = "select g.*, (select count(*) from tblGroup where id = g.id and seq = g.gseq) as checkGroup, (select name from tblGroup where seq = g.gseq) as groupname, (select nickname from tblUser where id = g.id) as nickname, (select (select nickname from tblUser where a.id = id) from (select id from tblGroup where seq = g.gseq) a) as groupking from tblUserGroup g where id = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);

            rs = pstat.executeQuery();

            ArrayList<GroupDTO> list = new ArrayList<GroupDTO>();


            int i = 1;

            while (rs.next()) {

                GroupDTO dto = new GroupDTO();

                dto.setRnum(String.valueOf(i++));
                dto.setSeq(rs.getString("gseq"));
                //dto.setRnum(rs.getString("rnum"));
                dto.setName(rs.getString("groupname")); //그룹이름
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setState(rs.getString("checkGroup"));
                dto.setGroupKing(rs.getString("groupking"));

                list.add(dto);

            }

            return list;

        } catch (Exception e) {
            System.out.println("MyGroupDAO_list");
            e.printStackTrace();

        }

        return null;

    }

    //List > 총 게시물 수
    public int getTotalCount(HashMap<String, String> map) {

        try {

            String where = "";

            //검색 조건이 있을 경우
            if (map.get("isSearch").equals("y")) {
                where = String.format("where %s like %%%s%%'", map.get("column"), map.get("word"));
            }

            String sql = "select count(*) as cnt from tblGroup " + where;

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("cnt");
            }

        } catch (Exception e) {
            System.out.println("MyGroupDAO_getTotalCount");
            e.printStackTrace();

        }

        return 0;

    }

    //그룹 생성 후 유저그룹테이블에도 추가
    public int addGroupMaster(GroupDTO gdto) {

        try {

            String sql = "insert into TBLUSERGROUP (seq, id, GSEQ) values (seqUSERGROUP.nextVal, ?, ?)";


            pstat = conn.prepareStatement(sql);
            pstat.setString(1, gdto.getId());
            pstat.setString(2, gdto.getSeq());

            return pstat.executeUpdate();


        } catch (Exception e) {
            System.out.println("MyGroupDAO_add");
            e.printStackTrace();

        }

        return 0;

    }

    //참여중/참여신청
    public ArrayList<GroupDTO> list2(ArrayList<GroupDTO> list, String id) {

        try {


            String sql = "select gr.*, (select name from tblGroup where seq = gr.gseq) as groupname, (select nickname from tblUser where id = gr.id) as nickname, 2 as checkGroup, (select (select nickname from tblUser where a.id = id) from (select id from tblGroup where seq = gr.gseq) a) as groupking from tblgrouprequest gr where id = ?";


            pstat = conn.prepareStatement(sql);
            pstat.setString(1, id);

            rs = pstat.executeQuery();

            int i = 0;
            if(list.size() != 0) {

                i = Integer.parseInt(list.get(list.size() - 1).getRnum()) + 1;
            }

            while (rs.next()) {

                GroupDTO dto = new GroupDTO();

                dto.setSeq(rs.getString("gseq"));
                dto.setRnum(String.valueOf(i++));
                dto.setName(rs.getString("groupname")); //그룹이름
                dto.setId(rs.getString("id"));
                dto.setNickname(rs.getString("nickname"));
                dto.setState(rs.getString("checkGroup"));
                dto.setGroupKing(rs.getString("groupking"));

                list.add(dto);

            }

            return list;

        } catch (Exception e) {
            System.out.println("MyGroupDAO_list");
            e.printStackTrace();

        }
        return null;
    }


    //그룹 정보페이지에 넣을 정보 가져오기
    public GroupDTO groupinfo(String seq) {

        try {

            String sql = "select g.*, (select nickname from tblUser where id = g.id) as nickname, (select count(*) from tblUserGroup where gseq = g.seq) as cnt from tblGroup g where seq = ?";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, seq);

            rs = pstat.executeQuery();


            if (rs.next()) {

                GroupDTO dto = new GroupDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setName(rs.getString("name"));
                dto.setInfo(rs.getString("info"));
                dto.setRecruitment(rs.getString("recruitment"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setNickname(rs.getString("nickname")); //그룹장 찾기
                dto.setId(rs.getString("id"));
                dto.setCnt(rs.getString("cnt")); //그룹 인원수

                return dto;

            }


        } catch (Exception e) {
            System.out.println("MyGroupDAO_groupinfo");
            e.printStackTrace();

        }
        return null;
    }


    //그룹 해시태그 알아내기
    public ArrayList<String> groupHash(String seq) {

        try {

            String sql = "select ht.hashtag as hashtag from tblGroup g inner join tblGroupHash gh on g.seq = gh.gseq inner join tblHashtag ht on gh.hseq = ht.seq where g.seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            ArrayList<String> list = new ArrayList<String>();

            while (rs.next()) {

                list.add(rs.getString("hashtag"));

            }
            return list;

        } catch (Exception e) {
            System.out.println("MyGroupDAO_groupHash");
            e.printStackTrace();

        }

        return null;


    }

    public GroupDTO findGroup(String seq) {

        try {

            String sql = "select * from tblGroup where seq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setString(1, seq);

            rs = pstat.executeQuery();

            if (rs.next()) {
                GroupDTO dto = new GroupDTO();

                dto.setSeq(seq);
                dto.setName(rs.getString("name"));
                dto.setInfo(rs.getString("info"));
                dto.setRecruitment(rs.getString("recruitment"));

                return dto;
            }

        } catch(Exception e) {
            System.out.println("MyGroupDAO_findGroup");
            e.printStackTrace();

        }

        return null;
    }


    //해시태그 삭제
    public int delHashTag(String seq) {

        try {

            String sql = "delete from tblGROUPHASH where gseq = ?";

            pstat = conn.prepareStatement(sql);

            pstat.setString(1, seq);

            return pstat.executeUpdate();



        } catch(Exception e) {
            System.out.println("MyGroupDAO_delHashTag");
            e.printStackTrace();

        }
            return 0;
    }

    //그룹 수정
    public int edit(GroupDTO gdto) {

        try {

            String sql = "update tblGroup set name = ?, info = ?, recruitment = ? where seq = ?";


            pstat = conn.prepareStatement(sql);
            pstat.setString(1, gdto.getName());
            pstat.setString(2, gdto.getInfo());
            pstat.setString(3, gdto.getRecruitment());
            pstat.setString(4, gdto.getSeq());

            return pstat.executeUpdate();


        } catch (Exception e) {
            System.out.println("MyGroupDAO_add");
            e.printStackTrace();

        }

        return 0;

    }
}















