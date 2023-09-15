package com.office.library.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component // 리포지터리 써도 됨
public class AdminMemberDAO {// 여기서는 마리아디비를 사용할거임

	@Autowired
	JdbcTemplate jdbcTemplate;// JdbcTemplate을 사용하겠다고 선언
	// 자바코드 내에서 문자열을 사용해서 데베에 접근하는건 좋은방법이 아님

	@Autowired
	PasswordEncoder passwordEncoder;// 암호화관련 클래스 선언

	public boolean isAdminMember(String a_m_id) {
		System.out.println("[AdminMemberDAO] isAdminMember()");

		String sql = "SELECT COUNT(*) FROM tbl_admin_member " + "WHERE a_m_id = ?";// ?는 프리페어드스테이트먼트를 쓰기위한 준비?

		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);// a_m_id : ?에 들어갈 데이터

		if (result > 0) {
			return true;// 아이디값 중복됨
		} else {
			return false;// 조회를 하려고하는 멤버의 아이디값이 데베에 없기 때문에 중복되지 않는다는 뜻
		}
	}

	public int insertAdminAccount(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberDAO] insertAdminAccount()");

		List<String> args = new ArrayList<String>();

		String sql = "INSERT INTO tbl_admin_member(";
		if (adminMemberVO.getA_m_id().equals("super admin")) {// 멤버가 가진 아이디 값이 suepr admin일 경우
			sql += "a_m_approval, ";
			args.add("1");// 조건을보고 insert into tbl_admin_member(a_m_approval 여기에 들어갈 값을 1로 설정한것
			// 1은 최고 관리자라는 뜻
		}

		sql += "a_m_id, ";// 기존에 있었떤 문자열에 insert into tbl_admin_member(a_m_approval, a_m_id 이렇게 추가하는것
		args.add(adminMemberVO.getA_m_id());

		sql += "a_m_pw, ";// 기존에 있었떤 문자열에 insert into tbl_admin_member(a_m_approval, a_m_id, a_m_pw 이렇게
							// 추가하는것
		args.add(passwordEncoder.encode(adminMemberVO.getA_m_pw()));// passwordEncoder를 이용해서 암화호를 한후 args.add로 추가함

		sql += "a_m_name, ";
		args.add(adminMemberVO.getA_m_name());// 위와 같음

		sql += "a_m_gender, ";
		args.add(adminMemberVO.getA_m_gender());

		sql += "a_m_part, ";
		args.add(adminMemberVO.getA_m_part());

		sql += "a_m_position, ";
		args.add(adminMemberVO.getA_m_position());

		sql += "a_m_mail, ";
		args.add(adminMemberVO.getA_m_mail());

		sql += "a_m_phone, ";
		args.add(adminMemberVO.getA_m_phone());

		sql += "a_m_reg_date, a_m_mod_date)";// insert into tbl_admin_member(....., a_m_mod_date)

		if (adminMemberVO.getA_m_id().equals("super admin")) {
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		} else {
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		}

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 일치하는지 조회하기
	public AdminMemberVO selectAdmin(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberDAO] selectAdmin()");

		String sql = "SELECT * FROM tbl_admin_member " + "WHERE a_m_id = ? AND a_m_approval > 0";

		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();

		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {

				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();

					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));

					return adminMemberVO;
				}
			}, adminMemberVO.getA_m_id());

			if (!passwordEncoder.matches(adminMemberVO.getA_m_pw(), adminMemberVOs.get(0).getA_m_pw())) {
				adminMemberVOs.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminMemberVOs.size() > 0 ? adminMemberVOs.get(0) : null;
	}

	public List<AdminMemberVO> selectAdmins() {
		System.out.println("[AdminMemberDAO] selectAdmins()");

		String sql = "SELECT * FROM tbl_admin_member";

		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();

		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();

					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));

					return adminMemberVO;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adminMemberVOs;
	}

	public int updateAdminAccount(int a_m_no) {
		System.out.println("[AdminMemberDAO updateAdminAccount()]");
		String sql = "UPDATE tbl_admin_member SET "
						+ "a_m_approval = 1 "
						+ "WHERE a_m_no = ?";

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, a_m_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
