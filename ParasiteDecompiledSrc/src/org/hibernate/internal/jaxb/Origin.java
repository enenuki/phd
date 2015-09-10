/*  1:   */ package org.hibernate.internal.jaxb;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class Origin
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private final SourceType type;
/*  9:   */   private final String name;
/* 10:   */   
/* 11:   */   public Origin(SourceType type, String name)
/* 12:   */   {
/* 13:38 */     this.type = type;
/* 14:39 */     this.name = name;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public SourceType getType()
/* 18:   */   {
/* 19:48 */     return this.type;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:58 */     return this.name;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.Origin
 * JD-Core Version:    0.7.0.1
 */