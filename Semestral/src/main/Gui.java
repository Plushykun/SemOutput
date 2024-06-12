package Final;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Gui extends JFrame {
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton speechButton;

    public Gui() {
        // Initialize components
        initComponents();

        // Configure the JFrame
        setTitle("Time-Scheme Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void initComponents() {
        // Panel for the task input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        // Text field for entering new tasks
        taskField = new JTextField();
        inputPanel.add(taskField, BorderLayout.CENTER);

        // Panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        addButton = new JButton("Add Task");
        buttonPanel.add(addButton);

        deleteButton = new JButton("Delete Task");
        buttonPanel.add(deleteButton);

        speechButton = new JButton("Speech to Text");
        buttonPanel.add(speechButton);

        inputPanel.add(buttonPanel, BorderLayout.EAST);
        add(inputPanel, BorderLayout.NORTH);

        // Model and list for displaying tasks
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskCellRenderer());
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Add ActionListeners
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

        speechButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startSpeechRecognition();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error initializing speech recognizer", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void addTask() {
        String taskText = taskField.getText().trim();
        if (!taskText.isEmpty()) {
            taskListModel.addElement(new Task(taskText));
            taskField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Task cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "No task selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startSpeechRecognition() throws IOException {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        recognizer.startRecognition(true);

        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            String resultText = result.getHypothesis();
            if (!resultText.isEmpty()) {
                taskField.setText(resultText);
                addTask();
                break;
            }
        }
        recognizer.stopRecognition();
    }

    // Custom cell renderer to change color based on completion status and add checkbox
    private static class TaskCellRenderer extends JPanel implements ListCellRenderer<Task> {
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

            if (isSelected) {
                setBackground(Color.BLUE);
                label.setForeground(Color.WHITE);
            }

            checkBox.addActionListener(e -> {
                value.setCompleted(checkBox.isSelected());
                list.repaint();
            });

            return this;
        }
    }

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
