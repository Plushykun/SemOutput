package No_sphinx;

// Imports, add the functionalities to the system to be function well.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Main class
public class Gui extends JFrame {

    // Functionalities of the code
    private JTextField taskField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton speechButton;
    private JList<Task> taskList;
    private DefaultListModel<Task> taskListModel;
    private List<Task> tasks;
    private JLabel userNameLabel;

    // Gui Class
    public Gui() {
        super("Time-Scheme Application");
        getContentPane().setLayout(new BorderLayout());

        // Create task input field and buttons and customized their size and colors
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        taskField = new JTextField(20);
        taskField.setBackground(Color.WHITE);
        taskField.setForeground(Color.BLACK);
        addButton = new JButton("Add Task");
        addButton.setBackground(new Color(255, 255, 255));
        addButton.setForeground(Color.BLACK);
        deleteButton = new JButton("Delete Task");
        deleteButton.setBackground(new Color(255, 255, 255));
        deleteButton.setForeground(Color.BLACK);
        speechButton = new JButton("Speech to Text");
        speechButton.setBackground(new Color(255, 255, 255));
        speechButton.setForeground(Color.BLACK);

        // Initializing and calling of methods
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(speechButton);
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);
        getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Create user name label
        userNameLabel = new JLabel("Welcome back, ");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userNameLabel.setForeground(Color.BLACK);
        getContentPane().add(userNameLabel, BorderLayout.SOUTH);

        // Create task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(SystemColor.inactiveCaption);
        taskList.setForeground(Color.BLACK);
        taskList.setCellRenderer(new TaskCellRenderer());
        getContentPane().add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Initialize tasks list
        tasks = new ArrayList<>();

        // Add action listeners use to add functionalities to every buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        // Delete action listeners use to add functionalities to every buttons
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        // Speech action listeners use to add functionalities to every buttons
        speechButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add speech functionality here
                System.out.println("Speech button clicked");
            }
        });

        // Set up frame
        setSize(437, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false); // Hide the main window initially
    }

    // Used to add the task in the jpanel frame
    private void addTask() {
        String taskText = taskField.getText().trim();
        if (!taskText.isEmpty()) {
            Task task = new Task(taskText);
            tasks.add(task);
            taskListModel.addElement(task);
            taskField.setText("");
            // Show notification when a task is added
            JOptionPane.showMessageDialog(this, "Task added: " + taskText);
        }
    }

    // Used to delete the task in the jpanel frame
    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            taskListModel.remove(selectedIndex);
         // Show notification when a task is deleted
            JOptionPane.showMessageDialog(this, "Successfully deleted ");
        }
    }

    // Display the name of the user at the bottom of the screen
    public void setUserName(String userName) {
        userNameLabel.setText("Welcome back, " + userName);
    }

    // Once completed it will select as complete identified if it match the condition based on the user actions
    private static class Task {
        private String text;
        private boolean completed;

        public Task(String text) {
            this.text = text;
            this.completed = false;
        }

        public String getText() {
            return text;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    // Used of implementation to use the list cell renderer for the inputs of the user along to combine with the checkbox
    private class TaskCellRenderer extends JPanel implements ListCellRenderer<Task> {
        private JCheckBox checkBox;
        private JLabel label;

        public TaskCellRenderer() {
            setLayout(new BorderLayout());
            checkBox = new JCheckBox();
            label = new JLabel();
            add(checkBox, BorderLayout.WEST);
            add(label, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {
            checkBox.setSelected(value.isCompleted());
            label.setText(value.getText());

            if (value.isCompleted()) {
                setBackground(Color.LIGHT_GRAY);
                label.setForeground(Color.GREEN);
                label.setFont(label.getFont().deriveFont(Font.BOLD | Font.ITALIC));
            } else {
                setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
                label.setFont(label.getFont().deriveFont(Font.PLAIN));
            }
            // Functionalities of the colors once user click the task it display the color
            if (isSelected) {
                setBackground(Color.BLUE);
                label.setForeground(Color.WHITE);
            }

            checkBox.addActionListener(e -> {
                value.setCompleted(checkBox.isSelected());
                list.repaint();
            });
            // Return the checkbox once completed
            return this;
        }
    }

    // Main Class along with the methods in the other class.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui gui = new Gui();
                NameInput nameInputFrame = new NameInput(gui);
                nameInputFrame.setVisible(true);
            }
        });
    }
}
