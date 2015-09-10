/*   1:    */ package org.apache.http.conn.scheme;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.concurrent.ConcurrentHashMap;
/*   7:    */ import org.apache.http.HttpHost;
/*   8:    */ import org.apache.http.annotation.ThreadSafe;
/*   9:    */ 
/*  10:    */ @ThreadSafe
/*  11:    */ public final class SchemeRegistry
/*  12:    */ {
/*  13:    */   private final ConcurrentHashMap<String, Scheme> registeredSchemes;
/*  14:    */   
/*  15:    */   public SchemeRegistry()
/*  16:    */   {
/*  17: 55 */     this.registeredSchemes = new ConcurrentHashMap();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public final Scheme getScheme(String name)
/*  21:    */   {
/*  22: 69 */     Scheme found = get(name);
/*  23: 70 */     if (found == null) {
/*  24: 71 */       throw new IllegalStateException("Scheme '" + name + "' not registered.");
/*  25:    */     }
/*  26: 74 */     return found;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final Scheme getScheme(HttpHost host)
/*  30:    */   {
/*  31: 89 */     if (host == null) {
/*  32: 90 */       throw new IllegalArgumentException("Host must not be null.");
/*  33:    */     }
/*  34: 92 */     return getScheme(host.getSchemeName());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final Scheme get(String name)
/*  38:    */   {
/*  39:104 */     if (name == null) {
/*  40:105 */       throw new IllegalArgumentException("Name must not be null.");
/*  41:    */     }
/*  42:109 */     Scheme found = (Scheme)this.registeredSchemes.get(name);
/*  43:110 */     return found;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final Scheme register(Scheme sch)
/*  47:    */   {
/*  48:124 */     if (sch == null) {
/*  49:125 */       throw new IllegalArgumentException("Scheme must not be null.");
/*  50:    */     }
/*  51:127 */     Scheme old = (Scheme)this.registeredSchemes.put(sch.getName(), sch);
/*  52:128 */     return old;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final Scheme unregister(String name)
/*  56:    */   {
/*  57:140 */     if (name == null) {
/*  58:141 */       throw new IllegalArgumentException("Name must not be null.");
/*  59:    */     }
/*  60:145 */     Scheme gone = (Scheme)this.registeredSchemes.remove(name);
/*  61:146 */     return gone;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final List<String> getSchemeNames()
/*  65:    */   {
/*  66:155 */     return new ArrayList(this.registeredSchemes.keySet());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setItems(Map<String, Scheme> map)
/*  70:    */   {
/*  71:165 */     if (map == null) {
/*  72:166 */       return;
/*  73:    */     }
/*  74:168 */     this.registeredSchemes.clear();
/*  75:169 */     this.registeredSchemes.putAll(map);
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.SchemeRegistry
 * JD-Core Version:    0.7.0.1
 */