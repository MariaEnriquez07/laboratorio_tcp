package co.icesi.buscaminas.client;

import co.icesi.buscaminas.models.Request;
import co.icesi.buscaminas.models.Response;
import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private BuscaminasTCPClient client;
    private Scanner scanner;

    public MainClient() {
        this.client = new BuscaminasTCPClient(HOST, PORT);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("BUSCAMINAS DISTRIBUIDO - CLIENTE TCP");
        System.out.println("=".repeat(50));

        boolean playing = true;
        while (playing) {
            printMenu();
            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1":
                        initGame();
                        break;
                    case "2":
                        selectCell();
                        break;
                    case "3":
                        markCell();
                        break;
                    case "4":
                        getBoard();
                        break;
                    case "5":
                        surrender();
                        break;
                    case "6":
                        playing = false;
                        System.out.println("Hasta luego!");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (IOException e) {
                System.out.println("Error de conexion: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingresa numeros validos");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("[1] Iniciar nueva partida (Filas, Columnas, Minas)");
        System.out.println("[2] Destapar celda (Fila, Columna)");
        System.out.println("[3] Marcar / Desmarcar bandera (Fila, Columna)");
        System.out.println("[4] Consultar estado actual del tablero");
        System.out.println("[5] Rendirse y revelar tablero completo");
        System.out.println("[6] Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private void initGame() throws IOException {
        System.out.print("Numero de filas: ");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.print("Numero de columnas: ");
        int cols = Integer.parseInt(scanner.nextLine());
        System.out.print("Numero de minas: ");
        int mines = Integer.parseInt(scanner.nextLine());

        Request request = new Request("INIT_GAME");
        request.data.put("n", String.valueOf(rows));
        request.data.put("m", String.valueOf(cols));
        request.data.put("minas", String.valueOf(mines));

        Response response = client.sendRequest(request);
        if ("OK".equals(response.status)) {
            System.out.println("Partida iniciada");
            BoardRenderer.printBoard(response.data.get("board"));
        } else {
            System.out.println("Error al iniciar partida");
        }
    }

    private void selectCell() throws IOException {
        System.out.print("Fila: ");
        int i = Integer.parseInt(scanner.nextLine());
        System.out.print("Columna: ");
        int j = Integer.parseInt(scanner.nextLine());

        Request request = new Request("SELECT_CELL");
        request.data.put("i", String.valueOf(i));
        request.data.put("j", String.valueOf(j));

        Response response = client.sendRequest(request);
        BoardRenderer.printBoard(response.data.get("board"));

        Object gameEndObj = response.data.get("gameEnd");
        Object winObj = response.data.get("win");
        
        boolean gameEnd = (gameEndObj instanceof Boolean) ? (Boolean) gameEndObj : false;
        boolean win = (winObj instanceof Boolean) ? (Boolean) winObj : false;

        if (gameEnd) {
            if (win) {
                System.out.println("GANASTE LA PARTIDA!");
            } else {
                System.out.println("EXPLOTÓ UNA MINA! GAME OVER");
            }
        }
    }

    private void markCell() throws IOException {
        System.out.print("Fila: ");
        int i = Integer.parseInt(scanner.nextLine());
        System.out.print("Columna: ");
        int j = Integer.parseInt(scanner.nextLine());

        Request request = new Request("MARK_CELL");
        request.data.put("i", String.valueOf(i));
        request.data.put("j", String.valueOf(j));

        Response response = client.sendRequest(request);
        System.out.println("Celda marcada/desmarcada");
        BoardRenderer.printBoard(response.data.get("board"));
    }

    private void getBoard() throws IOException {
        Request request = new Request("GET_BOARD");
        Response response = client.sendRequest(request);
        BoardRenderer.printBoard(response.data.get("board"));
    }

    private void surrender() throws IOException {
        Request request = new Request("SOW_ALL");
        Response response = client.sendRequest(request);
        System.out.println("Tablero revelado:");
        BoardRenderer.printBoard(response.data.get("board"));
    }

    public static void main(String[] args) {
        MainClient mainClient = new MainClient();
        mainClient.run();
    }
}