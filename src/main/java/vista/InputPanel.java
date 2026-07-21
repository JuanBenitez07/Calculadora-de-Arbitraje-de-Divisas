package vista;

import controlador.*;
import model.Results;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputPanel extends JPanel {

    private JTextField capital;
    private JComboBox<String> moneda1;
    private JComboBox<String> moneda2;
    private JComboBox<String> moneda3;
    private JButton calcularManual;
    private ResultPanel resultPanel;
    private controller controller;

    public InputPanel(controller controller) {

        this.controller = controller;

        setBackground(AppColors.CARD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Datos del arbitraje");
        title.setFont(AppFonts.TITLE);
        title.setForeground(AppColors.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 6;
        add(title, gbc);
        gbc.gridwidth = 1;

        String[] monedas = controller.getCurrencyCodes().toArray(new String[0]);

        capital = new JTextField(10);
        styleField(capital);
        capital.setToolTipText("Monto inicial a invertir");
        capital.addActionListener(e -> calcularRutaElegida());

        moneda1 = new JComboBox<>(monedas);
        moneda2 = new JComboBox<>(monedas);
        moneda3 = new JComboBox<>(monedas);
        styleCombo(moneda1);
        styleCombo(moneda2);
        styleCombo(moneda3);

        gbc.gridx = 0; gbc.gridy = 1;
        add(makeLabel("Capital"), gbc);
        gbc.gridx = 1;
        add(capital, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(makeLabel("Moneda 1"), gbc);
        gbc.gridx = 1;
        add(moneda1, gbc);

        gbc.gridx = 2;
        add(makeLabel("Moneda 2"), gbc);
        gbc.gridx = 3;
        add(moneda2, gbc);

        gbc.gridx = 4;
        add(makeLabel("Moneda 3"), gbc);
        gbc.gridx = 5;
        add(moneda3, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 6;
        gbc.insets = new Insets(14, 8, 10, 8);
        add(sep, gbc);
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.gridwidth = 1;

        calcularManual = createButton("Calcular arbitraje", AppColors.PRIMARY, AppColors.PRIMARY_HOVER);
        calcularManual.setToolTipText("Calcula el arbitraje para el ciclo de 3 monedas que elegiste arriba");

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 6;
        add(calcularManual, gbc);
        gbc.gridwidth = 1;

        calcularManual.addActionListener(e -> calcularRutaElegida());
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.LABEL_BOLD);
        label.setForeground(AppColors.TEXT_PRIMARY);
        return label;
    }

    private void styleField(JTextField field) {
        field.setFont(AppFonts.VALUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(AppFonts.VALUE);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(AppColors.BORDER, 1, true));
    }

    private JButton createButton(String text, Color base, Color hover) {

        JButton button = new JButton(text);
        button.setFont(AppFonts.BUTTON);
        button.setForeground(Color.WHITE);
        button.setBackground(base);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(base);
            }
        });

        return button;
    }

    public void setResultPanel(ResultPanel panel) {
        this.resultPanel = panel;
    }

    private void calcularRutaElegida() {
        try {
            double monto = Double.parseDouble(capital.getText().trim());

            if (monto <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El capital debe ser mayor a 0.",
                        "Capital inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String c1 = (String) moneda1.getSelectedItem();
            String c2 = (String) moneda2.getSelectedItem();
            String c3 = (String) moneda3.getSelectedItem();

            if (c1.equals(c2) || c2.equals(c3) || c1.equals(c3)) {
                JOptionPane.showMessageDialog(this,
                        "Elige 3 monedas distintas para formar el ciclo.",
                        "Selección inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Results r = controller.executeArbitrage(monto, c1, c2, c3);
            resultPanel.showResult(r);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa un capital numérico válido.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }
}