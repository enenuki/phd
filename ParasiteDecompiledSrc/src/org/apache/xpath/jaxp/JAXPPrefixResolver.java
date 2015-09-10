/*   1:    */ package org.apache.xpath.jaxp;
/*   2:    */ 
/*   3:    */ import javax.xml.namespace.NamespaceContext;
/*   4:    */ import org.apache.xml.utils.PrefixResolver;
/*   5:    */ import org.w3c.dom.NamedNodeMap;
/*   6:    */ import org.w3c.dom.Node;
/*   7:    */ 
/*   8:    */ public class JAXPPrefixResolver
/*   9:    */   implements PrefixResolver
/*  10:    */ {
/*  11:    */   private NamespaceContext namespaceContext;
/*  12:    */   public static final String S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace";
/*  13:    */   
/*  14:    */   public JAXPPrefixResolver(NamespaceContext nsContext)
/*  15:    */   {
/*  16: 42 */     this.namespaceContext = nsContext;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public String getNamespaceForPrefix(String prefix)
/*  20:    */   {
/*  21: 47 */     return this.namespaceContext.getNamespaceURI(prefix);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getBaseIdentifier()
/*  25:    */   {
/*  26: 56 */     return null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean handlesNullPrefixes()
/*  30:    */   {
/*  31: 63 */     return false;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getNamespaceForPrefix(String prefix, Node namespaceContext)
/*  35:    */   {
/*  36: 88 */     Node parent = namespaceContext;
/*  37: 89 */     String namespace = null;
/*  38: 91 */     if (prefix.equals("xml"))
/*  39:    */     {
/*  40: 92 */       namespace = "http://www.w3.org/XML/1998/namespace";
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44:    */       int type;
/*  45: 97 */       while ((null != parent) && (null == namespace) && (((type = parent.getNodeType()) == 1) || (type == 5)))
/*  46:    */       {
/*  47:    */         int i;
/*  48:100 */         if (i == 1)
/*  49:    */         {
/*  50:101 */           NamedNodeMap nnm = parent.getAttributes();
/*  51:103 */           for (int i = 0; i < nnm.getLength(); i++)
/*  52:    */           {
/*  53:104 */             Node attr = nnm.item(i);
/*  54:105 */             String aname = attr.getNodeName();
/*  55:106 */             boolean isPrefix = aname.startsWith("xmlns:");
/*  56:108 */             if ((isPrefix) || (aname.equals("xmlns")))
/*  57:    */             {
/*  58:109 */               int index = aname.indexOf(':');
/*  59:110 */               String p = isPrefix ? aname.substring(index + 1) : "";
/*  60:112 */               if (p.equals(prefix))
/*  61:    */               {
/*  62:113 */                 namespace = attr.getNodeValue();
/*  63:114 */                 break;
/*  64:    */               }
/*  65:    */             }
/*  66:    */           }
/*  67:    */         }
/*  68:120 */         parent = parent.getParentNode();
/*  69:    */       }
/*  70:    */     }
/*  71:123 */     return namespace;
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.jaxp.JAXPPrefixResolver
 * JD-Core Version:    0.7.0.1
 */