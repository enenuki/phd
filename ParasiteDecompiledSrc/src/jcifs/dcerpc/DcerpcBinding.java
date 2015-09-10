/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.Set;
/*  6:   */ import jcifs.dcerpc.msrpc.lsarpc;
/*  7:   */ import jcifs.dcerpc.msrpc.netdfs;
/*  8:   */ import jcifs.dcerpc.msrpc.samr;
/*  9:   */ import jcifs.dcerpc.msrpc.srvsvc;
/* 10:   */ 
/* 11:   */ public class DcerpcBinding
/* 12:   */ {
/* 13:32 */   private static HashMap INTERFACES = new HashMap();
/* 14:   */   String proto;
/* 15:   */   String server;
/* 16:   */   
/* 17:   */   static
/* 18:   */   {
/* 19:33 */     INTERFACES.put("srvsvc", srvsvc.getSyntax());
/* 20:34 */     INTERFACES.put("lsarpc", lsarpc.getSyntax());
/* 21:35 */     INTERFACES.put("samr", samr.getSyntax());
/* 22:36 */     INTERFACES.put("netdfs", netdfs.getSyntax());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static void addInterface(String name, String syntax)
/* 26:   */   {
/* 27:41 */     INTERFACES.put(name, syntax);
/* 28:   */   }
/* 29:   */   
/* 30:46 */   String endpoint = null;
/* 31:47 */   HashMap options = null;
/* 32:48 */   UUID uuid = null;
/* 33:   */   int major;
/* 34:   */   int minor;
/* 35:   */   
/* 36:   */   DcerpcBinding(String proto, String server)
/* 37:   */   {
/* 38:53 */     this.proto = proto;
/* 39:54 */     this.server = server;
/* 40:   */   }
/* 41:   */   
/* 42:   */   void setOption(String key, Object val)
/* 43:   */     throws DcerpcException
/* 44:   */   {
/* 45:58 */     if (key.equals("endpoint"))
/* 46:   */     {
/* 47:59 */       this.endpoint = val.toString().toLowerCase();
/* 48:60 */       if (this.endpoint.startsWith("\\pipe\\"))
/* 49:   */       {
/* 50:61 */         String iface = (String)INTERFACES.get(this.endpoint.substring(6));
/* 51:62 */         if (iface != null)
/* 52:   */         {
/* 53:64 */           int c = iface.indexOf(':');
/* 54:65 */           int p = iface.indexOf('.', c + 1);
/* 55:66 */           this.uuid = new UUID(iface.substring(0, c));
/* 56:67 */           this.major = Integer.parseInt(iface.substring(c + 1, p));
/* 57:68 */           this.minor = Integer.parseInt(iface.substring(p + 1));
/* 58:69 */           return;
/* 59:   */         }
/* 60:   */       }
/* 61:72 */       throw new DcerpcException("Bad endpoint: " + this.endpoint);
/* 62:   */     }
/* 63:74 */     if (this.options == null) {
/* 64:75 */       this.options = new HashMap();
/* 65:   */     }
/* 66:76 */     this.options.put(key, val);
/* 67:   */   }
/* 68:   */   
/* 69:   */   Object getOption(String key)
/* 70:   */   {
/* 71:79 */     if (key.equals("endpoint")) {
/* 72:80 */       return this.endpoint;
/* 73:   */     }
/* 74:81 */     if (this.options != null) {
/* 75:82 */       return this.options.get(key);
/* 76:   */     }
/* 77:83 */     return null;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public String toString()
/* 81:   */   {
/* 82:87 */     String ret = this.proto + ":" + this.server + "[" + this.endpoint;
/* 83:88 */     if (this.options != null)
/* 84:   */     {
/* 85:89 */       Iterator iter = this.options.keySet().iterator();
/* 86:90 */       while (iter.hasNext())
/* 87:   */       {
/* 88:91 */         Object key = iter.next();
/* 89:92 */         Object val = this.options.get(key);
/* 90:93 */         ret = ret + "," + key + "=" + val;
/* 91:   */       }
/* 92:   */     }
/* 93:96 */     ret = ret + "]";
/* 94:97 */     return ret;
/* 95:   */   }
/* 96:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcBinding
 * JD-Core Version:    0.7.0.1
 */