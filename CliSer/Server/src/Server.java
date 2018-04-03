import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        // set up a simple HTTP server on our local host
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);


            // create a context to get the request to display the results
            server.createContext("/displayresults", new DisplayHandler());
            server.createContext("/displayresults/directory", new DisplayHandlerHTML());
            server.createContext("/displayresults/style.css", new DisplayHandlerCSS());

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
                String encoding = "UTF-8";

//                httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }


        }
    }

    private static class DisplayHandlerHTML implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // set up the header
            try {
                String response = preTable();
                response += employeeHtmlTableFormat(MainDirectory.getInstance().getEmployeesList());
                response += endTable();
                // write out the response
                String encoding = "UTF-8";

                httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }


        }

        private String preTable() {
            return "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "<table>\n" +
                    "    <tr id=\"table-header\">\n" +
                    "        <th>Submission Time</th>\n" +
                    "        <th>Title</th>\n" +
                    "        <th>First Name</th>\n" +
                    "        <th>Last Name</th>\n" +
                    "        <th>Department</th>\n" +
                    "        <th>Phone</th>\n" +
                    "        <th>Gender</th>\n" +
                    "    </tr>";
        }

        private String employeeHtmlTableFormat(List<EmployeeSubmission> employees) {
            String res = "";
            for (EmployeeSubmission e : employees) {
                res += "<tr>";
                res += "<td>" + e.getSubmittedDate() + "</td>\n";
                res += "<td>" + e.getTitle() + "</td>\n";
                res += "<td>" + e.getFname() + "</td>\n";
                res += "<td>" + e.getLname() + "</td>\n";
                res += "<td>" + e.getDept() + "</td>\n";
                res += "<td>" + e.getPhone() + "</td>\n";
                res += "<td>" + e.getGender() + "</td>\n";
                res += "</tr>\n";
            }

            return res;
        }

        private String endTable() {
            return "</table>";
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

    private static class DisplayHandlerCSS implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            try (Scanner s = new Scanner(new File("style.css"))) {
                while (s.hasNextLine()) {
                    response += s.nextLine();
                }
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
