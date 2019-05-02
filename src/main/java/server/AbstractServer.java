package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.rmi.ServerException;

public abstract class AbstractServer {

    private int port;
    private ServerSocket server = null;

    //binds a server to a port
    public AbstractServer(int port) {
        this.port = port;
    }

    //blocks and waits for clients
    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for clients ...");
                Socket client = server.accept();
                System.out.println("Client connected ...");
                processRequest(client);
            }

        } catch (IOException e) {
            throw new ServerException("Starting server error ", e);

        } finally {
            try {
                server.close();
            } catch (IOException e) {
                throw new ServerException("Closing server error ", e);
            }
        }
    }

    protected void processRequest(Socket client) {
        Thread tw = createWorker(client);
        tw.start();
    }

    protected abstract Thread createWorker(Socket client);

    //closes the server
    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
