/*   1:    */ package org.apache.http.impl.auth;
/*   2:    */ 
/*   3:    */ import java.security.Principal;
/*   4:    */ import org.apache.commons.codec.binary.Base64;
/*   5:    */ import org.apache.http.Header;
/*   6:    */ import org.apache.http.HttpRequest;
/*   7:    */ import org.apache.http.annotation.NotThreadSafe;
/*   8:    */ import org.apache.http.auth.AuthenticationException;
/*   9:    */ import org.apache.http.auth.Credentials;
/*  10:    */ import org.apache.http.auth.MalformedChallengeException;
/*  11:    */ import org.apache.http.auth.params.AuthParams;
/*  12:    */ import org.apache.http.message.BufferedHeader;
/*  13:    */ import org.apache.http.util.CharArrayBuffer;
/*  14:    */ import org.apache.http.util.EncodingUtils;
/*  15:    */ 
/*  16:    */ @NotThreadSafe
/*  17:    */ public class BasicScheme
/*  18:    */   extends RFC2617Scheme
/*  19:    */ {
/*  20:    */   private boolean complete;
/*  21:    */   
/*  22:    */   public BasicScheme()
/*  23:    */   {
/*  24: 66 */     this.complete = false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getSchemeName()
/*  28:    */   {
/*  29: 75 */     return "basic";
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void processChallenge(Header header)
/*  33:    */     throws MalformedChallengeException
/*  34:    */   {
/*  35: 89 */     super.processChallenge(header);
/*  36: 90 */     this.complete = true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isComplete()
/*  40:    */   {
/*  41:100 */     return this.complete;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isConnectionBased()
/*  45:    */   {
/*  46:109 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Header authenticate(Credentials credentials, HttpRequest request)
/*  50:    */     throws AuthenticationException
/*  51:    */   {
/*  52:128 */     if (credentials == null) {
/*  53:129 */       throw new IllegalArgumentException("Credentials may not be null");
/*  54:    */     }
/*  55:131 */     if (request == null) {
/*  56:132 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  57:    */     }
/*  58:135 */     String charset = AuthParams.getCredentialCharset(request.getParams());
/*  59:136 */     return authenticate(credentials, charset, isProxy());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static Header authenticate(Credentials credentials, String charset, boolean proxy)
/*  63:    */   {
/*  64:152 */     if (credentials == null) {
/*  65:153 */       throw new IllegalArgumentException("Credentials may not be null");
/*  66:    */     }
/*  67:155 */     if (charset == null) {
/*  68:156 */       throw new IllegalArgumentException("charset may not be null");
/*  69:    */     }
/*  70:159 */     StringBuilder tmp = new StringBuilder();
/*  71:160 */     tmp.append(credentials.getUserPrincipal().getName());
/*  72:161 */     tmp.append(":");
/*  73:162 */     tmp.append(credentials.getPassword() == null ? "null" : credentials.getPassword());
/*  74:    */     
/*  75:164 */     byte[] base64password = Base64.encodeBase64(EncodingUtils.getBytes(tmp.toString(), charset));
/*  76:    */     
/*  77:    */ 
/*  78:167 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/*  79:168 */     if (proxy) {
/*  80:169 */       buffer.append("Proxy-Authorization");
/*  81:    */     } else {
/*  82:171 */       buffer.append("Authorization");
/*  83:    */     }
/*  84:173 */     buffer.append(": Basic ");
/*  85:174 */     buffer.append(base64password, 0, base64password.length);
/*  86:    */     
/*  87:176 */     return new BufferedHeader(buffer);
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.BasicScheme
 * JD-Core Version:    0.7.0.1
 */