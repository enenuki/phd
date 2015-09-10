/*   1:    */ package org.dom4j;
/*   2:    */ 
/*   3:    */ import org.dom4j.tree.AbstractNode;
/*   4:    */ import org.dom4j.tree.DefaultNamespace;
/*   5:    */ import org.dom4j.tree.NamespaceCache;
/*   6:    */ 
/*   7:    */ public class Namespace
/*   8:    */   extends AbstractNode
/*   9:    */ {
/*  10: 25 */   protected static final NamespaceCache CACHE = new NamespaceCache();
/*  11: 28 */   public static final Namespace XML_NAMESPACE = CACHE.get("xml", "http://www.w3.org/XML/1998/namespace");
/*  12: 32 */   public static final Namespace NO_NAMESPACE = CACHE.get("", "");
/*  13:    */   private String prefix;
/*  14:    */   private String uri;
/*  15:    */   private int hashCode;
/*  16:    */   
/*  17:    */   public Namespace(String prefix, String uri)
/*  18:    */   {
/*  19: 52 */     this.prefix = (prefix != null ? prefix : "");
/*  20: 53 */     this.uri = (uri != null ? uri : "");
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static Namespace get(String prefix, String uri)
/*  24:    */   {
/*  25: 68 */     return CACHE.get(prefix, uri);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static Namespace get(String uri)
/*  29:    */   {
/*  30: 81 */     return CACHE.get(uri);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public short getNodeType()
/*  34:    */   {
/*  35: 85 */     return 13;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int hashCode()
/*  39:    */   {
/*  40: 95 */     if (this.hashCode == 0) {
/*  41: 96 */       this.hashCode = createHashCode();
/*  42:    */     }
/*  43: 99 */     return this.hashCode;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected int createHashCode()
/*  47:    */   {
/*  48:109 */     int result = this.uri.hashCode() ^ this.prefix.hashCode();
/*  49:111 */     if (result == 0) {
/*  50:112 */       result = 47806;
/*  51:    */     }
/*  52:115 */     return result;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean equals(Object object)
/*  56:    */   {
/*  57:128 */     if (this == object) {
/*  58:129 */       return true;
/*  59:    */     }
/*  60:130 */     if ((object instanceof Namespace))
/*  61:    */     {
/*  62:131 */       Namespace that = (Namespace)object;
/*  63:134 */       if (hashCode() == that.hashCode()) {
/*  64:135 */         return (this.uri.equals(that.getURI())) && (this.prefix.equals(that.getPrefix()));
/*  65:    */       }
/*  66:    */     }
/*  67:140 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getText()
/*  71:    */   {
/*  72:144 */     return this.uri;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getStringValue()
/*  76:    */   {
/*  77:148 */     return this.uri;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getPrefix()
/*  81:    */   {
/*  82:157 */     return this.prefix;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getURI()
/*  86:    */   {
/*  87:166 */     return this.uri;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getXPathNameStep()
/*  91:    */   {
/*  92:170 */     if ((this.prefix != null) && (!"".equals(this.prefix))) {
/*  93:171 */       return "namespace::" + this.prefix;
/*  94:    */     }
/*  95:174 */     return "namespace::*[name()='']";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getPath(Element context)
/*  99:    */   {
/* 100:178 */     StringBuffer path = new StringBuffer(10);
/* 101:179 */     Element parent = getParent();
/* 102:181 */     if ((parent != null) && (parent != context))
/* 103:    */     {
/* 104:182 */       path.append(parent.getPath(context));
/* 105:183 */       path.append('/');
/* 106:    */     }
/* 107:186 */     path.append(getXPathNameStep());
/* 108:    */     
/* 109:188 */     return path.toString();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getUniquePath(Element context)
/* 113:    */   {
/* 114:192 */     StringBuffer path = new StringBuffer(10);
/* 115:193 */     Element parent = getParent();
/* 116:195 */     if ((parent != null) && (parent != context))
/* 117:    */     {
/* 118:196 */       path.append(parent.getUniquePath(context));
/* 119:197 */       path.append('/');
/* 120:    */     }
/* 121:200 */     path.append(getXPathNameStep());
/* 122:    */     
/* 123:202 */     return path.toString();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String toString()
/* 127:    */   {
/* 128:206 */     return super.toString() + " [Namespace: prefix " + getPrefix() + " mapped to URI \"" + getURI() + "\"]";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String asXML()
/* 132:    */   {
/* 133:211 */     StringBuffer asxml = new StringBuffer(10);
/* 134:212 */     String pref = getPrefix();
/* 135:214 */     if ((pref != null) && (pref.length() > 0))
/* 136:    */     {
/* 137:215 */       asxml.append("xmlns:");
/* 138:216 */       asxml.append(pref);
/* 139:217 */       asxml.append("=\"");
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:219 */       asxml.append("xmlns=\"");
/* 144:    */     }
/* 145:222 */     asxml.append(getURI());
/* 146:223 */     asxml.append("\"");
/* 147:    */     
/* 148:225 */     return asxml.toString();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void accept(Visitor visitor)
/* 152:    */   {
/* 153:229 */     visitor.visit(this);
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected Node createXPathResult(Element parent)
/* 157:    */   {
/* 158:233 */     return new DefaultNamespace(parent, getPrefix(), getURI());
/* 159:    */   }
/* 160:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.Namespace
 * JD-Core Version:    0.7.0.1
 */