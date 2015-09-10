/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.IntrospectionException;
/*   5:    */ import java.beans.Introspector;
/*   6:    */ import java.beans.PropertyDescriptor;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.security.AccessController;
/*   9:    */ import java.security.PrivilegedAction;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ 
/*  12:    */ public class Cloneable
/*  13:    */ {
/*  14: 43 */   private static final Object[] READER_METHOD_ARGS = new Object[0];
/*  15:    */   
/*  16:    */   public Object shallowCopy()
/*  17:    */   {
/*  18: 53 */     AccessController.doPrivileged(new PrivilegedAction()
/*  19:    */     {
/*  20:    */       public Object run()
/*  21:    */       {
/*  22: 56 */         return Cloneable.this.copyListeners();
/*  23:    */       }
/*  24:    */     });
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void validate()
/*  28:    */     throws HibernateException
/*  29:    */   {
/*  30: 70 */     AccessController.doPrivileged(new PrivilegedAction()
/*  31:    */     {
/*  32:    */       public Object run()
/*  33:    */       {
/*  34: 73 */         Cloneable.this.checkListeners();
/*  35: 74 */         return null;
/*  36:    */       }
/*  37:    */     });
/*  38:    */   }
/*  39:    */   
/*  40:    */   private Object copyListeners()
/*  41:    */   {
/*  42: 82 */     Object copy = null;
/*  43: 83 */     BeanInfo beanInfo = null;
/*  44:    */     try
/*  45:    */     {
/*  46: 85 */       beanInfo = Introspector.getBeanInfo(getClass(), Object.class);
/*  47: 86 */       internalCheckListeners(beanInfo);
/*  48: 87 */       copy = getClass().newInstance();
/*  49: 88 */       PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
/*  50: 89 */       int i = 0;
/*  51: 89 */       for (int max = pds.length; i < max; i++) {
/*  52:    */         try
/*  53:    */         {
/*  54: 91 */           pds[i].getWriteMethod().invoke(copy, new Object[] { pds[i].getReadMethod().invoke(this, READER_METHOD_ARGS) });
/*  55:    */         }
/*  56:    */         catch (Throwable t)
/*  57:    */         {
/*  58: 99 */           throw new HibernateException("Unable copy copy listener [" + pds[i].getName() + "]");
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:    */     catch (Exception t)
/*  63:    */     {
/*  64:104 */       throw new HibernateException("Unable to copy listeners", t);
/*  65:    */     }
/*  66:    */     finally
/*  67:    */     {
/*  68:107 */       if (beanInfo != null) {
/*  69:110 */         Introspector.flushFromCaches(getClass());
/*  70:    */       }
/*  71:    */     }
/*  72:114 */     return copy;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void checkListeners()
/*  76:    */   {
/*  77:118 */     BeanInfo beanInfo = null;
/*  78:    */     try
/*  79:    */     {
/*  80:120 */       beanInfo = Introspector.getBeanInfo(getClass(), Object.class);
/*  81:121 */       internalCheckListeners(beanInfo);
/*  82:    */     }
/*  83:    */     catch (IntrospectionException t)
/*  84:    */     {
/*  85:124 */       throw new HibernateException("Unable to validate listener config", t);
/*  86:    */     }
/*  87:    */     finally
/*  88:    */     {
/*  89:127 */       if (beanInfo != null) {
/*  90:130 */         Introspector.flushFromCaches(getClass());
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void internalCheckListeners(BeanInfo beanInfo)
/*  96:    */   {
/*  97:136 */     PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
/*  98:    */     try
/*  99:    */     {
/* 100:138 */       int i = 0;
/* 101:138 */       for (int max = pds.length; i < max; i++)
/* 102:    */       {
/* 103:139 */         Object listener = pds[i].getReadMethod().invoke(this, READER_METHOD_ARGS);
/* 104:140 */         if (listener == null) {
/* 105:141 */           throw new HibernateException("Listener [" + pds[i].getName() + "] was null");
/* 106:    */         }
/* 107:143 */         if (listener.getClass().isArray())
/* 108:    */         {
/* 109:144 */           Object[] listenerArray = (Object[])listener;
/* 110:145 */           int length = listenerArray.length;
/* 111:146 */           for (int index = 0; index < length; index++) {
/* 112:147 */             if (listenerArray[index] == null) {
/* 113:148 */               throw new HibernateException("Listener in [" + pds[i].getName() + "] was null");
/* 114:    */             }
/* 115:    */           }
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */     catch (HibernateException e)
/* 120:    */     {
/* 121:155 */       throw e;
/* 122:    */     }
/* 123:    */     catch (Throwable t)
/* 124:    */     {
/* 125:158 */       throw new HibernateException("Unable to validate listener config");
/* 126:    */     }
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.Cloneable
 * JD-Core Version:    0.7.0.1
 */