/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.relational.Column;
/*  4:   */ import org.hibernate.metamodel.relational.DerivedValue;
/*  5:   */ import org.hibernate.metamodel.relational.SimpleValue;
/*  6:   */ 
/*  7:   */ public class SimpleValueBinding
/*  8:   */ {
/*  9:   */   private SimpleValue simpleValue;
/* 10:   */   private boolean includeInInsert;
/* 11:   */   private boolean includeInUpdate;
/* 12:   */   
/* 13:   */   public SimpleValueBinding()
/* 14:   */   {
/* 15:39 */     this(true, true);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public SimpleValueBinding(SimpleValue simpleValue)
/* 19:   */   {
/* 20:43 */     this();
/* 21:44 */     setSimpleValue(simpleValue);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public SimpleValueBinding(SimpleValue simpleValue, boolean includeInInsert, boolean includeInUpdate)
/* 25:   */   {
/* 26:48 */     this(includeInInsert, includeInUpdate);
/* 27:49 */     setSimpleValue(simpleValue);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public SimpleValueBinding(boolean includeInInsert, boolean includeInUpdate)
/* 31:   */   {
/* 32:53 */     this.includeInInsert = includeInInsert;
/* 33:54 */     this.includeInUpdate = includeInUpdate;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public SimpleValue getSimpleValue()
/* 37:   */   {
/* 38:58 */     return this.simpleValue;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setSimpleValue(SimpleValue simpleValue)
/* 42:   */   {
/* 43:62 */     this.simpleValue = simpleValue;
/* 44:63 */     if (DerivedValue.class.isInstance(simpleValue))
/* 45:   */     {
/* 46:64 */       this.includeInInsert = false;
/* 47:65 */       this.includeInUpdate = false;
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean isDerived()
/* 52:   */   {
/* 53:70 */     return DerivedValue.class.isInstance(this.simpleValue);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean isNullable()
/* 57:   */   {
/* 58:74 */     return (isDerived()) || (((Column)Column.class.cast(this.simpleValue)).isNullable());
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean isIncludeInInsert()
/* 62:   */   {
/* 63:86 */     return this.includeInInsert;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setIncludeInInsert(boolean includeInInsert)
/* 67:   */   {
/* 68:90 */     this.includeInInsert = includeInInsert;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean isIncludeInUpdate()
/* 72:   */   {
/* 73:94 */     return this.includeInUpdate;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void setIncludeInUpdate(boolean includeInUpdate)
/* 77:   */   {
/* 78:98 */     this.includeInUpdate = includeInUpdate;
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.SimpleValueBinding
 * JD-Core Version:    0.7.0.1
 */