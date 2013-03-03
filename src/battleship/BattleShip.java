package battleship;

import board.Board;
import board.HitResponse;
import ships.Point;
import ships.Ship.ShipType;

import java.util.List;

public class BattleShip {

	public static void main(String[] args) {
		Player player1 = new RandomComputerPlayer(new Board(), "computer_player1");
		Player player2 = new ExpertComputerPlayer(new Board(), "computer_player2");

		Player activePlayer = player1;
		Player nonactivePlayer = player2;
		Player tempPlayer = null;

		HitResponse hitResult;
		List<Point> actualShipLocation;

		int turnCount = 0;

		while(!player1.isDefeated() && !player2.isDefeated())
		{
			activePlayer.moveShips();
			while(!activePlayer.isTurnOver())
			{
				Shot shot = activePlayer.takeAShot();
				
				ShipType shipTargeted = activePlayer.getTargedShipType();
				actualShipLocation = nonactivePlayer.getShipLocation(shipTargeted);
				
				hitResult = nonactivePlayer.isHit(shot);
				
				activePlayer.informActualLocation(actualShipLocation);
				activePlayer.getResponse(hitResult);
			}

			tempPlayer = activePlayer;
			activePlayer = nonactivePlayer;
			nonactivePlayer = tempPlayer;
			turnCount++;
		}

		System.out.println("The game is over after " + turnCount/2 + " turns");
		if(player1.isDefeated())
			System.out.println(player2.getName() + " wins!!");
		else
			System.out.println(player1.getName() + " wins!!");
	}
}
