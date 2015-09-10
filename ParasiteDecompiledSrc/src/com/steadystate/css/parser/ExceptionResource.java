/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ExceptionResource
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:35 */     return contents;
/* 11:   */   }
/* 12:   */   
/* 13:38 */   static final Object[][] contents = { { "s0", "Syntax error" }, { "s1", "Array out of bounds error" }, { "s2", "This style sheet is read only" }, { "s3", "The text does not represent an unknown rule" }, { "s4", "The text does not represent a style rule" }, { "s5", "The text does not represent a charset rule" }, { "s6", "The text does not represent an import rule" }, { "s7", "The text does not represent a media rule" }, { "s8", "The text does not represent a font face rule" }, { "s9", "The text does not represent a page rule" }, { "s10", "This isn't a Float type" }, { "s11", "This isn't a String type" }, { "s12", "This isn't a Counter type" }, { "s13", "This isn't a Rect type" }, { "s14", "This isn't an RGBColor type" }, { "s15", "A charset rule must be the first rule" }, { "s16", "A charset rule already exists" }, { "s17", "An import rule must preceed all other rules" }, { "s18", "The specified type was not found" } };
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.ExceptionResource
 * JD-Core Version:    0.7.0.1
 */