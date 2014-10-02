package casestudy.dataaccess;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Image;

public class ImageDao {
	private DataSource dataSource;

	/*
	 * JNDI API를 이용하여 네이밍 서비스에 바인딩(jdbc/imageDB)된 DataSource를 검색한다.
	 */
	public ImageDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/jdbc/imageDB");
		} catch (NamingException ne) {
			System.err.println("A JNDI error occured.");
			ne.printStackTrace(System.err);
			throw new RuntimeException("A JNDI error occurred. "
					+ ne.getMessage());
		}
	}

	/*
	 * DataSource의 getConnection() 메소드를 통해 Connection을 만든다.
	 */
	private Connection obtainConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public Image selectImage(int imageId) {
		String query = "SELECT image_name, content_type, contents FROM sakdagu_product_image WHERE image_id=?";
		System.out.println("ImageDao selectImage() query: " + query);

		Image image = null;

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, imageId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String imageName = rs.getString("image_name");
				String contentType = rs.getString("content_type");
				Blob contents = rs.getBlob("contents");
				image = new Image(imageId, imageName, contentType,
						contents.getBytes(1L, (int) contents.length()));
			}

		} catch (SQLException se) {
			System.err.println("ImageDao selectBoardCount() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

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

		return image;
	}

	public Image[] selectAllImages() {
		String query = "SELECT image_id, image_name, content_type FROM sakdagu_product_image";
		System.out.println("ImageDao selectAllImages() query: " + query);

		Image image = null;

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Image> imageList = new ArrayList<Image>();

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int imageId = rs.getInt("image_id");
				String imageName = rs.getString("image_name");
				String contentType = rs.getString("content_type");
				image = new Image(imageId, imageName, contentType);
				imageList.add(image);
			}

		} catch (SQLException se) {
			System.err.println("ImageDao selectAllImages() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

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

		return imageList.toArray(new Image[0]);
	}

	public void insertImage(int optionID, Image image) {
		String query = "INSERT INTO sakdagu_product_image (optionID, contents) VALUES (?, ?)";
		System.out.println("ImageDao insertImage() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			byte[] contents = image.getContents();
			// first, create an input stream
			InputStream is = new ByteArrayInputStream(contents);
			stmt.setInt(1, optionID);
			// set the value of the input parameter to the input stream
			stmt.setBinaryStream(2, is, contents.length);
			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("ImageDao insertImage() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

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
