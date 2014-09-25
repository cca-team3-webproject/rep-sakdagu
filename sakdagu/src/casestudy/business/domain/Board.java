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
	/* ���� �۹�ȣ */
	private int masterNum;
	/* �亯 �۹�ȣ */
	private int replyOrder;
	/* �亯 �۱��� */
	private int replyStep;

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

	// �Խñ� �� ��ȸ��
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

	// ����Ʈ ��ȸ��
	public Board(int num, String writer, String title, int readCount,
			String regDate, int replyStep) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.readCount = readCount;
		this.regDate = regDate;

		this.replyStep = replyStep;
	}

	// �Խñ� �����
	public Board(String writer, String title, String contents, String ip) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;

	}

	// �Խñ� ������
	public Board(int num, String writer, String title, String contents,
			String ip) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
	}

	// �亯�� �����

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

	@Override
	public String toString() {
		return "Board [num=" + num + ", writer=" + writer + ", title=" + title
				+ ", contents=" + contents + ", ip=" + ip + ", readCount="
				+ readCount + ", regDate=" + regDate + ", modDate=" + modDate
				+ ", masterNum=" + masterNum + ", replyOrder=" + replyOrder
				+ ", replyStep=" + replyStep + "]";
	}

}
