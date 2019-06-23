package com.spring.ex04;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spring.ex01.MemberVO;

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
	
	public String  selectName() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		// selectOne() 메소드로 인자로 지정한 SQL문을 실행한 후 한 개의 데이터(문자열) 반환
		String name = session.selectOne("mapper.member.selectName");
		return name;
	} 
		
	public int  selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		// selectOne() 메소드로 지정한 SQL문을 실행한 후 한 개의 데이터(정수)를 반환
		int pwd = session.selectOne("mapper.member.selectPwd");
		return pwd;
	}

	public MemberVO selectMemberById(String id){
		sqlMapper=getInstance();
		SqlSession session=sqlMapper.openSession();
		MemberVO memberVO=session.selectOne("mapper.member.selectMemberById",id);
		return memberVO;		
	}

	public List<MemberVO> selectMemberByPwd(int pwd) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		membersList= session.selectList("mapper.member.selectMemberByPwd", pwd);
		return membersList;
	}
	
	// MemberDAO 클래스에서 insert문을 사용하려면 SqlSession 클래스의 insert() 메소드를 이용
	// 다음과 같이 insert() 메소드의 첫 번째 인자에는 실행하고자 하는 SQL문의 id를 입력하고 
	// 두 번째 인자에는 SQL문으로 전달할 데이터를 지정. 
	// SQL문으로 전달할 데이터는 <insert> 태그의 parameterType 속성의 데이터 타입인 MemberVO 클래스와 일치해야 한다.
	public int insertMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		// 지정한 id의 SQL문에 memberVO의 값을 전달하여 회원 정보를 테이블에 추가
		result = session.insert("mapper.member.insertMember", memberVO);
		// 수동 커밋이므로 반드시 commit()메소드를 호출하여 영구 반영
		session.commit();
		return result;
	}

	public int insertMember2(Map<String,String> memberMap){
		sqlMapper=getInstance();
		SqlSession session=sqlMapper.openSession();
		// 메소드로 전달된 HashMap을 다시 SQL문으로 전달
		int result = session.insert("mapper.member.insertMember2",memberMap);
		session.commit();	
		return result;		
	}

	// update() 메소드를 호출하면서 서블릿에서 전달된 memberVO를  update문으로 전달
	// update() 메소드로 SQL문을 실행한 후에는 반드시 commit()메소드를 사용해서 커밋을 해주어야 한다.
	public int updateMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		// update문 호출 시 SqlSession의 update() 메소드를 이용
		int result = session.update("mapper.member.updateMember", memberVO);
		session.commit();
		return result;
	}   

	// delete() 메소드를 이용해 delete문을 실행하고 전달된 ID를 다시 delete() 메소드를 호출하면서
	// delete문으로 전달
	public int deleteMember(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		// delete문을 실행하려면 SqlSession의 delete()메소드를 이용해야 한다.
		result = session.delete("mapper.member.deleteMember", id);
		// SQL문을 실행한 후 반드시 커밋
		session.commit();
		return result;
	} 

	// 서블릿에서 전달된 이름과 이메일을 MemeberVO객체의 각 속성에 저장한 후 다시 
	// SqlSession 클래스의 selectList()메소드를 호출하면서 SQL문으로 전달
	public List<MemberVO>  searchMember(MemberVO  memberVO){
		sqlMapper=getInstance();
		SqlSession session=sqlMapper.openSession();
		// 회원 검색창에서 전달된 이름과 나이 값을 memberVO에 설정하여 SQL문으로 전달
		List list=session.selectList("mapper.member.searchMember",memberVO);
		return list;		
	} 

	public List<MemberVO>  foreachSelect(List nameList){
		sqlMapper=getInstance();
		SqlSession session=sqlMapper.openSession();
		List list=session.selectList("mapper.member.foreachSelect",nameList);
		return list;		
	}

	public int  foreachInsert(List memList){
		sqlMapper=getInstance();
		SqlSession session=sqlMapper.openSession();
		// insert문이 성공적으로 실행되면 양수를 반환
		// 회원정보가 저장된 memList를 SQL문으로 전달
		int result = session.insert("mapper.member.foreachInsert",memList);
		// 반드시 commit()을 호출
		session.commit();
		return result ;		
	}

	public List<MemberVO>  selectLike(String name){
		sqlMapper=getInstance();
		SqlSession session=sqlMapper.openSession();
		List list=session.selectList("mapper.member.selectLike",name);
		return list;		
	}
}
