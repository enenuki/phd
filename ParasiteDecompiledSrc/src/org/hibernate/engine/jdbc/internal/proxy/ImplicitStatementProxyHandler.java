/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.sql.Connection;
/*  5:   */ import java.sql.Statement;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ 
/*  8:   */ public class ImplicitStatementProxyHandler
/*  9:   */   extends AbstractStatementProxyHandler
/* 10:   */ {
/* 11:   */   protected ImplicitStatementProxyHandler(Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/* 12:   */   {
/* 13:38 */     super(statement, connectionProxyHandler, connectionProxy);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected void beginningInvocationHandling(Method method, Object[] args)
/* 17:   */   {
/* 18:43 */     if (method.getName().startsWith("execute")) {
/* 19:44 */       throw new HibernateException("execution not allowed on implicit statement object");
/* 20:   */     }
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.ImplicitStatementProxyHandler
 * JD-Core Version:    0.7.0.1
 */