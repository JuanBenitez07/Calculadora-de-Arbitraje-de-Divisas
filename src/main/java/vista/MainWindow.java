package vista;

import controlador.controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(controller controller) {

        setTitle("Calculadora de Arbitraje de Divisas");
        setSize(950, 750);
        setMinimumSize(new Dimension(820, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(buildMenuBar());

        JPanel container = new JPanel(new BorderLayout(0, 16));
        container.setBackground(AppColors.BACKGROUND);
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        InputPanel input = new InputPanel(controller);
        ResultPanel result = new ResultPanel();

        input.setResultPanel(result);

        container.add(input, BorderLayout.NORTH);
        container.add(result, BorderLayout.CENTER);

        setContentPane(container);
    }

    private JMenuBar buildMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu ayuda = new JMenu("Ayuda");
        ayuda.setFont(AppFonts.LABEL_BOLD);

        JMenuItem quEsArbitraje = new JMenuItem("¿Qué es el arbitraje de divisas?");
        quEsArbitraje.setFont(AppFonts.LABEL);
        quEsArbitraje.addActionListener(e -> {
            InfoDialog dialog = new InfoDialog(this);
            dialog.setVisible(true);
        });

        ayuda.add(quEsArbitraje);
        menuBar.add(ayuda);

        return menuBar;
    }
}