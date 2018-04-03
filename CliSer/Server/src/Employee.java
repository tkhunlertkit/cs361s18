public class Employee implements Comparable {

    private String fname;
    private String lname;
    private String phone;
    private String dept;
    private String title;
    private String gender;

    public Employee(String title, String fname, String lname, String dept, String phone, String gender) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.dept  = dept;
        this.title = title;
        this.gender = gender;
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
        return title + " " + lname + ", " + fname + ", " + gender + ", is in " + dept + ". Contact: " + phone;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getDept() {
        return dept;
    }

    public String getTitle() {
        return title;
    }

    public String getGender() {
        return gender;
    }
}
