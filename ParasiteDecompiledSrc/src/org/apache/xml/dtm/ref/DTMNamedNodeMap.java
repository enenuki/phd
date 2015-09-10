/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.w3c.dom.DOMException;
/*   5:    */ import org.w3c.dom.NamedNodeMap;
/*   6:    */ import org.w3c.dom.Node;
/*   7:    */ 
/*   8:    */ public class DTMNamedNodeMap
/*   9:    */   implements NamedNodeMap
/*  10:    */ {
/*  11:    */   DTM dtm;
/*  12:    */   int element;
/*  13: 54 */   short m_count = -1;
/*  14:    */   
/*  15:    */   public DTMNamedNodeMap(DTM dtm, int element)
/*  16:    */   {
/*  17: 64 */     this.dtm = dtm;
/*  18: 65 */     this.element = element;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getLength()
/*  22:    */   {
/*  23: 76 */     if (this.m_count == -1)
/*  24:    */     {
/*  25: 78 */       short count = 0;
/*  26: 80 */       for (int n = this.dtm.getFirstAttribute(this.element); n != -1; n = this.dtm.getNextAttribute(n)) {
/*  27: 83 */         count = (short)(count + 1);
/*  28:    */       }
/*  29: 86 */       this.m_count = count;
/*  30:    */     }
/*  31: 89 */     return this.m_count;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Node getNamedItem(String name)
/*  35:    */   {
/*  36:102 */     for (int n = this.dtm.getFirstAttribute(this.element); n != -1; n = this.dtm.getNextAttribute(n)) {
/*  37:105 */       if (this.dtm.getNodeName(n).equals(name)) {
/*  38:106 */         return this.dtm.getNode(n);
/*  39:    */       }
/*  40:    */     }
/*  41:109 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Node item(int i)
/*  45:    */   {
/*  46:123 */     int count = 0;
/*  47:125 */     for (int n = this.dtm.getFirstAttribute(this.element); n != -1; n = this.dtm.getNextAttribute(n))
/*  48:    */     {
/*  49:128 */       if (count == i) {
/*  50:129 */         return this.dtm.getNode(n);
/*  51:    */       }
/*  52:131 */       count++;
/*  53:    */     }
/*  54:134 */     return null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Node setNamedItem(Node newNode)
/*  58:    */   {
/*  59:163 */     throw new DTMException((short)7);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Node removeNamedItem(String name)
/*  63:    */   {
/*  64:183 */     throw new DTMException((short)7);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Node getNamedItemNS(String namespaceURI, String localName)
/*  68:    */   {
/*  69:199 */     Node retNode = null;
/*  70:200 */     for (int n = this.dtm.getFirstAttribute(this.element); n != -1; n = this.dtm.getNextAttribute(n)) {
/*  71:203 */       if (localName.equals(this.dtm.getLocalName(n)))
/*  72:    */       {
/*  73:205 */         String nsURI = this.dtm.getNamespaceURI(n);
/*  74:206 */         if (((namespaceURI == null) && (nsURI == null)) || ((namespaceURI != null) && (namespaceURI.equals(nsURI))))
/*  75:    */         {
/*  76:209 */           retNode = this.dtm.getNode(n);
/*  77:210 */           break;
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:214 */     return retNode;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Node setNamedItemNS(Node arg)
/*  85:    */     throws DOMException
/*  86:    */   {
/*  87:242 */     throw new DTMException((short)7);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Node removeNamedItemNS(String namespaceURI, String localName)
/*  91:    */     throws DOMException
/*  92:    */   {
/*  93:268 */     throw new DTMException((short)7);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public class DTMException
/*  97:    */     extends DOMException
/*  98:    */   {
/*  99:    */     static final long serialVersionUID = -8290238117162437678L;
/* 100:    */     
/* 101:    */     public DTMException(short code, String message)
/* 102:    */     {
/* 103:286 */       super(message);
/* 104:    */     }
/* 105:    */     
/* 106:    */     public DTMException(short code)
/* 107:    */     {
/* 108:297 */       super("");
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMNamedNodeMap
 * JD-Core Version:    0.7.0.1
 */