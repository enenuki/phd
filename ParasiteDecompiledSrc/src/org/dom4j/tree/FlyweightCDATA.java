/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import org.dom4j.CDATA;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.dom4j.Node;
/*  6:   */ 
/*  7:   */ public class FlyweightCDATA
/*  8:   */   extends AbstractCDATA
/*  9:   */   implements CDATA
/* 10:   */ {
/* 11:   */   protected String text;
/* 12:   */   
/* 13:   */   public FlyweightCDATA(String text)
/* 14:   */   {
/* 15:39 */     this.text = text;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getText()
/* 19:   */   {
/* 20:43 */     return this.text;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected Node createXPathResult(Element parent)
/* 24:   */   {
/* 25:47 */     return new DefaultCDATA(parent, getText());
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.FlyweightCDATA
 * JD-Core Version:    0.7.0.1
 */