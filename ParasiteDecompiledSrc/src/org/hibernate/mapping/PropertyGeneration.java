/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class PropertyGeneration
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:38 */   public static final PropertyGeneration NEVER = new PropertyGeneration("never");
/*  9:42 */   public static final PropertyGeneration INSERT = new PropertyGeneration("insert");
/* 10:46 */   public static final PropertyGeneration ALWAYS = new PropertyGeneration("always");
/* 11:   */   private final String name;
/* 12:   */   
/* 13:   */   private PropertyGeneration(String name)
/* 14:   */   {
/* 15:51 */     this.name = name;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:55 */     return this.name;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static PropertyGeneration parse(String name)
/* 24:   */   {
/* 25:59 */     if ("insert".equalsIgnoreCase(name)) {
/* 26:60 */       return INSERT;
/* 27:   */     }
/* 28:62 */     if ("always".equalsIgnoreCase(name)) {
/* 29:63 */       return ALWAYS;
/* 30:   */     }
/* 31:66 */     return NEVER;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private Object readResolve()
/* 35:   */   {
/* 36:71 */     return parse(this.name);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String toString()
/* 40:   */   {
/* 41:75 */     return getClass().getName() + "(" + getName() + ")";
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.PropertyGeneration
 * JD-Core Version:    0.7.0.1
 */