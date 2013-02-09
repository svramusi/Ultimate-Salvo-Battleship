package battleship;

import board.Board;
import display.*;
import ships.Point;

public class BattleShip {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Player player1 = new HumanPlayer(new Board(), "human player");
		Player player2 = new DumbComputerPlayer(new Board(), "computer player");
		
		Player activePlayer = player1;
		Player nonactivePlayer = player2;
		Player tempPlayer = null;
		
		boolean hitResult = false;
		
		while(!player1.isDefeated() && !player2.isDefeated())
		{
			activePlayer.moveShips();
			Shot shot = activePlayer.takeAShot();
			hitResult = nonactivePlayer.isHit(shot);
			activePlayer.getResponse(hitResult);
			
			tempPlayer = activePlayer;
			activePlayer = nonactivePlayer;
			nonactivePlayer = tempPlayer;
		}
	}

}
