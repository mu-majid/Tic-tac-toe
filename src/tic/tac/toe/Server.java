/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

/**
 *
 * @author Majid
 */
import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Server extends Thread {

    private ServerSocket listener;
    private Socket connection;
    private int port;
    private boolean hasConnection;
    public NetGame game = new NetGame(this.connection, true);

    public Server(int p) throws Exception {
        this.port = p;
        this.hasConnection = false;
        listener = new ServerSocket(port);

    }

    @Override
    public void run() {
        while (!this.hasConnection) {
            try {
                this.port = listener.getLocalPort();
                System.out.println("Listening on port " + this.port + "...");
                this.connection = listener.accept();
                System.out.println("after accept");
                // using server socket && creating game object
                try {
                    // initialize new game with the correct socket and host statu
                    game.setConnection(connection);
                System.out.println("set connection");
                    game.setMyTurn(true);
                System.out.println("after set turn");
                    game.setIsHost(true);
                System.out.println("after set host");
                    game.isWaiting = false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("hahafasdfasdfasdfsadfasdfsadfasdfsdafhahafasdfasdfasdfsadfasdfsadfasdfsdaf");
            this.hasConnection = true;
        }
    }

    public boolean hasConnection() {
        return hasConnection;
    }

    public Socket connection() {
        return connection;
    }

    public int getPort() {
        return port;
    }

    public String getIp() throws SocketException {
        Pattern patternIP = Pattern.compile("(^127\\.[1-9])|(^127\\.0\\.[1-9])|(^127\\.0\\.0\\.[02-9])|(^10\\.)|(^172\\.1[6-9]\\.)|(^172\\.2[0-9]\\.)|(^172\\.3[0-1]\\.)|(^192\\.168\\.)");
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (patternIP.matcher(i.getHostAddress()).find()) {
                    return i.getHostAddress();
                }
            }
        }
        return null;
    }
}
