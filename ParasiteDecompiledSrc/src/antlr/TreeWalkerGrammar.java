/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ class TreeWalkerGrammar
/*  6:   */   extends Grammar
/*  7:   */ {
/*  8:21 */   protected boolean transform = false;
/*  9:   */   
/* 10:   */   TreeWalkerGrammar(String paramString1, Tool paramTool, String paramString2)
/* 11:   */   {
/* 12:25 */     super(paramString1, paramTool, paramString2);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void generate()
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:30 */     this.generator.gen(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getSuperClass()
/* 22:   */   {
/* 23:35 */     return "TreeParser";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processArguments(String[] paramArrayOfString)
/* 27:   */   {
/* 28:44 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 29:45 */       if (paramArrayOfString[i].equals("-trace"))
/* 30:   */       {
/* 31:46 */         this.traceRules = true;
/* 32:47 */         this.antlrTool.setArgOK(i);
/* 33:   */       }
/* 34:49 */       else if (paramArrayOfString[i].equals("-traceTreeParser"))
/* 35:   */       {
/* 36:50 */         this.traceRules = true;
/* 37:51 */         this.antlrTool.setArgOK(i);
/* 38:   */       }
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean setOption(String paramString, Token paramToken)
/* 43:   */   {
/* 44:63 */     if (paramString.equals("buildAST"))
/* 45:   */     {
/* 46:64 */       if (paramToken.getText().equals("true")) {
/* 47:65 */         this.buildAST = true;
/* 48:67 */       } else if (paramToken.getText().equals("false")) {
/* 49:68 */         this.buildAST = false;
/* 50:   */       } else {
/* 51:71 */         this.antlrTool.error("buildAST option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 52:   */       }
/* 53:73 */       return true;
/* 54:   */     }
/* 55:75 */     if (paramString.equals("ASTLabelType"))
/* 56:   */     {
/* 57:76 */       super.setOption(paramString, paramToken);
/* 58:77 */       return true;
/* 59:   */     }
/* 60:79 */     if (paramString.equals("className"))
/* 61:   */     {
/* 62:80 */       super.setOption(paramString, paramToken);
/* 63:81 */       return true;
/* 64:   */     }
/* 65:83 */     if (super.setOption(paramString, paramToken)) {
/* 66:84 */       return true;
/* 67:   */     }
/* 68:86 */     this.antlrTool.error("Invalid option: " + paramString, getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 69:87 */     return false;
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TreeWalkerGrammar
 * JD-Core Version:    0.7.0.1
 */