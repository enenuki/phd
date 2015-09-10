/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.net.URLConnection;
/*   8:    */ 
/*   9:    */ public class URLClassPath
/*  10:    */   implements ClassPath
/*  11:    */ {
/*  12:    */   protected String hostname;
/*  13:    */   protected int port;
/*  14:    */   protected String directory;
/*  15:    */   protected String packageName;
/*  16:    */   
/*  17:    */   public URLClassPath(String host, int port, String directory, String packageName)
/*  18:    */   {
/*  19: 61 */     this.hostname = host;
/*  20: 62 */     this.port = port;
/*  21: 63 */     this.directory = directory;
/*  22: 64 */     this.packageName = packageName;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String toString()
/*  26:    */   {
/*  27: 68 */     return this.hostname + ":" + this.port + this.directory;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public InputStream openClassfile(String classname)
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 78 */       URLConnection con = openClassfile0(classname);
/*  35: 79 */       if (con != null) {
/*  36: 80 */         return con.getInputStream();
/*  37:    */       }
/*  38:    */     }
/*  39:    */     catch (IOException e) {}
/*  40: 83 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private URLConnection openClassfile0(String classname)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 87 */     if ((this.packageName == null) || (classname.startsWith(this.packageName)))
/*  47:    */     {
/*  48: 88 */       String jarname = this.directory + classname.replace('.', '/') + ".class";
/*  49:    */       
/*  50: 90 */       return fetchClass0(this.hostname, this.port, jarname);
/*  51:    */     }
/*  52: 93 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public URL find(String classname)
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59:103 */       URLConnection con = openClassfile0(classname);
/*  60:104 */       InputStream is = con.getInputStream();
/*  61:105 */       if (is != null)
/*  62:    */       {
/*  63:106 */         is.close();
/*  64:107 */         return con.getURL();
/*  65:    */       }
/*  66:    */     }
/*  67:    */     catch (IOException e) {}
/*  68:111 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void close() {}
/*  72:    */   
/*  73:    */   public static byte[] fetchClass(String host, int port, String directory, String classname)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:134 */     URLConnection con = fetchClass0(host, port, directory + classname.replace('.', '/') + ".class");
/*  77:    */     
/*  78:136 */     int size = con.getContentLength();
/*  79:137 */     InputStream s = con.getInputStream();
/*  80:    */     byte[] b;
/*  81:    */     try
/*  82:    */     {
/*  83:    */       byte[] b;
/*  84:139 */       if (size <= 0)
/*  85:    */       {
/*  86:140 */         b = ClassPoolTail.readStream(s);
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:142 */         b = new byte[size];
/*  91:143 */         int len = 0;
/*  92:    */         do
/*  93:    */         {
/*  94:145 */           int n = s.read(b, len, size - len);
/*  95:146 */           if (n < 0) {
/*  96:147 */             throw new IOException("the stream was closed: " + classname);
/*  97:    */           }
/*  98:150 */           len += n;
/*  99:151 */         } while (len < size);
/* 100:    */       }
/* 101:    */     }
/* 102:    */     finally
/* 103:    */     {
/* 104:155 */       s.close();
/* 105:    */     }
/* 106:158 */     return b;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static URLConnection fetchClass0(String host, int port, String filename)
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:    */     URL url;
/* 113:    */     try
/* 114:    */     {
/* 115:167 */       url = new URL("http", host, port, filename);
/* 116:    */     }
/* 117:    */     catch (MalformedURLException e)
/* 118:    */     {
/* 119:171 */       throw new IOException("invalid URL?");
/* 120:    */     }
/* 121:174 */     URLConnection con = url.openConnection();
/* 122:175 */     con.connect();
/* 123:176 */     return con;
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.URLClassPath
 * JD-Core Version:    0.7.0.1
 */