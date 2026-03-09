import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.Map;

public class Server1 {

    public static void serveFile(HttpExchange res, String filePath, String contentType) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        res.getResponseHeaders().set("Content-Type", contentType);
        res.sendResponseHeaders(200, bytes.length);
        res.getResponseBody().write(bytes);
        res.getResponseBody().close();
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        System.out.println("Running at http://localhost:8081");

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



        server.createContext("/data", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            DataAnalyzerAP analyzer = new DataAnalyzerAP();
            try {
                analyzer.countCategoriesFromFile("status.txt");
            } catch (IOException e) {
                System.out.println("Error reading status.txt: " + e.getMessage());
            }

            Map<String, Integer> counts = analyzer.getAllConservationCounts();
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                if (entry.getValue() == 0) continue;
                if (!first) json.append(",");
                json.append("{\"name\":\"").append(entry.getKey())
                    .append("\",\"value\":").append(entry.getValue()).append("}");
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

            DataAnalyzerAP analyzer = new DataAnalyzerAP();
            try {
                analyzer.countCategoriesFromFile("status.txt");
            } catch (IOException e) {
                System.out.println("Error reading status.txt: " + e.getMessage());
            }

            String json = "{\"totalSpecies\":" + analyzer.getTotalConservationCount() + "}";
            byte[] response = json.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });

        server.start();
    }
}
