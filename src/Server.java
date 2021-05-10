
import object.UserInfo;

import java.io.DataInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    private static final String TAG = "Server.java";

    private ServerSocket serverSocket;
    private static final int port = 50000;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            System.out.println("Connecting...");
            serverSocket = new ServerSocket(port);

            GetOrderThread thread = new GetOrderThread();
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 어떤 작업을 할지 결정하는 Thread
    class GetOrderThread extends Thread{
        @Override
        public void run() {
            try{
                while(true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Accepting...");

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    String order = dis.readUTF();

                    if(order.equals("insert")){
                        InsertThread insert_thread = new InsertThread(socket);
                        insert_thread.start();
                    } else if(order.equals("select")){
                        GetIndexThread select_thread = new GetIndexThread(socket);
                        select_thread.start();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // UserInfo를 insert하는 Thread
    class InsertThread extends Thread{

        Socket socket;

        InsertThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Insert...");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                UserInfo userInfo = (UserInfo) ois.readObject();
                userInfo.print();

                DBManager.InsertUserInfo(userInfo);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // p_id를 입력받는 Thread
    class GetIndexThread extends Thread{

        Socket socket;

        GetIndexThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Select...");
            try{
                try{
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    int p_id = dis.readInt();

                    SendThread thread = new SendThread(socket, p_id);
                    thread.start();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 입력받은 p_id에 해당하는 UserInfo 반환하는 Thread
    class SendThread extends Thread {

        Socket socket;
        int pid;

        public SendThread(Socket socket, int pid){
            this.socket = socket;
            this.pid = pid;
        }

        @Override
        public void run() {
            System.out.println("SendThread...");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                UserInfo userInfo = DBManager.getUserInfo(pid);
                if(userInfo == null){
                    oos.writeObject(new UserInfo());
                } else{
                    System.out.println("userinfo is not null");
                    oos.writeObject(userInfo);
                }

                System.out.println("Done");
            } catch (Exception e){
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }
}
