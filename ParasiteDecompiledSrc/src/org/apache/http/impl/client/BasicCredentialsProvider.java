/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.concurrent.ConcurrentHashMap;
/*   5:    */ import org.apache.http.annotation.ThreadSafe;
/*   6:    */ import org.apache.http.auth.AuthScope;
/*   7:    */ import org.apache.http.auth.Credentials;
/*   8:    */ import org.apache.http.client.CredentialsProvider;
/*   9:    */ 
/*  10:    */ @ThreadSafe
/*  11:    */ public class BasicCredentialsProvider
/*  12:    */   implements CredentialsProvider
/*  13:    */ {
/*  14:    */   private final ConcurrentHashMap<AuthScope, Credentials> credMap;
/*  15:    */   
/*  16:    */   public BasicCredentialsProvider()
/*  17:    */   {
/*  18: 53 */     this.credMap = new ConcurrentHashMap();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setCredentials(AuthScope authscope, Credentials credentials)
/*  22:    */   {
/*  23: 59 */     if (authscope == null) {
/*  24: 60 */       throw new IllegalArgumentException("Authentication scope may not be null");
/*  25:    */     }
/*  26: 62 */     this.credMap.put(authscope, credentials);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private static Credentials matchCredentials(Map<AuthScope, Credentials> map, AuthScope authscope)
/*  30:    */   {
/*  31: 77 */     Credentials creds = (Credentials)map.get(authscope);
/*  32: 78 */     if (creds == null)
/*  33:    */     {
/*  34: 81 */       int bestMatchFactor = -1;
/*  35: 82 */       AuthScope bestMatch = null;
/*  36: 83 */       for (AuthScope current : map.keySet())
/*  37:    */       {
/*  38: 84 */         int factor = authscope.match(current);
/*  39: 85 */         if (factor > bestMatchFactor)
/*  40:    */         {
/*  41: 86 */           bestMatchFactor = factor;
/*  42: 87 */           bestMatch = current;
/*  43:    */         }
/*  44:    */       }
/*  45: 90 */       if (bestMatch != null) {
/*  46: 91 */         creds = (Credentials)map.get(bestMatch);
/*  47:    */       }
/*  48:    */     }
/*  49: 94 */     return creds;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Credentials getCredentials(AuthScope authscope)
/*  53:    */   {
/*  54: 98 */     if (authscope == null) {
/*  55: 99 */       throw new IllegalArgumentException("Authentication scope may not be null");
/*  56:    */     }
/*  57:101 */     return matchCredentials(this.credMap, authscope);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void clear()
/*  61:    */   {
/*  62:105 */     this.credMap.clear();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String toString()
/*  66:    */   {
/*  67:110 */     return this.credMap.toString();
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.BasicCredentialsProvider
 * JD-Core Version:    0.7.0.1
 */