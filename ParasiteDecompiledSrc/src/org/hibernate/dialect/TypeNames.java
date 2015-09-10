/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import java.util.TreeMap;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ 
/*  10:    */ public class TypeNames
/*  11:    */ {
/*  12: 66 */   private Map<Integer, Map<Long, String>> weighted = new HashMap();
/*  13: 67 */   private Map<Integer, String> defaults = new HashMap();
/*  14:    */   
/*  15:    */   public String get(int typecode)
/*  16:    */     throws MappingException
/*  17:    */   {
/*  18: 75 */     String result = (String)this.defaults.get(Integer.valueOf(typecode));
/*  19: 76 */     if (result == null) {
/*  20: 76 */       throw new MappingException("No Dialect mapping for JDBC type: " + typecode);
/*  21:    */     }
/*  22: 77 */     return result;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String get(int typeCode, long size, int precision, int scale)
/*  26:    */     throws MappingException
/*  27:    */   {
/*  28: 90 */     Map<Long, String> map = (Map)this.weighted.get(Integer.valueOf(typeCode));
/*  29: 91 */     if ((map != null) && (map.size() > 0)) {
/*  30: 93 */       for (Map.Entry<Long, String> entry : map.entrySet()) {
/*  31: 94 */         if (size <= ((Long)entry.getKey()).longValue()) {
/*  32: 95 */           return replace((String)entry.getValue(), size, precision, scale);
/*  33:    */         }
/*  34:    */       }
/*  35:    */     }
/*  36: 99 */     return replace(get(typeCode), size, precision, scale);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static String replace(String type, long size, int precision, int scale)
/*  40:    */   {
/*  41:103 */     type = StringHelper.replaceOnce(type, "$s", Integer.toString(scale));
/*  42:104 */     type = StringHelper.replaceOnce(type, "$l", Long.toString(size));
/*  43:105 */     return StringHelper.replaceOnce(type, "$p", Integer.toString(precision));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void put(int typecode, long capacity, String value)
/*  47:    */   {
/*  48:113 */     Map<Long, String> map = (Map)this.weighted.get(Integer.valueOf(typecode));
/*  49:114 */     if (map == null)
/*  50:    */     {
/*  51:115 */       map = new TreeMap();
/*  52:116 */       this.weighted.put(Integer.valueOf(typecode), map);
/*  53:    */     }
/*  54:118 */     map.put(Long.valueOf(capacity), value);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void put(int typecode, String value)
/*  58:    */   {
/*  59:126 */     this.defaults.put(Integer.valueOf(typecode), value);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.TypeNames
 * JD-Core Version:    0.7.0.1
 */