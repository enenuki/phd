/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Writer;
/*  5:   */ import org.dom4j.Element;
/*  6:   */ import org.dom4j.Entity;
/*  7:   */ import org.dom4j.Visitor;
/*  8:   */ 
/*  9:   */ public abstract class AbstractEntity
/* 10:   */   extends AbstractNode
/* 11:   */   implements Entity
/* 12:   */ {
/* 13:   */   public short getNodeType()
/* 14:   */   {
/* 15:31 */     return 5;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getPath(Element context)
/* 19:   */   {
/* 20:36 */     Element parent = getParent();
/* 21:   */     
/* 22:38 */     return (parent != null) && (parent != context) ? parent.getPath(context) + "/text()" : "text()";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getUniquePath(Element context)
/* 26:   */   {
/* 27:44 */     Element parent = getParent();
/* 28:   */     
/* 29:46 */     return (parent != null) && (parent != context) ? parent.getUniquePath(context) + "/text()" : "text()";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toString()
/* 33:   */   {
/* 34:51 */     return super.toString() + " [Entity: &" + getName() + ";]";
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getStringValue()
/* 38:   */   {
/* 39:55 */     return "&" + getName() + ";";
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String asXML()
/* 43:   */   {
/* 44:59 */     return "&" + getName() + ";";
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void write(Writer writer)
/* 48:   */     throws IOException
/* 49:   */   {
/* 50:63 */     writer.write("&");
/* 51:64 */     writer.write(getName());
/* 52:65 */     writer.write(";");
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void accept(Visitor visitor)
/* 56:   */   {
/* 57:69 */     visitor.visit(this);
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractEntity
 * JD-Core Version:    0.7.0.1
 */