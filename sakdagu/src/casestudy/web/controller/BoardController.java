package casestudy.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import casestudy.business.domain.Board;
import casestudy.business.service.BoardService;
import casestudy.business.service.BoardServiceImpl;
import casestudy.business.service.DataNotFoundException;
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
			} else if (action.equals("/read")) {
				readBoard(request, response);
			} else if (action.equals("/writeForm")) {
				writeBoardForm(request, response);
			} else if (action.equals("/write")) {
				writeBoard(request, response);
			} else if (action.equals("/updateForm")) {
				updateBoardForm(request, response);
			} else if (action.equals("/update")) {
				updateBoard(request, response);
			} else if (action.equals("/remove")) {
				removeBoard(request, response);
			}
			// ��� ���� ��û(/replyForm, /reply)�� ���� ó�� �߰�
			else if (action.equals("/replyForm")) {
				replyBoardForm(request, response);
			} else if (action.equals("/reply")) {
				replyBoard(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/*
	 * ���ǿ� �´� ��� �Խù� ����� �����ִ� ��û�� ó���Ѵ�.
	 */
	private void selectBoardList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1. searchType, searchText ��û�Ķ���� ���� ���Ѵ�.
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		// 1.2 pageNumber ��û �Ķ���� ���� ���Ѵ�.
		String pageNumber = request.getParameter("pageNumber");

		// (1) ���� ������ ��ȣ
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		// 1.2 �˻� �ɼ��� ��� �ִ� Map ��ü�� �����Ͽ� searchType, searchText ���� �����Ѵ�.
		Map<String, Object> searchInfo = new HashMap<String, Object>();

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

		// ���� �������� �Խñ� ��Ͽ��� ó�� ������ �Խñ��� �� ��ȣ
		int startRow = PageHandler.getStartRow(currentPageNumber);

		// ���� �������� �Խñ� ��Ͽ��� �������� ������ �Խñ��� �� ��ȣ
		int endRow = PageHandler.getEndRow(currentPageNumber);

		// 3.1 �˻��ɼ� Map() startRow�� endRow ���� �����Ѵ�.
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);

		searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
		// 4. BoardService ��ü�κ��� ��� �Խñ� ����Ʈ�� ���ؿ´�.
		Board[] boardList = service.getBoardList(searchInfo);

		// 5. request scope �Ӽ�(boardList)�� �Խñ� ����Ʈ�� �����Ѵ�.
		request.setAttribute("boardList", boardList);
		// 2. request scope �Ӽ����� searchType, searchText�� �����Ѵ�.
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);
		// 4.2 request scope �Ӽ����� currentPageNumber, startPageNumber,
		// endPageNumber
		request.setAttribute("currentPageNumber", currentPageNumber);
		request.setAttribute("startPageNumber", startPageNumber);
		request.setAttribute("endPageNumber", endPageNumber);
		request.setAttribute("totalPageCount", totalPageCount);

		// 6. RequestDispatcher ��ü�� ���� �� ������(list.jsp)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		dispatcher.forward(request, response);

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

		// 3.1 request scope �Ӽ�(board)�� �Խñ��� �����Ѵ�.
		request.setAttribute("board", board);
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

		// RequestDispatcher ��ü�� ���� �� ������(writeForm.jsp)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/writeForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * �Խñ��� ����ϴ� ��û�� ó���Ѵ�.
	 */
	private void writeBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. ��û �Ķ���ͷ� ���� �ۼ���(writer), ����(title), ����(contents)�� ���Ѵ�.
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		String ip = request.getRemoteAddr();
		System.err.println(ip);
		// 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
		Board board = new Board(writer, title, contents, ip);

		// 3. BoardService ��ü�� ���� �ش� �Խñ��� ����Ѵ�.
		BoardService service = new BoardServiceImpl();
		service.writeBoard(board);

		// 4. RequestDispatcher ��ü�� ���� ��� ����(list)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request.getRequestDispatcher("list");
		dispatcher.forward(request, response);
	}

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
	 * �亯���� ����ϴ� ��û�� ó���Ѵ�.
	 */
	private void replyBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		// 1. ��û �Ķ���ͷ� ���� �� ��ȣ(num), ������(masterNum),
		// �ۼ���(writer), ����(title),����(contents)�� ���Ѵ�.
		String num = request.getParameter("num");
		String masterNum = request.getParameter("masterNum");
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String replyOrder = request.getParameter("replyOrder");
		String replyStep = request.getParameter("replyStep");

		String ip = request.getRemoteAddr();

		// 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
		Board board = new Board(Integer.parseInt(num), writer, title, contents,
				ip, Integer.parseInt(masterNum), Integer.parseInt(replyOrder),
				Integer.parseInt(replyStep));

		// 3. BoardService ��ü�� ���� �ش� �Խñ��� ����Ѵ�.
		BoardService service = new BoardServiceImpl();
		service.replyBoard(board);

		// 4. request scope �Ӽ�(board)�� �Խñ��� �����Ѵ�.
		request.setAttribute("board", board);
		// 5. RequestDispatcher ��ü�� ���� �Խù� ����(read)�� ��û�� �����Ѵ�.
		RequestDispatcher dispatcher = request.getRequestDispatcher("list");
		dispatcher.forward(request, response);

	}

	/*
	 * �亯�� �ۼ��� ���� ������ ������ ä���� ���� �����Ѵ�.
	 */
	private void replyBoardForm(HttpServletRequest request,
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
				.getRequestDispatcher("/WEB-INF/views/board/replyForm.jsp");
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
