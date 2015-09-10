/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ 
/*  6:   */ public class IdGenerator
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private String name;
/* 10:   */   private String identifierGeneratorStrategy;
/* 11:37 */   private Properties params = new Properties();
/* 12:   */   
/* 13:   */   public String getIdentifierGeneratorStrategy()
/* 14:   */   {
/* 15:44 */     return this.identifierGeneratorStrategy;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:51 */     return this.name;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Properties getParams()
/* 24:   */   {
/* 25:58 */     return this.params;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdentifierGeneratorStrategy(String string)
/* 29:   */   {
/* 30:62 */     this.identifierGeneratorStrategy = string;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setName(String string)
/* 34:   */   {
/* 35:66 */     this.name = string;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void addParam(String key, String value)
/* 39:   */   {
/* 40:70 */     this.params.setProperty(key, value);
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.IdGenerator
 * JD-Core Version:    0.7.0.1
 */