/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.utils.XMLString;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.XPathContext;
/*   9:    */ import org.apache.xpath.res.XPATHMessages;
/*  10:    */ 
/*  11:    */ public class XRTreeFragSelectWrapper
/*  12:    */   extends XRTreeFrag
/*  13:    */   implements Cloneable
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -6526177905590461251L;
/*  16:    */   
/*  17:    */   public XRTreeFragSelectWrapper(Expression expr)
/*  18:    */   {
/*  19: 38 */     super(expr);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  23:    */   {
/*  24: 53 */     ((Expression)this.m_obj).fixupVariables(vars, globalsSize);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public XObject execute(XPathContext xctxt)
/*  28:    */     throws TransformerException
/*  29:    */   {
/*  30: 69 */     XObject m_selected = ((Expression)this.m_obj).execute(xctxt);
/*  31: 70 */     m_selected.allowDetachToRelease(this.m_allowRelease);
/*  32: 71 */     if (m_selected.getType() == 3) {
/*  33: 72 */       return m_selected;
/*  34:    */     }
/*  35: 74 */     return new XString(m_selected.str());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void detach()
/*  39:    */   {
/*  40: 88 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_DETACH_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public double num()
/*  44:    */     throws TransformerException
/*  45:    */   {
/*  46:100 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NUM_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public XMLString xstr()
/*  50:    */   {
/*  51:111 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_XSTR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String str()
/*  55:    */   {
/*  56:121 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_STR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getType()
/*  60:    */   {
/*  61:131 */     return 3;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int rtf()
/*  65:    */   {
/*  66:141 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DTMIterator asNodeIterator()
/*  70:    */   {
/*  71:151 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XRTreeFragSelectWrapper
 * JD-Core Version:    0.7.0.1
 */