package com.spring.ex01;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/*
 * MemberDAO 클래스를 다음과 같이 작성
 * SqlMapConfig.xml 파일을 이용해 SqlMapper 객체를 생성.
 * 그런 다음 selectAllMemberList() 메소드를 호출하면서 
 * 인자로 mapper.member.selectAllMemberList를 전달 해
 * member.xml에서 해당 네임스페이스와  id에 해당하는 SQL문을 실행 
 */
public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				// MemberDAO의 각 메소드 호출 시 src/mybatis/SqlMapConfig.xml 에서
				// 설정 정보를 읽은 후 데이터베이스와의 연동 준비
				String resource = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				// 마이바티스를 이용하는  sqlMapper 객체를 가져온다.
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;

	}

	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		// 실제 member.xml의 SQL문을 호출하는 데 사용되는 sqlSession 객체를 가져온다.
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist = null;
		// 여러 개의 레코드를 조회하므로 selectList()메소드에 실행하고자 하는 SQL문의 id를 인자로 전달.
		memlist = session.selectList("mapper.member.selectAllMemberList");
		return memlist;
	}
}
