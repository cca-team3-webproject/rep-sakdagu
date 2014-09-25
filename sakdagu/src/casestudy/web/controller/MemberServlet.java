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
	 * HTTP GET�� POST ����� ��û�� ��� ó���Ѵ�. 1. ��û�Ķ���� ���� Ȯ���Ͽ� �ش� ȸ���� �������� �����ش�.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");

		// 1.1. action ��û�Ķ���� ���� Ȯ���Ѵ�.
		String action = request.getParameter("action");

		/*
		 * 1.2. action ��(select/register)�� ���� ������ �޼ҵ带 �����Ͽ� ȣ���Ѵ�. (select �̸�
		 * selectMember(), register �̸� registerMember() �޼ҵ� ȣ��)
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
		//1.1 session scope �Ӽ����� ȸ�������� ã�� 
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "�α����� �ʿ��մϴ�.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (member == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"�α����� �ʿ��մϴ�.");
			return;
		}
		
		//1.2 ����Ͻ� ������ ������ memverService ��ü�� �����Ͽ�
		MemberService MemberService = new MemberServiceImpl();
		
		MemberService.removeMember(member);
		
		//1.4
		session.removeAttribute("loginMember");
		session.invalidate();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 3. ���ο� ȸ���� ����ϴ� ��û�� ó���Ѵ�.
	 */
	private void registerMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataDuplicatedException {

		HttpSession session = request.getSession(false);

		// String memberID = loginMember.getMemberID();

		// 3.1. ��û �Ķ���͸� ���� HTML �� �����͸� ����.
		String memberID = request.getParameter("memberID");

		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");
		int point = 0;

		// 3.2. �� �������� ��ȿ���� �����ϴ� ó���� �Ѵ�.
		// 3.2.1. ���� �޽������� ������ ����Ʈ
		List<String> errorMsgs = new ArrayList<String>();

		// 3.2.2. �� �����Ͱ� ��ȿ�� �� ����
		if ((memberID == null) || (memberID.length() == 0)) {
			errorMsgs.add("ȸ�����̵� �Է����ּ���.");
		}
		if ((password == null) || (password.length() == 0)) {
			errorMsgs.add("�н����带 �Է����ּ���.");
		}
		if ((name == null) || (name.length() == 0)) {
			errorMsgs.add("�̸��� �Է����ּ���.");
		}
		if ((email == null) || (email.length() == 0)) {
			errorMsgs.add("�̸����ּҸ� �Է����ּ���.");
		}

		// 3.2.3. ��ȿ���� ���� �����Ͱ� ������
		if (!errorMsgs.isEmpty()) {
			// 3.2.4. ���� ������ request scope �Ӽ��� �����ϰ�
			request.setAttribute("errorMsgs", errorMsgs);
			// 3.2.5. ���� ������ �� ����(userError.jsp)���� ��û�� �����Ѵ�.
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 3.3. ���ο� ȸ���� ����ϴ� ó���� �Ѵ�.
		// 3.3.1. ������ �����͸� ���� Member ��ü�� �����Ͽ�
		Member member = new Member(memberID, password, name, email, tel,
				zipcode, address, point);
		// 3.3.2. MemberService ��ü�� �����Ͽ� ȸ���� ����Ѵ�.
		MemberService MemberService = new MemberServiceImpl();
		MemberService.registerMember(member);
		// 3.3.3. request scope �Ӽ��� ��ϵ� member�� �����ϰ�
		session.setAttribute("loginMember", member);
		request.setAttribute("member", member);

		// 3.3.4. RequestDispatcher ��ü�� ���� �� ����(thankYou.jsp")���� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/thankYou.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 3. ���ο� ȸ���� ����ϴ� ��û�� ó���Ѵ�.
	 */
	private void updateMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		// 4.0 session scope �Ӽ����� ȸ�������� ã�� memberID���� Ȯ���Ѵ�.

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "�α����� �ʿ��մϴ�.");
			return;
		}

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"�α����� �ʿ��մϴ�.");
			return;
		}

		String memberID = loginMember.getMemberID();
		// String memberID = request.getParameter("memberID");
		// 4.1 ��û �Ķ���͸� ���� HTML �� �����͸� ����.
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");

		// 4.2. �� �������� ��ȿ���� �����ϴ� ó���� �Ѵ�.
		// 4.2.1. ���� �޽������� ������ ����Ʈ
		List<String> errorMsgs = new ArrayList<String>();

		// 4.2.2. �� �����Ͱ� ��ȿ�� �� ����
		if ((password == null) || (password.length() == 0)) {
			errorMsgs.add("�н����带 �Է����ּ���.");
		}
		if ((name == null) || (name.length() == 0)) {
			errorMsgs.add("�̸��� �Է����ּ���.");
		}
		if ((email == null) || (email.length() == 0)) {
			errorMsgs.add("�̸����ּҸ� �Է����ּ���.");
		}

		// 4.2.3. ��ȿ���� ���� �����Ͱ� ������
		if (!errorMsgs.isEmpty()) {
			// 4.2.4. ���� ������ request scope �Ӽ��� �����ϰ�
			request.setAttribute("errorMsgs", errorMsgs);
			// 4.2.5. ���� ������ �� ����(userError.jsp)���� ��û�� �����Ѵ�.
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 4.3. ���ο� ȸ���� ����ϴ� ó���� �Ѵ�.
		// 4.3.1. ������ �����͸� ���� Member ��ü�� �����Ͽ�
		Member member = new Member(memberID, password, name, email, tel,
				zipcode, address);
		// 4.3.2. MemberService ��ü�� �����Ͽ� ȸ���� ����Ѵ�.
		MemberService MemberService = new MemberServiceImpl();
		MemberService.updateMember(member);
		// 4.3.3. session scope �Ӽ��� ����� ������ ȸ�� ������ ���� �˻��� ȸ�� ������ ����
		// request.setAttribute("member", member);
		session.setAttribute("loginMember", member);

		// 4.3.4. RequestDispatcher ��ü�� ���� �� ����(thankYou.jsp")���� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/thankYou.jsp");
		dispatcher.forward(request, response);
	}

	private void selectMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		/*
		 * // 1.1. memberID ��û�Ķ���� ���� Ȯ���Ѵ�. String memberID =
		 * request.getParameter("memberID"); if ((memberID == null) ||
		 * (memberID.length() == 0)) { memberID = "duke"; }
		 */

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "�α����� �ʿ��մϴ�.");
			return;
		}

		Member member = (Member) session.getAttribute("loginMember");
		if (member == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"�α����� �ʿ��մϴ�.");
			return;
		}

		String memberID = member.getMemberID();

		// 1.2. ����Ͻ� ������ ������ MemberService ��ü�� �����Ͽ�
		MemberService memberService = new MemberServiceImpl();

		// 1.3. memberID�� ȸ���� �˻��Ѵ�.(MemberService�� findMember() ���)
		Member selectedMember = memberService.findMember(memberID);

		// 1.4 ���䳻���� ���� view �������� �����Ͽ� ��û�� �����Ѵ�.
		// 1.4.1 session scope �Ӽ��� ����� ������ ȸ�� ������ ���� �˻��� ȸ�� ������ ����
		session.setAttribute("loginMember", selectedMember);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/updateMember.jsp");
		dispatcher.forward(request, response);

	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.1. memberID password��û�Ķ���� ���� Ȯ���Ѵ�.
		String memberID = request.getParameter("memberID");
		String password = request.getParameter("password");

		// 1.2. ����Ͻ� ������ ������ MemberService ��ü�� �����Ͽ�
		MemberService memberService = new MemberServiceImpl();

		// 1.3. memberID�� ȸ���� �˻��Ѵ�.(MemberService�� findMember() ���)
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
				loginErrorMsg = "���̵� �������� �ʽ��ϴ�.";
			} else if (check == Member.INVALID_PASSWORD) {
				loginErrorMsg = "�н����尡 ��ġ���� �ʽ��ϴ�.";
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
