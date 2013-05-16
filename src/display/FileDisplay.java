package display;

import board.Board;
import java.io.*;

public class FileDisplay extends Display {

    private String fileName;

    public FileDisplay(Board board, String playerName, String fileName)
    {
        super(board, playerName);
        this.fileName = fileName;

        writeLine("--------------------------------------------");
    }

    public void writeLine(String line)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(line + "\n");
            out.close();
        }
        catch (Exception e) {}
    }

}
