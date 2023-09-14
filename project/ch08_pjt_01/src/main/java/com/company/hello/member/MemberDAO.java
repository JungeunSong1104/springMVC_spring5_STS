package com.company.hello.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component // 컴포넌트든 서비스든 리파지토리든 상관없음 기능 같음
public class MemberDAO {// ioc의 빈객체로 등록될거임
	private Map<String, MemberVO> memberDB = new HashMap<String, MemberVO>();

	public void insertMember(MemberVO memberVO) {
		System.out.println("[MemberDAO] insertMember()");
		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());
    
		memberDB.put(memberVO.getM_id(), memberVO);//전달받은 데이터를 넣어줌
    //memberDB라는 해쉬맵 객체에 put으로 
    //memberVO객체 전체가 들어감
    
		this.printAllMember();//가지고 있는 멤버출력
  }

	public MemberVO selectMember(MemberVO memberVO) {// 가져오는 데이터의 타입은 MemberVO
		System.out.println("[MemberDAO] selectMember()");

		MemberVO signedInMember = memberDB.get(memberVO.getM_id());
		if (signedInMember != null && memberVO.getM_pw().equals(signedInMember.getM_pw())) {
			// 들어간 값이 null 이면서 가져온 멤버객체의 비번이 signedInMember로그인을 시도한 멤버의 비번이 같으면
			return signedInMember;
		} else {
			return null;
		}
	}

	private void printAllMember() {
		System.out.println("[MemberDAO] printAllMembers()");

		Set<String> keys = memberDB.keySet();
		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			MemberVO memberVO = memberDB.get(key);

			System.out.println("m_id : " + memberVO.getM_id());
			System.out.println("m_pw : " + memberVO.getM_pw());
			System.out.println("m_mail : " + memberVO.getM_mail());
			System.out.println("m_phone : " + memberVO.getM_phone());
		}
	}
}