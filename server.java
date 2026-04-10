import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    // Helper method that accepts HttpExchange
    public static void serveFile(HttpExchange res, String filePath, String contentType) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        res.getResponseHeaders().set("Content-Type", contentType);
        res.sendResponseHeaders(200, bytes.length);
        res.getResponseBody().write(bytes);
        res.getResponseBody().close();
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("Running at http://localhost:8080");

        server.createContext("/", new HttpHandler() {
            public void handle(HttpExchange res) throws IOException {
                serveFile(res, "index.html", "text/html");
            }
        });

        server.createContext("/style.css", new HttpHandler() {
            public void handle(HttpExchange res) throws IOException {
                serveFile(res, "style.css", "text/css");
            }
        });

        server.createContext("/code.js", new HttpHandler() {
            public void handle(HttpExchange res) throws IOException {
                serveFile(res, "code.js", "application/javascript");
            }
        });

        // Returns all bird groups and their counts as a JSON array
        // e.g. [{"name":"Owl","value":3},{"name":"Eagle","value":2},...]
        server.createContext("/data", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            DataAnalyzer analyzer = new DataAnalyzer();
            ArrayList<String> birds = FileOperator.getStringList("names.txt");
            HashMap<String, ArrayList<String>> groups = analyzer.groupByType(birds);

            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            for (String type : groups.keySet()) {
                int count = groups.get(type).size();
                if (count == 0) continue; // skip empty groups
                if (!first) json.append(",");
                json.append("{\"name\":\"").append(type).append("\",\"value\":").append(count).append("}");
                first = false;
            }
            json.append("]");

            byte[] response = json.toString().getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });

        server.createContext("/stats", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            DataAnalyzer analyzer = new DataAnalyzer();
            ArrayList<String> birds = FileOperator.getStringList("names.txt");
            HashMap<String, ArrayList<String>> groups = analyzer.groupByType(birds);
            String largest = analyzer.largestGroup(groups);
            String json = analyzer.statsToJson(largest);
            byte[] response = json.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });

        server.start();
    }
}