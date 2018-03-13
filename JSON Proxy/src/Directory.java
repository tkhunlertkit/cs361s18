import java.util.Collection;

public interface Directory {

    public void execute(String commandArgs);
    public void add(String jsonEmpList);
    public void print();
    public void clear();
}
