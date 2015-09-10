/*   1:    */ package org.dom4j.datatype;
/*   2:    */ 
/*   3:    */ import com.sun.msv.datatype.DatabindableDatatype;
/*   4:    */ import com.sun.msv.datatype.SerializationContext;
/*   5:    */ import com.sun.msv.datatype.xsd.XSDatatype;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.Namespace;
/*   8:    */ import org.dom4j.QName;
/*   9:    */ import org.dom4j.tree.AbstractAttribute;
/*  10:    */ import org.relaxng.datatype.DatatypeException;
/*  11:    */ import org.relaxng.datatype.ValidationContext;
/*  12:    */ 
/*  13:    */ public class DatatypeAttribute
/*  14:    */   extends AbstractAttribute
/*  15:    */   implements SerializationContext, ValidationContext
/*  16:    */ {
/*  17:    */   private Element parent;
/*  18:    */   private QName qname;
/*  19:    */   private XSDatatype datatype;
/*  20:    */   private Object data;
/*  21:    */   private String text;
/*  22:    */   
/*  23:    */   public DatatypeAttribute(QName qname, XSDatatype datatype)
/*  24:    */   {
/*  25: 50 */     this.qname = qname;
/*  26: 51 */     this.datatype = datatype;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public DatatypeAttribute(QName qname, XSDatatype datatype, String text)
/*  30:    */   {
/*  31: 55 */     this.qname = qname;
/*  32: 56 */     this.datatype = datatype;
/*  33: 57 */     this.text = text;
/*  34: 58 */     this.data = convertToValue(text);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String toString()
/*  38:    */   {
/*  39: 62 */     return getClass().getName() + hashCode() + " [Attribute: name " + getQualifiedName() + " value \"" + getValue() + "\" data: " + getData() + "]";
/*  40:    */   }
/*  41:    */   
/*  42:    */   public XSDatatype getXSDatatype()
/*  43:    */   {
/*  44: 73 */     return this.datatype;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getNamespacePrefix(String uri)
/*  48:    */   {
/*  49: 79 */     Element parentElement = getParent();
/*  50: 81 */     if (parentElement != null)
/*  51:    */     {
/*  52: 82 */       Namespace namespace = parentElement.getNamespaceForURI(uri);
/*  53: 84 */       if (namespace != null) {
/*  54: 85 */         return namespace.getPrefix();
/*  55:    */       }
/*  56:    */     }
/*  57: 89 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getBaseUri()
/*  61:    */   {
/*  62: 96 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isNotation(String notationName)
/*  66:    */   {
/*  67:101 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isUnparsedEntity(String entityName)
/*  71:    */   {
/*  72:106 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String resolveNamespacePrefix(String prefix)
/*  76:    */   {
/*  77:111 */     if (prefix.equals(getNamespacePrefix())) {
/*  78:112 */       return getNamespaceURI();
/*  79:    */     }
/*  80:114 */     Element parentElement = getParent();
/*  81:116 */     if (parentElement != null)
/*  82:    */     {
/*  83:117 */       Namespace namespace = parentElement.getNamespaceForPrefix(prefix);
/*  84:120 */       if (namespace != null) {
/*  85:121 */         return namespace.getURI();
/*  86:    */       }
/*  87:    */     }
/*  88:126 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public QName getQName()
/*  92:    */   {
/*  93:132 */     return this.qname;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getValue()
/*  97:    */   {
/*  98:136 */     return this.text;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setValue(String value)
/* 102:    */   {
/* 103:140 */     validate(value);
/* 104:    */     
/* 105:142 */     this.text = value;
/* 106:143 */     this.data = convertToValue(value);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Object getData()
/* 110:    */   {
/* 111:147 */     return this.data;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setData(Object data)
/* 115:    */   {
/* 116:151 */     String s = this.datatype.convertToLexicalValue(data, this);
/* 117:152 */     validate(s);
/* 118:153 */     this.text = s;
/* 119:154 */     this.data = data;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Element getParent()
/* 123:    */   {
/* 124:158 */     return this.parent;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setParent(Element parent)
/* 128:    */   {
/* 129:162 */     this.parent = parent;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean supportsParent()
/* 133:    */   {
/* 134:166 */     return true;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isReadOnly()
/* 138:    */   {
/* 139:170 */     return false;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void validate(String txt)
/* 143:    */     throws IllegalArgumentException
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:177 */       this.datatype.checkValid(txt, this);
/* 148:    */     }
/* 149:    */     catch (DatatypeException e)
/* 150:    */     {
/* 151:179 */       throw new IllegalArgumentException(e.getMessage());
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected Object convertToValue(String txt)
/* 156:    */   {
/* 157:184 */     if ((this.datatype instanceof DatabindableDatatype))
/* 158:    */     {
/* 159:185 */       DatabindableDatatype bindable = this.datatype;
/* 160:    */       
/* 161:187 */       return bindable.createJavaObject(txt, this);
/* 162:    */     }
/* 163:189 */     return this.datatype.createValue(txt, this);
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.datatype.DatatypeAttribute
 * JD-Core Version:    0.7.0.1
 */