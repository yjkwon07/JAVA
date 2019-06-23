package com.spring.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import com.spring.member.vo.MemberVO;

/*
 * ���� ���Ͽ��� ���� sqlSession ���� �Ӽ� sqlSession�� �����ϱ� ���� 
 * setter�� ���� 
 * sqlSession ���� �޼ҵ带 �̿��� ���� ���Ͽ� ���ǵ� SQL ���� ���
 */
public class MemberDAOImpl implements MemberDAO {
	private SqlSession sqlSession;
	// �Ӽ� sqlSession�� sqlSession ���� �����ϱ� ���� setter�� ����
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<MemberVO> selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		// ���Ե� sqlSession ������ selectList() �޼ҵ带 ȣ���ϸ鼭 SQL���� id�� ����
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		// ���Ե� sqlSession ������ insert() �޼ҵ带 ȣ���ϸ鼭 SQL���� id�� memverVO�� ����
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	@Override
	public int deleteMember(String id) throws DataAccessException {
		// ���Ե� sqlSession ������ delete() �޼ҵ带 ȣ���ϸ鼭 SQL���� id�� ȸ�� ID�� ����.
		int result =  sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}
}
