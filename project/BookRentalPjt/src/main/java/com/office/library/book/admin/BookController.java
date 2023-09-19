package com.office.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.office.library.book.BookVO;
import com.office.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	BookService bookService;// 북서비스객체선언

	@Autowired
	UploadFileService uploadFileService;// UploadFileService 객체선언

	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[BookController] registerBookForm()");

		String nextPage = "admin/book/register_book_form";
		return nextPage;
	}

	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVO bookVO, @RequestParam("file") MultipartFile file) {
		// MultipartFile는 매개변수 써준다고 알아서 매핑되지않음 input태그의 name이 file이라고 되어있기 때문에
		// MultipartFile에 연결되는 파라미터를 넣어줌?
		System.out.println("[BookController] registerBookConfirm()");
		String nextPage = "admin/book/register_book_ok";

		String savedFileName = uploadFileService.upload(file);// 파일이름저장
		// uploadFileService의 upload메서드
		// uploadFileService : 위에 만들준 클래스
		if (savedFileName != null) {
			bookVO.setB_thumbnail(savedFileName);
			int result = bookService.registerBookConfirm(bookVO);

			if (result <= 0) {// 등록이됐을때는 0이상의 값이 올거고
				nextPage = "admin/book/register_book_ng";
				// 북서비스의 registerBookConfirm이 실패했을떄의 if문
			}
		} else {
			nextPage = "admin/book/register_book_ng";
			// uploadFileService의구문을 실행했을때 파일 업로드 실패했을때 즉 null 값이 들어가면 ng페이지
		}
		return nextPage;
	}
}
