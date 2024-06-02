import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {

    private JTextField taskField;
    private JButton addButton;
    private JButton deleteButton;
    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;
    private List<String> tasks;

    public Gui() {
        super("Time-Scheme Application");
        setLayout(new BorderLayout());

        // Create task input field and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        taskField = new JTextField(20);
        taskField.setBackground(Color.WHITE);
        taskField.setForeground(Color.BLACK);
        addButton = new JButton("Add Task");
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.WHITE);
        deleteButton = new JButton("Delete Task");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(deleteButton);
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);
        add(inputPanel, BorderLayout.NORTH);

        // Create task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(Color.WHITE);
        taskList.setForeground(Color.BLACK);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Initialize tasks list
        tasks = new ArrayList<>();

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        // Set up frame
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void addTask() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            taskListModel.addElement(task);
            taskField.setText("");
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex!= -1) {
            tasks.remove(selectedIndex);
            taskListModel.remove(selectedIndex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui gui = new Gui();
                gui.setVisible(true);
            }
        });
    }
}