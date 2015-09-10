/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.transformer.KeyManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xml.dtm.DTM;
/*   8:    */ import org.apache.xml.dtm.DTMIterator;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xml.utils.XMLString;
/*  11:    */ import org.apache.xpath.Expression;
/*  12:    */ import org.apache.xpath.XPathContext;
/*  13:    */ import org.apache.xpath.axes.NodeSequence;
/*  14:    */ import org.apache.xpath.axes.UnionPathIterator;
/*  15:    */ import org.apache.xpath.functions.Function2Args;
/*  16:    */ import org.apache.xpath.functions.FunctionOneArg;
/*  17:    */ import org.apache.xpath.objects.XNodeSet;
/*  18:    */ import org.apache.xpath.objects.XObject;
/*  19:    */ 
/*  20:    */ public class FuncKey
/*  21:    */   extends Function2Args
/*  22:    */ {
/*  23:    */   static final long serialVersionUID = 9089293100115347340L;
/*  24: 46 */   private static Boolean ISTRUE = new Boolean(true);
/*  25:    */   
/*  26:    */   public XObject execute(XPathContext xctxt)
/*  27:    */     throws TransformerException
/*  28:    */   {
/*  29: 60 */     TransformerImpl transformer = (TransformerImpl)xctxt.getOwnerObject();
/*  30: 61 */     XNodeSet nodes = null;
/*  31: 62 */     int context = xctxt.getCurrentNode();
/*  32: 63 */     DTM dtm = xctxt.getDTM(context);
/*  33: 64 */     int docContext = dtm.getDocumentRoot(context);
/*  34: 66 */     if (-1 == docContext) {}
/*  35: 72 */     String xkeyname = getArg0().execute(xctxt).str();
/*  36: 73 */     QName keyname = new QName(xkeyname, xctxt.getNamespaceContext());
/*  37: 74 */     XObject arg = getArg1().execute(xctxt);
/*  38: 75 */     boolean argIsNodeSetDTM = 4 == arg.getType();
/*  39: 76 */     KeyManager kmgr = transformer.getKeyManager();
/*  40: 79 */     if (argIsNodeSetDTM)
/*  41:    */     {
/*  42: 81 */       XNodeSet ns = (XNodeSet)arg;
/*  43: 82 */       ns.setShouldCacheNodes(true);
/*  44: 83 */       int len = ns.getLength();
/*  45: 84 */       if (len <= 1) {
/*  46: 85 */         argIsNodeSetDTM = false;
/*  47:    */       }
/*  48:    */     }
/*  49: 88 */     if (argIsNodeSetDTM)
/*  50:    */     {
/*  51: 90 */       Hashtable usedrefs = null;
/*  52: 91 */       DTMIterator ni = arg.iter();
/*  53:    */       
/*  54: 93 */       UnionPathIterator upi = new UnionPathIterator();
/*  55: 94 */       upi.exprSetParent(this);
/*  56:    */       int pos;
/*  57: 96 */       while (-1 != (pos = ni.nextNode()))
/*  58:    */       {
/*  59:    */         int i;
/*  60: 98 */         dtm = xctxt.getDTM(i);
/*  61: 99 */         XMLString ref = dtm.getStringValue(i);
/*  62:101 */         if (null != ref)
/*  63:    */         {
/*  64:104 */           if (null == usedrefs) {
/*  65:105 */             usedrefs = new Hashtable();
/*  66:    */           }
/*  67:107 */           if (usedrefs.get(ref) == null)
/*  68:    */           {
/*  69:115 */             usedrefs.put(ref, ISTRUE);
/*  70:    */             
/*  71:    */ 
/*  72:118 */             XNodeSet nl = kmgr.getNodeSetDTMByKey(xctxt, docContext, keyname, ref, xctxt.getNamespaceContext());
/*  73:    */             
/*  74:    */ 
/*  75:    */ 
/*  76:122 */             nl.setRoot(xctxt.getCurrentNode(), xctxt);
/*  77:    */             
/*  78:    */ 
/*  79:    */ 
/*  80:126 */             upi.addIterator(nl);
/*  81:    */           }
/*  82:    */         }
/*  83:    */       }
/*  84:135 */       int current = xctxt.getCurrentNode();
/*  85:136 */       upi.setRoot(current, xctxt);
/*  86:    */       
/*  87:138 */       nodes = new XNodeSet(upi);
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:142 */       XMLString ref = arg.xstr();
/*  92:143 */       nodes = kmgr.getNodeSetDTMByKey(xctxt, docContext, keyname, ref, xctxt.getNamespaceContext());
/*  93:    */       
/*  94:    */ 
/*  95:146 */       nodes.setRoot(xctxt.getCurrentNode(), xctxt);
/*  96:    */     }
/*  97:149 */     return nodes;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.FuncKey
 * JD-Core Version:    0.7.0.1
 */