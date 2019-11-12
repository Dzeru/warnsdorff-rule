package com.dzeru.warnsdorffrule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableFrame extends JFrame {
        private DefaultTableModel tableModel;
        private JTable table;

        private Generator generator = new Generator();
        private Object[] columnsHeader = Generator.LETTERS;
        private int horseCoordinates[] = {1, 1};

    public TableFrame() {
        super("TableModel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        for (int i = 0; i < Generator.CELL_COUNT; i++)
            tableModel.addRow(generator.cellValues[i]);

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new ChessCellRenderer());

        JButton step = new JButton("Step");
        step.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateStep();
            }
        });

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));
        getContentPane().add(contents);

        JPanel buttons = new JPanel();
        buttons.add(step);
        getContentPane().add(buttons, "South");
        setSize(1000, 1000);
        setVisible(true);
    }

    private void calculateStep() {
        generator.visited[horseCoordinates[0]][horseCoordinates[1]] = true;
        horseCoordinates = getNextHorseCoordinates();
        generator.cellValues = generator.generateCellValues();

        if(horseCoordinates[0] == Integer.MAX_VALUE || horseCoordinates[1] == Integer.MAX_VALUE) {
            System.out.println("GAME OVER!!!");
            //make button inactive;
        }

        for(int i = 1; i < Generator.CELL_COUNT; i++) {
            for(int k = 1; k < Generator.CELL_COUNT; k++) {
                tableModel.setValueAt(String.valueOf(generator.cellValues[i][k]), i, k);
            }
        }
        //tableModel.setValueAt(String.valueOf(new Random().nextInt()), 2, 2);
    }

    private int[] getNextHorseCoordinates() {
        int[] s1 = {horseCoordinates[0] + 1, horseCoordinates[1] + 2};
        int[] s2 = {horseCoordinates[0] - 1, horseCoordinates[1] - 2};
        int[] s3 = {horseCoordinates[0] + 1, horseCoordinates[1] - 2};
        int[] s4 = {horseCoordinates[0] - 1, horseCoordinates[1] + 2};

        int[] s5 = {horseCoordinates[0] + 2, horseCoordinates[1] + 1};
        int[] s6 = {horseCoordinates[0] - 2, horseCoordinates[1] - 1};
        int[] s7 = {horseCoordinates[0] + 2, horseCoordinates[1] - 1};
        int[] s8 = {horseCoordinates[0] - 2, horseCoordinates[1] + 1};

        int[] nextCell = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int minNextCellValue = Integer.MAX_VALUE;

        int[][] posCells = {s1, s2, s3, s4, s5, s6, s7, s8};

        for(int i = 0; i < posCells.length; i++) {
            for(int k = 0; k < posCells.length; k++) {
                if(i != k) {
                    if(generator.possible(posCells[i])) {
                        int m1 = Integer.parseInt((String) tableModel.getValueAt(posCells[i][0], posCells[i][1]));
                        if(generator.possible(posCells[k])) {
                            int m2 = Integer.parseInt((String) tableModel.getValueAt(posCells[k][0], posCells[k][1]));
                            if(m1 < minNextCellValue) {
                                minNextCellValue = m1;
                                nextCell = posCells[i];
                            }
                            if(m2 < minNextCellValue && m2 < m1) {
                                minNextCellValue = m2;
                                nextCell = posCells[k];
                            }
                        }
                        else {
                            if(m1 < minNextCellValue) {
                                minNextCellValue = m1;
                                nextCell = posCells[i];
                            }
                        }
                    }
                }
            }
        }

        return nextCell;
    }

    class ChessCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row, int column) {
            DefaultTableModel wtm = (DefaultTableModel) table.getModel();
            Font font = new Font("Verdana", Font.PLAIN, 24);
            table.setFont(font);
            table.setRowHeight(100);

            TableColumn col;
            for (int i = 0; i < Generator.CELL_COUNT; i++) {
                col = table.getColumnModel().getColumn(i);
                col.setMaxWidth(100);
                col.setMinWidth(100);
                col.setPreferredWidth(100);
            }

            if (generator.visited[row][column]) {
                setBackground(Constants.VISITED_CELL_COLOR);
            } else {
                setBackground(Color.white);
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
}
