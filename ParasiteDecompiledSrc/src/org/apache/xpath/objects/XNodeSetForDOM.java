/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTMManager;
/*   5:    */ import org.apache.xpath.NodeSetDTM;
/*   6:    */ import org.apache.xpath.XPathContext;
/*   7:    */ import org.apache.xpath.axes.NodeSequence;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ import org.w3c.dom.traversal.NodeIterator;
/*  11:    */ 
/*  12:    */ public class XNodeSetForDOM
/*  13:    */   extends XNodeSet
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -8396190713754624640L;
/*  16:    */   Object m_origObj;
/*  17:    */   
/*  18:    */   public XNodeSetForDOM(Node node, DTMManager dtmMgr)
/*  19:    */   {
/*  20: 42 */     this.m_dtmMgr = dtmMgr;
/*  21: 43 */     this.m_origObj = node;
/*  22: 44 */     int dtmHandle = dtmMgr.getDTMHandleFromNode(node);
/*  23: 45 */     setObject(new NodeSetDTM(dtmMgr));
/*  24: 46 */     ((NodeSetDTM)this.m_obj).addNode(dtmHandle);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public XNodeSetForDOM(XNodeSet val)
/*  28:    */   {
/*  29: 56 */     super(val);
/*  30: 57 */     if ((val instanceof XNodeSetForDOM)) {
/*  31: 58 */       this.m_origObj = ((XNodeSetForDOM)val).m_origObj;
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public XNodeSetForDOM(NodeList nodeList, XPathContext xctxt)
/*  36:    */   {
/*  37: 63 */     this.m_dtmMgr = xctxt.getDTMManager();
/*  38: 64 */     this.m_origObj = nodeList;
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44: 70 */     NodeSetDTM nsdtm = new NodeSetDTM(nodeList, xctxt);
/*  45: 71 */     this.m_last = nsdtm.getLength();
/*  46: 72 */     setObject(nsdtm);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public XNodeSetForDOM(NodeIterator nodeIter, XPathContext xctxt)
/*  50:    */   {
/*  51: 77 */     this.m_dtmMgr = xctxt.getDTMManager();
/*  52: 78 */     this.m_origObj = nodeIter;
/*  53:    */     
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58: 84 */     NodeSetDTM nsdtm = new NodeSetDTM(nodeIter, xctxt);
/*  59: 85 */     this.m_last = nsdtm.getLength();
/*  60: 86 */     setObject(nsdtm);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Object object()
/*  64:    */   {
/*  65: 97 */     return this.m_origObj;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public NodeIterator nodeset()
/*  69:    */     throws TransformerException
/*  70:    */   {
/*  71:109 */     return (this.m_origObj instanceof NodeIterator) ? (NodeIterator)this.m_origObj : super.nodeset();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public NodeList nodelist()
/*  75:    */     throws TransformerException
/*  76:    */   {
/*  77:122 */     return (this.m_origObj instanceof NodeList) ? (NodeList)this.m_origObj : super.nodelist();
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XNodeSetForDOM
 * JD-Core Version:    0.7.0.1
 */