/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import org.dom4j.Element;
/*  4:   */ import org.dom4j.Node;
/*  5:   */ import org.dom4j.Text;
/*  6:   */ 
/*  7:   */ public class FlyweightText
/*  8:   */   extends AbstractText
/*  9:   */   implements Text
/* 10:   */ {
/* 11:   */   protected String text;
/* 12:   */   
/* 13:   */   public FlyweightText(String text)
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
/* 25:47 */     return new DefaultText(parent, getText());
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.FlyweightText
 * JD-Core Version:    0.7.0.1
 */