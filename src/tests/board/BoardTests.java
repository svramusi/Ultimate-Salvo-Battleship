package tests.board;

import static org.junit.Assert.*;

import org.junit.*;
import board.*;

public class BoardTests {

	private Board board;
	@Before
	public void setUp() throws Exception {
		board = Board.getBoard();
	}

	@Test
	public void testBoardSize() {
		assertEquals(10, board.getWidth());
		assertEquals(10, board.getHeight());
	}
	
}
