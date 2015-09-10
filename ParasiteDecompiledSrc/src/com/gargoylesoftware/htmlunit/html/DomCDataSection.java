/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.io.PrintWriter;
/*  5:   */ import org.w3c.dom.CDATASection;
/*  6:   */ 
/*  7:   */ public class DomCDataSection
/*  8:   */   extends DomText
/*  9:   */   implements CDATASection
/* 10:   */ {
/* 11:   */   public static final String NODE_NAME = "#cdata-section";
/* 12:   */   
/* 13:   */   public DomCDataSection(SgmlPage page, String data)
/* 14:   */   {
/* 15:42 */     super(page, data);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public short getNodeType()
/* 19:   */   {
/* 20:50 */     return 4;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getNodeName()
/* 24:   */   {
/* 25:58 */     return "#cdata-section";
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected void printXml(String indent, PrintWriter printWriter)
/* 29:   */   {
/* 30:66 */     printWriter.print("<![CDATA[");
/* 31:67 */     printWriter.print(getData());
/* 32:68 */     printWriter.print("]]>");
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected DomText createSplitTextNode(int offset)
/* 36:   */   {
/* 37:76 */     return new DomCDataSection(getPage(), getData().substring(offset));
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomCDataSection
 * JD-Core Version:    0.7.0.1
 */