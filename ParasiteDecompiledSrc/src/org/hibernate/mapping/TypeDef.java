/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ 
/*  6:   */ public class TypeDef
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private String typeClass;
/* 10:   */   private Properties parameters;
/* 11:   */   
/* 12:   */   public TypeDef(String typeClass, Properties parameters)
/* 13:   */   {
/* 14:37 */     this.typeClass = typeClass;
/* 15:38 */     this.parameters = parameters;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Properties getParameters()
/* 19:   */   {
/* 20:42 */     return this.parameters;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getTypeClass()
/* 24:   */   {
/* 25:45 */     return this.typeClass;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.TypeDef
 * JD-Core Version:    0.7.0.1
 */