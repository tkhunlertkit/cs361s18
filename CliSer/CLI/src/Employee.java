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
    public boolean equals(Object other) {
        if (!(other instanceof Employee)) {
            return false;
        } else {
            Employee o = (Employee) other;
            return  o.fname.equalsIgnoreCase(this.fname) &&
                    o.lname.equalsIgnoreCase(this.lname) &&
                    o.title.equalsIgnoreCase(this.title) &&
                    o.dept.equalsIgnoreCase(this.dept) &&
                    o.phone.equalsIgnoreCase(this.phone) &&
                    o.gender.equalsIgnoreCase(this.gender);
        }
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
}
