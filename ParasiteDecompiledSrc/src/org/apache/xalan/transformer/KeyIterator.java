/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ import org.apache.xalan.templates.KeyDeclaration;
/*   7:    */ import org.apache.xml.utils.QName;
/*   8:    */ import org.apache.xpath.XPath;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.axes.LocPathIterator;
/*  11:    */ import org.apache.xpath.axes.OneStepIteratorForward;
/*  12:    */ import org.apache.xpath.axes.PredicatedNodeTest;
/*  13:    */ 
/*  14:    */ public class KeyIterator
/*  15:    */   extends OneStepIteratorForward
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -1349109910100249661L;
/*  18:    */   private QName m_name;
/*  19:    */   private Vector m_keyDeclarations;
/*  20:    */   
/*  21:    */   public QName getName()
/*  22:    */   {
/*  23: 58 */     return this.m_name;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Vector getKeyDeclarations()
/*  27:    */   {
/*  28: 73 */     return this.m_keyDeclarations;
/*  29:    */   }
/*  30:    */   
/*  31:    */   KeyIterator(QName name, Vector keyDeclarations)
/*  32:    */   {
/*  33: 83 */     super(16);
/*  34: 84 */     this.m_keyDeclarations = keyDeclarations;
/*  35:    */     
/*  36: 86 */     this.m_name = name;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public short acceptNode(int testNode)
/*  40:    */   {
/*  41:102 */     boolean foundKey = false;
/*  42:103 */     KeyIterator ki = (KeyIterator)this.m_lpi;
/*  43:104 */     XPathContext xctxt = ki.getXPathContext();
/*  44:105 */     Vector keys = ki.getKeyDeclarations();
/*  45:    */     
/*  46:107 */     QName name = ki.getName();
/*  47:    */     try
/*  48:    */     {
/*  49:111 */       int nDeclarations = keys.size();
/*  50:114 */       for (int i = 0; i < nDeclarations; i++)
/*  51:    */       {
/*  52:116 */         KeyDeclaration kd = (KeyDeclaration)keys.elementAt(i);
/*  53:120 */         if (kd.getName().equals(name))
/*  54:    */         {
/*  55:123 */           foundKey = true;
/*  56:    */           
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:128 */           XPath matchExpr = kd.getMatch();
/*  61:129 */           double score = matchExpr.getMatchScore(xctxt, testNode);
/*  62:    */           
/*  63:131 */           kd.getMatch();
/*  64:131 */           if (score != (-1.0D / 0.0D)) {
/*  65:134 */             return 1;
/*  66:    */           }
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70:    */     catch (TransformerException se) {}
/*  71:144 */     if (!foundKey) {
/*  72:145 */       throw new RuntimeException(XSLMessages.createMessage("ER_NO_XSLKEY_DECLARATION", new Object[] { name.getLocalName() }));
/*  73:    */     }
/*  74:150 */     return 2;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.KeyIterator
 * JD-Core Version:    0.7.0.1
 */