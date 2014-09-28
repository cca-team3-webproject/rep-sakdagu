package casestudy.business.service;

import java.util.Map;

import casestudy.business.domain.Board;
import casestudy.dataaccess.BoardDaoImpl;

public class BoardServiceImpl implements BoardService {
	BoardDao BoardDataAccess = new BoardDaoImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardService#readBoard(int)
	 */
	@Override
	public Board readBoard(int num) throws DataNotFoundException {
		if (!BoardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
		Board board = BoardDataAccess.selectBoard(num);
		BoardDataAccess.addReadCount(num);
		return board;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardService#findBoard(int)
	 */
	@Override
	public Board findBoard(int num) throws DataNotFoundException {
		if (!BoardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
		Board board = BoardDataAccess.selectBoard(num);
		return board;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardService#getBoardList(java.util.Map)
	 */
	@Override
	public Board[] getBoardList(Map<String, Object> searchInfo) {
		return BoardDataAccess.selectBoardList(searchInfo)
				.toArray(new Board[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardService#getBoardCount(java.util.Map)
	 */
	@Override
	public int getBoardCount(Map<String, Object> searchInfo) {

		return BoardDataAccess.selectBoardCount(searchInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.BoardService#writeBoard(casestudy.business
	 * .domain.Board)
	 */
	@Override
	public void writeBoard(Board board) {
		BoardDataAccess.insertBoard(board);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.BoardService#updateBoard(casestudy.business
	 * .domain.Board)
	 */
	@Override
	public void updateBoard(Board board) throws DataNotFoundException {
		if (!BoardDataAccess.boardNumExists(board.getNum())) {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
		BoardDataAccess.updateBoard(board);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see casestudy.business.service.BoardService#removeBoard(int)
	 */
	@Override
	public void removeBoard(int num) throws DataNotFoundException {
		if (!BoardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
		BoardDataAccess.deleteBoard(num);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * casestudy.business.service.BoardService#replyBoard(Board)
	 * 
	 * BoardDao 객체를 사용해 답글 정보를 등록한다.
	 */
	@Override
	public void replyBoard(Board board) throws DataNotFoundException {
		if (!BoardDataAccess.boardNumExists(board.getNum())) {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
		BoardDataAccess.insertReplyBoard(board);
	}

	@Override
	public String[] getSubCategoryList(String category) {
		return BoardDataAccess.getSubCategoryList(category).toArray(new String[0]);
	}

}
