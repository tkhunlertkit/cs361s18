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

    @Override
    public void add(String jsonEmpList) {
        Collection<Employee> emps = new Gson().fromJson(jsonEmpList, new TypeToken<Collection<Employee>>(){}.getType());
        System.out.println("Receive after json decode");
        System.out.println(emps);
        System.out.println("End of decoded message");
        emps.forEach(e -> this.employees.add(e));
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public void clear() {
        this.employees.clear();
    }

    public String toString() {
        String res = "";
        if (!employees.isEmpty()) {
            Collections.sort(this.employees);
            for (Employee e : this.employees)
                res += e + "\n";
        } else {
            res = "<empty directory>";
        }

        return res;
    }
}
