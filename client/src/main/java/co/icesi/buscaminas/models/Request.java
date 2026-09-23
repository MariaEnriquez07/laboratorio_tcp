package main.java.co.icesi.buscaminas.models;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public String action;
    public Map<String, String> data;

    public Request(String action) {
        this.action = action;
        this.data = new HashMap<>();
    }
}