package casestudy.test;

import casestudy.business.domain.Member;
import casestudy.business.service.MemberDao;
import casestudy.dataaccess.MemberDaoImpl;

public class TestMemberDao {
    
    public static void main(String[] args) {
    	
        MemberDao memberDao = new MemberDaoImpl();
        
        /**** Insert Member  ****/
        Member tempMember = new Member("lily199iu", "1234", "이지은", "iu@iloen.com", "02-1234-5678", "135-882", "서울 강남구 삼성동", 1000);
        memberDao.insertMember(tempMember);
        System.out.println();
        
        /**** Select Member  ****/
        Member selectedMember = memberDao.selectMember("lily199iu");
        System.out.println("MemberID: " + selectedMember.getMemberID());
        System.out.println("Password: " + selectedMember.getPassword());
        System.out.println("Name: " + selectedMember.getName());
        System.out.println("Tel: " + selectedMember.getTel());
        System.out.println("Zipcode: " + selectedMember.getZipcode());
        System.out.println("Address: " + selectedMember.getAddress());
        System.out.println("Point: " + selectedMember.getPoint());
        System.out.println("MemberDate: " + selectedMember.getMemberDate());
        System.out.println();
        
        /**** Update Member  ****/
        tempMember = new Member("lily199iu", "123456", "아이유", "lily199iu@iloen.com", "02-4321-4567", "135-859", "서울 강남구 도곡동");
        memberDao.updateMember(tempMember);
        System.out.println(memberDao.selectMember("lily199iu"));
        System.out.println();
        
        /**** Check Member(Login)  ****/       
        Member loginMember = memberDao.checkMember("lily199iu", "123456");
        System.out.println(loginMember.getCheck() == Member.VALID_MEMBER);
        System.out.println();
        
        loginMember = memberDao.checkMember("lily199iu","iuzzang");
        System.out.println(loginMember.getCheck() == Member.INVALID_PASSWORD);
        System.out.println();
         
        loginMember = memberDao.checkMember("joecool","snoppy");
        System.out.println(loginMember.getCheck() == Member.INVALID_ID);
        System.out.println();
        
        /**** Select All Members ****/
        Member[] memberList = memberDao.selectAllMembers();
        
        for (Member member : memberList) {
            System.out.println(member);
        }
        System.out.println();
        
        /**** Member Exists ****/       
        boolean result = memberDao.memberIDExists("lily199iu");
        System.out.println(result);        
        
        /**** Delete Member  ****/
        memberDao.deleteMember(tempMember);
        
        result = memberDao.memberIDExists("lily199iu");
        System.out.println(result);
        
    }
    
}
