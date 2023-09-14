package com.company.hello.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//해당 클래스가서비스 클래스라는걸 알려주기 위해 서비스 아노테이션을 넣어줌
public class MemberService  {
	
	@Autowired
	MemberDAO memberDAO;//우리가 객체화 시켜줄 필요없음 autowired라고 지정만해주면됨 그러면 스프링이 알아서해줌
	public int signUpConfirm(MemberVO memberVO) {//메서드 생성
System.out.println("[MemberService] signUpConfirm()");
		
		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());
		
		memberDAO.insertMember(memberVO);//insertMember가 호출될때 memberVO를 가지고 넘어감
		return 0;//컨트롤러가 아니기 때문에 리턴할 때 뷰를 연결해주지 않음
	}
	
	public MemberVO signInConfirm(MemberVO memberVO) {//signInConfirm호출이되면 다오에 뭔가 작업하는 코드가 들어가야겠지
System.out.println("[MemberService] signInConfirm()");
		
		MemberVO signedInMember = memberDAO.selectMember(memberVO);//전달받은 아이디와 비번으로 디비를 조회해야겠지 조회해서 해당 데이터가 있는지 없는지 다오로 받아오는것
		return signedInMember;
	}
}
