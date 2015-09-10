/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class TypeDef
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private final String name;
/* 11:   */   private final String typeClass;
/* 12:   */   private final Map<String, String> parameters;
/* 13:   */   
/* 14:   */   public TypeDef(String name, String typeClass, Map<String, String> parameters)
/* 15:   */   {
/* 16:41 */     this.name = name;
/* 17:42 */     this.typeClass = typeClass;
/* 18:43 */     this.parameters = parameters;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getName()
/* 22:   */   {
/* 23:47 */     return this.name;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getTypeClass()
/* 27:   */   {
/* 28:51 */     return this.typeClass;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Map<String, String> getParameters()
/* 32:   */   {
/* 33:55 */     return Collections.unmodifiableMap(this.parameters);
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.TypeDef
 * JD-Core Version:    0.7.0.1
 */