/**
 * 파일명 : ProductService.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;

/**
 * 상품과 관련한 업무나 처리에 관련한 서비스를 담당할 객체의 규격을 정의한 인터페이스.<br/> 
 * 비즈니스 로직 층은 유스케이스로 표현되는 특정 업무나 특정 부서 처리의 통합인 서비스 및 도메인으로 구성된다.<br/>
 * 비즈니스 로직 층은 어플리케이션의 중심이 되며, 어플리케이션의 기능 추가와 변경이란 주로 비즈니스 로직 층의 변경이다.
 * 따라서 유연한 아키텍처를 가진 어플리케이션을 만들기 위해서는 비즈니스 로직 층을 잘 만드는 것이 중요하다.
 *  
 * @author 고범석(kidmania@hotmail.com)
 *
 */

public interface ProductService {
	
    /**
     * 인수로 주어진 productID에 해당하는 상품을 검색한다.
     *
     * @param productID 검색하고자 하는 상품의 productID
     * @return 검색된 상품정보를 담고 있는 Product 객체
     * @throws DataNotFoundException productID에 해당하는 상품이 존재하지 않을 경우 발생
     */
	public Product findProduct(String productID) throws DataNotFoundException;
	
    /**
     * 인수로 주어진 productID에 해당하는 상품을 등록한다.
     *
     * @param product 등록하고자 하는 상품 정보를 담고있는 Product 객체
     * @throws DataNotFoundException product와 중복되는 상품이 있을 경우 발생
     */
	public void registerProduct(Product product) throws DataDuplicatedException;
	
    /**
     * 모든 상품을 검색한다.
     * 
     * @return 검색된 모든 상품정보를 담고 있는 Product 배열
     */
	public Product[] getAllProducts();

	public void removeProduct(Product product);
	
}
