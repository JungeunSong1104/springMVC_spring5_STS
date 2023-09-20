package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVO;

@Service
public class BookService {
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;
	final static public int BOOK_REGISTER_SUCCESS = 1;
	final static public int BOOK_REGISTER_FAIl = -1;

	@Autowired
	BookDAO bookDAO;

	public int registerBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] registerBookConfirm()");

		boolean isISBN = bookDAO.isISBN(bookVO.getB_isbn());

		if (!isISBN) {
			int result = bookDAO.insertBook(bookVO);

			if (result > 0) {
				return BOOK_REGISTER_SUCCESS;
			} else {
				return BOOK_REGISTER_FAIl;
			}
		} else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}

	public List<BookVO> searchBookConfirm(BookVO bookVO) {
		// searchBookConfirm : bookDAO이 가지고있는 selectBooksbySearch를 호출하면서 bookVO를 넘겨주는
		// 메서드
		System.out.println("[BookSservice] searchBookConfirm");

		return bookDAO.selectBooksBySearch(bookVO);
	}

	public BookVO bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");

		return bookDAO.selectBook(b_no);
	}

	public BookVO modifyBookForm(int b_no) {
		System.out.println("[BookService] modifyBookForm()");
		return bookDAO.selectBook(b_no);
	}

	public int modifyBookConfirm(BookVO bookVO) {
		// 업데이트문을 실행할거기 때문에 업데이트문은 항상 몇개의 행기 영향 받았는지 리턴형태를...?
		System.out.println("[BookService] modifyBookConfirm()");

		return bookDAO.updateBook(bookVO);
	}

	public int deleteBookConfirm(int b_no) {
		// 업데이트문을 실행할거기 때문에 업데이트문은 항상 몇개의 행기 영향 받았는지 리턴형태를...?
		System.out.println("[BookService] deleteBookConfirm()");

		return bookDAO.deleteBook(b_no);
	}
}
