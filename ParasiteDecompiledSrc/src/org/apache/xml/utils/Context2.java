/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Vector;
/*   6:    */ 
/*   7:    */ final class Context2
/*   8:    */ {
/*   9:450 */   private static final Enumeration EMPTY_ENUMERATION = new Vector().elements();
/*  10:    */   Hashtable prefixTable;
/*  11:    */   Hashtable uriTable;
/*  12:    */   Hashtable elementNameTable;
/*  13:    */   Hashtable attributeNameTable;
/*  14:461 */   String defaultNS = null;
/*  15:467 */   private Vector declarations = null;
/*  16:468 */   private boolean tablesDirty = false;
/*  17:469 */   private Context2 parent = null;
/*  18:470 */   private Context2 child = null;
/*  19:    */   
/*  20:    */   Context2(Context2 parent)
/*  21:    */   {
/*  22:477 */     if (parent == null)
/*  23:    */     {
/*  24:479 */       this.prefixTable = new Hashtable();
/*  25:480 */       this.uriTable = new Hashtable();
/*  26:481 */       this.elementNameTable = null;
/*  27:482 */       this.attributeNameTable = null;
/*  28:    */     }
/*  29:    */     else
/*  30:    */     {
/*  31:485 */       setParent(parent);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   Context2 getChild()
/*  36:    */   {
/*  37:495 */     return this.child;
/*  38:    */   }
/*  39:    */   
/*  40:    */   Context2 getParent()
/*  41:    */   {
/*  42:504 */     return this.parent;
/*  43:    */   }
/*  44:    */   
/*  45:    */   void setParent(Context2 parent)
/*  46:    */   {
/*  47:516 */     this.parent = parent;
/*  48:517 */     parent.child = this;
/*  49:518 */     this.declarations = null;
/*  50:519 */     this.prefixTable = parent.prefixTable;
/*  51:520 */     this.uriTable = parent.uriTable;
/*  52:521 */     this.elementNameTable = parent.elementNameTable;
/*  53:522 */     this.attributeNameTable = parent.attributeNameTable;
/*  54:523 */     this.defaultNS = parent.defaultNS;
/*  55:524 */     this.tablesDirty = false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   void declarePrefix(String prefix, String uri)
/*  59:    */   {
/*  60:538 */     if (!this.tablesDirty) {
/*  61:539 */       copyTables();
/*  62:    */     }
/*  63:541 */     if (this.declarations == null) {
/*  64:542 */       this.declarations = new Vector();
/*  65:    */     }
/*  66:545 */     prefix = prefix.intern();
/*  67:546 */     uri = uri.intern();
/*  68:547 */     if ("".equals(prefix))
/*  69:    */     {
/*  70:548 */       if ("".equals(uri)) {
/*  71:549 */         this.defaultNS = null;
/*  72:    */       } else {
/*  73:551 */         this.defaultNS = uri;
/*  74:    */       }
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:554 */       this.prefixTable.put(prefix, uri);
/*  79:555 */       this.uriTable.put(uri, prefix);
/*  80:    */     }
/*  81:557 */     this.declarations.addElement(prefix);
/*  82:    */   }
/*  83:    */   
/*  84:    */   String[] processName(String qName, boolean isAttribute)
/*  85:    */   {
/*  86:    */     Hashtable table;
/*  87:578 */     if (isAttribute)
/*  88:    */     {
/*  89:579 */       if (this.elementNameTable == null) {
/*  90:580 */         this.elementNameTable = new Hashtable();
/*  91:    */       }
/*  92:581 */       table = this.elementNameTable;
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:583 */       if (this.attributeNameTable == null) {
/*  97:584 */         this.attributeNameTable = new Hashtable();
/*  98:    */       }
/*  99:585 */       table = this.attributeNameTable;
/* 100:    */     }
/* 101:591 */     String[] name = (String[])table.get(qName);
/* 102:592 */     if (name != null) {
/* 103:593 */       return name;
/* 104:    */     }
/* 105:598 */     name = new String[3];
/* 106:599 */     int index = qName.indexOf(':');
/* 107:603 */     if (index == -1)
/* 108:    */     {
/* 109:604 */       if ((isAttribute) || (this.defaultNS == null)) {
/* 110:605 */         name[0] = "";
/* 111:    */       } else {
/* 112:607 */         name[0] = this.defaultNS;
/* 113:    */       }
/* 114:609 */       name[1] = qName.intern();
/* 115:610 */       name[2] = name[1];
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:615 */       String prefix = qName.substring(0, index);
/* 120:616 */       String local = qName.substring(index + 1);
/* 121:    */       String uri;
/* 122:618 */       if ("".equals(prefix)) {
/* 123:619 */         uri = this.defaultNS;
/* 124:    */       } else {
/* 125:621 */         uri = (String)this.prefixTable.get(prefix);
/* 126:    */       }
/* 127:623 */       if (uri == null) {
/* 128:624 */         return null;
/* 129:    */       }
/* 130:626 */       name[0] = uri;
/* 131:627 */       name[1] = local.intern();
/* 132:628 */       name[2] = qName.intern();
/* 133:    */     }
/* 134:632 */     table.put(name[2], name);
/* 135:633 */     this.tablesDirty = true;
/* 136:634 */     return name;
/* 137:    */   }
/* 138:    */   
/* 139:    */   String getURI(String prefix)
/* 140:    */   {
/* 141:648 */     if ("".equals(prefix)) {
/* 142:649 */       return this.defaultNS;
/* 143:    */     }
/* 144:650 */     if (this.prefixTable == null) {
/* 145:651 */       return null;
/* 146:    */     }
/* 147:653 */     return (String)this.prefixTable.get(prefix);
/* 148:    */   }
/* 149:    */   
/* 150:    */   String getPrefix(String uri)
/* 151:    */   {
/* 152:670 */     if (this.uriTable == null) {
/* 153:671 */       return null;
/* 154:    */     }
/* 155:673 */     return (String)this.uriTable.get(uri);
/* 156:    */   }
/* 157:    */   
/* 158:    */   Enumeration getDeclaredPrefixes()
/* 159:    */   {
/* 160:686 */     if (this.declarations == null) {
/* 161:687 */       return EMPTY_ENUMERATION;
/* 162:    */     }
/* 163:689 */     return this.declarations.elements();
/* 164:    */   }
/* 165:    */   
/* 166:    */   Enumeration getPrefixes()
/* 167:    */   {
/* 168:705 */     if (this.prefixTable == null) {
/* 169:706 */       return EMPTY_ENUMERATION;
/* 170:    */     }
/* 171:708 */     return this.prefixTable.keys();
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void copyTables()
/* 175:    */   {
/* 176:731 */     this.prefixTable = ((Hashtable)this.prefixTable.clone());
/* 177:732 */     this.uriTable = ((Hashtable)this.uriTable.clone());
/* 178:739 */     if (this.elementNameTable != null) {
/* 179:740 */       this.elementNameTable = new Hashtable();
/* 180:    */     }
/* 181:741 */     if (this.attributeNameTable != null) {
/* 182:742 */       this.attributeNameTable = new Hashtable();
/* 183:    */     }
/* 184:743 */     this.tablesDirty = true;
/* 185:    */   }
/* 186:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.Context2
 * JD-Core Version:    0.7.0.1
 */