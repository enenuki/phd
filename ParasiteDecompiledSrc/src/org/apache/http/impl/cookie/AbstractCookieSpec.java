/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.http.annotation.NotThreadSafe;
/*   7:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*   8:    */ import org.apache.http.cookie.CookieSpec;
/*   9:    */ 
/*  10:    */ @NotThreadSafe
/*  11:    */ public abstract class AbstractCookieSpec
/*  12:    */   implements CookieSpec
/*  13:    */ {
/*  14:    */   private final Map<String, CookieAttributeHandler> attribHandlerMap;
/*  15:    */   
/*  16:    */   public AbstractCookieSpec()
/*  17:    */   {
/*  18: 60 */     this.attribHandlerMap = new HashMap(10);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void registerAttribHandler(String name, CookieAttributeHandler handler)
/*  22:    */   {
/*  23: 65 */     if (name == null) {
/*  24: 66 */       throw new IllegalArgumentException("Attribute name may not be null");
/*  25:    */     }
/*  26: 68 */     if (handler == null) {
/*  27: 69 */       throw new IllegalArgumentException("Attribute handler may not be null");
/*  28:    */     }
/*  29: 71 */     this.attribHandlerMap.put(name, handler);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected CookieAttributeHandler findAttribHandler(String name)
/*  33:    */   {
/*  34: 83 */     return (CookieAttributeHandler)this.attribHandlerMap.get(name);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected CookieAttributeHandler getAttribHandler(String name)
/*  38:    */   {
/*  39: 95 */     CookieAttributeHandler handler = findAttribHandler(name);
/*  40: 96 */     if (handler == null) {
/*  41: 97 */       throw new IllegalStateException("Handler not registered for " + name + " attribute.");
/*  42:    */     }
/*  43:100 */     return handler;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected Collection<CookieAttributeHandler> getAttribHandlers()
/*  47:    */   {
/*  48:105 */     return this.attribHandlerMap.values();
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.AbstractCookieSpec
 * JD-Core Version:    0.7.0.1
 */