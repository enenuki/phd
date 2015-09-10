/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import org.dom4j.Element;
/*  4:   */ 
/*  5:   */ public class DefaultText
/*  6:   */   extends FlyweightText
/*  7:   */ {
/*  8:   */   private Element parent;
/*  9:   */   
/* 10:   */   public DefaultText(String text)
/* 11:   */   {
/* 12:33 */     super(text);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DefaultText(Element parent, String text)
/* 16:   */   {
/* 17:45 */     super(text);
/* 18:46 */     this.parent = parent;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setText(String text)
/* 22:   */   {
/* 23:50 */     this.text = text;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Element getParent()
/* 27:   */   {
/* 28:54 */     return this.parent;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setParent(Element parent)
/* 32:   */   {
/* 33:58 */     this.parent = parent;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean supportsParent()
/* 37:   */   {
/* 38:62 */     return true;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean isReadOnly()
/* 42:   */   {
/* 43:66 */     return false;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultText
 * JD-Core Version:    0.7.0.1
 */