package com.spring.account;
/*
 * AccountService Ŭ������ ������ ���� �ۼ�
 * ���� Ŭ���� �޼ҵ�� ���� ����� �����ϹǷ� @Transactional �ֳ����̼��� ���� Ŭ������ 
 * ������ �޼ҵ庰�� Ʈ������� ����
 */
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// @Transactional�� �̿��� AccountService Ŭ������ ��� �޼ҵ忡 Ʈ������� ����
@Transactional(propagation=Propagation.REQUIRED)
public class AccountService {
	private AccountDAO accDAO;
	// �Ӽ� accDAO���� �����ϱ� ���� setter�� ����
	public void setAccDAO(AccountDAO accDAO) {
		this.accDAO = accDAO;
	}
	
	// sendMoney() �޼ҵ� ȣ�� �� accDAO�� �� ���� SQL���� ����
	public void sendMoney() throws Exception {
		accDAO.updateBalance1();
		accDAO.updateBalance2();
	}
}


