/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.net.InetAddress;
/*   7:    */ import java.net.Socket;
/*   8:    */ import jcifs.Config;
/*   9:    */ import jcifs.util.LogStream;
/*  10:    */ 
/*  11:    */ public class NbtSocket
/*  12:    */   extends Socket
/*  13:    */ {
/*  14:    */   private static final int SSN_SRVC_PORT = 139;
/*  15:    */   private static final int BUFFER_SIZE = 512;
/*  16:    */   private static final int DEFAULT_SO_TIMEOUT = 5000;
/*  17: 41 */   private static LogStream log = ;
/*  18:    */   private NbtAddress address;
/*  19:    */   private Name calledName;
/*  20:    */   private int soTimeout;
/*  21:    */   
/*  22:    */   public NbtSocket() {}
/*  23:    */   
/*  24:    */   public NbtSocket(NbtAddress address, int port)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 51 */     this(address, port, null, 0);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public NbtSocket(NbtAddress address, int port, InetAddress localAddr, int localPort)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 55 */     this(address, null, port, localAddr, localPort);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public NbtSocket(NbtAddress address, String calledName, int port, InetAddress localAddr, int localPort)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 59 */     super(address.getInetAddress(), port == 0 ? 139 : port, localAddr, localPort);
/*  40:    */     
/*  41: 61 */     this.address = address;
/*  42: 62 */     if (calledName == null) {
/*  43: 63 */       this.calledName = address.hostName;
/*  44:    */     } else {
/*  45: 65 */       this.calledName = new Name(calledName, 32, null);
/*  46:    */     }
/*  47: 67 */     this.soTimeout = Config.getInt("jcifs.netbios.soTimeout", 5000);
/*  48: 68 */     connect();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public NbtAddress getNbtAddress()
/*  52:    */   {
/*  53: 72 */     return this.address;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public InputStream getInputStream()
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 75 */     return new SocketInputStream(super.getInputStream());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public OutputStream getOutputStream()
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 78 */     return new SocketOutputStream(super.getOutputStream());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getPort()
/*  69:    */   {
/*  70: 81 */     return super.getPort();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public InetAddress getLocalAddress()
/*  74:    */   {
/*  75: 84 */     return super.getLocalAddress();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getLocalPort()
/*  79:    */   {
/*  80: 87 */     return super.getLocalPort();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String toString()
/*  84:    */   {
/*  85: 90 */     return "NbtSocket[addr=" + this.address + ",port=" + super.getPort() + ",localport=" + super.getLocalPort() + "]";
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void connect()
/*  89:    */     throws IOException
/*  90:    */   {
/*  91: 95 */     byte[] buffer = new byte[512];
/*  92:    */     try
/*  93:    */     {
/*  94:100 */       InputStream in = super.getInputStream();
/*  95:101 */       OutputStream out = super.getOutputStream();
/*  96:    */       
/*  97:103 */       SessionServicePacket ssp0 = new SessionRequestPacket(this.calledName, NbtAddress.localhost.hostName);
/*  98:104 */       out.write(buffer, 0, ssp0.writeWireFormat(buffer, 0));
/*  99:    */       
/* 100:106 */       setSoTimeout(this.soTimeout);
/* 101:107 */       type = SessionServicePacket.readPacketType(in, buffer, 0);
/* 102:    */     }
/* 103:    */     catch (IOException ioe)
/* 104:    */     {
/* 105:    */       int type;
/* 106:109 */       close();
/* 107:110 */       throw ioe;
/* 108:    */     }
/* 109:    */     InputStream in;
/* 110:    */     int type;
/* 111:113 */     switch (type)
/* 112:    */     {
/* 113:    */     case 130: 
/* 114:115 */       if (LogStream.level > 2) {
/* 115:116 */         log.println("session established ok with " + this.address);
/* 116:    */       }
/* 117:117 */       return;
/* 118:    */     case 131: 
/* 119:119 */       int errorCode = in.read() & 0xFF;
/* 120:120 */       close();
/* 121:121 */       throw new NbtException(2, errorCode);
/* 122:    */     case -1: 
/* 123:123 */       throw new NbtException(2, -1);
/* 124:    */     }
/* 125:125 */     close();
/* 126:126 */     throw new NbtException(2, 0);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void close()
/* 130:    */     throws IOException
/* 131:    */   {
/* 132:130 */     if (LogStream.level > 3) {
/* 133:131 */       log.println("close: " + this);
/* 134:    */     }
/* 135:132 */     super.close();
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NbtSocket
 * JD-Core Version:    0.7.0.1
 */