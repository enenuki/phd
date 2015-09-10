/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.FastStringBuffer;
/*   6:    */ import org.apache.xml.utils.PrefixResolver;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.XPath;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.XPathFactory;
/*  11:    */ import org.apache.xpath.compiler.XPathParser;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ 
/*  14:    */ public class AVTPartXPath
/*  15:    */   extends AVTPart
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -4460373807550527675L;
/*  18:    */   private XPath m_xpath;
/*  19:    */   
/*  20:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  21:    */   {
/*  22: 56 */     this.m_xpath.fixupVariables(vars, globalsSize);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean canTraverseOutsideSubtree()
/*  26:    */   {
/*  27: 67 */     return this.m_xpath.getExpression().canTraverseOutsideSubtree();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public AVTPartXPath(XPath xpath)
/*  31:    */   {
/*  32: 77 */     this.m_xpath = xpath;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public AVTPartXPath(String val, PrefixResolver nsNode, XPathParser xpathProcessor, XPathFactory factory, XPathContext liaison)
/*  36:    */     throws TransformerException
/*  37:    */   {
/*  38:104 */     this.m_xpath = new XPath(val, null, nsNode, 0, liaison.getErrorListener());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getSimpleString()
/*  42:    */   {
/*  43:114 */     return "{" + this.m_xpath.getPatternString() + "}";
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void evaluate(XPathContext xctxt, FastStringBuffer buf, int context, PrefixResolver nsNode)
/*  47:    */     throws TransformerException
/*  48:    */   {
/*  49:135 */     XObject xobj = this.m_xpath.execute(xctxt, context, nsNode);
/*  50:137 */     if (null != xobj) {
/*  51:139 */       xobj.appendToFsb(buf);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void callVisitors(XSLTVisitor visitor)
/*  56:    */   {
/*  57:148 */     this.m_xpath.getExpression().callVisitors(this.m_xpath, visitor);
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.AVTPartXPath
 * JD-Core Version:    0.7.0.1
 */