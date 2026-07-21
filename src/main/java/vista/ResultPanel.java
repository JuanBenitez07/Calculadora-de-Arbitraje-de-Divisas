package vista;

import model.Results;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ResultPanel extends JPanel {

    private JLabel badge;
    private JLabel ruta;
    private JLabel inicial;
    private JLabel fin;
    private JLabel ganancia;
    private JLabel porcentaje;

    private static final NumberFormat MONEY = NumberFormat.getNumberInstance(Locale.US);
    static {
        MONEY.setMinimumFractionDigits(2);
        MONEY.setMaximumFractionDigits(2);
    }

    public ResultPanel() {

        setBackground(AppColors.CARD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 8, 10, 8);

        JLabel title = new JLabel("Resultado");
        title.setFont(AppFonts.TITLE);
        title.setForeground(AppColors.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);
        gbc.gridwidth = 1;

        badge = new JLabel("Ingresa los datos y presiona calcular");
        badge.setFont(AppFonts.BADGE);
        badge.setForeground(AppColors.TEXT_SECONDARY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(badge, gbc);
        gbc.gridwidth = 1;

        int row = 2;
        ruta = addRow(gbc, row++, "Ruta", AppFonts.MONO);
        inicial = addRow(gbc, row++, "Capital inicial", AppFonts.VALUE);
        fin = addRow(gbc, row++, "Capital final", AppFonts.VALUE);
        ganancia = addRow(gbc, row++, "Ganancia", AppFonts.VALUE_BOLD);
        porcentaje = addRow(gbc, row++, "Rentabilidad", AppFonts.VALUE_BOLD);
    }

    private JLabel addRow(GridBagConstraints gbc, int row, String label, Font valueFont) {

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(AppFonts.LABEL);
        labelComp.setForeground(AppColors.TEXT_SECONDARY);

        JLabel valueComp = new JLabel("-");
        valueComp.setFont(valueFont);
        valueComp.setForeground(AppColors.TEXT_PRIMARY);

        gbc.gridx = 0; gbc.gridy = row;
        add(labelComp, gbc);

        gbc.gridx = 1;
        add(valueComp, gbc);

        return valueComp;
    }

    public void showResult(Results r) {

    if (r.getCycle() == null) {
        badge.setText("No existe una ruta directa entre esas monedas");
        badge.setForeground(AppColors.ERROR);
        ruta.setText("-");
        inicial.setText("-");
        fin.setText("-");
        ganancia.setText("-");
        porcentaje.setText("-");
        return;
    }

    if (!r.isArbitrageFound()) {
        badge.setText("No hay ganancia con este ciclo de monedas");
        badge.setForeground(AppColors.TEXT_SECONDARY);
        ruta.setText("-");
        inicial.setText("$ " + MONEY.format(r.getInitialCapital()));
        fin.setText("$ " + MONEY.format(r.getFinalCapital()));
        ganancia.setText("-$ " + MONEY.format(Math.abs(r.getProfit())));
        ganancia.setForeground(AppColors.ERROR);
        porcentaje.setText(String.format("%.2f %%", r.getPercentage()));
        porcentaje.setForeground(AppColors.ERROR);
        return;
    }

    badge.setText("Se encontró arbitraje");
    badge.setForeground(AppColors.SUCCESS);

    ruta.setText(formatRuta(r.getRoute()));
    inicial.setText("$ " + MONEY.format(r.getInitialCapital()));
    fin.setText("$ " + MONEY.format(r.getFinalCapital()));

    double profit = r.getProfit();
    ganancia.setText("+$ " + MONEY.format(profit));
    ganancia.setForeground(AppColors.SUCCESS);

    porcentaje.setText(String.format("%.2f %%", r.getPercentage()));
    porcentaje.setForeground(AppColors.SUCCESS);
}

// Convierte los saltos de línea del texto plano en <br> dentro de HTML
// para que el JLabel los muestre correctamente, uno debajo del otro.
    private String formatRuta(String rutaPlano) {

        String[] pasos = rutaPlano.split("\n");
        StringBuilder html = new StringBuilder("<html><div style='font-family:Consolas;'>");

        for (String paso : pasos) {
            if (paso.isBlank()) continue;
            html.append(paso.replace(" ", "&nbsp;")).append("<br>");
        }

        html.append("</div></html>");
        return html.toString();
    }
}