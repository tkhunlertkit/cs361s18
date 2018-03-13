import com.google.gson.Gson;

import java.util.ArrayList;

public class DirectoryEditor {

    private enum State {IDLE, ADD}

    private State state = State.IDLE;
    private DirectoryProxy dp;
    private ArrayList<Employee> emps = new ArrayList<>();

    public DirectoryEditor(){
        dp = new DirectoryProxy();
    }

    public void execute(String command) {
        String testedCommand = command.trim().toUpperCase();
        switch(testedCommand) {
            case "ADD":
                this.state = State.ADD;
                break;
            case "END":
                this.state = State.IDLE;
                System.out.println("Sending...");
                System.out.println(new Gson().toJson(emps));
                System.out.println("Complete Sending\n");
                this.dp.add(new Gson().toJson(emps));
                this.emps.clear();
                break;
            case "PRINT":
                this.dp.print();
                break;
            case "CLR":
                this.dp.clear();
                break;
            default:
                if (this.state == State.ADD) {
                    try {
                        String[] empElem = command.split(" ");
                        this.emps.add(new Employee(empElem[0], empElem[1], empElem[2], empElem[3], empElem[4], empElem[5]));
                    } catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid employee info");
                    }
                } else {
                    System.out.println("Invalid Command...");
                }
                break;
        }
    }
}
