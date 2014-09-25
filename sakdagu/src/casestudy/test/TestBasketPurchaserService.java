package casestudy.test;

import java.util.Collection;
import java.util.Iterator;

import casestudy.business.domain.Basket;
import casestudy.business.domain.Member;
import casestudy.business.domain.Product;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;
import casestudy.business.service.BasketPurchaserService;
import casestudy.business.service.BasketPurchaserServiceImpl;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.MemberService;
import casestudy.business.service.MemberServiceImpl;
import casestudy.business.service.ProductService;
import casestudy.business.service.ProductServiceImpl;

public class TestBasketPurchaserService {
    
    public static void main(String[] args)throws DataNotFoundException {
        TestBasketPurchaserService dao = new TestBasketPurchaserService();
        dao.insert();
        
        dao.selectAll();
        
        dao.delete();
    }
    
    private void delete() {
        BasketPurchaserService basketPurchaserService = new BasketPurchaserServiceImpl();
        basketPurchaserService.deleteBasket("duke");
    }

    private void selectAll() {
    	BasketPurchaserService basketPurchaserService = new BasketPurchaserServiceImpl();
        Collection<TheOrder> list = basketPurchaserService.listBasketPurchaser("duke");
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
    
    private void insert()throws DataNotFoundException {
        MemberService memberService = new MemberServiceImpl();
        Member member = memberService.findMember("duke");
        
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProduct("sams110");
        
        BasketPurchaserService basketPurchaserService = new BasketPurchaserServiceImpl();
        // Basket Table ���
        // Maximum Order number
        int orderNo = basketPurchaserService.orderMaxNo();
        System.out.println("Max Order Number : " + orderNo);
        
        Basket basket = new Basket();
        // �ֹ���ȣ
        basket.setOrderNum(orderNo);
        // ��ǰID
        basket.setProductID(product.getProductID());
        // ����
        basket.setQuantity(1);
        // ��ǰ����
        basket.setPrice(product.getPrice2());
        
        // Purchaser Table ���
        Purchaser purchaser = new Purchaser();
        // �ֹ���ȣ
        purchaser.setOrderNum(orderNo);
        // ȸ��ID
        purchaser.setMemberID(member.getMemberID());
        // �ּ�
        purchaser.setAddress(member.getAddress());
        // �̸�
        purchaser.setName(member.getName());
        // �̸���
        purchaser.setEmail(member.getEmail());
        // ��ȭ��ȣ
        purchaser.setTel(member.getTel());
        // ����� - ����(1), ����(2)
        purchaser.setPlace("1");
        // ī���ȣ
        purchaser.setCardNumber("N");
        // ī������
        purchaser.setCardType("N");
        // ���� �Ϸ�ó������(Y/N)
        purchaser.setPayStatus("N");
        // �¶����Ա�(remit), ī��(card)
        purchaser.setPayType("remit");
        // �ѱ��ž�
        purchaser.setAmount(basket.getTotalPrice());
        
        basketPurchaserService.insertBasketPurchaser(basket, purchaser, "duke");
    }
}
