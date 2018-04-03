import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class MainDirectory implements Directory {

    List<EmployeeSubmission> employees = new ArrayList<>();
    private static MainDirectory instance = new MainDirectory();

    private MainDirectory(){}

    public static MainDirectory getInstance() {
        return instance;
    }

    @Override
    public void add(String jsonEmpList) {
//        System.out.println(jsonEmpList);
        Collection<EmployeeSubmission> emps = new Gson().fromJson(jsonEmpList, new TypeToken<Collection<EmployeeSubmission>>(){}.getType());
        Date submittedDate = new Date();
        emps.forEach(e -> {
            System.out.println("hello");
            e.addTime(submittedDate);
            this.employees.add(e);
        });
        System.out.println("Receive after json decode");
        System.out.println(emps);
        System.out.println("End of decoded message");
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

    public List<EmployeeSubmission> getEmployeesList() {
        return employees;
    }
}
