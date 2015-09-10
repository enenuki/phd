/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.w3c.css.sac.Locator;
/*   5:    */ 
/*   6:    */ public class LocatorImpl
/*   7:    */   implements Locator, Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 2240824537064705530L;
/*  10:    */   private String uri;
/*  11:    */   private int lineNumber;
/*  12:    */   private int columnNumber;
/*  13:    */   
/*  14:    */   public String getUri()
/*  15:    */   {
/*  16: 50 */     return this.uri;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void setUri(String uri)
/*  20:    */   {
/*  21: 55 */     this.uri = uri;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setLineNumber(int line)
/*  25:    */   {
/*  26: 60 */     this.lineNumber = line;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setColumnNumber(int column)
/*  30:    */   {
/*  31: 65 */     this.columnNumber = column;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public LocatorImpl(String uri, int line, int column)
/*  35:    */   {
/*  36: 71 */     this.uri = uri;
/*  37: 72 */     this.lineNumber = line;
/*  38: 73 */     this.columnNumber = column;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public LocatorImpl() {}
/*  42:    */   
/*  43:    */   public int getLineNumber()
/*  44:    */   {
/*  45: 88 */     return this.lineNumber;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getURI()
/*  49:    */   {
/*  50:101 */     return this.uri;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getColumnNumber()
/*  54:    */   {
/*  55:113 */     return this.columnNumber;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.LocatorImpl
 * JD-Core Version:    0.7.0.1
 */