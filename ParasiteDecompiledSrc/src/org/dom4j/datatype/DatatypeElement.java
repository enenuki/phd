/*   1:    */ package org.dom4j.datatype;
/*   2:    */ 
/*   3:    */ import com.sun.msv.datatype.DatabindableDatatype;
/*   4:    */ import com.sun.msv.datatype.SerializationContext;
/*   5:    */ import com.sun.msv.datatype.xsd.XSDatatype;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.Namespace;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ import org.dom4j.QName;
/*  10:    */ import org.dom4j.tree.DefaultElement;
/*  11:    */ import org.relaxng.datatype.DatatypeException;
/*  12:    */ import org.relaxng.datatype.ValidationContext;
/*  13:    */ 
/*  14:    */ public class DatatypeElement
/*  15:    */   extends DefaultElement
/*  16:    */   implements SerializationContext, ValidationContext
/*  17:    */ {
/*  18:    */   private XSDatatype datatype;
/*  19:    */   private Object data;
/*  20:    */   
/*  21:    */   public DatatypeElement(QName qname, XSDatatype datatype)
/*  22:    */   {
/*  23: 42 */     super(qname);
/*  24: 43 */     this.datatype = datatype;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public DatatypeElement(QName qname, int attributeCount, XSDatatype type)
/*  28:    */   {
/*  29: 47 */     super(qname, attributeCount);
/*  30: 48 */     this.datatype = type;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toString()
/*  34:    */   {
/*  35: 52 */     return getClass().getName() + hashCode() + " [Element: <" + getQualifiedName() + " attributes: " + attributeList() + " data: " + getData() + " />]";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public XSDatatype getXSDatatype()
/*  39:    */   {
/*  40: 63 */     return this.datatype;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getNamespacePrefix(String uri)
/*  44:    */   {
/*  45: 69 */     Namespace namespace = getNamespaceForURI(uri);
/*  46:    */     
/*  47: 71 */     return namespace != null ? namespace.getPrefix() : null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getBaseUri()
/*  51:    */   {
/*  52: 78 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isNotation(String notationName)
/*  56:    */   {
/*  57: 83 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isUnparsedEntity(String entityName)
/*  61:    */   {
/*  62: 88 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String resolveNamespacePrefix(String prefix)
/*  66:    */   {
/*  67: 92 */     Namespace namespace = getNamespaceForPrefix(prefix);
/*  68: 94 */     if (namespace != null) {
/*  69: 95 */       return namespace.getURI();
/*  70:    */     }
/*  71: 98 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object getData()
/*  75:    */   {
/*  76:104 */     if (this.data == null)
/*  77:    */     {
/*  78:105 */       String text = getTextTrim();
/*  79:107 */       if ((text != null) && (text.length() > 0)) {
/*  80:108 */         if ((this.datatype instanceof DatabindableDatatype))
/*  81:    */         {
/*  82:109 */           DatabindableDatatype bind = this.datatype;
/*  83:110 */           this.data = bind.createJavaObject(text, this);
/*  84:    */         }
/*  85:    */         else
/*  86:    */         {
/*  87:112 */           this.data = this.datatype.createValue(text, this);
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:117 */     return this.data;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setData(Object data)
/*  95:    */   {
/*  96:121 */     String s = this.datatype.convertToLexicalValue(data, this);
/*  97:122 */     validate(s);
/*  98:123 */     this.data = data;
/*  99:124 */     setText(s);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Element addText(String text)
/* 103:    */   {
/* 104:128 */     validate(text);
/* 105:    */     
/* 106:130 */     return super.addText(text);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setText(String text)
/* 110:    */   {
/* 111:134 */     validate(text);
/* 112:135 */     super.setText(text);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected void childAdded(Node node)
/* 116:    */   {
/* 117:148 */     this.data = null;
/* 118:149 */     super.childAdded(node);
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected void childRemoved(Node node)
/* 122:    */   {
/* 123:159 */     this.data = null;
/* 124:160 */     super.childRemoved(node);
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void validate(String text)
/* 128:    */     throws IllegalArgumentException
/* 129:    */   {
/* 130:    */     try
/* 131:    */     {
/* 132:165 */       this.datatype.checkValid(text, this);
/* 133:    */     }
/* 134:    */     catch (DatatypeException e)
/* 135:    */     {
/* 136:167 */       throw new IllegalArgumentException(e.getMessage());
/* 137:    */     }
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.datatype.DatatypeElement
 * JD-Core Version:    0.7.0.1
 */