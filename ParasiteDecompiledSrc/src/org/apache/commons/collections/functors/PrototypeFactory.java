/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.ObjectInputStream;
/*   7:    */ import java.io.ObjectOutputStream;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import org.apache.commons.collections.Factory;
/*  12:    */ import org.apache.commons.collections.FunctorException;
/*  13:    */ 
/*  14:    */ public class PrototypeFactory
/*  15:    */ {
/*  16:    */   public static Factory getInstance(Object prototype)
/*  17:    */   {
/*  18: 59 */     if (prototype == null) {
/*  19: 60 */       return ConstantFactory.NULL_INSTANCE;
/*  20:    */     }
/*  21:    */     try
/*  22:    */     {
/*  23: 63 */       Method method = prototype.getClass().getMethod("clone", (Class[])null);
/*  24: 64 */       return new PrototypeCloneFactory(prototype, method, null);
/*  25:    */     }
/*  26:    */     catch (NoSuchMethodException ex)
/*  27:    */     {
/*  28:    */       try
/*  29:    */       {
/*  30: 68 */         prototype.getClass().getConstructor(new Class[] { prototype.getClass() });
/*  31: 69 */         return new InstantiateFactory(prototype.getClass(), new Class[] { prototype.getClass() }, new Object[] { prototype });
/*  32:    */       }
/*  33:    */       catch (NoSuchMethodException ex2)
/*  34:    */       {
/*  35: 75 */         if ((prototype instanceof Serializable)) {
/*  36: 76 */           return new PrototypeSerializationFactory((Serializable)prototype, null);
/*  37:    */         }
/*  38: 80 */         throw new IllegalArgumentException("The prototype must be cloneable via a public clone method");
/*  39:    */       }
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   static class PrototypeCloneFactory
/*  44:    */     implements Factory, Serializable
/*  45:    */   {
/*  46:    */     private static final long serialVersionUID = 5604271422565175555L;
/*  47:    */     private final Object iPrototype;
/*  48:    */     private transient Method iCloneMethod;
/*  49:    */     
/*  50:    */     PrototypeCloneFactory(Object x0, Method x1, PrototypeFactory.1 x2)
/*  51:    */     {
/*  52: 96 */       this(x0, x1);
/*  53:    */     }
/*  54:    */     
/*  55:    */     private PrototypeCloneFactory(Object prototype, Method method)
/*  56:    */     {
/*  57:111 */       this.iPrototype = prototype;
/*  58:112 */       this.iCloneMethod = method;
/*  59:    */     }
/*  60:    */     
/*  61:    */     private void findCloneMethod()
/*  62:    */     {
/*  63:    */       try
/*  64:    */       {
/*  65:120 */         this.iCloneMethod = this.iPrototype.getClass().getMethod("clone", (Class[])null);
/*  66:    */       }
/*  67:    */       catch (NoSuchMethodException ex)
/*  68:    */       {
/*  69:123 */         throw new IllegalArgumentException("PrototypeCloneFactory: The clone method must exist and be public ");
/*  70:    */       }
/*  71:    */     }
/*  72:    */     
/*  73:    */     public Object create()
/*  74:    */     {
/*  75:134 */       if (this.iCloneMethod == null) {
/*  76:135 */         findCloneMethod();
/*  77:    */       }
/*  78:    */       try
/*  79:    */       {
/*  80:139 */         return this.iCloneMethod.invoke(this.iPrototype, (Object[])null);
/*  81:    */       }
/*  82:    */       catch (IllegalAccessException ex)
/*  83:    */       {
/*  84:142 */         throw new FunctorException("PrototypeCloneFactory: Clone method must be public", ex);
/*  85:    */       }
/*  86:    */       catch (InvocationTargetException ex)
/*  87:    */       {
/*  88:144 */         throw new FunctorException("PrototypeCloneFactory: Clone method threw an exception", ex);
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   static class PrototypeSerializationFactory
/*  94:    */     implements Factory, Serializable
/*  95:    */   {
/*  96:    */     private static final long serialVersionUID = -8704966966139178833L;
/*  97:    */     private final Serializable iPrototype;
/*  98:    */     
/*  99:    */     PrototypeSerializationFactory(Serializable x0, PrototypeFactory.1 x1)
/* 100:    */     {
/* 101:154 */       this(x0);
/* 102:    */     }
/* 103:    */     
/* 104:    */     private PrototypeSerializationFactory(Serializable prototype)
/* 105:    */     {
/* 106:167 */       this.iPrototype = prototype;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public Object create()
/* 110:    */     {
/* 111:176 */       ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
/* 112:177 */       ByteArrayInputStream bais = null;
/* 113:    */       try
/* 114:    */       {
/* 115:179 */         ObjectOutputStream out = new ObjectOutputStream(baos);
/* 116:180 */         out.writeObject(this.iPrototype);
/* 117:    */         
/* 118:182 */         bais = new ByteArrayInputStream(baos.toByteArray());
/* 119:183 */         ObjectInputStream in = new ObjectInputStream(bais);
/* 120:184 */         return in.readObject();
/* 121:    */       }
/* 122:    */       catch (ClassNotFoundException ex)
/* 123:    */       {
/* 124:187 */         throw new FunctorException(ex);
/* 125:    */       }
/* 126:    */       catch (IOException ex)
/* 127:    */       {
/* 128:189 */         throw new FunctorException(ex);
/* 129:    */       }
/* 130:    */       finally
/* 131:    */       {
/* 132:    */         try
/* 133:    */         {
/* 134:192 */           if (bais != null) {
/* 135:193 */             bais.close();
/* 136:    */           }
/* 137:    */         }
/* 138:    */         catch (IOException ex) {}
/* 139:    */         try
/* 140:    */         {
/* 141:199 */           if (baos != null) {
/* 142:200 */             baos.close();
/* 143:    */           }
/* 144:    */         }
/* 145:    */         catch (IOException ex) {}
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.PrototypeFactory
 * JD-Core Version:    0.7.0.1
 */