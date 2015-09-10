/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.axes.OneStepIterator;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ import org.w3c.dom.traversal.NodeIterator;
/*  12:    */ 
/*  13:    */ public class XObjectFactory
/*  14:    */ {
/*  15:    */   public static XObject create(Object val)
/*  16:    */   {
/*  17:    */     XObject result;
/*  18: 48 */     if ((val instanceof XObject)) {
/*  19: 50 */       result = (XObject)val;
/*  20: 52 */     } else if ((val instanceof String)) {
/*  21: 54 */       result = new XString((String)val);
/*  22: 56 */     } else if ((val instanceof Boolean)) {
/*  23: 58 */       result = new XBoolean((Boolean)val);
/*  24: 60 */     } else if ((val instanceof Double)) {
/*  25: 62 */       result = new XNumber((Double)val);
/*  26:    */     } else {
/*  27: 66 */       result = new XObject(val);
/*  28:    */     }
/*  29: 69 */     return result;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static XObject create(Object val, XPathContext xctxt)
/*  33:    */   {
/*  34:    */     XObject result;
/*  35: 87 */     if ((val instanceof XObject))
/*  36:    */     {
/*  37: 89 */       result = (XObject)val;
/*  38:    */     }
/*  39: 91 */     else if ((val instanceof String))
/*  40:    */     {
/*  41: 93 */       result = new XString((String)val);
/*  42:    */     }
/*  43: 95 */     else if ((val instanceof Boolean))
/*  44:    */     {
/*  45: 97 */       result = new XBoolean((Boolean)val);
/*  46:    */     }
/*  47: 99 */     else if ((val instanceof Number))
/*  48:    */     {
/*  49:101 */       result = new XNumber((Number)val);
/*  50:    */     }
/*  51:103 */     else if ((val instanceof DTM))
/*  52:    */     {
/*  53:105 */       DTM dtm = (DTM)val;
/*  54:    */       try
/*  55:    */       {
/*  56:108 */         int dtmRoot = dtm.getDocument();
/*  57:109 */         DTMAxisIterator iter = dtm.getAxisIterator(13);
/*  58:110 */         iter.setStartNode(dtmRoot);
/*  59:111 */         DTMIterator iterator = new OneStepIterator(iter, 13);
/*  60:112 */         iterator.setRoot(dtmRoot, xctxt);
/*  61:113 */         result = new XNodeSet(iterator);
/*  62:    */       }
/*  63:    */       catch (Exception ex)
/*  64:    */       {
/*  65:117 */         throw new WrappedRuntimeException(ex);
/*  66:    */       }
/*  67:    */     }
/*  68:120 */     else if ((val instanceof DTMAxisIterator))
/*  69:    */     {
/*  70:122 */       DTMAxisIterator iter = (DTMAxisIterator)val;
/*  71:    */       try
/*  72:    */       {
/*  73:125 */         DTMIterator iterator = new OneStepIterator(iter, 13);
/*  74:126 */         iterator.setRoot(iter.getStartNode(), xctxt);
/*  75:127 */         result = new XNodeSet(iterator);
/*  76:    */       }
/*  77:    */       catch (Exception ex)
/*  78:    */       {
/*  79:131 */         throw new WrappedRuntimeException(ex);
/*  80:    */       }
/*  81:    */     }
/*  82:134 */     else if ((val instanceof DTMIterator))
/*  83:    */     {
/*  84:136 */       result = new XNodeSet((DTMIterator)val);
/*  85:    */     }
/*  86:140 */     else if ((val instanceof Node))
/*  87:    */     {
/*  88:142 */       result = new XNodeSetForDOM((Node)val, xctxt);
/*  89:    */     }
/*  90:146 */     else if ((val instanceof NodeList))
/*  91:    */     {
/*  92:148 */       result = new XNodeSetForDOM((NodeList)val, xctxt);
/*  93:    */     }
/*  94:150 */     else if ((val instanceof NodeIterator))
/*  95:    */     {
/*  96:152 */       result = new XNodeSetForDOM((NodeIterator)val, xctxt);
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100:156 */       result = new XObject(val);
/* 101:    */     }
/* 102:159 */     return result;
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XObjectFactory
 * JD-Core Version:    0.7.0.1
 */