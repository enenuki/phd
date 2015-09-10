/*   1:    */ package org.apache.http.impl.auth;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.http.HeaderElement;
/*   7:    */ import org.apache.http.annotation.NotThreadSafe;
/*   8:    */ import org.apache.http.auth.MalformedChallengeException;
/*   9:    */ import org.apache.http.message.BasicHeaderValueParser;
/*  10:    */ import org.apache.http.message.HeaderValueParser;
/*  11:    */ import org.apache.http.message.ParserCursor;
/*  12:    */ import org.apache.http.util.CharArrayBuffer;
/*  13:    */ 
/*  14:    */ @NotThreadSafe
/*  15:    */ public abstract class RFC2617Scheme
/*  16:    */   extends AuthSchemeBase
/*  17:    */ {
/*  18:    */   private Map<String, String> params;
/*  19:    */   
/*  20:    */   protected void parseChallenge(CharArrayBuffer buffer, int pos, int len)
/*  21:    */     throws MalformedChallengeException
/*  22:    */   {
/*  23: 67 */     HeaderValueParser parser = BasicHeaderValueParser.DEFAULT;
/*  24: 68 */     ParserCursor cursor = new ParserCursor(pos, buffer.length());
/*  25: 69 */     HeaderElement[] elements = parser.parseElements(buffer, cursor);
/*  26: 70 */     if (elements.length == 0) {
/*  27: 71 */       throw new MalformedChallengeException("Authentication challenge is empty");
/*  28:    */     }
/*  29: 74 */     this.params = new HashMap(elements.length);
/*  30: 75 */     for (HeaderElement element : elements) {
/*  31: 76 */       this.params.put(element.getName(), element.getValue());
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected Map<String, String> getParameters()
/*  36:    */   {
/*  37: 86 */     if (this.params == null) {
/*  38: 87 */       this.params = new HashMap();
/*  39:    */     }
/*  40: 89 */     return this.params;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getParameter(String name)
/*  44:    */   {
/*  45:100 */     if (name == null) {
/*  46:101 */       throw new IllegalArgumentException("Parameter name may not be null");
/*  47:    */     }
/*  48:103 */     if (this.params == null) {
/*  49:104 */       return null;
/*  50:    */     }
/*  51:106 */     return (String)this.params.get(name.toLowerCase(Locale.ENGLISH));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getRealm()
/*  55:    */   {
/*  56:115 */     return getParameter("realm");
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.RFC2617Scheme
 * JD-Core Version:    0.7.0.1
 */