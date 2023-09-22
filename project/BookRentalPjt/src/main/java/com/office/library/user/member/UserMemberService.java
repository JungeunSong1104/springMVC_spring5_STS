package com.office.library.user.member;

import java.security.SecureRandom;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service // 항상 어노테이션을 이용해 빈을 먼저 등록해주는게 좋음
public class UserMemberService {

	@Autowired
	UserMemberDAO userMemberDAO;// UserMemberDAO 사용해야하니까 생성자 생성해줌 외부에서

	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;//javamail서비스구현체

	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	public int createAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] createAccountConfirm()");

		boolean isMember = userMemberDAO.isUserMember(userMemberVO.getU_m_id());

		if (!isMember) {
			int result = userMemberDAO.insertUserAccount(userMemberVO);

			if (result > 0) {
				return USER_ACCOUNT_CREATE_SUCCESS;
			} else {
				return USER_ACCOUNT_CREATE_FAIL;
			}
		} else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
	}

	public UserMemberVO loginConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] loginConfirm()");

		UserMemberVO loginedUserMemberVO = userMemberDAO.selectUser(userMemberVO);

		if (loginedUserMemberVO != null) {
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS");
		} else {
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL");
		}
		return loginedUserMemberVO;
	}

	public int modifyAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] modifyAccountConfirm()");

		return userMemberDAO.updateUserAccount(userMemberVO);
	}

	public UserMemberVO getLoginedUserMemberVO(int u_m_no) {
		System.out.println("[UserMemberService] getLoginedUserMemberVO()");

		return userMemberDAO.selectUser(u_m_no);
	}

	public int findPasswordConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] findPasswordConfirm()");
		UserMemberVO selectedUserMemberVO = userMemberDAO.selectUser(userMemberVO.getU_m_id(), userMemberVO.u_m_name,
				userMemberVO.u_m_mail);

		int result = 0;

		if (selectedUserMemberVO != null) {
			String newPassword = createNewPassword();
			result = userMemberDAO.updatePassword(userMemberVO.getU_m_id(), newPassword);
			if (result > 0) {
				sendNewPasswordByMail(userMemberVO.getU_m_mail(), newPassword);
			}
		}
		return result;
	}

	private String createNewPassword() {
		System.out.println("[UserMemberService] createNewPassword()");

		char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		StringBuffer stringBuffer = new StringBuffer();// stringBuffer 선언
		SecureRandom secureRandom = new SecureRandom();
		// SecureRandom 클래스는 랜덤함수중에서도 강력한 랜덤함수, 유추하기 더 어려운정도의 랜덤수를 만들어줌
		// 랜덤이라는 함수는 기본적으로 대표적인 방법이
		// 시계를 보면 202309181411(밀리초단위도들어감) 이렇게 베이스가 되는 숫자를 만듦 이걸 seed 라고 부름
		// 난수를 만들어내는 순가의 밀리초를 잡아옴 여러 수학공식을 사용해서 최종적으로 랜덤함수를 이용해서 난수를만듦
		// 세상에 완벽한 랜덤 숫자는 없음
		secureRandom.setSeed(new Date().getTime());// 현재시간을 밀리초단위로 가져와서 seed로 사용
		// secureRandom가 가진 setSeed를 실행

		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);// secureRandom에 저장되어있는 문자열을 가져와서 index에 넣고

			if (index % 2 == 0) {// index가 짝수면 대문자
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			} else {// index가 홀수면 소문자
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}
		}

		System.out.println("[UserMemberService] NEW PASSWORD : " + stringBuffer.toString());
		return stringBuffer.toString();
	}

	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		System.out.println("[UserMemberService ] sendNewPasswrodByMail()");

		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception{//prepare라는 메서드 정의
				final MimeMessageHelper mimeMessagerHleper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				/* 여기부터 중요 */
				mimeMessagerHleper.setTo("jukp1104@gmail.com");// 받는사람 칸에 toMailAddr에 들어가있는 문자열 즉 메일주소가 옴 바뀐 비번 받을 이메일 주소
				mimeMessagerHleper.setSubject("[한국도서관]  새비밀번호입니다.");
				mimeMessagerHleper.setText("새비밀번호 : " + newPassword, true);
			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
	}
}
