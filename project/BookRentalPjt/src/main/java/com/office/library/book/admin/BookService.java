package com.office.library.book.admin;

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

		if(!isISBN) {
			int result = bookDAO.insertBook(bookVO);

			if(result>0) {
				return BOOK_REGISTER_SUCCESS;
			}else {
				return BOOK_REGISTER_FAIl;
			}
		}else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}
}
