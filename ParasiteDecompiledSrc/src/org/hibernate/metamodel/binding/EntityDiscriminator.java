/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.relational.SimpleValue;
/*  4:   */ 
/*  5:   */ public class EntityDiscriminator
/*  6:   */ {
/*  7:35 */   private final HibernateTypeDescriptor explicitHibernateTypeDescriptor = new HibernateTypeDescriptor();
/*  8:   */   private SimpleValue boundValue;
/*  9:   */   private boolean forced;
/* 10:39 */   private boolean inserted = true;
/* 11:   */   
/* 12:   */   public SimpleValue getBoundValue()
/* 13:   */   {
/* 14:45 */     return this.boundValue;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setBoundValue(SimpleValue boundValue)
/* 18:   */   {
/* 19:49 */     this.boundValue = boundValue;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public HibernateTypeDescriptor getExplicitHibernateTypeDescriptor()
/* 23:   */   {
/* 24:53 */     return this.explicitHibernateTypeDescriptor;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isForced()
/* 28:   */   {
/* 29:57 */     return this.forced;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setForced(boolean forced)
/* 33:   */   {
/* 34:61 */     this.forced = forced;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean isInserted()
/* 38:   */   {
/* 39:65 */     return this.inserted;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setInserted(boolean inserted)
/* 43:   */   {
/* 44:69 */     this.inserted = inserted;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:74 */     StringBuilder sb = new StringBuilder();
/* 50:75 */     sb.append("EntityDiscriminator");
/* 51:76 */     sb.append("{boundValue=").append(this.boundValue);
/* 52:77 */     sb.append(", forced=").append(this.forced);
/* 53:78 */     sb.append(", inserted=").append(this.inserted);
/* 54:79 */     sb.append('}');
/* 55:80 */     return sb.toString();
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.EntityDiscriminator
 * JD-Core Version:    0.7.0.1
 */