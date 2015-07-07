package epn.edu.ec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	/**
     * Se establece un lazo infinito en el que el 
     * servidor escuchará por el el puerto: 9002
     */
    public static void main(String[] args) throws Exception {
        System.out.println("El servidor está escuchando por el puerto 9002.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9002);
        try {
            while (true) {
                new atenderCliente(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }
    
    /**
     * Asigna una thread a cada cliente.
     */
    private static class atenderCliente extends Thread {
        private Socket socket;
        private int numeroCliente;

        public atenderCliente(Socket socket, int clientNumber) {
            this.socket = socket;
            this.numeroCliente = clientNumber;
            log("Conexión establecida con el cliente #" + clientNumber + ", " + socket);
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Mensaje de bienvenido al cliente.
                out.println("Hola cliente #" + numeroCliente);

                // Devuelve el mensaje del cliente
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    System.out.println("Mensaje del cliente #" + numeroCliente + ":" +input.toUpperCase());
                }
            } catch (IOException e) {
                log("Error handling client# " + numeroCliente + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("No se pudo cerrar el socket");
                }
                log("Conexión con el cliente # " + numeroCliente + " cerrada");
            }
        }

        /**
         * Envia un mensaje log al servidor
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}
