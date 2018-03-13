import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Server {

    public static void main(String[] args) {

        // set up a simple HTTP server on our local host
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);


            // create a context to get the request to display the results
            server.createContext("/displayresults", new DisplayHandler());

            // create a context to get the request for the POST
            server.createContext("/sendresults",new PostHandler());
            server.setExecutor(null); // creates a default executor

            // get it going
            System.out.println("Starting Server...");
        server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class DisplayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // set up the header
            try {
                String response = MainDirectory.getInstance().toString();
                // write out the response
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }


        }
    }

    private static class PostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            String res = "";
            // set up a stream to read the body of the request
            InputStream inputStr = httpExchange.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = httpExchange.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }

            executeCommand(sb.toString());

            // create our response String to use in other handler
            res = res + sb.toString();

            // respond to the POST with ROGER
            String postResponse = "ROGER JSON RECEIVED";

            System.out.println("response: " + res);

            //Desktop dt = Desktop.getDesktop();
            //dt.open(new File("raceresults.html"));

            // assume that stuff works all the time
            httpExchange.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();
        }

        private void executeCommand(String commandArgs) {
            String command = commandArgs.substring(0, commandArgs.indexOf(' ')).trim();
            String jsonParam = commandArgs.substring(commandArgs.indexOf(' ') + 1);

            switch (command) {
                case "ADD": MainDirectory.getInstance().add(jsonParam); break;
                case "PRINT": MainDirectory.getInstance().print(); break;
                case "CLRSPEC": MainDirectory.getInstance().clear(); break;
                default: System.out.println("Invalid Command"); break;
            }
        }
    }
}
