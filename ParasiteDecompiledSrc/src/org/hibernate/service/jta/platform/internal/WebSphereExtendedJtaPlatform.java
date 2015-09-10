/*   1:    */ package org.hibernate.service.jta.platform.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationHandler;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Proxy;
/*   6:    */ import javax.transaction.NotSupportedException;
/*   7:    */ import javax.transaction.RollbackException;
/*   8:    */ import javax.transaction.Synchronization;
/*   9:    */ import javax.transaction.SystemException;
/*  10:    */ import javax.transaction.Transaction;
/*  11:    */ import javax.transaction.TransactionManager;
/*  12:    */ import javax.transaction.UserTransaction;
/*  13:    */ import javax.transaction.xa.XAResource;
/*  14:    */ import org.hibernate.HibernateException;
/*  15:    */ import org.hibernate.service.jndi.spi.JndiService;
/*  16:    */ 
/*  17:    */ public class WebSphereExtendedJtaPlatform
/*  18:    */   extends AbstractJtaPlatform
/*  19:    */ {
/*  20:    */   public static final String UT_NAME = "java:comp/UserTransaction";
/*  21:    */   
/*  22:    */   protected boolean canCacheTransactionManager()
/*  23:    */   {
/*  24: 63 */     return true;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected TransactionManager locateTransactionManager()
/*  28:    */   {
/*  29: 68 */     return new TransactionManagerAdapter(null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected UserTransaction locateUserTransaction()
/*  33:    */   {
/*  34: 73 */     return (UserTransaction)jndiService().locate("java:comp/UserTransaction");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object getTransactionIdentifier(Transaction transaction)
/*  38:    */   {
/*  39: 80 */     return Integer.valueOf(transaction.hashCode());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public class TransactionManagerAdapter
/*  43:    */     implements TransactionManager
/*  44:    */   {
/*  45:    */     private final Class synchronizationCallbackClass;
/*  46:    */     private final Method registerSynchronizationMethod;
/*  47:    */     private final Method getLocalIdMethod;
/*  48:    */     private Object extendedJTATransaction;
/*  49:    */     
/*  50:    */     private TransactionManagerAdapter()
/*  51:    */       throws HibernateException
/*  52:    */     {
/*  53:    */       try
/*  54:    */       {
/*  55: 91 */         this.synchronizationCallbackClass = Class.forName("com.ibm.websphere.jtaextensions.SynchronizationCallback");
/*  56: 92 */         extendedJTATransactionClass = Class.forName("com.ibm.websphere.jtaextensions.ExtendedJTATransaction");
/*  57: 93 */         this.registerSynchronizationMethod = extendedJTATransactionClass.getMethod("registerSynchronizationCallbackForCurrentTran", new Class[] { this.synchronizationCallbackClass });
/*  58:    */         
/*  59:    */ 
/*  60:    */ 
/*  61: 97 */         this.getLocalIdMethod = extendedJTATransactionClass.getMethod("getLocalId", (Class[])null);
/*  62:    */       }
/*  63:    */       catch (ClassNotFoundException cnfe)
/*  64:    */       {
/*  65:    */         Class extendedJTATransactionClass;
/*  66:100 */         throw new HibernateException(cnfe);
/*  67:    */       }
/*  68:    */       catch (NoSuchMethodException nsme)
/*  69:    */       {
/*  70:103 */         throw new HibernateException(nsme);
/*  71:    */       }
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void begin()
/*  75:    */       throws NotSupportedException, SystemException
/*  76:    */     {
/*  77:109 */       throw new UnsupportedOperationException();
/*  78:    */     }
/*  79:    */     
/*  80:    */     public void commit()
/*  81:    */       throws UnsupportedOperationException
/*  82:    */     {
/*  83:114 */       throw new UnsupportedOperationException();
/*  84:    */     }
/*  85:    */     
/*  86:    */     public int getStatus()
/*  87:    */       throws SystemException
/*  88:    */     {
/*  89:119 */       return getTransaction() == null ? 6 : getTransaction().getStatus();
/*  90:    */     }
/*  91:    */     
/*  92:    */     public Transaction getTransaction()
/*  93:    */       throws SystemException
/*  94:    */     {
/*  95:124 */       return new TransactionAdapter(null);
/*  96:    */     }
/*  97:    */     
/*  98:    */     public void resume(Transaction txn)
/*  99:    */       throws UnsupportedOperationException
/* 100:    */     {
/* 101:129 */       throw new UnsupportedOperationException();
/* 102:    */     }
/* 103:    */     
/* 104:    */     public void rollback()
/* 105:    */       throws UnsupportedOperationException
/* 106:    */     {
/* 107:134 */       throw new UnsupportedOperationException();
/* 108:    */     }
/* 109:    */     
/* 110:    */     public void setRollbackOnly()
/* 111:    */       throws UnsupportedOperationException
/* 112:    */     {
/* 113:139 */       throw new UnsupportedOperationException();
/* 114:    */     }
/* 115:    */     
/* 116:    */     public void setTransactionTimeout(int i)
/* 117:    */       throws UnsupportedOperationException
/* 118:    */     {
/* 119:144 */       throw new UnsupportedOperationException();
/* 120:    */     }
/* 121:    */     
/* 122:    */     public Transaction suspend()
/* 123:    */       throws UnsupportedOperationException
/* 124:    */     {
/* 125:149 */       throw new UnsupportedOperationException();
/* 126:    */     }
/* 127:    */     
/* 128:    */     public class TransactionAdapter
/* 129:    */       implements Transaction
/* 130:    */     {
/* 131:    */       private TransactionAdapter()
/* 132:    */       {
/* 133:155 */         if (WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.extendedJTATransaction == null) {
/* 134:156 */           WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.extendedJTATransaction = WebSphereExtendedJtaPlatform.this.jndiService().locate("java:comp/websphere/ExtendedJTATransaction");
/* 135:    */         }
/* 136:    */       }
/* 137:    */       
/* 138:    */       public void registerSynchronization(final Synchronization synchronization)
/* 139:    */         throws RollbackException, IllegalStateException, SystemException
/* 140:    */       {
/* 141:165 */         InvocationHandler ih = new InvocationHandler()
/* 142:    */         {
/* 143:    */           public Object invoke(Object proxy, Method method, Object[] args)
/* 144:    */             throws Throwable
/* 145:    */           {
/* 146:169 */             if ("afterCompletion".equals(method.getName()))
/* 147:    */             {
/* 148:170 */               int status = args[2].equals(Boolean.TRUE) ? 3 : 5;
/* 149:    */               
/* 150:    */ 
/* 151:173 */               synchronization.afterCompletion(status);
/* 152:    */             }
/* 153:175 */             else if ("beforeCompletion".equals(method.getName()))
/* 154:    */             {
/* 155:176 */               synchronization.beforeCompletion();
/* 156:    */             }
/* 157:178 */             else if ("toString".equals(method.getName()))
/* 158:    */             {
/* 159:179 */               return synchronization.toString();
/* 160:    */             }
/* 161:181 */             return null;
/* 162:    */           }
/* 163:185 */         };
/* 164:186 */         Object synchronizationCallback = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.synchronizationCallbackClass }, ih);
/* 165:    */         try
/* 166:    */         {
/* 167:193 */           WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.registerSynchronizationMethod.invoke(WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.extendedJTATransaction, new Object[] { synchronizationCallback });
/* 168:    */         }
/* 169:    */         catch (Exception e)
/* 170:    */         {
/* 171:196 */           throw new HibernateException(e);
/* 172:    */         }
/* 173:    */       }
/* 174:    */       
/* 175:    */       public int hashCode()
/* 176:    */       {
/* 177:203 */         return getLocalId().hashCode();
/* 178:    */       }
/* 179:    */       
/* 180:    */       public boolean equals(Object other)
/* 181:    */       {
/* 182:208 */         if (!(other instanceof TransactionAdapter)) {
/* 183:208 */           return false;
/* 184:    */         }
/* 185:209 */         TransactionAdapter that = (TransactionAdapter)other;
/* 186:210 */         return getLocalId().equals(that.getLocalId());
/* 187:    */       }
/* 188:    */       
/* 189:    */       private Object getLocalId()
/* 190:    */         throws HibernateException
/* 191:    */       {
/* 192:    */         try
/* 193:    */         {
/* 194:215 */           return WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.getLocalIdMethod.invoke(WebSphereExtendedJtaPlatform.TransactionManagerAdapter.this.extendedJTATransaction, (Object[])null);
/* 195:    */         }
/* 196:    */         catch (Exception e)
/* 197:    */         {
/* 198:218 */           throw new HibernateException(e);
/* 199:    */         }
/* 200:    */       }
/* 201:    */       
/* 202:    */       public void commit()
/* 203:    */         throws UnsupportedOperationException
/* 204:    */       {
/* 205:224 */         throw new UnsupportedOperationException();
/* 206:    */       }
/* 207:    */       
/* 208:    */       public boolean delistResource(XAResource resource, int i)
/* 209:    */         throws UnsupportedOperationException
/* 210:    */       {
/* 211:229 */         throw new UnsupportedOperationException();
/* 212:    */       }
/* 213:    */       
/* 214:    */       public boolean enlistResource(XAResource resource)
/* 215:    */         throws UnsupportedOperationException
/* 216:    */       {
/* 217:234 */         throw new UnsupportedOperationException();
/* 218:    */       }
/* 219:    */       
/* 220:    */       public int getStatus()
/* 221:    */       {
/* 222:239 */         return Integer.valueOf(0).equals(getLocalId()) ? 6 : 0;
/* 223:    */       }
/* 224:    */       
/* 225:    */       public void rollback()
/* 226:    */         throws UnsupportedOperationException
/* 227:    */       {
/* 228:245 */         throw new UnsupportedOperationException();
/* 229:    */       }
/* 230:    */       
/* 231:    */       public void setRollbackOnly()
/* 232:    */         throws UnsupportedOperationException
/* 233:    */       {
/* 234:250 */         throw new UnsupportedOperationException();
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.WebSphereExtendedJtaPlatform
 * JD-Core Version:    0.7.0.1
 */