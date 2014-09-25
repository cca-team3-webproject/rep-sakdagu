/**
 * ���ϸ� : ProductDao.java
 * �ۼ��� : 2014. 2. 12.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;

/**
 * ��ǰ ���� ������ �׼��� ó���� ����� ��ü�� �԰��� ������ �������̽�.<br/> 
 * ������ �׼��� ���� �и������ν� ������ �׼��� �� �̿� ����̳� ������ ����Ǿ 
 * ����Ͻ� ���� ���� ������ ���� �ʴ´�.
 * 
 * @author �����(kidmania@hotmail.com)
 *
 */
public interface ProductDao {
	
    /**
     * ������ ����ҿ��� �μ��� �־��� productID�� �ش��ϴ� ��ǰ������ �˻��Ѵ�.
     *
     * @param productID �˻��ϰ��� �ϴ� ��ǰ�� productID
     * @return �˻��� ��ǰ������ ��� �ִ� Product ��ü
     */
	public Product selectProduct(String productID);
	
    /**
     * ������ ����ҿ��� �μ��� �־��� product�� �ش��ϴ� ��ǰ������ ����Ѵ�.
     *
     * @param product ����ϰ��� �ϴ� ��ǰ ������ ����ִ� Product ��ü
     */
	public void insertProduct(Product product);
	
    /**
     * ������ ����ҿ��� ��� ��ǰ������ �˻��Ѵ�.
     * 
     * @return �˻��� ��� ��ǰ������ ��� �ִ� Product �迭
     */
	public Product[] selectAllProducts();
	
    /**
     * ������ ����ҿ� �μ��� �־��� productID�� �ش��ϴ� ���� ��ǰ������ �ִ��� Ȯ���Ѵ�.
     * 
     * @return �ش��ϴ� ��ǰ������ �����ϸ� true, �������� ������ false
     */
	public boolean productIDExists(String productID);
	
}
