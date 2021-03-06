import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class servidor {
	/**
     * Se establece un lazo infinito en el que el 
     * servidor escuchara por el el puerto: 9999
     */
    public static void main(String[] args) throws Exception {
        System.out.println("El servidor se encuentra ejecutandose...");
        int numeroCliente = 0;
        ServerSocket listener = new ServerSocket(9999);
        try {
            while (true) {
                new Server(listener.accept(), numeroCliente++).start();
            }
        } finally {
            listener.close();
        }
    }
    
    /**
     * Asigna una thread a cada cliente.
     */
    private static class Server extends Thread {
        private Socket socket;
        private int numeroCliente;

        public Server(Socket socket, int clientNumber) {
            this.socket = socket;
            this.numeroCliente = clientNumber;
            log("Nueva conexion con el cliente # " + clientNumber + " en " + socket);
        }


        public void run() {
            try {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Hola, eres el cliente #" + numeroCliente + ".");
                out.println("Ingresa como mensaje 'salir!!!' para hacerlo\n");

                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals("salir!!!")) {
                        break;
                    }
                    out.println("mensaje enviado");
                    log("Mensaje del cliente #" + numeroCliente + ":"+input);
                }
            } catch (IOException e) {
                log("Error handling client# " + numeroCliente + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("No se puede cerrar el socket - Error desconocido");
                }
                log("Conexion con el cliente # " + numeroCliente + " cerrada");
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
