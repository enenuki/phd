/*   1:    */ package org.apache.xalan.xsltc.runtime;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.xml.sax.Attributes;
/*   5:    */ 
/*   6:    */ public class AttributeList
/*   7:    */   implements Attributes
/*   8:    */ {
/*   9:    */   private static final String EMPTYSTRING = "";
/*  10:    */   private static final String CDATASTRING = "CDATA";
/*  11:    */   private Hashtable _attributes;
/*  12:    */   private Vector _names;
/*  13:    */   private Vector _qnames;
/*  14:    */   private Vector _values;
/*  15:    */   private Vector _uris;
/*  16:    */   private int _length;
/*  17:    */   
/*  18:    */   public AttributeList()
/*  19:    */   {
/*  20: 52 */     this._length = 0;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public AttributeList(Attributes attributes)
/*  24:    */   {
/*  25: 59 */     this();
/*  26: 60 */     if (attributes != null)
/*  27:    */     {
/*  28: 61 */       int count = attributes.getLength();
/*  29: 62 */       for (int i = 0; i < count; i++) {
/*  30: 63 */         add(attributes.getQName(i), attributes.getValue(i));
/*  31:    */       }
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void alloc()
/*  36:    */   {
/*  37: 75 */     this._attributes = new Hashtable();
/*  38: 76 */     this._names = new Vector();
/*  39: 77 */     this._values = new Vector();
/*  40: 78 */     this._qnames = new Vector();
/*  41: 79 */     this._uris = new Vector();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getLength()
/*  45:    */   {
/*  46: 86 */     return this._length;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getURI(int index)
/*  50:    */   {
/*  51: 93 */     if (index < this._length) {
/*  52: 94 */       return (String)this._uris.elementAt(index);
/*  53:    */     }
/*  54: 96 */     return null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getLocalName(int index)
/*  58:    */   {
/*  59:103 */     if (index < this._length) {
/*  60:104 */       return (String)this._names.elementAt(index);
/*  61:    */     }
/*  62:106 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getQName(int pos)
/*  66:    */   {
/*  67:113 */     if (pos < this._length) {
/*  68:114 */       return (String)this._qnames.elementAt(pos);
/*  69:    */     }
/*  70:116 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getType(int index)
/*  74:    */   {
/*  75:123 */     return "CDATA";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIndex(String namespaceURI, String localPart)
/*  79:    */   {
/*  80:130 */     return -1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIndex(String qname)
/*  84:    */   {
/*  85:137 */     return -1;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getType(String uri, String localName)
/*  89:    */   {
/*  90:144 */     return "CDATA";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getType(String qname)
/*  94:    */   {
/*  95:151 */     return "CDATA";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getValue(int pos)
/*  99:    */   {
/* 100:158 */     if (pos < this._length) {
/* 101:159 */       return (String)this._values.elementAt(pos);
/* 102:    */     }
/* 103:161 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getValue(String qname)
/* 107:    */   {
/* 108:168 */     if (this._attributes != null)
/* 109:    */     {
/* 110:169 */       Integer obj = (Integer)this._attributes.get(qname);
/* 111:170 */       if (obj == null) {
/* 112:170 */         return null;
/* 113:    */       }
/* 114:171 */       return getValue(obj.intValue());
/* 115:    */     }
/* 116:174 */     return null;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getValue(String uri, String localName)
/* 120:    */   {
/* 121:181 */     return getValue(uri + ':' + localName);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void add(String qname, String value)
/* 125:    */   {
/* 126:189 */     if (this._attributes == null) {
/* 127:190 */       alloc();
/* 128:    */     }
/* 129:193 */     Integer obj = (Integer)this._attributes.get(qname);
/* 130:194 */     if (obj == null)
/* 131:    */     {
/* 132:195 */       this._attributes.put(qname, obj = new Integer(this._length++));
/* 133:196 */       this._qnames.addElement(qname);
/* 134:197 */       this._values.addElement(value);
/* 135:198 */       int col = qname.lastIndexOf(':');
/* 136:199 */       if (col > -1)
/* 137:    */       {
/* 138:200 */         this._uris.addElement(qname.substring(0, col));
/* 139:201 */         this._names.addElement(qname.substring(col + 1));
/* 140:    */       }
/* 141:    */       else
/* 142:    */       {
/* 143:204 */         this._uris.addElement("");
/* 144:205 */         this._names.addElement(qname);
/* 145:    */       }
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:209 */       int index = obj.intValue();
/* 150:210 */       this._values.set(index, value);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void clear()
/* 155:    */   {
/* 156:218 */     this._length = 0;
/* 157:219 */     if (this._attributes != null)
/* 158:    */     {
/* 159:220 */       this._attributes.clear();
/* 160:221 */       this._names.removeAllElements();
/* 161:222 */       this._values.removeAllElements();
/* 162:223 */       this._qnames.removeAllElements();
/* 163:224 */       this._uris.removeAllElements();
/* 164:    */     }
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.AttributeList
 * JD-Core Version:    0.7.0.1
 */