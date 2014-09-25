/**
 * ���ϸ� : ProductService.java
 * �ۼ��� : 2014. 2. 12.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;

/**
 * ��ǰ�� ������ ������ ó���� ������ ���񽺸� ����� ��ü�� �԰��� ������ �������̽�.<br/> 
 * ����Ͻ� ���� ���� �������̽��� ǥ���Ǵ� Ư�� ������ Ư�� �μ� ó���� ������ ���� �� ���������� �����ȴ�.<br/>
 * ����Ͻ� ���� ���� ���ø����̼��� �߽��� �Ǹ�, ���ø����̼��� ��� �߰��� �����̶� �ַ� ����Ͻ� ���� ���� �����̴�.
 * ���� ������ ��Ű��ó�� ���� ���ø����̼��� ����� ���ؼ��� ����Ͻ� ���� ���� �� ����� ���� �߿��ϴ�.
 *  
 * @author �����(kidmania@hotmail.com)
 *
 */

public interface ProductService {
	
    /**
     * �μ��� �־��� productID�� �ش��ϴ� ��ǰ�� �˻��Ѵ�.
     *
     * @param productID �˻��ϰ��� �ϴ� ��ǰ�� productID
     * @return �˻��� ��ǰ������ ��� �ִ� Product ��ü
     * @throws DataNotFoundException productID�� �ش��ϴ� ��ǰ�� �������� ���� ��� �߻�
     */
	public Product findProduct(String productID) throws DataNotFoundException;
	
    /**
     * �μ��� �־��� productID�� �ش��ϴ� ��ǰ�� ����Ѵ�.
     *
     * @param product ����ϰ��� �ϴ� ��ǰ ������ ����ִ� Product ��ü
     * @throws DataNotFoundException product�� �ߺ��Ǵ� ��ǰ�� ���� ��� �߻�
     */
	public void registerProduct(Product product) throws DataDuplicatedException;
	
    /**
     * ��� ��ǰ�� �˻��Ѵ�.
     * 
     * @return �˻��� ��� ��ǰ������ ��� �ִ� Product �迭
     */
	public Product[] getAllProducts();

	public void removeProduct(Product product);
	
}
