/*   1:    */ package org.apache.http.cookie;
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
/*  12:    */ public final class CookieSpecRegistry
/*  13:    */ {
/*  14:    */   private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs;
/*  15:    */   
/*  16:    */   public CookieSpecRegistry()
/*  17:    */   {
/*  18: 55 */     this.registeredSpecs = new ConcurrentHashMap();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void register(String name, CookieSpecFactory factory)
/*  22:    */   {
/*  23: 70 */     if (name == null) {
/*  24: 71 */       throw new IllegalArgumentException("Name may not be null");
/*  25:    */     }
/*  26: 73 */     if (factory == null) {
/*  27: 74 */       throw new IllegalArgumentException("Cookie spec factory may not be null");
/*  28:    */     }
/*  29: 76 */     this.registeredSpecs.put(name.toLowerCase(Locale.ENGLISH), factory);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void unregister(String id)
/*  33:    */   {
/*  34: 85 */     if (id == null) {
/*  35: 86 */       throw new IllegalArgumentException("Id may not be null");
/*  36:    */     }
/*  37: 88 */     this.registeredSpecs.remove(id.toLowerCase(Locale.ENGLISH));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public CookieSpec getCookieSpec(String name, HttpParams params)
/*  41:    */     throws IllegalStateException
/*  42:    */   {
/*  43:105 */     if (name == null) {
/*  44:106 */       throw new IllegalArgumentException("Name may not be null");
/*  45:    */     }
/*  46:108 */     CookieSpecFactory factory = (CookieSpecFactory)this.registeredSpecs.get(name.toLowerCase(Locale.ENGLISH));
/*  47:109 */     if (factory != null) {
/*  48:110 */       return factory.newInstance(params);
/*  49:    */     }
/*  50:112 */     throw new IllegalStateException("Unsupported cookie spec: " + name);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CookieSpec getCookieSpec(String name)
/*  54:    */     throws IllegalStateException
/*  55:    */   {
/*  56:127 */     return getCookieSpec(name, null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<String> getSpecNames()
/*  60:    */   {
/*  61:140 */     return new ArrayList(this.registeredSpecs.keySet());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setItems(Map<String, CookieSpecFactory> map)
/*  65:    */   {
/*  66:150 */     if (map == null) {
/*  67:151 */       return;
/*  68:    */     }
/*  69:153 */     this.registeredSpecs.clear();
/*  70:154 */     this.registeredSpecs.putAll(map);
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.CookieSpecRegistry
 * JD-Core Version:    0.7.0.1
 */