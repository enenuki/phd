/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.ProcessingInstruction;
/*   7:    */ 
/*   8:    */ public class DomProcessingInstruction
/*   9:    */   extends DomNode
/*  10:    */   implements ProcessingInstruction
/*  11:    */ {
/*  12:    */   private final String target_;
/*  13:    */   private String data_;
/*  14:    */   
/*  15:    */   public DomProcessingInstruction(SgmlPage page, String target, String data)
/*  16:    */   {
/*  17: 43 */     super(page);
/*  18: 44 */     this.target_ = target;
/*  19: 45 */     setData(data);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public short getNodeType()
/*  23:    */   {
/*  24: 54 */     return 7;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getNodeName()
/*  28:    */   {
/*  29: 62 */     return this.target_;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getTarget()
/*  33:    */   {
/*  34: 69 */     return getNodeName();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getData()
/*  38:    */   {
/*  39: 76 */     return getNodeValue();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setData(String data)
/*  43:    */     throws DOMException
/*  44:    */   {
/*  45: 83 */     setNodeValue(data);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setNodeValue(String value)
/*  49:    */   {
/*  50: 91 */     this.data_ = value;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getNodeValue()
/*  54:    */   {
/*  55: 99 */     return this.data_;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setTextContent(String textContent)
/*  59:    */   {
/*  60:107 */     setNodeValue(textContent);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void printXml(String indent, PrintWriter printWriter)
/*  64:    */   {
/*  65:115 */     printWriter.print("<?");
/*  66:116 */     printWriter.print(getTarget());
/*  67:117 */     printWriter.print(" ");
/*  68:118 */     printWriter.print(getData());
/*  69:119 */     printWriter.print("?>");
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */