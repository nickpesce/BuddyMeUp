package com.github.nickpesce.buddymeup;


import android.os.AsyncTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import packets.LocationPacket;
import packets.Packet;

public class Networking {

    String hostName = "10.128.128.88";
    int port = 5634;
    InetAddress host;

    private PacketHandler packetHandler;
    private ConcurrentLinkedQueue<Packet> sendQueue;

    public Networking(final PacketHandler handler) {
        sendQueue = new ConcurrentLinkedQueue<>();
        packetHandler = handler;
        try {
            host = InetAddress.getByName(Networking.this.hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        listen();
        startSender();
    }

    public void startSender()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(port);
                    socket.connect(host, port);
                    while(true) {
                        Packet o;
                        synchronized(sendQueue) {
                            while (sendQueue.isEmpty())
                                sendQueue.wait();
                            o = sendQueue.poll();
                        }
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        ObjectOutputStream output = new ObjectOutputStream(b);



                        o.setIp(socket.getLocalAddress().getHostName());
                        o.setPort(port);
                        output.writeObject(o);

                        byte buf[] = b.toByteArray();

                        DatagramPacket packet = new DatagramPacket(buf, buf.length, host, port);

                        socket.send(packet);
                        System.out.println("SENT: " + packet);
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void listen()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(6969);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                while(true) {
                    byte buf[] = new byte[4096];

                    DatagramPacket rec = new DatagramPacket(buf, buf.length);
                    try {
                        socket.receive(rec);
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));
                        Object recPacket = ois.readObject();
                        System.out.println("Received Packet " + recPacket);
                        packetHandler.handlePacket((Packet) recPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
               // socket.close();
            }
        }).start();
    }

    public void send(Packet o) {
        synchronized (sendQueue) {
            sendQueue.add(o);
            sendQueue.notifyAll();
        }
    }

//    class ReceiveTask extends AsyncTask<Void, Void, Packet>
//    {
//
//        @Override
//        protected Packet doInBackground(Void... params) {
//            byte buf[] = new byte[4096];
//            DatagramSocket socket = null;
//            try {
//                socket = new DatagramSocket(6969);
//            } catch (SocketException e) {
//                e.printStackTrace();
//            }
//            socket.connect(host, port);
//            DatagramPacket rec = new DatagramPacket(buf, buf.length);
//            try {
//                socket.receive(rec);
//                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));
//                Object recPacket = ois.readObject();
//                return (Packet)recPacket;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }catch (ClassNotFoundException e) {
//                e.printStackTrace();
//                return null;
//            }
//            finally {
//                socket.close();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Packet result) {
//            super.onPostExecute(result);
//            if(result != null)
//                handlePacket(result);
//            new ReceiveTask().execute();
//        }
//    }
//
//    class SendTask extends AsyncTask<Packet, Void, Void> {
//        @Override
//        protected Void doInBackground(Packet... o) {
//
//            try {
//                ByteArrayOutputStream b = new ByteArrayOutputStream();
//                ObjectOutputStream output = new ObjectOutputStream(b);
//
//                DatagramSocket socket = new DatagramSocket(6969);
//                socket.connect(host, port);
//
//                //o[0].setIp(socket.getLocalAddress().getHostName());
//                //o[0].setPort(port);
//                output.writeObject(o[0]);
//
//                byte buf[] = b.toByteArray();
//
//                DatagramPacket packet = new DatagramPacket(buf, buf.length, host, port);
//
//                socket.send(packet);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//                return null;
//        }
//    }

}
