package com.spring.account;
/*
 * AccountService 클래스를 다음과 같이 작성
 * 서비스 클랫의 메소드는 단위 기능을 수행하므로 @Transactional 애너테이션을 서비스 클래스에 
 * 적용해 메소드별로 트랜잭션을 적용
 */
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// @Transactional을 이용해 AccountService 클래스의 모든 메소드에 트랜잭션을 적용
@Transactional(propagation=Propagation.REQUIRED)
public class AccountService {
	private AccountDAO accDAO;
	// 속성 accDAO빈을 주입하기 위해 setter를 구현
	public void setAccDAO(AccountDAO accDAO) {
		this.accDAO = accDAO;
	}
	
	// sendMoney() 메소드 호출 시 accDAO의 두 개의 SQL문을 실행
	public void sendMoney() throws Exception {
		accDAO.updateBalance1();
		accDAO.updateBalance2();
	}
}


