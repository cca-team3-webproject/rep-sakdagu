package casestudy.util;

public class PageHandler {
	// �� �������� ������ �Խñ� ����
	private static final int PAGE_LIST_SIZE = 6;
	// ������ ���� �ٿ� ������ ������ ����
	private static final int PAGE_GROUP_SIZE = 3;

	// (4) ��ü �Խñ� ����
	// int totalBoardCount = service.getBoardCount(searchInfo);

	// ��ü �Խñ� �����κ��� ��ü ������ ������ �����ִ� ���
	public static int getTotalPageCount(int totalBoardCount) {
		int totalPageCount = (int) Math.ceil(totalBoardCount
				/ (float) PAGE_LIST_SIZE);
		return totalPageCount;
	}

	// Ư�� �������� ������ ���� �ٿ� ǥ�õ� ���� ������ ��ȣ�� �����ִ� ���
	public static int getStartPageNumber(int pageNumber) {
		int startPageNumber = (pageNumber - 1) / PAGE_GROUP_SIZE
				* PAGE_GROUP_SIZE + 1;

		return startPageNumber;
	}

	// Ư�� �������� ������ ���� �ٿ� ǥ�õ� �� ������ ��ȣ�� �����ִ� ���
	public static int getEndPageNumber(int pageNumber, int totalBoardCount) {
		int endPageNumber = pageNumber + PAGE_GROUP_SIZE - 1;
		if (endPageNumber > getTotalPageCount(totalBoardCount)) {
			endPageNumber = getTotalPageCount(totalBoardCount);
		}

		return endPageNumber;
	}

	// Ư�� �������� �Խñ� ��Ͽ��� ó�� ������ �Խñ��� �� ��ȣ�� �����ִ� ���
	public static int getStartRow(int pageNumber) {
		int startRow = (pageNumber - 1) * PAGE_LIST_SIZE + 1;
		return startRow;
	}

	// Ư�� �������� �Խñ� ��Ͽ��� �������� ������ �Խñ��� �� ��ȣ�� �����ִ� ���
	public static int getEndRow(int pageNumber) {
		int endRow = pageNumber * PAGE_LIST_SIZE;
		return endRow;
	}

}
