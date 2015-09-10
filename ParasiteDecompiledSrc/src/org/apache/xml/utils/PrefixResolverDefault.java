/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.NamedNodeMap;
/*   4:    */ import org.w3c.dom.Node;
/*   5:    */ 
/*   6:    */ public class PrefixResolverDefault
/*   7:    */   implements PrefixResolver
/*   8:    */ {
/*   9:    */   Node m_context;
/*  10:    */   
/*  11:    */   public PrefixResolverDefault(Node xpathExpressionContext)
/*  12:    */   {
/*  13: 50 */     this.m_context = xpathExpressionContext;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public String getNamespaceForPrefix(String prefix)
/*  17:    */   {
/*  18: 63 */     return getNamespaceForPrefix(prefix, this.m_context);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getNamespaceForPrefix(String prefix, Node namespaceContext)
/*  22:    */   {
/*  23: 80 */     Node parent = namespaceContext;
/*  24: 81 */     String namespace = null;
/*  25: 83 */     if (prefix.equals("xml"))
/*  26:    */     {
/*  27: 85 */       namespace = "http://www.w3.org/XML/1998/namespace";
/*  28:    */     }
/*  29:    */     else
/*  30:    */     {
/*  31:    */       int type;
/*  32: 92 */       while ((null != parent) && (null == namespace) && (((type = parent.getNodeType()) == 1) || (type == 5)))
/*  33:    */       {
/*  34:    */         int i;
/*  35: 95 */         if (i == 1)
/*  36:    */         {
/*  37: 97 */           if (parent.getNodeName().indexOf(prefix + ":") == 0) {
/*  38: 98 */             return parent.getNamespaceURI();
/*  39:    */           }
/*  40: 99 */           NamedNodeMap nnm = parent.getAttributes();
/*  41:101 */           for (int i = 0; i < nnm.getLength(); i++)
/*  42:    */           {
/*  43:103 */             Node attr = nnm.item(i);
/*  44:104 */             String aname = attr.getNodeName();
/*  45:105 */             boolean isPrefix = aname.startsWith("xmlns:");
/*  46:107 */             if ((isPrefix) || (aname.equals("xmlns")))
/*  47:    */             {
/*  48:109 */               int index = aname.indexOf(':');
/*  49:110 */               String p = isPrefix ? aname.substring(index + 1) : "";
/*  50:112 */               if (p.equals(prefix))
/*  51:    */               {
/*  52:114 */                 namespace = attr.getNodeValue();
/*  53:    */                 
/*  54:116 */                 break;
/*  55:    */               }
/*  56:    */             }
/*  57:    */           }
/*  58:    */         }
/*  59:122 */         parent = parent.getParentNode();
/*  60:    */       }
/*  61:    */     }
/*  62:126 */     return namespace;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getBaseIdentifier()
/*  66:    */   {
/*  67:136 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean handlesNullPrefixes()
/*  71:    */   {
/*  72:142 */     return false;
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.PrefixResolverDefault
 * JD-Core Version:    0.7.0.1
 */