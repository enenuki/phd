/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.security.AccessController;
/*   6:    */ import java.security.PrivilegedAction;
/*   7:    */ 
/*   8:    */ public final class LazilyLoadedCtor
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1L;
/*  12:    */   private static final int STATE_BEFORE_INIT = 0;
/*  13:    */   private static final int STATE_INITIALIZING = 1;
/*  14:    */   private static final int STATE_WITH_VALUE = 2;
/*  15:    */   private final ScriptableObject scope;
/*  16:    */   private final String propertyName;
/*  17:    */   private final String className;
/*  18:    */   private final boolean sealed;
/*  19:    */   private final boolean privileged;
/*  20:    */   private Object initializedValue;
/*  21:    */   private int state;
/*  22:    */   
/*  23:    */   public LazilyLoadedCtor(ScriptableObject scope, String propertyName, String className, boolean sealed)
/*  24:    */   {
/*  25: 69 */     this(scope, propertyName, className, sealed, false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   LazilyLoadedCtor(ScriptableObject scope, String propertyName, String className, boolean sealed, boolean privileged)
/*  29:    */   {
/*  30: 76 */     this.scope = scope;
/*  31: 77 */     this.propertyName = propertyName;
/*  32: 78 */     this.className = className;
/*  33: 79 */     this.sealed = sealed;
/*  34: 80 */     this.privileged = privileged;
/*  35: 81 */     this.state = 0;
/*  36:    */     
/*  37: 83 */     scope.addLazilyInitializedValue(propertyName, 0, this, 2);
/*  38:    */   }
/*  39:    */   
/*  40:    */   void init()
/*  41:    */   {
/*  42: 89 */     synchronized (this)
/*  43:    */     {
/*  44: 90 */       if (this.state == 1) {
/*  45: 91 */         throw new IllegalStateException("Recursive initialization for " + this.propertyName);
/*  46:    */       }
/*  47: 93 */       if (this.state == 0)
/*  48:    */       {
/*  49: 94 */         this.state = 1;
/*  50:    */         
/*  51:    */ 
/*  52: 97 */         Object value = Scriptable.NOT_FOUND;
/*  53:    */         try
/*  54:    */         {
/*  55: 99 */           value = buildValue();
/*  56:    */         }
/*  57:    */         finally
/*  58:    */         {
/*  59:101 */           this.initializedValue = value;
/*  60:102 */           this.state = 2;
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   Object getValue()
/*  67:    */   {
/*  68:110 */     if (this.state != 2) {
/*  69:111 */       throw new IllegalStateException(this.propertyName);
/*  70:    */     }
/*  71:112 */     return this.initializedValue;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private Object buildValue()
/*  75:    */   {
/*  76:117 */     if (this.privileged) {
/*  77:119 */       AccessController.doPrivileged(new PrivilegedAction()
/*  78:    */       {
/*  79:    */         public Object run()
/*  80:    */         {
/*  81:123 */           return LazilyLoadedCtor.this.buildValue0();
/*  82:    */         }
/*  83:    */       });
/*  84:    */     }
/*  85:129 */     return buildValue0();
/*  86:    */   }
/*  87:    */   
/*  88:    */   private Object buildValue0()
/*  89:    */   {
/*  90:135 */     Class<? extends Scriptable> cl = cast(Kit.classOrNull(this.className));
/*  91:136 */     if (cl != null) {
/*  92:    */       try
/*  93:    */       {
/*  94:138 */         Object value = ScriptableObject.buildClassCtor(this.scope, cl, this.sealed, false);
/*  95:140 */         if (value != null) {
/*  96:141 */           return value;
/*  97:    */         }
/*  98:146 */         value = this.scope.get(this.propertyName, this.scope);
/*  99:147 */         if (value != Scriptable.NOT_FOUND) {
/* 100:148 */           return value;
/* 101:    */         }
/* 102:    */       }
/* 103:    */       catch (InvocationTargetException ex)
/* 104:    */       {
/* 105:151 */         Throwable target = ex.getTargetException();
/* 106:152 */         if ((target instanceof RuntimeException)) {
/* 107:153 */           throw ((RuntimeException)target);
/* 108:    */         }
/* 109:    */       }
/* 110:    */       catch (RhinoException ex) {}catch (InstantiationException ex) {}catch (IllegalAccessException ex) {}catch (SecurityException ex) {}
/* 111:    */     }
/* 112:161 */     return Scriptable.NOT_FOUND;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private Class<? extends Scriptable> cast(Class<?> cl)
/* 116:    */   {
/* 117:166 */     return cl;
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.LazilyLoadedCtor
 * JD-Core Version:    0.7.0.1
 */