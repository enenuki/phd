/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.Transformer;
/*   4:    */ import org.apache.xalan.templates.ElemTemplate;
/*   5:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   6:    */ import org.apache.xml.dtm.DTM;
/*   7:    */ import org.apache.xml.dtm.DTMIterator;
/*   8:    */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ import org.w3c.dom.traversal.NodeIterator;
/*  12:    */ 
/*  13:    */ public class XalanTransformState
/*  14:    */   implements TransformState
/*  15:    */ {
/*  16: 40 */   Node m_node = null;
/*  17: 41 */   ElemTemplateElement m_currentElement = null;
/*  18: 42 */   ElemTemplate m_currentTemplate = null;
/*  19: 43 */   ElemTemplate m_matchedTemplate = null;
/*  20: 44 */   int m_currentNodeHandle = -1;
/*  21: 45 */   Node m_currentNode = null;
/*  22: 46 */   int m_matchedNode = -1;
/*  23: 47 */   DTMIterator m_contextNodeList = null;
/*  24: 48 */   boolean m_elemPending = false;
/*  25: 49 */   TransformerImpl m_transformer = null;
/*  26:    */   
/*  27:    */   public void setCurrentNode(Node n)
/*  28:    */   {
/*  29: 55 */     this.m_node = n;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void resetState(Transformer transformer)
/*  33:    */   {
/*  34: 62 */     if ((transformer != null) && ((transformer instanceof TransformerImpl)))
/*  35:    */     {
/*  36: 63 */       this.m_transformer = ((TransformerImpl)transformer);
/*  37: 64 */       this.m_currentElement = this.m_transformer.getCurrentElement();
/*  38: 65 */       this.m_currentTemplate = this.m_transformer.getCurrentTemplate();
/*  39: 66 */       this.m_matchedTemplate = this.m_transformer.getMatchedTemplate();
/*  40: 67 */       int currentNodeHandle = this.m_transformer.getCurrentNode();
/*  41: 68 */       DTM dtm = this.m_transformer.getXPathContext().getDTM(currentNodeHandle);
/*  42: 69 */       this.m_currentNode = dtm.getNode(currentNodeHandle);
/*  43: 70 */       this.m_matchedNode = this.m_transformer.getMatchedNode();
/*  44: 71 */       this.m_contextNodeList = this.m_transformer.getContextNodeList();
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ElemTemplateElement getCurrentElement()
/*  49:    */   {
/*  50: 79 */     if (this.m_elemPending) {
/*  51: 80 */       return this.m_currentElement;
/*  52:    */     }
/*  53: 82 */     return this.m_transformer.getCurrentElement();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Node getCurrentNode()
/*  57:    */   {
/*  58: 89 */     if (this.m_currentNode != null) {
/*  59: 90 */       return this.m_currentNode;
/*  60:    */     }
/*  61: 92 */     DTM dtm = this.m_transformer.getXPathContext().getDTM(this.m_transformer.getCurrentNode());
/*  62: 93 */     return dtm.getNode(this.m_transformer.getCurrentNode());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ElemTemplate getCurrentTemplate()
/*  66:    */   {
/*  67:101 */     if (this.m_elemPending) {
/*  68:102 */       return this.m_currentTemplate;
/*  69:    */     }
/*  70:104 */     return this.m_transformer.getCurrentTemplate();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ElemTemplate getMatchedTemplate()
/*  74:    */   {
/*  75:111 */     if (this.m_elemPending) {
/*  76:112 */       return this.m_matchedTemplate;
/*  77:    */     }
/*  78:114 */     return this.m_transformer.getMatchedTemplate();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Node getMatchedNode()
/*  82:    */   {
/*  83:122 */     if (this.m_elemPending)
/*  84:    */     {
/*  85:123 */       DTM dtm = this.m_transformer.getXPathContext().getDTM(this.m_matchedNode);
/*  86:124 */       return dtm.getNode(this.m_matchedNode);
/*  87:    */     }
/*  88:126 */     DTM dtm = this.m_transformer.getXPathContext().getDTM(this.m_transformer.getMatchedNode());
/*  89:127 */     return dtm.getNode(this.m_transformer.getMatchedNode());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public NodeIterator getContextNodeList()
/*  93:    */   {
/*  94:135 */     if (this.m_elemPending) {
/*  95:136 */       return new DTMNodeIterator(this.m_contextNodeList);
/*  96:    */     }
/*  97:138 */     return new DTMNodeIterator(this.m_transformer.getContextNodeList());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Transformer getTransformer()
/* 101:    */   {
/* 102:145 */     return this.m_transformer;
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.XalanTransformState
 * JD-Core Version:    0.7.0.1
 */