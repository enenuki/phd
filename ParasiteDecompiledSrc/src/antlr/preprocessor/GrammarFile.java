/*  1:   */ package antlr.preprocessor;
/*  2:   */ 
/*  3:   */ import antlr.Tool;
/*  4:   */ import antlr.collections.impl.IndexedVector;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.PrintWriter;
/*  7:   */ import java.util.Enumeration;
/*  8:   */ 
/*  9:   */ public class GrammarFile
/* 10:   */ {
/* 11:   */   protected String fileName;
/* 12:20 */   protected String headerAction = "";
/* 13:   */   protected IndexedVector options;
/* 14:   */   protected IndexedVector grammars;
/* 15:23 */   protected boolean expanded = false;
/* 16:   */   protected Tool tool;
/* 17:   */   
/* 18:   */   public GrammarFile(Tool paramTool, String paramString)
/* 19:   */   {
/* 20:27 */     this.fileName = paramString;
/* 21:28 */     this.grammars = new IndexedVector();
/* 22:29 */     this.tool = paramTool;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void addGrammar(Grammar paramGrammar)
/* 26:   */   {
/* 27:33 */     this.grammars.appendElement(paramGrammar.getName(), paramGrammar);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void generateExpandedFile()
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:37 */     if (!this.expanded) {
/* 34:38 */       return;
/* 35:   */     }
/* 36:40 */     String str = nameForExpandedGrammarFile(getName());
/* 37:   */     
/* 38:   */ 
/* 39:43 */     PrintWriter localPrintWriter = this.tool.openOutputFile(str);
/* 40:44 */     localPrintWriter.println(toString());
/* 41:45 */     localPrintWriter.close();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public IndexedVector getGrammars()
/* 45:   */   {
/* 46:49 */     return this.grammars;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String getName()
/* 50:   */   {
/* 51:53 */     return this.fileName;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String nameForExpandedGrammarFile(String paramString)
/* 55:   */   {
/* 56:57 */     if (this.expanded) {
/* 57:59 */       return "expanded" + this.tool.fileMinusPath(paramString);
/* 58:   */     }
/* 59:62 */     return paramString;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setExpanded(boolean paramBoolean)
/* 63:   */   {
/* 64:67 */     this.expanded = paramBoolean;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void addHeaderAction(String paramString)
/* 68:   */   {
/* 69:71 */     this.headerAction = (this.headerAction + paramString + System.getProperty("line.separator"));
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setOptions(IndexedVector paramIndexedVector)
/* 73:   */   {
/* 74:75 */     this.options = paramIndexedVector;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public String toString()
/* 78:   */   {
/* 79:79 */     String str1 = this.headerAction == null ? "" : this.headerAction;
/* 80:80 */     String str2 = this.options == null ? "" : Hierarchy.optionsToString(this.options);
/* 81:   */     
/* 82:82 */     StringBuffer localStringBuffer = new StringBuffer(10000);localStringBuffer.append(str1);localStringBuffer.append(str2);
/* 83:83 */     for (Enumeration localEnumeration = this.grammars.elements(); localEnumeration.hasMoreElements();)
/* 84:   */     {
/* 85:84 */       Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/* 86:85 */       localStringBuffer.append(localGrammar.toString());
/* 87:   */     }
/* 88:87 */     return localStringBuffer.toString();
/* 89:   */   }
/* 90:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.GrammarFile
 * JD-Core Version:    0.7.0.1
 */