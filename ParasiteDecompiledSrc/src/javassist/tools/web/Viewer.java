/*   1:    */ package javassist.tools.web;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.lang.reflect.InvocationTargetException;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.net.URLConnection;
/*  10:    */ 
/*  11:    */ public class Viewer
/*  12:    */   extends ClassLoader
/*  13:    */ {
/*  14:    */   private String server;
/*  15:    */   private int port;
/*  16:    */   
/*  17:    */   public static void main(String[] args)
/*  18:    */     throws Throwable
/*  19:    */   {
/*  20: 58 */     if (args.length >= 3)
/*  21:    */     {
/*  22: 59 */       Viewer cl = new Viewer(args[0], Integer.parseInt(args[1]));
/*  23: 60 */       String[] args2 = new String[args.length - 3];
/*  24: 61 */       System.arraycopy(args, 3, args2, 0, args.length - 3);
/*  25: 62 */       cl.run(args[2], args2);
/*  26:    */     }
/*  27:    */     else
/*  28:    */     {
/*  29: 65 */       System.err.println("Usage: java javassist.tools.web.Viewer <host> <port> class [args ...]");
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Viewer(String host, int p)
/*  34:    */   {
/*  35: 76 */     this.server = host;
/*  36: 77 */     this.port = p;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getServer()
/*  40:    */   {
/*  41: 83 */     return this.server;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getPort()
/*  45:    */   {
/*  46: 88 */     return this.port;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void run(String classname, String[] args)
/*  50:    */     throws Throwable
/*  51:    */   {
/*  52: 99 */     Class c = loadClass(classname);
/*  53:    */     try
/*  54:    */     {
/*  55:101 */       c.getDeclaredMethod("main", new Class[] { new String[0].getClass() }).invoke(null, new Object[] { args });
/*  56:    */     }
/*  57:    */     catch (InvocationTargetException e)
/*  58:    */     {
/*  59:105 */       throw e.getTargetException();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected synchronized Class loadClass(String name, boolean resolve)
/*  64:    */     throws ClassNotFoundException
/*  65:    */   {
/*  66:115 */     Class c = findLoadedClass(name);
/*  67:116 */     if (c == null) {
/*  68:117 */       c = findClass(name);
/*  69:    */     }
/*  70:119 */     if (c == null) {
/*  71:120 */       throw new ClassNotFoundException(name);
/*  72:    */     }
/*  73:122 */     if (resolve) {
/*  74:123 */       resolveClass(c);
/*  75:    */     }
/*  76:125 */     return c;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Class findClass(String name)
/*  80:    */     throws ClassNotFoundException
/*  81:    */   {
/*  82:139 */     Class c = null;
/*  83:140 */     if ((name.startsWith("java.")) || (name.startsWith("javax.")) || (name.equals("javassist.tools.web.Viewer"))) {
/*  84:142 */       c = findSystemClass(name);
/*  85:    */     }
/*  86:144 */     if (c == null) {
/*  87:    */       try
/*  88:    */       {
/*  89:146 */         byte[] b = fetchClass(name);
/*  90:147 */         if (b != null) {
/*  91:148 */           c = defineClass(name, b, 0, b.length);
/*  92:    */         }
/*  93:    */       }
/*  94:    */       catch (Exception e) {}
/*  95:    */     }
/*  96:153 */     return c;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected byte[] fetchClass(String classname)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102:163 */     URL url = new URL("http", this.server, this.port, "/" + classname.replace('.', '/') + ".class");
/* 103:    */     
/* 104:165 */     URLConnection con = url.openConnection();
/* 105:166 */     con.connect();
/* 106:167 */     int size = con.getContentLength();
/* 107:168 */     InputStream s = con.getInputStream();
/* 108:    */     byte[] b;
/* 109:    */     byte[] b;
/* 110:169 */     if (size <= 0)
/* 111:    */     {
/* 112:170 */       b = readStream(s);
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:172 */       b = new byte[size];
/* 117:173 */       int len = 0;
/* 118:    */       do
/* 119:    */       {
/* 120:175 */         int n = s.read(b, len, size - len);
/* 121:176 */         if (n < 0)
/* 122:    */         {
/* 123:177 */           s.close();
/* 124:178 */           throw new IOException("the stream was closed: " + classname);
/* 125:    */         }
/* 126:181 */         len += n;
/* 127:182 */       } while (len < size);
/* 128:    */     }
/* 129:185 */     s.close();
/* 130:186 */     return b;
/* 131:    */   }
/* 132:    */   
/* 133:    */   private byte[] readStream(InputStream fin)
/* 134:    */     throws IOException
/* 135:    */   {
/* 136:190 */     byte[] buf = new byte[4096];
/* 137:191 */     int size = 0;
/* 138:192 */     int len = 0;
/* 139:    */     do
/* 140:    */     {
/* 141:194 */       size += len;
/* 142:195 */       if (buf.length - size <= 0)
/* 143:    */       {
/* 144:196 */         byte[] newbuf = new byte[buf.length * 2];
/* 145:197 */         System.arraycopy(buf, 0, newbuf, 0, size);
/* 146:198 */         buf = newbuf;
/* 147:    */       }
/* 148:201 */       len = fin.read(buf, size, buf.length - size);
/* 149:202 */     } while (len >= 0);
/* 150:204 */     byte[] result = new byte[size];
/* 151:205 */     System.arraycopy(buf, 0, result, 0, size);
/* 152:206 */     return result;
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.web.Viewer
 * JD-Core Version:    0.7.0.1
 */