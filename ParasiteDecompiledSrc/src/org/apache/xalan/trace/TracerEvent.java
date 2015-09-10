/*   1:    */ package org.apache.xalan.trace;
/*   2:    */ 
/*   3:    */ import java.util.EventListener;
/*   4:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   5:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   6:    */ import org.apache.xml.utils.QName;
/*   7:    */ import org.w3c.dom.Attr;
/*   8:    */ import org.w3c.dom.Element;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ 
/*  12:    */ public class TracerEvent
/*  13:    */   implements EventListener
/*  14:    */ {
/*  15:    */   public final ElemTemplateElement m_styleNode;
/*  16:    */   public final TransformerImpl m_processor;
/*  17:    */   public final Node m_sourceNode;
/*  18:    */   public final QName m_mode;
/*  19:    */   
/*  20:    */   public TracerEvent(TransformerImpl processor, Node sourceNode, QName mode, ElemTemplateElement styleNode)
/*  21:    */   {
/*  22: 71 */     this.m_processor = processor;
/*  23: 72 */     this.m_sourceNode = sourceNode;
/*  24: 73 */     this.m_mode = mode;
/*  25: 74 */     this.m_styleNode = styleNode;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static String printNode(Node n)
/*  29:    */   {
/*  30: 90 */     String r = n.hashCode() + " ";
/*  31: 92 */     if ((n instanceof Element))
/*  32:    */     {
/*  33: 94 */       r = r + "<" + n.getNodeName();
/*  34:    */       
/*  35: 96 */       Node c = n.getFirstChild();
/*  36: 98 */       while (null != c)
/*  37:    */       {
/*  38:100 */         if ((c instanceof Attr)) {
/*  39:102 */           r = r + printNode(c) + " ";
/*  40:    */         }
/*  41:105 */         c = c.getNextSibling();
/*  42:    */       }
/*  43:108 */       r = r + ">";
/*  44:    */     }
/*  45:112 */     else if ((n instanceof Attr))
/*  46:    */     {
/*  47:114 */       r = r + n.getNodeName() + "=" + n.getNodeValue();
/*  48:    */     }
/*  49:    */     else
/*  50:    */     {
/*  51:118 */       r = r + n.getNodeName();
/*  52:    */     }
/*  53:122 */     return r;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static String printNodeList(NodeList l)
/*  57:    */   {
/*  58:139 */     String r = l.hashCode() + "[";
/*  59:140 */     int len = l.getLength() - 1;
/*  60:141 */     int i = 0;
/*  61:143 */     while (i < len)
/*  62:    */     {
/*  63:145 */       Node n = l.item(i);
/*  64:147 */       if (null != n) {
/*  65:149 */         r = r + printNode(n) + ", ";
/*  66:    */       }
/*  67:152 */       i++;
/*  68:    */     }
/*  69:155 */     if (i == len)
/*  70:    */     {
/*  71:157 */       Node n = l.item(len);
/*  72:159 */       if (null != n) {
/*  73:161 */         r = r + printNode(n);
/*  74:    */       }
/*  75:    */     }
/*  76:165 */     return r + "]";
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.TracerEvent
 * JD-Core Version:    0.7.0.1
 */