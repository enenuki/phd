/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.configuration.ClassConfiguration;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.configuration.JavaScriptConfiguration;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDocument;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLHttpRequest;
/*  11:    */ import java.lang.reflect.Method;
/*  12:    */ import java.util.Map;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  17:    */ import org.apache.commons.lang.StringUtils;
/*  18:    */ import org.apache.commons.logging.Log;
/*  19:    */ import org.apache.commons.logging.LogFactory;
/*  20:    */ 
/*  21:    */ public class ActiveXObject
/*  22:    */   extends SimpleScriptable
/*  23:    */ {
/*  24: 51 */   private static final Log LOG = LogFactory.getLog(ActiveXObject.class);
/*  25:    */   
/*  26:    */   public static Scriptable jsConstructor(Context cx, Object[] args, Function ctorObj, boolean inNewExpr)
/*  27:    */   {
/*  28: 71 */     if ((args.length < 1) || (args.length > 2)) {
/*  29: 72 */       throw Context.reportRuntimeError("ActiveXObject Error: constructor must have one or two String parameters.");
/*  30:    */     }
/*  31: 75 */     if (args[0] == Context.getUndefinedValue()) {
/*  32: 76 */       throw Context.reportRuntimeError("ActiveXObject Error: constructor parameter is undefined.");
/*  33:    */     }
/*  34: 78 */     if (!(args[0] instanceof String)) {
/*  35: 79 */       throw Context.reportRuntimeError("ActiveXObject Error: constructor parameter must be a String.");
/*  36:    */     }
/*  37: 81 */     String activeXName = (String)args[0];
/*  38: 89 */     if (isXMLHttpRequest(activeXName)) {
/*  39: 90 */       return buildXMLHttpRequest();
/*  40:    */     }
/*  41: 93 */     if (isXMLDocument(activeXName)) {
/*  42: 94 */       return buildXMLDocument(getWindow(ctorObj).getWebWindow());
/*  43:    */     }
/*  44: 97 */     if (isXMLTemplate(activeXName)) {
/*  45: 98 */       return buildXSLTemplate();
/*  46:    */     }
/*  47:101 */     WebClient webClient = getWindow(ctorObj).getWebWindow().getWebClient();
/*  48:102 */     Map<String, String> map = webClient.getActiveXObjectMap();
/*  49:103 */     if (map != null)
/*  50:    */     {
/*  51:104 */       Object mapValue = map.get(activeXName);
/*  52:105 */       if (mapValue != null)
/*  53:    */       {
/*  54:106 */         String xClassString = (String)mapValue;
/*  55:107 */         Object object = null;
/*  56:    */         try
/*  57:    */         {
/*  58:109 */           Class<?> xClass = Class.forName(xClassString);
/*  59:110 */           object = xClass.newInstance();
/*  60:    */         }
/*  61:    */         catch (Exception e)
/*  62:    */         {
/*  63:113 */           throw Context.reportRuntimeError("ActiveXObject Error: failed instantiating class " + xClassString + " because " + e.getMessage() + ".");
/*  64:    */         }
/*  65:116 */         return Context.toObject(object, ctorObj);
/*  66:    */       }
/*  67:    */     }
/*  68:119 */     if ((webClient.isActiveXNative()) && (System.getProperty("os.name").contains("Windows"))) {
/*  69:    */       try
/*  70:    */       {
/*  71:121 */         return new ActiveXObjectImpl(activeXName);
/*  72:    */       }
/*  73:    */       catch (Exception e)
/*  74:    */       {
/*  75:124 */         LOG.warn("Error initiating Jacob", e);
/*  76:    */       }
/*  77:    */     }
/*  78:128 */     LOG.warn("Automation server can't create object for '" + activeXName + "'.");
/*  79:129 */     throw Context.reportRuntimeError("Automation server can't create object for '" + activeXName + "'.");
/*  80:    */   }
/*  81:    */   
/*  82:    */   static boolean isXMLHttpRequest(String name)
/*  83:    */   {
/*  84:138 */     if (name == null) {
/*  85:139 */       return false;
/*  86:    */     }
/*  87:141 */     name = name.toLowerCase();
/*  88:142 */     return ("Microsoft.XMLHTTP".equalsIgnoreCase(name)) || (name.startsWith("Msxml2.XMLHTTP".toLowerCase()));
/*  89:    */   }
/*  90:    */   
/*  91:    */   static boolean isXMLDocument(String name)
/*  92:    */   {
/*  93:151 */     if (name == null) {
/*  94:152 */       return false;
/*  95:    */     }
/*  96:154 */     name = name.toLowerCase();
/*  97:155 */     return ("Microsoft.XMLDOM".equalsIgnoreCase(name)) || (name.matches("msxml\\d*\\.domdocument.*")) || (name.matches("msxml\\d*\\.freethreadeddomdocument.*"));
/*  98:    */   }
/*  99:    */   
/* 100:    */   static boolean isXMLTemplate(String name)
/* 101:    */   {
/* 102:166 */     if (name == null) {
/* 103:167 */       return false;
/* 104:    */     }
/* 105:169 */     name = name.toLowerCase();
/* 106:170 */     return name.matches("msxml\\d*\\.xsltemplate.*");
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static Scriptable buildXMLHttpRequest()
/* 110:    */   {
/* 111:174 */     SimpleScriptable scriptable = new XMLHttpRequest(false);
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:179 */     addProperty(scriptable, "onreadystatechange", true, true);
/* 117:180 */     addProperty(scriptable, "readyState", true, false);
/* 118:181 */     addProperty(scriptable, "responseText", true, false);
/* 119:182 */     addProperty(scriptable, "responseXML", true, false);
/* 120:183 */     addProperty(scriptable, "status", true, false);
/* 121:184 */     addProperty(scriptable, "statusText", true, false);
/* 122:    */     
/* 123:    */ 
/* 124:187 */     addFunction(scriptable, "abort");
/* 125:188 */     addFunction(scriptable, "getAllResponseHeaders");
/* 126:189 */     addFunction(scriptable, "getResponseHeader");
/* 127:190 */     addFunction(scriptable, "open");
/* 128:191 */     addFunction(scriptable, "send");
/* 129:192 */     addFunction(scriptable, "setRequestHeader");
/* 130:    */     
/* 131:194 */     return scriptable;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static Scriptable buildXSLTemplate()
/* 135:    */   {
/* 136:198 */     SimpleScriptable scriptable = new XSLTemplate();
/* 137:    */     
/* 138:200 */     addProperty(scriptable, "stylesheet", true, true);
/* 139:201 */     addFunction(scriptable, "createProcessor");
/* 140:    */     
/* 141:203 */     return scriptable;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static XMLDocument buildXMLDocument(WebWindow enclosingWindow)
/* 145:    */   {
/* 146:212 */     XMLDocument document = new XMLDocument(enclosingWindow);
/* 147:    */     
/* 148:    */ 
/* 149:215 */     addProperty(document, "async", true, true);
/* 150:216 */     addProperty(document, "parseError", true, false);
/* 151:217 */     addProperty(document, "preserveWhiteSpace", true, true);
/* 152:218 */     addProperty(document, "xml", true, false);
/* 153:    */     
/* 154:    */ 
/* 155:221 */     addFunction(document, "createNode");
/* 156:222 */     addFunction(document, "createCDATASection");
/* 157:223 */     addFunction(document, "createProcessingInstruction");
/* 158:224 */     addFunction(document, "getElementsByTagName");
/* 159:225 */     addFunction(document, "load");
/* 160:226 */     addFunction(document, "loadXML");
/* 161:227 */     addFunction(document, "nodeFromID");
/* 162:228 */     addFunction(document, "selectNodes");
/* 163:229 */     addFunction(document, "selectSingleNode");
/* 164:230 */     addFunction(document, "setProperty");
/* 165:    */     
/* 166:232 */     JavaScriptConfiguration jsConfig = JavaScriptConfiguration.getInstance(BrowserVersion.INTERNET_EXPLORER_7);
/* 167:235 */     for (String className = "Document"; StringUtils.isNotBlank(className);)
/* 168:    */     {
/* 169:236 */       ClassConfiguration classConfig = jsConfig.getClassConfiguration(className);
/* 170:237 */       for (String function : classConfig.functionKeys()) {
/* 171:238 */         addFunction(document, function);
/* 172:    */       }
/* 173:240 */       for (String property : classConfig.propertyKeys()) {
/* 174:241 */         addProperty(document, property, classConfig.getPropertyReadMethod(property) != null, classConfig.getPropertyWriteMethod(property) != null);
/* 175:    */       }
/* 176:245 */       className = classConfig.getExtendedClassName();
/* 177:    */     }
/* 178:248 */     return document;
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static void addFunction(SimpleScriptable scriptable, String jsMethodName)
/* 182:    */   {
/* 183:253 */     addFunction(scriptable, jsMethodName, "jsxFunction_" + jsMethodName);
/* 184:    */   }
/* 185:    */   
/* 186:    */   private static void addFunction(SimpleScriptable scriptable, String jsMethodName, String javaMethodName)
/* 187:    */   {
/* 188:258 */     Method javaFunction = getMethod(scriptable.getClass(), javaMethodName);
/* 189:259 */     FunctionObject fo = new FunctionObject(null, javaFunction, scriptable);
/* 190:260 */     scriptable.defineProperty(jsMethodName, fo, 1);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static void addProperty(SimpleScriptable scriptable, String propertyName, boolean isGetter, boolean isSetter)
/* 194:    */   {
/* 195:272 */     String getterName = null;
/* 196:273 */     if (isGetter) {
/* 197:274 */       getterName = "jsxGet_" + propertyName;
/* 198:    */     }
/* 199:276 */     String setterName = null;
/* 200:277 */     if (isSetter) {
/* 201:278 */       setterName = "jsxSet_" + propertyName;
/* 202:    */     }
/* 203:280 */     addProperty(scriptable, propertyName, getterName, setterName);
/* 204:    */   }
/* 205:    */   
/* 206:    */   static void addProperty(SimpleScriptable scriptable, String propertyName, String getterName, String setterName)
/* 207:    */   {
/* 208:285 */     scriptable.defineProperty(propertyName, null, getMethod(scriptable.getClass(), getterName), getMethod(scriptable.getClass(), setterName), 4);
/* 209:    */   }
/* 210:    */   
/* 211:    */   static Method getMethod(Class<? extends SimpleScriptable> clazz, String name)
/* 212:    */   {
/* 213:297 */     if (name == null) {
/* 214:298 */       return null;
/* 215:    */     }
/* 216:300 */     for (Method method : clazz.getMethods()) {
/* 217:301 */       if (method.getName().equals(name)) {
/* 218:302 */         return method;
/* 219:    */       }
/* 220:    */     }
/* 221:305 */     return null;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getClassName()
/* 225:    */   {
/* 226:314 */     return "ActiveXObject";
/* 227:    */   }
/* 228:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.ActiveXObject
 * JD-Core Version:    0.7.0.1
 */