import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeSubmission extends Employee {

    private Date submittedDate;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public EmployeeSubmission(String title, String fname, String lname, String dept, String phone, String gender) {
        super(title, fname, lname, dept, phone, gender);
        this.submittedDate = new Date();
    }

    public void addTime() {
        if (this.submittedDate == null) {
            this.submittedDate = new Date();
        }
    }

    public void addTime(Date submittedDate) {
        if (this.submittedDate == null) {
            this.submittedDate = submittedDate;
        }
    }

    @Override
    public String toString() {
        return sdf.format(submittedDate) + ": " + super.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Employee) {
            return this.submittedDate.compareTo(((EmployeeSubmission) o).submittedDate);
        }

        return 0;
    }

    public String getSubmittedDate() {
        return sdf.format(submittedDate);
    }

    @Override
    public String getDept() {
        return super.getDept();
    }

    @Override
    public String getFname() {
        return super.getFname();
    }

    @Override
    public String getLname() {
        return super.getLname();
    }

    @Override
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getGender() {
        return super.getGender();
    }
}
