/**
 * ���ϸ� : MemberServiceImpl.java
 * �ۼ��� : 2014. 2. 13.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import casestudy.business.domain.Member;
import casestudy.dataaccess.MemberDaoImpl;

/**
 * ȸ�� ���� ����Ͻ� ������ ������ ���� Ŭ������ 
 * ������ �׼��� ó���� MemberDao ��ü���� �����Ͽ� �����Ѵ�.
 * 
 * @author �����(kidmania@hotmail.com)
 *
 */
public class MemberServiceImpl implements MemberService {

    private MemberDao memberDataAccess;
    
    /* 
     * 1. MemberDaoImpl ��ü�� �����Ͽ� memberDataAccess �ν��Ͻ� ���� �ʱ�ȭ  
     */
    public MemberServiceImpl() {
    	this.memberDataAccess = new MemberDaoImpl();
    }
    
	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#registerMember(casestudy.business.domain.Member)
	 * 
	 * 2. MemberDao ��ü�� ����� ���ڷ� ���� ȸ�� ������ ����Ѵ�.
	 */
	@Override
	public void registerMember(Member member)throws DataDuplicatedException {
		
		if(memberDataAccess.memberIDExists(member.getMemberID())){
			throw new DataDuplicatedException("������ ȸ���� �ֽ��ϴ�.");
		}
		
		memberDataAccess.insertMember(member);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#findMember(java.lang.String)
	 * 
	 * 3. MemberDao ��ü�� ����� ���ڷ� ���� memberID�� �ش��ϴ� ȸ�� ������ ã�Ƽ� �����Ѵ�.
	 */
	@Override
	public Member findMember(String memberID)throws DataNotFoundException {
		
		if(!memberDataAccess.memberIDExists(memberID)){
			throw new DataNotFoundException();
		}
		return memberDataAccess.selectMember(memberID);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#updateMember(casestudy.business.domain.Member)
	 * 
	 * 4. MemberDao ��ü�� ����� ���ڷ� ���� ȸ�� ������ �����Ѵ�.
	 */
	@Override
	public void updateMember(Member member)throws DataNotFoundException  {
		
		if(!memberDataAccess.memberIDExists(member.getMemberID())){
			throw new DataNotFoundException();
		}
		memberDataAccess.updateMember(member);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#removeMember(casestudy.business.domain.Member)
	 * 
	 * 5. MemberDao ��ü�� ����� ���ڷ� ���� ȸ�� ������ �����Ѵ�.
	 */
	@Override
	public void removeMember(Member member)throws DataNotFoundException {
		
		if(!memberDataAccess.memberIDExists(member.getMemberID())){
			throw new DataNotFoundException();
		}
		memberDataAccess.deleteMember(member);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#loginCheck(java.lang.String, java.lang.String)
	 * 
	 * 6. MemberDao ��ü�� ����� ���ڷ� ���� memberID, password�� 
	 *   �α��� ���� ���θ� Ȯ���ϰ�, �ش� ȸ�� ������ �����Ѵ�.
	 */
	@Override
	public Member loginCheck(String memberID, String password) {
		
		return memberDataAccess.checkMember(memberID, password);
		
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#getAllMembers()
	 * 
	 * 7. MemberDao ��ü�� ����� ��� ȸ�� ������ ���ؼ� �����Ѵ�.
	 */
	@Override
	public Member[] getAllMembers() {
		
		return memberDataAccess.selectAllMembers();
	}

}
