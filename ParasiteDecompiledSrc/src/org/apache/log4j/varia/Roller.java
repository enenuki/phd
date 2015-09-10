/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.net.Socket;
/*   8:    */ import org.apache.log4j.BasicConfigurator;
/*   9:    */ import org.apache.log4j.Category;
/*  10:    */ import org.apache.log4j.Logger;
/*  11:    */ 
/*  12:    */ public class Roller
/*  13:    */ {
/*  14: 41 */   static Logger cat = Logger.getLogger(Roller.class);
/*  15:    */   static String host;
/*  16:    */   static int port;
/*  17:    */   
/*  18:    */   public static void main(String[] argv)
/*  19:    */   {
/*  20:    */     
/*  21: 63 */     if (argv.length == 2) {
/*  22: 64 */       init(argv[0], argv[1]);
/*  23:    */     } else {
/*  24: 66 */       usage("Wrong number of arguments.");
/*  25:    */     }
/*  26: 68 */     roll();
/*  27:    */   }
/*  28:    */   
/*  29:    */   static void usage(String msg)
/*  30:    */   {
/*  31: 73 */     System.err.println(msg);
/*  32: 74 */     System.err.println("Usage: java " + Roller.class.getName() + "host_name port_number");
/*  33:    */     
/*  34: 76 */     System.exit(1);
/*  35:    */   }
/*  36:    */   
/*  37:    */   static void init(String hostArg, String portArg)
/*  38:    */   {
/*  39: 81 */     host = hostArg;
/*  40:    */     try
/*  41:    */     {
/*  42: 83 */       port = Integer.parseInt(portArg);
/*  43:    */     }
/*  44:    */     catch (NumberFormatException e)
/*  45:    */     {
/*  46: 86 */       usage("Second argument " + portArg + " is not a valid integer.");
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   static void roll()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 93 */       Socket socket = new Socket(host, port);
/*  55: 94 */       DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
/*  56: 95 */       DataInputStream dis = new DataInputStream(socket.getInputStream());
/*  57: 96 */       dos.writeUTF("RollOver");
/*  58: 97 */       String rc = dis.readUTF();
/*  59: 98 */       if ("OK".equals(rc))
/*  60:    */       {
/*  61: 99 */         cat.info("Roll over signal acknowledged by remote appender.");
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65:101 */         cat.warn("Unexpected return code " + rc + " from remote entity.");
/*  66:102 */         System.exit(2);
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (IOException e)
/*  70:    */     {
/*  71:105 */       cat.error("Could not send roll signal on host " + host + " port " + port + " .", e);
/*  72:    */       
/*  73:107 */       System.exit(2);
/*  74:    */     }
/*  75:109 */     System.exit(0);
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.Roller
 * JD-Core Version:    0.7.0.1
 */