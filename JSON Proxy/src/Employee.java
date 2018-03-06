public class Employee implements Comparable {

    private String fname;
    private String lname;
    private String phone;
    private String dept;

    public Employee(String fname, String lname, String dept, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.dept  = dept;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Employee) {
            return this.lname.compareToIgnoreCase(((Employee) o).lname);
        }

        return 0;
    }

    @Override
    public String toString() {
        return lname + ", " + fname + " " + phone + " " + dept;
    }
}
