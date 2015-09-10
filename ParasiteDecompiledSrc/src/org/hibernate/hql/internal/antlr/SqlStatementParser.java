/*   1:    */ package org.hibernate.hql.internal.antlr;
/*   2:    */ 
/*   3:    */ import antlr.LLkParser;
/*   4:    */ import antlr.ParserSharedInputState;
/*   5:    */ import antlr.RecognitionException;
/*   6:    */ import antlr.Token;
/*   7:    */ import antlr.TokenBuffer;
/*   8:    */ import antlr.TokenStream;
/*   9:    */ import antlr.TokenStreamException;
/*  10:    */ import antlr.collections.impl.BitSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.LinkedList;
/*  13:    */ import java.util.List;
/*  14:    */ import org.hibernate.hql.internal.ast.ErrorReporter;
/*  15:    */ 
/*  16:    */ public class SqlStatementParser
/*  17:    */   extends LLkParser
/*  18:    */   implements SqlStatementParserTokenTypes
/*  19:    */ {
/*  20: 34 */   private ErrorHandler errorHandler = new ErrorHandler(null);
/*  21:    */   
/*  22:    */   public void reportError(RecognitionException e)
/*  23:    */   {
/*  24: 38 */     this.errorHandler.reportError(e);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void reportError(String s)
/*  28:    */   {
/*  29: 43 */     this.errorHandler.reportError(s);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void reportWarning(String s)
/*  33:    */   {
/*  34: 48 */     this.errorHandler.reportWarning(s);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void throwExceptionIfErrorOccurred()
/*  38:    */   {
/*  39: 52 */     if (this.errorHandler.hasErrors()) {
/*  40: 53 */       throw new StatementParserException(this.errorHandler.getErrorMessage());
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44: 58 */   private List<String> statementList = new LinkedList();
/*  45: 61 */   private StringBuilder current = new StringBuilder();
/*  46:    */   
/*  47:    */   protected void out(String stmt)
/*  48:    */   {
/*  49: 64 */     this.current.append(stmt);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected void out(Token token)
/*  53:    */   {
/*  54: 68 */     out(token.getText());
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<String> getStatementList()
/*  58:    */   {
/*  59: 72 */     return this.statementList;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void statementEnd()
/*  63:    */   {
/*  64: 76 */     this.statementList.add(this.current.toString().trim());
/*  65: 77 */     this.current = new StringBuilder();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public class StatementParserException
/*  69:    */     extends RuntimeException
/*  70:    */   {
/*  71:    */     public StatementParserException(String message)
/*  72:    */     {
/*  73: 82 */       super();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   private class ErrorHandler
/*  78:    */     implements ErrorReporter
/*  79:    */   {
/*  80: 87 */     private List<String> errorList = new LinkedList();
/*  81:    */     
/*  82:    */     private ErrorHandler() {}
/*  83:    */     
/*  84:    */     public void reportError(RecognitionException e)
/*  85:    */     {
/*  86: 91 */       reportError(e.toString());
/*  87:    */     }
/*  88:    */     
/*  89:    */     public void reportError(String s)
/*  90:    */     {
/*  91: 96 */       this.errorList.add(s);
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void reportWarning(String s) {}
/*  95:    */     
/*  96:    */     public boolean hasErrors()
/*  97:    */     {
/*  98:104 */       return !this.errorList.isEmpty();
/*  99:    */     }
/* 100:    */     
/* 101:    */     public String getErrorMessage()
/* 102:    */     {
/* 103:108 */       StringBuffer buf = new StringBuffer();
/* 104:109 */       for (Iterator iterator = this.errorList.iterator(); iterator.hasNext();)
/* 105:    */       {
/* 106:110 */         buf.append((String)iterator.next());
/* 107:111 */         if (iterator.hasNext()) {
/* 108:112 */           buf.append("\n");
/* 109:    */         }
/* 110:    */       }
/* 111:115 */       return buf.toString();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected SqlStatementParser(TokenBuffer tokenBuf, int k)
/* 116:    */   {
/* 117:120 */     super(tokenBuf, k);
/* 118:121 */     this.tokenNames = _tokenNames;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public SqlStatementParser(TokenBuffer tokenBuf)
/* 122:    */   {
/* 123:125 */     this(tokenBuf, 1);
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected SqlStatementParser(TokenStream lexer, int k)
/* 127:    */   {
/* 128:129 */     super(lexer, k);
/* 129:130 */     this.tokenNames = _tokenNames;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public SqlStatementParser(TokenStream lexer)
/* 133:    */   {
/* 134:134 */     this(lexer, 1);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public SqlStatementParser(ParserSharedInputState state)
/* 138:    */   {
/* 139:138 */     super(state, 1);
/* 140:139 */     this.tokenNames = _tokenNames;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public final void script()
/* 144:    */     throws RecognitionException, TokenStreamException
/* 145:    */   {
/* 146:    */     try
/* 147:    */     {
/* 148:149 */       while ((LA(1) >= 4) && (LA(1) <= 6)) {
/* 149:150 */         statement();
/* 150:    */       }
/* 151:    */     }
/* 152:    */     catch (RecognitionException ex)
/* 153:    */     {
/* 154:160 */       reportError(ex);
/* 155:161 */       recover(ex, _tokenSet_0);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public final void statement()
/* 160:    */     throws RecognitionException, TokenStreamException
/* 161:    */   {
/* 162:167 */     Token s = null;
/* 163:168 */     Token q = null;
/* 164:    */     try
/* 165:    */     {
/* 166:    */       for (;;)
/* 167:    */       {
/* 168:174 */         switch (LA(1))
/* 169:    */         {
/* 170:    */         case 4: 
/* 171:177 */           s = LT(1);
/* 172:178 */           match(4);
/* 173:179 */           out(s);
/* 174:180 */           break;
/* 175:    */         case 5: 
/* 176:184 */           q = LT(1);
/* 177:185 */           match(5);
/* 178:186 */           out(q);
/* 179:    */         }
/* 180:    */       }
/* 181:196 */       match(6);
/* 182:197 */       statementEnd();
/* 183:    */     }
/* 184:    */     catch (RecognitionException ex)
/* 185:    */     {
/* 186:200 */       reportError(ex);
/* 187:201 */       recover(ex, _tokenSet_1);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:206 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "NOT_STMT_END", "QUOTED_STRING", "STMT_END", "ESCqs", "LINE_COMMENT", "MULTILINE_COMMENT" };
/* 192:    */   
/* 193:    */   private static final long[] mk_tokenSet_0()
/* 194:    */   {
/* 195:220 */     long[] data = { 2L, 0L };
/* 196:221 */     return data;
/* 197:    */   }
/* 198:    */   
/* 199:223 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 200:    */   
/* 201:    */   private static final long[] mk_tokenSet_1()
/* 202:    */   {
/* 203:225 */     long[] data = { 114L, 0L };
/* 204:226 */     return data;
/* 205:    */   }
/* 206:    */   
/* 207:228 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 208:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.antlr.SqlStatementParser
 * JD-Core Version:    0.7.0.1
 */