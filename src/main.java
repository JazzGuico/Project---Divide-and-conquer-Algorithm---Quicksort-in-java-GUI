import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class main {
    private JFrame mainframe;
    private List<JButton> buttonList = new ArrayList<>();
    private List<JPanel> valuePanels = new ArrayList<>();
    private int[] numbers;
    private final Color defaultColor = new Color(0x457b9d);
    private final Color selectedColor = new Color(0x003049);
    private final Color highlightColor = new Color(0x6a040f); // pivot highlight
    private final Color panelDefaultColor = new Color(0x457b9d);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new main().quicksort());
    }

    private void quicksort() {
        mainframe = new JFrame("Divide and Conquer Algorithm - Quicksort");
        mainframe.setSize(1280, 720);
        mainframe.setLayout(null);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color bgColor = new Color(0x90e0ef);
        mainframe.getContentPane().setBackground(bgColor);

        JLabel header = new JLabel("Welcome to Quicksort Algorithm!", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setBounds(0, 30, 1280, 50);
        header.setForeground(new Color(0x1d3557));
        mainframe.add(header);

        JLabel pivotLabel = new JLabel("Choose a pivot", SwingConstants.CENTER);
        pivotLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        pivotLabel.setBounds(0, 90, 1280, 30);
        pivotLabel.setForeground(new Color(0x1d3557));
        mainframe.add(pivotLabel);
        
        JLabel pop3 = new JLabel("Original set of values");
        pop3.setFont(new Font("Arial", Font.BOLD, 22));
        pop3.setBounds(530, 180, 1280, 50);
        pop3.setForeground(new Color(0x1d3557));
        mainframe.add(pop3);
        
        JLabel pop4 = new JLabel("Sorted values using quicksort algorithm");
        pop4.setFont(new Font("Arial", Font.BOLD, 22));
        pop4.setBounds(435, 430, 1280, 50);
        pop4.setForeground(new Color(0x1d3557));
        mainframe.add(pop4);

        List<Integer> numberPool = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            numberPool.add(i);
        }

        Collections.shuffle(numberPool);
        numbers = numberPool.subList(0, 7).stream().mapToInt(Integer::intValue).toArray();

        int buttonCount = numbers.length;
        int buttonSize = 80;
        int spacing = 40;
        int totalWidth = buttonCount * buttonSize + (buttonCount - 1) * spacing;
        int startX = (1280 - totalWidth) / 2;
        int yTop = 240;
        int yBottom = yTop + buttonSize + 160;

        final JButton[] selectedButton = {null};

        for (int i = 0; i < numbers.length; i++) {
            int x = startX + i * (buttonSize + spacing);
            int index = i;

            JButton numButton = new JButton(String.valueOf(numbers[i]));
            numButton.setFont(new Font("Arial", Font.BOLD, 24));
            numButton.setBackground(defaultColor);
            numButton.setForeground(new Color(0xf1faee));
            numButton.setFocusPainted(false);
            numButton.setBounds(x, yTop, buttonSize, buttonSize);

            numButton.addActionListener(e -> {
                if (selectedButton[0] != null) {
                    selectedButton[0].setBackground(defaultColor);
                }

                numButton.setBackground(selectedColor);
                selectedButton[0] = numButton;

                int pivotValue = Integer.parseInt(numButton.getText());
                int[] workingCopy = numbers.clone();
                visualQuickSort(workingCopy, 0, workingCopy.length - 1, pivotValue);
            });

            buttonList.add(numButton);
            mainframe.add(numButton);

            // Panel below each button for the divided part (valuePanels)
            JPanel valuePanel = new JPanel();
            valuePanel.setLayout(new BorderLayout());
            valuePanel.setBounds(x, yBottom, buttonSize, buttonSize);
            valuePanel.setBackground(panelDefaultColor);

            JLabel valueLabel = new JLabel(String.valueOf(numbers[i]), SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
            valueLabel.setForeground(Color.WHITE);
            valuePanel.add(valueLabel, BorderLayout.CENTER);

            valuePanels.add(valuePanel);
            mainframe.add(valuePanel);
            
            
        }

        mainframe.setVisible(true);
    }

    private void visualQuickSort(int[] arr, int low, int high, int pivotValue) {
        new SwingWorker<Void, int[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                quickSortVisual(arr, low, high, pivotValue);
                return null;
            }
        }.execute();
    }

    private void quickSortVisual(int[] arr, int low, int high, int pivotValue) throws InterruptedException {
        if (low < high) {
            int pi = partitionVisual(arr, low, high, pivotValue);
            quickSortVisual(arr, low, pi - 1, pivotValue);
            quickSortVisual(arr, pi + 1, high, pivotValue);
        } else {
            // Once sorting is complete, update the final sorted array
            updateSortedPanel(arr);
        }
    }

    private int partitionVisual(int[] arr, int low, int high, int pivotValue) throws InterruptedException {
        int pivot = arr[high];
        highlightPanel(high, Color.ORANGE); // highlight pivot
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            highlightPanel(j, Color.YELLOW); // checking element
            Thread.sleep(1000);

            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                updateDividedPanel(arr, pivotValue);
                highlightPanel(i, Color.GREEN); // swapped
                highlightPanel(j, Color.GREEN);
                Thread.sleep(1000);
            }

            resetPanelColor(j);
        }

        swap(arr, i + 1, high);
        updateDividedPanel(arr, pivotValue);
        highlightPanel(i + 1, Color.RED); // pivot final position
        Thread.sleep(1000);

        resetAllPanelColors();
        return i + 1;
    }

    private void updateDividedPanel(int[] arr, int pivotValue) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < arr.length; i++) {
                JLabel label = (JLabel) valuePanels.get(i).getComponent(0);
                label.setText(String.valueOf(arr[i]));
                if (arr[i] == pivotValue) {
                    valuePanels.get(i).setBackground(highlightColor);
                } else {
                    valuePanels.get(i).setBackground(panelDefaultColor);
                }
            }
        });
    }

    private void updateSortedPanel(int[] arr) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < arr.length; i++) {
                JLabel label = (JLabel) valuePanels.get(i).getComponent(0);
                label.setText(String.valueOf(arr[i]));
                valuePanels.get(i).setBackground(panelDefaultColor);
            }
        });
    }

    private void highlightPanel(int index, Color color) {
        SwingUtilities.invokeLater(() -> valuePanels.get(index).setBackground(color));
    }

    private void resetPanelColor(int index) {
        SwingUtilities.invokeLater(() -> valuePanels.get(index).setBackground(panelDefaultColor));
    }

    private void resetAllPanelColors() {
        SwingUtilities.invokeLater(() -> {
            for (JPanel panel : valuePanels) {
                panel.setBackground(panelDefaultColor);
            }
        });
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
