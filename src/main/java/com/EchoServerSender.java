//package com;
//
//
//import com.network.Transport;
//import com.network.message.Message;
//
//import java.net.InetSocketAddress;
//import java.net.SocketAddress;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static com.network.message.Message.DeleteClientMessage;
//import static com.network.message.Message.EchoMessage;
//import static com.network.message.MessageNames.echoMessageCode;
//
//
//public class EchoServerSender implements Runnable {
//
//    private static Map<SocketAddress, Integer> mapEcho = new HashMap<>();
//    private static ExecutorService pool = Executors.newCachedThreadPool();
//    static private DB db;
//    private static boolean quit = false;
//    private static Transport transport = new Transport();
//
//    static void setDb(DB db) {
//        EchoServerSender.db = db;
//    }
//
//    static void shutdown() {
//        quit = true;
//        pool.shutdown();
//    }
//
//    public void run() {
//        long time5 = System.currentTimeMillis();
//        while (!quit) {
//            if (System.currentTimeMillis() - time5 >= 5 * 1000) {//////
//                echo();
//                time5 = System.currentTimeMillis();
//                try {
//                    Thread.sleep(4500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void echo() {
//        try {
//            for (InetSocketAddress clientServerPartAddress : db.getAllActiveClientsServerParts()) {
//                pool.submit(new ECHO(clientServerPartAddress));
//            }
//        } catch (SQLException t) {
//            for (Throwable e : t) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    static class ECHO implements Runnable {
//
//        private InetSocketAddress clientServerPartAddress;
//
//        ECHO(InetSocketAddress clientServerPartAddress) {
//            this.clientServerPartAddress = clientServerPartAddress;
//        }
//
//        public void run() {
//            try {
//                System.out.println(clientServerPartAddress + " ECHO");
//                ServerController.getInstance().logEcho(clientServerPartAddress + " ECHO");
//                Message msg = transport.sendAndReceive_CRYPTED(
//                        new EchoMessage(), clientServerPartAddress, db.getPassword(db.getName(clientServerPartAddress))
//                );
//
//                if (msg.getCode() == echoMessageCode) {
//                    mapEcho.remove(clientServerPartAddress);
//                }
//            } catch (Exception e) {
//                try {
//                    System.out.println(db.getName(clientServerPartAddress) + " не ответил на  ECHO-сообщение");
//                    ServerController.getInstance().logEcho(db.getName(clientServerPartAddress) + " не ответил на  ECHO-сообщение");
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//                mapEcho.putIfAbsent(clientServerPartAddress, 0);
//                mapEcho.compute(clientServerPartAddress, (k, v) -> v + 1);
//                if (mapEcho.get(clientServerPartAddress) > 3) {
//                    String name = "";
//                    try {
//                        name = Server.getDb().getName(clientServerPartAddress);
//                        System.out.println("Ехосервер удалил бяку - " + name);
//                        ServerController.getInstance().log("Ехосервер удалил бяку - " + name);
//                        db.deleteActiveClient(clientServerPartAddress);
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    }
//                    mapEcho.remove(clientServerPartAddress);
//                    List<InetSocketAddress> list = new ArrayList<>();
//                    try {
//                        list = Server.getDb().getAllActiveClientsServerParts();
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                    for (InetSocketAddress addr : list) {
//                        try {
//                            transport.sendMessage_CRYPTED(new DeleteClientMessage(name), addr, db.getPassword(db.getName(addr)));
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
