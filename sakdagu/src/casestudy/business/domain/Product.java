/**
 * 파일명 : Product.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.business.domain;

/**
 * 상품과 관련한 정보를 저장하고 있는 객체를 정의한 도메인 클래스.<br/>
 * 비즈니스 로직 층은 유스케이스로 표현되는 특정 업무나 특정 부서 처리의 통합인 서비스 및 도메인으로 구성된다.<br/>
 * 도메인은 서비스로부터 비즈니스를 실행하는데 있어 당연히 인식되는 클래스(고객이나 주문과 같은)의 집합으로 자신이 무엇인지 나타내는 값과 그
 * 값을 이용한 처리를 실현한다.<br/>
 * 도메인이 로직을 포함하지 않고 단순히 값만 저장하기만 하는 객체일 경우 VO(Value Object: 값을 저장하는 객체)나
 * DTO(Data Transfer Object: 값을 전달하기만 하는 객체)라고 부르기도 한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 * 
 */
public class Product {
	private int boardNum;
	private int productID;
	private String productTitle;
	private productPhoto photo;
	private productOption[] option;

	public Product(int boardNum, int productID, String productTitle,
			productOption[] option) {
		super();
		this.boardNum = boardNum;
		this.productID = productID;
		this.productTitle = productTitle;
		this.setOption(option);
	}

	public Product() {
	}

	public Product(int boardNum, int productID, String productTitle) {
		this.boardNum = boardNum;
		this.productID = productID;
		this.productTitle = productTitle;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	@Override
	public String toString() {
		return "Product [boardNum=" + boardNum + ", productID=" + productID
				+ ", productTitle=" + productTitle + "]";
	}

	public productOption[] getOption() {
		return option;
	}

	public void setOption(productOption[] option) {
		this.option = option;
	}

	public productPhoto getPhoto() {
		return photo;
	}

	public void setPhoto(productPhoto photo) {
		this.photo = photo;
	}

}
