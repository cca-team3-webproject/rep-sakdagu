package casestudy.test;

import casestudy.business.domain.Product;
import casestudy.business.service.ProductDao;
import casestudy.dataaccess.ProductDaoImpl;

public class TestProductDao {
    
    public static void main(String[] args) {
    	
        ProductDao productDao = new ProductDaoImpl();
        
        /**** Select Product  ****/
        Product selectedProduct = productDao.selectProduct("sams110");
        
        System.out.println("ProductID: " + selectedProduct.getProductID());
        System.out.println("MallID: " + selectedProduct.getMallID());
        System.out.println("ProductName: " + selectedProduct.getProductTitle());
        System.out.println("Company: " + selectedProduct.getCompany());
        System.out.println("Price1: " + selectedProduct.getPrice1());
        System.out.println("Price2: " + selectedProduct.getPrice2());
        System.out.println("Install: " + selectedProduct.getInstallment());
        System.out.println("Keyword: " + selectedProduct.getKeyword());
        System.out.println("Detail: " + selectedProduct.getDetail());
        System.out.println("ProductDate: " + selectedProduct.getProductDate());
        System.out.println("PhotoDir: " + selectedProduct.getPhotoDir());
        System.out.println();
        
        /**** Select All Products ****/
        Product[] productList = productDao.selectAllProducts();
        
        for (Product product : productList) {
            System.out.println(product);
        }
      
        /**** Product Exists ****/       
        boolean result = productDao.productIDExists("sams110");
        System.out.println(result);
        
        result = productDao.productIDExists("tempProduct");
        System.out.println(result);
    }
    
}
