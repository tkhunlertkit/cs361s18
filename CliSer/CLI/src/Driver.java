import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        DirectoryEditor de = new DirectoryEditor();

        try (Scanner s = new Scanner(System.in)) {
            String in;
            System.out.print("[c]onsole or [g]ui: ");
            char op = s.nextLine().toLowerCase().charAt(0);
            if (op == 'c') {
                while (true) {
                    in = s.nextLine();
                    if (in.toLowerCase().startsWith("exit")) {
                        break;
                    }
                    de.execute(in);
                }
            } else if (op == 'g') {
                new EmployeeEditor();
            } else {
                System.out.println("BYE!");
            }
        }
    }

    private static class EmployeeEditor extends JFrame{

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private String[] titles = {"Mr.", "Ms.", "Mrs.", "Dr.", "Col.", "Prof."};
        private String[] gender = {"Male", "Female", "Other"};
        private ArrayList<Employee> employees = new ArrayList<>();
        private int currIndex = 0;

        private JComboBox<String> title = new JComboBox<>(titles);
        private String genderString = gender[0];
        private TextField firstName = new TextField(15);
        private TextField lastName = new TextField(15);
        private TextField department = new TextField(15);
        private TextField phoneNumber = new TextField(15);
        private TextField ipAddress = new TextField(15);

        public EmployeeEditor() {
            this.setSize(500,  500);
            this.setTitle("Employee Editor");
            createContent(this);
            this.pack();
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setVisible(true);
        }

        private void createContent(JFrame jf) {
            JPanel content = new JPanel();
            JPanel allContents = new JPanel();
            ActionListener a = new ButtonAction();

            // Set Up Radio Buttons
            JRadioButton maleButton = new JRadioButton(gender[0]);
            JRadioButton femaleButton = new JRadioButton(gender[1]);
            JRadioButton otherButton = new JRadioButton(gender[2]);
            maleButton.setActionCommand(gender[0]);
            maleButton.addActionListener(a);
            femaleButton.setActionCommand(gender[1]);
            femaleButton.addActionListener(a);
            otherButton.setActionCommand(gender[2]);
            otherButton.addActionListener(a);
            maleButton.setSelected(true);

            // Put Radio buttons into panel
            JPanel genderButtons = new JPanel();
            genderButtons.setLayout(new GridLayout(1, 0));
            genderButtons.add(maleButton);
            genderButtons.add(femaleButton);
            genderButtons.add(otherButton);

            // group all the buttons
            ButtonGroup bg = new ButtonGroup();
            bg.add(maleButton);
            bg.add(femaleButton);
            bg.add(otherButton);

            // Commands Button
            JButton submit = new JButton("ADD");
            submit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Employee tmp = new Employee(title.getSelectedItem().toString(), firstName.getText(), lastName.getText(), department.getText(), phoneNumber.getText(), genderString);
                    if (!employees.contains(tmp)) {
                        employees.add(tmp);
                    }
                    new DirectoryProxy(ipAddress.getText().toString()).add(new Gson().toJson(employees));
                    employees.clear();
                    currIndex = 0;
                    clearDataFields();
                }
            });

            JButton clear = new JButton("CLR");
            clear.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    new DirectoryProxy(ipAddress.getText().toString()).clear();
                    employees.clear();
                    currIndex = 0;
                    clearDataFields();
                }
            });

            JButton print = new JButton("PRINT");
            print.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    new DirectoryProxy(ipAddress.getText().toString()).print();
                }
            });

            JButton next = new JButton("->");
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    employees.add(new Employee(title.getSelectedItem().toString(), firstName.getText(), lastName.getText(), department.getText(), phoneNumber.getText(), genderString));
                    currIndex++;
                    clearDataFields();
                }
            });

            JPanel options = new JPanel();
            options.setLayout(new GridLayout(1, 0));
            options.add(submit);
            options.add(clear);
            options.add(print);

            // setup contents
            content.setLayout(new GridLayout(0, 2));

            JLabel titleLabel = new JLabel("Title");
            titleLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(titleLabel);
            title = new JComboBox<>(titles);
            content.add(title);

            JLabel firstNameLabel = new JLabel("First Name");
            firstNameLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(firstNameLabel);
            content.add(firstName);

            JLabel lastNameLabel = new JLabel("Last Name");
            lastNameLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(lastNameLabel);
            content.add(lastName);

            JLabel genderLabel = new JLabel("Gender");
            genderLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(genderLabel);
            content.add(genderButtons);

            JLabel departmentLabel = new JLabel("Department");
            departmentLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(departmentLabel);
            content.add(department);

            JLabel phoneLabel = new JLabel("Phone Number");
            phoneLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(phoneLabel);
            content.add(phoneNumber);


            JLabel ipLabel = new JLabel("IP Address");
            ipLabel.setHorizontalAlignment(JLabel.RIGHT);
            content.add(ipLabel);
            content.add(ipAddress);
            content.add(new JPanel());
            content.add(options);

            allContents.setLayout(new BorderLayout());
            allContents.add(content, BorderLayout.CENTER);
            allContents.add(next, BorderLayout.EAST);


            jf.add(allContents);
        }

        private void clearDataFields() {
            title.setSelectedIndex(0);
            genderString = gender[0];
            firstName.setText("");
            lastName.setText("");
            department.setText("");
            phoneNumber.setText("");
        }

        private class ButtonAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                genderString = e.getActionCommand();
            }

        }

    }
}
