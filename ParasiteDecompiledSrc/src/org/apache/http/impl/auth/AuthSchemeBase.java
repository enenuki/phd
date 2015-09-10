/*   1:    */ package org.apache.http.impl.auth;
/*   2:    */ 
/*   3:    */ import org.apache.http.FormattedHeader;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.HttpRequest;
/*   6:    */ import org.apache.http.annotation.NotThreadSafe;
/*   7:    */ import org.apache.http.auth.AuthenticationException;
/*   8:    */ import org.apache.http.auth.ContextAwareAuthScheme;
/*   9:    */ import org.apache.http.auth.Credentials;
/*  10:    */ import org.apache.http.auth.MalformedChallengeException;
/*  11:    */ import org.apache.http.protocol.HTTP;
/*  12:    */ import org.apache.http.protocol.HttpContext;
/*  13:    */ import org.apache.http.util.CharArrayBuffer;
/*  14:    */ 
/*  15:    */ @NotThreadSafe
/*  16:    */ public abstract class AuthSchemeBase
/*  17:    */   implements ContextAwareAuthScheme
/*  18:    */ {
/*  19:    */   private boolean proxy;
/*  20:    */   
/*  21:    */   public void processChallenge(Header header)
/*  22:    */     throws MalformedChallengeException
/*  23:    */   {
/*  24: 76 */     if (header == null) {
/*  25: 77 */       throw new IllegalArgumentException("Header may not be null");
/*  26:    */     }
/*  27: 79 */     String authheader = header.getName();
/*  28: 80 */     if (authheader.equalsIgnoreCase("WWW-Authenticate")) {
/*  29: 81 */       this.proxy = false;
/*  30: 82 */     } else if (authheader.equalsIgnoreCase("Proxy-Authenticate")) {
/*  31: 83 */       this.proxy = true;
/*  32:    */     } else {
/*  33: 85 */       throw new MalformedChallengeException("Unexpected header name: " + authheader);
/*  34:    */     }
/*  35:    */     int pos;
/*  36:    */     CharArrayBuffer buffer;
/*  37:    */     int pos;
/*  38: 90 */     if ((header instanceof FormattedHeader))
/*  39:    */     {
/*  40: 91 */       CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  41: 92 */       pos = ((FormattedHeader)header).getValuePos();
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 94 */       String s = header.getValue();
/*  46: 95 */       if (s == null) {
/*  47: 96 */         throw new MalformedChallengeException("Header value is null");
/*  48:    */       }
/*  49: 98 */       buffer = new CharArrayBuffer(s.length());
/*  50: 99 */       buffer.append(s);
/*  51:100 */       pos = 0;
/*  52:    */     }
/*  53:102 */     while ((pos < buffer.length()) && (HTTP.isWhitespace(buffer.charAt(pos)))) {
/*  54:103 */       pos++;
/*  55:    */     }
/*  56:105 */     int beginIndex = pos;
/*  57:106 */     while ((pos < buffer.length()) && (!HTTP.isWhitespace(buffer.charAt(pos)))) {
/*  58:107 */       pos++;
/*  59:    */     }
/*  60:109 */     int endIndex = pos;
/*  61:110 */     String s = buffer.substring(beginIndex, endIndex);
/*  62:111 */     if (!s.equalsIgnoreCase(getSchemeName())) {
/*  63:112 */       throw new MalformedChallengeException("Invalid scheme identifier: " + s);
/*  64:    */     }
/*  65:115 */     parseChallenge(buffer, pos, buffer.length());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Header authenticate(Credentials credentials, HttpRequest request, HttpContext context)
/*  69:    */     throws AuthenticationException
/*  70:    */   {
/*  71:124 */     return authenticate(credentials, request);
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected abstract void parseChallenge(CharArrayBuffer paramCharArrayBuffer, int paramInt1, int paramInt2)
/*  75:    */     throws MalformedChallengeException;
/*  76:    */   
/*  77:    */   public boolean isProxy()
/*  78:    */   {
/*  79:138 */     return this.proxy;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String toString()
/*  83:    */   {
/*  84:143 */     return getSchemeName();
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.AuthSchemeBase
 * JD-Core Version:    0.7.0.1
 */