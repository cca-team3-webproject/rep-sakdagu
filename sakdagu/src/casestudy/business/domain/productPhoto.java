package casestudy.business.domain;

import java.util.Arrays;

public class productPhoto {
	private int boardNum;
	private int productId;
	private int optionId;
	private String imageName;
	private String contentType;
	private byte[] contents;

	// 이미지 리스트 조회용 생성자
	public productPhoto(int boardNum, int productId, String name, String type) {
		this.boardNum = boardNum;
		this.productId = productId;
		this.imageName = name;
		this.contentType = type;
	}


	// 이미지 조회용 생성자
	public productPhoto(int boardNum, int productId, int optionId, String name,
			String type, byte[] contents) {
		this.boardNum = boardNum;
		this.productId = productId;
		this.setOptionId(optionId);
		this.imageName = name;
		this.contentType = type;
		this.contents = contents;
	}

	// 이미지 저장용 생성자
	public productPhoto(int productId, int optionId, String name, String type,
			byte[] contents) {
		this.productId = productId;
		this.setOptionId(optionId);
		this.imageName = name;
		this.contentType = type;
		this.contents = contents;
	}

	/*
	 * public productPhoto(private int targetId,String name, String type, byte[]
	 * contents) { this.imageName = name; this.contentType = type; this.contents
	 * = contents; }
	 */

	public int getBoardNum() {
		return boardNum;
	}

	public String getImageName() {
		return imageName;
	}

	public String getContentType() {
		return contentType;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "productPhoto [boardNum=" + boardNum + ", productId="
				+ productId + ", optionId=" + optionId + ", imageName="
				+ imageName + ", contentType=" + contentType + ", contents="
				+ Arrays.toString(contents) + "]";
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

}
