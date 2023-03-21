import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<Client> clientList;
    ServerSocket serverSocket;

    public ChatServer() throws IOException {
        this.clientList = new ArrayList<>();
        // создаем серверный сокет на порту 1234
        this.serverSocket = new ServerSocket(1234);
    }

    void sendAll(String msg) {
        for (Client client: clientList) {
            client.receiveMessage(msg);
        }
    }

    public void run() {
        while(true) {
            System.out.println("Waiting...");
            try {
                // ждем клиента
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                Client client = new Client(socket, this);
                clientList.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
       ChatServer server = new ChatServer();
       server.run();
    }
}