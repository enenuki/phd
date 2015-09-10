/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URL;
/*  5:   */ import java.net.URLConnection;
/*  6:   */ import java.net.URLStreamHandler;
/*  7:   */ 
/*  8:   */ public class Handler
/*  9:   */   extends URLStreamHandler
/* 10:   */ {
/* 11:30 */   static final URLStreamHandler SMB_HANDLER = new Handler();
/* 12:   */   
/* 13:   */   protected int getDefaultPort()
/* 14:   */   {
/* 15:33 */     return 445;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public URLConnection openConnection(URL u)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:36 */     return new SmbFile(u);
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void parseURL(URL u, String spec, int start, int limit)
/* 25:   */   {
/* 26:39 */     String host = u.getHost();
/* 27:43 */     if (spec.equals("smb://"))
/* 28:   */     {
/* 29:44 */       spec = "smb:////";
/* 30:45 */       limit += 2;
/* 31:   */     }
/* 32:46 */     else if ((!spec.startsWith("smb://")) && (host != null) && (host.length() == 0))
/* 33:   */     {
/* 34:48 */       spec = "//" + spec;
/* 35:49 */       limit += 2;
/* 36:   */     }
/* 37:51 */     super.parseURL(u, spec, start, limit);
/* 38:52 */     String path = u.getPath();
/* 39:53 */     String ref = u.getRef();
/* 40:54 */     if (ref != null) {
/* 41:55 */       path = path + '#' + ref;
/* 42:   */     }
/* 43:57 */     int port = u.getPort();
/* 44:58 */     if (port == -1) {
/* 45:59 */       port = getDefaultPort();
/* 46:   */     }
/* 47:61 */     setURL(u, "smb", u.getHost(), port, u.getAuthority(), u.getUserInfo(), path, u.getQuery(), null);
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Handler
 * JD-Core Version:    0.7.0.1
 */