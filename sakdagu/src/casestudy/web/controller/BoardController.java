package casestudy.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import casestudy.business.domain.Board;
import casestudy.business.domain.Member;
import casestudy.business.domain.Product;
import casestudy.business.domain.productOption;
import casestudy.business.domain.productPhoto;
import casestudy.business.service.BoardService;
import casestudy.business.service.BoardServiceImpl;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.MemberService;
import casestudy.business.service.MemberServiceImpl;
import casestudy.business.service.ProductService;
import casestudy.business.service.ProductServiceImpl;
import casestudy.dataaccess.photoDao;
import casestudy.util.PageHandler;

/**
 * Servlet implementation class BoardController
 */
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * HTTP GET�� POST ����� ��û�� ��� ó���Ѵ�. ��û�Ķ���� ���� Ȯ���Ͽ� ������ ������� ��û�� ó���Ѵ�.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// action ��û�Ķ���� ���� Ȯ���Ѵ�.
			/* String action = request.getParameter("action"); */
			String action = request.getPathInfo();

			// action ���� ���� ������ �޼ҵ带 �����Ͽ� ȣ���Ѵ�.
			if (action.equals("/list")) {
				selectBoardList(request, response);
			} else if (action.equals("/images")) {
				selectImageList(request, response);
			} else if (action.equals("/read")) {
				readBoard(request, response);
			} else if (action.equals("/img")) {
				viewImage(request, response);
			} else if (action.equals("/writeForm")) {
				writeBoardForm(request, response);
			} else if (action.equals("/write")) {
				// writeBoard(request, response);
				saveImage(request, response);
			} else if (action.equals("/updateForm")) {
				updateBoardForm(request, response);
			} else if (action.equals("/update")) {
				updateBoard(request, response);
			} else if (action.equals("/remove")) {
				removeBoard(request, response);
			} else if (action.equals("/selectProduct")) {
				selectProduct(request, response);
			} else if (action.equals("/selectOption")) {
				selectOption(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	// Json ���� ����
	private void selectProduct(HttpServletRequest request,
			HttpServletResponse response) throws DataNotFoundException,
			IOException {
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		int productID = Integer.parseInt(request.getParameter("productID"));
		/* String optionID = request.getParameter("optionID"); */
		ProductService service = new ProductServiceImpl();
		productOption[] options = service.findOptions(boardNum, productID);
		JSONObject j;
		JSONArray arr = new JSONArray();
		for (productOption option : options) {
			j = new JSONObject();
			j.put("optionID", option.getOptionID());
			j.put("optionTitle", option.getOptionTitle());
			j.put("price1", option.getPrice1());
			j.put("price2", option.getPrice2());
			j.put("quantity", option.getQuantity());

			arr.add(j);
		}
		PrintWriter os = response.getWriter();
		os.println(arr);
	}

	@SuppressWarnings("unchecked")
	// Json ���� ����
	private void selectOption(HttpServletRequest request,
			HttpServletResponse response) throws DataNotFoundException,
			IOException {
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		int productID = Integer.parseInt(request.getParameter("productID"));
		int optionID = Integer.parseInt(request.getParameter("optionID"));
		ProductService service = new ProductServiceImpl();
		productOption option = service
				.findOption(boardNum, productID, optionID);
		JSONObject j;
		j = new JSONObject();
		j.put("optionID", option.getOptionID());
		j.put("optionTitle", option.getOptionTitle());
		j.put("price1", option.getPrice1());
		j.put("price2", option.getPrice2());
		j.put("quantity", option.getQuantity());

		PrintWriter os = response.getWriter();
		os.println(j);
	}

	/*
	 * ���ǿ� �´� ��� �Խù� ����� �����ִ� ��û�� ó���Ѵ�.
	 */
	private void selectBoardList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");
		if (category == null || category.length() == 0) {
			category = (String) request.getAttribute("category");
			if (category == null || category.length() == 0) {
				category = "����Ʈ";
			}
		}
		String[] subCategoryList = subCategoryList(category);

		request.setAttribute("subCategoryList", subCategoryList);
		// 6. RequestDispatcher ��ü�� ���� �� ������(list.jsp)�� ��û�� �����Ѵ�.

		RequestDispatcher dispatcher;
		dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/imageList.jsp");

		dispatcher.forward(request, response);
	}

	private void selectImageList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1. searchType, searchText ��û�Ķ���� ���� ���Ѵ�.
		String searchText = request.getParameter("searchText");
		String category = request.getParameter("category");
		if (category == null || category.length() == 0) {
			category = (String) request.getAttribute("category");
			if (category == null || category.length() == 0) {
				category = "����Ʈ";
			}
		}

		String subCategory = request.getParameter("subCategory");
		// 1.2 pageNumber ��û �Ķ���� ���� ���Ѵ�.
		String pageNumber = request.getParameter("pageNumber");

		// (1) ���� ������ ��ȣ
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		// 1.2 �˻� �ɼ��� ��� �ִ� Map ��ü�� �����Ͽ� searchType, searchText ���� �����Ѵ�.
		Map<String, Object> searchInfo = new HashMap<String, Object>();

		// ���� �������� �Խñ� ��Ͽ��� ó�� ������ �Խñ��� �� ��ȣ
		int startRow = PageHandler.getStartRow(currentPageNumber);

		// ���� �������� �Խñ� ��Ͽ��� �������� ������ �Խñ��� �� ��ȣ
		int endRow = PageHandler.getEndRow(currentPageNumber);
		// 3.1 �˻��ɼ� Map() startRow�� endRow ���� �����Ѵ�.
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);

		searchInfo.put("searchText", searchText);

		searchInfo.put("category", category);
		searchInfo.put("subCategory", subCategory);

		// 2. BoardService ��ü�� �����Ѵ�.
		BoardService service = new BoardServiceImpl();
		// ��ü �Խñ� ����
		int totalBoardCount = service.getBoardCount(searchInfo);

		// ��ü ������ ����
		int totalPageCount = PageHandler.getTotalPageCount(totalBoardCount);

		// ������ ���� �ٿ� ǥ�õ� ���� ������ ��ȣ
		int startPageNumber = PageHandler.getStartPageNumber(currentPageNumber);

		// ������ ���� �ٿ� ǥ�õ� �� ������ ��ȣ
		int endPageNumber = PageHandler.getEndPageNumber(startPageNumber,
				totalBoardCount);

		// 4. BoardService ��ü�κ��� ��� �Խñ� ����Ʈ�� ���ؿ´�.
		Board[] boardList = service.getBoardList(searchInfo);
		String[] subCategoryList = subCategoryList(category);
		// 5. request scope �Ӽ�(boardList)�� �Խñ� ����Ʈ�� �����Ѵ�.
		request.setAttribute("boardList", boardList);
		request.setAttribute("subCategoryList", subCategoryList);

		// 2. request scope �Ӽ����� searchType, searchText�� �����Ѵ�.
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);
		// 4.2 request scope �Ӽ����� currentPageNumber, startPageNumber,
		// endPageNumber
		request.setAttribute("currentPageNumber", currentPageNumber);
		request.setAttribute("startPageNumber", startPageNumber);
		request.setAttribute("endPageNumber", endPageNumber);
		request.setAttribute("totalPageCount", totalPageCount);

		HttpSession session = request.getSession(false);

		Member member = null;
		if (session != null) {
			member = ((Member) session.getAttribute("loginMember"));
		}

		RequestDispatcher dispatcher;
		MemberService service2 = new MemberServiceImpl();

		if (member != null && service2.isAdmin(member.getMemberID())) {
			System.out.println("�����ڰ���Ÿ����");
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		} else {
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/views/board/images.jsp");
		}
		dispatcher.forward(request, response);

	}

	private String[] subCategoryList(String category) {
		return new BoardServiceImpl().getSubCategoryList(category);
	}

	/*
	 * ���õ� �Խñ��� �о�ͼ� �����ִ� ��û�� ó���Ѵ�.
	 */
	private void readBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1.1 ��û �Ķ����(num)�� ���� �� ��ȣ�� ���Ѵ�.
		int num = Integer.parseInt(request.getParameter("num"));

		String pageNumber = request.getParameter("pageNumber");

		// (1) ���� ������ ��ȣ
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		// 1.2
		// String searchType = request.getParameter("searchType");
		// String searchText = request.getParameter("searchText");

		// 2. BoardService ��ü�κ��� �ش� �� ��ȣ�� �Խñ��� ���ؿ´�.
		BoardService service = new BoardServiceImpl();
		Board board = service.readBoard(num);
		ProductService service2 = new ProductServiceImpl();
		Product products[] = service2.findProduct(num);
		// 3.1 request scope �Ӽ�(board)�� �Խñ��� �����Ѵ�.
		request.setAttribute("board", board);
		request.setAttribute("products", products);
		request.setAttribute("currentPageNumber", currentPageNumber);
		// 3.2 request scope �Ӽ����� searchType, searchText�� �����Ѵ�.
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);

		// 4. RequestDispatcher ��ü�� ���� �� ������(read.jsp)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/read.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * �Խñ� ����� ���� ���� �����Ѵ�.
	 */
	private void writeBoardForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		BoardService service = new BoardServiceImpl();
		String category = request.getParameter("category");
		String[] subCategoryList = service.getSubCategoryList(category);
		request.setAttribute("subCategoryList", subCategoryList);
		// RequestDispatcher ��ü�� ���� �� ������(writeForm.jsp)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/writeForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * �Խñ��� ����ϴ� ��û�� ó���Ѵ�.
	 */
	//
	// private void writeBoard(HttpServletRequest request_source,
	// HttpServletResponse response) throws ServletException, IOException,
	// DataNotFoundException {
	//
	// String upPath = getServletContext().getRealPath("/images");
	// int mb = 10;
	// MultipartRequest request = new MultipartRequest(request_source, upPath,
	// mb * 1024 * 1024, "utf-8");
	//
	// // 1. ��û �Ķ���ͷ� ���� �ۼ���(writer), ����(title), ����(contents)�� ���Ѵ�.
	// String writer = request.getParameter("writer");
	// String title = request.getParameter("title");
	// String photoDir = request.getFilesystemName("photoDir");
	// String contents = request.getParameter("contents");
	//
	// String category = request.getParameter("category");
	// String subCategory = request.getParameter("subCategory");
	//
	// String ip = request_source.getRemoteAddr();
	// System.err.println(ip);
	// // 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
	// Board board = new Board(writer, title, contents, ip, category,
	// subCategory);
	//
	// // 3. BoardService ��ü�� ���� �ش� �Խñ��� ����Ѵ�.
	// BoardService service = new BoardServiceImpl();
	// service.writeBoard(board);
	//
	// request_source.setAttribute("category", category);
	// // 4. RequestDispatcher ��ü�� ���� ��� ����(list)�� ��û�� �����Ѵ�.
	//
	// RequestDispatcher dispatcher = request_source
	// .getRequestDispatcher("list");
	// dispatcher.forward(request_source, response);
	//
	// }

	private void saveImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		System.out.println("���̺�");

		// ��ũ ����� FileItem factory ����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// factory ���� ����
		ServletContext servletContext = this.getServletConfig()
				.getServletContext();
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		factory.setSizeThreshold(1024 * 100); // �޸𸮿� ������ �ִ� size (100K������ �޸𸮿�
												// ����)
		factory.setRepository(repository); // ���� �ӽ� ����� (100K �̻��̸� repository��
											// ����)

		// ���� ���ε� �ڵ鷯 ����
		ServletFileUpload upload = new ServletFileUpload(factory);

		// �� request size ���� ����
		upload.setSizeMax(1024 * 1024 * 20); // �ִ� size (5M���� ����)
		Map<String, Object> args = new HashMap<String, Object>();

		// ��û �Ľ�
		try {
			List<FileItem> items = upload.parseRequest(request);

			// ���ε�� items ó��
			Iterator<FileItem> iter = items.iterator();

			int proNo = 0;
			int optNo = 0;
			int[] productID = new int[10];
			String[] productTitle = new String[10];
			productOption[][] options = new productOption[10][10];
			productPhoto mainphoto = null;
			while (iter.hasNext()) {
				FileItem item = iter.next();
				// �Ϲ� �� �ʵ� ó�� (<input type="file">�� �ƴ� ���)
				if (item.isFormField()) {
					String name = item.getFieldName(); // �ʵ� �̸�
					String value = item.getString("utf-8"); // �ʵ� ��
					args.put(name, value);
					if (name.equals("productID")) {
						proNo++;
						optNo = 0;
						productID[proNo - 1] = Integer.parseInt(value);
						options[proNo - 1] = new productOption[10];
					} else if (name.equals("productTitle")) {
						productTitle[proNo - 1] = value;
					} else if (name.equals("optionTitle")) {
						optNo++;
					}

					out.println("�� �Ķ���� : " + name + " = " + value + " proNo="
							+ proNo + " optNo=" + optNo + "<br>");
					// ���� ���ε� ó�� (<input type="file">�� ���)
				} else {

					String fileName = item.getName(); // ��ΰ� ���Ե� ���ϸ�
					String contentType = item.getContentType(); // ����Ʈ ����
					// long sizeInBytes = item.getSize(); // ���� ũ��
					byte[] contents = item.get(); // ���� ������ byte �迭�� ���
					out.print(contents);
					int index = fileName.lastIndexOf("\\"); // ���͸� ������ ��ġ�� ����
					if (index == -1) {
						index = fileName.lastIndexOf("/");
					}
					fileName = fileName.substring(index + 1); // ���ϸ� ����

					int optionID;
					try {
						optionID = Integer.parseInt((String) args
								.get("optionID"));
						int price1 = Integer.parseInt((String) args
								.get("optionPrice1"));
						int price2 = Integer.parseInt((String) args
								.get("optionPrice2"));
						int quantity = Integer.parseInt((String) args
								.get("optionQuantity"));
						String installment = (String) args.get("installment");

						String optionTitle = (String) args.get("optionTitle");
						productPhoto photo = new productPhoto(
								productID[proNo - 1], optionID, fileName,
								contentType, contents);
						options[proNo - 1][optNo - 1] = new productOption(
								productID[proNo - 1], optionID, optionTitle,
								price1, price2, quantity, installment, photo);
					} catch (NumberFormatException nfe) {
						optionID = 0;
						mainphoto = new productPhoto(0, optionID, fileName,
								contentType, contents);
					}

				}

			}

			String writer = (String) args.get("writer");
			String title = (String) args.get("title");
			String contents = (String) args.get("contents");
			String category = (String) args.get("category");
			String subCategory = (String) args.get("subCategory");

			String ip = request.getRemoteAddr();
			System.err.println(ip);

			// 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
			Board board = new Board(writer, title, contents, ip, category,
					subCategory, mainphoto);

			// 3. BoardService ��ü�� ���� �ش� �Խñ��� ����Ѵ�.
			BoardService service = new BoardServiceImpl();

			int num = service.writeBoard(board);
			photoDao photoDataAccess = new photoDao();
			photoDataAccess.insertPhoto(num, mainphoto);
			out.println("�۹�ȣ : " + num + "<hr>");
			out.println("��ǰ��ȣ : " + proNo + "<hr>");

			ProductService service2 = new ProductServiceImpl();

			for (int i = 0; i < proNo; i++) {
				Product product = new Product(num, productID[i],
						productTitle[i], options[i]);
				service2.registerProduct(num, product);
			}

			/*
			 * for (productOption option : options[i]) { productPhoto image =
			 * option.getPhoto(); out.println("���� " + image.getImageName() +
			 * " <img src='img?targetId=" + num + "&imageId=" +
			 * image.getImageId() + "'><br>"); }
			 */
			request.setAttribute("num", num);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("read?num=" + request.getAttribute("num"))
				.forward(request, response);
	}

	private void viewImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Image ������ �����ͺ��̽����� ��ȸ
		photoDao service = new photoDao();
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String productID = request.getParameter("productID");
		String optionID = request.getParameter("optionID");
		productPhoto image;
		if (productID == null) {
			image = service.selectImage(boardNum);
		} else {
			image = service.selectImage(boardNum, Integer.parseInt(productID),
					Integer.parseInt(optionID));
		}
		if (image != null) {
			byte[] photo = image.getContents();

			// image ���̳ʸ��� ����
			response.setContentType(image.getContentType());
			response.setContentLength(photo.length);
			ServletOutputStream os = response.getOutputStream();
			try {
				os.write(photo);
			} catch (IOException e) {
				System.out.println("���� ����" + boardNum + "-" + productID + "-"
						+ optionID);
				throw e;
			} finally {
				os.close();
			}
		}
	}

	// private void writeBoard_cos(HttpServletRequest request_source,
	// HttpServletResponse response) throws ServletException, IOException,
	// DataNotFoundException {
	//
	// String upPath = getServletContext().getRealPath("/images");
	// int mb = 10;
	//
	// MultipartRequest request = new MultipartRequest(request_source, upPath,
	// mb * 1024 * 1024, "utf-8");
	//
	// // 1. ��û �Ķ���ͷ� ���� �ۼ���(writer), ����(title), ����(contents)�� ���Ѵ�.
	// String writer = request.getParameter("writer");
	// String title = request.getParameter("title");
	// String photoDir = request.getFilesystemName("photoDir");
	// String contents = request.getParameter("contents");
	//
	// String category = request.getParameter("category");
	// String subCategory = request.getParameter("subCategory");
	//
	// String ip = request_source.getRemoteAddr();
	// System.err.println(ip);
	// // 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
	// Board board = new Board(writer, title, contents, ip, category,
	// subCategory);
	//
	// // 3. BoardService ��ü�� ���� �ش� �Խñ��� ����Ѵ�.
	// BoardService service = new BoardServiceImpl();
	// service.writeBoard(board);
	//
	// request_source.setAttribute("category", category);
	// // 4. RequestDispatcher ��ü�� ���� ��� ����(list)�� ��û�� �����Ѵ�.
	//
	// RequestDispatcher dispatcher = request_source
	// .getRequestDispatcher("list");
	// dispatcher.forward(request_source, response);
	// }

	/*
	 * �Խñ� ������ ���� ������ ������ ä���� ���� �����Ѵ�.
	 */
	private void updateBoardForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// ��û �Ķ���ͷ� ���� �� ��ȣ(num)�� ���Ѵ�.
		String num = request.getParameter("num");

		String pageNumber = request.getParameter("pageNumber");

		// (1) ���� ������ ��ȣ
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		// String searchType = request.getParameter("searchType");
		// String searchText = request.getParameter("searchText");
		// BoardService ��ü�� ���� �ش� ��ȣ�� �Խñ��� �˻��Ѵ�.
		BoardService boardService = new BoardServiceImpl();
		Board board = boardService.findBoard(Integer.parseInt(num));

		// request scope �Ӽ�(board)�� �˻��� �Խñ��� �����Ѵ�.
		request.setAttribute("board", board);
		request.setAttribute("currentPageNumber", currentPageNumber);
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);

		// RequestDispatcher ��ü�� ���� �� ������(updateForm.jsp)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/updateForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * �Խñ��� �����ϴ� ��û�� ó���Ѵ�.
	 */
	private void updateBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. ��û �Ķ���ͷ� ���� �� ��ȣ(num), �ۼ���(writer), ����(title), ����(contents)�� ���Ѵ�.
		String num = request.getParameter("num");
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		String ip = request.getRemoteAddr();

		// 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
		Board board = new Board(Integer.parseInt(num), writer, title, contents,
				ip);

		// 3. BoardService ��ü�� ���� �ش� �Խñ��� �����Ѵ�.
		BoardService service = new BoardServiceImpl();
		service.updateBoard(board);
		// 4. request scope �Ӽ�(board)�� �Խñ��� �����Ѵ�.
		request.setAttribute("board", board);

		// 5. RequestDispatcher ��ü�� ���� �Խù� ����(read)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request.getRequestDispatcher("read");
		dispatcher.forward(request, response);
	}

	/*
	 * �Խñ��� �����ϴ� ��û�� ó���Ѵ�.
	 */
	private void removeBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. ��û �Ķ���ͷ� ���� �� ��ȣ(num)�� ���Ѵ�.
		String num = request.getParameter("num");
		// 2. BoardService ��ü�� ���� �ش� ��ȣ�� �Խñ��� �����Ѵ�.
		BoardService service = new BoardServiceImpl();
		service.removeBoard(Integer.parseInt(num));

		// 3. RequestDispatcher ��ü�� ���� ��� ����(list)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request.getRequestDispatcher("list");
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
