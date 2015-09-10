/*   1:    */ package antlr.preprocessor;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.Vector;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.Enumeration;
/*   9:    */ 
/*  10:    */ public class Tool
/*  11:    */ {
/*  12:    */   protected Hierarchy theHierarchy;
/*  13:    */   protected String grammarFileName;
/*  14:    */   protected String[] args;
/*  15:    */   protected int nargs;
/*  16:    */   protected Vector grammars;
/*  17:    */   protected antlr.Tool antlrTool;
/*  18:    */   
/*  19:    */   public Tool(antlr.Tool paramTool, String[] paramArrayOfString)
/*  20:    */   {
/*  21: 24 */     this.antlrTool = paramTool;
/*  22: 25 */     processArguments(paramArrayOfString);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static void main(String[] paramArrayOfString)
/*  26:    */   {
/*  27: 29 */     antlr.Tool localTool = new antlr.Tool();
/*  28: 30 */     Tool localTool1 = new Tool(localTool, paramArrayOfString);
/*  29: 31 */     localTool1.preprocess();
/*  30: 32 */     String[] arrayOfString = localTool1.preprocessedArgList();
/*  31: 33 */     for (int i = 0; i < arrayOfString.length; i++) {
/*  32: 34 */       System.out.print(" " + arrayOfString[i]);
/*  33:    */     }
/*  34: 36 */     System.out.println();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean preprocess()
/*  38:    */   {
/*  39: 40 */     if (this.grammarFileName == null)
/*  40:    */     {
/*  41: 41 */       this.antlrTool.toolError("no grammar file specified");
/*  42: 42 */       return false;
/*  43:    */     }
/*  44:    */     Enumeration localEnumeration;
/*  45: 44 */     if (this.grammars != null)
/*  46:    */     {
/*  47: 45 */       this.theHierarchy = new Hierarchy(this.antlrTool);
/*  48: 46 */       for (localEnumeration = this.grammars.elements(); localEnumeration.hasMoreElements();)
/*  49:    */       {
/*  50: 47 */         localObject = (String)localEnumeration.nextElement();
/*  51:    */         try
/*  52:    */         {
/*  53: 49 */           this.theHierarchy.readGrammarFile((String)localObject);
/*  54:    */         }
/*  55:    */         catch (FileNotFoundException localFileNotFoundException)
/*  56:    */         {
/*  57: 52 */           this.antlrTool.toolError("file " + (String)localObject + " not found");
/*  58: 53 */           return false;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62: 59 */     boolean bool = this.theHierarchy.verifyThatHierarchyIsComplete();
/*  63: 60 */     if (!bool) {
/*  64: 61 */       return false;
/*  65:    */     }
/*  66: 62 */     this.theHierarchy.expandGrammarsInFile(this.grammarFileName);
/*  67: 63 */     Object localObject = this.theHierarchy.getFile(this.grammarFileName);
/*  68: 64 */     String str = ((GrammarFile)localObject).nameForExpandedGrammarFile(this.grammarFileName);
/*  69: 67 */     if (str.equals(this.grammarFileName)) {
/*  70: 68 */       this.args[(this.nargs++)] = this.grammarFileName;
/*  71:    */     } else {
/*  72:    */       try
/*  73:    */       {
/*  74: 72 */         ((GrammarFile)localObject).generateExpandedFile();
/*  75: 73 */         this.args[(this.nargs++)] = (this.antlrTool.getOutputDirectory() + System.getProperty("file.separator") + str);
/*  76:    */       }
/*  77:    */       catch (IOException localIOException)
/*  78:    */       {
/*  79: 78 */         this.antlrTool.toolError("cannot write expanded grammar file " + str);
/*  80: 79 */         return false;
/*  81:    */       }
/*  82:    */     }
/*  83: 82 */     return true;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String[] preprocessedArgList()
/*  87:    */   {
/*  88: 87 */     String[] arrayOfString = new String[this.nargs];
/*  89: 88 */     System.arraycopy(this.args, 0, arrayOfString, 0, this.nargs);
/*  90: 89 */     this.args = arrayOfString;
/*  91: 90 */     return this.args;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void processArguments(String[] paramArrayOfString)
/*  95:    */   {
/*  96: 98 */     this.nargs = 0;
/*  97: 99 */     this.args = new String[paramArrayOfString.length];
/*  98:100 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  99:101 */       if (paramArrayOfString[i].length() == 0)
/* 100:    */       {
/* 101:103 */         this.antlrTool.warning("Zero length argument ignoring...");
/* 102:    */       }
/* 103:106 */       else if (paramArrayOfString[i].equals("-glib"))
/* 104:    */       {
/* 105:108 */         if ((File.separator.equals("\\")) && (paramArrayOfString[i].indexOf('/') != -1))
/* 106:    */         {
/* 107:110 */           this.antlrTool.warning("-glib cannot deal with '/' on a PC: use '\\'; ignoring...");
/* 108:    */         }
/* 109:    */         else
/* 110:    */         {
/* 111:113 */           this.grammars = antlr.Tool.parseSeparatedList(paramArrayOfString[(i + 1)], ';');
/* 112:114 */           i++;
/* 113:    */         }
/* 114:    */       }
/* 115:117 */       else if (paramArrayOfString[i].equals("-o"))
/* 116:    */       {
/* 117:118 */         this.args[(this.nargs++)] = paramArrayOfString[i];
/* 118:119 */         if (i + 1 >= paramArrayOfString.length)
/* 119:    */         {
/* 120:120 */           this.antlrTool.error("missing output directory with -o option; ignoring");
/* 121:    */         }
/* 122:    */         else
/* 123:    */         {
/* 124:123 */           i++;
/* 125:124 */           this.args[(this.nargs++)] = paramArrayOfString[i];
/* 126:125 */           this.antlrTool.setOutputDirectory(paramArrayOfString[i]);
/* 127:    */         }
/* 128:    */       }
/* 129:128 */       else if (paramArrayOfString[i].charAt(0) == '-')
/* 130:    */       {
/* 131:129 */         this.args[(this.nargs++)] = paramArrayOfString[i];
/* 132:    */       }
/* 133:    */       else
/* 134:    */       {
/* 135:133 */         this.grammarFileName = paramArrayOfString[i];
/* 136:134 */         if (this.grammars == null) {
/* 137:135 */           this.grammars = new Vector(10);
/* 138:    */         }
/* 139:137 */         this.grammars.appendElement(this.grammarFileName);
/* 140:138 */         if (i + 1 < paramArrayOfString.length)
/* 141:    */         {
/* 142:139 */           this.antlrTool.warning("grammar file must be last; ignoring other arguments...");
/* 143:140 */           break;
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.Tool
 * JD-Core Version:    0.7.0.1
 */