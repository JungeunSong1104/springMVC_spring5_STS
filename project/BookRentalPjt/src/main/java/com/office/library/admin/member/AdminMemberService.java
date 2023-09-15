package com.office.library.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//서비스 대신 컴포넌트로 해도 무방함 같은 기능을 하기 때문 대신 변수이름 짓듯이 알아보기쉽게
//서비스클래스를 만들때느 서비스아노테이션써준느게 일반적
public class AdminMemberService {// 컨트롤러에서 명시적으로 넘겨줘야함
//AdminMemberService는 DAO를 사용해야함 그래서 DAO에도 빈 설정을해줌

	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;// 상수값 정의해줌 상수값의 변수이름은 대문자로
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;

	@Autowired
	AdminMemberDAO adminMemberDAO;

	public int createAccountConfirm(AdminMemberVO adminMemverVO) {
		// createAccountConfirm 컨트롤러에서 호출되거고 호출될당시에 adminMemverVO를 매개변수로 넘겨줄거기 때문에
		System.out.println("[AdminMemberService] createAccountConfirm()");

		boolean isMember = adminMemberDAO.isAdminMember(adminMemverVO.getA_m_id());

		if (!isMember) {// 중복된 아이디 있는지 체크
			int result = adminMemberDAO.insertAdminAccount(adminMemverVO);// 중복된 아이디가 없는 경우에

			// 인서트 구문 같은경우 jdbc에서 사용했을 대 리턴값으로 하나의 행이 추가됨
			// 변화된 행이 몇개인지 리턴하게됨
			// 정상적으로 리턴하게 된다면 리절트에는 1이 들어가게됨

			if (result > 0) {
				return ADMIN_ACCOUNT_CREATE_SUCCESS; // return 1;
				// return 1이라고 해도 무방함 코드를 읽을 때 리턴 1이라고 하면 이 1이 어떤 값을 리턴한지 알기가 어려움 그래서 상수값을 만들어서
				// 상수갑을 리턴한다 하면 바로 알아볼수있으니까 이런식으로 상수값쓰는것
			} else {
				return ADMIN_ACCOUNT_CREATE_FAIL;// return -1;
			}
		} else {
			return ADMIN_ACCOUNT_ALREADY_EXIST;// return 0;
		}
		// return 0; 이제 이부분은 핅요없음 if문을 만나게되면 리턴되는건 기정사실이니까
	}

	public AdminMemberVO loginConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] loginConfirm()");

		AdminMemberVO loginedAdminMemberVO = adminMemberDAO.selectAdmin(adminMemberVO);

		if (loginedAdminMemberVO != null) {
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS");
		} else {
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL");
		}
		return loginedAdminMemberVO;
		// 반환값 loginedAdminMemberVO 데베에서 로그인이 되면 loginedAdminMemberVO의 값이 들어가있겠지
	}

	public List<AdminMemberVO> listupAdmin() {
		System.out.println("[AdminMemberService] listupAdmin()");

		return adminMemberDAO.selectAdmins();
	}

	public void setAdminApproval(int a_m_no) {
	//리턴타입이 void인 이유는 제대로 업데이트됐느지 안됐는지 보여줄 필요없음 컨트롤러보면 리턴페이지가 어차피 관리자목록페이지로 돌아올 거기 때문에
		System.out.println("[AdminMemberService] setAdminApproval()");

		int result = adminMemberDAO.updateAdminAccount(a_m_no);//컨트롤러에서는 set으로 하지만 데베에서는 update를 이용
	}
}
