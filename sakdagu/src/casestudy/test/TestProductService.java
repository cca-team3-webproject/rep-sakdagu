package casestudy.test;

import casestudy.business.domain.Product;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.ProductService;
import casestudy.business.service.ProductServiceImpl;

public class TestProductService {
    
    public static void main(String[] args) throws DataNotFoundException {
    	
    	ProductService service = new ProductServiceImpl();
        
        /**** Find Product  ****/
        Product selectedProduct = service.findProduct("sams110");
        
        System.out.println("ProductID: " + selectedProduct.getProductID());
        System.out.println("MallID: " + selectedProduct.getMallID());
        System.out.println("ProductName: " + selectedProduct.getProductName());
        System.out.println("Company: " + selectedProduct.getCompany());
        System.out.println("Price1: " + selectedProduct.getPrice1());
        System.out.println("Price2: " + selectedProduct.getPrice2());
        System.out.println("Install: " + selectedProduct.getInstallment());
        System.out.println("Keyword: " + selectedProduct.getKeyword());
        System.out.println("Detail: " + selectedProduct.getDetail());
        System.out.println("ProductDate: " + selectedProduct.getProductDate());
        System.out.println("PhotoDir: " + selectedProduct.getPhotoDir());
        System.out.println();
        
        /**** Get All Products ****/
        Product[] productList = service.getAllProducts();
        
        for (Product product : productList) {
            System.out.println(product);
        }
        
    }
    
}
