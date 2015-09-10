/*   1:    */ package junit.framework;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.lang.reflect.Constructor;
/*   6:    */ import java.lang.reflect.InvocationTargetException;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.lang.reflect.Modifier;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Vector;
/*  13:    */ 
/*  14:    */ public class TestSuite
/*  15:    */   implements Test
/*  16:    */ {
/*  17:    */   private String fName;
/*  18:    */   
/*  19:    */   public static Test createTest(Class<?> theClass, String name)
/*  20:    */   {
/*  21:    */     Constructor<?> constructor;
/*  22:    */     try
/*  23:    */     {
/*  24: 54 */       constructor = getTestConstructor(theClass);
/*  25:    */     }
/*  26:    */     catch (NoSuchMethodException e)
/*  27:    */     {
/*  28: 56 */       return warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()");
/*  29:    */     }
/*  30:    */     Object test;
/*  31:    */     try
/*  32:    */     {
/*  33: 60 */       if (constructor.getParameterTypes().length == 0)
/*  34:    */       {
/*  35: 61 */         Object test = constructor.newInstance(new Object[0]);
/*  36: 62 */         if ((test instanceof TestCase)) {
/*  37: 63 */           ((TestCase)test).setName(name);
/*  38:    */         }
/*  39:    */       }
/*  40:    */       else
/*  41:    */       {
/*  42: 65 */         test = constructor.newInstance(new Object[] { name });
/*  43:    */       }
/*  44:    */     }
/*  45:    */     catch (InstantiationException e)
/*  46:    */     {
/*  47: 68 */       return warning("Cannot instantiate test case: " + name + " (" + exceptionToString(e) + ")");
/*  48:    */     }
/*  49:    */     catch (InvocationTargetException e)
/*  50:    */     {
/*  51: 70 */       return warning("Exception in constructor: " + name + " (" + exceptionToString(e.getTargetException()) + ")");
/*  52:    */     }
/*  53:    */     catch (IllegalAccessException e)
/*  54:    */     {
/*  55: 72 */       return warning("Cannot access test case: " + name + " (" + exceptionToString(e) + ")");
/*  56:    */     }
/*  57: 74 */     return (Test)test;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static Constructor<?> getTestConstructor(Class<?> theClass)
/*  61:    */     throws NoSuchMethodException
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 83 */       return theClass.getConstructor(new Class[] { String.class });
/*  66:    */     }
/*  67:    */     catch (NoSuchMethodException e) {}
/*  68: 87 */     return theClass.getConstructor(new Class[0]);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static Test warning(final String message)
/*  72:    */   {
/*  73: 94 */     new TestCase("warning")
/*  74:    */     {
/*  75:    */       protected void runTest()
/*  76:    */       {
/*  77: 97 */         fail(message);
/*  78:    */       }
/*  79:    */     };
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static String exceptionToString(Throwable t)
/*  83:    */   {
/*  84:106 */     StringWriter stringWriter = new StringWriter();
/*  85:107 */     PrintWriter writer = new PrintWriter(stringWriter);
/*  86:108 */     t.printStackTrace(writer);
/*  87:109 */     return stringWriter.toString();
/*  88:    */   }
/*  89:    */   
/*  90:114 */   private Vector<Test> fTests = new Vector(10);
/*  91:    */   
/*  92:    */   public TestSuite() {}
/*  93:    */   
/*  94:    */   public TestSuite(Class<?> theClass)
/*  95:    */   {
/*  96:129 */     addTestsFromTestCase(theClass);
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void addTestsFromTestCase(Class<?> theClass)
/* 100:    */   {
/* 101:133 */     this.fName = theClass.getName();
/* 102:    */     try
/* 103:    */     {
/* 104:135 */       getTestConstructor(theClass);
/* 105:    */     }
/* 106:    */     catch (NoSuchMethodException e)
/* 107:    */     {
/* 108:137 */       addTest(warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
/* 109:138 */       return;
/* 110:    */     }
/* 111:141 */     if (!Modifier.isPublic(theClass.getModifiers()))
/* 112:    */     {
/* 113:142 */       addTest(warning("Class " + theClass.getName() + " is not public"));
/* 114:143 */       return;
/* 115:    */     }
/* 116:146 */     Class<?> superClass = theClass;
/* 117:147 */     List<String> names = new ArrayList();
/* 118:148 */     while (Test.class.isAssignableFrom(superClass))
/* 119:    */     {
/* 120:149 */       for (Method each : superClass.getDeclaredMethods()) {
/* 121:150 */         addTestMethod(each, names, theClass);
/* 122:    */       }
/* 123:151 */       superClass = superClass.getSuperclass();
/* 124:    */     }
/* 125:153 */     if (this.fTests.size() == 0) {
/* 126:154 */       addTest(warning("No tests found in " + theClass.getName()));
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public TestSuite(Class<? extends TestCase> theClass, String name)
/* 131:    */   {
/* 132:162 */     this(theClass);
/* 133:163 */     setName(name);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public TestSuite(String name)
/* 137:    */   {
/* 138:170 */     setName(name);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public TestSuite(Class<?>... classes)
/* 142:    */   {
/* 143:178 */     for (Class<?> each : classes) {
/* 144:179 */       addTest(testCaseForClass(each));
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   private Test testCaseForClass(Class<?> each)
/* 149:    */   {
/* 150:183 */     if (TestCase.class.isAssignableFrom(each)) {
/* 151:184 */       return new TestSuite(each.asSubclass(TestCase.class));
/* 152:    */     }
/* 153:186 */     return warning(each.getCanonicalName() + " does not extend TestCase");
/* 154:    */   }
/* 155:    */   
/* 156:    */   public TestSuite(Class<? extends TestCase>[] classes, String name)
/* 157:    */   {
/* 158:194 */     this(classes);
/* 159:195 */     setName(name);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void addTest(Test test)
/* 163:    */   {
/* 164:202 */     this.fTests.add(test);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void addTestSuite(Class<? extends TestCase> testClass)
/* 168:    */   {
/* 169:209 */     addTest(new TestSuite(testClass));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int countTestCases()
/* 173:    */   {
/* 174:216 */     int count = 0;
/* 175:217 */     for (Test each : this.fTests) {
/* 176:218 */       count += each.countTestCases();
/* 177:    */     }
/* 178:219 */     return count;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getName()
/* 182:    */   {
/* 183:228 */     return this.fName;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void run(TestResult result)
/* 187:    */   {
/* 188:235 */     for (Test each : this.fTests)
/* 189:    */     {
/* 190:236 */       if (result.shouldStop()) {
/* 191:    */         break;
/* 192:    */       }
/* 193:238 */       runTest(each, result);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void runTest(Test test, TestResult result)
/* 198:    */   {
/* 199:243 */     test.run(result);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setName(String name)
/* 203:    */   {
/* 204:251 */     this.fName = name;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Test testAt(int index)
/* 208:    */   {
/* 209:258 */     return (Test)this.fTests.get(index);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int testCount()
/* 213:    */   {
/* 214:265 */     return this.fTests.size();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Enumeration<Test> tests()
/* 218:    */   {
/* 219:272 */     return this.fTests.elements();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String toString()
/* 223:    */   {
/* 224:279 */     if (getName() != null) {
/* 225:280 */       return getName();
/* 226:    */     }
/* 227:281 */     return super.toString();
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void addTestMethod(Method m, List<String> names, Class<?> theClass)
/* 231:    */   {
/* 232:285 */     String name = m.getName();
/* 233:286 */     if (names.contains(name)) {
/* 234:287 */       return;
/* 235:    */     }
/* 236:288 */     if (!isPublicTestMethod(m))
/* 237:    */     {
/* 238:289 */       if (isTestMethod(m)) {
/* 239:290 */         addTest(warning("Test method isn't public: " + m.getName() + "(" + theClass.getCanonicalName() + ")"));
/* 240:    */       }
/* 241:291 */       return;
/* 242:    */     }
/* 243:293 */     names.add(name);
/* 244:294 */     addTest(createTest(theClass, name));
/* 245:    */   }
/* 246:    */   
/* 247:    */   private boolean isPublicTestMethod(Method m)
/* 248:    */   {
/* 249:298 */     return (isTestMethod(m)) && (Modifier.isPublic(m.getModifiers()));
/* 250:    */   }
/* 251:    */   
/* 252:    */   private boolean isTestMethod(Method m)
/* 253:    */   {
/* 254:302 */     return (m.getParameterTypes().length == 0) && (m.getName().startsWith("test")) && (m.getReturnType().equals(Void.TYPE));
/* 255:    */   }
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.TestSuite
 * JD-Core Version:    0.7.0.1
 */