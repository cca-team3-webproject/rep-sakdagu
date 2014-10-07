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

	/* ī�װ� */
	private String category;
	/* ����ī�װ� */
	private String subCategory;

	private String photoDir;

	private productPhoto productPhoto;

	private int minPrice;

	public Board() {

	}

	// �Խñ� �� ��ȸ��
	public Board(int num, String writer, String title, String contents,
			String ip, int readCount, String regDate, String modDate,
			String category, String subCategory) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.readCount = readCount;
		this.regDate = regDate;
		this.modDate = modDate;

		this.category = category;
		this.subCategory = subCategory;
	}

	// ����Ʈ ��ȸ��
	public Board(int num, String writer, String title, int readCount,
			String regDate, String category, String subCategory, int minPrice) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.readCount = readCount;
		this.regDate = regDate;
		this.category = category;
		this.subCategory = subCategory;
		this.setMinPrice(minPrice);
	}

	// �Խñ� �����
	public Board(String writer, String title, String contents, String ip,
			String category, String subCategory, productPhoto productPhoto) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.category = category;
		this.subCategory = subCategory;
		this.setProductPhoto(productPhoto);
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
				+ ", category=" + category + ", subCategory=" + subCategory
				+ ", photoDir=" + photoDir + ", productPhoto=" + productPhoto.getImageName()
				+ ", minPrice=" + minPrice + "]";
	}

	public productPhoto getProductPhoto() {
		return productPhoto;
	}

	public void setProductPhoto(productPhoto productPhoto) {
		this.productPhoto = productPhoto;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

}
