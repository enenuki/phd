/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Collections;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class MetaAttribute
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final String name;
/* 12:38 */   private List<String> values = new ArrayList();
/* 13:   */   
/* 14:   */   public MetaAttribute(String name)
/* 15:   */   {
/* 16:41 */     this.name = name;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:45 */     return this.name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<String> getValues()
/* 25:   */   {
/* 26:49 */     return Collections.unmodifiableList(this.values);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void addValue(String value)
/* 30:   */   {
/* 31:53 */     this.values.add(value);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getValue()
/* 35:   */   {
/* 36:57 */     if (this.values.size() != 1) {
/* 37:58 */       throw new IllegalStateException("no unique value");
/* 38:   */     }
/* 39:60 */     return (String)this.values.get(0);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isMultiValued()
/* 43:   */   {
/* 44:64 */     return this.values.size() > 1;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:68 */     return "[" + this.name + "=" + this.values + "]";
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.MetaAttribute
 * JD-Core Version:    0.7.0.1
 */