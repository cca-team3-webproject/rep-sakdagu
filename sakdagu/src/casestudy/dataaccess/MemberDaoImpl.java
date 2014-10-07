/**
 * 파일명 : MemberDaoImpl.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.dataaccess;

import java.sql.*;
import java.util.ArrayList;

import casestudy.business.domain.Member;
import casestudy.business.service.MemberDao;

import javax.naming.*;
import javax.sql.*;

/**
 * 회원 관련 데이터 액세스 처리를 JDBC API를 활용하여 구현한 클래스로 데이터베이스 접속과 Member 테이블을 사용하는 SQL 문의
 * 수행을 통해 관련 처리를 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 * 
 */
public class MemberDaoImpl implements MemberDao {
	private DataSource dataSource;

	/*
	 * 1. JNDI API를 이용하여 네이밍 서비스에 등록(바인딩)된 DataSource를 검색한다 ("jdbc.sakdaguDB" 이란
	 * 논리적 이름으로 검색) Driver 클래스(com.mysql.jdbc.Driver)를 Class의 forName 메서드를 사용하여
	 * 로딩한다.
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
	 * DriverManager의 getConnection() 메소드를 통해 Connection을 만든다.
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
	 * 2. 인자로 받은 Member 객체 정보를 통해 Member 테이블에 새로운 레코드를 추가(insert) 한다. 2.1.
	 * Member 테이블에 레코드를 INSERT하는 SQL 구문을 작성한다. 2.2. obtainConnection() 메소드를 호출해서
	 * Connection을 얻어온다. 2.3. Statement 객체를 생성한다. 2.4. executeUpdate() 메소드를 호출해
	 * INSERT SQL문을 실행시킨다.
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
	 * 3. 인자로 받은 memberID로 레코드를 찾아(select) 해당 정보를 가진 Member 객체를 리턴한다. 3.1.
	 * Member 테이블에서 레코드를 SELECT하는 SQL 구문을 작성한다. 3.2. obtainConnection() 메소드를
	 * 호출해서 Connection을 얻어온다. 3.3. Statement 객체를 생성한다. 3.4. executeQuery()를 호출해
	 * SELECT SQL문을 실행시킨다. 3.5. ResultSet 객체를 통해 얻어진 필드 데이터들로 Member 객체를 채워
	 * 리턴한다.
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
	 * 4. 인자로 받은 Member 객체의 정보로 Member 테이블의 레코드를 갱신(update) 한다. 4.1. Member 테이블의
	 * 레코드를 UPDATE하는 SQL 구문을 작성한다. 4.2. obtainConnection() 메소드를 호출해서 Connection을
	 * 얻어온다. 4.3. Statement 객체를 생성한다. 4.4. executeUpdate()를 호출해 UPDATE SQL문을
	 * 실행시킨다.
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
	 * 5. 인자로 받은 Member 객체의 정보를 통해 Member 테이블의 레코드를 삭제(delete) 한다. 5.1. Member
	 * 테이블의 레코드를 DELETE하는 SQL 구문을 작성한다. 5.2. obtainConnection() 메소드를 호출해서
	 * Connection을 얻어온다. 5.3. Statement 객체를 생성한다. 5.4. executeUpdate()를 호출해
	 * DELETE SQL문을 실행시킨다.
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
	 * 인자로 받은 아이디와 패스워드 정보로 로그인 가능 여부(id, password 확인)를 확인하고 해당 정보를 담은 Member
	 * 객체를 리턴한다. (1) 아이디가 존재하지 않을 경우 Member의 check의 값을 Member.INVALID_ID 로, (2)
	 * 패스워드가 맞지 않을 경우 Member의 check 값을 Member.INVALID_PASSWORD 로, (3) 아이디와 패스워드가
	 * 모두 일치할 경우 Member의 check의 값을 Member.VALID_MEMBER 로 세팅하여 Member 객체를 리턴한다.
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
	 * 6. Member 테이블에서 모든 회원 정보를 검색해 배열에 담아 리턴한다. 6.1. Member 테이블에서 모든 레코드를
	 * SELECT하는 SQL 구문을 작성한다. 6.2. obtainConnection() 메소드를 호출해서 Connection을
	 * 얻어온다. 6.3. Statement 객체를 생성한다. 6.4. executeQuery()를 호출해 SELECT SQL문을
	 * 실행시킨다. 6.5. ResultSet 객체를 통해 얻어진 필드 데이터들로 채워진 Member 객체들을 임시컬렉션에 추가한다.
	 * 6.6. 컬렉션에 담긴 객체들을 Member 배열에 옮겨 리턴한다.
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
	 * 7. 인자로 받은 memberID에 해당하는 기존 레코드가 Member 테이블에 존재하는지 여부를 확인한다. 7.1. Member
	 * 테이블에서 레코드를 SELECT하는 SQL 구문을 작성한다. 7.2. obtainConnection() 메소드를 호출해서
	 * Connection을 얻어온다. 7.3. Statement 객체를 생성한다. 7.4. executeQuery()를 호출해
	 * SELECT SQL문을 실행시킨다. 7.5. ResultSet 객체를 확인해 레코드가 존재하면 true, 없으면 false를
	 * 리턴한다.
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
				System.err.println(rs.getString("id") + memberID + " 있음");
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
