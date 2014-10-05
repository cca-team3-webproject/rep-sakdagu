/**
 * ���ϸ� : Product.java
 * �ۼ��� : 2014. 2. 12.
 * ���ϼ��� : 
 */
package casestudy.business.domain;

/**
 * ��ǰ�� ������ ������ �����ϰ� �ִ� ��ü�� ������ ������ Ŭ����.<br/>
 * ����Ͻ� ���� ���� �������̽��� ǥ���Ǵ� Ư�� ������ Ư�� �μ� ó���� ������ ���� �� ���������� �����ȴ�.<br/>
 * �������� ���񽺷κ��� ����Ͻ��� �����ϴµ� �־� �翬�� �νĵǴ� Ŭ����(���̳� �ֹ��� ����)�� �������� �ڽ��� �������� ��Ÿ���� ���� ��
 * ���� �̿��� ó���� �����Ѵ�.<br/>
 * �������� ������ �������� �ʰ� �ܼ��� ���� �����ϱ⸸ �ϴ� ��ü�� ��� VO(Value Object: ���� �����ϴ� ��ü)��
 * DTO(Data Transfer Object: ���� �����ϱ⸸ �ϴ� ��ü)��� �θ��⵵ �Ѵ�.
 * 
 * @author �����(kidmania@hotmail.com)
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
