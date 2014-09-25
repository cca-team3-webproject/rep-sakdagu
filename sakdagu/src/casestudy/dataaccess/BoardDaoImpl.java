package casestudy.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Board;
import casestudy.business.service.BoardDao;

public class BoardDaoImpl implements BoardDao {

	private DataSource dataSource;

	public BoardDaoImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/jdbc/sakdaguDB");
			// Class.forName("ORacle.jdbc.OracleDriver");
		} catch (NamingException ne) {
			System.err.println("JNDI error occured.");
			ne.printStackTrace(System.err);
			throw new RuntimeException("JNDI error occured." + ne.getMessage());
		}
	}

	@Override
	public List<Board> selectBoardList(Map<String, Object> searchInfo) {

		// 1. searchInfo로부터 검색 조건을 구한다.

		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");
		String category = (String) searchInfo.get("category");
		String subCategory = (String) searchInfo.get("subCategory");
		// 1.

		int startRow = (Integer) searchInfo.get("startRow");
		int endRow = (Integer) searchInfo.get("endRow");

		// 2. searchType 값에 따라 사용될 조건절을 생성한다.
		String whereSQL = "WHERE category = ? and sub_category = ?";
		if ((searchType == null) || (searchType.length() == 0)) {
			// whereSQL = "";
		} else if (searchType.equals("all")) {
			whereSQL = "AND title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		} else if (searchType.equals("title") || searchType.equals("writer")
				|| searchType.equals("contents")) {
			whereSQL = "AND " + searchType + " LIKE ?";
		}

		// 3. LIKE절에 포항될 수 있도록 searchText값 앞뒤에 % 기호를 붙인다.
		if (searchText != null) {
			searchText = "%" + searchText.trim() + "%";
		}

		String query = "SELECT * FROM "
				+ "(SELECT rownum AS r , num, writer, title, read_count, reg_date, category, sub_category FROM "
				+ "(SELECT num, writer, title, read_count, reg_date, category, sub_category FROM sakdagu_board "
				+ whereSQL
				+ " ORDER BY master_num DESC) ) WHERE r BETWEEN ? AND ?";
		System.out.println("BoardDAOImpl selectBoardList() query: " + query
				+ "searchText: " + searchText);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		ArrayList<Board> temp = new ArrayList<Board>();
		Board Board = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			// 5. searchType 값에 따라 prepareStatement의 파라미터값을 설정한다.
			// startRow, endRow 값 포함
			if ((searchType == null) || (searchType.length() == 0)) {
				stmt.setString(1, category);
				stmt.setString(2, subCategory);
				stmt.setInt(3, startRow);
				stmt.setInt(4, endRow);
			} else if (searchType.equals("all")) {
				stmt.setString(1, category);
				stmt.setString(2, subCategory);
				stmt.setString(3, searchText);
				stmt.setString(4, searchText);
				stmt.setString(5, searchText);
				stmt.setInt(6, startRow);
				stmt.setInt(7, endRow);
			} else if (searchType.equals("title")
					|| searchType.equals("writer")
					|| searchType.equals("contents")) {
				stmt.setString(1, category);
				stmt.setString(2, subCategory);
				stmt.setString(3, searchText);
				stmt.setInt(4, startRow);
				stmt.setInt(5, endRow);
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				if (title.length() > 28) {
					title = new StringBuilder(title.substring(0, 28)).append(
							"...").toString();
				}
				Board = new Board(rs.getInt("num"), rs.getString("writer"),
						title, rs.getInt("read_count"),
						rs.getString("reg_date"), rs.getInt("reply_step"));
				temp.add(Board);
			}

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl selectBoardList() Error :"
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

		return temp;
	}

	private Connection obtainConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public int selectBoardCount(Map<String, Object> searchInfo) {
		// 1. searchInfo로부터 검색 조건을 구한다.

		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");

		// 2. searchType 값에 따라 사용될 조건절을 생성한다.
		String whereSQL = "";
		if ((searchType == null) || (searchType.length() == 0)) {
			whereSQL = "";
		} else if (searchType.equals("all")) {
			whereSQL = "WHERE title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		} else if (searchType.equals("title") || searchType.equals("writer")
				|| searchType.equals("contents")) {
			whereSQL = "WHERE " + searchType + " LIKE ?";
		}

		// 3. LIKE절에 포항될 수 있도록 searchText값 앞뒤에 % 기호를 붙인다.
		if (searchText != null) {
			searchText = "%" + searchText.trim() + "%";
		}

		// 4. SELECT 문에 생성된 WHERE 절을 붙인다.
		String query = "SELECT count(num) FROM sakdagu_Board" + whereSQL;
		System.out.println("BoardDAOImpl selectBoardCount() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			// 5. searchType 값에 따라 prepareStatement의 파라미터값을 설정한다.
			if ((searchType == null) || (searchType.length() == 0)) {

			} else if (searchType.equals("all")) {
				stmt.setString(1, searchText);
				stmt.setString(2, searchText);
				stmt.setString(3, searchText);
			} else if (searchType.equals("title")
					|| searchType.equals("writer")
					|| searchType.equals("contents")) {
				stmt.setString(1, searchText);

			}

			rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count(num)");
			}

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl selectBoardCount() Error :"
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
		return count;

	}

	@Override
	public Board selectBoard(int num) {
		String query = "SELECT num,writer,title,contents,ip,read_count,reg_date,mod_date "
				+ ", master_num, reply_order, reply_step "
				+ "FROM sakdagu_board WHERE num=?";
		System.out.println("BoardDAOImpl selectBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Board Board = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Board = new Board(rs.getInt("num"), rs.getString("writer"),
						rs.getString("title"), rs.getString("contents"),
						rs.getString("ip"), rs.getInt("read_count"),
						rs.getString("reg_date"), rs.getString("mod_date"),
						rs.getInt("master_num"), rs.getInt("reply_order"),
						rs.getInt("reply_step"));
			}

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl selectBoard() Error :"
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

		return Board;
	}

	@Override
	public void addReadCount(int num) {
		String query = "UPDATE sakdagu_board SET read_count=read_count+1 WHERE num=?";
		System.out.println("BoardDAOImpl addReadCount() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl addReadCount() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
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

	}

	@Override
	public boolean boardNumExists(int num) {
		boolean result = false;

		String query = "SELECT num FROM sakdagu_board WHERE num=?";
		System.out.println("BoardDAOImpl boardNumExists() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);
			rs = stmt.executeQuery();
			result = rs.next();
		} catch (SQLException se) {
			System.err.println("BoardDAOImpl boardNumExists() Error :"
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
	public void insertBoard(Board board) {
		String query = "INSERT INTO sakdagu_board "
				+ "(num, writer, title, contents, ip, read_count, reg_date,"
				+ "mod_date, master_num) VALUES (board_num_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE, board_num_seq.CURRVAL)";

		System.out.println("BoardDAOImpl insertBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, board.getWriter());
			stmt.setString(2, board.getTitle());
			stmt.setString(3, board.getContents());
			stmt.setString(4, board.getIp());
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl insertBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
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
	}

	@Override
	public void updateBoard(Board board) {
		String query = "UPDATE sakdagu_board SET writer=?, title=?, contents=?, ip=?, mod_date=SYSDATE WHERE num=?";

		System.out.println("BoardDAOImpl updateBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, board.getWriter());
			stmt.setString(2, board.getTitle());
			stmt.setString(3, board.getContents());
			stmt.setString(4, board.getIp());
			stmt.setInt(5, board.getNum());
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl updateBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardDao#deleteBoard(int)
	 */
	@Override
	public void deleteBoard(int num) {
		String query = "DELETE FROM sakdagu_board WHERE num = ?";

		System.out.println("BoardDAOImpl deleteBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl deleteBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardDao#insertReplyBoard(Board)
	 * 
	 * 답글 추가 작업을 위해 원글의 기존 답글들에 대한 정보(reply_order)를 일부 갱신하고 신규 답글에 해당하는 레코드를
	 * 추가한다.
	 */
	@Override
	public void insertReplyBoard(Board board) {
		String query1 = "UPDATE sakdagu_board SET reply_order = reply_order + 1 "
				+ "WHERE master_num = ? AND reply_order > ?";
		System.out.println("BoardDAOImpl insertReplyBoard() query: " + query1);

		String query2 = "INSERT INTO sakdagu_board"
				+ "(num, writer, title, contents, ip, read_count, reg_date,	mod_date, "
				+ "master_num, reply_order, reply_step) "
				+ "VALUES (board_num_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE,"
				+ " ?, ?, ?)";

		System.out.println("BoardDAOImpl insertReplyBoard() query: " + query2);

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query1);
			stmt.setInt(1, board.getMasterNum());
			stmt.setInt(2, board.getReplyOrder());
			stmt.executeUpdate();
			stmt.close();

			stmt = connection.prepareStatement(query2);
			stmt.setString(1, board.getWriter());
			stmt.setString(2, board.getTitle());
			stmt.setString(3, board.getContents());
			stmt.setString(4, board.getIp());
			stmt.setInt(5, board.getMasterNum());
			stmt.setInt(6, board.getReplyOrder() + 1); // 현재 게시글 다음에 위치시켜야 하므로
														// reply_order + 1
			stmt.setInt(7, board.getReplyStep() + 1); // 현재 게시글에 대한 답글이므로
														// reply_step + 1
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl insertReplyBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occurred. " +
			// se.getMessage());

		} finally {
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
	}
}
