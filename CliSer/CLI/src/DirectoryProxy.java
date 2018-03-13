import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DirectoryProxy implements Directory{

    private String ip;

    public DirectoryProxy() {
        this("localhost");
    }

    public DirectoryProxy(String ip) {
        this.ip = ip;
    }

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
         this.sendCommand("CLRSPEC {}");
    }

    private void sendCommand(String command) {
        try {
            // Client will connect to this location
            URL site = new URL("http://" + this.ip + ":8000/sendresults");
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
