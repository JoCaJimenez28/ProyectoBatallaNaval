/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteprueba;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author José Carlos Jiménez
 */
public class Metodos {

    DemoCliente dc = new DemoCliente();
    Scanner teclado = new Scanner(System.in);

    String dividirCoordenadaInicial[] =new String[2];
    String dividirCoordenadaFinal[]=new String[2];
    String tableroPropio[][] = new String[10][10];

    public boolean esComando(String mensaje) {
        String dividirComando[] = mensaje.split(":");
        return dividirComando[2].trim().equals(dividirComando[2].trim().toUpperCase());
    }

    public String modoComando(String mensaje) throws IOException {
        String dividirComando[] = mensaje.split(":");
        String respuesta = "";

        switch (dividirComando[2].trim()) {
            case "JUGAR":
                dc.solicitud = true;
                respuesta = "Quieres jugar batalla naval?";
                break;
            case "SI":
                respuesta = "se aceptó la solicitud\n elija la primer posicion";
                dc.enjuego = true;
                break;
            case "NO":
                respuesta = "se rechazó la solicitud";
                break;

        }

        return respuesta;
    }

    public void prueba() {
        System.out.println("Corrio el metodo alv uauauau algo bien");
    }

    public static boolean isNumeric(String s) {
        if (s == null || s.equals("")) {
            return false;
        }

        return s.chars().allMatch(Character::isDigit);
    }

    public boolean esCoordenada(String mensaje) {
        String dividirCoordenada[] = mensaje.split(",");
            if (isNumeric(dividirCoordenada[0]) && isNumeric(dividirCoordenada[1])) {
                return true;
            } else {
                return false;
            }
    }

    public String elegirBarco() {
        System.out.println("Elige la opcion de barcos que jugaran en el tablero");
        System.out.println(
                "1.- 2 BUQUES, 2SUBMARINOS, 2CRUCEROSS, 1 LANCHA\n"
                + "2.- 1 PORTA AVIONES, 1 BUQUE, 1 SUBMARINO, 2CRUCEROS, 2 LANCHAS\n"
                + "3.- 1 BUQUE, 2 SUBMARINOS, 2 CRUCEROSS, 3 LANCHAS");

        String op = dc.me;
        switch (op) {
            case "1":
                //elegirPosiciones(0, 2, 2, 2, 1);
                break;
            case "2":
                //elegirPosiciones(1, 1, 1, 2, 2);
                break;
            case "3":
                //elegirPosiciones(0, 1, 2, 2, 3);
                break;
        }
        return op;

    }

    public void elegirPosiciones(int paviones, int buques, int submarino, int crucero, int lancha, String ci, String cf) {
        System.out.println("Elegir posiciones");
        rellenarTablero();

        for (int i = 0; i < 2; i++) {
            coordenadasBarcos(3, ci, cf);
        }

        /*for (int i = 0; i < buques; i++) {
            System.out.println("entro al 4");
            coordenadasBarcos(4, ci, cf);
        }

        for (int i = 0; i < submarino; i++) {
            System.out.println("entro al 3");
            coordenadasBarcos(3, ci, cf);
        }

        for (int i = 0; i < crucero; i++) {
            System.out.println("entro al 2");
            coordenadasBarcos(2, ci, cf);
        }

        for (int i = 0; i < lancha; i++) {
            System.out.println("entro al 1");
            coordenadasBarcos(1, ci, cf);
        }*/
        mostrarTablero();
        
    }
    
    public void rellenarTablero(){
        for(int i=0; i<tableroPropio.length; i++){
            for(int j=0; j<tableroPropio.length; j++){
                tableroPropio[i][j]= "0";
            }
        }
    }
    public void mostrarTablero(){
        for(int i=0; i<tableroPropio.length; i++){
            for(int j=0; j<tableroPropio.length; j++){
                System.out.print("["+ tableroPropio[j][i] + "] ");
            }
            System.out.println("");
        }
    }

    public String ingresarCoordenada(String coordenada) {
        return coordenada;
    }

    public void coordenadasBarcos(int tamaño, String ci, String cf) {

        if (tamaño == 1) {
            dividirCoordenadaInicial = ci.split(",");
            tableroPropio[Integer.parseInt(dividirCoordenadaInicial[0])][Integer.parseInt(dividirCoordenadaInicial[1])] = "1";
        } else {
            /*System.out.println("ingrese la coordenada inicial");
            coordenadaInicial = teclado.nextLine(); 
            System.out.println(coordenadaInicial);
            System.out.println("ingrese la coordenada final"); 
            coordenadaFinal = teclado.nextLine();
            System.out.println(coordenadaFinal);

            dividirCoordenadaInicial = coordenadaInicial.split(","); 
            dividirCoordenadaFinal = coordenadaFinal.split(",");

            System.out.println(dividirCoordenadaInicial[0]);
            System.out.println(dividirCoordenadaInicial[1]);*/
            dividirCoordenadaInicial = ci.split(","); 
            dividirCoordenadaFinal = cf.split(","); 
            
            int pp =Integer.parseInt(dividirCoordenadaInicial[0])+1;
            int sp =Integer.parseInt(dividirCoordenadaInicial[1])+1;

            if (dividirCoordenadaInicial[0].equals(dividirCoordenadaFinal[0])) {
                if (Integer.parseInt(dividirCoordenadaInicial[1]) < Integer.parseInt(dividirCoordenadaFinal[1])) {
                    for (int i = sp; i <= sp + tamaño; i++) {
                        tableroPropio[Integer.parseInt(dividirCoordenadaInicial[0])][i] = "1";
                    }
                }

                if (Integer.parseInt(dividirCoordenadaInicial[1]) > Integer.parseInt(dividirCoordenadaFinal[1])) {
                    for (int i = sp; i <= sp + tamaño; i--) {
                        tableroPropio[Integer.parseInt(dividirCoordenadaInicial[0])][i] = "1";
                    }
                }
            } else if (dividirCoordenadaInicial[1].equals(dividirCoordenadaFinal[1])) {
                if (Integer.parseInt(dividirCoordenadaInicial[0]) < Integer.parseInt(dividirCoordenadaFinal[0])) {
                    for (int i = pp; i <= pp + tamaño; i++) {
                        tableroPropio[i][Integer.parseInt(dividirCoordenadaInicial[0])] = "1";
                    }
                }

                if (Integer.parseInt(dividirCoordenadaInicial[0]) > Integer.parseInt(dividirCoordenadaFinal[0])) {
                    for (int i = Integer.parseInt(dividirCoordenadaInicial[0]); i <= tamaño; i--) {
                        tableroPropio[i][Integer.parseInt(dividirCoordenadaInicial[0])] = "1";
                    }
                }
            }
        }
    }
}
