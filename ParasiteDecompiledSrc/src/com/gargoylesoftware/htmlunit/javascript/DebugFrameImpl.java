/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Callable;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.EcmaError;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.IdFunctionObject;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeFunction;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*  14:    */ import org.apache.commons.lang.StringUtils;
/*  15:    */ import org.apache.commons.logging.Log;
/*  16:    */ import org.apache.commons.logging.LogFactory;
/*  17:    */ 
/*  18:    */ public class DebugFrameImpl
/*  19:    */   extends DebugFrameAdapter
/*  20:    */ {
/*  21: 57 */   private static final Log LOG = LogFactory.getLog(DebugFrameImpl.class);
/*  22:    */   private static final String KEY_LAST_LINE = "DebugFrameImpl#line";
/*  23:    */   private static final String KEY_LAST_SOURCE = "DebugFrameImpl#source";
/*  24:    */   private final DebuggableScript functionOrScript_;
/*  25:    */   
/*  26:    */   public DebugFrameImpl(DebuggableScript functionOrScript)
/*  27:    */   {
/*  28: 70 */     this.functionOrScript_ = functionOrScript;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void onEnter(Context cx, Scriptable activation, Scriptable thisObj, Object[] args)
/*  32:    */   {
/*  33: 78 */     if (LOG.isTraceEnabled())
/*  34:    */     {
/*  35: 79 */       StringBuilder sb = new StringBuilder();
/*  36:    */       
/*  37: 81 */       String line = getFirstLine(cx);
/*  38: 82 */       String source = getSourceName(cx);
/*  39: 83 */       sb.append(source).append(":").append(line).append(" ");
/*  40:    */       
/*  41: 85 */       Scriptable parent = activation.getParentScope();
/*  42: 86 */       while (parent != null)
/*  43:    */       {
/*  44: 87 */         sb.append("   ");
/*  45: 88 */         parent = parent.getParentScope();
/*  46:    */       }
/*  47: 90 */       String functionName = getFunctionName(thisObj);
/*  48: 91 */       sb.append(functionName).append("(");
/*  49: 92 */       int nbParams = this.functionOrScript_.getParamCount();
/*  50: 93 */       for (int i = 0; i < nbParams; i++)
/*  51:    */       {
/*  52:    */         String argAsString;
/*  53:    */         String argAsString;
/*  54: 95 */         if (i < args.length) {
/*  55: 96 */           argAsString = stringValue(args[i]);
/*  56:    */         } else {
/*  57: 99 */           argAsString = "undefined";
/*  58:    */         }
/*  59:101 */         sb.append(getParamName(i)).append(": ").append(argAsString);
/*  60:102 */         if (i < nbParams - 1) {
/*  61:103 */           sb.append(", ");
/*  62:    */         }
/*  63:    */       }
/*  64:106 */       sb.append(")");
/*  65:    */       
/*  66:108 */       LOG.trace(sb);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   private String stringValue(Object arg)
/*  71:    */   {
/*  72:113 */     if ((arg instanceof NativeFunction))
/*  73:    */     {
/*  74:116 */       String name = StringUtils.defaultIfEmpty(((NativeFunction)arg).getFunctionName(), "anonymous");
/*  75:117 */       return "[function " + name + "]";
/*  76:    */     }
/*  77:119 */     if ((arg instanceof IdFunctionObject)) {
/*  78:120 */       return "[function " + ((IdFunctionObject)arg).getFunctionName() + "]";
/*  79:    */     }
/*  80:122 */     if ((arg instanceof Function)) {
/*  81:123 */       return "[function anonymous]";
/*  82:    */     }
/*  83:125 */     String asString = null;
/*  84:    */     try
/*  85:    */     {
/*  86:128 */       asString = Context.toString(arg);
/*  87:129 */       if ((arg instanceof Event)) {
/*  88:130 */         asString = asString + "<" + ((Event)arg).jsxGet_type() + ">";
/*  89:    */       }
/*  90:    */     }
/*  91:    */     catch (Throwable e)
/*  92:    */     {
/*  93:135 */       asString = String.valueOf(arg);
/*  94:    */     }
/*  95:137 */     return asString;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void onExceptionThrown(Context cx, Throwable t)
/*  99:    */   {
/* 100:145 */     if (LOG.isTraceEnabled()) {
/* 101:146 */       if ((t instanceof JavaScriptException))
/* 102:    */       {
/* 103:147 */         JavaScriptException e = (JavaScriptException)t;
/* 104:148 */         LOG.trace(getSourceName(cx) + ":" + getFirstLine(cx) + " Exception thrown: " + Context.toString(e.details()));
/* 105:    */       }
/* 106:151 */       else if ((t instanceof EcmaError))
/* 107:    */       {
/* 108:152 */         EcmaError e = (EcmaError)t;
/* 109:153 */         LOG.trace(getSourceName(cx) + ":" + getFirstLine(cx) + " Exception thrown: " + Context.toString(e.details()));
/* 110:    */       }
/* 111:    */       else
/* 112:    */       {
/* 113:157 */         LOG.trace(getSourceName(cx) + ":" + getFirstLine(cx) + " Exception thrown: " + t.getCause());
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void onLineChange(Context cx, int lineNumber)
/* 119:    */   {
/* 120:167 */     cx.putThreadLocal("DebugFrameImpl#line", Integer.valueOf(lineNumber));
/* 121:168 */     cx.putThreadLocal("DebugFrameImpl#source", this.functionOrScript_.getSourceName());
/* 122:    */   }
/* 123:    */   
/* 124:    */   private String getFunctionName(Scriptable thisObj)
/* 125:    */   {
/* 126:183 */     if (this.functionOrScript_.isFunction())
/* 127:    */     {
/* 128:184 */       String name = this.functionOrScript_.getFunctionName();
/* 129:185 */       if ((name != null) && (name.length() > 0)) {
/* 130:187 */         return name;
/* 131:    */       }
/* 132:194 */       if ((thisObj instanceof SimpleScriptable)) {
/* 133:195 */         return "[anonymous]";
/* 134:    */       }
/* 135:198 */       Scriptable obj = thisObj;
/* 136:199 */       while (obj != null)
/* 137:    */       {
/* 138:200 */         for (Object id : obj.getIds()) {
/* 139:201 */           if ((id instanceof String))
/* 140:    */           {
/* 141:202 */             String s = (String)id;
/* 142:203 */             if ((obj instanceof ScriptableObject))
/* 143:    */             {
/* 144:204 */               Object o = ((ScriptableObject)obj).getGetterOrSetter(s, 0, false);
/* 145:205 */               if (o == null)
/* 146:    */               {
/* 147:206 */                 o = ((ScriptableObject)obj).getGetterOrSetter(s, 0, true);
/* 148:207 */                 if ((o != null) && ((o instanceof Callable))) {
/* 149:208 */                   return "__defineSetter__ " + s;
/* 150:    */                 }
/* 151:    */               }
/* 152:211 */               else if ((o instanceof Callable))
/* 153:    */               {
/* 154:212 */                 return "__defineGetter__ " + s;
/* 155:    */               }
/* 156:    */             }
/* 157:    */             Object o;
/* 158:    */             try
/* 159:    */             {
/* 160:218 */               o = obj.get(s, obj);
/* 161:    */             }
/* 162:    */             catch (Exception e)
/* 163:    */             {
/* 164:221 */               return "[anonymous]";
/* 165:    */             }
/* 166:223 */             if ((o instanceof NativeFunction))
/* 167:    */             {
/* 168:224 */               NativeFunction f = (NativeFunction)o;
/* 169:225 */               if (f.getDebuggableView() == this.functionOrScript_) {
/* 170:226 */                 return s;
/* 171:    */               }
/* 172:    */             }
/* 173:    */           }
/* 174:    */         }
/* 175:231 */         obj = obj.getPrototype();
/* 176:    */       }
/* 177:234 */       return "[anonymous]";
/* 178:    */     }
/* 179:237 */     return "[script]";
/* 180:    */   }
/* 181:    */   
/* 182:    */   private String getParamName(int index)
/* 183:    */   {
/* 184:248 */     if ((index >= 0) && (this.functionOrScript_.getParamCount() > index)) {
/* 185:249 */       return this.functionOrScript_.getParamOrVarName(index);
/* 186:    */     }
/* 187:251 */     return "???";
/* 188:    */   }
/* 189:    */   
/* 190:    */   private String getSourceName(Context cx)
/* 191:    */   {
/* 192:260 */     String source = (String)cx.getThreadLocal("DebugFrameImpl#source");
/* 193:261 */     if (source == null) {
/* 194:262 */       return "unknown";
/* 195:    */     }
/* 196:265 */     source = StringUtils.substringAfterLast(source, "/");
/* 197:    */     
/* 198:267 */     source = StringUtils.substringBefore(source, " ");
/* 199:268 */     return source;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private String getFirstLine(Context cx)
/* 203:    */   {
/* 204:279 */     Object line = cx.getThreadLocal("DebugFrameImpl#line");
/* 205:    */     String result;
/* 206:    */     String result;
/* 207:281 */     if (line == null) {
/* 208:282 */       result = "??";
/* 209:    */     } else {
/* 210:285 */       result = String.valueOf(line);
/* 211:    */     }
/* 212:287 */     return StringUtils.leftPad(result, 5);
/* 213:    */   }
/* 214:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.DebugFrameImpl
 * JD-Core Version:    0.7.0.1
 */