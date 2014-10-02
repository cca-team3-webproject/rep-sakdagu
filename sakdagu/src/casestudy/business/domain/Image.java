package casestudy.business.domain;

import java.util.Arrays;

public class Image {
	private int optionId;
	private String imageName;
	private String contentType;
	private byte[] contents;
	
	// 이미지 리스트 조회용 생성자
	public Image(int id, String name, String type) {
		this.optionId = id;
		this.imageName = name;
		this.contentType = type;
	}
	
	// 이미지 조회용 생성자
	public Image(int id, String name, String type,  byte[] contents) {
		this.optionId = id;
		this.imageName = name;
		this.contentType = type;
		this.contents = contents;
	}
	
	// 이미지 저장용 생성자
	public Image(String name, String type, byte[] contents) {
		this.imageName = name;
		this.contentType = type;
		this.contents = contents;
	}

	public Image(byte[] contents) {
		this.contents = contents;
	}

	public int getOptionId() {
		return optionId;
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

	public void setOptionId(int optionId) {
		this.optionId = optionId;
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
		return "Image [imageId=" + optionId + ", imageName=" + imageName
				+ ", contentType=" + contentType + ", contents="
				+ Arrays.toString(contents) + "]";
	}
}
