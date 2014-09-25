package casestudy.test;

import java.util.Collection;
import java.util.Iterator;

import casestudy.business.domain.Basket;
import casestudy.business.domain.Member;
import casestudy.business.domain.Product;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;
import casestudy.business.service.BasketPurchaserDao;
import casestudy.business.service.MemberDao;
import casestudy.business.service.ProductDao;
import casestudy.dataaccess.BasketPurchaserDaoImpl;
import casestudy.dataaccess.MemberDaoImpl;
import casestudy.dataaccess.ProductDaoImpl;

public class TestBasketPurchaserDao {
    
    public static void main(String[] args) {
        TestBasketPurchaserDao Dao = new TestBasketPurchaserDao();
        Dao.insert();
        
        Dao.selectAll();
        
        Dao.delete();
    }
    
    private void delete() {
        BasketPurchaserDao basketPurchaserDao = new BasketPurchaserDaoImpl();
        basketPurchaserDao.deleteBasket("duke");
    }

    private void selectAll() {
        BasketPurchaserDao basketPurchaserDao = new BasketPurchaserDaoImpl();
        Collection<TheOrder> list = basketPurchaserDao.listBasketPurchaser("duke");
        Iterator<TheOrder> iter = list.iterator();
        while (iter.hasNext()) {
            TheOrder order = iter.next();
            System.out.println("==== Order List ===");    
            System.out.println("OrderNum :" + order.getOrderNum());
            System.out.println("ProductName :" + order.getProductName());
            System.out.println("Price :" + order.getPrice());
            System.out.println("Quantity :" + order.getQuantity());
            System.out.println("Address :" + order.getAddress());
            System.out.println("Paytype :" + order.getPaytype());
            System.out.println("setEmail :" + order.getEmail());
            System.out.println("setTel :" + order.getTel());
        }
    }
    
    private void insert() {
        MemberDao memberDao = new MemberDaoImpl();
        Member member = memberDao.selectMember("duke");
        
        ProductDao productDao = new ProductDaoImpl();
        Product product = productDao.selectProduct("sams110");
        
        BasketPurchaserDao basketPurchaserDao = new BasketPurchaserDaoImpl();
        // Basket Table 등록
        // Maximum Order number
        int orderNo = basketPurchaserDao.orderMaxNo();
        System.out.println("Max Order Number : " + orderNo);
        
        Basket basket = new Basket();
        // 주문번호
        basket.setOrderNum(orderNo);
        // 상품ID
        basket.setProductID(product.getProductID());
        // 수량
        basket.setQuantity(1);
        // 상품가격
        basket.setPrice(product.getPrice2());
        
        // Purchaser Table 등록
        Purchaser purchaser = new Purchaser();
        // 주문번호
        purchaser.setOrderNum(orderNo);
        // 회원ID
        purchaser.setMemberID(member.getMemberID());
        // 주소
        purchaser.setAddress(member.getAddress());
        // 이름
        purchaser.setName(member.getName());
        // 이메일
        purchaser.setEmail(member.getEmail());
        // 전화번호
        purchaser.setTel(member.getTel());
        // 배송지 - 자택(1), 직장(2)
        purchaser.setPlace("1");
        // 카드번호
        purchaser.setCardNumber("N");
        // 카드종류
        purchaser.setCardType("N");
        // 결재 완료처리상태(Y/N)
        purchaser.setPayStatus("N");
        // 온라인입금(remit), 카드(card)
        purchaser.setPayType("remit");
        // 총구매액
        purchaser.setAmount(basket.getTotalPrice());
        
        basketPurchaserDao.insertBasketPurchaser(basket, purchaser, "duke");
    }
}
