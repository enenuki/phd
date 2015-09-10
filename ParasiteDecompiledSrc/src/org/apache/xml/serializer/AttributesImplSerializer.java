/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import org.xml.sax.Attributes;
/*   5:    */ import org.xml.sax.helpers.AttributesImpl;
/*   6:    */ 
/*   7:    */ public final class AttributesImplSerializer
/*   8:    */   extends AttributesImpl
/*   9:    */ {
/*  10: 49 */   private final Hashtable m_indexFromQName = new Hashtable();
/*  11: 51 */   private final StringBuffer m_buff = new StringBuffer();
/*  12:    */   private static final int MAX = 12;
/*  13:    */   private static final int MAXMinus1 = 11;
/*  14:    */   
/*  15:    */   public final int getIndex(String qname)
/*  16:    */   {
/*  17:    */     int index;
/*  18: 75 */     if (super.getLength() < 12)
/*  19:    */     {
/*  20: 79 */       index = super.getIndex(qname);
/*  21: 80 */       return index;
/*  22:    */     }
/*  23: 84 */     Integer i = (Integer)this.m_indexFromQName.get(qname);
/*  24: 85 */     if (i == null) {
/*  25: 86 */       index = -1;
/*  26:    */     } else {
/*  27: 88 */       index = i.intValue();
/*  28:    */     }
/*  29: 89 */     return index;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public final void addAttribute(String uri, String local, String qname, String type, String val)
/*  33:    */   {
/*  34:110 */     int index = super.getLength();
/*  35:111 */     super.addAttribute(uri, local, qname, type, val);
/*  36:115 */     if (index < 11) {
/*  37:117 */       return;
/*  38:    */     }
/*  39:119 */     if (index == 11)
/*  40:    */     {
/*  41:121 */       switchOverToHash(12);
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45:127 */       Integer i = new Integer(index);
/*  46:128 */       this.m_indexFromQName.put(qname, i);
/*  47:    */       
/*  48:    */ 
/*  49:131 */       this.m_buff.setLength(0);
/*  50:132 */       this.m_buff.append('{').append(uri).append('}').append(local);
/*  51:133 */       String key = this.m_buff.toString();
/*  52:134 */       this.m_indexFromQName.put(key, i);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void switchOverToHash(int numAtts)
/*  57:    */   {
/*  58:149 */     for (int index = 0; index < numAtts; index++)
/*  59:    */     {
/*  60:151 */       String qName = super.getQName(index);
/*  61:152 */       Integer i = new Integer(index);
/*  62:153 */       this.m_indexFromQName.put(qName, i);
/*  63:    */       
/*  64:    */ 
/*  65:156 */       String uri = super.getURI(index);
/*  66:157 */       String local = super.getLocalName(index);
/*  67:158 */       this.m_buff.setLength(0);
/*  68:159 */       this.m_buff.append('{').append(uri).append('}').append(local);
/*  69:160 */       String key = this.m_buff.toString();
/*  70:161 */       this.m_indexFromQName.put(key, i);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final void clear()
/*  75:    */   {
/*  76:173 */     int len = super.getLength();
/*  77:174 */     super.clear();
/*  78:175 */     if (12 <= len) {
/*  79:179 */       this.m_indexFromQName.clear();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final void setAttributes(Attributes atts)
/*  84:    */   {
/*  85:195 */     super.setAttributes(atts);
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:200 */     int numAtts = atts.getLength();
/*  91:201 */     if (12 <= numAtts) {
/*  92:202 */       switchOverToHash(numAtts);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public final int getIndex(String uri, String localName)
/*  97:    */   {
/*  98:    */     int index;
/*  99:217 */     if (super.getLength() < 12)
/* 100:    */     {
/* 101:221 */       index = super.getIndex(uri, localName);
/* 102:222 */       return index;
/* 103:    */     }
/* 104:227 */     this.m_buff.setLength(0);
/* 105:228 */     this.m_buff.append('{').append(uri).append('}').append(localName);
/* 106:229 */     String key = this.m_buff.toString();
/* 107:230 */     Integer i = (Integer)this.m_indexFromQName.get(key);
/* 108:231 */     if (i == null) {
/* 109:232 */       index = -1;
/* 110:    */     } else {
/* 111:234 */       index = i.intValue();
/* 112:    */     }
/* 113:235 */     return index;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.AttributesImplSerializer
 * JD-Core Version:    0.7.0.1
 */