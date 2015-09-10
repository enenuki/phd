/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.Expression;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XBoolean;
/*  8:   */ import org.apache.xpath.objects.XObject;
/*  9:   */ 
/* 10:   */ public class FuncLang
/* 11:   */   extends FunctionOneArg
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = -7868705139354872185L;
/* 14:   */   
/* 15:   */   public XObject execute(XPathContext xctxt)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:47 */     String lang = this.m_arg0.execute(xctxt).str();
/* 19:48 */     int parent = xctxt.getCurrentNode();
/* 20:49 */     boolean isLang = false;
/* 21:50 */     DTM dtm = xctxt.getDTM(parent);
/* 22:52 */     while (-1 != parent)
/* 23:   */     {
/* 24:54 */       if (1 == dtm.getNodeType(parent))
/* 25:   */       {
/* 26:56 */         int langAttr = dtm.getAttributeNode(parent, "http://www.w3.org/XML/1998/namespace", "lang");
/* 27:58 */         if (-1 != langAttr)
/* 28:   */         {
/* 29:60 */           String langVal = dtm.getNodeValue(langAttr);
/* 30:62 */           if (!langVal.toLowerCase().startsWith(lang.toLowerCase())) {
/* 31:   */             break;
/* 32:   */           }
/* 33:64 */           int valLen = lang.length();
/* 34:66 */           if ((langVal.length() == valLen) || (langVal.charAt(valLen) == '-')) {
/* 35:69 */             isLang = true;
/* 36:   */           }
/* 37:69 */           break;
/* 38:   */         }
/* 39:   */       }
/* 40:77 */       parent = dtm.getParent(parent);
/* 41:   */     }
/* 42:80 */     return isLang ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncLang
 * JD-Core Version:    0.7.0.1
 */