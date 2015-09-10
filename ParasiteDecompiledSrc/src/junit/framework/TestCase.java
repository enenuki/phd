/*   1:    */ package junit.framework;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ 
/*   7:    */ public abstract class TestCase
/*   8:    */   extends Assert
/*   9:    */   implements Test
/*  10:    */ {
/*  11:    */   private String fName;
/*  12:    */   
/*  13:    */   public TestCase()
/*  14:    */   {
/*  15: 87 */     this.fName = null;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public TestCase(String name)
/*  19:    */   {
/*  20: 93 */     this.fName = name;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int countTestCases()
/*  24:    */   {
/*  25: 99 */     return 1;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected TestResult createResult()
/*  29:    */   {
/*  30:107 */     return new TestResult();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public TestResult run()
/*  34:    */   {
/*  35:116 */     TestResult result = createResult();
/*  36:117 */     run(result);
/*  37:118 */     return result;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void run(TestResult result)
/*  41:    */   {
/*  42:124 */     result.run(this);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void runBare()
/*  46:    */     throws Throwable
/*  47:    */   {
/*  48:131 */     Throwable exception = null;
/*  49:132 */     setUp();
/*  50:    */     try
/*  51:    */     {
/*  52:134 */       runTest();
/*  53:    */       try
/*  54:    */       {
/*  55:140 */         tearDown();
/*  56:    */       }
/*  57:    */       catch (Throwable tearingDown)
/*  58:    */       {
/*  59:142 */         if (exception == null) {
/*  60:142 */           exception = tearingDown;
/*  61:    */         }
/*  62:    */       }
/*  63:145 */       if (exception == null) {
/*  64:    */         return;
/*  65:    */       }
/*  66:    */     }
/*  67:    */     catch (Throwable running)
/*  68:    */     {
/*  69:136 */       exception = running;
/*  70:    */     }
/*  71:    */     finally
/*  72:    */     {
/*  73:    */       try
/*  74:    */       {
/*  75:140 */         tearDown();
/*  76:    */       }
/*  77:    */       catch (Throwable tearingDown)
/*  78:    */       {
/*  79:142 */         if (exception == null) {
/*  80:142 */           exception = tearingDown;
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:145 */     throw exception;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void runTest()
/*  88:    */     throws Throwable
/*  89:    */   {
/*  90:152 */     assertNotNull("TestCase.fName cannot be null", this.fName);
/*  91:153 */     Method runMethod = null;
/*  92:    */     try
/*  93:    */     {
/*  94:159 */       runMethod = getClass().getMethod(this.fName, (Class[])null);
/*  95:    */     }
/*  96:    */     catch (NoSuchMethodException e)
/*  97:    */     {
/*  98:161 */       fail("Method \"" + this.fName + "\" not found");
/*  99:    */     }
/* 100:163 */     if (!Modifier.isPublic(runMethod.getModifiers())) {
/* 101:164 */       fail("Method \"" + this.fName + "\" should be public");
/* 102:    */     }
/* 103:    */     try
/* 104:    */     {
/* 105:168 */       runMethod.invoke(this, new Object[0]);
/* 106:    */     }
/* 107:    */     catch (InvocationTargetException e)
/* 108:    */     {
/* 109:171 */       e.fillInStackTrace();
/* 110:172 */       throw e.getTargetException();
/* 111:    */     }
/* 112:    */     catch (IllegalAccessException e)
/* 113:    */     {
/* 114:175 */       e.fillInStackTrace();
/* 115:176 */       throw e;
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void setUp()
/* 120:    */     throws Exception
/* 121:    */   {}
/* 122:    */   
/* 123:    */   protected void tearDown()
/* 124:    */     throws Exception
/* 125:    */   {}
/* 126:    */   
/* 127:    */   public String toString()
/* 128:    */   {
/* 129:196 */     return getName() + "(" + getClass().getName() + ")";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getName()
/* 133:    */   {
/* 134:203 */     return this.fName;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setName(String name)
/* 138:    */   {
/* 139:210 */     this.fName = name;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.TestCase
 * JD-Core Version:    0.7.0.1
 */