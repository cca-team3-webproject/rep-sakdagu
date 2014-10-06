package casestudy.util;

public class PageHandler {
	// 한 페이지에 보여줄 게시글 개수
	private static final int PAGE_LIST_SIZE = 6;
	// 페이지 선택 바에 보여줄 페이지 개수
	private static final int PAGE_GROUP_SIZE = 3;

	// (4) 전체 게시글 개수
	// int totalBoardCount = service.getBoardCount(searchInfo);

	// 전체 게시글 개수로부터 전체 페이지 개수를 구해주는 기능
	public static int getTotalPageCount(int totalBoardCount) {
		int totalPageCount = (int) Math.ceil(totalBoardCount
				/ (float) PAGE_LIST_SIZE);
		return totalPageCount;
	}

	// 특정 페이지의 페이지 선택 바에 표시될 시작 페이지 번호를 구해주는 기능
	public static int getStartPageNumber(int pageNumber) {
		int startPageNumber = (pageNumber - 1) / PAGE_GROUP_SIZE
				* PAGE_GROUP_SIZE + 1;

		return startPageNumber;
	}

	// 특정 페이지의 페이지 선택 바에 표시될 끝 페이지 번호를 구해주는 기능
	public static int getEndPageNumber(int pageNumber, int totalBoardCount) {
		int endPageNumber = pageNumber + PAGE_GROUP_SIZE - 1;
		if (endPageNumber > getTotalPageCount(totalBoardCount)) {
			endPageNumber = getTotalPageCount(totalBoardCount);
		}

		return endPageNumber;
	}

	// 특정 페이지의 게시글 목록에서 처음 보여질 게시글의 행 번호를 구해주는 기능
	public static int getStartRow(int pageNumber) {
		int startRow = (pageNumber - 1) * PAGE_LIST_SIZE + 1;
		return startRow;
	}

	// 특정 페이지의 게시글 목록에서 마지막에 보여질 게시글의 행 번호를 구해주는 기능
	public static int getEndRow(int pageNumber) {
		int endRow = pageNumber * PAGE_LIST_SIZE;
		return endRow;
	}

}
