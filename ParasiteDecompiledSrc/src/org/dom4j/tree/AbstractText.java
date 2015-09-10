/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Writer;
/*  5:   */ import org.dom4j.Text;
/*  6:   */ import org.dom4j.Visitor;
/*  7:   */ 
/*  8:   */ public abstract class AbstractText
/*  9:   */   extends AbstractCharacterData
/* 10:   */   implements Text
/* 11:   */ {
/* 12:   */   public short getNodeType()
/* 13:   */   {
/* 14:30 */     return 3;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String toString()
/* 18:   */   {
/* 19:34 */     return super.toString() + " [Text: \"" + getText() + "\"]";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String asXML()
/* 23:   */   {
/* 24:38 */     return getText();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void write(Writer writer)
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:42 */     writer.write(getText());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void accept(Visitor visitor)
/* 34:   */   {
/* 35:46 */     visitor.visit(this);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractText
 * JD-Core Version:    0.7.0.1
 */