package battleship;

import board.Board;
import display.*;
import ships.Point;

public class BattleShip {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Player player1 = new DumbComputerPlayer(new Board(), "computer_player1");
		Player player2 = new RandomComputerPlayer(new Board(), "computer_player2");
		
		Player activePlayer = player1;
		Player nonactivePlayer = player2;
		Player tempPlayer = null;
		
		boolean hitResult = false;
		
		while(!player1.isDefeated() && !player2.isDefeated())
		{
			activePlayer.moveShips();
			while(!activePlayer.isTurnOver())
			{
				hitResult = nonactivePlayer.isHit(activePlayer.takeAShot());
				activePlayer.getResponse(hitResult);
			}
			
			tempPlayer = activePlayer;
			activePlayer = nonactivePlayer;
			nonactivePlayer = tempPlayer;
		}
	}

}
