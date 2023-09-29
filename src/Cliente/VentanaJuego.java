package Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import Cliente.Conexion.Cliente;

public class VentanaJuego {
    private JTextField textNombre;
    private JButton registrarseButton;
    private JButton iniciarJuegoButton;
    private JPanel jPanel;
    private JButton pintarButton;
    private JPanel jPanelJuego;
    private JFrame jFrame;
    List<List<Integer>> malla;
    int SIZE_CASILLAS = 30;


    public VentanaJuego() {

        initComponents();

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (Cliente.registrarJugador(new Usuario(5000, textNombre.getText()))) {
                    case "Registrado Exitosamente": {
                        JOptionPane.showMessageDialog(null, "Registrado Exitosamente\nEsperando a que inicie el juego.", "Agregado", JOptionPane.INFORMATION_MESSAGE);
                        textNombre.setEnabled(false);
                        registrarseButton.setEnabled(false);
                        break;
                    }
                    case "Registrado Fallido": {
                        JOptionPane.showMessageDialog(null, "Registro Fallido\nIngrese otro nombre de jugador.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    case "Juego Iniciado":{
                        JOptionPane.showMessageDialog(null, "El juego ya ha iniciado, espere a la proxima partida.", "Error", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                }

            }
        });
        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (Cliente.iniciarJuego()){
                    case "Juego Iniciado":
                    {
                        System.out.println("Aca deberia de empezar el juego, pero aun no");
                        break;
                    }
                    case "Juego No Iniciado":{
                        JOptionPane.showMessageDialog(null, "Deben de haber minimo 2 jugadores para iniciar", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    case "Juego Ya Iniciado":{
                        JOptionPane.showMessageDialog(null, "El juego ya ha iniciado, espere a la proxima partida.", "Error", JOptionPane.WARNING_MESSAGE);
                        break;
                    }


                }

            }
        });

        jPanelJuego.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Cliente.seleccionarLinea(new Usuario(5000, textNombre.getText()),e.getX(),e.getY());
            }
        });
    }

    private void initComponents() {
        jFrame = new JFrame();
        jFrame.setContentPane(jPanel);
        jFrame.setTitle("Connect Dots");
        jFrame.pack();
        jFrame.setVisible(true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    List<List<Integer>> malla = Cliente.pintarVentana();
                    if(malla!=null){
                        Graphics g = jPanelJuego.getGraphics();

                        g.setColor(Color.BLACK);
                        int y = 1;
                        boolean horizontal = true;
                        for (List<Integer> i: malla) {
                            int x = 1;
                            if (horizontal){
                                for (int j: i){
                                    if (j==1){
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,(x+1)*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                    }
                                    else{
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,x*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                        g.drawLine((x+1)*SIZE_CASILLAS,y*SIZE_CASILLAS,(x+1)*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                    }
                                    x += 1;
                                    horizontal = false;
                                }
                            }
                            else{
                                for (int j: i){

                                    if (j==1){
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,x*SIZE_CASILLAS,(y+1)*SIZE_CASILLAS);
                                    }
                                    else{
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,x*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                        g.drawLine(x*SIZE_CASILLAS,(y+1)*SIZE_CASILLAS,x*SIZE_CASILLAS,(y+1)*SIZE_CASILLAS);
                                    }
                                    x += 1;
                                }
                                y+=1;
                                horizontal = true;

                            }

                        }
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


    }
}
