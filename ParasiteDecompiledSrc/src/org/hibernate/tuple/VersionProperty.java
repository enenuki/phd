/*  1:   */ package org.hibernate.tuple;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.CascadeStyle;
/*  4:   */ import org.hibernate.engine.spi.VersionValue;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class VersionProperty
/*  8:   */   extends StandardProperty
/*  9:   */ {
/* 10:   */   private final VersionValue unsavedValue;
/* 11:   */   
/* 12:   */   public VersionProperty(String name, String node, Type type, boolean lazy, boolean insertable, boolean updateable, boolean insertGenerated, boolean updateGenerated, boolean nullable, boolean checkable, boolean versionable, CascadeStyle cascadeStyle, VersionValue unsavedValue)
/* 13:   */   {
/* 14:74 */     super(name, node, type, lazy, insertable, updateable, insertGenerated, updateGenerated, nullable, checkable, versionable, cascadeStyle, null);
/* 15:75 */     this.unsavedValue = unsavedValue;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public VersionValue getUnsavedValue()
/* 19:   */   {
/* 20:79 */     return this.unsavedValue;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.VersionProperty
 * JD-Core Version:    0.7.0.1
 */