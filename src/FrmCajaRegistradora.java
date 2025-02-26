import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCajaRegistradora extends JFrame {
    private JComboBox<String> cboDenominaciones;
    private JTextField txtCantidad, txtDevuelta, txtMontoRecibido;
    private JButton btnAgregar, btnCalcular;
    private JTable tblResultado;
    private DefaultTableModel modeloTabla;
    private int[] denominaciones = {50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private String[] tipoDenominacion = {"Billete", "Billete", "Billete", "Billete", "Billete", "Billete/Moneda", "Moneda", "Moneda", "Moneda", "Moneda"};
    private int[] existencias;

    public FrmCajaRegistradora() {
        setTitle("Caja Registradora");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        existencias = new int[denominaciones.length];

        cboDenominaciones = new JComboBox<>();
        for (int i = 0; i < denominaciones.length; i++) {
            cboDenominaciones.addItem(tipoDenominacion[i] + " de $ " + denominaciones[i]);
        }
        cboDenominaciones.setBounds(20, 20, 150, 25);
        add(cboDenominaciones);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(180, 20, 80, 25);
        add(txtCantidad);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(270, 20, 100, 25);
        add(btnAgregar);

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = cboDenominaciones.getSelectedIndex();
                    int cantidad = Integer.parseInt(txtCantidad.getText());
                    existencias[index] = cantidad;
                    txtCantidad.setText("");
                } catch (Exception ex) {
                    txtCantidad.setText("");
                    JOptionPane.showMessageDialog(null, "Debe especificar un valor numérico");
                }
            }
        });

        JLabel lblMontoPagar = new JLabel("Monto a pagar:");
        lblMontoPagar.setBounds(20, 60, 100, 25);
        add(lblMontoPagar);

        txtDevuelta = new JTextField();
        txtDevuelta.setBounds(130, 60, 100, 25);
        add(txtDevuelta);

        JLabel lblMontoRecibido = new JLabel("Monto recibido:");
        lblMontoRecibido.setBounds(20, 90, 100, 25);
        add(lblMontoRecibido);

        txtMontoRecibido = new JTextField();
        txtMontoRecibido.setBounds(130, 90, 100, 25);
        add(txtMontoRecibido);

        btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(130, 130, 100, 25);
        add(btnCalcular);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Denominación");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Cantidad");
        tblResultado = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tblResultado);
        scrollPane.setBounds(20, 170, 350, 100);
        add(scrollPane);

        btnCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    calcularDevuelta();
                } catch (Exception ex) {
                    txtCantidad.setText("");
                    JOptionPane.showMessageDialog(null, "Debe especificar un valor numérico");
                }
            }
        });
    }

    private void calcularDevuelta() {
        int monto = Integer.parseInt(txtDevuelta.getText());
        int montoRecibido = Integer.parseInt(txtMontoRecibido.getText());
        int devuelta = montoRecibido - monto;

        if (devuelta < 0) {
            JOptionPane.showMessageDialog(null, "El monto recibido es insuficiente.");
            return;
        }

        modeloTabla.setRowCount(0);

        for (int i = 0; i < denominaciones.length; i++) {
            int cantidad = Math.min(devuelta / denominaciones[i], existencias[i]);
            if (cantidad > 0) {
                modeloTabla.addRow(new Object[]{"$ " + denominaciones[i], tipoDenominacion[i], cantidad});
                devuelta -= cantidad * denominaciones[i];
                existencias[i] -= cantidad;
            }
        }

        if (devuelta > 0) {
            JOptionPane.showMessageDialog(null, "No hay suficiente efectivo para dar el cambio completo.");
        }
    }
}