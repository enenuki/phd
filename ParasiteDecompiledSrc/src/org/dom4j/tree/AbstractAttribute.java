/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Writer;
/*   5:    */ import org.dom4j.Attribute;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.Namespace;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ import org.dom4j.QName;
/*  10:    */ import org.dom4j.Visitor;
/*  11:    */ 
/*  12:    */ public abstract class AbstractAttribute
/*  13:    */   extends AbstractNode
/*  14:    */   implements Attribute
/*  15:    */ {
/*  16:    */   public short getNodeType()
/*  17:    */   {
/*  18: 31 */     return 2;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setNamespace(Namespace namespace)
/*  22:    */   {
/*  23: 35 */     String msg = "This Attribute is read only and cannot be changed";
/*  24: 36 */     throw new UnsupportedOperationException(msg);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getText()
/*  28:    */   {
/*  29: 40 */     return getValue();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setText(String text)
/*  33:    */   {
/*  34: 44 */     setValue(text);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setValue(String value)
/*  38:    */   {
/*  39: 48 */     String msg = "This Attribute is read only and cannot be changed";
/*  40: 49 */     throw new UnsupportedOperationException(msg);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object getData()
/*  44:    */   {
/*  45: 53 */     return getValue();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setData(Object data)
/*  49:    */   {
/*  50: 57 */     setValue(data == null ? null : data.toString());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55: 61 */     return super.toString() + " [Attribute: name " + getQualifiedName() + " value \"" + getValue() + "\"]";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String asXML()
/*  59:    */   {
/*  60: 66 */     return getQualifiedName() + "=\"" + getValue() + "\"";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void write(Writer writer)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66: 70 */     writer.write(getQualifiedName());
/*  67: 71 */     writer.write("=\"");
/*  68: 72 */     writer.write(getValue());
/*  69: 73 */     writer.write("\"");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void accept(Visitor visitor)
/*  73:    */   {
/*  74: 77 */     visitor.visit(this);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Namespace getNamespace()
/*  78:    */   {
/*  79: 82 */     return getQName().getNamespace();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getName()
/*  83:    */   {
/*  84: 86 */     return getQName().getName();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getNamespacePrefix()
/*  88:    */   {
/*  89: 90 */     return getQName().getNamespacePrefix();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getNamespaceURI()
/*  93:    */   {
/*  94: 94 */     return getQName().getNamespaceURI();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getQualifiedName()
/*  98:    */   {
/*  99: 98 */     return getQName().getQualifiedName();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getPath(Element context)
/* 103:    */   {
/* 104:102 */     StringBuffer result = new StringBuffer();
/* 105:    */     
/* 106:104 */     Element parent = getParent();
/* 107:106 */     if ((parent != null) && (parent != context))
/* 108:    */     {
/* 109:107 */       result.append(parent.getPath(context));
/* 110:108 */       result.append("/");
/* 111:    */     }
/* 112:111 */     result.append("@");
/* 113:    */     
/* 114:113 */     String uri = getNamespaceURI();
/* 115:114 */     String prefix = getNamespacePrefix();
/* 116:116 */     if ((uri == null) || (uri.length() == 0) || (prefix == null) || (prefix.length() == 0)) {
/* 117:118 */       result.append(getName());
/* 118:    */     } else {
/* 119:120 */       result.append(getQualifiedName());
/* 120:    */     }
/* 121:123 */     return result.toString();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getUniquePath(Element context)
/* 125:    */   {
/* 126:127 */     StringBuffer result = new StringBuffer();
/* 127:    */     
/* 128:129 */     Element parent = getParent();
/* 129:131 */     if ((parent != null) && (parent != context))
/* 130:    */     {
/* 131:132 */       result.append(parent.getUniquePath(context));
/* 132:133 */       result.append("/");
/* 133:    */     }
/* 134:136 */     result.append("@");
/* 135:    */     
/* 136:138 */     String uri = getNamespaceURI();
/* 137:139 */     String prefix = getNamespacePrefix();
/* 138:141 */     if ((uri == null) || (uri.length() == 0) || (prefix == null) || (prefix.length() == 0)) {
/* 139:143 */       result.append(getName());
/* 140:    */     } else {
/* 141:145 */       result.append(getQualifiedName());
/* 142:    */     }
/* 143:148 */     return result.toString();
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected Node createXPathResult(Element parent)
/* 147:    */   {
/* 148:152 */     return new DefaultAttribute(parent, getQName(), getValue());
/* 149:    */   }
/* 150:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractAttribute
 * JD-Core Version:    0.7.0.1
 */