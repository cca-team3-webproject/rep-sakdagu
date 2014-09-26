package casestudy.business.domain;

public class Board {
	private int num;

	private String writer;
	private String title;
	private String contents;
	private String ip;
	private int readCount;
	private String regDate;
	private String modDate;
	/* 원본 글번호 */
	private int masterNum;
	/* 답변 글번호 */
	private int replyOrder;
	/* 답변 글깊이 */
	private int replyStep;

	/* 카테고리 */
	private String category;
	/* 서브카테고리 */
	private String subCategory;

	private String photoDir;

	public Board() {

	}

	public int getMasterNum() {
		return masterNum;
	}

	public int getReplyOrder() {
		return replyOrder;
	}

	public int getReplyStep() {
		return replyStep;
	}

	public void setMasterNum(int masterNum) {
		this.masterNum = masterNum;
	}

	public void setReplyOrder(int replyOrder) {
		this.replyOrder = replyOrder;
	}

	public void setReplyStep(int replyStep) {
		this.replyStep = replyStep;
	}

	// 게시글 상세 조회용
	public Board(int num, String writer, String title, String contents,
			String ip, int readCount, String regDate, String modDate,
			int masterNum, int replyOrder, int replyStep) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.readCount = readCount;
		this.regDate = regDate;
		this.modDate = modDate;
		this.masterNum = masterNum;
		this.replyOrder = replyOrder;
		this.replyStep = replyStep;
	}

	// 리스트 조회용
	public Board(int num, String writer, String title, int readCount,
			String regDate, String category, String subCategory, String photoDir) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.readCount = readCount;
		this.regDate = regDate;
		this.category = category;
		this.subCategory = subCategory;
		this.photoDir = photoDir;
		// this.replyStep = replyStep;
	}

	// 게시글 쓰기용
	public Board(String writer, String title, String contents, String ip,
			String category, String subCategory, String photoDir) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.category = category;
		this.subCategory = subCategory;
		this.setPhotoDir(photoDir);
	}

	// 게시글 수정용
	public Board(int num, String writer, String title, String contents,
			String ip) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
	}

	// 답변글 쓰기용

	public Board(int num, String writer, String title, String contents,
			String ip, int masterNum, int replyOrder, int replyStep) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.masterNum = masterNum;
		this.replyOrder = replyOrder;
		this.replyStep = replyStep;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getRegDate() { // "2014-09-18"
		return regDate.substring(0, 10);
	}

	public String getRegDateTime() { // "2014-09-18 14:14:30"
		return regDate.substring(0, 20);
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() { // "2014-09-18"
		return modDate.substring(0, 9);
	}

	public String getModDateTime() { // "2014-09-18 14:14:30"
		return modDate.substring(0, 19);
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getPhotoDir() {
		return photoDir;
	}

	public void setPhotoDir(String photoDir) {
		this.photoDir = photoDir;
	}

	@Override
	public String toString() {
		return "Board [num=" + num + ", writer=" + writer + ", title=" + title
				+ ", contents=" + contents + ", ip=" + ip + ", readCount="
				+ readCount + ", regDate=" + regDate + ", modDate=" + modDate
				+ ", masterNum=" + masterNum + ", replyOrder=" + replyOrder
				+ ", replyStep=" + replyStep + ", category=" + category
				+ ", subCategory=" + subCategory + ", photoDir=" + photoDir
				+ "]";
	}

}
