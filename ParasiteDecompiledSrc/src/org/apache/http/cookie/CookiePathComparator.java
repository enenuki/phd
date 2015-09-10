/*  1:   */ package org.apache.http.cookie;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class CookiePathComparator
/*  9:   */   implements Serializable, Comparator<Cookie>
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 7523645369616405818L;
/* 12:   */   
/* 13:   */   private String normalizePath(Cookie cookie)
/* 14:   */   {
/* 15:56 */     String path = cookie.getPath();
/* 16:57 */     if (path == null) {
/* 17:58 */       path = "/";
/* 18:   */     }
/* 19:60 */     if (!path.endsWith("/")) {
/* 20:61 */       path = path + '/';
/* 21:   */     }
/* 22:63 */     return path;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int compare(Cookie c1, Cookie c2)
/* 26:   */   {
/* 27:67 */     String path1 = normalizePath(c1);
/* 28:68 */     String path2 = normalizePath(c2);
/* 29:69 */     if (path1.equals(path2)) {
/* 30:70 */       return 0;
/* 31:   */     }
/* 32:71 */     if (path1.startsWith(path2)) {
/* 33:72 */       return -1;
/* 34:   */     }
/* 35:73 */     if (path2.startsWith(path1)) {
/* 36:74 */       return 1;
/* 37:   */     }
/* 38:77 */     return 0;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.CookiePathComparator
 * JD-Core Version:    0.7.0.1
 */