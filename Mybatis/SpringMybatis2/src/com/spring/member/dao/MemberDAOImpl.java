package com.spring.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import com.spring.member.vo.MemberVO;

/*
 * 설정 파일에서 만든 sqlSession 빈을 속성 sqlSession에 주입하기 위해 
 * setter를 구현 
 * sqlSession 빈의 메소드를 이용해 매퍼 파일에 정의된 SQL 문을 사용
 */
public class MemberDAOImpl implements MemberDAO {
	private SqlSession sqlSession;
	// 속성 sqlSession에 sqlSession 빈을 주입하기 위해 setter를 구현
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<MemberVO> selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		// 주입된 sqlSession 빈으로 selectList() 메소드를 호출하면서 SQL문의 id를 전달
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		// 주입된 sqlSession 빈으로 insert() 메소드를 호출하면서 SQL문의 id와 memverVO를 전달
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	@Override
	public int deleteMember(String id) throws DataAccessException {
		// 주입된 sqlSession 빈으로 delete() 메소드를 호출하면서 SQL문의 id와 회원 ID를 전달.
		int result =  sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}
}
