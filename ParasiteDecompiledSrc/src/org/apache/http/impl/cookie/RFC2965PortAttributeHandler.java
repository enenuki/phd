/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.cookie.ClientCookie;
/*   6:    */ import org.apache.http.cookie.Cookie;
/*   7:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*   8:    */ import org.apache.http.cookie.CookieOrigin;
/*   9:    */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*  10:    */ import org.apache.http.cookie.MalformedCookieException;
/*  11:    */ import org.apache.http.cookie.SetCookie;
/*  12:    */ import org.apache.http.cookie.SetCookie2;
/*  13:    */ 
/*  14:    */ @Immutable
/*  15:    */ public class RFC2965PortAttributeHandler
/*  16:    */   implements CookieAttributeHandler
/*  17:    */ {
/*  18:    */   private static int[] parsePortAttribute(String portValue)
/*  19:    */     throws MalformedCookieException
/*  20:    */   {
/*  21: 66 */     StringTokenizer st = new StringTokenizer(portValue, ",");
/*  22: 67 */     int[] ports = new int[st.countTokens()];
/*  23:    */     try
/*  24:    */     {
/*  25: 69 */       int i = 0;
/*  26: 70 */       while (st.hasMoreTokens())
/*  27:    */       {
/*  28: 71 */         ports[i] = Integer.parseInt(st.nextToken().trim());
/*  29: 72 */         if (ports[i] < 0) {
/*  30: 73 */           throw new MalformedCookieException("Invalid Port attribute.");
/*  31:    */         }
/*  32: 75 */         i++;
/*  33:    */       }
/*  34:    */     }
/*  35:    */     catch (NumberFormatException e)
/*  36:    */     {
/*  37: 78 */       throw new MalformedCookieException("Invalid Port attribute: " + e.getMessage());
/*  38:    */     }
/*  39: 81 */     return ports;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private static boolean portMatch(int port, int[] ports)
/*  43:    */   {
/*  44: 94 */     boolean portInList = false;
/*  45: 95 */     int i = 0;
/*  46: 95 */     for (int len = ports.length; i < len; i++) {
/*  47: 96 */       if (port == ports[i])
/*  48:    */       {
/*  49: 97 */         portInList = true;
/*  50: 98 */         break;
/*  51:    */       }
/*  52:    */     }
/*  53:101 */     return portInList;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void parse(SetCookie cookie, String portValue)
/*  57:    */     throws MalformedCookieException
/*  58:    */   {
/*  59:109 */     if (cookie == null) {
/*  60:110 */       throw new IllegalArgumentException("Cookie may not be null");
/*  61:    */     }
/*  62:112 */     if ((cookie instanceof SetCookie2))
/*  63:    */     {
/*  64:113 */       SetCookie2 cookie2 = (SetCookie2)cookie;
/*  65:114 */       if ((portValue != null) && (portValue.trim().length() > 0))
/*  66:    */       {
/*  67:115 */         int[] ports = parsePortAttribute(portValue);
/*  68:116 */         cookie2.setPorts(ports);
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  74:    */     throws MalformedCookieException
/*  75:    */   {
/*  76:127 */     if (cookie == null) {
/*  77:128 */       throw new IllegalArgumentException("Cookie may not be null");
/*  78:    */     }
/*  79:130 */     if (origin == null) {
/*  80:131 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  81:    */     }
/*  82:133 */     int port = origin.getPort();
/*  83:134 */     if (((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("port"))) {
/*  84:136 */       if (!portMatch(port, cookie.getPorts())) {
/*  85:137 */         throw new CookieRestrictionViolationException("Port attribute violates RFC 2965: Request port not found in cookie's port list.");
/*  86:    */       }
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  91:    */   {
/*  92:150 */     if (cookie == null) {
/*  93:151 */       throw new IllegalArgumentException("Cookie may not be null");
/*  94:    */     }
/*  95:153 */     if (origin == null) {
/*  96:154 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  97:    */     }
/*  98:156 */     int port = origin.getPort();
/*  99:157 */     if (((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("port")))
/* 100:    */     {
/* 101:159 */       if (cookie.getPorts() == null) {
/* 102:161 */         return false;
/* 103:    */       }
/* 104:163 */       if (!portMatch(port, cookie.getPorts())) {
/* 105:164 */         return false;
/* 106:    */       }
/* 107:    */     }
/* 108:167 */     return true;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2965PortAttributeHandler
 * JD-Core Version:    0.7.0.1
 */