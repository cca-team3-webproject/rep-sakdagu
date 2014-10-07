/**
 * ���ϸ� : MemberDaoImpl.java
 * �ۼ��� : 2014. 2. 12.
 * ���ϼ��� : 
 */
package casestudy.dataaccess;

import java.sql.*;
import java.util.ArrayList;

import casestudy.business.domain.Member;
import casestudy.business.service.MemberDao;

import javax.naming.*;
import javax.sql.*;

/**
 * ȸ�� ���� ������ �׼��� ó���� JDBC API�� Ȱ���Ͽ� ������ Ŭ������ �����ͺ��̽� ���Ӱ� Member ���̺��� ����ϴ� SQL ����
 * ������ ���� ���� ó���� �����Ѵ�.
 * 
 * @author �����(kidmania@hotmail.com)
 * 
 */
public class MemberDaoImpl implements MemberDao {
	private DataSource dataSource;

	/*
	 * 1. JNDI API�� �̿��Ͽ� ���̹� ���񽺿� ���(���ε�)�� DataSource�� �˻��Ѵ� ("jdbc.sakdaguDB" �̶�
	 * ���� �̸����� �˻�) Driver Ŭ����(com.mysql.jdbc.Driver)�� Class�� forName �޼��带 ����Ͽ�
	 * �ε��Ѵ�.
	 */
	public MemberDaoImpl() {
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
	 * casestudy.business.service.MemberDao#insertMember(casestudy.business.
	 * domain.Member)
	 * 
	 * 2. ���ڷ� ���� Member ��ü ������ ���� Member ���̺� ���ο� ���ڵ带 �߰�(insert) �Ѵ�. 2.1.
	 * Member ���̺� ���ڵ带 INSERT�ϴ� SQL ������ �ۼ��Ѵ�. 2.2. obtainConnection() �޼ҵ带 ȣ���ؼ�
	 * Connection�� ���´�. 2.3. Statement ��ü�� �����Ѵ�. 2.4. executeUpdate() �޼ҵ带 ȣ����
	 * INSERT SQL���� �����Ų��.
	 */
	@Override
	public void insertMember(Member member) {
		String query = "INSERT INTO sakdagu_Member VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		/*
		 * "INSERT INTO Member VALUES ('" + member.getMemberID() + "', '" +
		 * member.getPassword() + "', '" + member.getName() + "', '" +
		 * member.getEmail() + "', '" + member.getTel() + "', '" +
		 * member.getZipcode() + "', '" + member.getAddress() + "', '" +
		 * member.getPoint() + "', '" + new
		 * Date(System.currentTimeMillis()).toString() + "' )";
		 */
		System.out.println("MemberDAOImpl insertMember() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, member.getMemberID());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			stmt.setString(4, member.getEmail());
			stmt.setString(5, member.getTel());
			stmt.setString(6, member.getZipcode1());
			stmt.setString(7, member.getZipcode2());
			stmt.setString(8, member.getAddress());
			stmt.setInt(9, member.getPoint());
			stmt.setString(10, new Date(System.currentTimeMillis()).toString());
			stmt.execute();

		} catch (SQLException se) {
			System.err.println("MemberDAOImpl insertMember() Error :"
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.MemberDao#selectMember(java.lang.String)
	 * 
	 * 3. ���ڷ� ���� memberID�� ���ڵ带 ã��(select) �ش� ������ ���� Member ��ü�� �����Ѵ�. 3.1.
	 * Member ���̺��� ���ڵ带 SELECT�ϴ� SQL ������ �ۼ��Ѵ�. 3.2. obtainConnection() �޼ҵ带
	 * ȣ���ؼ� Connection�� ���´�. 3.3. Statement ��ü�� �����Ѵ�. 3.4. executeQuery()�� ȣ����
	 * SELECT SQL���� �����Ų��. 3.5. ResultSet ��ü�� ���� ����� �ʵ� �����͵�� Member ��ü�� ä��
	 * �����Ѵ�.
	 */
	@Override
	public Member selectMember(String memberID) {
		Member member = null;

		String query = "SELECT * FROM sakdagu_Member where MEMBERID = ?";

		System.out.println("MemberDAOImpl selectMember() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, memberID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				member = new Member(rs.getString("MemberID"),
						rs.getString("Password"), rs.getString("Name"),
						rs.getString("Email"), rs.getString("Tel"),
						rs.getString("Zipcode1"), rs.getString("Zipcode2"),
						rs.getString("Address"), rs.getInt("Point"),
						rs.getDate("MemberDate"));
			}

		} catch (SQLException se) {
			System.err.println("MemberDAOImpl selectMember() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
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

		return member;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.MemberDao#updateMember(casestudy.business.
	 * domain.Member)
	 * 
	 * 4. ���ڷ� ���� Member ��ü�� ������ Member ���̺��� ���ڵ带 ����(update) �Ѵ�. 4.1. Member ���̺���
	 * ���ڵ带 UPDATE�ϴ� SQL ������ �ۼ��Ѵ�. 4.2. obtainConnection() �޼ҵ带 ȣ���ؼ� Connection��
	 * ���´�. 4.3. Statement ��ü�� �����Ѵ�. 4.4. executeUpdate()�� ȣ���� UPDATE SQL����
	 * �����Ų��.
	 */
	@Override
	public void updateMember(Member member) {
		String query = "UPDATE sakdagu_Member SET Password=? , Name=?, Email=?, Tel=?, Zipcode1=?, Zipcode2=?, Address=?"
				+ " WHERE MemberID=?";
		/*
		 * String query = "UPDATE Member SET Password='" + member.getPassword()
		 * + "', " + "Name='" + member.getName() + "', Email='" +
		 * member.getEmail() + "', " + "Tel='" + member.getTel() +
		 * "', Zipcode='" + member.getZipcode() + "', " + "Address='" +
		 * member.getAddress() + "'" + " WHERE MemberID='" +
		 * member.getMemberID() + "'";
		 */System.out.println("MemberDAOImpl updateMember() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, member.getPassword());
			stmt.setString(2, member.getName());
			stmt.setString(3, member.getEmail());
			stmt.setString(4, member.getTel());
			stmt.setString(5, member.getZipcode1());
			stmt.setString(6, member.getZipcode2());
			stmt.setString(7, member.getAddress());
			stmt.setString(8, member.getMemberID());
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("MemberDAOImpl updateMember() Error :"
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.MemberDao#deleteMember(casestudy.business.
	 * domain.Member)
	 * 
	 * 5. ���ڷ� ���� Member ��ü�� ������ ���� Member ���̺��� ���ڵ带 ����(delete) �Ѵ�. 5.1. Member
	 * ���̺��� ���ڵ带 DELETE�ϴ� SQL ������ �ۼ��Ѵ�. 5.2. obtainConnection() �޼ҵ带 ȣ���ؼ�
	 * Connection�� ���´�. 5.3. Statement ��ü�� �����Ѵ�. 5.4. executeUpdate()�� ȣ����
	 * DELETE SQL���� �����Ų��.
	 */
	@Override
	public void deleteMember(Member member) {

		String query = "DELETE FROM sakdagu_Member WHERE MemberID=?";

		System.out.println("MemberDAOImpl deleteMember() query: " + query);
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, member.getMemberID());
			stmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("MemberDAOImpl deleteMember() Error :"
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.MemberDao#checkMember(java.lang.String,
	 * java.lang.String)
	 * 
	 * ���ڷ� ���� ���̵�� �н����� ������ �α��� ���� ����(id, password Ȯ��)�� Ȯ���ϰ� �ش� ������ ���� Member
	 * ��ü�� �����Ѵ�. (1) ���̵� �������� ���� ��� Member�� check�� ���� Member.INVALID_ID ��, (2)
	 * �н����尡 ���� ���� ��� Member�� check ���� Member.INVALID_PASSWORD ��, (3) ���̵�� �н����尡
	 * ��� ��ġ�� ��� Member�� check�� ���� Member.VALID_MEMBER �� �����Ͽ� Member ��ü�� �����Ѵ�.
	 */
	@Override
	public Member checkMember(String memberID, String password) {
		Member member = new Member(memberID, password);

		String query = "SELECT Password, Name, Email, Tel, Zipcode1,Zipcode2, Address, Point, MemberDate FROM sakdagu_Member WHERE MemberID=?";
		System.out.println("MemberDAOImpl checkMember() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, memberID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String pw = rs.getString("Password");
				if (pw.equals(password)) {
					member.setName(rs.getString("Name"));
					member.setEmail(rs.getString("Email"));
					member.setTel(rs.getString("Tel"));
					member.setZipcode1(rs.getString("Zipcode1"));
					member.setZipcode2(rs.getString("Zipcode2"));
					member.setAddress(rs.getString("Address"));
					member.setPoint(rs.getInt("Point"));
					member.setMemberDate(rs.getDate("MemberDate"));
					member.setCheck(Member.VALID_MEMBER);
				} else {
					member.setCheck(Member.INVALID_PASSWORD);
				}
			} else {
				member.setCheck(Member.INVALID_ID);
			}

		} catch (SQLException se) {
			System.err.println("MemberDAOImpl checkMember() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
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

		return member;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.MemberDao#selectAllMembers()
	 * 
	 * 6. Member ���̺��� ��� ȸ�� ������ �˻��� �迭�� ��� �����Ѵ�. 6.1. Member ���̺��� ��� ���ڵ带
	 * SELECT�ϴ� SQL ������ �ۼ��Ѵ�. 6.2. obtainConnection() �޼ҵ带 ȣ���ؼ� Connection��
	 * ���´�. 6.3. Statement ��ü�� �����Ѵ�. 6.4. executeQuery()�� ȣ���� SELECT SQL����
	 * �����Ų��. 6.5. ResultSet ��ü�� ���� ����� �ʵ� �����͵�� ä���� Member ��ü���� �ӽ��÷��ǿ� �߰��Ѵ�.
	 * 6.6. �÷��ǿ� ��� ��ü���� Member �迭�� �Ű� �����Ѵ�.
	 */
	@Override
	public Member[] selectAllMembers() {
		String query = "SELECT MemberID, Password, Name, Email, Tel, Zipcode1, Zipcode2, Address, Point, MemberDate FROM sakdagu_Member";
		System.out.println("MemberDAOImpl selectAllMembers() query: " + query);

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<Member> temp = new ArrayList<Member>();
		Member member = null;

		try {
			connection = obtainConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				member = new Member(rs.getString("MemberID"),
						rs.getString("Password"), rs.getString("Name"),
						rs.getString("Email"), rs.getString("Tel"),
						rs.getString("Zipcode1"), rs.getString("Zipcode2"),
						rs.getString("Address"), rs.getInt("Point"),
						rs.getDate("MemberDate"));

				temp.add(member);
			}

		} catch (SQLException se) {
			System.err.println("MemberDAOImpl selectAllMembers() Error :"
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

		return temp.toArray(new Member[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.MemberDao#memberIDExists(java.lang.String)
	 * 
	 * 7. ���ڷ� ���� memberID�� �ش��ϴ� ���� ���ڵ尡 Member ���̺� �����ϴ��� ���θ� Ȯ���Ѵ�. 7.1. Member
	 * ���̺��� ���ڵ带 SELECT�ϴ� SQL ������ �ۼ��Ѵ�. 7.2. obtainConnection() �޼ҵ带 ȣ���ؼ�
	 * Connection�� ���´�. 7.3. Statement ��ü�� �����Ѵ�. 7.4. executeQuery()�� ȣ����
	 * SELECT SQL���� �����Ų��. 7.5. ResultSet ��ü�� Ȯ���� ���ڵ尡 �����ϸ� true, ������ false��
	 * �����Ѵ�.
	 */
	@Override
	public boolean memberIDExists(String memberID) {
		boolean result = false;

		String query = "SELECT MemberID FROM sakdagu_Member WHERE MEMBERID =?";
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, memberID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				result = true;
			}
		} catch (SQLException se) {
			System.err.println("MemberDAOImpl memberIDExists() Error :"
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
	public boolean isAdmin(String memberID) {
		boolean result = false;

		String query = "SELECT ID FROM sakdagu_Admin WHERE ID = ? ";
		System.out.println(query + memberID);
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, memberID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				System.err.println(rs.getString("id") + memberID + " ����");
				result = true;
			}
		} catch (SQLException se) {
			System.err.println("MemberDAOImpl isAdmin() Error :"
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
}
