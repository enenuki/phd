/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  10:    */ import org.apache.commons.lang.ArrayUtils;
/*  11:    */ import org.apache.commons.lang.ClassUtils;
/*  12:    */ import org.w3c.dom.NamedNodeMap;
/*  13:    */ import org.w3c.dom.NodeList;
/*  14:    */ 
/*  15:    */ public class ScriptableWrapper
/*  16:    */   extends ScriptableObject
/*  17:    */ {
/*  18: 42 */   private final Map<String, Method> properties_ = new HashMap();
/*  19:    */   private Method getByIndexMethod_;
/*  20:    */   private final Object javaObject_;
/*  21:    */   private final String jsClassName_;
/*  22:    */   private Method getByNameFallback_;
/*  23:    */   
/*  24:    */   public ScriptableWrapper(Scriptable scope, Object javaObject, Class<?> staticType)
/*  25:    */   {
/*  26: 57 */     this.javaObject_ = javaObject;
/*  27: 58 */     setParentScope(scope);
/*  28: 62 */     if ((NodeList.class.equals(staticType)) || (NamedNodeMap.class.equals(staticType))) {
/*  29:    */       try
/*  30:    */       {
/*  31: 65 */         this.jsClassName_ = ClassUtils.getShortClassName(staticType);
/*  32:    */         
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38: 72 */         Method length = javaObject.getClass().getMethod("getLength", ArrayUtils.EMPTY_CLASS_ARRAY);
/*  39:    */         
/*  40: 74 */         this.properties_.put("length", length);
/*  41:    */         
/*  42: 76 */         Method item = javaObject.getClass().getMethod("item", new Class[] { Integer.TYPE });
/*  43:    */         
/*  44: 78 */         defineProperty("item", new MethodWrapper("item", staticType, new Class[] { Integer.TYPE }), 0);
/*  45:    */         
/*  46:    */ 
/*  47: 81 */         Method toString = getClass().getMethod("jsToString", ArrayUtils.EMPTY_CLASS_ARRAY);
/*  48:    */         
/*  49: 83 */         defineProperty("toString", new FunctionObject("toString", toString, this), 0);
/*  50:    */         
/*  51:    */ 
/*  52: 86 */         this.getByIndexMethod_ = item;
/*  53: 88 */         if (NamedNodeMap.class.equals(staticType))
/*  54:    */         {
/*  55: 89 */           Method getNamedItem = javaObject.getClass().getMethod("getNamedItem", new Class[] { String.class });
/*  56:    */           
/*  57:    */ 
/*  58: 92 */           defineProperty("getNamedItem", new MethodWrapper("getNamedItem", staticType, new Class[] { String.class }), 0);
/*  59:    */           
/*  60:    */ 
/*  61: 95 */           this.getByNameFallback_ = getNamedItem;
/*  62:    */         }
/*  63:    */       }
/*  64:    */       catch (Exception e)
/*  65:    */       {
/*  66: 99 */         throw new RuntimeException("Method not found", e);
/*  67:    */       }
/*  68:    */     } else {
/*  69:103 */       throw new RuntimeException("Unknown type: " + staticType.getName());
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object get(String name, Scriptable start)
/*  74:    */   {
/*  75:113 */     Method propertyGetter = (Method)this.properties_.get(name);
/*  76:    */     Object response;
/*  77:    */     Object response;
/*  78:115 */     if (propertyGetter != null)
/*  79:    */     {
/*  80:116 */       response = invoke(propertyGetter);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:119 */       Object fromSuper = super.get(name, start);
/*  85:    */       Object response;
/*  86:120 */       if (fromSuper != Scriptable.NOT_FOUND)
/*  87:    */       {
/*  88:121 */         response = fromSuper;
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:124 */         Object byName = invoke(this.getByNameFallback_, new Object[] { name });
/*  93:    */         Object response;
/*  94:126 */         if (byName != null) {
/*  95:127 */           response = byName;
/*  96:    */         } else {
/*  97:130 */           response = Scriptable.NOT_FOUND;
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:135 */     return Context.javaToJS(response, ScriptableObject.getTopLevelScope(start));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean has(String name, Scriptable start)
/* 105:    */   {
/* 106:145 */     return (this.properties_.containsKey(name)) || (super.has(name, start));
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected Object invoke(Method method)
/* 110:    */   {
/* 111:154 */     return invoke(method, ArrayUtils.EMPTY_OBJECT_ARRAY);
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected Object invoke(Method method, Object[] args)
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:165 */       return method.invoke(this.javaObject_, args);
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:168 */       throw new RuntimeException("Invocation of method on java object failed", e);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Object get(int index, Scriptable start)
/* 127:    */   {
/* 128:179 */     if (this.getByIndexMethod_ != null)
/* 129:    */     {
/* 130:180 */       Object byIndex = invoke(this.getByIndexMethod_, new Object[] { Integer.valueOf(index) });
/* 131:    */       
/* 132:182 */       return Context.javaToJS(byIndex, ScriptableObject.getTopLevelScope(start));
/* 133:    */     }
/* 134:185 */     return super.get(index, start);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Object getDefaultValue(Class<?> hint)
/* 138:    */   {
/* 139:194 */     if ((String.class.equals(hint)) || (hint == null)) {
/* 140:195 */       return jsToString();
/* 141:    */     }
/* 142:197 */     return super.getDefaultValue(hint);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String jsToString()
/* 146:    */   {
/* 147:205 */     return "[object " + getClassName() + "]";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getClassName()
/* 151:    */   {
/* 152:214 */     return this.jsClassName_;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Object getWrappedObject()
/* 156:    */   {
/* 157:222 */     return this.javaObject_;
/* 158:    */   }
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.ScriptableWrapper
 * JD-Core Version:    0.7.0.1
 */