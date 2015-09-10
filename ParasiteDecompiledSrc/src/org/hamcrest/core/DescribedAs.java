/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import java.util.regex.Pattern;
/*  4:   */ import org.hamcrest.BaseMatcher;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Factory;
/*  7:   */ 
/*  8:   */ public class DescribedAs<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   private final String descriptionTemplate;
/* 12:   */   private final org.hamcrest.Matcher<T> matcher;
/* 13:   */   private final Object[] values;
/* 14:20 */   private static final Pattern ARG_PATTERN = Pattern.compile("%([0-9]+)");
/* 15:   */   
/* 16:   */   public DescribedAs(String descriptionTemplate, org.hamcrest.Matcher<T> matcher, Object[] values)
/* 17:   */   {
/* 18:23 */     this.descriptionTemplate = descriptionTemplate;
/* 19:24 */     this.matcher = matcher;
/* 20:25 */     this.values = ((Object[])values.clone());
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean matches(Object o)
/* 24:   */   {
/* 25:29 */     return this.matcher.matches(o);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void describeTo(Description description)
/* 29:   */   {
/* 30:33 */     java.util.regex.Matcher arg = ARG_PATTERN.matcher(this.descriptionTemplate);
/* 31:   */     
/* 32:35 */     int textStart = 0;
/* 33:36 */     while (arg.find())
/* 34:   */     {
/* 35:37 */       description.appendText(this.descriptionTemplate.substring(textStart, arg.start()));
/* 36:38 */       int argIndex = Integer.parseInt(arg.group(1));
/* 37:39 */       description.appendValue(this.values[argIndex]);
/* 38:40 */       textStart = arg.end();
/* 39:   */     }
/* 40:43 */     if (textStart < this.descriptionTemplate.length()) {
/* 41:44 */       description.appendText(this.descriptionTemplate.substring(textStart));
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   @Factory
/* 46:   */   public static <T> org.hamcrest.Matcher<T> describedAs(String description, org.hamcrest.Matcher<T> matcher, Object... values)
/* 47:   */   {
/* 48:53 */     return new DescribedAs(description, matcher, values);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.DescribedAs
 * JD-Core Version:    0.7.0.1
 */