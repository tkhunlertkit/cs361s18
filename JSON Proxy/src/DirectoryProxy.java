import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

public class DirectoryProxy implements Directory{

    private ArrayList<Employee> emps = new ArrayList<>();

    public void add(Employee e) {
        emps.add(e);
    }

    public void end() {
        this.add(emps);
        emps = new ArrayList<>();
    }

    @Override
    public void add(Collection<Employee> employees) {
        System.out.println("Sending");
        System.out.println(new Gson().toJson(emps));
        System.out.println("End of message\n");
        MainDirectory.getInstance().add(new Gson().toJson(emps));
    }

    @Override
    public void print() {
        MainDirectory.getInstance().print();
    }

    @Override
    public void clear() {
        MainDirectory.getInstance().clear();
    }
}
