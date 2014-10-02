package casestudy.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import casestudy.business.domain.Board;
import casestudy.business.domain.Image;
import casestudy.business.domain.Member;
import casestudy.business.service.BoardService;
import casestudy.business.service.BoardServiceImpl;
import casestudy.business.service.DataNotFoundException;
import casestudy.dataaccess.ImageDao;
import casestudy.util.PageHandler;

import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardController
 */
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * HTTP GET과 POST 방식의 요청을 모두 처리한다. 요청파라미터 값을 확인하여 적절한 사용자의 요청을 처리한다.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// action 요청파라미터 값을 확인한다.
			/* String action = request.getParameter("action"); */
			String action = request.getPathInfo();

			// action 값에 따라 적절한 메소드를 선택하여 호출한다.
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
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/*
	 * 조건에 맞는 모든 게시물 목록을 보여주는 요청을 처리한다.
	 */
	private void selectBoardList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1. searchType, searchText 요청파라미터 값을 구한다.
		String searchText = request.getParameter("searchText");
		String category = request.getParameter("category");
		if (category == null || category.length() == 0) {
			category = (String) request.getAttribute("category");
			if (category == null || category.length() == 0) {
				category = "베스트";
			}
		}

		String subCategory = request.getParameter("subCategory");
		// 1.2 pageNumber 요청 파라미터 값을 구한다.
		String pageNumber = request.getParameter("pageNumber");

		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		// 1.2 검색 옵션을 담고 있는 Map 객체를 생성하여 searchType, searchText 값을 저장한다.
		Map<String, Object> searchInfo = new HashMap<String, Object>();

		// 현재 페이지의 게시글 목록에서 처음 보여질 게시글의 행 번호
		int startRow = PageHandler.getStartRow(currentPageNumber);

		// 현재 페이지의 게시글 목록에서 마지막에 보여질 게시글의 행 번호
		int endRow = PageHandler.getEndRow(currentPageNumber);
		// 3.1 검색옵션 Map() startRow와 endRow 값을 저장한다.
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);

		searchInfo.put("searchText", searchText);

		searchInfo.put("category", category);
		searchInfo.put("subCategory", subCategory);

		// 2. BoardService 객체를 생성한다.
		BoardService service = new BoardServiceImpl();
		// 전체 게시글 개수
		int totalBoardCount = service.getBoardCount(searchInfo);

		// 전체 페이지 개수
		int totalPageCount = PageHandler.getTotalPageCount(totalBoardCount);

		// 페이지 선택 바에 표시될 시작 페이지 번호
		int startPageNumber = PageHandler.getStartPageNumber(currentPageNumber);

		// 페이지 선택 바에 표시될 끝 페이지 번호
		int endPageNumber = PageHandler.getEndPageNumber(startPageNumber,
				totalBoardCount);

		// 4. BoardService 객체로부터 모든 게시글 리스트를 구해온다.
		Board[] boardList = service.getBoardList(searchInfo);
		String[] subCategoryList = service.getSubCategoryList(category);
		// 5. request scope 속성(boardList)에 게시글 리스트를 저장한다.
		request.setAttribute("boardList", boardList);
		request.setAttribute("subCategoryList", subCategoryList);

		// 2. request scope 속성으로 searchType, searchText를 저장한다.
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);
		// 4.2 request scope 속성으로 currentPageNumber, startPageNumber,
		// endPageNumber
		request.setAttribute("currentPageNumber", currentPageNumber);
		request.setAttribute("startPageNumber", startPageNumber);
		request.setAttribute("endPageNumber", endPageNumber);
		request.setAttribute("totalPageCount", totalPageCount);

		// 6. RequestDispatcher 객체를 통해 뷰 페이지(list.jsp)로 요청을 전달한다.
		HttpSession session = request.getSession(false);
		Member member = null;
		if (session != null) {
			member = ((Member) session.getAttribute("loginMember"));

		}
		System.out.println("memememe" + member);
		RequestDispatcher dispatcher;
		if (member != null && member.getMemberID().equals("duke")) {
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		} else {
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/views/board/imageList.jsp");

		}
		dispatcher.forward(request, response);

	}

	/*
	 * 선택된 게시글을 읽어와서 보여주는 요청을 처리한다.
	 */
	private void readBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1.1 요청 파라미터(num)로 부터 글 번호를 구한다.
		int num = Integer.parseInt(request.getParameter("num"));

		String pageNumber = request.getParameter("pageNumber");

		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		// 1.2
		// String searchType = request.getParameter("searchType");
		// String searchText = request.getParameter("searchText");

		// 2. BoardService 객체로부터 해당 글 번호의 게시글을 구해온다.
		BoardService service = new BoardServiceImpl();
		Board board = service.readBoard(num);

		// 3.1 request scope 속성(board)에 게시글을 저장한다.
		request.setAttribute("board", board);
		request.setAttribute("currentPageNumber", currentPageNumber);
		// 3.2 request scope 속성으로 searchType, searchText를 저장한다.
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);

		// 4. RequestDispatcher 객체를 통해 뷰 페이지(read.jsp)로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/read.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 게시글 등록을 위한 폼을 응답한다.
	 */
	private void writeBoardForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		BoardService service = new BoardServiceImpl();
		String category = request.getParameter("category");
		String[] subCategoryList = service.getSubCategoryList(category);
		request.setAttribute("subCategoryList", subCategoryList);
		// RequestDispatcher 객체를 통해 뷰 페이지(writeForm.jsp)로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/writeForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 게시글을 등록하는 요청을 처리한다.
	 */
	private void writeBoard(HttpServletRequest request_source,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		String upPath = getServletContext().getRealPath("/images");
		int mb = 10;

		MultipartRequest request = new MultipartRequest(request_source, upPath,
				mb * 1024 * 1024, "utf-8");

		// 1. 요청 파라미터로 부터 작성자(writer), 제목(title), 내용(contents)를 구한다.
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String photoDir = request.getFilesystemName("photoDir");
		String contents = request.getParameter("contents");

		String category = request.getParameter("category");
		String subCategory = request.getParameter("subCategory");

		String ip = request_source.getRemoteAddr();
		System.err.println(ip);
		// 2. 구해 온 요청 파라미터 값와 ip 값을 지닌 Board 객체를 생성한다.
		Board board = new Board(writer, title, contents, ip, category,
				subCategory, photoDir);

		// 3. BoardService 객체를 통해 해당 게시글을 등록한다.
		BoardService service = new BoardServiceImpl();
		service.writeBoard(board);

		request_source.setAttribute("category", category);
		// 4. RequestDispatcher 객체를 통해 목록 보기(list)로 요청을 전달한다.

		/*
		 * RequestDispatcher dispatcher = request_source
		 * .getRequestDispatcher("list"); dispatcher.forward(request_source,
		 * response);
		 */
		saveImage(request_source, response);
	}

	private void saveImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
System.out.println("세이브");
		List<Image> imageList = new ArrayList<Image>();
		// 디스크 기반의 FileItem factory 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// factory 제약 설정
		ServletContext servletContext = this.getServletConfig()
				.getServletContext();
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		factory.setSizeThreshold(1024 * 100); // 메모리에 저장할 최대 size (100K까지는 메모리에
												// 저장)
		factory.setRepository(repository); // 파일 임시 저장소 (100K 이상이면 repository에
											// 저장)

		// 파일 업로드 핸들러 생성
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 총 request size 제약 설정
		upload.setSizeMax(1024 * 1024 * 5); // 최대 size (5M까지 가능)

		// 요청 파싱
		try {
			List<FileItem> items = upload.parseRequest(request);

			// 업로드된 items 처리
			Iterator<FileItem> iter = items.iterator();

			while (iter.hasNext()) {
				FileItem item = iter.next();
				// 파일 업로드 처리 (<input type="file">인 경우)
				if (!item.isFormField()) {
					/*String fileName = item.getName(); // 경로가 포함된 파일명
					String contentType = item.getContentType(); // 컨텐트 유형
*/					// long sizeInBytes = item.getSize(); // 파일 크기
					byte[] contents = item.get(); // 파일 내용을 byte 배열에 담기

/*					int index = fileName.lastIndexOf("\\"); // 디렉터리 구분자 위치를 통해
					if (index == -1) {
						index = fileName.lastIndexOf("/");
					}
					fileName = fileName.substring(index + 1); // 파일명만 추출
*/
					// Image 정보를 데이터베이스에 저장

					Image image = new Image(contents);
					System.out.println("\t2 " +  image);
					new ImageDao().insertImage(2, image);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("list").forward(request, response);
	}

	private void writeBoard_cos(HttpServletRequest request_source,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		String upPath = getServletContext().getRealPath("/images");
		int mb = 10;

		MultipartRequest request = new MultipartRequest(request_source, upPath,
				mb * 1024 * 1024, "utf-8");

		// 1. 요청 파라미터로 부터 작성자(writer), 제목(title), 내용(contents)를 구한다.
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String photoDir = request.getFilesystemName("photoDir");
		String contents = request.getParameter("contents");

		String category = request.getParameter("category");
		String subCategory = request.getParameter("subCategory");

		String ip = request_source.getRemoteAddr();
		System.err.println(ip);
		// 2. 구해 온 요청 파라미터 값와 ip 값을 지닌 Board 객체를 생성한다.
		Board board = new Board(writer, title, contents, ip, category,
				subCategory, photoDir);

		// 3. BoardService 객체를 통해 해당 게시글을 등록한다.
		BoardService service = new BoardServiceImpl();
		service.writeBoard(board);

		request_source.setAttribute("category", category);
		// 4. RequestDispatcher 객체를 통해 목록 보기(list)로 요청을 전달한다.

		RequestDispatcher dispatcher = request_source
				.getRequestDispatcher("list");
		dispatcher.forward(request_source, response);
	}

	/*
	 * 게시글 수정을 위해 적절한 내용이 채워진 폼을 응답한다.
	 */
	private void updateBoardForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 요청 파라미터로 부터 글 번호(num)를 구한다.
		String num = request.getParameter("num");

		String pageNumber = request.getParameter("pageNumber");

		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() != 0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		// String searchType = request.getParameter("searchType");
		// String searchText = request.getParameter("searchText");
		// BoardService 객체를 통해 해당 번호의 게시글을 검색한다.
		BoardService boardService = new BoardServiceImpl();
		Board board = boardService.findBoard(Integer.parseInt(num));

		// request scope 속성(board)에 검색한 게시글을 저장한다.
		request.setAttribute("board", board);
		request.setAttribute("currentPageNumber", currentPageNumber);
		// request.setAttribute("searchType", searchType);
		// request.setAttribute("searchText", searchText);

		// RequestDispatcher 객체를 통해 뷰 페이지(updateForm.jsp)로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/updateForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 게시글을 수정하는 요청을 처리한다.
	 */
	private void updateBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. 요청 파라미터로 부터 글 번호(num), 작성자(writer), 제목(title), 내용(contents)을 구한다.
		String num = request.getParameter("num");
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		String ip = request.getRemoteAddr();

		// 2. 구해 온 요청 파라미터 값와 ip 값을 지닌 Board 객체를 생성한다.
		Board board = new Board(Integer.parseInt(num), writer, title, contents,
				ip);

		// 3. BoardService 객체를 통해 해당 게시글을 갱신한다.
		BoardService service = new BoardServiceImpl();
		service.updateBoard(board);
		// 4. request scope 속성(board)에 게시글을 저장한다.
		request.setAttribute("board", board);

		// 5. RequestDispatcher 객체를 통해 게시물 보기(read)로 요청을 전달한다.
		RequestDispatcher dispatcher = request.getRequestDispatcher("read");
		dispatcher.forward(request, response);
	}

	/*
	 * 게시글을 삭제하는 요청을 처리한다.
	 */
	private void removeBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. 요청 파라미터로 부터 글 번호(num)를 구한다.
		String num = request.getParameter("num");
		// 2. BoardService 객체를 통해 해당 번호의 게시글을 삭제한다.
		BoardService service = new BoardServiceImpl();
		service.removeBoard(Integer.parseInt(num));

		// 3. RequestDispatcher 객체를 통해 목록 보기(list)로 요청을 전달한다.
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
