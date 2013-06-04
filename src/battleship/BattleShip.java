package battleship;

import java.util.List;

import battleshipAgents.ExpertComputerPlayer;
import battleshipAgents.ImprovedExpertComputerPlayer;
import battleshipAgents.Player;
import board.Board;
import board.HitResponse;

public class BattleShip
{

    public static void main(String[] args)
    {
        Player player1 = new ImprovedExpertComputerPlayer(new Board(), "computer_player1");
        Player player2 = new ExpertComputerPlayer(new Board(), "computer_player2");

        Player activePlayer = player1;
        Player nonactivePlayer = player2;
        Player tempPlayer = null;

        List<HitResponse> hitResults;
        int turnCount = 0;

        while (!player1.isDefeated() && !player2.isDefeated())
        {
            activePlayer.moveShips();
            while (!activePlayer.isTurnOver())
            {
                List<Shot> shots = activePlayer.takeAShot();

                hitResults = nonactivePlayer.isHit(shots);
                activePlayer.getResponse(hitResults);
            }

            tempPlayer = activePlayer;
            activePlayer = nonactivePlayer;
            nonactivePlayer = tempPlayer;
            turnCount++;
        }

        System.out.println("The game is over after " + turnCount / 2 + " turns");
        if (player1.isDefeated())
            System.out.println(player2.getName() + " wins!!");
        else
            System.out.println(player1.getName() + " wins!!");
    }
}
