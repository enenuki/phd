/*  1:   */ package org.hibernate.engine.profile;
/*  2:   */ 
/*  3:   */ public class Fetch
/*  4:   */ {
/*  5:   */   private final Association association;
/*  6:   */   private final Style style;
/*  7:   */   
/*  8:   */   public Fetch(Association association, Style style)
/*  9:   */   {
/* 10:37 */     this.association = association;
/* 11:38 */     this.style = style;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Association getAssociation()
/* 15:   */   {
/* 16:42 */     return this.association;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Style getStyle()
/* 20:   */   {
/* 21:46 */     return this.style;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static enum Style
/* 25:   */   {
/* 26:57 */     JOIN("join"),  SELECT("select");
/* 27:   */     
/* 28:   */     private final String name;
/* 29:   */     
/* 30:   */     private Style(String name)
/* 31:   */     {
/* 32:63 */       this.name = name;
/* 33:   */     }
/* 34:   */     
/* 35:   */     public String toString()
/* 36:   */     {
/* 37:67 */       return this.name;
/* 38:   */     }
/* 39:   */     
/* 40:   */     public static Style parse(String name)
/* 41:   */     {
/* 42:71 */       if (SELECT.name.equals(name)) {
/* 43:72 */         return SELECT;
/* 44:   */       }
/* 45:76 */       return JOIN;
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:82 */     return "Fetch[" + this.style + "{" + this.association.getRole() + "}]";
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.profile.Fetch
 * JD-Core Version:    0.7.0.1
 */