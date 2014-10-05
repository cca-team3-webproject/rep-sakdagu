/**
 * ���ϸ� : ProductServiceImpl.java
 * �ۼ��� : 2014. 2. 13.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;
import casestudy.business.domain.productOption;
import casestudy.business.domain.productPhoto;
import casestudy.dataaccess.ProductDaoImpl;
import casestudy.dataaccess.photoDao;

/**
 * ��ǰ ���� ����Ͻ� ������ ������ ���� Ŭ������ ������ �׼��� ó���� ProductDao ��ü���� �����Ͽ� �����Ѵ�.
 * 
 * @author �����(kidmania@hotmail.com)
 * 
 */
public class ProductServiceImpl implements ProductService {

	private ProductDao productDataAccess;
	private photoDao photoDataAccess;

	/*
	 * 1. ProductDaoImpl ��ü�� �����Ͽ� productDataAccess �ν��Ͻ� ���� �ʱ�ȭ
	 */
	public ProductServiceImpl() {
		this.productDataAccess = new ProductDaoImpl();
		this.photoDataAccess = new photoDao();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.ProductService#findProduct(java.lang.String)
	 * 
	 * 2. ProductDao ��ü�� ����� ���ڷ� ���� productID�� �ش��ϴ� ��ǰ ������ ã�Ƽ� �����Ѵ�.
	 */
	@Override
	public Product[] findProduct(int num) throws DataNotFoundException {
		if (!productDataAccess.productIDExists(num)) {
			throw new DataNotFoundException();
		}
		return productDataAccess.selectProduct(num);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.ProductService#getAllProducts()
	 * 
	 * 3. ProductDao ��ü�� ����� ��� ��ǰ ������ ���ؼ� �����Ѵ�.
	 */
	@Override
	public Product[] getAllProducts() {
		return productDataAccess.selectAllProducts();
	}

	@Override
	public void registerProduct(int num, Product product)
			throws DataDuplicatedException {
		if (productDataAccess.productIDExists(product.getProductID())) {
			throw new DataDuplicatedException();
		}
		productDataAccess.insertProduct(product);
		for (productOption option : product.getOption()) {
			if (option != null) {
				productDataAccess.insertOption(num, option);
				productPhoto photo = option.getPhoto();
				photoDataAccess.insertPhoto(num, photo);
			}
		}
	}

	@Override
	public void removeProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public productOption[] findOprion(int num) throws DataNotFoundException {
		if (!productDataAccess.productIDExists(num)) {
			throw new DataNotFoundException();
		}
		return productDataAccess.selectOption(num,0);
	}

}
