/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Writer;
/*  5:   */ import org.dom4j.Comment;
/*  6:   */ import org.dom4j.Element;
/*  7:   */ import org.dom4j.Visitor;
/*  8:   */ 
/*  9:   */ public abstract class AbstractComment
/* 10:   */   extends AbstractCharacterData
/* 11:   */   implements Comment
/* 12:   */ {
/* 13:   */   public short getNodeType()
/* 14:   */   {
/* 15:32 */     return 8;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getPath(Element context)
/* 19:   */   {
/* 20:36 */     Element parent = getParent();
/* 21:   */     
/* 22:38 */     return (parent != null) && (parent != context) ? parent.getPath(context) + "/comment()" : "comment()";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getUniquePath(Element context)
/* 26:   */   {
/* 27:43 */     Element parent = getParent();
/* 28:   */     
/* 29:45 */     return (parent != null) && (parent != context) ? parent.getUniquePath(context) + "/comment()" : "comment()";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toString()
/* 33:   */   {
/* 34:50 */     return super.toString() + " [Comment: \"" + getText() + "\"]";
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String asXML()
/* 38:   */   {
/* 39:54 */     return "<!--" + getText() + "-->";
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void write(Writer writer)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:58 */     writer.write("<!--");
/* 46:59 */     writer.write(getText());
/* 47:60 */     writer.write("-->");
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void accept(Visitor visitor)
/* 51:   */   {
/* 52:64 */     visitor.visit(this);
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractComment
 * JD-Core Version:    0.7.0.1
 */