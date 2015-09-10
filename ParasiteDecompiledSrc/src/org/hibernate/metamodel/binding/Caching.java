/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.cache.spi.access.AccessType;
/*  4:   */ 
/*  5:   */ public class Caching
/*  6:   */ {
/*  7:   */   private String region;
/*  8:   */   private AccessType accessType;
/*  9:   */   private boolean cacheLazyProperties;
/* 10:   */   
/* 11:   */   public Caching() {}
/* 12:   */   
/* 13:   */   public Caching(String region, AccessType accessType, boolean cacheLazyProperties)
/* 14:   */   {
/* 15:43 */     this.region = region;
/* 16:44 */     this.accessType = accessType;
/* 17:45 */     this.cacheLazyProperties = cacheLazyProperties;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getRegion()
/* 21:   */   {
/* 22:49 */     return this.region;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setRegion(String region)
/* 26:   */   {
/* 27:53 */     this.region = region;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public AccessType getAccessType()
/* 31:   */   {
/* 32:57 */     return this.accessType;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setAccessType(AccessType accessType)
/* 36:   */   {
/* 37:61 */     this.accessType = accessType;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean isCacheLazyProperties()
/* 41:   */   {
/* 42:65 */     return this.cacheLazyProperties;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setCacheLazyProperties(boolean cacheLazyProperties)
/* 46:   */   {
/* 47:69 */     this.cacheLazyProperties = cacheLazyProperties;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:74 */     StringBuilder sb = new StringBuilder();
/* 53:75 */     sb.append("Caching");
/* 54:76 */     sb.append("{region='").append(this.region).append('\'');
/* 55:77 */     sb.append(", accessType=").append(this.accessType);
/* 56:78 */     sb.append(", cacheLazyProperties=").append(this.cacheLazyProperties);
/* 57:79 */     sb.append('}');
/* 58:80 */     return sb.toString();
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.Caching
 * JD-Core Version:    0.7.0.1
 */