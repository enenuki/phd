/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Node;
/*   4:    */ 
/*   5:    */ public final class DOM2Helper
/*   6:    */ {
/*   7:    */   public String getLocalNameOfNode(Node n)
/*   8:    */   {
/*   9: 72 */     String name = n.getLocalName();
/*  10:    */     
/*  11: 74 */     return null == name ? getLocalNameOfNodeFallback(n) : name;
/*  12:    */   }
/*  13:    */   
/*  14:    */   private String getLocalNameOfNodeFallback(Node n)
/*  15:    */   {
/*  16: 91 */     String qname = n.getNodeName();
/*  17: 92 */     int index = qname.indexOf(':');
/*  18:    */     
/*  19: 94 */     return index < 0 ? qname : qname.substring(index + 1);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getNamespaceOfNode(Node n)
/*  23:    */   {
/*  24:114 */     return n.getNamespaceURI();
/*  25:    */   }
/*  26:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.DOM2Helper
 * JD-Core Version:    0.7.0.1
 */