package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Controlador.Main;
import Excepciones.Validaciones;

public class VRegistrar {
    private JPanel pPrincipal;
    private JTextField tfNombre;
    private JTextField tfCorreo;
    private JTextField tfFechaNacimiento;
    private JTextField tfcontraseña;
    private JButton registrarButton;


    public VRegistrar() {
        registrarButton.setBorderPainted(false);
        registrarButton.setFocusable(false);
        registrarButton.setRolloverEnabled(true);

        registrarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registrarButton.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registrarButton.setBackground(UIManager.getColor("control"));
            }
        });

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        if ( validarRegistrarUsuario() ) {
            try {
                Main.registrarUsuario(tfNombre.getText(), tfcontraseña.getText(), tfCorreo.getText(), tfFechaNacimiento.getText());
            } catch (Exception e) {
                Validaciones.mostrarError(e.getMessage());
            }
        }

    }

    private boolean validarRegistrarUsuario() {
        return (Validaciones.validarTexto(tfNombre) &&  Validaciones.validarTexto(tfcontraseña) &&
                Validaciones.validarEmail(tfCorreo) &&  Validaciones.validarFecha(tfFechaNacimiento));
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("VRegistrar");
        frame.setContentPane(new VRegistrar().pPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getpPrincipal() {
        return pPrincipal;
    }

    public void setpPrincipal(JPanel pPrincipal) {
        this.pPrincipal = pPrincipal;
    }
}


