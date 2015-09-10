/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Attr;
/*   4:    */ import org.w3c.dom.NamedNodeMap;
/*   5:    */ import org.w3c.dom.Node;
/*   6:    */ import org.xml.sax.Attributes;
/*   7:    */ 
/*   8:    */ public class AttList
/*   9:    */   implements Attributes
/*  10:    */ {
/*  11:    */   NamedNodeMap m_attrs;
/*  12:    */   int m_lastIndex;
/*  13:    */   DOMHelper m_dh;
/*  14:    */   
/*  15:    */   public AttList(NamedNodeMap attrs, DOMHelper dh)
/*  16:    */   {
/*  17: 72 */     this.m_attrs = attrs;
/*  18: 73 */     this.m_lastIndex = (this.m_attrs.getLength() - 1);
/*  19: 74 */     this.m_dh = dh;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getLength()
/*  23:    */   {
/*  24: 85 */     return this.m_attrs.getLength();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getURI(int index)
/*  28:    */   {
/*  29: 98 */     String ns = this.m_dh.getNamespaceOfNode((Attr)this.m_attrs.item(index));
/*  30: 99 */     if (null == ns) {
/*  31:100 */       ns = "";
/*  32:    */     }
/*  33:101 */     return ns;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getLocalName(int index)
/*  37:    */   {
/*  38:114 */     return this.m_dh.getLocalNameOfNode((Attr)this.m_attrs.item(index));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getQName(int i)
/*  42:    */   {
/*  43:127 */     return ((Attr)this.m_attrs.item(i)).getName();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getType(int i)
/*  47:    */   {
/*  48:140 */     return "CDATA";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getValue(int i)
/*  52:    */   {
/*  53:153 */     return ((Attr)this.m_attrs.item(i)).getValue();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getType(String name)
/*  57:    */   {
/*  58:166 */     return "CDATA";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getType(String uri, String localName)
/*  62:    */   {
/*  63:181 */     return "CDATA";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getValue(String name)
/*  67:    */   {
/*  68:194 */     Attr attr = (Attr)this.m_attrs.getNamedItem(name);
/*  69:195 */     return null != attr ? attr.getValue() : null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getValue(String uri, String localName)
/*  73:    */   {
/*  74:210 */     Node a = this.m_attrs.getNamedItemNS(uri, localName);
/*  75:211 */     return a == null ? null : a.getNodeValue();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIndex(String uri, String localPart)
/*  79:    */   {
/*  80:225 */     for (int i = this.m_attrs.getLength() - 1; i >= 0; i--)
/*  81:    */     {
/*  82:227 */       Node a = this.m_attrs.item(i);
/*  83:228 */       String u = a.getNamespaceURI();
/*  84:229 */       if ((u == null ? false : uri == null ? true : u.equals(uri))) {
/*  85:229 */         if (a.getLocalName().equals(localPart)) {
/*  86:232 */           return i;
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:234 */     return -1;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIndex(String qName)
/*  94:    */   {
/*  95:246 */     for (int i = this.m_attrs.getLength() - 1; i >= 0; i--)
/*  96:    */     {
/*  97:248 */       Node a = this.m_attrs.item(i);
/*  98:249 */       if (a.getNodeName().equals(qName)) {
/*  99:250 */         return i;
/* 100:    */       }
/* 101:    */     }
/* 102:252 */     return -1;
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.AttList
 * JD-Core Version:    0.7.0.1
 */