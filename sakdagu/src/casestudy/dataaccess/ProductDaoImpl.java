/**
 * ���ϸ� : ProductDaoImpl.java
 * �ۼ��� : 2014. 2. 12.
 * ���ϼ��� : 
 */
package casestudy.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Product;
import casestudy.business.service.ProductDao;

/**
 * ��ǰ ���� ������ �׼��� ó���� JDBC API�� Ȱ���Ͽ� ������ Ŭ������ �����ͺ��̽� ���Ӱ� Product ���̺��� ����ϴ� SQL ����
 * ������ ���� ���� ó���� �����Ѵ�.
 * 
 * @author ������(kidmania@hotmail.com)
 * 
 */
public class ProductDaoImpl implements ProductDao {

	private DataSource dataSource;

	/*
	 * Driver Ŭ����(com.mysql.jdbc.Driver)�� Class�� forName �޼��带 ����Ͽ� �ε��Ѵ�.
	 */
	public ProductDaoImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/jdbc/sakdaguDB");
			// Class.forName("oracle.jdbc.OracleDriver");
		} catch (NamingException ne) {
			System.err.println("JNDI error occured.");
			ne.printStackTrace(System.err);
			throw new RuntimeException("JNDI error occured." + ne.getMessage());
		}
	}

	/*
	 * DriverManager�� getConnection() �޼ҵ带 ���� Connection�� �����.
	 */
	private Connection obtainConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.ProductDao#selectProduct(java.lang.String)
	 * 
	 * ���ڷ� ���� productID�� ���ڵ带 ã��(select) �ش� ������ ���� Product ��ü�� �����Ѵ�.
	 */
	@Override
	public Product selectProduct(String productID) {
		Product product = null;

		String query = "SELECT ProductID, MallID, ProductName, Company, Price1, Price2, Installment, Keyword, Detail, ProductDate, PhotoDir "
				+ "FROM sakdagu_Product WHERE ProductID=?";
		System.out.println("ProductDAOImpl selectProduct() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, productID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				product = new Product(rs.getString("ProductID"),
						rs.getString("MallID"), rs.getString("ProductName"),
						rs.getString("Company"), rs.getInt("Price1"),
						rs.getInt("Price2"), rs.getString("Installment"),
						rs.getString("Keyword"), rs.getString("Detail"),
						rs.getDate("ProductDate"), rs.getString("PhotoDir"));
			}

		} catch (SQLException se) {
			System.err.println("ProductDAOImpl selectProduct() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occured. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.ProductDao#selectAllProducts()
	 * 
	 * 1. Product ���̺����� ��� ��ǰ ������ �˻��� �迭�� ��� �����Ѵ�. 1.1. Product ���̺����� ��� ���ڵ带
	 * SELECT�ϴ� SQL ������ �ۼ��Ѵ�. 1.2. obtainConnection() �޼ҵ带 ȣ���ؼ� Connection��
	 * ���´�. 1.3. Statement ��ü�� �����Ѵ�. 1.4. executeQuery()�� ȣ���� SELECT SQL����
	 * �����Ų��. 1.5. ResultSet ��ü�� ���� ����� �ʵ� �����͵�� ä���� Product ��ü���� �ӽ��÷��ǿ� �߰��Ѵ�.
	 * 1.6. �÷��ǿ� ��� ��ü���� Product �迭�� �Ű� �����Ѵ�.
	 */
	@Override
	public Product[] selectAllProducts() {
		String query = "SELECT ProductID, MallID, ProductName, Company, Price1, Price2, Installment, Keyword, Detail, ProductDate, PhotoDir FROM sakdagu_Product";
		System.out
				.println("ProductDAOImpl selectAllProducts() query: " + query);

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<Product> temp = new ArrayList<Product>();
		Product product = null;

		try {
			connection = obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				product = new Product(rs.getString("ProductID"),
						rs.getString("MallID"), rs.getString("ProductName"),
						rs.getString("Company"), rs.getInt("Price1"),
						rs.getInt("Price2"), rs.getString("Installment"),
						rs.getString("Keyword"), rs.getString("Detail"),
						rs.getDate("ProductDate"), rs.getString("PhotoDir"));
				temp.add(product);
			}

		} catch (SQLException se) {
			System.err.println("ProductDAOImpl selectAllProducts() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
		}

		return temp.toArray(new Product[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.ProductDao#productIDExists(java.lang.String)
	 * 
	 * 2. ���ڷ� ���� productID�� �ش��ϴ� ���� ���ڵ尡 Product ���̺��� �����ϴ��� ���θ� Ȯ���Ѵ�. 2.1.
	 * Product ���̺����� ���ڵ带 SELECT�ϴ� SQL ������ �ۼ��Ѵ�. 2.2. obtainConnection() �޼ҵ带
	 * ȣ���ؼ� Connection�� ���´�. 2.3. Statement ��ü�� �����Ѵ�. 2.4. executeQuery()�� ȣ����
	 * SELECT SQL���� �����Ų��. 2.5. ResultSet ��ü�� Ȯ���� ���ڵ尡 �����ϸ� true, ������ false��
	 * �����Ѵ�.
	 */
	@Override
	public boolean productIDExists(String productID) {
		boolean result = false;

		String query = "SELECT ProductID FROM PRODUCT WHERE PRODUCTID = ?";
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, productID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException se) {
			System.err.println("ProductDAOImpl productIDExists() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
		}
		return result;
	}

	@Override
	public void insertProduct(Product product) {
		String query = "INSERT INTO sakdagu_product VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? )";
		
		//INSERT INTO  "SKU"."PRODUCT" (PRODUCTID, MALLID, PRODUCTNAME, COMPANY, PRICE1, PRICE2, INSTALLMENT, KEYWORD, DETAIL, PRODUCTDATE, PHOTODIR) VALUES ('1', '2', '3', '4', '5', '6', '7', '8', '9', TO_DATE('2014-09-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '11')

		/*
		 * "INSERT INTO product VALUES ('" + product.getproductID() + "', '" +
		 * product.getPassword() + "', '" + product.getName() + "', '" +
		 * product.getEmail() + "', '" + product.getTel() + "', '" +
		 * product.getZipcode() + "', '" + product.getAddress() + "', '" +
		 * product.getPoint() + "', '" + new
		 * Date(System.currentTimeMillis()).toString() + "' )";
		 */
		System.out.println("productDAOImpl insertProduct() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, product.getProductID());
			stmt.setString(2, product.getMallID());
			stmt.setString(3, product.getProductName());
			stmt.setString(4, product.getCompany());
			stmt.setInt(5, product.getPrice1());
			stmt.setInt(6, product.getPrice2());
			stmt.setString(7, product.getInstallment());
			stmt.setString(8, product.getKeyword());
			stmt.setString(9, product.getDetail());
			stmt.setDate(10, product.getProductDate());
			stmt.setString(11, product.getPhotoDir());
			stmt.execute();

		} catch (SQLException se) {
			System.err.println("productDAOImpl insertProduct() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
	}
}