package main.java.co.icesi.buscaminas.models;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public String status;
    public Map<String, Object> data;

    public Response() {
        this.data = new HashMap<>();
    }
}