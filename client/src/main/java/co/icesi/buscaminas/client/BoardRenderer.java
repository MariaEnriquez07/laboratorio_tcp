package co.icesi.buscaminas.client;

import co.icesi.buscaminas.models.Cell;
import com.google.gson.Gson;

public class BoardRenderer {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void printBoard(Object boardObj) {
        if (boardObj == null) {
            System.out.println("Tablero no inicializado");
            return;
        }

        Gson gson = new Gson();
        Cell[][] board = gson.fromJson(gson.toJsonTree(boardObj), Cell[][].class);

        if (board == null || board.length == 0) {
            System.out.println("Tablero vacio");
            return;
        }

        int rows = board.length;
        int cols = board[0].length;

        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.printf("%3d ", j);
        }
        System.out.println();

        System.out.print("   +");
        for (int j = 0; j < cols; j++) {
            System.out.print("----");
        }
        System.out.println("+");

        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < cols; j++) {
                Cell cell = board[i][j];
                String symbol = getCellSymbol(cell);
                System.out.printf("%3s ", symbol);
            }
            System.out.println("|");
        }

        System.out.print("   +");
        for (int j = 0; j < cols; j++) {
            System.out.print("----");
        }
        System.out.println("+");
    }

    private static String getCellSymbol(Cell cell) {
        if (cell.isMarked()) {
            return ANSI_YELLOW + "[M]" + ANSI_RESET;
        } else if (cell.isHide() && !cell.isShowAll()) {
            return "[.]";
        } else if (cell.isLandMine()) {
            return ANSI_RED + "[*]" + ANSI_RESET;
        } else if (cell.getValue() == 0) {
            return "[ ]";
        } else {
            return "[" + cell.getValue() + "]";
        }
    }
}
