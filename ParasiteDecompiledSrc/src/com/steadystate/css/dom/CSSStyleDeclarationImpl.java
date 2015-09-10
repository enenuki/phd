/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.io.StringReader;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.dom.DOMException;
/*  10:    */ import org.w3c.dom.css.CSSRule;
/*  11:    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*  12:    */ import org.w3c.dom.css.CSSValue;
/*  13:    */ 
/*  14:    */ public class CSSStyleDeclarationImpl
/*  15:    */   implements CSSStyleDeclaration, Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -2373755821317100189L;
/*  18:    */   private CSSRule parentRule;
/*  19: 57 */   private List<Property> properties = new ArrayList();
/*  20:    */   
/*  21:    */   public void setParentRule(CSSRule parentRule)
/*  22:    */   {
/*  23: 61 */     this.parentRule = parentRule;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<Property> getProperties()
/*  27:    */   {
/*  28: 66 */     return this.properties;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setProperties(List<Property> properties)
/*  32:    */   {
/*  33: 71 */     this.properties = properties;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public CSSStyleDeclarationImpl(CSSRule parentRule)
/*  37:    */   {
/*  38: 76 */     this.parentRule = parentRule;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public CSSStyleDeclarationImpl() {}
/*  42:    */   
/*  43:    */   public String getCssText()
/*  44:    */   {
/*  45: 84 */     StringBuilder sb = new StringBuilder();
/*  46: 85 */     for (int i = 0; i < this.properties.size(); i++)
/*  47:    */     {
/*  48: 86 */       Property p = (Property)this.properties.get(i);
/*  49: 87 */       if (p != null) {
/*  50: 88 */         sb.append(p.toString());
/*  51:    */       }
/*  52: 90 */       if (i < this.properties.size() - 1) {
/*  53: 91 */         sb.append("; ");
/*  54:    */       }
/*  55:    */     }
/*  56: 94 */     return sb.toString();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setCssText(String cssText)
/*  60:    */     throws DOMException
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 99 */       InputSource is = new InputSource(new StringReader(cssText));
/*  65:100 */       CSSOMParser parser = new CSSOMParser();
/*  66:101 */       this.properties.clear();
/*  67:102 */       parser.parseStyleDeclaration(this, is);
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71:104 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getPropertyValue(String propertyName)
/*  76:    */   {
/*  77:112 */     Property p = getPropertyDeclaration(propertyName);
/*  78:113 */     return p != null ? p.getValue().toString() : "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public CSSValue getPropertyCSSValue(String propertyName)
/*  82:    */   {
/*  83:117 */     Property p = getPropertyDeclaration(propertyName);
/*  84:118 */     return p != null ? p.getValue() : null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String removeProperty(String propertyName)
/*  88:    */     throws DOMException
/*  89:    */   {
/*  90:122 */     for (int i = 0; i < this.properties.size(); i++)
/*  91:    */     {
/*  92:123 */       Property p = (Property)this.properties.get(i);
/*  93:124 */       if (p.getName().equalsIgnoreCase(propertyName))
/*  94:    */       {
/*  95:125 */         this.properties.remove(i);
/*  96:126 */         return p.getValue().toString();
/*  97:    */       }
/*  98:    */     }
/*  99:129 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getPropertyPriority(String propertyName)
/* 103:    */   {
/* 104:133 */     Property p = getPropertyDeclaration(propertyName);
/* 105:134 */     if (p != null) {
/* 106:135 */       return p.isImportant() ? "important" : "";
/* 107:    */     }
/* 108:137 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setProperty(String propertyName, String value, String priority)
/* 112:    */     throws DOMException
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:145 */       InputSource is = new InputSource(new StringReader(value));
/* 117:146 */       CSSOMParser parser = new CSSOMParser();
/* 118:147 */       CSSValue expr = parser.parsePropertyValue(is);
/* 119:148 */       Property p = getPropertyDeclaration(propertyName);
/* 120:149 */       boolean important = priority != null ? priority.equalsIgnoreCase("important") : false;
/* 121:152 */       if (p == null)
/* 122:    */       {
/* 123:153 */         p = new Property(propertyName, expr, important);
/* 124:154 */         addProperty(p);
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:156 */         p.setValue(expr);
/* 129:157 */         p.setImportant(important);
/* 130:    */       }
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:160 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getLength()
/* 139:    */   {
/* 140:168 */     return this.properties.size();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String item(int index)
/* 144:    */   {
/* 145:172 */     Property p = (Property)this.properties.get(index);
/* 146:173 */     return p != null ? p.getName() : "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public CSSRule getParentRule()
/* 150:    */   {
/* 151:177 */     return this.parentRule;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void addProperty(Property p)
/* 155:    */   {
/* 156:181 */     this.properties.add(p);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Property getPropertyDeclaration(String name)
/* 160:    */   {
/* 161:185 */     for (int i = 0; i < this.properties.size(); i++)
/* 162:    */     {
/* 163:186 */       Property p = (Property)this.properties.get(i);
/* 164:187 */       if (p.getName().equalsIgnoreCase(name)) {
/* 165:188 */         return p;
/* 166:    */       }
/* 167:    */     }
/* 168:191 */     return null;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String toString()
/* 172:    */   {
/* 173:195 */     return getCssText();
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSStyleDeclarationImpl
 * JD-Core Version:    0.7.0.1
 */