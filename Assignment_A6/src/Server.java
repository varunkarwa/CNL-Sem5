import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Server
{
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out=null;
    private Scanner sc=null;
    // constructor with port
    public Server(int port)
    {
// starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
// takes input from the client socket
            in = new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
            sc=new Scanner(System.in);
            int choice=1;
            do {
                try {
                    choice = in.read();
                    switch (choice) {
                        case 1:
                            String line = "";
// reads message from client until "Over" is sent
                            while (!line.equals("Over")) {
                                line = in.readUTF();
                                System.out.println(line);
                                out.writeUTF(sc.nextLine());
                            }
                            break;
                        case 2:
                            String ext=in.readUTF();
                            FileOutputStream fos = new FileOutputStream("D:/testfile."+ext);
                            byte[] buffer = new byte[4096];
                            int filesize = in.read(); // Send file size in separate msg
                            int read = 0;
                            int totalRead = 0;
                            int remaining = filesize;
                            while((read = in.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                                totalRead += read;
                                remaining -= read;
                                System.out.println("read " + totalRead + " bytes.");
                                fos.write(buffer, 0, read);
                            }
                            fos.close();
                            break;
                        case 3:
                            double theta=in.readDouble();
                            int ch=in.read();
                            switch(ch){
                                case 1:
                                    double radTheta= Math.toRadians(theta);
                                    out.writeUTF(String.valueOf(Math.sin(radTheta)));
                                    break;
                                case 2:
                                    radTheta= Math.toRadians(theta);
                                    out.writeUTF(String.valueOf(Math.cos(radTheta)));
                                    break;
                                case 3:
                                    radTheta= Math.toRadians(theta);
                                    out.writeUTF(String.valueOf(Math.cos(radTheta)));
                            }
                    }
                } catch (IOException i) {
                    System.out.println(i);
                }
            }while(choice!=0);
            System.out.println("Closing connection");
// close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}

