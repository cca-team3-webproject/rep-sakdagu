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

		String searchText = (String) searchInfo.get("searchText");
		String category = (String) searchInfo.get("category");
		String subCategory = (String) searchInfo.get("subCategory");
		// 1.

		int startRow = (Integer) searchInfo.get("startRow");
		int endRow = (Integer) searchInfo.get("endRow");

		// 2. searchType 값에 따라 사용될 조건절을 생성한다.
		String whereSQL = "";
		//

		if ((category == null) || (category.length() == 0)
				|| (category.equals("전체"))) {
			// whereSQL = "WHERE ";
		} else if (category.equals("베스트")) {
			whereSQL = "WHERE read_count > 2 ";
		} else {
			whereSQL = "WHERE category = ? ";
		}

		if ((searchText != null) && (searchText.length() != 0)) {
			if (whereSQL.equals("")) {
				whereSQL = "WHERE ";
			} else {
				whereSQL += "AND ";
			}
			whereSQL += "title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		}
		if (subCategory != null && subCategory.length() != 0) {
			whereSQL += "AND sub_category = ? ";
		}

		// 3. LIKE절에 포항될 수 있도록 searchText값 앞뒤에 % 기호를 붙인다.
		if (searchText != null && !searchText.equals("")) {
			searchText = "%" + searchText.trim() + "%";
		}

		String query = "SELECT * FROM "
				+ "(SELECT rownum AS r , num, writer, title, read_count, reg_date, category, sub_category, photo_dir FROM "
				+ "(SELECT num, writer, title, read_count, reg_date, category, sub_category, photo_dir FROM sakdagu_board "
				+ whereSQL
				+ " ORDER BY master_num DESC) ) WHERE r BETWEEN ? AND ?";

		System.out.println("BoardDAOImpl selectBoardList() query: " + query
				+ "\n searchText: " + searchText + "\n category: " + category
				+ "\n subCategory: " + subCategory + "\n startRow: " + startRow
				+ "\n endRow: " + endRow);

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
			int index = 1;
			if (!category.equals("전체") && !category.equals("베스트")) {
				stmt.setString(index++, category);
			}

			if ((searchText != null) && (searchText.length() != 0)) {
				stmt.setString(index++, searchText);
				stmt.setString(index++, searchText);
				stmt.setString(index++, searchText);
			}
			if (subCategory != null && subCategory.length() != 0) {
				stmt.setString(index++, subCategory);
			}

			stmt.setInt(index++, startRow);
			stmt.setInt(index++, endRow);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				if (title.length() > 28) {
					title = new StringBuilder(title.substring(0, 28)).append(
							"...").toString();
				}
				Board = new Board(rs.getInt("num"), rs.getString("writer"),
						title, rs.getInt("read_count"),
						rs.getString("reg_date"), rs.getString("category"),
						rs.getString("sub_category"), rs.getString("photo_dir"));
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

		String searchText = (String) searchInfo.get("searchText");
		String category = (String) searchInfo.get("category");
		String subCategory = (String) searchInfo.get("subCategory");
		System.err.println(searchText + category + subCategory);
		// 2. searchType 값에 따라 사용될 조건절을 생성한다.
		String whereSQL = "";
		//

		if ((category == null) || (category.length() == 0)
				|| (category.equals("전체"))) {
			// whereSQL = "WHERE ";
		} else if (category.equals("베스트")) {
			whereSQL = "WHERE read_count > 2 ";
		} else {
			whereSQL = "WHERE category = ? ";
		}
		if ((searchText != null) && (searchText.length() != 0)) {
			if (whereSQL.equals("")) {
				whereSQL = "WHERE ";
			} else {
				whereSQL += "AND ";
			}
			whereSQL += "title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		}
		if (subCategory != null && subCategory.length() != 0) {
			whereSQL += "AND sub_category = ? ";
		}

		// 3. LIKE절에 포항될 수 있도록 searchText값 앞뒤에 % 기호를 붙인다.
		if (searchText != null && !searchText.equals("")) {
			searchText = "%" + searchText.trim() + "%";
		}

		// 4. SELECT 문에 생성된 WHERE 절을 붙인다.
		String query = "SELECT count(num) FROM sakdagu_Board " + whereSQL;
		System.out.println("BoardDAOImpl selectBoardCount() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			// 5. searchType 값에 따라 prepareStatement의 파라미터값을 설정한다.
			int index = 1;
			if (!category.equals("전체") && !category.equals("베스트")) {
				stmt.setString(index++, category);
			}

			if ((searchText != null) && (searchText.length() != 0)) {
				stmt.setString(index++, searchText);
				stmt.setString(index++, searchText);
				stmt.setString(index++, searchText);
			}
			if (subCategory != null && subCategory.length() != 0) {
				stmt.setString(index++, subCategory);
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
				+ ", master_num, reply_order, reply_step, photo_dir ,category, sub_category "
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
						rs.getInt("reply_step"), rs.getString("photo_dir"),
						rs.getString("category"), rs.getString("sub_category"));
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
		System.out.println(board);
		String query = "INSERT INTO sakdagu_board "
				+ "(num, writer, title, contents, ip, read_count, reg_date, "
				+ "mod_date, master_num, category, sub_category, photo_dir) VALUES (sakdagu_board_num_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE, sakdagu_board_num_seq.CURRVAL, ?, ?, ?)";

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
			stmt.setString(5, board.getCategory());
			stmt.setString(6, board.getSubCategory());
			stmt.setString(7, board.getPhotoDir());
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

	@Override
	public List<String> getSubCategoryList(String category) {
		String query;
		if (category.equals("베스트")) {
			query = "select sub_category FROM sakdagu_board WHERE read_count > 2 ";
		} else {
			query = "select sub_category FROM sakdagu_category WHERE category = ?";
		}
		System.out.println("BoardDAOImpl getSubCategoryList() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ArrayList<String> sub_category = new ArrayList<String>();
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			if (!category.equals("베스트")) {
				stmt.setString(1, category);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				sub_category.add(rs.getString(1));
				System.err.println(rs.getString(1));
			}
		} catch (SQLException se) {
			System.err.println("BoardDAOImpl getSubCategoryList() Error :"
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

		return sub_category;
	}
}
