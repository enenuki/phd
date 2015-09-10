/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   9:    */ 
/*  10:    */ public class ActiveXObjectImpl
/*  11:    */   extends SimpleScriptable
/*  12:    */ {
/*  13:    */   private static final Class<?> activeXComponentClass_;
/*  14:    */   private static final Method METHOD_getProperty_;
/*  15:    */   private final Object object_;
/*  16:    */   private static final Method METHOD_callN_;
/*  17:    */   private static final Method METHOD_getvt_;
/*  18:    */   private static final Method METHOD_getDispatch_;
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24: 50 */       activeXComponentClass_ = Class.forName("com.jacob.activeX.ActiveXComponent");
/*  25: 51 */       METHOD_getProperty_ = activeXComponentClass_.getMethod("getProperty", new Class[] { String.class });
/*  26: 52 */       Class<?> dispatchClass = Class.forName("com.jacob.com.Dispatch");
/*  27: 53 */       METHOD_callN_ = dispatchClass.getMethod("callN", new Class[] { dispatchClass, String.class, [Ljava.lang.Object.class });
/*  28: 54 */       Class<?> variantClass = Class.forName("com.jacob.com.Variant");
/*  29: 55 */       METHOD_getvt_ = variantClass.getMethod("getvt", new Class[0]);
/*  30: 56 */       METHOD_getDispatch_ = variantClass.getMethod("getDispatch", new Class[0]);
/*  31:    */     }
/*  32:    */     catch (Exception e)
/*  33:    */     {
/*  34: 59 */       throw new RuntimeException(e);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ActiveXObjectImpl(String activeXName)
/*  39:    */     throws Exception
/*  40:    */   {
/*  41: 70 */     this(activeXComponentClass_.getConstructor(new Class[] { String.class }).newInstance(new Object[] { activeXName }));
/*  42:    */   }
/*  43:    */   
/*  44:    */   private ActiveXObjectImpl(Object object)
/*  45:    */   {
/*  46: 74 */     this.object_ = object;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object get(final String name, Scriptable start)
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 83 */       Object variant = METHOD_getProperty_.invoke(this.object_, new Object[] { name });
/*  54: 84 */       return wrapIfNecessary(variant);
/*  55:    */     }
/*  56:    */     catch (Exception e) {}
/*  57: 87 */     new Function()
/*  58:    */     {
/*  59:    */       public Object call(Context arg0, Scriptable arg1, Scriptable arg2, Object[] arg3)
/*  60:    */       {
/*  61:    */         try
/*  62:    */         {
/*  63: 91 */           Object rv = ActiveXObjectImpl.METHOD_callN_.invoke(null, new Object[] { ActiveXObjectImpl.this.object_, name, arg3 });
/*  64: 92 */           return ActiveXObjectImpl.this.wrapIfNecessary(rv);
/*  65:    */         }
/*  66:    */         catch (Exception e)
/*  67:    */         {
/*  68: 95 */           throw Context.throwAsScriptRuntimeEx(e);
/*  69:    */         }
/*  70:    */       }
/*  71:    */       
/*  72:    */       public Scriptable construct(Context arg0, Scriptable arg1, Object[] arg2)
/*  73:    */       {
/*  74:100 */         throw new UnsupportedOperationException();
/*  75:    */       }
/*  76:    */       
/*  77:    */       public void delete(String arg0)
/*  78:    */       {
/*  79:104 */         throw new UnsupportedOperationException();
/*  80:    */       }
/*  81:    */       
/*  82:    */       public void delete(int arg0)
/*  83:    */       {
/*  84:108 */         throw new UnsupportedOperationException();
/*  85:    */       }
/*  86:    */       
/*  87:    */       public Object get(String arg0, Scriptable arg1)
/*  88:    */       {
/*  89:112 */         throw new UnsupportedOperationException();
/*  90:    */       }
/*  91:    */       
/*  92:    */       public Object get(int arg0, Scriptable arg1)
/*  93:    */       {
/*  94:116 */         throw new UnsupportedOperationException();
/*  95:    */       }
/*  96:    */       
/*  97:    */       public String getClassName()
/*  98:    */       {
/*  99:120 */         throw new UnsupportedOperationException();
/* 100:    */       }
/* 101:    */       
/* 102:    */       public Object getDefaultValue(Class<?> arg0)
/* 103:    */       {
/* 104:124 */         throw new UnsupportedOperationException();
/* 105:    */       }
/* 106:    */       
/* 107:    */       public Object[] getIds()
/* 108:    */       {
/* 109:128 */         throw new UnsupportedOperationException();
/* 110:    */       }
/* 111:    */       
/* 112:    */       public Scriptable getParentScope()
/* 113:    */       {
/* 114:132 */         throw new UnsupportedOperationException();
/* 115:    */       }
/* 116:    */       
/* 117:    */       public Scriptable getPrototype()
/* 118:    */       {
/* 119:136 */         throw new UnsupportedOperationException();
/* 120:    */       }
/* 121:    */       
/* 122:    */       public boolean has(String arg0, Scriptable arg1)
/* 123:    */       {
/* 124:140 */         throw new UnsupportedOperationException();
/* 125:    */       }
/* 126:    */       
/* 127:    */       public boolean has(int arg0, Scriptable arg1)
/* 128:    */       {
/* 129:144 */         throw new UnsupportedOperationException();
/* 130:    */       }
/* 131:    */       
/* 132:    */       public boolean hasInstance(Scriptable arg0)
/* 133:    */       {
/* 134:148 */         throw new UnsupportedOperationException();
/* 135:    */       }
/* 136:    */       
/* 137:    */       public void put(String arg0, Scriptable arg1, Object arg2)
/* 138:    */       {
/* 139:152 */         throw new UnsupportedOperationException();
/* 140:    */       }
/* 141:    */       
/* 142:    */       public void put(int arg0, Scriptable arg1, Object arg2)
/* 143:    */       {
/* 144:156 */         throw new UnsupportedOperationException();
/* 145:    */       }
/* 146:    */       
/* 147:    */       public void setParentScope(Scriptable arg0)
/* 148:    */       {
/* 149:160 */         throw new UnsupportedOperationException();
/* 150:    */       }
/* 151:    */       
/* 152:    */       public void setPrototype(Scriptable arg0)
/* 153:    */       {
/* 154:164 */         throw new UnsupportedOperationException();
/* 155:    */       }
/* 156:    */     };
/* 157:    */   }
/* 158:    */   
/* 159:    */   private Object wrapIfNecessary(Object variant)
/* 160:    */     throws Exception
/* 161:    */   {
/* 162:176 */     if (((Short)METHOD_getvt_.invoke(variant, new Object[0])).shortValue() == 9) {
/* 163:177 */       return new ActiveXObjectImpl(METHOD_getDispatch_.invoke(variant, new Object[0]));
/* 164:    */     }
/* 165:179 */     return variant;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void put(String name, Scriptable start, Object value)
/* 169:    */   {
/* 170:    */     try
/* 171:    */     {
/* 172:188 */       Method setMethod = activeXComponentClass_.getMethod("setProperty", new Class[] { String.class, value.getClass() });
/* 173:189 */       setMethod.invoke(this.object_, new Object[] { name, Context.toString(value) });
/* 174:    */     }
/* 175:    */     catch (Exception e)
/* 176:    */     {
/* 177:192 */       throw Context.throwAsScriptRuntimeEx(e);
/* 178:    */     }
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.ActiveXObjectImpl
 * JD-Core Version:    0.7.0.1
 */