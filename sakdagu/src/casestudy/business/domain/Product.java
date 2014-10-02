/**
 * ���ϸ� : Product.java
 * �ۼ��� : 2014. 2. 12.
 * ���ϼ��� : 
 */
package casestudy.business.domain;

import java.sql.Date;

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
	private String boardNum;
	private String productID;
	private String productName;
	private productOption option;

	public Product() {

	}

	public Product(String boardNum, String productID, String productName) {
		this.boardNum = boardNum;
		this.productID = productID;
		this.productName = productName;
	}

	public Product(String boardNum, String productID, String productName,
			productOption option) {
		this.boardNum = boardNum;
		this.productID = productID;
		this.productName = productName;
		this.setOption(option);
	}

	public String getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(String boardNum) {
		this.boardNum = boardNum;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "Product [boardNum=" + boardNum + ", productID=" + productID
				+ ", productName=" + productName + "]";
	}

	public productOption getOption() {
		return option;
	}

	public void setOption(productOption option) {
		this.option = option;
	}

}
