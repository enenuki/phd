/*   1:    */ package org.apache.http.auth;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Locale;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.concurrent.ConcurrentHashMap;
/*   8:    */ import org.apache.http.annotation.ThreadSafe;
/*   9:    */ import org.apache.http.params.HttpParams;
/*  10:    */ 
/*  11:    */ @ThreadSafe
/*  12:    */ public final class AuthSchemeRegistry
/*  13:    */ {
/*  14:    */   private final ConcurrentHashMap<String, AuthSchemeFactory> registeredSchemes;
/*  15:    */   
/*  16:    */   public AuthSchemeRegistry()
/*  17:    */   {
/*  18: 52 */     this.registeredSchemes = new ConcurrentHashMap();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void register(String name, AuthSchemeFactory factory)
/*  22:    */   {
/*  23: 73 */     if (name == null) {
/*  24: 74 */       throw new IllegalArgumentException("Name may not be null");
/*  25:    */     }
/*  26: 76 */     if (factory == null) {
/*  27: 77 */       throw new IllegalArgumentException("Authentication scheme factory may not be null");
/*  28:    */     }
/*  29: 79 */     this.registeredSchemes.put(name.toLowerCase(Locale.ENGLISH), factory);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void unregister(String name)
/*  33:    */   {
/*  34: 89 */     if (name == null) {
/*  35: 90 */       throw new IllegalArgumentException("Name may not be null");
/*  36:    */     }
/*  37: 92 */     this.registeredSchemes.remove(name.toLowerCase(Locale.ENGLISH));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public AuthScheme getAuthScheme(String name, HttpParams params)
/*  41:    */     throws IllegalStateException
/*  42:    */   {
/*  43:109 */     if (name == null) {
/*  44:110 */       throw new IllegalArgumentException("Name may not be null");
/*  45:    */     }
/*  46:112 */     AuthSchemeFactory factory = (AuthSchemeFactory)this.registeredSchemes.get(name.toLowerCase(Locale.ENGLISH));
/*  47:113 */     if (factory != null) {
/*  48:114 */       return factory.newInstance(params);
/*  49:    */     }
/*  50:116 */     throw new IllegalStateException("Unsupported authentication scheme: " + name);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<String> getSchemeNames()
/*  54:    */   {
/*  55:127 */     return new ArrayList(this.registeredSchemes.keySet());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setItems(Map<String, AuthSchemeFactory> map)
/*  59:    */   {
/*  60:137 */     if (map == null) {
/*  61:138 */       return;
/*  62:    */     }
/*  63:140 */     this.registeredSchemes.clear();
/*  64:141 */     this.registeredSchemes.putAll(map);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.AuthSchemeRegistry
 * JD-Core Version:    0.7.0.1
 */