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
	 * HTTP GET�� POST ����� ��û�� ��� ó���Ѵ�. 1. ��û�Ķ���� ���� Ȯ���Ͽ� ������ ������� ��û�� ó���Ѵ�.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");

		// 1.1. action ��û�Ķ���� ���� Ȯ���Ѵ�.
		String action = request.getParameter("action");

		/*
		 * 1.2. action ��(select/select-all)�� ���� ������ �޼ҵ带 �����Ͽ� ȣ���Ѵ�. (select �̸�
		 * selectProduct(), select-all �̸� selectAllProducts() �޼ҵ� ȣ��)
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
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "�α����� �ʿ��մϴ�.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (!member.getMemberID().equals("duke")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"duke������ �ʿ��մϴ�.");
			return;
		}
		
		// String memberID = loginMember.getMemberID();

		// 3.1. ��û �Ķ���͸� ���� HTML �� �����͸� ����.
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

		// 3.2. �� �������� ��ȿ���� �����ϴ� ó���� �Ѵ�.
		// 3.2.1. ���� �޽������� ������ ����Ʈ
		List<String> errorMsgs = new ArrayList<String>();

		// 3.2.2. �� �����Ͱ� ��ȿ�� �� ����
		if ((productID == null) || (productID.length() == 0)) {
			errorMsgs.add("��ǰ���̵� �Է����ּ���.");
		}
		if ((mallID == null) || (mallID.length() == 0)) {
			errorMsgs.add("��������̵� �Է����ּ���.");
		}
		if ((productName == null) || (productName.length() == 0)) {
			errorMsgs.add("��ǰ���� �Է����ּ���.");
		}
		if ((company == null) || (company.length() == 0)) {
			errorMsgs.add("��������� �Է����ּ���.");
		}
		if (price1 <= 0) {
			errorMsgs.add("�Ϲݰ����� �Է����ּ���.");
		}
		if (price2 <= 0) {
			errorMsgs.add("�ǸŰ����� �Է����ּ���.");
		}
		if ((keyword == null) || (keyword.length() == 0)) {
			errorMsgs.add("Ű���带 �Է����ּ���.");
		}
		if ((detail == null) || (detail.length() == 0)) {
			errorMsgs.add("������ �Է����ּ���.");
		}
		if ((photoDir == null) || (photoDir.length() == 0)) {
			errorMsgs.add("������θ� �Է����ּ���.");
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
		Product product = new Product(productID, mallID, productName, company, price1, price2, installment, keyword, detail, productDate, photoDir);
		
		// 3.3.2. MemberService ��ü�� �����Ͽ� ȸ���� ����Ѵ�.
		ProductService ProductService = new ProductServiceImpl();
		ProductService.registerProduct(product);
		// 3.3.3. request scope �Ӽ��� ��ϵ� product�� �����ϰ�
//		session.setAttribute("loginMember", product);
		request.setAttribute("selectedProduct", product);

		// 3.3.4. RequestDispatcher ��ü�� ���� �� ����(sucessProductAdd.jsp")���� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/sucessProductAdd.jsp");
		dispatcher.forward(request, response);
	}

	private void registerProduct2(HttpServletRequest request,
			HttpServletResponse response) throws DataDuplicatedException, IOException, ServletException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "�α����� �ʿ��մϴ�.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (!member.getMemberID().equals("duke")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"duke������ �ʿ��մϴ�.");
			return;
		}
		
		String upPath = getServletContext().getRealPath("/images");
		int mb = 10;
		MultipartRequest mr = new MultipartRequest(request, upPath, mb*1024*1024,"utf-8");
		
		
		// 3.1. ��û �Ķ���͸� ���� HTML �� �����͸� ����.
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

		// 3.2. �� �������� ��ȿ���� �����ϴ� ó���� �Ѵ�.
		// 3.2.1. ���� �޽������� ������ ����Ʈ
		List<String> errorMsgs = new ArrayList<String>();

		// 3.2.2. �� �����Ͱ� ��ȿ�� �� ����
		if ((productID == null) || (productID.length() == 0)) {
			errorMsgs.add("��ǰ���̵� �Է����ּ���.");
		}
		if ((mallID == null) || (mallID.length() == 0)) {
			errorMsgs.add("��������̵� �Է����ּ���.");
		}
		if ((productName == null) || (productName.length() == 0)) {
			errorMsgs.add("��ǰ���� �Է����ּ���.");
		}
		if ((company == null) || (company.length() == 0)) {
			errorMsgs.add("��������� �Է����ּ���.");
		}
		if (price1 <= 0) {
			errorMsgs.add("�Ϲݰ����� �Է����ּ���.");
		}
		if (price2 <= 0) {
			errorMsgs.add("�ǸŰ����� �Է����ּ���.");
		}
		if ((keyword == null) || (keyword.length() == 0)) {
			errorMsgs.add("Ű���带 �Է����ּ���.");
		}
		if ((detail == null) || (detail.length() == 0)) {
			errorMsgs.add("������ �Է����ּ���.");
		}
		if ((photoDir == null) || (photoDir.length() == 0)) {
			errorMsgs.add("������θ� �Է����ּ���."+photoDir);
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
		Product product = new Product(productID, mallID, productName, company, price1, price2, installment, keyword, detail, productDate, photoDir);
		
		// 3.3.2. MemberService ��ü�� �����Ͽ� ȸ���� ����Ѵ�.
		ProductService ProductService = new ProductServiceImpl();
		ProductService.registerProduct(product);
		// 3.3.3. request scope �Ӽ��� ��ϵ� product�� �����ϰ�
//		session.setAttribute("loginMember", product);
		request.setAttribute("selectedProduct", product);

		// 3.3.4. RequestDispatcher ��ü�� ���� �� ����(sucessProductAdd.jsp")���� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/sucessProductAdd.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * �ϳ��� ��ǰ �������� �����ִ� ��û�� ó���Ѵ�.
	 */
	private void selectProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		// ��û�Ķ���� ���� Ȯ���ϰ� ����
		String productID = request.getParameter("productID");
		if ((productID == null) || (productID.length() == 0)) {
			productID = "sams110";
		}

		// ���� ��ü�� ���� ������ ����Ͻ� ������ ����
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProduct(productID);

		// ���� ������ ���
		request.setAttribute("selectedProduct", product);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("selectProduct.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * ��� ��ǰ ����Ʈ�� �����ִ� ��û�� ó���Ѵ�.
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
		//1.1 session scope �Ӽ����� ȸ�������� ã�� 
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "�α����� �ʿ��մϴ�.");
			return;
		}
		
		Member member = (Member) session.getAttribute("loginMember");
		if (!member.getMemberID().equals("duke")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"duke������ �ʿ��մϴ�.");
			return;
		}
		
		Product product = (Product) request.getAttribute("selectedProduct");
		
		
		//1.2 ����Ͻ� ������ ������ memverService ��ü�� �����Ͽ�
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
