# Mybatis
[레퍼런스 - KO](http://www.mybatis.org/mybatis-3/ko/index.html)

# 스프링 JDBC로 데이터베이스와의 연동 설정
JDBC는 자바 데이터 접근 기술의 근간이라 할 정도로 대부분의 개발자가 쉽게 이해할 수 있어 많이 사용하는 데이터 엑세스 기술이다.

그러나 시간이 지남에따라 SQL문이 지나치게 복잡해지면서 개발니나 유지관리에서 어려움이 생기기 시작해다.

특히 Connection 객체 같은 공유 리소스를 제대로 처리해 주지 않으면 버그를 발생시키는 원인이 되고 했다.
## jdbc JNDI 사용
```java
public class BoardDAO {
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List selectAllArticles(Map pagingMap){
		List articlesList = new ArrayList();
		// 전송된  section과 pageNum값을 가져온다.
		int section = (Integer)pagingMap.get("section");
		int pageNum=(Integer)pagingMap.get("pageNum");
		
		try{
		   conn = dataFactory.getConnection();
		   // 계층형으로 조회된 레코드의 ROWNUM(recNum)이 표시되도록 조회 한다.
		   // 계층형 SQL문으로 글을 계층별로 조회
		   // section과 pageNum 값으로 조건식의 recNum 범위를 정한 후 조회된 글 중
		   // 해당하는 값이 있는 경우 최종적으로 조회한다.
		   String query ="SELECT * FROM ( "
						+ "select ROWNUM  as recNum,"
							+"LVL,"
							+"articleNO,"
							+"parentNO,"
							+"title,"
							+"id,"
							+"writeDate"
							+" from (select LEVEL as LVL, "
								+"articleNO,"
								+"parentNO,"
								+"title,"
								+"id,"
								+"writeDate"
								+" from t_board" 
									+" START WITH  parentNO=0"
									+" CONNECT BY PRIOR articleNO = parentNO"
									+"  ORDER SIBLINGS BY articleNO DESC)"
					+") "                        
						+" where recNum between(?-1)*100+(?-1)*10+1 and (?-1)*100+(?)*10";     
					// section과 pageNum 값으로 레코드 번호의 범위를 조건으로 정한다.
					// (이들 값이 각각1로 전송되었으면 between 1 and 10이 된다.)           
		   System.out.println(query);
		   pstmt= conn.prepareStatement(query);
		   pstmt.setInt(1, section);
		   pstmt.setInt(2, pageNum);
		   pstmt.setInt(3, section);
		   pstmt.setInt(4, pageNum);
		   ResultSet rs =pstmt.executeQuery();
		   while(rs.next()){
		      int level = rs.getInt("lvl");
		      int articleNO = rs.getInt("articleNO");
		      int parentNO = rs.getInt("parentNO");
		      String title = rs.getString("title");
		      String id = rs.getString("id");
			  Date writeDate= rs.getDate("writeDate");
			  
		      ArticleVO article = new ArticleVO();
		      article.setLevel(level);
		      article.setArticleNO(articleNO);
		      article.setParentNO(parentNO);
		      article.setTitle(title);
		      article.setId(id);
		      article.setWriteDate(writeDate);
		      articlesList.add(article);	
		   } //end while
		   rs.close();
		   pstmt.close();
		   conn.close();
	  }catch(Exception e){
	     e.printStackTrace();	
	  }
	  return articlesList;
    } 
```
## Spring JdbcTemplte 주입 

```java
// 설정 파일에서 생성한 dataSoucre 빈을 setter를 이용해 JdbcTemplate 클래스 생성자의 인자로 입력
public void steDataSource(DataSource dataSource){
	this.jdbcTemplate = new JdbcTemplate(dataSource);
}
public List selectAllMembers() throws DataAccessExcption {
  String query = "select id,pwd,email,joinDate"
                + " from t_member "
                + " order by joinDate desc ";
  List memberList = new ArrayList();
  memberList = this.jdbcTemplete.query(query , new RowMapper() {
              public Object mapRow(ResultSet rs, int rowNum) thorws SQLException {
                MemberVO memberVO = new MemberVO();
                memberVO.setId(rs.getString("id"));
                memberVO.setId(rs.getString("pwd"));
                memberVO.setId(rs.getString("name"));
                memberVO.setId(rs.getString("email"));
                memberVO.setId(rs.getString("joinDate"));
                return memberVO;
             }
  });
  return memberList;
}
```
# 마이바티스?
애플리케이션의 규모가 작을 때는 JDBC를 이용해 충분히 개발할 수 있었다. 

그러나 인터넷 사용자가 폭발적으로 증가하고 애플리케이션의 기능이 복잡해짐에 따라 기존의 JDBC로 개발하는 데는 한계가 드러나게 되었다.

기존 JDBC로 개발할 경우 반복적으로 구현해야 할 SQL문도 많을 뿐만 아니라 SQL문도 복잡하다. 따라서 자연스럽게 마이바티스나 하이버테이트 같은
데이터베이스 연동 관련 프레임워크가 등장하게 되었다. 

기존의 JDBC를 연동하려면 다음과 같은 과정을 거쳐야 했다.
```
connection ->  Statement 객체 생성 -> SQL문 전송 -> 결과 반환 -> close
```
이 방식의 단점은 SQL문이 프로그래밍 코드에 섞여 코드를 복잡하게 만든다는 것이다.

이 방법을 개선해 SQL문 가독성을 높여 사용하기 편하게 만든 것이 바로 마이바티스 프레임워크이다.

데이터베이스 연동 시 사용되는 SQL문을 미리 SqlMapConfig.xml에 작성해 놓고 애플리케이션에서 데이터베이스 연동 시 해당 SQL문에서
사용될 데이터를 지원하는 해당 매개변수에 저장한 후 SQL문에 전달한다. 

전달된 매개변수와 SQL문을 결합해 SQL문을 DBMS로 전송하여 실행한다.

그리고 그 결과를 애플리케이션에서 제공하는 자료형으로 반환한다.
```java
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
  
  ...
```

## spring mybatis
SqlSeesion을 미리 빈을 주입 하여 편리하게 사용
```java
@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}
  
  ...
```
