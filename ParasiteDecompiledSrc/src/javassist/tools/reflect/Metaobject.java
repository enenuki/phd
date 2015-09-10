/*   1:    */ package javassist.tools.reflect;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.reflect.Field;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ 
/*  11:    */ public class Metaobject
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   protected ClassMetaobject classmetaobject;
/*  15:    */   protected Metalevel baseobject;
/*  16:    */   protected Method[] methods;
/*  17:    */   
/*  18:    */   public Metaobject(Object self, Object[] args)
/*  19:    */   {
/*  20: 60 */     this.baseobject = ((Metalevel)self);
/*  21: 61 */     this.classmetaobject = this.baseobject._getClass();
/*  22: 62 */     this.methods = this.classmetaobject.getReflectiveMethods();
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected Metaobject()
/*  26:    */   {
/*  27: 71 */     this.baseobject = null;
/*  28: 72 */     this.classmetaobject = null;
/*  29: 73 */     this.methods = null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   private void writeObject(ObjectOutputStream out)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 77 */     out.writeObject(this.baseobject);
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void readObject(ObjectInputStream in)
/*  39:    */     throws IOException, ClassNotFoundException
/*  40:    */   {
/*  41: 83 */     this.baseobject = ((Metalevel)in.readObject());
/*  42: 84 */     this.classmetaobject = this.baseobject._getClass();
/*  43: 85 */     this.methods = this.classmetaobject.getReflectiveMethods();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final ClassMetaobject getClassMetaobject()
/*  47:    */   {
/*  48: 94 */     return this.classmetaobject;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final Object getObject()
/*  52:    */   {
/*  53:101 */     return this.baseobject;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final void setObject(Object self)
/*  57:    */   {
/*  58:110 */     this.baseobject = ((Metalevel)self);
/*  59:111 */     this.classmetaobject = this.baseobject._getClass();
/*  60:112 */     this.methods = this.classmetaobject.getReflectiveMethods();
/*  61:    */     
/*  62:    */ 
/*  63:115 */     this.baseobject._setMetaobject(this);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final String getMethodName(int identifier)
/*  67:    */   {
/*  68:123 */     String mname = this.methods[identifier].getName();
/*  69:124 */     int j = 3;
/*  70:    */     for (;;)
/*  71:    */     {
/*  72:126 */       char c = mname.charAt(j++);
/*  73:127 */       if ((c < '0') || ('9' < c)) {
/*  74:    */         break;
/*  75:    */       }
/*  76:    */     }
/*  77:131 */     return mname.substring(j);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final Class[] getParameterTypes(int identifier)
/*  81:    */   {
/*  82:140 */     return this.methods[identifier].getParameterTypes();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final Class getReturnType(int identifier)
/*  86:    */   {
/*  87:148 */     return this.methods[identifier].getReturnType();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object trapFieldRead(String name)
/*  91:    */   {
/*  92:159 */     Class jc = getClassMetaobject().getJavaClass();
/*  93:    */     try
/*  94:    */     {
/*  95:161 */       return jc.getField(name).get(getObject());
/*  96:    */     }
/*  97:    */     catch (NoSuchFieldException e)
/*  98:    */     {
/*  99:164 */       throw new RuntimeException(e.toString());
/* 100:    */     }
/* 101:    */     catch (IllegalAccessException e)
/* 102:    */     {
/* 103:167 */       throw new RuntimeException(e.toString());
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void trapFieldWrite(String name, Object value)
/* 108:    */   {
/* 109:179 */     Class jc = getClassMetaobject().getJavaClass();
/* 110:    */     try
/* 111:    */     {
/* 112:181 */       jc.getField(name).set(getObject(), value);
/* 113:    */     }
/* 114:    */     catch (NoSuchFieldException e)
/* 115:    */     {
/* 116:184 */       throw new RuntimeException(e.toString());
/* 117:    */     }
/* 118:    */     catch (IllegalAccessException e)
/* 119:    */     {
/* 120:187 */       throw new RuntimeException(e.toString());
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Object trapMethodcall(int identifier, Object[] args)
/* 125:    */     throws Throwable
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:227 */       return this.methods[identifier].invoke(getObject(), args);
/* 130:    */     }
/* 131:    */     catch (InvocationTargetException e)
/* 132:    */     {
/* 133:230 */       throw e.getTargetException();
/* 134:    */     }
/* 135:    */     catch (IllegalAccessException e)
/* 136:    */     {
/* 137:233 */       throw new CannotInvokeException(e);
/* 138:    */     }
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.Metaobject
 * JD-Core Version:    0.7.0.1
 */