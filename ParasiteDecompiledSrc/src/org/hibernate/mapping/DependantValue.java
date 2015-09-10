/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.cfg.Mappings;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class DependantValue
/*  8:   */   extends SimpleValue
/*  9:   */ {
/* 10:   */   private KeyValue wrappedValue;
/* 11:   */   private boolean nullable;
/* 12:   */   private boolean updateable;
/* 13:   */   
/* 14:   */   public DependantValue(Mappings mappings, Table table, KeyValue prototype)
/* 15:   */   {
/* 16:42 */     super(mappings, table);
/* 17:43 */     this.wrappedValue = prototype;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Type getType()
/* 21:   */     throws MappingException
/* 22:   */   {
/* 23:47 */     return this.wrappedValue.getType();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setTypeUsingReflection(String className, String propertyName) {}
/* 27:   */   
/* 28:   */   public Object accept(ValueVisitor visitor)
/* 29:   */   {
/* 30:53 */     return visitor.accept(this);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean isNullable()
/* 34:   */   {
/* 35:57 */     return this.nullable;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setNullable(boolean nullable)
/* 39:   */   {
/* 40:62 */     this.nullable = nullable;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isUpdateable()
/* 44:   */   {
/* 45:66 */     return this.updateable;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setUpdateable(boolean updateable)
/* 49:   */   {
/* 50:70 */     this.updateable = updateable;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.DependantValue
 * JD-Core Version:    0.7.0.1
 */