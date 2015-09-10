/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.StringWriter;
/*  5:   */ import java.io.Writer;
/*  6:   */ import org.dom4j.CDATA;
/*  7:   */ import org.dom4j.Visitor;
/*  8:   */ 
/*  9:   */ public abstract class AbstractCDATA
/* 10:   */   extends AbstractCharacterData
/* 11:   */   implements CDATA
/* 12:   */ {
/* 13:   */   public short getNodeType()
/* 14:   */   {
/* 15:32 */     return 4;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toString()
/* 19:   */   {
/* 20:36 */     return super.toString() + " [CDATA: \"" + getText() + "\"]";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String asXML()
/* 24:   */   {
/* 25:40 */     StringWriter writer = new StringWriter();
/* 26:   */     try
/* 27:   */     {
/* 28:43 */       write(writer);
/* 29:   */     }
/* 30:   */     catch (IOException e) {}
/* 31:48 */     return writer.toString();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void write(Writer writer)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:52 */     writer.write("<![CDATA[");
/* 38:54 */     if (getText() != null) {
/* 39:55 */       writer.write(getText());
/* 40:   */     }
/* 41:58 */     writer.write("]]>");
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void accept(Visitor visitor)
/* 45:   */   {
/* 46:62 */     visitor.visit(this);
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractCDATA
 * JD-Core Version:    0.7.0.1
 */