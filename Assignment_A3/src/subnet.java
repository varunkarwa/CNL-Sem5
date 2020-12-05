import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;
public class subnet {

    public static void main(String[] args) {
        String ip;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the IP Address: ");
        ip = scanner.nextLine();
        String split_ip[] = ip.split("\\."); //Split the string after every .
        String split_bip[] = new String[4]; //split binary ip
        String bip = "";
        System.out.println("IP Address in binary is");

        for(int i=0;i<4;i++)
        {
            split_bip[i] = appendZeros(Integer.toBinaryString(Integer.parseInt(split_ip[i])));
            System.out.println(split_bip[i]);
            bip += split_bip[i];
        }

        int numberOfSubnets;
        System.out.println("Enter number of subnets required: ");
        numberOfSubnets = scanner.nextInt();
        scanner.nextLine();

        //Calculating class of IP
        int subnet;
        int cc = Integer.parseInt(ip.substring(0, 3));
        if(cc >= 0 && cc <= 127)
        {
            System.out.println("The IP Address belongs to class A");
            subnet = 8;
        }
        else if(cc >= 128 && cc <= 191)
        {
            System.out.println("The IP Address belongs to class B");
            subnet = 16;
        }
        else if(cc >= 192 && cc <= 223)
        {
            System.out.println("The IP Address belongs to class C");
            subnet = 24;
        }
        else
        {
            System.out.println("Invalid IP Address");
            return;
        }

        //Calculating nearest power of 2 to numberOfSubnets and storing it in variable x
        int bits = (int)Math.ceil(Math.log(numberOfSubnets)/Math.log(2));
        System.out.println("Number of bits required for address = "+bits);
        int x = (int)Math.pow(2, bits);
        subnet += (int)Math.ceil(Math.log(x)/Math.log(2));

        System.out.println("Number of subnets: " + subnet);

        String subnetmask = "";
        int i;
        for(i=1; i<=subnet; i++)
        {
            subnetmask += '1';
        }
        for(i=i; i<=32; i++)
        {
            subnetmask += '0';
        }
        String ans[] = {"", "", "", ""};
        int ansDecimal[] = {0, 0, 0, 0};
        for(i=0; i<32; i+=8)
        {
            ans[i/8] = subnetmask.substring(i, i+8);
            ansDecimal[i/8] = Integer.parseInt(subnetmask.substring(i, i+8), 2);
        }
        System.out.println("Subnet mask in Binary:" );
        for(i=0; i<4; i++)
        {
            if(i<3)
            {
                System.out.print(ans[i] + ".");
            }
            else
            {
                System.out.print(ans[i]);
            }
        }
        System.out.println();
        System.out.println("Subnet mask in decimal" );
        for(i=0; i<4; i++)
        {
            if(i<3)
            {
                System.out.print(ansDecimal[i] + ".");
            }
            else
            {
                System.out.print(ansDecimal[i]);
            }
        }

        System.out.println();

        int fbip[] = new int[32];
        for(i=0; i<32;i++)
            fbip[i] = (int)bip.charAt(i)-48;
        for(i=31;i>31-bits;i--)
            fbip[i] &= 0;
        String fip[] ={"","","",""};
        for(i=0;i<32;i++)
            fip[i/8] = new String(fip[i/8]+fbip[i]);
        System.out.print("Subnet address is = ");
        for(i=0;i<4;i++)
        {
            System.out.print(Integer.parseInt(fip[i],2));
            if(i!=3)
                System.out.print(".");
        }

        System.out.println();

        int lbip[] = new int[32];
        for( i=0; i<32;i++)
            lbip[i] = (int)bip.charAt(i)-48;
        for( i=31;i>31-bits;i--)
            lbip[i] |= 1;
        String lip[] = {"","","",""};
        for( i=0;i<32;i++)
            lip[i/8] = new String(lip[i/8]+lbip[i]);
        System.out.print("Broadcast address is = ");
        for( i=0;i<4;i++)
        {
            System.out.print(Integer.parseInt(lip[i],2));
            if(i!=3)
                System.out.print(".");
        }
        System.out.println();


    }
    static String appendZeros(String s)
    {
        String temp = new String("00000000");
        return temp.substring(s.length())+ s;
    }


}
