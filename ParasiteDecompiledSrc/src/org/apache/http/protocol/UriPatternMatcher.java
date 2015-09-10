/*   1:    */ package org.apache.http.protocol;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ 
/*   8:    */ public class UriPatternMatcher
/*   9:    */ {
/*  10:    */   private final Map map;
/*  11:    */   
/*  12:    */   public UriPatternMatcher()
/*  13:    */   {
/*  14: 58 */     this.map = new HashMap();
/*  15:    */   }
/*  16:    */   
/*  17:    */   public synchronized void register(String pattern, Object obj)
/*  18:    */   {
/*  19: 68 */     if (pattern == null) {
/*  20: 69 */       throw new IllegalArgumentException("URI request pattern may not be null");
/*  21:    */     }
/*  22: 71 */     this.map.put(pattern, obj);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public synchronized void unregister(String pattern)
/*  26:    */   {
/*  27: 80 */     if (pattern == null) {
/*  28: 81 */       return;
/*  29:    */     }
/*  30: 83 */     this.map.remove(pattern);
/*  31:    */   }
/*  32:    */   
/*  33:    */   /**
/*  34:    */    * @deprecated
/*  35:    */    */
/*  36:    */   public synchronized void setHandlers(Map map)
/*  37:    */   {
/*  38: 90 */     if (map == null) {
/*  39: 91 */       throw new IllegalArgumentException("Map of handlers may not be null");
/*  40:    */     }
/*  41: 93 */     this.map.clear();
/*  42: 94 */     this.map.putAll(map);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public synchronized void setObjects(Map map)
/*  46:    */   {
/*  47:102 */     if (map == null) {
/*  48:103 */       throw new IllegalArgumentException("Map of handlers may not be null");
/*  49:    */     }
/*  50:105 */     this.map.clear();
/*  51:106 */     this.map.putAll(map);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public synchronized Object lookup(String requestURI)
/*  55:    */   {
/*  56:116 */     if (requestURI == null) {
/*  57:117 */       throw new IllegalArgumentException("Request URI may not be null");
/*  58:    */     }
/*  59:120 */     int index = requestURI.indexOf("?");
/*  60:121 */     if (index != -1) {
/*  61:122 */       requestURI = requestURI.substring(0, index);
/*  62:    */     }
/*  63:126 */     Object obj = this.map.get(requestURI);
/*  64:    */     String bestMatch;
/*  65:    */     Iterator it;
/*  66:127 */     if (obj == null)
/*  67:    */     {
/*  68:129 */       bestMatch = null;
/*  69:130 */       for (it = this.map.keySet().iterator(); it.hasNext();)
/*  70:    */       {
/*  71:131 */         String pattern = (String)it.next();
/*  72:132 */         if (matchUriRequestPattern(pattern, requestURI)) {
/*  73:134 */           if ((bestMatch == null) || (bestMatch.length() < pattern.length()) || ((bestMatch.length() == pattern.length()) && (pattern.endsWith("*"))))
/*  74:    */           {
/*  75:137 */             obj = this.map.get(pattern);
/*  76:138 */             bestMatch = pattern;
/*  77:    */           }
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:143 */     return obj;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected boolean matchUriRequestPattern(String pattern, String requestUri)
/*  85:    */   {
/*  86:155 */     if (pattern.equals("*")) {
/*  87:156 */       return true;
/*  88:    */     }
/*  89:158 */     return ((pattern.endsWith("*")) && (requestUri.startsWith(pattern.substring(0, pattern.length() - 1)))) || ((pattern.startsWith("*")) && (requestUri.endsWith(pattern.substring(1, pattern.length()))));
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.UriPatternMatcher
 * JD-Core Version:    0.7.0.1
 */