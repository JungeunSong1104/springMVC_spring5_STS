package com.office.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
				+"VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

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
}
