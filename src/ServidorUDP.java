import java.io.*;
import java.net.*;
import java.util.Enumeration;

class ServidorUDP {
    private static int portaServidor = 9871;
    private static byte[] receiveData = new byte[1024];
    private static byte[] sendData = new byte[1024];

    public void printDadosServidor(DatagramSocket socket){
        InetAddress	inetAddress = socket.getInetAddress();
        String ipServidor = inetAddress.getHostAddress();
        String hostName = inetAddress.getHostName();
        System.out.println("IP Servidor: " + ipServidor);
        System.out.println("Host Name: " + hostName);
        System.out.println("Porta Servidor: " + socket.getLocalPort());

        try {
            System.out.println("Full list of Network Interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException e) {
            System.out.println(" (error retrieving network interface list)");
        }
    }

   public static void main(String args[]) throws Exception {
        DatagramSocket socket = new DatagramSocket(portaServidor);
        boolean end = false;

        while(!end) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            System.out.println("Aguardando datagrama do cliente....");
            socket.receive(receivePacket);

            System.out.println("RECEIVED: " + new String(receivePacket.getData()));
            InetAddress ipCliente = receivePacket.getAddress();
            int portaCliente = receivePacket.getPort();
            sendData = (new String(receivePacket.getData())).toUpperCase().getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipCliente, portaCliente);
            socket.send(sendPacket);
            System.out.println("Enviado...");
        }
   }
}