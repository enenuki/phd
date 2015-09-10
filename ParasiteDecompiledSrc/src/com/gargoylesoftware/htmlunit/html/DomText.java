/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.Text;
/*   7:    */ 
/*   8:    */ public class DomText
/*   9:    */   extends DomCharacterData
/*  10:    */   implements Text
/*  11:    */ {
/*  12:    */   public static final String NODE_NAME = "#text";
/*  13:    */   
/*  14:    */   public DomText(SgmlPage page, String data)
/*  15:    */   {
/*  16: 48 */     super(page, data);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public DomText splitText(int offset)
/*  20:    */   {
/*  21: 55 */     if ((offset < 0) || (offset > getLength())) {
/*  22: 56 */       throw new IllegalArgumentException("offset: " + offset + " data.length: " + getLength());
/*  23:    */     }
/*  24: 60 */     DomText newText = createSplitTextNode(offset);
/*  25: 61 */     setData(getData().substring(0, offset));
/*  26: 64 */     if (getParentNode() != null) {
/*  27: 65 */       getParentNode().insertBefore(newText, getNextSibling());
/*  28:    */     }
/*  29: 67 */     return newText;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected DomText createSplitTextNode(int offset)
/*  33:    */   {
/*  34: 78 */     return new DomText(getPage(), getData().substring(offset));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean isElementContentWhitespace()
/*  38:    */   {
/*  39: 86 */     throw new UnsupportedOperationException("DomText.isElementContentWhitespace is not yet implemented.");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getWholeText()
/*  43:    */   {
/*  44: 94 */     throw new UnsupportedOperationException("DomText.getWholeText is not yet implemented.");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Text replaceWholeText(String content)
/*  48:    */     throws DOMException
/*  49:    */   {
/*  50:102 */     throw new UnsupportedOperationException("DomText.replaceWholeText is not yet implemented.");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public short getNodeType()
/*  54:    */   {
/*  55:110 */     return 3;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getNodeName()
/*  59:    */   {
/*  60:118 */     return "#text";
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void printXml(String indent, PrintWriter printWriter)
/*  64:    */   {
/*  65:129 */     String data = getData();
/*  66:130 */     if (org.apache.commons.lang.StringUtils.isNotBlank(data))
/*  67:    */     {
/*  68:131 */       printWriter.print(indent);
/*  69:132 */       if ((!(getParentNode() instanceof HtmlStyle)) || (!data.startsWith("<!--")) || (!data.endsWith("-->"))) {
/*  70:133 */         data = com.gargoylesoftware.htmlunit.util.StringUtils.escapeXmlChars(data);
/*  71:    */       }
/*  72:135 */       printWriter.println(data);
/*  73:    */     }
/*  74:137 */     printChildrenAsXml(indent, printWriter);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:146 */     return asText();
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected boolean isTrimmedText()
/*  83:    */   {
/*  84:154 */     return false;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomText
 * JD-Core Version:    0.7.0.1
 */