/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.PrintWriter;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Map;
/*  7:   */ 
/*  8:   */ public class DefaultJavaCodeGeneratorPrintWriterManager
/*  9:   */   implements JavaCodeGeneratorPrintWriterManager
/* 10:   */ {
/* 11:   */   private Grammar grammar;
/* 12:   */   private PrintWriterWithSMAP smapOutput;
/* 13:   */   private PrintWriter currentOutput;
/* 14:   */   private Tool tool;
/* 15:13 */   private Map sourceMaps = new HashMap();
/* 16:   */   private String currentFileName;
/* 17:   */   
/* 18:   */   public PrintWriter setupOutput(Tool paramTool, Grammar paramGrammar)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:17 */     return setupOutput(paramTool, paramGrammar, null);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public PrintWriter setupOutput(Tool paramTool, String paramString)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:21 */     return setupOutput(paramTool, null, paramString);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public PrintWriter setupOutput(Tool paramTool, Grammar paramGrammar, String paramString)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:25 */     this.tool = paramTool;
/* 34:26 */     this.grammar = paramGrammar;
/* 35:28 */     if (paramString == null) {
/* 36:29 */       paramString = paramGrammar.getClassName();
/* 37:   */     }
/* 38:31 */     this.smapOutput = new PrintWriterWithSMAP(paramTool.openOutputFile(paramString + ".java"));
/* 39:32 */     this.currentFileName = (paramString + ".java");
/* 40:33 */     this.currentOutput = this.smapOutput;
/* 41:34 */     return this.currentOutput;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void startMapping(int paramInt)
/* 45:   */   {
/* 46:38 */     this.smapOutput.startMapping(paramInt);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void startSingleSourceLineMapping(int paramInt)
/* 50:   */   {
/* 51:42 */     this.smapOutput.startSingleSourceLineMapping(paramInt);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void endMapping()
/* 55:   */   {
/* 56:46 */     this.smapOutput.endMapping();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void finishOutput()
/* 60:   */     throws IOException
/* 61:   */   {
/* 62:50 */     this.currentOutput.close();
/* 63:51 */     if (this.grammar != null)
/* 64:   */     {
/* 65:53 */       PrintWriter localPrintWriter = this.tool.openOutputFile(this.grammar.getClassName() + ".smap");
/* 66:54 */       String str = this.grammar.getFilename();
/* 67:55 */       str = str.replace('\\', '/');
/* 68:56 */       int i = str.lastIndexOf('/');
/* 69:57 */       if (i != -1) {
/* 70:58 */         str = str.substring(i + 1);
/* 71:   */       }
/* 72:59 */       this.smapOutput.dump(localPrintWriter, this.grammar.getClassName(), str);
/* 73:60 */       this.sourceMaps.put(this.currentFileName, this.smapOutput.getSourceMap());
/* 74:   */     }
/* 75:62 */     this.currentOutput = null;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public Map getSourceMaps()
/* 79:   */   {
/* 80:66 */     return this.sourceMaps;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public int getCurrentOutputLine()
/* 84:   */   {
/* 85:71 */     return this.smapOutput.getCurrentOutputLine();
/* 86:   */   }
/* 87:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DefaultJavaCodeGeneratorPrintWriterManager
 * JD-Core Version:    0.7.0.1
 */