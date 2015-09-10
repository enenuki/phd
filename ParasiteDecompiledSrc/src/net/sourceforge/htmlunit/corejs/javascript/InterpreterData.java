/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*   5:    */ 
/*   6:    */ final class InterpreterData
/*   7:    */   implements Serializable, DebuggableScript
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = 5067677351589230234L;
/*  10:    */   static final int INITIAL_MAX_ICODE_LENGTH = 1024;
/*  11:    */   static final int INITIAL_STRINGTABLE_SIZE = 64;
/*  12:    */   static final int INITIAL_NUMBERTABLE_SIZE = 64;
/*  13:    */   String itsName;
/*  14:    */   String itsSourceFile;
/*  15:    */   boolean itsNeedsActivation;
/*  16:    */   int itsFunctionType;
/*  17:    */   String[] itsStringTable;
/*  18:    */   double[] itsDoubleTable;
/*  19:    */   InterpreterData[] itsNestedFunctions;
/*  20:    */   Object[] itsRegExpLiterals;
/*  21:    */   byte[] itsICode;
/*  22:    */   int[] itsExceptionTable;
/*  23:    */   int itsMaxVars;
/*  24:    */   int itsMaxLocals;
/*  25:    */   int itsMaxStack;
/*  26:    */   int itsMaxFrameArray;
/*  27:    */   String[] argNames;
/*  28:    */   boolean[] argIsConst;
/*  29:    */   int argCount;
/*  30:    */   int itsMaxCalleeArgs;
/*  31:    */   String encodedSource;
/*  32:    */   int encodedSourceStart;
/*  33:    */   int encodedSourceEnd;
/*  34:    */   int languageVersion;
/*  35:    */   boolean useDynamicScope;
/*  36:    */   boolean isStrict;
/*  37:    */   boolean topLevel;
/*  38:    */   Object[] literalIds;
/*  39:    */   UintMap longJumps;
/*  40:    */   
/*  41:    */   InterpreterData(int languageVersion, String sourceFile, String encodedSource, boolean isStrict)
/*  42:    */   {
/*  43: 58 */     this.languageVersion = languageVersion;
/*  44: 59 */     this.itsSourceFile = sourceFile;
/*  45: 60 */     this.encodedSource = encodedSource;
/*  46: 61 */     this.isStrict = isStrict;
/*  47: 62 */     init();
/*  48:    */   }
/*  49:    */   
/*  50:    */   InterpreterData(InterpreterData parent)
/*  51:    */   {
/*  52: 67 */     this.parentData = parent;
/*  53: 68 */     this.languageVersion = parent.languageVersion;
/*  54: 69 */     this.itsSourceFile = parent.itsSourceFile;
/*  55: 70 */     this.encodedSource = parent.encodedSource;
/*  56:    */     
/*  57: 72 */     init();
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void init()
/*  61:    */   {
/*  62: 77 */     this.itsICode = new byte[1024];
/*  63: 78 */     this.itsStringTable = new String[64];
/*  64:    */   }
/*  65:    */   
/*  66:121 */   int firstLinePC = -1;
/*  67:    */   InterpreterData parentData;
/*  68:    */   boolean evalScriptFlag;
/*  69:    */   
/*  70:    */   public boolean isTopLevel()
/*  71:    */   {
/*  72:129 */     return this.topLevel;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isFunction()
/*  76:    */   {
/*  77:134 */     return this.itsFunctionType != 0;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getFunctionName()
/*  81:    */   {
/*  82:139 */     return this.itsName;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getParamCount()
/*  86:    */   {
/*  87:144 */     return this.argCount;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getParamAndVarCount()
/*  91:    */   {
/*  92:149 */     return this.argNames.length;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getParamOrVarName(int index)
/*  96:    */   {
/*  97:154 */     return this.argNames[index];
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean getParamOrVarConst(int index)
/* 101:    */   {
/* 102:159 */     return this.argIsConst[index];
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getSourceName()
/* 106:    */   {
/* 107:164 */     return this.itsSourceFile;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isGeneratedScript()
/* 111:    */   {
/* 112:169 */     return ScriptRuntime.isGeneratedScript(this.itsSourceFile);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int[] getLineNumbers()
/* 116:    */   {
/* 117:174 */     return Interpreter.getLineNumbers(this);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getFunctionCount()
/* 121:    */   {
/* 122:179 */     return this.itsNestedFunctions == null ? 0 : this.itsNestedFunctions.length;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public DebuggableScript getFunction(int index)
/* 126:    */   {
/* 127:184 */     return this.itsNestedFunctions[index];
/* 128:    */   }
/* 129:    */   
/* 130:    */   public DebuggableScript getParent()
/* 131:    */   {
/* 132:189 */     return this.parentData;
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.InterpreterData
 * JD-Core Version:    0.7.0.1
 */