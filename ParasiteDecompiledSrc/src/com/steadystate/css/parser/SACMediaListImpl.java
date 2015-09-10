/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.w3c.css.sac.SACMediaList;
/*  6:   */ 
/*  7:   */ public class SACMediaListImpl
/*  8:   */   implements SACMediaList
/*  9:   */ {
/* 10:41 */   private List<String> _selectors = new ArrayList(10);
/* 11:   */   
/* 12:   */   public int getLength()
/* 13:   */   {
/* 14:44 */     return this._selectors.size();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String item(int index)
/* 18:   */   {
/* 19:48 */     return (String)this._selectors.get(index);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void add(String s)
/* 23:   */   {
/* 24:52 */     this._selectors.add(s);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:56 */     StringBuilder sb = new StringBuilder();
/* 30:57 */     int len = getLength();
/* 31:58 */     for (int i = 0; i < len; i++)
/* 32:   */     {
/* 33:59 */       sb.append(item(i));
/* 34:60 */       if (i < len - 1) {
/* 35:61 */         sb.append(", ");
/* 36:   */       }
/* 37:   */     }
/* 38:64 */     return sb.toString();
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACMediaListImpl
 * JD-Core Version:    0.7.0.1
 */