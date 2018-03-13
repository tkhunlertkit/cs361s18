import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class DirectoryProxy implements Directory{

    @Override
    public void add(String jsonEmpList) {
        this.sendCommand("ADD " + jsonEmpList);
    }

    @Override
    public void print() {
        this.sendCommand("PRINT {}");
    }

    @Override
    public void clear() {
        // this.sendCommand("CLR {}");
    }

    private void sendCommand(String command) {
        try {
            // Client will connect to this location
            URL site = new URL("http://localhost:8000/sendresults");
            HttpURLConnection conn = (HttpURLConnection) site.openConnection();

            // now create a POST request
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            // build a string that contains JSON from console
            String content = "";
            content = command;

            // write out string to output buffer for message
            out.writeBytes(content);
            out.flush();
            out.close();

            System.out.println("Done sent to server");

            conn.getInputStream();

        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }

    }
}
