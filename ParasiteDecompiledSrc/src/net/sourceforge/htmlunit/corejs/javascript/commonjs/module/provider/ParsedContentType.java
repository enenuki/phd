/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.StringTokenizer;
/*  5:   */ 
/*  6:   */ public final class ParsedContentType
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private final String contentType;
/* 11:   */   private final String encoding;
/* 12:   */   
/* 13:   */   public ParsedContentType(String mimeType)
/* 14:   */   {
/* 15:25 */     String contentType = null;
/* 16:26 */     String encoding = null;
/* 17:27 */     if (mimeType != null)
/* 18:   */     {
/* 19:28 */       StringTokenizer tok = new StringTokenizer(mimeType, ";");
/* 20:29 */       if (tok.hasMoreTokens())
/* 21:   */       {
/* 22:30 */         contentType = tok.nextToken().trim();
/* 23:31 */         while (tok.hasMoreTokens())
/* 24:   */         {
/* 25:32 */           String param = tok.nextToken().trim();
/* 26:33 */           if (param.startsWith("charset="))
/* 27:   */           {
/* 28:34 */             encoding = param.substring(8).trim();
/* 29:35 */             int l = encoding.length();
/* 30:36 */             if (l <= 0) {
/* 31:   */               break;
/* 32:   */             }
/* 33:37 */             if (encoding.charAt(0) == '"') {
/* 34:38 */               encoding = encoding.substring(1);
/* 35:   */             }
/* 36:40 */             if (encoding.charAt(l - 1) != '"') {
/* 37:   */               break;
/* 38:   */             }
/* 39:41 */             encoding = encoding.substring(0, l - 1); break;
/* 40:   */           }
/* 41:   */         }
/* 42:   */       }
/* 43:   */     }
/* 44:49 */     this.contentType = contentType;
/* 45:50 */     this.encoding = encoding;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getContentType()
/* 49:   */   {
/* 50:59 */     return this.contentType;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getEncoding()
/* 54:   */   {
/* 55:68 */     return this.encoding;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.ParsedContentType
 * JD-Core Version:    0.7.0.1
 */