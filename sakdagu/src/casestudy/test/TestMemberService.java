package casestudy.test;

import casestudy.business.domain.Member;
import casestudy.business.service.DataDuplicatedException;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.MemberService;
import casestudy.business.service.MemberServiceImpl;

public class TestMemberService {
    
    public static void main(String[] args) throws DataDuplicatedException, DataNotFoundException {
    	
        MemberService service = new MemberServiceImpl();
        
        /**** Register Member  ****/
        Member tempMember = new Member("lily199iu", "1234", "이지은", "iu@iloen.com", "02-1234-5678", "135-882", "서울 강남구 삼성동", 1000);
        service.registerMember(tempMember);
        System.out.println();
        
        /**** Find Member  ****/
        Member selectedMember = service.findMember("lily199iu");
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
        service.updateMember(tempMember);
        System.out.println(service.findMember("lily199iu"));
        System.out.println();
        
        /**** Login Check  ****/       
        Member loginMember = service.loginCheck("lily199iu", "123456");
        System.out.println(loginMember.getCheck() == Member.VALID_MEMBER);
        System.out.println();
        
        loginMember = service.loginCheck("lily199iu","iuzzang");
        System.out.println(loginMember.getCheck() == Member.INVALID_PASSWORD);
        System.out.println();
         
        loginMember = service.loginCheck("joecool","snoppy");
        System.out.println(loginMember.getCheck() == Member.INVALID_ID);
        System.out.println();
        
        /**** Get All Members ****/
        Member[] memberList = service.getAllMembers();
        
        for (Member member : memberList) {
            System.out.println(member);
        }
        System.out.println();

        /**** Remove Member  ****/
        service.removeMember(tempMember);
        
    }
    
}
