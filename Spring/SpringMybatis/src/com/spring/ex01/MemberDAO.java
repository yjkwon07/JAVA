package com.spring.ex01;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/*
 * MemberDAO Ŭ������ ������ ���� �ۼ�
 * SqlMapConfig.xml ������ �̿��� SqlMapper ��ü�� ����.
 * �׷� ���� selectAllMemberList() �޼ҵ带 ȣ���ϸ鼭 
 * ���ڷ� mapper.member.selectAllMemberList�� ���� ��
 * member.xml���� �ش� ���ӽ����̽���  id�� �ش��ϴ� SQL���� ���� 
 */
public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				// MemberDAO�� �� �޼ҵ� ȣ�� �� src/mybatis/SqlMapConfig.xml ����
				// ���� ������ ���� �� �����ͺ��̽����� ���� �غ�
				String resource = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				// ���̹�Ƽ���� �̿��ϴ�  sqlMapper ��ü�� �����´�.
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
		// ���� member.xml�� SQL���� ȣ���ϴ� �� ���Ǵ� sqlSession ��ü�� �����´�.
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist = null;
		// ���� ���� ���ڵ带 ��ȸ�ϹǷ� selectList()�޼ҵ忡 �����ϰ��� �ϴ� SQL���� id�� ���ڷ� ����.
		memlist = session.selectList("mapper.member.selectAllMemberList");
		return memlist;
	}
}
