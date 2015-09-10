/*  1:   */ package junit.framework;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.junit.Ignore;
/*  5:   */ import org.junit.runner.Describable;
/*  6:   */ import org.junit.runner.Description;
/*  7:   */ import org.junit.runner.Request;
/*  8:   */ import org.junit.runner.Runner;
/*  9:   */ import org.junit.runner.manipulation.Filter;
/* 10:   */ import org.junit.runner.manipulation.Filterable;
/* 11:   */ import org.junit.runner.manipulation.NoTestsRemainException;
/* 12:   */ import org.junit.runner.manipulation.Sortable;
/* 13:   */ import org.junit.runner.manipulation.Sorter;
/* 14:   */ 
/* 15:   */ public class JUnit4TestAdapter
/* 16:   */   implements Test, Filterable, Sortable, Describable
/* 17:   */ {
/* 18:   */   private final Class<?> fNewTestClass;
/* 19:   */   private final Runner fRunner;
/* 20:   */   private final JUnit4TestAdapterCache fCache;
/* 21:   */   
/* 22:   */   public JUnit4TestAdapter(Class<?> newTestClass)
/* 23:   */   {
/* 24:24 */     this(newTestClass, JUnit4TestAdapterCache.getDefault());
/* 25:   */   }
/* 26:   */   
/* 27:   */   public JUnit4TestAdapter(Class<?> newTestClass, JUnit4TestAdapterCache cache)
/* 28:   */   {
/* 29:29 */     this.fCache = cache;
/* 30:30 */     this.fNewTestClass = newTestClass;
/* 31:31 */     this.fRunner = Request.classWithoutSuiteMethod(newTestClass).getRunner();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int countTestCases()
/* 35:   */   {
/* 36:35 */     return this.fRunner.testCount();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void run(TestResult result)
/* 40:   */   {
/* 41:39 */     this.fRunner.run(this.fCache.getNotifier(result, this));
/* 42:   */   }
/* 43:   */   
/* 44:   */   public List<Test> getTests()
/* 45:   */   {
/* 46:44 */     return this.fCache.asTestList(getDescription());
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Class<?> getTestClass()
/* 50:   */   {
/* 51:49 */     return this.fNewTestClass;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Description getDescription()
/* 55:   */   {
/* 56:53 */     Description description = this.fRunner.getDescription();
/* 57:54 */     return removeIgnored(description);
/* 58:   */   }
/* 59:   */   
/* 60:   */   private Description removeIgnored(Description description)
/* 61:   */   {
/* 62:58 */     if (isIgnored(description)) {
/* 63:59 */       return Description.EMPTY;
/* 64:   */     }
/* 65:60 */     Description result = description.childlessCopy();
/* 66:61 */     for (Description each : description.getChildren())
/* 67:   */     {
/* 68:62 */       Description child = removeIgnored(each);
/* 69:63 */       if (!child.isEmpty()) {
/* 70:64 */         result.addChild(child);
/* 71:   */       }
/* 72:   */     }
/* 73:66 */     return result;
/* 74:   */   }
/* 75:   */   
/* 76:   */   private boolean isIgnored(Description description)
/* 77:   */   {
/* 78:70 */     return description.getAnnotation(Ignore.class) != null;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public String toString()
/* 82:   */   {
/* 83:75 */     return this.fNewTestClass.getName();
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void filter(Filter filter)
/* 87:   */     throws NoTestsRemainException
/* 88:   */   {
/* 89:79 */     filter.apply(this.fRunner);
/* 90:   */   }
/* 91:   */   
/* 92:   */   public void sort(Sorter sorter)
/* 93:   */   {
/* 94:83 */     sorter.apply(this.fRunner);
/* 95:   */   }
/* 96:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.JUnit4TestAdapter
 * JD-Core Version:    0.7.0.1
 */