package com.kh.quiz;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizDAO {//데이터베이스와 통신을 하니까 리파지토리 아노테이션 사용
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	public QuizDO submitAnswer(QuizDO answer) {
		return sqlSession.selectOne("quizMapper.submitAnswer", answer);//매퍼에 적어준 정보를 괄호안에 적어줌 sql매핑되어있는 태그의 정보를 적어줌
		//퀴즈디오형태로 오기때문(리절트맵을 이용해서 지정을 해줬기 때문에 퀘스쳔과답을담고있는 객체의 형태로 담아오는거니까)
	}
	
	
}
