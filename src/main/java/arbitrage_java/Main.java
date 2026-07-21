package arbitrage_java;

import controlador.*;
import model.*;
import vista.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Grafo graph = new Grafo();

        List<String> fiatCodes = Arrays.asList("USD", "EUR", "GBP", "MXN","JPY","CHF","CAD","AUD");

        try {

            ExchangeRateService.loadGraph(graph, fiatCodes, true);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "No se pudieron obtener las tasas de cambio.\n" +
                    "Verifica tu conexión a internet.\n" + e.getMessage(),
                    "Error de red", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller controller = new controller(graph);

        SwingUtilities.invokeLater(() -> {
            new MainWindow(controller).setVisible(true);
        });
    }
}