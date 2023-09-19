package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
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

	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;// 빈 생성한거 사용할 수 있게 적어줌 JavaMailSenderImpl를 이용해서 이제 메일을 보낼수있음

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
		// 리턴타입이 void인 이유는 제대로 업데이트됐느지 안됐는지 보여줄 필요없음 컨트롤러보면 리턴페이지가 어차피 관리자목록페이지로 돌아올 거기
		// 때문에
		System.out.println("[AdminMemberService] setAdminApproval()");

		int result = adminMemberDAO.updateAdminAccount(a_m_no);// 컨트롤러에서는 set으로 하지만 데베에서는 update를 이용
	}

	public int modifyAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] modifyAccountConfirm()");
		return adminMemberDAO.updateAdminAccount(adminMemberVO);
	}

	public AdminMemberVO getLoginedAdminMemberVO(int a_m_no) {
		System.out.println("[AdminMemberService] getLoginedAdminMemberVO()");

		return adminMemberDAO.selectAdmin(a_m_no);
	}

	public int findPasswordConfirm(AdminMemberVO adminMemberVO) {
	  System.out.println("[AdminMemberService] findPasswordConfirm");

	  AdminMemberVO selectedAdminMemberVO = adminMemberDAO.selectAdmin(adminMemberVO.getA_m_id(),
			  adminMemberVO.getA_m_name(),
			  adminMemberVO.getA_m_mail());

	  int result = 0;

	  if(selectedAdminMemberVO != null) {
		  String newPassword = createNewPassword();
		  result = adminMemberDAO.updatePassword(adminMemberVO.getA_m_id(), newPassword);
		  //난수로 바꾼 비번을 바꿔줘야함
		  System.out.println("Service : "+result);
		  if(result >0) {
			  sendNewPasswordByMail(adminMemberVO.getA_m_mail(), newPassword);
			  //createNewPassword에서 만든 비번을 메일로 보냄
		  }

	  }
	  return result;
  }

	private String createNewPassword() {
		System.out.println("[AdminMemberService] createNewPassword()");
		char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		// SecureRandom 클래스는 랜덤함수중에서도 강력한 랜덤함수, 유추하기 더 어려운정도의 랜덤수를 만들어줌
		// 랜덤이라는 함수는 기본적으로 대표적인 방법이
		// 시계를 보면 202309181411(밀리초단위도들어감) 이렇게 베이스가 되는 숫자를 만듦 이걸 seed 라고 부름
		// 난수를 만들어내는 순가의 밀리초를 잡아옴 여러 수학공식을 사용해서 최종적으로 랜덤함수를 이용해서 난수를만듦
		// 세상에 완벽한 랜덤 숫자는 없음
		secureRandom.setSeed(new Date().getTime());// 현재시간을 밀리초단위로 가져와서 seed로 사용

		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);

			if (index % 2 == 0) {
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			} else {
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}

			System.out.println("[AdminMemberService] NEW PASSWORD : " + stringBuffer.toString());


		}return stringBuffer.toString();
	}

	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		System.out.println("[AdminMemberService] sendNewPasswordByMail()");

		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			// 인터페이스이기 때문에 구현하는 클래스를 따로 구현해주거나 내부에서 익명 클래스를 사용
			// 여기에서는 내부에서 익명 클래스를 사용함

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessagerHleper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessagerHleper.setTo(toMailAddr);// 받는사람 칸에 toMailAddr에 들어가있는 문자열 즉 메일주소가 옴 바뀐 비번 받을 이메일 주소
				mimeMessagerHleper.setSubject("[한국도서관]  새비밀번호입니다.");
				mimeMessagerHleper.setText("새비밀번호 : " + newPassword, true);// 메일내용
			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
	}
}