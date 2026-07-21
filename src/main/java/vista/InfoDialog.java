package vista;

import javax.swing.*;
import java.awt.*;

public class InfoDialog extends JDialog {

    public InfoDialog(Frame owner) {
        super(owner, "¿Qué es el arbitraje de divisas?", true);

        setSize(640, 560);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setText(buildHtmlContent());
        editorPane.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton cerrar = new JButton("Cerrar");
        cerrar.setFont(AppFonts.BUTTON);
        cerrar.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        bottom.add(cerrar);

        add(scrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private String buildHtmlContent() {
        return """
            <html>
            <body style="font-family: Segoe UI, sans-serif; padding: 16px; font-size: 13px; color: #212529;">

                <h2 style="color:#1E88E5;">¿Qué es el arbitraje de divisas?</h2>
                <p>
                    El <b>arbitraje de divisas</b> es una estrategia que consiste en aprovechar
                    diferencias temporales en el precio de cambio entre distintas monedas para
                    obtener una ganancia <b>sin asumir riesgo de mercado</b>. Esto ocurre cuando,
                    al convertir una moneda a través de una secuencia de otras monedas
                    (por ejemplo USD → EUR → GBP → USD), el monto final es mayor al monto inicial,
                    aunque en teoría no debería haber ganancia si el mercado estuviera perfectamente
                    equilibrado.
                </p>

                <h3 style="color:#1E88E5;">¿Por qué ocurre?</h3>
                <p>
                    Los precios de cambio entre monedas los fijan distintos bancos, casas de cambio
                    y plataformas, y no siempre están perfectamente sincronizados entre sí. Estas
                    pequeñas inconsistencias, aunque duran poco tiempo (segundos o minutos), pueden
                    ser aprovechadas antes de que el mercado las corrija.
                </p>

                <h3 style="color:#1E88E5;">¿En qué campos se utiliza?</h3>
                <ul>
                    <li><b>Mercado Forex (divisas fiat):</b> bancos y traders comparan tasas entre
                        múltiples pares de monedas (USD, EUR, GBP, JPY, etc.).</li>
                    <li><b>Criptomonedas:</b> debido a la alta volatilidad y a que existen muchos
                        exchanges distintos (Binance, Coinbase, Kraken...), las oportunidades de
                        arbitraje entre BTC y monedas fiat son más frecuentes.</li>
                    <li><b>Comercio internacional:</b> empresas que operan en varios países
                        aprovechan diferencias cambiarias al convertir ganancias entre monedas.</li>
                    <li><b>Trading algorítmico:</b> sistemas automatizados (bots) que detectan y
                        ejecutan estas oportunidades en milisegundos, antes que un humano pueda
                        reaccionar.</li>
                </ul>

                <h3 style="color:#1E88E5;">Ejemplo en la vida real</h3>
                <p>
                    Supongamos las siguientes tasas de cambio en un instante dado:
                </p>
                <ul>
                    <li>1 USD = 0.9200 EUR</li>
                    <li>1 EUR = 0.8600 GBP</li>
                    <li>1 GBP = 1.2900 USD</li>
                </ul>
                <p>
                    Si convertimos <b>100 USD</b> siguiendo el ciclo USD → EUR → GBP → USD:
                </p>
                <ul>
                    <li>100 USD × 0.9200 = 92.00 EUR</li>
                    <li>92.00 EUR × 0.8600 = 79.12 GBP</li>
                    <li>79.12 GBP × 1.2900 = <b>102.06 USD</b></li>
                </ul>
                <p>
                    Terminamos con <b>102.06 USD</b>, es decir, una ganancia de <b>2.06 USD</b>
                    (2.06%) sin haber asumido ningún riesgo de inversión, solo aprovechando la
                    inconsistencia entre las tres tasas de cambio.
                </p>

                <h3 style="color:#1E88E5;">¿Cómo lo hace esta aplicación?</h3>
                <p>
                    El programa modela cada moneda como un nodo de un grafo, y cada tasa de cambio
                    como una arista con peso <code>-log(tasa)</code>. Luego usa el algoritmo de
                    <b>Bellman-Ford</b> para detectar <b>ciclos de peso negativo</b>, que
                    matemáticamente equivalen a un ciclo de arbitraje rentable.
                </p>
                <p>
                    En los resultados, la <b>ruta</b> no solo muestra el camino de monedas
                    (por ejemplo <code>USD → EUR → GBP → USD</code>), sino también la
                    <b>tasa de cambio usada en cada tramo</b>, para que puedas ver exactamente de
                    dónde sale la ganancia (o la pérdida) en cada conversión.
                </p>

            </body>
            </html>
        """;
    }
}