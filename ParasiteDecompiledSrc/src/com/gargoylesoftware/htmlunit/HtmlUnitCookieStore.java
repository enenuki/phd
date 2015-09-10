/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.http.client.CookieStore;
/*   9:    */ import org.apache.http.cookie.Cookie;
/*  10:    */ import org.apache.http.cookie.CookieIdentityComparator;
/*  11:    */ 
/*  12:    */ class HtmlUnitCookieStore
/*  13:    */   implements CookieStore, Serializable
/*  14:    */ {
/*  15:727 */   private final List<Cookie> cookies_ = new ArrayList();
/*  16:728 */   private final CookieIdentityComparator comparator_ = new CookieIdentityComparator();
/*  17:    */   
/*  18:    */   public synchronized void addCookie(Cookie cookie)
/*  19:    */   {
/*  20:734 */     if (cookie == null) {
/*  21:735 */       return;
/*  22:    */     }
/*  23:738 */     int index = findCookieIndex(cookie);
/*  24:739 */     if (cookie.isExpired(new Date()))
/*  25:    */     {
/*  26:740 */       if (index != -1) {
/*  27:741 */         this.cookies_.remove(index);
/*  28:    */       }
/*  29:    */     }
/*  30:744 */     else if (index == -1) {
/*  31:745 */       this.cookies_.add(cookie);
/*  32:    */     } else {
/*  33:748 */       this.cookies_.set(index, cookie);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   private int findCookieIndex(Cookie cookie)
/*  38:    */   {
/*  39:753 */     for (int i = 0; i < this.cookies_.size(); i++)
/*  40:    */     {
/*  41:754 */       Cookie curCookie = (Cookie)this.cookies_.get(i);
/*  42:755 */       if (this.comparator_.compare(cookie, curCookie) == 0) {
/*  43:756 */         return i;
/*  44:    */       }
/*  45:    */     }
/*  46:759 */     return -1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public synchronized List<Cookie> getCookies()
/*  50:    */   {
/*  51:766 */     return new ArrayList(this.cookies_);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public synchronized boolean clearExpired(Date date)
/*  55:    */   {
/*  56:773 */     if (date == null) {
/*  57:774 */       return false;
/*  58:    */     }
/*  59:776 */     boolean removed = false;
/*  60:777 */     for (Iterator<Cookie> it = this.cookies_.iterator(); it.hasNext();) {
/*  61:778 */       if (((Cookie)it.next()).isExpired(date))
/*  62:    */       {
/*  63:779 */         it.remove();
/*  64:780 */         removed = true;
/*  65:    */       }
/*  66:    */     }
/*  67:783 */     return removed;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public synchronized void clear()
/*  71:    */   {
/*  72:790 */     this.cookies_.clear();
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.HtmlUnitCookieStore
 * JD-Core Version:    0.7.0.1
 */