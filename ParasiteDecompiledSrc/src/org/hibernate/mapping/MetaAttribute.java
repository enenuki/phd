/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Collections;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class MetaAttribute
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private String name;
/* 12:35 */   private List values = new ArrayList();
/* 13:   */   
/* 14:   */   public MetaAttribute(String name)
/* 15:   */   {
/* 16:38 */     this.name = name;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:42 */     return this.name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List getValues()
/* 25:   */   {
/* 26:46 */     return Collections.unmodifiableList(this.values);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void addValue(String value)
/* 30:   */   {
/* 31:50 */     this.values.add(value);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getValue()
/* 35:   */   {
/* 36:54 */     if (this.values.size() != 1) {
/* 37:55 */       throw new IllegalStateException("no unique value");
/* 38:   */     }
/* 39:57 */     return (String)this.values.get(0);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isMultiValued()
/* 43:   */   {
/* 44:61 */     return this.values.size() > 1;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:65 */     return "[" + this.name + "=" + this.values + "]";
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.MetaAttribute
 * JD-Core Version:    0.7.0.1
 */