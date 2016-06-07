package fps2.ex03.labyrinth;

import java.io.*;

public class Solver {

    private final char WALL = '0';
    private final char POSSIBLE_WAY = '.';
    private final char SOLUTION_WAY = '+';

    private char[][] labyrinth;
    //           \ \- contains the chars of one line (x coordinate)
    //            \- contains the lines (y coordinate)

    private int sizeX;
    private int sizeY;

    private int startX;
    private int startY;

    private int endX;
    private int endY;

    /*
     * First command line argument is the file name of the labyrinth input file.
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("You have to specify a file name!");
            return;
        }

        Solver solver = new Solver(args[0]);
    }

    public Solver(String fileName) {
        try {
            readInputFile(fileName);

            printInfo();

            if (probeWay(startX, startY)) {
                printLabyrinth();
            } else
                System.out.println("No solution found!");

        } catch (Exception e) {
            System.err.println("Could not read labyrinth input file!");
            e.printStackTrace();
        }
    }

    private boolean probeWay(int x, int y) {
        //check if coordinates are in bounds of the labyrinth
        if (x < 0 || x > sizeX - 1 || y < 0 || y > sizeY - 1)
            return false;

        //we can not go here
        if (labyrinth[y][x] != POSSIBLE_WAY)
            return false;

        labyrinth[y][x] = SOLUTION_WAY;

        //we reached the end
        if (x == endX && y == endY) {
            return true;
        }

        //try the surrounding possibilities
        if (probeWay(x + 1, y) ||
                probeWay(x, y + 1) ||
                probeWay(x - 1, y) ||
                probeWay(x, y - 1))
            return true;

        labyrinth[y][x] = POSSIBLE_WAY;

        return false;
    }

    private void readInputFile(String fileName) throws Exception {
        String line;
        String[] splittedLine;

        FileReader fileReader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fileReader);

        line = br.readLine();       //read dimensions of labyrinth
        splittedLine = line.split(" ");
        sizeX = Integer.parseInt(splittedLine[0]);
        sizeY = Integer.parseInt(splittedLine[1]);

        line = br.readLine();       //read start point
        splittedLine = line.split(" ");
        startX = Integer.parseInt(splittedLine[0]);
        startY = Integer.parseInt(splittedLine[1]);

        line = br.readLine();       //read end point
        splittedLine = line.split(" ");
        endX = Integer.parseInt(splittedLine[0]);
        endY = Integer.parseInt(splittedLine[1]);

        br.readLine();

        labyrinth = new char[sizeY][];  //Create a new array with the given number of lines

        char[] charsInLine;
        int lineNum = 0;

        while ((line = br.readLine()) != null) {
            labyrinth[lineNum] = line.toCharArray();  //Insert all chars of one line into the array

            lineNum++;
        }

        br.close();
    }

    private void printInfo() {
        System.out.println(String.format("Labyrinth details: Start (%s,%s), End (%s,%s), Size(%s,%s)", startX, startY, endX, endY, sizeX, sizeY));
        System.out.println();
    }

    private void printLabyrinth() {
        StringBuffer sb = new StringBuffer();

        for (char[] i : labyrinth) {
            for (char j : i) {
                sb.append(j);
            }
            sb.append(System.lineSeparator());
        }

        System.out.println(sb.toString());
    }
}
