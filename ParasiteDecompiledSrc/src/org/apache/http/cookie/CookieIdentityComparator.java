/*  1:   */ package org.apache.http.cookie;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class CookieIdentityComparator
/*  9:   */   implements Serializable, Comparator<Cookie>
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 4466565437490631532L;
/* 12:   */   
/* 13:   */   public int compare(Cookie c1, Cookie c2)
/* 14:   */   {
/* 15:49 */     int res = c1.getName().compareTo(c2.getName());
/* 16:50 */     if (res == 0)
/* 17:   */     {
/* 18:52 */       String d1 = c1.getDomain();
/* 19:53 */       if (d1 == null) {
/* 20:54 */         d1 = "";
/* 21:55 */       } else if (d1.indexOf('.') == -1) {
/* 22:56 */         d1 = d1 + ".local";
/* 23:   */       }
/* 24:58 */       String d2 = c2.getDomain();
/* 25:59 */       if (d2 == null) {
/* 26:60 */         d2 = "";
/* 27:61 */       } else if (d2.indexOf('.') == -1) {
/* 28:62 */         d2 = d2 + ".local";
/* 29:   */       }
/* 30:64 */       res = d1.compareToIgnoreCase(d2);
/* 31:   */     }
/* 32:66 */     if (res == 0)
/* 33:   */     {
/* 34:67 */       String p1 = c1.getPath();
/* 35:68 */       if (p1 == null) {
/* 36:69 */         p1 = "/";
/* 37:   */       }
/* 38:71 */       String p2 = c2.getPath();
/* 39:72 */       if (p2 == null) {
/* 40:73 */         p2 = "/";
/* 41:   */       }
/* 42:75 */       res = p1.compareTo(p2);
/* 43:   */     }
/* 44:77 */     return res;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.CookieIdentityComparator
 * JD-Core Version:    0.7.0.1
 */