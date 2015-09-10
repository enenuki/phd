/*   1:    */ package org.junit.rules;
/*   2:    */ 
/*   3:    */ import org.hamcrest.CoreMatchers;
/*   4:    */ import org.hamcrest.Matcher;
/*   5:    */ import org.hamcrest.StringDescription;
/*   6:    */ import org.junit.Assert;
/*   7:    */ import org.junit.internal.matchers.CombinableMatcher;
/*   8:    */ import org.junit.internal.matchers.TypeSafeMatcher;
/*   9:    */ import org.junit.matchers.JUnitMatchers;
/*  10:    */ import org.junit.runners.model.Statement;
/*  11:    */ 
/*  12:    */ public class ExpectedException
/*  13:    */   extends TestRule
/*  14:    */ {
/*  15:    */   public static ExpectedException none()
/*  16:    */   {
/*  17: 50 */     return new ExpectedException();
/*  18:    */   }
/*  19:    */   
/*  20: 53 */   private Matcher<Object> fMatcher = null;
/*  21:    */   
/*  22:    */   protected Statement apply(Statement base, org.junit.runner.Description description)
/*  23:    */   {
/*  24: 62 */     return new ExpectedExceptionStatement(base);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void expect(Matcher<?> matcher)
/*  28:    */   {
/*  29: 71 */     if (this.fMatcher == null) {
/*  30: 72 */       this.fMatcher = matcher;
/*  31:    */     } else {
/*  32: 74 */       this.fMatcher = JUnitMatchers.both(this.fMatcher).and(matcher);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void expect(Class<? extends Throwable> type)
/*  37:    */   {
/*  38: 82 */     expect(CoreMatchers.instanceOf(type));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void expectMessage(String substring)
/*  42:    */   {
/*  43: 90 */     expectMessage(JUnitMatchers.containsString(substring));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void expectMessage(Matcher<String> matcher)
/*  47:    */   {
/*  48: 98 */     expect(hasMessage(matcher));
/*  49:    */   }
/*  50:    */   
/*  51:    */   private class ExpectedExceptionStatement
/*  52:    */     extends Statement
/*  53:    */   {
/*  54:    */     private final Statement fNext;
/*  55:    */     
/*  56:    */     public ExpectedExceptionStatement(Statement base)
/*  57:    */     {
/*  58:105 */       this.fNext = base;
/*  59:    */     }
/*  60:    */     
/*  61:    */     public void evaluate()
/*  62:    */       throws Throwable
/*  63:    */     {
/*  64:    */       try
/*  65:    */       {
/*  66:111 */         this.fNext.evaluate();
/*  67:    */       }
/*  68:    */       catch (Throwable e)
/*  69:    */       {
/*  70:113 */         if (ExpectedException.this.fMatcher == null) {
/*  71:114 */           throw e;
/*  72:    */         }
/*  73:115 */         Assert.assertThat(e, ExpectedException.this.fMatcher);
/*  74:116 */         return;
/*  75:    */       }
/*  76:118 */       if (ExpectedException.this.fMatcher != null) {
/*  77:119 */         throw new AssertionError("Expected test to throw " + StringDescription.toString(ExpectedException.this.fMatcher));
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   private Matcher<Throwable> hasMessage(final Matcher<String> matcher)
/*  83:    */   {
/*  84:125 */     new TypeSafeMatcher()
/*  85:    */     {
/*  86:    */       public void describeTo(org.hamcrest.Description description)
/*  87:    */       {
/*  88:127 */         description.appendText("exception with message ");
/*  89:128 */         description.appendDescriptionOf(matcher);
/*  90:    */       }
/*  91:    */       
/*  92:    */       public boolean matchesSafely(Throwable item)
/*  93:    */       {
/*  94:133 */         return matcher.matches(item.getMessage());
/*  95:    */       }
/*  96:    */     };
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.ExpectedException
 * JD-Core Version:    0.7.0.1
 */