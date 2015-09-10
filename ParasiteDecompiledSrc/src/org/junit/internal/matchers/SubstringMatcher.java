/*  1:   */ package org.junit.internal.matchers;
/*  2:   */ 
/*  3:   */ import org.hamcrest.Description;
/*  4:   */ 
/*  5:   */ public abstract class SubstringMatcher
/*  6:   */   extends TypeSafeMatcher<String>
/*  7:   */ {
/*  8:   */   protected final String substring;
/*  9:   */   
/* 10:   */   protected SubstringMatcher(String substring)
/* 11:   */   {
/* 12:10 */     this.substring = substring;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean matchesSafely(String item)
/* 16:   */   {
/* 17:15 */     return evalSubstringOf(item);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void describeTo(Description description)
/* 21:   */   {
/* 22:19 */     description.appendText("a string ").appendText(relationship()).appendText(" ").appendValue(this.substring);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected abstract boolean evalSubstringOf(String paramString);
/* 26:   */   
/* 27:   */   protected abstract String relationship();
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.matchers.SubstringMatcher
 * JD-Core Version:    0.7.0.1
 */