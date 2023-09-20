package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVO bookVO, Model model) {
		// 데이터 저장할 그릇으로 쓰이는 model 뷰페이지로 넘길때 model을 씀
		System.out.println("[UserBookController] searchBookConfirm()");

		String nextPage = "admin/book/search_book";

		List<BookVO> bookVOs = bookService.searchBookConfirm(bookVO);
		// BookVO 객체가 리스트형태로 저장됨

		model.addAttribute("bookVOs", bookVOs);
		return nextPage;
	}

	@GetMapping("/bookDetail") // @GetMapping할 때 파라미터는 메서드 안에 써줌, @RequestParam("b_no") : 매개변수로 자동으로 할당하게해줌
	public String boookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] bookDetail()");

		String nextPage = "admin/book/book_detail";
		BookVO bookVO = bookService.bookDetail(b_no);

		model.addAttribute("bookVO", bookVO);
		return nextPage;
	}

	@GetMapping("/modifyBookForm")
	public String modifyBookForm(@RequestParam("b_no") int b_no, Model model) {
		// 여기에서 int b_no에 들어갈 매개변수는 꼭 b_no이 아니어도됨 왜..?
		System.out.println("[BookController] bookDetail");

		String nextPage = "admin/book/modify_book_form";
		BookVO bookVO = bookService.modifyBookForm(b_no);

		model.addAttribute("bookVO", bookVO);
		return nextPage;
	}

	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVO bookVO, @RequestParam("file") MultipartFile file) {
		// MultipartFile : 사진을 전달해주면 파라미터값으로 들어오게됨
		System.out.println("[Bokcontroller] modifyBookConfirm()");

		String nextPage = "admin/book/modify_book_ok";

		if (!file.getOriginalFilename().equals("")) {
			// 관리자가 표지이미지를 새로 변경했는지 체크함 만약 파일을 첨부하지 않으면
			// getOriginalFilename이 비어있다면 새파일을 업데이트하지않는거니까 해당항복 비워두면 되고
			// 만약 사용자가 내용수정할때 표지를 바꾸고싶어서 파일을 첨부를 했다면 안의 if문이 실행됨
			// 업로드한 파일은 아까 라이브러리 업로드 폴더에 파일이 들어가게됨
			// savedFileName이 정상적으로 생성되면
			String savedFileName = uploadFileService.upload(file);
			if (savedFileName != null) {
				bookVO.setB_thumbnail(savedFileName);// savedFileName에 이미지의 경로가 들어감
			}
		}

		int result = bookService.modifyBookConfirm(bookVO);// bookVO에 새로 입력한 데이터가 들어가게됨

		if (result <= 0) {
			nextPage = "admin/book/modify_book_ng";
		}
		return nextPage;
	}

	@GetMapping("/deleteBookConfirm")
	public String deleteBookConfirm(@RequestParam("b_no") int b_no) {//삭제할 책 번호만 넘어오니까 값이 저장할매개변수 int형으로
		System.out.println("[BookControoler] deleteBookconfirm()");

		String nextPage = "admin/book/delete_book_ok";

		int result = bookService.deleteBookConfirm(b_no);

		if(result<=0) {
			nextPage = "admin/book/delete_book_ng";
		}
		return nextPage;
	}
}
