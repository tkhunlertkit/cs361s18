import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainDirectory implements Directory {

    List<Employee> employees = new ArrayList<>();
    private static MainDirectory instance = new MainDirectory();

    private MainDirectory(){}

    public static MainDirectory getInstance() {
        return instance;
    }

    public void add(String json) {
        Collection<Employee> emps = new Gson().fromJson(json, new TypeToken<Collection<Employee>>(){}.getType());
        System.out.println("Receive after json decode");
        System.out.println(emps);
        System.out.println("End of decoded message");
        this.add(emps);
    }

    @Override
    public void add(Collection<Employee> employees) {
        employees.forEach(e -> this.employees.add(e));
    }

    @Override
    public void print() {
        if (!employees.isEmpty()) {
            Collections.sort(this.employees);
            this.employees.forEach(e -> System.out.println(e));
        } else {
            System.out.println("<empty directory>");
        }
    }

    @Override
    public void clear() {
        this.employees.clear();
    }
}
