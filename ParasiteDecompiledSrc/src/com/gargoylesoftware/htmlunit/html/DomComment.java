/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.io.PrintWriter;
/*  5:   */ import org.w3c.dom.Comment;
/*  6:   */ 
/*  7:   */ public class DomComment
/*  8:   */   extends DomCharacterData
/*  9:   */   implements Comment
/* 10:   */ {
/* 11:   */   public static final String NODE_NAME = "#comment";
/* 12:   */   
/* 13:   */   public DomComment(SgmlPage page, String data)
/* 14:   */   {
/* 15:42 */     super(page, data);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public short getNodeType()
/* 19:   */   {
/* 20:51 */     return 8;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getNodeName()
/* 24:   */   {
/* 25:59 */     return "#comment";
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected void printXml(String indent, PrintWriter printWriter)
/* 29:   */   {
/* 30:70 */     printWriter.print(indent);
/* 31:71 */     printWriter.print("<!--");
/* 32:72 */     printWriter.print(getData());
/* 33:73 */     printWriter.print("-->");
/* 34:74 */     printChildrenAsXml(indent, printWriter);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String toString()
/* 38:   */   {
/* 39:83 */     return asXml();
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomComment
 * JD-Core Version:    0.7.0.1
 */