/*  1:   */ package org.hibernate.internal.util;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class MarkerObject
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private String name;
/*  9:   */   
/* 10:   */   public MarkerObject(String name)
/* 11:   */   {
/* 12:36 */     this.name = name;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString()
/* 16:   */   {
/* 17:40 */     return this.name;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.MarkerObject
 * JD-Core Version:    0.7.0.1
 */