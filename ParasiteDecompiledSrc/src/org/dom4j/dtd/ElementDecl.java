/*  1:   */ package org.dom4j.dtd;
/*  2:   */ 
/*  3:   */ public class ElementDecl
/*  4:   */ {
/*  5:   */   private String name;
/*  6:   */   private String model;
/*  7:   */   
/*  8:   */   public ElementDecl() {}
/*  9:   */   
/* 10:   */   public ElementDecl(String name, String model)
/* 11:   */   {
/* 12:29 */     this.name = name;
/* 13:30 */     this.model = model;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:39 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setName(String name)
/* 22:   */   {
/* 23:49 */     this.name = name;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getModel()
/* 27:   */   {
/* 28:58 */     return this.model;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setModel(String model)
/* 32:   */   {
/* 33:68 */     this.model = model;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:72 */     return "<!ELEMENT " + this.name + " " + this.model + ">";
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dtd.ElementDecl
 * JD-Core Version:    0.7.0.1
 */