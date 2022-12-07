/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteprueba;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JOSEMARIO
 */
public class DemoCliente extends Thread {

    static Metodos m;
    static DataInputStream entrada;
    static DataOutputStream salida;
    static Scanner teclado;
    static String mensaje;

    static boolean solicitud;
    static boolean enjuego;
    static String me;
    static String[] coord;

    public DemoCliente(Socket socket) throws IOException {

        entrada = new DataInputStream(socket.getInputStream());
        teclado = new Scanner(System.in);
        salida = new DataOutputStream(socket.getOutputStream());
        
        coord = new String[2];
    }

    DemoCliente() {
    }

    public static void recibir() {
        try {
            m = new Metodos();
            mensaje = entrada.readUTF();
            if (mensaje.equals("El nombre ya esta ocupado")) {
                System.out.println(mensaje);
                System.exit(1);
            } else {
                System.out.println(mensaje);
            }

            if (m.esComando(mensaje)) {
                String respuesta = m.modoComando(mensaje);
                System.out.println(respuesta);

            } else {
                solicitud = false;
            }

        } catch (IOException ex) {
            System.out.println("error en ingresar nombre");
        }

        while (true) {
            try {
                mensaje = entrada.readUTF();
                if (m.esComando(mensaje)) {
                    String respuesta = m.modoComando(mensaje);
                    System.out.println(respuesta);
                }
                else {
                    System.out.println(mensaje);
                    solicitud = false;
                }

            } catch (IOException ex) {
                Logger.getLogger(DemoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void enviar() {
        m = new Metodos();
        System.out.println("ingrese un nombre de usuario");
        String e = teclado.nextLine();
        try {
            salida.writeUTF(e);
        } catch (IOException ex) {
            System.out.println("Error al ingresar usuario");
            System.exit(1);
        }

        while (true) {
            try {

                if (solicitud) {
                    System.out.println("Ingrese SI/NO");
                    me = teclado.nextLine();
                    if (!(me.equals("SI") || !me.equals("NO"))) {
                        System.out.println("Tienes que ingresar SI o NO solamente");
                    } else if (me.equals("SI")) {
                        salida.writeUTF(me);
                        System.out.println("Elija la primer posicion");
                        solicitud = false;
                        enjuego=true;
                    } else {
                        salida.writeUTF(me);
                        solicitud = false;
                    }
                } else if (enjuego && m.esCoordenada(me)) {
                    if (coord[0] == null) {
                        coord[0] = me;
                    } else {
                        coord[1] = teclado.nextLine();
                        System.out.println("elija la ultima posicion");
                        m.elegirPosiciones(1, 1, 1, 2, 2, coord[0], coord[1]);
                        coord[0]=null;
                        me="";
                    }
                } else {
                    me = teclado.nextLine();
                    salida.writeUTF(me);
                }

            } catch (IOException ex) {
                Logger.getLogger(DemoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        DemoCliente a = new DemoCliente(socket);

        try {

            Runnable hiloRecibir = new Runnable() {
                @Override
                public void run() {
                    DemoCliente.recibir();
                }
            };

            Runnable hiloEnviar = new Runnable() {
                @Override
                public void run() {
                    DemoCliente.enviar();
                }
            };

            Thread Recibir = new Thread(hiloRecibir);
            Thread Enviar = new Thread(hiloEnviar);

            Recibir.start();
            Enviar.start();

        } catch (Exception e) {
            System.out.println("Error de conexión");
            System.exit(1);
        }

    }

}
