import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

public class DirectoryProxy implements Directory{

    @Override
    public void add(String jsonEmpList) {
        MainDirectory.getInstance().add(jsonEmpList);
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
