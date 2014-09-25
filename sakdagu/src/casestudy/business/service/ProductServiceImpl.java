/**
 * ���ϸ� : ProductServiceImpl.java
 * �ۼ��� : 2014. 2. 13.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;
import casestudy.dataaccess.ProductDaoImpl;

/**
 * ��ǰ ���� ����Ͻ� ������ ������ ���� Ŭ������ 
 * ������ �׼��� ó���� ProductDao ��ü���� �����Ͽ� �����Ѵ�.
 * 
 * @author �����(kidmania@hotmail.com)
 *
 */
public class ProductServiceImpl implements ProductService {
	
    private ProductDao productDataAccess;

    /* 
     * 1. ProductDaoImpl ��ü�� �����Ͽ� productDataAccess �ν��Ͻ� ���� �ʱ�ȭ  
     */
    public ProductServiceImpl() {
    	this.productDataAccess = new ProductDaoImpl();
    }    
    
	/* (non-Javadoc)
	 * @see casestudy.business.service.ProductService#findProduct(java.lang.String)
	 * 
	 * 2. ProductDao ��ü�� ����� ���ڷ� ���� productID�� �ش��ϴ� ��ǰ ������ ã�Ƽ� �����Ѵ�.
	 */
	@Override
	public Product findProduct(String productID)throws DataNotFoundException{
		if(!productDataAccess.productIDExists(productID)){
			throw new DataNotFoundException();
		}
		return productDataAccess.selectProduct(productID);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.ProductService#getAllProducts()
	 * 
	 * 3. ProductDao ��ü�� ����� ��� ��ǰ ������ ���ؼ� �����Ѵ�.
	 */
	@Override
	public Product[] getAllProducts() {
        return productDataAccess.selectAllProducts();
	}

	@Override
	public void registerProduct(Product product) throws DataDuplicatedException {
		if(productDataAccess.productIDExists(product.getProductID())){
			throw new DataDuplicatedException();
		}
		productDataAccess.insertProduct(product);
	}

	@Override
	public void removeProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

}
