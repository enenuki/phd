/*   1:    */ package org.hibernate.internal.jaxb.cfg;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlEnum;
/*   4:    */ import javax.xml.bind.annotation.XmlEnumValue;
/*   5:    */ import javax.xml.bind.annotation.XmlType;
/*   6:    */ 
/*   7:    */ @XmlType(name="type-attribute")
/*   8:    */ @XmlEnum
/*   9:    */ public enum JaxbTypeAttribute
/*  10:    */ {
/*  11: 67 */   AUTO_FLUSH("auto-flush"),  CREATE("create"),  CREATE_ONFLUSH("create-onflush"),  DELETE("delete"),  DIRTY_CHECK("dirty-check"),  EVICT("evict"),  FLUSH("flush"),  FLUSH_ENTITY("flush-entity"),  LOAD("load"),  LOAD_COLLECTION("load-collection"),  LOCK("lock"),  MERGE("merge"),  POST_COLLECTION_RECREATE("post-collection-recreate"),  POST_COLLECTION_REMOVE("post-collection-remove"),  POST_COLLECTION_UPDATE("post-collection-update"),  POST_COMMIT_DELETE("post-commit-delete"),  POST_COMMIT_INSERT("post-commit-insert"),  POST_COMMIT_UPDATE("post-commit-update"),  POST_DELETE("post-delete"),  POST_INSERT("post-insert"),  POST_LOAD("post-load"),  POST_UPDATE("post-update"),  PRE_COLLECTION_RECREATE("pre-collection-recreate"),  PRE_COLLECTION_REMOVE("pre-collection-remove"),  PRE_COLLECTION_UPDATE("pre-collection-update"),  PRE_DELETE("pre-delete"),  PRE_INSERT("pre-insert"),  PRE_LOAD("pre-load"),  PRE_UPDATE("pre-update"),  REFRESH("refresh"),  REPLICATE("replicate"),  SAVE("save"),  SAVE_UPDATE("save-update"),  UPDATE("update");
/*  12:    */   
/*  13:    */   private final String value;
/*  14:    */   
/*  15:    */   private JaxbTypeAttribute(String v)
/*  16:    */   {
/*  17:138 */     this.value = v;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String value()
/*  21:    */   {
/*  22:142 */     return this.value;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static JaxbTypeAttribute fromValue(String v)
/*  26:    */   {
/*  27:146 */     for (JaxbTypeAttribute c : ) {
/*  28:147 */       if (c.value.equals(v)) {
/*  29:148 */         return c;
/*  30:    */       }
/*  31:    */     }
/*  32:151 */     throw new IllegalArgumentException(v);
/*  33:    */   }
/*  34:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.cfg.JaxbTypeAttribute
 * JD-Core Version:    0.7.0.1
 */