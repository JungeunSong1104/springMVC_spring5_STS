package com.office.library.book.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.library.book.BookVO;

@Component // DAO인까 리포지터리 컴포넌트 둘다 됨
public class BookDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean isISBN(String b_isbn) {
		System.out.println("[BookDAO] isISBN()");
		String sql = "SELECT COUNT(*) FROM tbl_book WHERE b_isbn = ?";

		int result = jdbcTemplate.queryForObject(sql, Integer.class, b_isbn);

		return result > 0 ? true : false;
	}

	public int insertBook(BookVO bookVO) {
		System.out.println("[BookDAO] insertBook()");

		String sql = "INSERT INTO tbl_book(b_thumbnail, b_name, b_author, b_publisher, b_publish_year, b_isbn, b_call_number, b_rantal_able, b_reg_date, b_mod_date) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, bookVO.getB_thumbnail(), bookVO.getB_name(), bookVO.getB_author(),
					bookVO.getB_publisher(), bookVO.getB_publish_year(), bookVO.getB_isbn(), bookVO.getB_call_number(),
					bookVO.getB_rantal_able(), bookVO.getB_reg_date(), bookVO.getB_mod_date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<BookVO> selectBooksBySearch(BookVO bookVO) {
		System.out.println("[BookDAO] selectBookds()");

		String sql = "SELECT * FROM tbl_book WHERE b_name LIKE ? ORDER BY b_no DESC";
		// 컬럼 이름중에 b_no를 기준으로 ORDER BY를 사용하고있음
		// ?표 자리에 %를 써서

		List<BookVO> bookVOs = null;
		try {
			bookVOs = jdbcTemplate.query(sql, new RowMapper<BookVO>() {
				// RowMapper라는 인터페이스를 구현하고 있음
				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					// mapRow라는 메서드를 만들어서 호출하고있음
					BookVO bookVO = new BookVO();

					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_author(rs.getString("b_author"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rantal_able(rs.getInt("b_rantal_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));

					return bookVO;
				}
			}, "%" + bookVO.getB_name() + "%");
			// 컴퓨터 라고 검색한다고하면 select * from tbl_book where b_name like %컴퓨터% order by b_no
			// desc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookVOs.size() > 0 ? bookVOs : null;
	}

	public BookVO selectBook(int b_no) {
		System.out.println("[BookDAO] selectBook()");
		String sql = "SELECT * FROM tbl_book WHERE b_no=?";

		List<BookVO> bookVOs = null;

		try {
			bookVOs = jdbcTemplate.query(sql, new RowMapper<BookVO>() {

				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVO bookVO = new BookVO();

					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_author(rs.getString("b_author"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rantal_able(rs.getInt("b_rantal_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));

					return bookVO;
				}
			}, b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookVOs.size() > 0 ? bookVOs.get(0) : null;
	}

	public int updateBook(BookVO bookVO) {
		System.out.println("[BookDAO] updateBook()");

		List<String> args = new ArrayList<String>();

		String sql = "UPDATE tbl_book SET ";
		if (bookVO.getB_thumbnail() != null) {
			sql += "b_thumbnail = ?, ";
			args.add(bookVO.getB_thumbnail());
		}

		sql += "b_name = ?, ";
		args.add(bookVO.getB_name());

		sql += "b_author = ?, ";
		args.add(bookVO.getB_author());

		sql += "b_publisher = ?, ";
		args.add(bookVO.getB_publisher());

		sql += "b_publish_year = ?, ";
		args.add(bookVO.getB_publish_year());

		sql += "b_isbn = ?, ";
		args.add(bookVO.getB_isbn());

		sql += "b_call_number = ?, ";
		args.add(bookVO.getB_call_number());

		sql += "b_rantal_able = ?, ";
		args.add(Integer.toString(bookVO.getB_rantal_able()));

		sql += "b_mod_date = NOW() ";

		sql += "WHERE b_no = ?";
		args.add(Integer.toString(bookVO.getB_no()));

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteBook(int b_no) {
		System.out.println("[BookDAO] deleteBook()");

		String sql = "DELETE FROM tbl_book WHERE b_no = ?";

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, b_no);// 영향받은 행 개수(업데이트된 행개수)를 result에 넣어줌
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
