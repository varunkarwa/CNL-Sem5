import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client
{
    // initialize socket and input output streams
    private Socket socket = null;
    private Scanner sc = null;
    private DataOutputStream out = null;
    private DataInputStream in=null;
    // constructor to put ip address and port
    public Client(String address, int port)
    {
// establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
// takes input from terminal
            sc = new Scanner(System.in);
            in=new DataInputStream(socket.getInputStream());
// sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        int choice=1;
        do {
            try {
                System.out.println("Enter \n1.Chat \n2.File Transfer \n3.Trigonometric Calculator \n0.Exit:");
                choice = sc.nextInt();
                out.writeByte(choice);
                switch (choice) {
// string to read message from input
                    case 1:
                        String line = "";
// keep reading until "Over" is input
                        while (!line.equals("Over")) {
                            line = sc.nextLine();
                            out.writeUTF(line);
                            System.out.println(in.readUTF());
                        }
                        break;
                    case 2:
                        System.out.println("Please enter file type:" );
                        sc.nextLine();
                        String ext=sc.nextLine();
                        out.writeUTF(ext);
                        System.out.println("Please enter file path:");
                        String file = sc.nextLine();
                        FileInputStream fis = new FileInputStream(file);
                        File file1=new File(file);
                        out.writeByte(file.length());
                        byte[] buffer = new byte[4096];
                        while (fis.read(buffer) > 0) {
                            out.write(buffer);
                        }
                        fis.close();
                        System.out.println("file transfer complete");
                        break;
                    case 3:
                        System.out.println("Enter angle in degrees:");
                        double theta=sc.nextDouble();
                        out.writeDouble(theta);
                        System.out.println("Enter \n1.Sin \n2.Cos \n3.Tan:");
                        int ch=sc.nextInt();
                        out.writeByte(ch);
                        System.out.println(in.readUTF());
                }
            } catch (IOException i) {
                System.out.println(i);
            }
        }while(choice!=0);
// close the connection
        try
        {
            sc.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}
