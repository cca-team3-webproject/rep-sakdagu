package casestudy.business.domain;

public class productOption {
	private String productID;
	private String optionID;
	private String optionName;
	private int price1;// 시중가
	private int price2;// 판매가
	private int quantity;
	private String installment;// 카드할부여부
	productPhoto Photo;

	public productOption() {
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getOptionID() {
		return optionID;
	}

	public void setOptionID(String optionID) {
		this.optionID = optionID;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public int getPrice1() {
		return price1;
	}

	public void setPrice1(int price1) {
		this.price1 = price1;
	}

	public int getPrice2() {
		return price2;
	}

	public void setPrice2(int price2) {
		this.price2 = price2;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getInstallment() {
		return installment;
	}

	public void setInstallment(String installment) {
		this.installment = installment;
	}

	public productPhoto getPhoto() {
		return Photo;
	}

	public void setPhoto(productPhoto photo) {
		Photo = photo;
	}

	public productOption(String productID, String optionID, String optionName,
			int price1, int price2, int quantity, String installment,
			productPhoto photo) {
		super();
		this.productID = productID;
		this.optionID = optionID;
		this.optionName = optionName;
		this.price1 = price1;
		this.price2 = price2;
		this.quantity = quantity;
		this.installment = installment;
		Photo = photo;
	}

	@Override
	public String toString() {
		return "productOption [productID=" + productID + ", optionID="
				+ optionID + ", optionName=" + optionName + ", price1="
				+ price1 + ", price2=" + price2 + ", quantity=" + quantity
				+ ", installment=" + installment + ", Photo=" + Photo + "]";
	}
	
}
