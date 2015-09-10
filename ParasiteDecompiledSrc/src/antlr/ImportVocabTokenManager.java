/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.File;
/*  5:   */ import java.io.FileNotFoundException;
/*  6:   */ import java.io.FileReader;
/*  7:   */ 
/*  8:   */ class ImportVocabTokenManager
/*  9:   */   extends SimpleTokenManager
/* 10:   */   implements Cloneable
/* 11:   */ {
/* 12:   */   private String filename;
/* 13:   */   protected Grammar grammar;
/* 14:   */   
/* 15:   */   ImportVocabTokenManager(Grammar paramGrammar, String paramString1, String paramString2, Tool paramTool)
/* 16:   */   {
/* 17:25 */     super(paramString2, paramTool);
/* 18:   */     
/* 19:27 */     this.grammar = paramGrammar;
/* 20:28 */     this.filename = paramString1;
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:33 */     File localFile = new File(this.filename);
/* 26:35 */     if (!localFile.exists())
/* 27:   */     {
/* 28:36 */       localFile = new File(this.antlrTool.getOutputDirectory(), this.filename);
/* 29:38 */       if (!localFile.exists()) {
/* 30:39 */         this.antlrTool.panic("Cannot find importVocab file '" + this.filename + "'");
/* 31:   */       }
/* 32:   */     }
/* 33:43 */     setReadOnly(true);
/* 34:   */     try
/* 35:   */     {
/* 36:47 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
/* 37:48 */       ANTLRTokdefLexer localANTLRTokdefLexer = new ANTLRTokdefLexer(localBufferedReader);
/* 38:49 */       ANTLRTokdefParser localANTLRTokdefParser = new ANTLRTokdefParser(localANTLRTokdefLexer);
/* 39:50 */       localANTLRTokdefParser.setTool(this.antlrTool);
/* 40:51 */       localANTLRTokdefParser.setFilename(this.filename);
/* 41:52 */       localANTLRTokdefParser.file(this);
/* 42:   */     }
/* 43:   */     catch (FileNotFoundException localFileNotFoundException)
/* 44:   */     {
/* 45:55 */       this.antlrTool.panic("Cannot find importVocab file '" + this.filename + "'");
/* 46:   */     }
/* 47:   */     catch (RecognitionException localRecognitionException)
/* 48:   */     {
/* 49:58 */       this.antlrTool.panic("Error parsing importVocab file '" + this.filename + "': " + localRecognitionException.toString());
/* 50:   */     }
/* 51:   */     catch (TokenStreamException localTokenStreamException)
/* 52:   */     {
/* 53:61 */       this.antlrTool.panic("Error reading importVocab file '" + this.filename + "'");
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Object clone()
/* 58:   */   {
/* 59:67 */     ImportVocabTokenManager localImportVocabTokenManager = (ImportVocabTokenManager)super.clone();
/* 60:68 */     localImportVocabTokenManager.filename = this.filename;
/* 61:69 */     localImportVocabTokenManager.grammar = this.grammar;
/* 62:70 */     return localImportVocabTokenManager;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void define(TokenSymbol paramTokenSymbol)
/* 66:   */   {
/* 67:75 */     super.define(paramTokenSymbol);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void define(String paramString, int paramInt)
/* 71:   */   {
/* 72:80 */     Object localObject = null;
/* 73:81 */     if (paramString.startsWith("\"")) {
/* 74:82 */       localObject = new StringLiteralSymbol(paramString);
/* 75:   */     } else {
/* 76:85 */       localObject = new TokenSymbol(paramString);
/* 77:   */     }
/* 78:87 */     ((TokenSymbol)localObject).setTokenType(paramInt);
/* 79:88 */     super.define((TokenSymbol)localObject);
/* 80:89 */     this.maxToken = (paramInt + 1 > this.maxToken ? paramInt + 1 : this.maxToken);
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean isReadOnly()
/* 84:   */   {
/* 85:94 */     return this.readOnly;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public int nextTokenType()
/* 89:   */   {
/* 90:99 */     return super.nextTokenType();
/* 91:   */   }
/* 92:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ImportVocabTokenManager
 * JD-Core Version:    0.7.0.1
 */