import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    ChatServer server;
    Scanner in;
    PrintStream out;
    String nickname;

    public Client(Socket socket, ChatServer server) throws IOException {
        this.socket = socket;
        this.server = server;
        // получаем потоки ввода и вывода
        // создаем удобные средства ввода и вывода
        this.in = new Scanner(this.socket.getInputStream());
        this.out = new PrintStream(this.socket.getOutputStream());
        new Thread(this).start();
    }

    void receiveMessage(String msg) {
        this.out.println(msg);
    }

    public void run() {
        try {
            String input = "";
            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            while (input.trim().equals("")) {
                out.println("Set your nickname");
                input = in.nextLine();
            }
            this.nickname = input;
            out.println("Now you can chatting. Enter 'exit' for exit");
            input = in.nextLine();
            while (!input.equals("exit")) {
                server.sendAll(this.nickname + ": " + input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}