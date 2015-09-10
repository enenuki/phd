/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.res.XSLMessages;
/*   7:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   8:    */ import org.apache.xml.utils.QName;
/*   9:    */ 
/*  10:    */ public class ElemUse
/*  11:    */   extends ElemTemplateElement
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = 5830057200289299736L;
/*  14: 48 */   private QName[] m_attributeSetsNames = null;
/*  15:    */   
/*  16:    */   public void setUseAttributeSets(Vector v)
/*  17:    */   {
/*  18: 64 */     int n = v.size();
/*  19:    */     
/*  20: 66 */     this.m_attributeSetsNames = new QName[n];
/*  21: 68 */     for (int i = 0; i < n; i++) {
/*  22: 70 */       this.m_attributeSetsNames[i] = ((QName)v.elementAt(i));
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setUseAttributeSets(QName[] v)
/*  27:    */   {
/*  28: 87 */     this.m_attributeSetsNames = v;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public QName[] getUseAttributeSets()
/*  32:    */   {
/*  33:105 */     return this.m_attributeSetsNames;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void applyAttrSets(TransformerImpl transformer, StylesheetRoot stylesheet)
/*  37:    */     throws TransformerException
/*  38:    */   {
/*  39:124 */     applyAttrSets(transformer, stylesheet, this.m_attributeSetsNames);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void applyAttrSets(TransformerImpl transformer, StylesheetRoot stylesheet, QName[] attributeSetsNames)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:145 */     if (null != attributeSetsNames)
/*  46:    */     {
/*  47:147 */       int nNames = attributeSetsNames.length;
/*  48:149 */       for (int i = 0; i < nNames; i++)
/*  49:    */       {
/*  50:151 */         QName qname = attributeSetsNames[i];
/*  51:152 */         List attrSets = stylesheet.getAttributeSetComposed(qname);
/*  52:154 */         if (null != attrSets)
/*  53:    */         {
/*  54:156 */           int nSets = attrSets.size();
/*  55:160 */           for (int k = nSets - 1; k >= 0; k--)
/*  56:    */           {
/*  57:162 */             ElemAttributeSet attrSet = (ElemAttributeSet)attrSets.get(k);
/*  58:    */             
/*  59:    */ 
/*  60:165 */             attrSet.execute(transformer);
/*  61:    */           }
/*  62:    */         }
/*  63:    */         else
/*  64:    */         {
/*  65:170 */           throw new TransformerException(XSLMessages.createMessage("ER_NO_ATTRIB_SET", new Object[] { qname }), this);
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void execute(TransformerImpl transformer)
/*  72:    */     throws TransformerException
/*  73:    */   {
/*  74:198 */     if (null != this.m_attributeSetsNames) {
/*  75:200 */       applyAttrSets(transformer, getStylesheetRoot(), this.m_attributeSetsNames);
/*  76:    */     }
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemUse
 * JD-Core Version:    0.7.0.1
 */