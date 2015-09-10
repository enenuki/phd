/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   6:    */ import org.apache.xalan.templates.StylesheetRoot;
/*   7:    */ import org.apache.xml.utils.PrefixResolver;
/*   8:    */ import org.apache.xml.utils.QName;
/*   9:    */ import org.apache.xml.utils.XMLString;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XNodeSet;
/*  12:    */ 
/*  13:    */ public class KeyManager
/*  14:    */ {
/*  15: 42 */   private transient Vector m_key_tables = null;
/*  16:    */   
/*  17:    */   public XNodeSet getNodeSetDTMByKey(XPathContext xctxt, int doc, QName name, XMLString ref, PrefixResolver nscontext)
/*  18:    */     throws TransformerException
/*  19:    */   {
/*  20: 62 */     XNodeSet nl = null;
/*  21: 63 */     ElemTemplateElement template = (ElemTemplateElement)nscontext;
/*  22: 65 */     if ((null != template) && (null != template.getStylesheetRoot().getKeysComposed()))
/*  23:    */     {
/*  24: 68 */       boolean foundDoc = false;
/*  25: 70 */       if (null == this.m_key_tables)
/*  26:    */       {
/*  27: 72 */         this.m_key_tables = new Vector(4);
/*  28:    */       }
/*  29:    */       else
/*  30:    */       {
/*  31: 76 */         int nKeyTables = this.m_key_tables.size();
/*  32: 78 */         for (int i = 0; i < nKeyTables; i++)
/*  33:    */         {
/*  34: 80 */           KeyTable kt = (KeyTable)this.m_key_tables.elementAt(i);
/*  35: 82 */           if ((kt.getKeyTableName().equals(name)) && (doc == kt.getDocKey()))
/*  36:    */           {
/*  37: 84 */             nl = kt.getNodeSetDTMByKey(name, ref);
/*  38: 86 */             if (nl != null)
/*  39:    */             {
/*  40: 88 */               foundDoc = true;
/*  41:    */               
/*  42: 90 */               break;
/*  43:    */             }
/*  44:    */           }
/*  45:    */         }
/*  46:    */       }
/*  47: 96 */       if ((null == nl) && (!foundDoc))
/*  48:    */       {
/*  49: 98 */         KeyTable kt = new KeyTable(doc, nscontext, name, template.getStylesheetRoot().getKeysComposed(), xctxt);
/*  50:    */         
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:103 */         this.m_key_tables.addElement(kt);
/*  55:105 */         if (doc == kt.getDocKey())
/*  56:    */         {
/*  57:107 */           foundDoc = true;
/*  58:108 */           nl = kt.getNodeSetDTMByKey(name, ref);
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:113 */     return nl;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.KeyManager
 * JD-Core Version:    0.7.0.1
 */