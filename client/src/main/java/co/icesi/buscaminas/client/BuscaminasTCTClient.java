package co.icesi.buscaminas.client;

import co.icesi.buscaminas.models.Request;
import co.icesi.buscaminas.models.Response;
import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;

public class BuscaminasTCPClient {
    private String host;
    private int port;
    private Gson gson;

    public BuscaminasTCPClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.gson = new Gson();
    }

    public Response sendRequest(Request request) throws IOException {
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String jsonOut = gson.toJson(request);
            writer.write(jsonOut);
            writer.newLine();
            writer.flush();

            String jsonIn = reader.readLine();
            return gson.fromJson(jsonIn, Response.class);
        }
    }
}