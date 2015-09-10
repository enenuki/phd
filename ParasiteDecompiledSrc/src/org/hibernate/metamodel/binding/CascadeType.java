/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.engine.spi.CascadeStyle;
/*   7:    */ 
/*   8:    */ public enum CascadeType
/*   9:    */ {
/*  10: 41 */   ALL,  ALL_DELETE_ORPHAN,  UPDATE,  PERSIST,  MERGE,  LOCK,  REFRESH,  REPLICATE,  EVICT,  DELETE,  DELETE_ORPHAN,  NONE;
/*  11:    */   
/*  12:    */   private static final Map<String, CascadeType> hbmOptionToCascadeType;
/*  13:    */   private static final Map<javax.persistence.CascadeType, CascadeType> jpaCascadeTypeToHibernateCascadeType;
/*  14:    */   private static final Map<CascadeType, CascadeStyle> cascadeTypeToCascadeStyle;
/*  15:    */   
/*  16:    */   static
/*  17:    */   {
/*  18: 98 */     hbmOptionToCascadeType = new HashMap();
/*  19:    */     
/*  20:    */ 
/*  21:101 */     hbmOptionToCascadeType.put("all", ALL);
/*  22:102 */     hbmOptionToCascadeType.put("all-delete-orphan", ALL_DELETE_ORPHAN);
/*  23:103 */     hbmOptionToCascadeType.put("save-update", UPDATE);
/*  24:104 */     hbmOptionToCascadeType.put("persist", PERSIST);
/*  25:105 */     hbmOptionToCascadeType.put("merge", MERGE);
/*  26:106 */     hbmOptionToCascadeType.put("lock", LOCK);
/*  27:107 */     hbmOptionToCascadeType.put("refresh", REFRESH);
/*  28:108 */     hbmOptionToCascadeType.put("replicate", REPLICATE);
/*  29:109 */     hbmOptionToCascadeType.put("evict", EVICT);
/*  30:110 */     hbmOptionToCascadeType.put("delete", DELETE);
/*  31:111 */     hbmOptionToCascadeType.put("remove", DELETE);
/*  32:112 */     hbmOptionToCascadeType.put("delete-orphan", DELETE_ORPHAN);
/*  33:113 */     hbmOptionToCascadeType.put("none", NONE);
/*  34:    */     
/*  35:    */ 
/*  36:116 */     jpaCascadeTypeToHibernateCascadeType = new HashMap();
/*  37:    */     
/*  38:    */ 
/*  39:119 */     jpaCascadeTypeToHibernateCascadeType.put(javax.persistence.CascadeType.ALL, ALL);
/*  40:120 */     jpaCascadeTypeToHibernateCascadeType.put(javax.persistence.CascadeType.PERSIST, PERSIST);
/*  41:121 */     jpaCascadeTypeToHibernateCascadeType.put(javax.persistence.CascadeType.MERGE, MERGE);
/*  42:122 */     jpaCascadeTypeToHibernateCascadeType.put(javax.persistence.CascadeType.REFRESH, REFRESH);
/*  43:123 */     jpaCascadeTypeToHibernateCascadeType.put(javax.persistence.CascadeType.DETACH, EVICT);
/*  44:    */     
/*  45:    */ 
/*  46:126 */     cascadeTypeToCascadeStyle = new HashMap();
/*  47:    */     
/*  48:128 */     cascadeTypeToCascadeStyle.put(ALL, CascadeStyle.ALL);
/*  49:129 */     cascadeTypeToCascadeStyle.put(ALL_DELETE_ORPHAN, CascadeStyle.ALL_DELETE_ORPHAN);
/*  50:130 */     cascadeTypeToCascadeStyle.put(UPDATE, CascadeStyle.UPDATE);
/*  51:131 */     cascadeTypeToCascadeStyle.put(PERSIST, CascadeStyle.PERSIST);
/*  52:132 */     cascadeTypeToCascadeStyle.put(MERGE, CascadeStyle.MERGE);
/*  53:133 */     cascadeTypeToCascadeStyle.put(LOCK, CascadeStyle.LOCK);
/*  54:134 */     cascadeTypeToCascadeStyle.put(REFRESH, CascadeStyle.REFRESH);
/*  55:135 */     cascadeTypeToCascadeStyle.put(REPLICATE, CascadeStyle.REPLICATE);
/*  56:136 */     cascadeTypeToCascadeStyle.put(EVICT, CascadeStyle.EVICT);
/*  57:137 */     cascadeTypeToCascadeStyle.put(DELETE, CascadeStyle.DELETE);
/*  58:138 */     cascadeTypeToCascadeStyle.put(DELETE_ORPHAN, CascadeStyle.DELETE_ORPHAN);
/*  59:139 */     cascadeTypeToCascadeStyle.put(NONE, CascadeStyle.NONE);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static CascadeType getCascadeType(String hbmOptionName)
/*  63:    */   {
/*  64:148 */     return (CascadeType)hbmOptionToCascadeType.get(hbmOptionName);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static CascadeType getCascadeType(javax.persistence.CascadeType jpaCascade)
/*  68:    */   {
/*  69:157 */     return (CascadeType)jpaCascadeTypeToHibernateCascadeType.get(jpaCascade);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public CascadeStyle toCascadeStyle()
/*  73:    */   {
/*  74:166 */     CascadeStyle cascadeStyle = (CascadeStyle)cascadeTypeToCascadeStyle.get(this);
/*  75:167 */     if (cascadeStyle == null) {
/*  76:168 */       throw new MappingException("No CascadeStyle that corresponds with CascadeType=" + name());
/*  77:    */     }
/*  78:170 */     return cascadeStyle;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private CascadeType() {}
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.CascadeType
 * JD-Core Version:    0.7.0.1
 */