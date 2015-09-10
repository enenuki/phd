/*  1:   */ package org.hibernate.engine.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public final class AssociationKey
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private EntityKey ownerKey;
/*  9:   */   private String propertyName;
/* 10:   */   
/* 11:   */   public AssociationKey(EntityKey ownerKey, String propertyName)
/* 12:   */   {
/* 13:40 */     this.ownerKey = ownerKey;
/* 14:41 */     this.propertyName = propertyName;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean equals(Object that)
/* 18:   */   {
/* 19:45 */     AssociationKey key = (AssociationKey)that;
/* 20:46 */     return (key.propertyName.equals(this.propertyName)) && (key.ownerKey.equals(this.ownerKey));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int hashCode()
/* 24:   */   {
/* 25:51 */     return this.ownerKey.hashCode() + this.propertyName.hashCode();
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.AssociationKey
 * JD-Core Version:    0.7.0.1
 */