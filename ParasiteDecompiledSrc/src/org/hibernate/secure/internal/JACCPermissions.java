/*   1:    */ package org.hibernate.secure.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.UndeclaredThrowableException;
/*   4:    */ import java.security.AccessController;
/*   5:    */ import java.security.CodeSource;
/*   6:    */ import java.security.Policy;
/*   7:    */ import java.security.Principal;
/*   8:    */ import java.security.PrivilegedAction;
/*   9:    */ import java.security.PrivilegedActionException;
/*  10:    */ import java.security.PrivilegedExceptionAction;
/*  11:    */ import java.security.ProtectionDomain;
/*  12:    */ import java.util.Set;
/*  13:    */ import javax.security.auth.Subject;
/*  14:    */ import javax.security.jacc.EJBMethodPermission;
/*  15:    */ import javax.security.jacc.PolicyContext;
/*  16:    */ import javax.security.jacc.PolicyContextException;
/*  17:    */ 
/*  18:    */ public class JACCPermissions
/*  19:    */ {
/*  20:    */   public static void checkPermission(Class clazz, String contextID, EJBMethodPermission methodPerm)
/*  21:    */     throws SecurityException
/*  22:    */   {
/*  23: 50 */     CodeSource ejbCS = clazz.getProtectionDomain().getCodeSource();
/*  24:    */     try
/*  25:    */     {
/*  26: 53 */       setContextID(contextID);
/*  27:    */       
/*  28: 55 */       Policy policy = Policy.getPolicy();
/*  29:    */       
/*  30: 57 */       Subject caller = getContextSubject();
/*  31:    */       
/*  32: 59 */       Principal[] principals = null;
/*  33: 60 */       if (caller != null)
/*  34:    */       {
/*  35: 62 */         Set principalsSet = caller.getPrincipals();
/*  36: 63 */         principals = new Principal[principalsSet.size()];
/*  37: 64 */         principalsSet.toArray(principals);
/*  38:    */       }
/*  39: 67 */       ProtectionDomain pd = new ProtectionDomain(ejbCS, null, null, principals);
/*  40: 68 */       if (!policy.implies(pd, methodPerm))
/*  41:    */       {
/*  42: 69 */         String msg = "Denied: " + methodPerm + ", caller=" + caller;
/*  43: 70 */         SecurityException e = new SecurityException(msg);
/*  44: 71 */         throw e;
/*  45:    */       }
/*  46:    */     }
/*  47:    */     catch (PolicyContextException e)
/*  48:    */     {
/*  49: 75 */       throw new RuntimeException(e);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   static abstract interface PolicyContextActions
/*  54:    */   {
/*  55:    */     public static final String SUBJECT_CONTEXT_KEY = "javax.security.auth.Subject.container";
/*  56: 84 */     public static final PolicyContextActions PRIVILEGED = new PolicyContextActions()
/*  57:    */     {
/*  58: 85 */       private final PrivilegedExceptionAction exAction = new PrivilegedExceptionAction()
/*  59:    */       {
/*  60:    */         public Object run()
/*  61:    */           throws Exception
/*  62:    */         {
/*  63: 87 */           return (Subject)PolicyContext.getContext("javax.security.auth.Subject.container");
/*  64:    */         }
/*  65:    */       };
/*  66:    */       
/*  67:    */       public Subject getContextSubject()
/*  68:    */         throws PolicyContextException
/*  69:    */       {
/*  70:    */         try
/*  71:    */         {
/*  72: 93 */           return (Subject)AccessController.doPrivileged(this.exAction);
/*  73:    */         }
/*  74:    */         catch (PrivilegedActionException e)
/*  75:    */         {
/*  76: 96 */           Exception ex = e.getException();
/*  77: 97 */           if ((ex instanceof PolicyContextException)) {
/*  78: 98 */             throw ((PolicyContextException)ex);
/*  79:    */           }
/*  80:101 */           throw new UndeclaredThrowableException(ex);
/*  81:    */         }
/*  82:    */       }
/*  83:    */     };
/*  84:107 */     public static final PolicyContextActions NON_PRIVILEGED = new PolicyContextActions()
/*  85:    */     {
/*  86:    */       public Subject getContextSubject()
/*  87:    */         throws PolicyContextException
/*  88:    */       {
/*  89:109 */         return (Subject)PolicyContext.getContext("javax.security.auth.Subject.container");
/*  90:    */       }
/*  91:    */     };
/*  92:    */     
/*  93:    */     public abstract Subject getContextSubject()
/*  94:    */       throws PolicyContextException;
/*  95:    */   }
/*  96:    */   
/*  97:    */   static Subject getContextSubject()
/*  98:    */     throws PolicyContextException
/*  99:    */   {
/* 100:117 */     if (System.getSecurityManager() == null) {
/* 101:118 */       return PolicyContextActions.NON_PRIVILEGED.getContextSubject();
/* 102:    */     }
/* 103:121 */     return PolicyContextActions.PRIVILEGED.getContextSubject();
/* 104:    */   }
/* 105:    */   
/* 106:    */   private static class SetContextID
/* 107:    */     implements PrivilegedAction
/* 108:    */   {
/* 109:    */     String contextID;
/* 110:    */     
/* 111:    */     SetContextID(String contextID)
/* 112:    */     {
/* 113:129 */       this.contextID = contextID;
/* 114:    */     }
/* 115:    */     
/* 116:    */     public Object run()
/* 117:    */     {
/* 118:133 */       String previousID = PolicyContext.getContextID();
/* 119:134 */       PolicyContext.setContextID(this.contextID);
/* 120:135 */       return previousID;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   static String setContextID(String contextID)
/* 125:    */   {
/* 126:140 */     PrivilegedAction action = new SetContextID(contextID);
/* 127:141 */     String previousID = (String)AccessController.doPrivileged(action);
/* 128:142 */     return previousID;
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.secure.internal.JACCPermissions
 * JD-Core Version:    0.7.0.1
 */