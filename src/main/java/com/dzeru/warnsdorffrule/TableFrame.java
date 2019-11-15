package com.dzeru.warnsdorffrule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TableFrame extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField startCellField;
    private GameOverDialog gameOverDialog;

    private Generator generator = new Generator();
    private Object[] columnsHeader = Constants.LETTERS;
    private int[] horseCoordinates = {1, 1};
    private int[][] posCells;
    private int[] minNextCellValueCoordinates = {0, 0};
    private int minNextCellValue = -1;

    public TableFrame() {
        super("Правило Варнсдорфа");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        for (int i = 0; i < Constants.CELL_COUNT; i++)
            tableModel.addRow(generator.cellValues[i]);

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new ChessCellRenderer());
        table.setCellSelectionEnabled(false);

        JButton step = new JButton("Сделать ход");
        step.setFont(Constants.FONT);
        step.setBackground(Constants.MIN_NEXT_CELL_COLOR);
        step.addActionListener(e -> calculateStep());

        JButton restart = new JButton("Рестарт");
        restart.setFont(Constants.FONT);
        restart.setBackground(Constants.NEXT_CELL_COLOR);
        restart.addActionListener(e -> restart());

        JButton start = new JButton("Старт");
        start.setFont(Constants.FONT);
        start.setBackground(Constants.VISITED_CELL_COLOR);
        start.addActionListener(e -> start());

        JLabel startCellLabel = new JLabel("Введите старт. клетку:");
        startCellLabel.setFont(Constants.SMALL_FONT);
        startCellLabel.setBackground(Color.white);

        startCellField = new JTextField("A1");
        startCellField.setFont(Constants.FONT);
        startCellField.setEditable(true);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.white);
        buttons.add(start);
        buttons.add(step);
        buttons.add(restart);
        buttons.add(startCellLabel);
        buttons.add(startCellField);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 9;
        constraints.gridheight = 9;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        getContentPane().add(table, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 9;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.anchor = GridBagConstraints.SOUTHWEST;

        getContentPane().add(buttons, constraints);

        JTextArea description = new JTextArea(Constants.DESCRIPTION);
        description.setFont(Constants.SMALL_FONT);
        description.setBorder(BorderFactory.createEmptyBorder(Constants.INSETS_SIZE, Constants.INSETS_SIZE, Constants.INSETS_SIZE, Constants.INSETS_SIZE));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 4;
        constraints.gridheight = 10;
        constraints.gridx = 10;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        getContentPane().add(description, constraints);

        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setVisible(true);
    }

    private void start() {
        String statusText = startCellField.getText();
        if(statusText.length() == 2) {
            horseCoordinates = generator.getHorseCoordinatesByString(statusText);
        }
        getNextPossibleCoordinates();
    }

    private void restart() {
        generator.cellValues = Generator.initCellValues();
        generator.visited = Generator.initVisited();
        horseCoordinates = new int[]{1, 1};
        minNextCellValueCoordinates = new int[]{0, 0};
        minNextCellValue = -1;
        getNextPossibleCoordinates();
    }

    private void calculateStep() {
        try {
            clearNextPossibleCoordinates();
            generator.visited[horseCoordinates[0]][horseCoordinates[1]] = true;
            getNextPossibleCoordinates();
            getMinNextCellValueCoordinates();
            horseCoordinates = getNextHorseCoordinates();
            generator.cellValues = generator.generateCellValues();
            clearNextPossibleCoordinates();
            getNextPossibleCoordinates();

            if(minNextCellValue == Integer.MAX_VALUE || horseCoordinates[0] == Integer.MAX_VALUE || horseCoordinates[1] == Integer.MAX_VALUE) {
                if(gameOverDialog == null)
                    gameOverDialog = new GameOverDialog(this);
                gameOverDialog.setVisible(true);
            }

            for(int i = 1; i < Constants.CELL_COUNT; i++) {
                for(int k = 1; k < Constants.CELL_COUNT; k++) {
                    tableModel.setValueAt(String.valueOf(generator.cellValues[i][k]), i, k);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getNextPossibleCoordinates() {
        int[] s1 = {horseCoordinates[0] + 1, horseCoordinates[1] + 2};
        int[] s2 = {horseCoordinates[0] - 1, horseCoordinates[1] - 2};
        int[] s3 = {horseCoordinates[0] + 1, horseCoordinates[1] - 2};
        int[] s4 = {horseCoordinates[0] - 1, horseCoordinates[1] + 2};

        int[] s5 = {horseCoordinates[0] + 2, horseCoordinates[1] + 1};
        int[] s6 = {horseCoordinates[0] - 2, horseCoordinates[1] - 1};
        int[] s7 = {horseCoordinates[0] + 2, horseCoordinates[1] - 1};
        int[] s8 = {horseCoordinates[0] - 2, horseCoordinates[1] + 1};

        posCells = new int[][]{s1, s2, s3, s4, s5, s6, s7, s8};
        getMinNextCellValueCoordinates();
    }

    private void clearNextPossibleCoordinates() {
        posCells = null;
    }

    private void getMinNextCellValueCoordinates() {
        minNextCellValue = Integer.MAX_VALUE;

        int[] posCellsValues = new int[posCells.length];

        for(int i = 0; i < posCells.length; i++) {
            if(generator.possible(posCells[i])) {
                posCellsValues[i] = Integer.parseInt((String) tableModel.getValueAt(posCells[i][0], posCells[i][1]));
            }
            else {
                posCellsValues[i] = -1;
            }
        }

        for(int i = 0; i < posCellsValues.length; i++) {
            if(posCellsValues[i] > 0 && posCellsValues[i] < minNextCellValue) {
                minNextCellValue = posCellsValues[i];
                minNextCellValueCoordinates = posCells[i];
            }
        }
    }

    private int[] getNextHorseCoordinates() {
        return minNextCellValueCoordinates;
    }

    class ChessCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row, int column) {
            DefaultTableModel wtm = (DefaultTableModel) table.getModel();
            table.setFont(Constants.FONT);
            table.setRowHeight(Constants.CELL_HEIGHT);

            TableColumn col;
            for (int i = 0; i < Constants.CELL_COUNT; i++) {
                col = table.getColumnModel().getColumn(i);
                col.setMaxWidth(Constants.CELL_WIDTH);
                col.setMinWidth(Constants.CELL_WIDTH);
                col.setPreferredWidth(Constants.CELL_WIDTH);
            }

            setBackground(Color.white);

            if(posCells != null && posCells.length > 0) {
                for (int[] posCell : posCells) {
                    if (row > 0 && row < Constants.CELL_COUNT &&
                            column > 0 && column < Constants.CELL_COUNT &&
                            row == posCell[0] && column == posCell[1]) {
                        setBackground(Constants.NEXT_CELL_COLOR);
                    }
                }
            }

            if (generator.visited[row][column]) {
                setBackground(Constants.VISITED_CELL_COLOR);
            }

            if(row == minNextCellValueCoordinates[0] && column == minNextCellValueCoordinates[1]) {
                setBackground(Constants.MIN_NEXT_CELL_COLOR);
            }

            if(row == horseCoordinates[0] && column == horseCoordinates[1]) {
                setBackground(Constants.HORSE_CELL_COLOR);
            }

            if(row == 0 || column == 0) {
                setBackground(Constants.INDEX_CELL_COLOR);
            }

            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);

            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }
    }

    class GameOverDialog extends JDialog
    {
        public GameOverDialog(JFrame owner)
        {
            super(owner, "Game Over!", true);
            add(new JLabel("<html><h1>Больше ходить некуда!</h1></html>"),
                    BorderLayout.CENTER);

            JButton ok = new JButton("ok");
            ok.setBackground(Constants.MIN_NEXT_CELL_COLOR);
            ok.setFont(Constants.FONT);
            ok.addActionListener(event -> setVisible(false));

            JPanel panel = new JPanel();
            panel.add(ok);
            panel.setBackground(Color.white);
            add(panel, BorderLayout.SOUTH);
            setSize(400, 300);
        }
    }
}
