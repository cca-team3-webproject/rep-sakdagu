package casestudy.web.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import casestudy.business.domain.Member;
import casestudy.business.domain.Product;
import casestudy.business.service.DataDuplicatedException;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.ProductService;
import casestudy.business.service.ProductServiceImpl;

import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * HTTP GET과 POST 방식의 요청을 모두 처리한다. 1. 요청파라미터 값을 확인하여 적절한 사용자의 요청을 처리한다.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");

		// 1.1. action 요청파라미터 값을 확인한다.
		String action = request.getParameter("action");

		/*
		 * 1.2. action 값(select/select-all)에 따라 적절한 메소드를 선택하여 호출한다. (select 이면
		 * selectProduct(), select-all 이면 selectAllProducts() 메소드 호출)
		 */
		try {
			if (action.equals("select")) {
				selectProduct(request, response);
			} else if (action.equals("select-all")) {
				selectAllProducts(request, response);
			} else if (action.equals("register")) {
				registerProduct(request, response);
			} else if (action.equals("register2")) {
				registerProduct2(request, response);
			}
			else if (action.equals("register2")) {
				removeProduct(request, response);
			}
			
			
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}
	}

	private void registerProduct(HttpServletRequest request,
			HttpServletResponse response) throws DataDuplicatedException, IOException, ServletException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (!member.getMemberID().equals("duke")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"duke계정이 필요합니다.");
			return;
		}
		
		// String memberID = loginMember.getMemberID();

		// 3.1. 요청 파라미터를 통해 HTML 폼 데이터를 얻어낸다.
		 String productID = request.getParameter("productID");
		 String mallID = request.getParameter("mallID");
		 String productName = request.getParameter("productName");
		 String company = request.getParameter("company");
		 int price1 = Integer.parseInt(request.getParameter("price1"));
		 int price2 = Integer.parseInt(request.getParameter("price2"));
		 String installment = request.getParameter("installment");
		 String keyword = request.getParameter("keyword");
		 String detail = request.getParameter("detail");
		 Date productDate = new Date(System.currentTimeMillis());
		 String photoDir = request.getParameter("photoDir");

		// 3.2. 폼 데이터의 유효성을 검증하는 처리를 한다.
		// 3.2.1. 에러 메시지들을 저장할 리스트
		List<String> errorMsgs = new ArrayList<String>();

		// 3.2.2. 폼 데이터가 유효한 지 검증
		if ((productID == null) || (productID.length() == 0)) {
			errorMsgs.add("상품아이디를 입력해주세요.");
		}
		if ((mallID == null) || (mallID.length() == 0)) {
			errorMsgs.add("제조사아이디를 입력해주세요.");
		}
		if ((productName == null) || (productName.length() == 0)) {
			errorMsgs.add("제품명을 입력해주세요.");
		}
		if ((company == null) || (company.length() == 0)) {
			errorMsgs.add("제조사명을 입력해주세요.");
		}
		if (price1 <= 0) {
			errorMsgs.add("일반가격을 입력해주세요.");
		}
		if (price2 <= 0) {
			errorMsgs.add("판매가격을 입력해주세요.");
		}
		if ((keyword == null) || (keyword.length() == 0)) {
			errorMsgs.add("키워드를 입력해주세요.");
		}
		if ((detail == null) || (detail.length() == 0)) {
			errorMsgs.add("설명을 입력해주세요.");
		}
		if ((photoDir == null) || (photoDir.length() == 0)) {
			errorMsgs.add("사진경로를 입력해주세요.");
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
		Product product = new Product(productID, mallID, productName, company, price1, price2, installment, keyword, detail, productDate, photoDir);
		
		// 3.3.2. MemberService 객체에 위임하여 회원을 등록한다.
		ProductService ProductService = new ProductServiceImpl();
		ProductService.registerProduct(product);
		// 3.3.3. request scope 속성에 등록된 product를 저장하고
//		session.setAttribute("loginMember", product);
		request.setAttribute("selectedProduct", product);

		// 3.3.4. RequestDispatcher 객체를 통해 뷰 서블릿(sucessProductAdd.jsp")으로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/sucessProductAdd.jsp");
		dispatcher.forward(request, response);
	}

	private void registerProduct2(HttpServletRequest request,
			HttpServletResponse response) throws DataDuplicatedException, IOException, ServletException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (!member.getMemberID().equals("duke")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"duke계정이 필요합니다.");
			return;
		}
		
		String upPath = getServletContext().getRealPath("/images");
		int mb = 10;
		MultipartRequest mr = new MultipartRequest(request, upPath, mb*1024*1024,"utf-8");
		
		
		// 3.1. 요청 파라미터를 통해 HTML 폼 데이터를 얻어낸다.
		 String productID = mr.getParameter("productID");
		 String mallID = mr.getParameter("mallID");
		 String productName = mr.getParameter("productName");
		 String company = mr.getParameter("company");
		 int price1 = Integer.parseInt(mr.getParameter("price1"));
		 int price2 = Integer.parseInt(mr.getParameter("price2"));
		 String installment = mr.getParameter("installment");
		 String keyword = mr.getParameter("keyword");
		 String detail = mr.getParameter("detail");
		 Date productDate = new Date(System.currentTimeMillis());
		 String photoDir = mr.getFilesystemName("photoDir");

		// 3.2. 폼 데이터의 유효성을 검증하는 처리를 한다.
		// 3.2.1. 에러 메시지들을 저장할 리스트
		List<String> errorMsgs = new ArrayList<String>();

		// 3.2.2. 폼 데이터가 유효한 지 검증
		if ((productID == null) || (productID.length() == 0)) {
			errorMsgs.add("상품아이디를 입력해주세요.");
		}
		if ((mallID == null) || (mallID.length() == 0)) {
			errorMsgs.add("제조사아이디를 입력해주세요.");
		}
		if ((productName == null) || (productName.length() == 0)) {
			errorMsgs.add("제품명을 입력해주세요.");
		}
		if ((company == null) || (company.length() == 0)) {
			errorMsgs.add("제조사명을 입력해주세요.");
		}
		if (price1 <= 0) {
			errorMsgs.add("일반가격을 입력해주세요.");
		}
		if (price2 <= 0) {
			errorMsgs.add("판매가격을 입력해주세요.");
		}
		if ((keyword == null) || (keyword.length() == 0)) {
			errorMsgs.add("키워드를 입력해주세요.");
		}
		if ((detail == null) || (detail.length() == 0)) {
			errorMsgs.add("설명을 입력해주세요.");
		}
		if ((photoDir == null) || (photoDir.length() == 0)) {
			errorMsgs.add("사진경로를 입력해주세요."+photoDir);
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
		Product product = new Product(productID, mallID, productName, company, price1, price2, installment, keyword, detail, productDate, photoDir);
		
		// 3.3.2. MemberService 객체에 위임하여 회원을 등록한다.
		ProductService ProductService = new ProductServiceImpl();
		ProductService.registerProduct(product);
		// 3.3.3. request scope 속성에 등록된 product를 저장하고
//		session.setAttribute("loginMember", product);
		request.setAttribute("selectedProduct", product);

		// 3.3.4. RequestDispatcher 객체를 통해 뷰 서블릿(sucessProductAdd.jsp")으로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/sucessProductAdd.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 하나의 상품 상세정보를 보여주는 요청을 처리한다.
	 */
	private void selectProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		// 요청파라미터 값을 확인하고 검증
		String productID = request.getParameter("productID");
		if ((productID == null) || (productID.length() == 0)) {
			productID = "sams110";
		}

		// 서비스 객체를 통해 적절한 비즈니스 로직을 수행
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProduct(productID);

		// 응답 페이지 출력
		request.setAttribute("selectedProduct", product);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("selectProduct.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 모든 상품 리스트를 보여주는 요청을 처리한다.
	 */
	private void selectAllProducts(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ProductService productService = new ProductServiceImpl();
		Product[] productList = productService.getAllProducts();

		request.setAttribute("productList", productList);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("selectAllProducts.jsp");
		dispatcher.forward(request, response);

	}
	private void removeProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, DataNotFoundException, ServletException {
		//1.1 session scope 속성에서 회원정보를 찾아 
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (!member.getMemberID().equals("duke")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"duke계정이 필요합니다.");
			return;
		}
		
		Product product = (Product) request.getAttribute("selectedProduct");
		
		
		//1.2 비즈니스 로직을 수행할 memverService 객체를 생성하여
		ProductService productService = new ProductServiceImpl();
		
		productService.removeProduct(product);
		
		//1.4
		session.removeAttribute("loginMember");
		session.invalidate();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
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
