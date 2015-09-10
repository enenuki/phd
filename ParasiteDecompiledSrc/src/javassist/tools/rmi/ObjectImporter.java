/*   1:    */ package javassist.tools.rmi;
/*   2:    */ 
/*   3:    */ import java.applet.Applet;
/*   4:    */ import java.io.BufferedInputStream;
/*   5:    */ import java.io.BufferedOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.ObjectInputStream;
/*   9:    */ import java.io.ObjectOutputStream;
/*  10:    */ import java.io.OutputStream;
/*  11:    */ import java.io.Serializable;
/*  12:    */ import java.lang.reflect.Constructor;
/*  13:    */ import java.net.Socket;
/*  14:    */ import java.net.URL;
/*  15:    */ 
/*  16:    */ public class ObjectImporter
/*  17:    */   implements Serializable
/*  18:    */ {
/*  19: 75 */   private final byte[] endofline = { 13, 10 };
/*  20:    */   private String servername;
/*  21:    */   private String orgServername;
/*  22:    */   private int port;
/*  23:    */   private int orgPort;
/*  24: 79 */   protected byte[] lookupCommand = "POST /lookup HTTP/1.0".getBytes();
/*  25: 80 */   protected byte[] rmiCommand = "POST /rmi HTTP/1.0".getBytes();
/*  26:    */   
/*  27:    */   public ObjectImporter(Applet applet)
/*  28:    */   {
/*  29: 91 */     URL codebase = applet.getCodeBase();
/*  30: 92 */     this.orgServername = (this.servername = codebase.getHost());
/*  31: 93 */     this.orgPort = (this.port = codebase.getPort());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ObjectImporter(String servername, int port)
/*  35:    */   {
/*  36:110 */     this.orgServername = (this.servername = servername);
/*  37:111 */     this.orgPort = (this.port = port);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object getObject(String name)
/*  41:    */   {
/*  42:    */     try
/*  43:    */     {
/*  44:123 */       return lookupObject(name);
/*  45:    */     }
/*  46:    */     catch (ObjectNotFoundException e) {}
/*  47:126 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setHttpProxy(String host, int port)
/*  51:    */   {
/*  52:135 */     String proxyHeader = "POST http://" + this.orgServername + ":" + this.orgPort;
/*  53:136 */     String cmd = proxyHeader + "/lookup HTTP/1.0";
/*  54:137 */     this.lookupCommand = cmd.getBytes();
/*  55:138 */     cmd = proxyHeader + "/rmi HTTP/1.0";
/*  56:139 */     this.rmiCommand = cmd.getBytes();
/*  57:140 */     this.servername = host;
/*  58:141 */     this.port = port;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object lookupObject(String name)
/*  62:    */     throws ObjectNotFoundException
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:155 */       Socket sock = new Socket(this.servername, this.port);
/*  67:156 */       OutputStream out = sock.getOutputStream();
/*  68:157 */       out.write(this.lookupCommand);
/*  69:158 */       out.write(this.endofline);
/*  70:159 */       out.write(this.endofline);
/*  71:    */       
/*  72:161 */       ObjectOutputStream dout = new ObjectOutputStream(out);
/*  73:162 */       dout.writeUTF(name);
/*  74:163 */       dout.flush();
/*  75:    */       
/*  76:165 */       InputStream in = new BufferedInputStream(sock.getInputStream());
/*  77:166 */       skipHeader(in);
/*  78:167 */       ObjectInputStream din = new ObjectInputStream(in);
/*  79:168 */       int n = din.readInt();
/*  80:169 */       String classname = din.readUTF();
/*  81:170 */       din.close();
/*  82:171 */       dout.close();
/*  83:172 */       sock.close();
/*  84:174 */       if (n >= 0) {
/*  85:175 */         return createProxy(n, classname);
/*  86:    */       }
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:178 */       e.printStackTrace();
/*  91:179 */       throw new ObjectNotFoundException(name, e);
/*  92:    */     }
/*  93:182 */     throw new ObjectNotFoundException(name);
/*  94:    */   }
/*  95:    */   
/*  96:185 */   private static final Class[] proxyConstructorParamTypes = { ObjectImporter.class, Integer.TYPE };
/*  97:    */   
/*  98:    */   private Object createProxy(int oid, String classname)
/*  99:    */     throws Exception
/* 100:    */   {
/* 101:189 */     Class c = Class.forName(classname);
/* 102:190 */     Constructor cons = c.getConstructor(proxyConstructorParamTypes);
/* 103:191 */     return cons.newInstance(new Object[] { this, new Integer(oid) });
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Object call(int objectid, int methodid, Object[] args)
/* 107:    */     throws RemoteException
/* 108:    */   {
/* 109:    */     boolean result;
/* 110:    */     Object rvalue;
/* 111:    */     String errmsg;
/* 112:    */     try
/* 113:    */     {
/* 114:224 */       Socket sock = new Socket(this.servername, this.port);
/* 115:225 */       OutputStream out = new BufferedOutputStream(sock.getOutputStream());
/* 116:    */       
/* 117:227 */       out.write(this.rmiCommand);
/* 118:228 */       out.write(this.endofline);
/* 119:229 */       out.write(this.endofline);
/* 120:    */       
/* 121:231 */       ObjectOutputStream dout = new ObjectOutputStream(out);
/* 122:232 */       dout.writeInt(objectid);
/* 123:233 */       dout.writeInt(methodid);
/* 124:234 */       writeParameters(dout, args);
/* 125:235 */       dout.flush();
/* 126:    */       
/* 127:237 */       InputStream ins = new BufferedInputStream(sock.getInputStream());
/* 128:238 */       skipHeader(ins);
/* 129:239 */       ObjectInputStream din = new ObjectInputStream(ins);
/* 130:240 */       result = din.readBoolean();
/* 131:241 */       rvalue = null;
/* 132:242 */       errmsg = null;
/* 133:243 */       if (result) {
/* 134:244 */         rvalue = din.readObject();
/* 135:    */       } else {
/* 136:246 */         errmsg = din.readUTF();
/* 137:    */       }
/* 138:248 */       din.close();
/* 139:249 */       dout.close();
/* 140:250 */       sock.close();
/* 141:252 */       if ((rvalue instanceof RemoteRef))
/* 142:    */       {
/* 143:253 */         RemoteRef ref = (RemoteRef)rvalue;
/* 144:254 */         rvalue = createProxy(ref.oid, ref.classname);
/* 145:    */       }
/* 146:    */     }
/* 147:    */     catch (ClassNotFoundException e)
/* 148:    */     {
/* 149:258 */       throw new RemoteException(e);
/* 150:    */     }
/* 151:    */     catch (IOException e)
/* 152:    */     {
/* 153:261 */       throw new RemoteException(e);
/* 154:    */     }
/* 155:    */     catch (Exception e)
/* 156:    */     {
/* 157:264 */       throw new RemoteException(e);
/* 158:    */     }
/* 159:267 */     if (result) {
/* 160:268 */       return rvalue;
/* 161:    */     }
/* 162:270 */     throw new RemoteException(errmsg);
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void skipHeader(InputStream in)
/* 166:    */     throws IOException
/* 167:    */   {
/* 168:    */     int len;
/* 169:    */     do
/* 170:    */     {
/* 171:277 */       len = 0;
/* 172:    */       int c;
/* 173:278 */       while (((c = in.read()) >= 0) && (c != 13)) {
/* 174:279 */         len++;
/* 175:    */       }
/* 176:281 */       in.read();
/* 177:282 */     } while (len > 0);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void writeParameters(ObjectOutputStream dout, Object[] params)
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:288 */     int n = params.length;
/* 184:289 */     dout.writeInt(n);
/* 185:290 */     for (int i = 0; i < n; i++) {
/* 186:291 */       if ((params[i] instanceof Proxy))
/* 187:    */       {
/* 188:292 */         Proxy p = (Proxy)params[i];
/* 189:293 */         dout.writeObject(new RemoteRef(p._getObjectId()));
/* 190:    */       }
/* 191:    */       else
/* 192:    */       {
/* 193:296 */         dout.writeObject(params[i]);
/* 194:    */       }
/* 195:    */     }
/* 196:    */   }
/* 197:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.ObjectImporter
 * JD-Core Version:    0.7.0.1
 */