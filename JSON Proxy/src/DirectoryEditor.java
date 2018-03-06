public class DirectoryEditor {

    private enum State {IDLE, ADD}

    private State state = State.IDLE;
    private DirectoryProxy dp;

    public DirectoryEditor(){
        dp = new DirectoryProxy();
    }

    public void execute(String command) {
        command = command.trim().toUpperCase();
        switch(command) {
            case "ADD":
                this.state = State.ADD;
                break;
            case "END":
                this.state = State.IDLE;
                this.dp.end();
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
                        this.dp.add(new Employee(empElem[0], empElem[1], empElem[2], empElem[3]));
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
