package casestudy.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import casestudy.business.domain.Member;
import casestudy.business.service.DataDuplicatedException;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.MemberService;
import casestudy.business.service.MemberServiceImpl;

/**
 * Servlet implementation class MemberServlet
 */
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * HTTP GET과 POST 방식의 요청을 모두 처리한다. 1. 요청파라미터 값을 확인하여 해당 회원의 상세정보를 보여준다.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");

		// 1.1. action 요청파라미터 값을 확인한다.
		String action = request.getParameter("action");

		/*
		 * 1.2. action 값(select/register)에 따라 적절한 메소드를 선택하여 호출한다. (select 이면
		 * selectMember(), register 이면 registerMember() 메소드 호출)
		 */
		try {
			if (action.equals("select")) {
				selectMember(request, response);
			} else if (action.equals("register")) {
				registerMember(request, response);
			} else if (action.equals("update")) {
				updateMember(request, response);
			} else if (action.equals("login")) {
				login(request, response);
			} else if (action.equals("logout")) {
				logout(request, response);

			} else if (action.equals("remove")) {
				removeMember(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		}
	}

	private void removeMember(HttpServletRequest request, HttpServletResponse response) throws IOException, DataNotFoundException, ServletException {
		//1.1 session scope 속성에서 회원정보를 찾아 
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (member == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"로그인이 필요합니다.");
			return;
		}
		
		//1.2 비즈니스 로직을 수행할 memverService 객체를 생성하여
		MemberService MemberService = new MemberServiceImpl();
		
		MemberService.removeMember(member);
		
		//1.4
		session.removeAttribute("loginMember");
		session.invalidate();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 3. 새로운 회원을 등록하는 요청을 처리한다.
	 */
	private void registerMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataDuplicatedException {

		HttpSession session = request.getSession(false);

		// String memberID = loginMember.getMemberID();

		// 3.1. 요청 파라미터를 통해 HTML 폼 데이터를 얻어낸다.
		String memberID = request.getParameter("memberID");

		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");
		int point = 0;

		// 3.2. 폼 데이터의 유효성을 검증하는 처리를 한다.
		// 3.2.1. 에러 메시지들을 저장할 리스트
		List<String> errorMsgs = new ArrayList<String>();

		// 3.2.2. 폼 데이터가 유효한 지 검증
		if ((memberID == null) || (memberID.length() == 0)) {
			errorMsgs.add("회원아이디를 입력해주세요.");
		}
		if ((password == null) || (password.length() == 0)) {
			errorMsgs.add("패스워드를 입력해주세요.");
		}
		if ((name == null) || (name.length() == 0)) {
			errorMsgs.add("이름을 입력해주세요.");
		}
		if ((email == null) || (email.length() == 0)) {
			errorMsgs.add("이메일주소를 입력해주세요.");
		}

		// 3.2.3. 유효하지 않은 데이터가 있으면
		if (!errorMsgs.isEmpty()) {
			// 3.2.4. 에러 내용을 request scope 속성에 저장하고
			request.setAttribute("errorMsgs", errorMsgs);
			// 3.2.5. 에러 페이지 뷰 서블릿(userError.jsp)으로 요청을 전달한다.
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 3.3. 새로운 회원을 등록하는 처리를 한다.
		// 3.3.1. 적절한 데이터를 가진 Member 객체를 생성하여
		Member member = new Member(memberID, password, name, email, tel,
				zipcode, address, point);
		// 3.3.2. MemberService 객체에 위임하여 회원을 등록한다.
		MemberService MemberService = new MemberServiceImpl();
		MemberService.registerMember(member);
		// 3.3.3. request scope 속성에 등록된 member를 저장하고
		session.setAttribute("loginMember", member);
		request.setAttribute("member", member);

		// 3.3.4. RequestDispatcher 객체를 통해 뷰 서블릿(thankYou.jsp")으로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/thankYou.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 3. 새로운 회원을 등록하는 요청을 처리한다.
	 */
	private void updateMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		// 4.0 session scope 속성에서 회원정보를 찾아 memberID값을 확인한다.

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
			return;
		}

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"로그인이 필요합니다.");
			return;
		}

		String memberID = loginMember.getMemberID();
		// String memberID = request.getParameter("memberID");
		// 4.1 요청 파라미터를 통해 HTML 폼 데이터를 얻어낸다.
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");

		// 4.2. 폼 데이터의 유효성을 검증하는 처리를 한다.
		// 4.2.1. 에러 메시지들을 저장할 리스트
		List<String> errorMsgs = new ArrayList<String>();

		// 4.2.2. 폼 데이터가 유효한 지 검증
		if ((password == null) || (password.length() == 0)) {
			errorMsgs.add("패스워드를 입력해주세요.");
		}
		if ((name == null) || (name.length() == 0)) {
			errorMsgs.add("이름을 입력해주세요.");
		}
		if ((email == null) || (email.length() == 0)) {
			errorMsgs.add("이메일주소를 입력해주세요.");
		}

		// 4.2.3. 유효하지 않은 데이터가 있으면
		if (!errorMsgs.isEmpty()) {
			// 4.2.4. 에러 내용을 request scope 속성에 저장하고
			request.setAttribute("errorMsgs", errorMsgs);
			// 4.2.5. 에러 페이지 뷰 서블릿(userError.jsp)으로 요청을 전달한다.
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 4.3. 새로운 회원을 등록하는 처리를 한다.
		// 4.3.1. 적절한 데이터를 가진 Member 객체를 생성하여
		Member member = new Member(memberID, password, name, email, tel,
				zipcode, address);
		// 4.3.2. MemberService 객체에 위임하여 회원을 등록한다.
		MemberService MemberService = new MemberServiceImpl();
		MemberService.updateMember(member);
		// 4.3.3. session scope 속성에 저장된 기존의 회원 정보를 새로 검색된 회원 정보로 변경
		// request.setAttribute("member", member);
		session.setAttribute("loginMember", member);

		// 4.3.4. RequestDispatcher 객체를 통해 뷰 서블릿(thankYou.jsp")으로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/thankYou.jsp");
		dispatcher.forward(request, response);
	}

	private void selectMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		/*
		 * // 1.1. memberID 요청파라미터 값을 확인한다. String memberID =
		 * request.getParameter("memberID"); if ((memberID == null) ||
		 * (memberID.length() == 0)) { memberID = "duke"; }
		 */

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
			return;
		}

		Member member = (Member) session.getAttribute("loginMember");
		if (member == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"로그인이 필요합니다.");
			return;
		}

		String memberID = member.getMemberID();

		// 1.2. 비즈니스 로직을 수행할 MemberService 객체를 생성하여
		MemberService memberService = new MemberServiceImpl();

		// 1.3. memberID로 회원를 검색한다.(MemberService의 findMember() 사용)
		Member selectedMember = memberService.findMember(memberID);

		// 1.4 응답내용을 만들 view 페이지를 선택하여 요청을 전달한다.
		// 1.4.1 session scope 속성에 저장된 기존의 회원 정보를 새로 검색된 회원 정보로 변경
		session.setAttribute("loginMember", selectedMember);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/updateMember.jsp");
		dispatcher.forward(request, response);

	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.1. memberID password요청파라미터 값을 확인한다.
		String memberID = request.getParameter("memberID");
		String password = request.getParameter("password");

		// 1.2. 비즈니스 로직을 수행할 MemberService 객체를 생성하여
		MemberService memberService = new MemberServiceImpl();

		// 1.3. memberID로 회원를 검색한다.(MemberService의 findMember() 사용)
		Member member = memberService.loginCheck(memberID, password);

		int check = member.getCheck();

		if (check == Member.VALID_MEMBER) {

			HttpSession session = request.getSession(true);
			session.setAttribute("loginMember", member);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);

		} else {
			String loginErrorMsg = null;
			if (check == Member.INVALID_ID) {
				loginErrorMsg = "아이디가 존재하지 않습니다.";
			} else if (check == Member.INVALID_PASSWORD) {
				loginErrorMsg = "패스워드가 일치하지 않습니다.";
			}

			request.setAttribute("loginErrorMsg", loginErrorMsg);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.removeAttribute("loginMember");
			session.invalidate();
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
