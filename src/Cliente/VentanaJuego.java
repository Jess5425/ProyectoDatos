package Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Cliente.Conexion.Cliente;
import Cliente.Malla.Casillas.NodoUsuario;
import Cliente.Malla.Lineas.Filas;
import Cliente.Malla.Lineas.Malla;
import Cliente.Malla.Lineas.NodoLinea;

public class VentanaJuego {
    private JTextField textNombre;
    private JButton registrarseButton;
    private JButton iniciarJuegoButton;
    private JPanel jPanel;
    private JPanel jPanelJuego;
    private JFrame jFrame;
    private boolean juegoIniciado;
    int SIZE_CASILLAS = 30;


    public VentanaJuego() {

        initComponents();

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Switch que se encarga de determinar si se registro o no el usuario
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
                //Switch que determina si se logro iniciar la partida
                switch (Cliente.iniciarJuego()){
                    case "Juego Iniciado":
                    {
                        jPanel.repaint();
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

    /***
     * Inicia los componentes de la ventana
     */
    private void initComponents() {
        jFrame = new JFrame();
        jFrame.setContentPane(jPanel);
        jFrame.setTitle("Connect Dots");
        jFrame.pack();
        jFrame.setVisible(true);
        juegoIniciado= false;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ///Ciclo que carga y pinta en pantalla el tablero
                while (true){
                    Malla malla = Cliente.pintarVentana();
                    if(malla!=null){
                        if (!juegoIniciado){
                            jPanel.repaint();
                        }
                        juegoIniciado = true;
                        Graphics g = jPanelJuego.getGraphics();
                        g.setColor(Color.BLACK);

                        int y = 1;
                        boolean horizontal = true;

                        Filas tempFilas = malla.front;

                        while(tempFilas!=null){
                            NodoLinea tempLineas = tempFilas.data.front;
                            int x = 1;
                            while (tempLineas!=null){
                                //Pinta las lineas o puntos en la pantalla
                                if (tempLineas.linea.seleccionada) {
                                    if (horizontal) {
                                        g.drawLine(x * SIZE_CASILLAS, y * SIZE_CASILLAS, (x + 1) * SIZE_CASILLAS, y * SIZE_CASILLAS);
                                    }
                                    else{
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,x*SIZE_CASILLAS,(y+1)*SIZE_CASILLAS);
                                    }
                                }
                                else{
                                    if (horizontal) {
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,x*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                        g.drawLine((x+1)*SIZE_CASILLAS,y*SIZE_CASILLAS,(x+1)*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                    }
                                    else{
                                        g.drawLine(x*SIZE_CASILLAS,y*SIZE_CASILLAS,x*SIZE_CASILLAS,y*SIZE_CASILLAS);
                                        g.drawLine(x*SIZE_CASILLAS,(y+1)*SIZE_CASILLAS,x*SIZE_CASILLAS,(y+1)*SIZE_CASILLAS);
                                    }
                                }
                                x+=1;
                                tempLineas = tempLineas.next;
                            }
                            horizontal = !horizontal;
                            if (horizontal){
                                y+=1;
                            }
                            tempFilas = tempFilas.next;
                        }

                        y=1;

                        var tempCasillas = malla.casillasUsuarios.front;
                        //pinta las casillas segun el jugador que la haya llenado
                        while (tempCasillas!=null){
                            int x = 1;
                            NodoUsuario tempUsuario =  tempCasillas.data.front;

                            while (tempUsuario!=null){
                                if (tempUsuario.data!=null){
                                    g.drawString(String.valueOf(tempUsuario.data.nombre.charAt(0)),x*SIZE_CASILLAS+10,y*SIZE_CASILLAS+25);
                                }
                                tempUsuario = tempUsuario.next;
                                x+=1;
                            }
                            y+=1;
                            tempCasillas = tempCasillas.next;
                        }

                    }
                    else{
                        if(juegoIniciado){
                            textNombre.setEnabled(true);
                            registrarseButton.setEnabled(true);
                            juegoIniciado = false;
                        }
                    }

                    //verifica si alguien ya gano la partida
                    Usuario ganador = Cliente.ganador();

                    if (ganador!=null){
                        JOptionPane.showMessageDialog(null,"El ganador es "+ganador.nombre,"Fin de la partida",JOptionPane.INFORMATION_MESSAGE);
                        textNombre.setEnabled(true);
                        registrarseButton.setEnabled(true);
                    }

                    try {
                        Thread.sleep(100);
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
