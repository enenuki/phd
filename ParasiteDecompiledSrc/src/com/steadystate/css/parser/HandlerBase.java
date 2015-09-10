/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.sac.DocumentHandlerExt;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import org.w3c.css.sac.CSSException;
/*   6:    */ import org.w3c.css.sac.CSSParseException;
/*   7:    */ import org.w3c.css.sac.ErrorHandler;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.css.sac.LexicalUnit;
/*  10:    */ import org.w3c.css.sac.SACMediaList;
/*  11:    */ import org.w3c.css.sac.SelectorList;
/*  12:    */ 
/*  13:    */ public class HandlerBase
/*  14:    */   implements DocumentHandlerExt, ErrorHandler
/*  15:    */ {
/*  16:    */   public void startDocument(InputSource source)
/*  17:    */     throws CSSException
/*  18:    */   {}
/*  19:    */   
/*  20:    */   public void endDocument(InputSource source)
/*  21:    */     throws CSSException
/*  22:    */   {}
/*  23:    */   
/*  24:    */   public void comment(String text)
/*  25:    */     throws CSSException
/*  26:    */   {}
/*  27:    */   
/*  28:    */   public void ignorableAtRule(String atRule)
/*  29:    */     throws CSSException
/*  30:    */   {}
/*  31:    */   
/*  32:    */   public void namespaceDeclaration(String prefix, String uri)
/*  33:    */     throws CSSException
/*  34:    */   {}
/*  35:    */   
/*  36:    */   public void importStyle(String uri, SACMediaList media, String defaultNamespaceURI)
/*  37:    */     throws CSSException
/*  38:    */   {}
/*  39:    */   
/*  40:    */   public void startMedia(SACMediaList media)
/*  41:    */     throws CSSException
/*  42:    */   {}
/*  43:    */   
/*  44:    */   public void endMedia(SACMediaList media)
/*  45:    */     throws CSSException
/*  46:    */   {}
/*  47:    */   
/*  48:    */   public void startPage(String name, String pseudo_page)
/*  49:    */     throws CSSException
/*  50:    */   {}
/*  51:    */   
/*  52:    */   public void endPage(String name, String pseudo_page)
/*  53:    */     throws CSSException
/*  54:    */   {}
/*  55:    */   
/*  56:    */   public void startFontFace()
/*  57:    */     throws CSSException
/*  58:    */   {}
/*  59:    */   
/*  60:    */   public void endFontFace()
/*  61:    */     throws CSSException
/*  62:    */   {}
/*  63:    */   
/*  64:    */   public void startSelector(SelectorList selectors)
/*  65:    */     throws CSSException
/*  66:    */   {}
/*  67:    */   
/*  68:    */   public void endSelector(SelectorList selectors)
/*  69:    */     throws CSSException
/*  70:    */   {}
/*  71:    */   
/*  72:    */   public void property(String name, LexicalUnit value, boolean important)
/*  73:    */     throws CSSException
/*  74:    */   {}
/*  75:    */   
/*  76:    */   public void charset(String characterEncoding)
/*  77:    */     throws CSSException
/*  78:    */   {}
/*  79:    */   
/*  80:    */   public void warning(CSSParseException exception)
/*  81:    */     throws CSSException
/*  82:    */   {
/*  83: 91 */     StringBuilder sb = new StringBuilder();
/*  84: 92 */     sb.append(exception.getURI()).append(" [").append(exception.getLineNumber()).append(":").append(exception.getColumnNumber()).append("] ").append(exception.getMessage());
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91: 99 */     System.err.println(sb.toString());
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void error(CSSParseException exception)
/*  95:    */     throws CSSException
/*  96:    */   {
/*  97:103 */     StringBuilder sb = new StringBuilder();
/*  98:104 */     sb.append(exception.getURI()).append(" [").append(exception.getLineNumber()).append(":").append(exception.getColumnNumber()).append("] ").append(exception.getMessage());
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:111 */     System.err.println(sb.toString());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void fatalError(CSSParseException exception)
/* 109:    */     throws CSSException
/* 110:    */   {
/* 111:115 */     StringBuilder sb = new StringBuilder();
/* 112:116 */     sb.append(exception.getURI()).append(" [").append(exception.getLineNumber()).append(":").append(exception.getColumnNumber()).append("] ").append(exception.getMessage());
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:123 */     System.err.println(sb.toString());
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.HandlerBase
 * JD-Core Version:    0.7.0.1
 */