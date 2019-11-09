package com.dzeru.warnsdorffrule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TableFrame extends JFrame {
        private DefaultTableModel tableModel;
        private JTable table;
        private Object[][] cellValues = InitialGenerator.generateCellValues();

        private boolean[][] visited = InitialGenerator.generateVisited();

        private Object[] columnsHeader = InitialGenerator.LETTERS;

    public TableFrame() {
        super("TableModel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        for (int i = 0; i < InitialGenerator.CELL_COUNT; i++)
            tableModel.addRow(cellValues[i]);

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new WineCellRenderer());

        JButton add = new JButton("Step");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.setValueAt("V " + String.valueOf(new Random().nextInt()), 2, 2);
                visited[2][2] = true;
            }
        });

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));
        getContentPane().add(contents);

        JPanel buttons = new JPanel();
        buttons.add(add);
        getContentPane().add(buttons, "South");
        setSize(1000, 1000);
        setVisible(true);
    }

    class WineCellRenderer extends DefaultTableCellRenderer {

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
            for (int i = 0; i < InitialGenerator.CELL_COUNT; i++) {
                col = table.getColumnModel().getColumn(i);
                col.setMaxWidth(100);
                col.setMinWidth(100);
                col.setPreferredWidth(100);
            }

            if (visited[row][column]) {
                setBackground(Color.green);
            } else {
                setBackground(Color.white);
            }

            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }
    }
}
