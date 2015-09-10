/*   1:    */ package org.junit.internal.runners;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.concurrent.Callable;
/*   7:    */ import java.util.concurrent.ExecutorService;
/*   8:    */ import java.util.concurrent.Executors;
/*   9:    */ import java.util.concurrent.Future;
/*  10:    */ import java.util.concurrent.TimeUnit;
/*  11:    */ import java.util.concurrent.TimeoutException;
/*  12:    */ import org.junit.internal.AssumptionViolatedException;
/*  13:    */ import org.junit.runner.Description;
/*  14:    */ import org.junit.runner.notification.Failure;
/*  15:    */ import org.junit.runner.notification.RunNotifier;
/*  16:    */ 
/*  17:    */ @Deprecated
/*  18:    */ public class MethodRoadie
/*  19:    */ {
/*  20:    */   private final Object fTest;
/*  21:    */   private final RunNotifier fNotifier;
/*  22:    */   private final Description fDescription;
/*  23:    */   private TestMethod fTestMethod;
/*  24:    */   
/*  25:    */   public MethodRoadie(Object test, TestMethod method, RunNotifier notifier, Description description)
/*  26:    */   {
/*  27: 32 */     this.fTest = test;
/*  28: 33 */     this.fNotifier = notifier;
/*  29: 34 */     this.fDescription = description;
/*  30: 35 */     this.fTestMethod = method;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void run()
/*  34:    */   {
/*  35: 39 */     if (this.fTestMethod.isIgnored())
/*  36:    */     {
/*  37: 40 */       this.fNotifier.fireTestIgnored(this.fDescription);
/*  38: 41 */       return;
/*  39:    */     }
/*  40: 43 */     this.fNotifier.fireTestStarted(this.fDescription);
/*  41:    */     try
/*  42:    */     {
/*  43: 45 */       long timeout = this.fTestMethod.getTimeout();
/*  44: 46 */       if (timeout > 0L) {
/*  45: 47 */         runWithTimeout(timeout);
/*  46:    */       } else {
/*  47: 49 */         runTest();
/*  48:    */       }
/*  49:    */     }
/*  50:    */     finally
/*  51:    */     {
/*  52: 51 */       this.fNotifier.fireTestFinished(this.fDescription);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void runWithTimeout(final long timeout)
/*  57:    */   {
/*  58: 56 */     runBeforesThenTestThenAfters(new Runnable()
/*  59:    */     {
/*  60:    */       public void run()
/*  61:    */       {
/*  62: 59 */         ExecutorService service = Executors.newSingleThreadExecutor();
/*  63: 60 */         Callable<Object> callable = new Callable()
/*  64:    */         {
/*  65:    */           public Object call()
/*  66:    */             throws Exception
/*  67:    */           {
/*  68: 62 */             MethodRoadie.this.runTestMethod();
/*  69: 63 */             return null;
/*  70:    */           }
/*  71: 65 */         };
/*  72: 66 */         Future<Object> result = service.submit(callable);
/*  73: 67 */         service.shutdown();
/*  74:    */         try
/*  75:    */         {
/*  76: 69 */           boolean terminated = service.awaitTermination(timeout, TimeUnit.MILLISECONDS);
/*  77: 71 */           if (!terminated) {
/*  78: 72 */             service.shutdownNow();
/*  79:    */           }
/*  80: 73 */           result.get(0L, TimeUnit.MILLISECONDS);
/*  81:    */         }
/*  82:    */         catch (TimeoutException e)
/*  83:    */         {
/*  84: 75 */           MethodRoadie.this.addFailure(new Exception(String.format("test timed out after %d milliseconds", new Object[] { Long.valueOf(timeout) })));
/*  85:    */         }
/*  86:    */         catch (Exception e)
/*  87:    */         {
/*  88: 77 */           MethodRoadie.this.addFailure(e);
/*  89:    */         }
/*  90:    */       }
/*  91:    */     });
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void runTest()
/*  95:    */   {
/*  96: 84 */     runBeforesThenTestThenAfters(new Runnable()
/*  97:    */     {
/*  98:    */       public void run()
/*  99:    */       {
/* 100: 86 */         MethodRoadie.this.runTestMethod();
/* 101:    */       }
/* 102:    */     });
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void runBeforesThenTestThenAfters(Runnable test)
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109: 93 */       runBefores();
/* 110: 94 */       test.run();
/* 111:    */     }
/* 112:    */     catch (FailedBefore e) {}catch (Exception e)
/* 113:    */     {
/* 114: 97 */       throw new RuntimeException("test should never throw an exception to this level");
/* 115:    */     }
/* 116:    */     finally
/* 117:    */     {
/* 118: 99 */       runAfters();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected void runTestMethod()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:105 */       this.fTestMethod.invoke(this.fTest);
/* 127:106 */       if (this.fTestMethod.expectsException()) {
/* 128:107 */         addFailure(new AssertionError("Expected exception: " + this.fTestMethod.getExpectedException().getName()));
/* 129:    */       }
/* 130:    */     }
/* 131:    */     catch (InvocationTargetException e)
/* 132:    */     {
/* 133:109 */       Throwable actual = e.getTargetException();
/* 134:110 */       if ((actual instanceof AssumptionViolatedException)) {
/* 135:111 */         return;
/* 136:    */       }
/* 137:112 */       if (!this.fTestMethod.expectsException())
/* 138:    */       {
/* 139:113 */         addFailure(actual);
/* 140:    */       }
/* 141:114 */       else if (this.fTestMethod.isUnexpected(actual))
/* 142:    */       {
/* 143:115 */         String message = "Unexpected exception, expected<" + this.fTestMethod.getExpectedException().getName() + "> but was<" + actual.getClass().getName() + ">";
/* 144:    */         
/* 145:117 */         addFailure(new Exception(message, actual));
/* 146:    */       }
/* 147:    */     }
/* 148:    */     catch (Throwable e)
/* 149:    */     {
/* 150:120 */       addFailure(e);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void runBefores()
/* 155:    */     throws FailedBefore
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:    */       try
/* 160:    */       {
/* 161:127 */         List<Method> befores = this.fTestMethod.getBefores();
/* 162:128 */         for (Method before : befores) {
/* 163:129 */           before.invoke(this.fTest, new Object[0]);
/* 164:    */         }
/* 165:    */       }
/* 166:    */       catch (InvocationTargetException e)
/* 167:    */       {
/* 168:131 */         throw e.getTargetException();
/* 169:    */       }
/* 170:    */     }
/* 171:    */     catch (AssumptionViolatedException e)
/* 172:    */     {
/* 173:134 */       throw new FailedBefore();
/* 174:    */     }
/* 175:    */     catch (Throwable e)
/* 176:    */     {
/* 177:136 */       addFailure(e);
/* 178:137 */       throw new FailedBefore();
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void runAfters()
/* 183:    */   {
/* 184:142 */     List<Method> afters = this.fTestMethod.getAfters();
/* 185:143 */     for (Method after : afters) {
/* 186:    */       try
/* 187:    */       {
/* 188:145 */         after.invoke(this.fTest, new Object[0]);
/* 189:    */       }
/* 190:    */       catch (InvocationTargetException e)
/* 191:    */       {
/* 192:147 */         addFailure(e.getTargetException());
/* 193:    */       }
/* 194:    */       catch (Throwable e)
/* 195:    */       {
/* 196:149 */         addFailure(e);
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected void addFailure(Throwable e)
/* 202:    */   {
/* 203:154 */     this.fNotifier.fireTestFailure(new Failure(this.fDescription, e));
/* 204:    */   }
/* 205:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.MethodRoadie
 * JD-Core Version:    0.7.0.1
 */