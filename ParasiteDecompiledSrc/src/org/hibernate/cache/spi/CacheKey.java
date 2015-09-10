/*  1:   */ package org.hibernate.cache.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.internal.util.compare.EqualsHelper;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class CacheKey
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final Serializable key;
/* 12:   */   private final Type type;
/* 13:   */   private final String entityOrRoleName;
/* 14:   */   private final String tenantId;
/* 15:   */   private final int hashCode;
/* 16:   */   
/* 17:   */   public CacheKey(Serializable id, Type type, String entityOrRoleName, String tenantId, SessionFactoryImplementor factory)
/* 18:   */   {
/* 19:63 */     this.key = id;
/* 20:64 */     this.type = type;
/* 21:65 */     this.entityOrRoleName = entityOrRoleName;
/* 22:66 */     this.tenantId = tenantId;
/* 23:67 */     this.hashCode = type.getHashCode(this.key, factory);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:73 */     return this.entityOrRoleName + '#' + this.key.toString();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean equals(Object other)
/* 32:   */   {
/* 33:78 */     if (!(other instanceof CacheKey)) {
/* 34:79 */       return false;
/* 35:   */     }
/* 36:81 */     CacheKey that = (CacheKey)other;
/* 37:82 */     return (this.entityOrRoleName.equals(that.entityOrRoleName)) && (this.type.isEqual(this.key, that.key)) && (EqualsHelper.equals(this.tenantId, that.tenantId));
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int hashCode()
/* 41:   */   {
/* 42:89 */     return this.hashCode;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Serializable getKey()
/* 46:   */   {
/* 47:93 */     return this.key;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getEntityOrRoleName()
/* 51:   */   {
/* 52:97 */     return this.entityOrRoleName;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.CacheKey
 * JD-Core Version:    0.7.0.1
 */