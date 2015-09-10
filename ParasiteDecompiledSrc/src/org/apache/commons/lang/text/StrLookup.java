/*   1:    */ package org.apache.commons.lang.text;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ 
/*   5:    */ public abstract class StrLookup
/*   6:    */ {
/*   7: 49 */   private static final StrLookup NONE_LOOKUP = new MapStrLookup(null);
/*   8:    */   private static final StrLookup SYSTEM_PROPERTIES_LOOKUP;
/*   9:    */   
/*  10:    */   static
/*  11:    */   {
/*  12: 50 */     StrLookup lookup = null;
/*  13:    */     try
/*  14:    */     {
/*  15: 52 */       lookup = new MapStrLookup(System.getProperties());
/*  16:    */     }
/*  17:    */     catch (SecurityException ex)
/*  18:    */     {
/*  19: 54 */       lookup = NONE_LOOKUP;
/*  20:    */     }
/*  21: 56 */     SYSTEM_PROPERTIES_LOOKUP = lookup;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static StrLookup noneLookup()
/*  25:    */   {
/*  26: 66 */     return NONE_LOOKUP;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static StrLookup systemPropertiesLookup()
/*  30:    */   {
/*  31: 81 */     return SYSTEM_PROPERTIES_LOOKUP;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static StrLookup mapLookup(Map map)
/*  35:    */   {
/*  36: 94 */     return new MapStrLookup(map);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public abstract String lookup(String paramString);
/*  40:    */   
/*  41:    */   static class MapStrLookup
/*  42:    */     extends StrLookup
/*  43:    */   {
/*  44:    */     private final Map map;
/*  45:    */     
/*  46:    */     MapStrLookup(Map map)
/*  47:    */     {
/*  48:145 */       this.map = map;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public String lookup(String key)
/*  52:    */     {
/*  53:158 */       if (this.map == null) {
/*  54:159 */         return null;
/*  55:    */       }
/*  56:161 */       Object obj = this.map.get(key);
/*  57:162 */       if (obj == null) {
/*  58:163 */         return null;
/*  59:    */       }
/*  60:165 */       return obj.toString();
/*  61:    */     }
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.StrLookup
 * JD-Core Version:    0.7.0.1
 */