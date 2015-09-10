/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import org.dom4j.CharacterData;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ 
/*  6:   */ public abstract class AbstractCharacterData
/*  7:   */   extends AbstractNode
/*  8:   */   implements CharacterData
/*  9:   */ {
/* 10:   */   public String getPath(Element context)
/* 11:   */   {
/* 12:28 */     Element parent = getParent();
/* 13:   */     
/* 14:30 */     return (parent != null) && (parent != context) ? parent.getPath(context) + "/text()" : "text()";
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getUniquePath(Element context)
/* 18:   */   {
/* 19:35 */     Element parent = getParent();
/* 20:   */     
/* 21:37 */     return (parent != null) && (parent != context) ? parent.getUniquePath(context) + "/text()" : "text()";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void appendText(String text)
/* 25:   */   {
/* 26:42 */     setText(getText() + text);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractCharacterData
 * JD-Core Version:    0.7.0.1
 */