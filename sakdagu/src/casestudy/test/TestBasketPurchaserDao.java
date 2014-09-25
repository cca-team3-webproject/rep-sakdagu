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
        // Basket Table ���
        // Maximum Order number
        int orderNo = basketPurchaserDao.orderMaxNo();
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
        
        basketPurchaserDao.insertBasketPurchaser(basket, purchaser, "duke");
    }
}
