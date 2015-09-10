/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.TreeSet;
/*   9:    */ import org.apache.http.annotation.GuardedBy;
/*  10:    */ import org.apache.http.annotation.ThreadSafe;
/*  11:    */ import org.apache.http.client.CookieStore;
/*  12:    */ import org.apache.http.cookie.Cookie;
/*  13:    */ import org.apache.http.cookie.CookieIdentityComparator;
/*  14:    */ 
/*  15:    */ @ThreadSafe
/*  16:    */ public class BasicCookieStore
/*  17:    */   implements CookieStore, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -7581093305228232025L;
/*  20:    */   @GuardedBy("this")
/*  21:    */   private final TreeSet<Cookie> cookies;
/*  22:    */   
/*  23:    */   public BasicCookieStore()
/*  24:    */   {
/*  25: 55 */     this.cookies = new TreeSet(new CookieIdentityComparator());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public synchronized void addCookie(Cookie cookie)
/*  29:    */   {
/*  30: 69 */     if (cookie != null)
/*  31:    */     {
/*  32: 71 */       this.cookies.remove(cookie);
/*  33: 72 */       if (!cookie.isExpired(new Date())) {
/*  34: 73 */         this.cookies.add(cookie);
/*  35:    */       }
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public synchronized void addCookies(Cookie[] cookies)
/*  40:    */   {
/*  41: 89 */     if (cookies != null) {
/*  42: 90 */       for (Cookie cooky : cookies) {
/*  43: 91 */         addCookie(cooky);
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public synchronized List<Cookie> getCookies()
/*  49:    */   {
/*  50:104 */     return new ArrayList(this.cookies);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public synchronized boolean clearExpired(Date date)
/*  54:    */   {
/*  55:116 */     if (date == null) {
/*  56:117 */       return false;
/*  57:    */     }
/*  58:119 */     boolean removed = false;
/*  59:120 */     for (Iterator<Cookie> it = this.cookies.iterator(); it.hasNext();) {
/*  60:121 */       if (((Cookie)it.next()).isExpired(date))
/*  61:    */       {
/*  62:122 */         it.remove();
/*  63:123 */         removed = true;
/*  64:    */       }
/*  65:    */     }
/*  66:126 */     return removed;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public synchronized void clear()
/*  70:    */   {
/*  71:133 */     this.cookies.clear();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public synchronized String toString()
/*  75:    */   {
/*  76:138 */     return this.cookies.toString();
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.BasicCookieStore
 * JD-Core Version:    0.7.0.1
 */