/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.Attr;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.Node;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  14:    */ 
/*  15:    */ public class NamedNodeMap
/*  16:    */   extends SimpleScriptable
/*  17:    */   implements ScriptableWithFallbackGetter
/*  18:    */ {
/*  19:    */   private final org.w3c.dom.NamedNodeMap attributes_;
/*  20:    */   
/*  21:    */   public NamedNodeMap()
/*  22:    */   {
/*  23: 48 */     this.attributes_ = null;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public NamedNodeMap(DomElement element)
/*  27:    */   {
/*  28: 57 */     setParentScope(element.getScriptObject());
/*  29: 58 */     setPrototype(getPrototype(getClass()));
/*  30:    */     
/*  31: 60 */     this.attributes_ = element.getAttributes();
/*  32: 61 */     setDomNode(element, false);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final Object get(int index, Scriptable start)
/*  36:    */   {
/*  37: 71 */     NamedNodeMap startMap = (NamedNodeMap)start;
/*  38: 72 */     Object response = startMap.jsxFunction_item(index);
/*  39: 73 */     if (response != null) {
/*  40: 74 */       return response;
/*  41:    */     }
/*  42: 76 */     return NOT_FOUND;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Object getWithFallback(String name)
/*  46:    */   {
/*  47: 85 */     Object response = jsxFunction_getNamedItem(name);
/*  48: 86 */     if (response != null) {
/*  49: 87 */       return response;
/*  50:    */     }
/*  51: 89 */     if ((useRecursiveAttributeForIE()) && (isRecursiveAttribute(name))) {
/*  52: 90 */       return getUnspecifiedAttributeNode(name);
/*  53:    */     }
/*  54: 93 */     return NOT_FOUND;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object jsxFunction_getNamedItem(String name)
/*  58:    */   {
/*  59:102 */     DomNode attr = (DomNode)this.attributes_.getNamedItem(name);
/*  60:103 */     if (attr != null) {
/*  61:104 */       return attr.getScriptObject();
/*  62:    */     }
/*  63:106 */     if ((!"className".equals(name)) && (useRecursiveAttributeForIE()) && (isRecursiveAttribute(name))) {
/*  64:107 */       return getUnspecifiedAttributeNode(name);
/*  65:    */     }
/*  66:109 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void jsxFunction_setNamedItem(Node node)
/*  70:    */   {
/*  71:117 */     this.attributes_.setNamedItem(node.getDomNodeOrDie());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void jsxFunction_removeNamedItem(String name)
/*  75:    */   {
/*  76:125 */     this.attributes_.removeNamedItem(name);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Object jsxFunction_item(int index)
/*  80:    */   {
/*  81:134 */     DomNode attr = (DomNode)this.attributes_.item(index);
/*  82:135 */     if (attr != null) {
/*  83:136 */       return attr.getScriptObject();
/*  84:    */     }
/*  85:138 */     if (useRecursiveAttributeForIE())
/*  86:    */     {
/*  87:139 */       index -= this.attributes_.getLength();
/*  88:140 */       String name = getRecusiveAttributeNameAt(index);
/*  89:141 */       if (name != null) {
/*  90:142 */         return getUnspecifiedAttributeNode(name);
/*  91:    */       }
/*  92:    */     }
/*  93:145 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private boolean useRecursiveAttributeForIE()
/*  97:    */   {
/*  98:149 */     return (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_148)) && ((getDomNodeOrDie() instanceof HtmlElement));
/*  99:    */   }
/* 100:    */   
/* 101:    */   private Attr getUnspecifiedAttributeNode(String attrName)
/* 102:    */   {
/* 103:158 */     HtmlElement domNode = (HtmlElement)getDomNodeOrDie();
/* 104:    */     
/* 105:160 */     DomAttr attr = domNode.getPage().createAttribute(attrName);
/* 106:161 */     domNode.setAttributeNode(attr);
/* 107:162 */     return (Attr)attr.getScriptObject();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int jsxGet_length()
/* 111:    */   {
/* 112:170 */     int length = this.attributes_.getLength();
/* 113:171 */     if (useRecursiveAttributeForIE()) {
/* 114:172 */       length += getRecursiveAttributesLength();
/* 115:    */     }
/* 116:174 */     return length;
/* 117:    */   }
/* 118:    */   
/* 119:    */   private boolean isRecursiveAttribute(String name)
/* 120:    */   {
/* 121:178 */     for (Scriptable object = getDomNodeOrDie().getScriptObject(); object != null; object = object.getPrototype()) {
/* 122:180 */       for (Object id : object.getIds()) {
/* 123:181 */         if (name.equals(Context.toString(id))) {
/* 124:182 */           return true;
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:186 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private int getRecursiveAttributesLength()
/* 132:    */   {
/* 133:190 */     int length = 0;
/* 134:191 */     for (Scriptable object = getDomNodeOrDie().getScriptObject(); object != null; object = object.getPrototype()) {
/* 135:193 */       length += object.getIds().length;
/* 136:    */     }
/* 137:195 */     return length;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private String getRecusiveAttributeNameAt(int index)
/* 141:    */   {
/* 142:199 */     int i = 0;
/* 143:200 */     for (Scriptable object = getDomNodeOrDie().getScriptObject(); object != null; object = object.getPrototype()) {
/* 144:202 */       for (Object id : object.getIds())
/* 145:    */       {
/* 146:203 */         if (i == index) {
/* 147:204 */           return Context.toString(id);
/* 148:    */         }
/* 149:206 */         i++;
/* 150:    */       }
/* 151:    */     }
/* 152:209 */     return null;
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.NamedNodeMap
 * JD-Core Version:    0.7.0.1
 */