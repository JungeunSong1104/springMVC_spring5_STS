package com.kh.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {// 퀴즈서비스 구현

	@Autowired // 생성자를 스프링에게 맡김
	private QuizDAO quizDAO;

	public QuizDO submitAnswer(QuizDO answer) {// quizDO가 가진 특정한 메서드를 실행시켜주면됨
		return quizDAO.submitAnswer(answer);
	}
}
