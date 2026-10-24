package com.tourmanagement.presentation;

import com.tourmanagement.business.TourServiceImpl;
import com.tourmanagement.business.exception.EntityNotFoundException;
import com.tourmanagement.model.Tour;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class TourFrame extends JFrame implements ActionListener {
    private final transient TourServiceImpl service = new TourServiceImpl();

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nameField;
    private final JTextField posField;
    private final JTextField costField;
    private final JTextField durField;
    private final JTextField timeField;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final JButton updateButton;

    public TourFrame() {
        super();
        setBounds(100, 50, 1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Nev", "Indulas", "Idopont", "Ar", "Perc"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Also resz gombok lebelek meg textfieldek

        nameField = new JTextField(15);
        posField = new JTextField(15);
        timeField = new JTextField(14); // Ide pl. 14:30
        costField = new JTextField(8);
        durField = new JTextField(8);

        JPanel inputFieldsPanel = new JPanel();

        inputFieldsPanel.add(new JLabel("Név:"));
        inputFieldsPanel.add(nameField);
        inputFieldsPanel.add(new JLabel("Indulási hely:"));
        inputFieldsPanel.add(posField);
        inputFieldsPanel.add(new JLabel("Indulási dátum(ÉÉÉÉ-HH-NNTÓÓ:pp):"));
        inputFieldsPanel.add(timeField);
        inputFieldsPanel.add(new JLabel("Benevezési díj:"));
        inputFieldsPanel.add(costField);
        inputFieldsPanel.add(new JLabel("Tura hossza percben:"));
        inputFieldsPanel.add(durField);

        addButton = new JButton("Hozzáadás");
        deleteButton = new JButton("Törlés");
        refreshButton = new JButton("Frissítés");
        updateButton = new JButton("Modosítás");

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
        updateButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(updateButton);

        JPanel southPanel = new JPanel(new GridLayout(2, 1));

        southPanel.add(inputFieldsPanel);
        southPanel.add(buttonPanel);
        add(southPanel, BorderLayout.SOUTH);

        refreshTable();
        setVisible(true);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    fillFieldsFromRow(selectedRow);
                }
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Tour tour : service.findAllTour()) {
            tableModel.addRow(new Object[] {
                    tour.getId(),
                    tour.getName(),
                    tour.getStartPosition(),
                    tour.getStartTime(),
                    tour.getParticipationCost(),
                    tour.getDuration()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addButton.equals(e.getSource())) {
            addAction();
        }
        if (deleteButton.equals(e.getSource())) {
            delAction();
        }
        if (refreshButton.equals(e.getSource())) {
            refreshTable();
        }
        if (updateButton.equals(e.getSource())) {
            updateAction();
        }
    }

    private void addAction() {
        try {
            int cost = Integer.parseInt(costField.getText());
            int dur = Integer.parseInt(durField.getText());
            LocalDateTime time = LocalDateTime.parse(timeField.getText());
            Tour tour = new Tour(null, nameField.getText(), posField.getText(), time, cost, dur);
            service.createTour(tour);
            clearFields();
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Az ár és perc mezőbe csak számot irj");
        } catch (DateTimeParseException e1) {
            JOptionPane.showMessageDialog(this, "A dátum helyét helyesen töltsd ki");
        } catch (IllegalArgumentException ex2) {
            JOptionPane.showMessageDialog(this, ex2.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        posField.setText("");
        timeField.setText("");
        costField.setText("");
        durField.setText("");
    }

    private void delAction() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Kérlek válasz ki egy sort mielött megnyomod a törlés gombot");
            return;
        }
        Long id = (Long) table.getValueAt(row, 0);
        try {
            service.deleteTour(id);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Sikerult torluni");
        } catch (EntityNotFoundException exp) {
            JOptionPane.showMessageDialog(this, "Hiba torles sorran: " + exp.getLocalizedMessage());
        }
    }

    private void fillFieldsFromRow(int row) {
        // Az oszlopok sorrendje a táblázatod alapján: 0:ID, 1:Név, 2:Indulás, 3:Időpont, 4:Ár, 5:Perc
        nameField.setText(table.getValueAt(row, 1).toString());
        posField.setText(table.getValueAt(row, 2).toString());

        // A dátumnál figyelj, mert a LocalDateTime toString() formátuma
        // pont az, amit a LocalDateTime.parse() is vár (ISO)
        timeField.setText(table.getValueAt(row, 3).toString());

        costField.setText(table.getValueAt(row, 4).toString());
        durField.setText(table.getValueAt(row, 5).toString());
    }



    private void updateAction() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Kérlek válasz ki egy sort mielött megnyomod a modosítás gombot");
            return;
        }
        Long id = (Long) table.getValueAt(row, 0);
        try {
            int cost = Integer.parseInt(costField.getText());
            int dur = Integer.parseInt(durField.getText());
            LocalDateTime time = LocalDateTime.parse(timeField.getText());
            Tour tour = new Tour(id, nameField.getText(), posField.getText(), time, cost, dur);
            service.updateTour(tour);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Sikerult modositani");
        } catch (EntityNotFoundException exp) {
            JOptionPane.showMessageDialog(this, "Hiba a frisites soran sorran: " + exp.getLocalizedMessage());
        }
    }
}
