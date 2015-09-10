/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xml.utils.StringVector;
/*   8:    */ import org.apache.xml.utils.XMLString;
/*   9:    */ import org.apache.xpath.Expression;
/*  10:    */ import org.apache.xpath.NodeSetDTM;
/*  11:    */ import org.apache.xpath.XPathContext;
/*  12:    */ import org.apache.xpath.objects.XNodeSet;
/*  13:    */ import org.apache.xpath.objects.XObject;
/*  14:    */ 
/*  15:    */ public class FuncId
/*  16:    */   extends FunctionOneArg
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = 8930573966143567310L;
/*  19:    */   
/*  20:    */   private StringVector getNodesByID(XPathContext xctxt, int docContext, String refval, StringVector usedrefs, NodeSetDTM nodeSet, boolean mayBeMore)
/*  21:    */   {
/*  22: 60 */     if (null != refval)
/*  23:    */     {
/*  24: 62 */       String ref = null;
/*  25:    */       
/*  26: 64 */       StringTokenizer tokenizer = new StringTokenizer(refval);
/*  27: 65 */       boolean hasMore = tokenizer.hasMoreTokens();
/*  28: 66 */       DTM dtm = xctxt.getDTM(docContext);
/*  29: 68 */       while (hasMore)
/*  30:    */       {
/*  31: 70 */         ref = tokenizer.nextToken();
/*  32: 71 */         hasMore = tokenizer.hasMoreTokens();
/*  33: 73 */         if ((null != usedrefs) && (usedrefs.contains(ref)))
/*  34:    */         {
/*  35: 75 */           ref = null;
/*  36:    */         }
/*  37:    */         else
/*  38:    */         {
/*  39: 80 */           int node = dtm.getElementById(ref);
/*  40: 82 */           if (-1 != node) {
/*  41: 83 */             nodeSet.addNodeInDocOrder(node, xctxt);
/*  42:    */           }
/*  43: 85 */           if ((null != ref) && ((hasMore) || (mayBeMore)))
/*  44:    */           {
/*  45: 87 */             if (null == usedrefs) {
/*  46: 88 */               usedrefs = new StringVector();
/*  47:    */             }
/*  48: 90 */             usedrefs.addElement(ref);
/*  49:    */           }
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53: 95 */     return usedrefs;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public XObject execute(XPathContext xctxt)
/*  57:    */     throws TransformerException
/*  58:    */   {
/*  59:109 */     int context = xctxt.getCurrentNode();
/*  60:110 */     DTM dtm = xctxt.getDTM(context);
/*  61:111 */     int docContext = dtm.getDocument();
/*  62:113 */     if (-1 == docContext) {
/*  63:114 */       error(xctxt, "ER_CONTEXT_HAS_NO_OWNERDOC", null);
/*  64:    */     }
/*  65:116 */     XObject arg = this.m_arg0.execute(xctxt);
/*  66:117 */     int argType = arg.getType();
/*  67:118 */     XNodeSet nodes = new XNodeSet(xctxt.getDTMManager());
/*  68:119 */     NodeSetDTM nodeSet = nodes.mutableNodeset();
/*  69:121 */     if (4 == argType)
/*  70:    */     {
/*  71:123 */       DTMIterator ni = arg.iter();
/*  72:124 */       StringVector usedrefs = null;
/*  73:125 */       int pos = ni.nextNode();
/*  74:127 */       while (-1 != pos)
/*  75:    */       {
/*  76:129 */         DTM ndtm = ni.getDTM(pos);
/*  77:130 */         String refval = ndtm.getStringValue(pos).toString();
/*  78:    */         
/*  79:132 */         pos = ni.nextNode();
/*  80:133 */         usedrefs = getNodesByID(xctxt, docContext, refval, usedrefs, nodeSet, -1 != pos);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:138 */       if (-1 == argType) {
/*  86:140 */         return nodes;
/*  87:    */       }
/*  88:144 */       String refval = arg.str();
/*  89:    */       
/*  90:146 */       getNodesByID(xctxt, docContext, refval, null, nodeSet, false);
/*  91:    */     }
/*  92:149 */     return nodes;
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncId
 * JD-Core Version:    0.7.0.1
 */