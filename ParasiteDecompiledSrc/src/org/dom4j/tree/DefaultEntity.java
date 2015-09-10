/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import org.dom4j.Element;
/*  4:   */ 
/*  5:   */ public class DefaultEntity
/*  6:   */   extends FlyweightEntity
/*  7:   */ {
/*  8:   */   private Element parent;
/*  9:   */   
/* 10:   */   public DefaultEntity(String name)
/* 11:   */   {
/* 12:33 */     super(name);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DefaultEntity(String name, String text)
/* 16:   */   {
/* 17:45 */     super(name, text);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public DefaultEntity(Element parent, String name, String text)
/* 21:   */   {
/* 22:59 */     super(name, text);
/* 23:60 */     this.parent = parent;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setName(String name)
/* 27:   */   {
/* 28:64 */     this.name = name;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setText(String text)
/* 32:   */   {
/* 33:68 */     this.text = text;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Element getParent()
/* 37:   */   {
/* 38:72 */     return this.parent;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setParent(Element parent)
/* 42:   */   {
/* 43:76 */     this.parent = parent;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean supportsParent()
/* 47:   */   {
/* 48:80 */     return true;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean isReadOnly()
/* 52:   */   {
/* 53:84 */     return false;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultEntity
 * JD-Core Version:    0.7.0.1
 */