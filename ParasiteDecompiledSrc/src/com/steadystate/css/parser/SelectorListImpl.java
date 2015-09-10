/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import org.w3c.css.sac.Selector;
/*  7:   */ import org.w3c.css.sac.SelectorList;
/*  8:   */ 
/*  9:   */ public class SelectorListImpl
/* 10:   */   implements SelectorList, Serializable
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 7313376916207026333L;
/* 13:44 */   private List<Selector> selectors = new ArrayList(10);
/* 14:   */   
/* 15:   */   public List<Selector> getSelectors()
/* 16:   */   {
/* 17:48 */     return this.selectors;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setSelectors(List<Selector> selectors)
/* 21:   */   {
/* 22:53 */     this.selectors = selectors;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getLength()
/* 26:   */   {
/* 27:57 */     return this.selectors.size();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Selector item(int index)
/* 31:   */   {
/* 32:61 */     return (Selector)this.selectors.get(index);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void add(Selector sel)
/* 36:   */   {
/* 37:65 */     this.selectors.add(sel);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String toString()
/* 41:   */   {
/* 42:69 */     StringBuilder sb = new StringBuilder();
/* 43:70 */     int len = getLength();
/* 44:71 */     for (int i = 0; i < len; i++)
/* 45:   */     {
/* 46:72 */       sb.append(item(i).toString());
/* 47:73 */       if (i < len - 1) {
/* 48:74 */         sb.append(", ");
/* 49:   */       }
/* 50:   */     }
/* 51:77 */     return sb.toString();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SelectorListImpl
 * JD-Core Version:    0.7.0.1
 */