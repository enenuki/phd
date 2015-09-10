/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.lang.reflect.Modifier;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.bcel.generic.PUSH;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  17:    */ 
/*  18:    */ final class FunctionAvailableCall
/*  19:    */   extends FunctionCall
/*  20:    */ {
/*  21:    */   private Expression _arg;
/*  22: 44 */   private String _nameOfFunct = null;
/*  23: 45 */   private String _namespaceOfFunct = null;
/*  24: 46 */   private boolean _isFunctionAvailable = false;
/*  25:    */   
/*  26:    */   public FunctionAvailableCall(QName fname, Vector arguments)
/*  27:    */   {
/*  28: 55 */     super(fname, arguments);
/*  29: 56 */     this._arg = ((Expression)arguments.elementAt(0));
/*  30: 57 */     this._type = null;
/*  31: 59 */     if ((this._arg instanceof LiteralExpr))
/*  32:    */     {
/*  33: 60 */       LiteralExpr arg = (LiteralExpr)this._arg;
/*  34: 61 */       this._namespaceOfFunct = arg.getNamespace();
/*  35: 62 */       this._nameOfFunct = arg.getValue();
/*  36: 64 */       if (!isInternalNamespace()) {
/*  37: 65 */         this._isFunctionAvailable = hasMethods();
/*  38:    */       }
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Type typeCheck(SymbolTable stable)
/*  43:    */     throws TypeCheckError
/*  44:    */   {
/*  45: 75 */     if (this._type != null) {
/*  46: 76 */       return this._type;
/*  47:    */     }
/*  48: 78 */     if ((this._arg instanceof LiteralExpr)) {
/*  49: 79 */       return this._type = Type.Boolean;
/*  50:    */     }
/*  51: 81 */     ErrorMsg err = new ErrorMsg("NEED_LITERAL_ERR", "function-available", this);
/*  52:    */     
/*  53: 83 */     throw new TypeCheckError(err);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object evaluateAtCompileTime()
/*  57:    */   {
/*  58: 92 */     return getResult() ? Boolean.TRUE : Boolean.FALSE;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private boolean hasMethods()
/*  62:    */   {
/*  63:102 */     String className = getClassNameFromUri(this._namespaceOfFunct);
/*  64:    */     
/*  65:    */ 
/*  66:105 */     String methodName = null;
/*  67:106 */     int colonIndex = this._nameOfFunct.indexOf(":");
/*  68:107 */     if (colonIndex > 0)
/*  69:    */     {
/*  70:108 */       String functionName = this._nameOfFunct.substring(colonIndex + 1);
/*  71:109 */       int lastDotIndex = functionName.lastIndexOf('.');
/*  72:110 */       if (lastDotIndex > 0)
/*  73:    */       {
/*  74:111 */         methodName = functionName.substring(lastDotIndex + 1);
/*  75:112 */         if ((className != null) && (!className.equals(""))) {
/*  76:113 */           className = className + "." + functionName.substring(0, lastDotIndex);
/*  77:    */         } else {
/*  78:115 */           className = functionName.substring(0, lastDotIndex);
/*  79:    */         }
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83:118 */         methodName = functionName;
/*  84:    */       }
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88:121 */       methodName = this._nameOfFunct;
/*  89:    */     }
/*  90:123 */     if ((className == null) || (methodName == null)) {
/*  91:124 */       return false;
/*  92:    */     }
/*  93:128 */     if (methodName.indexOf('-') > 0) {
/*  94:129 */       methodName = FunctionCall.replaceDash(methodName);
/*  95:    */     }
/*  96:    */     try
/*  97:    */     {
/*  98:132 */       Class clazz = ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
/*  99:135 */       if (clazz == null) {
/* 100:136 */         return false;
/* 101:    */       }
/* 102:139 */       Method[] methods = clazz.getMethods();
/* 103:141 */       for (int i = 0; i < methods.length; i++)
/* 104:    */       {
/* 105:142 */         int mods = methods[i].getModifiers();
/* 106:144 */         if ((Modifier.isPublic(mods)) && (Modifier.isStatic(mods)) && (methods[i].getName().equals(methodName))) {
/* 107:147 */           return true;
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:    */     catch (ClassNotFoundException e)
/* 112:    */     {
/* 113:152 */       return false;
/* 114:    */     }
/* 115:154 */     return false;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean getResult()
/* 119:    */   {
/* 120:162 */     if (this._nameOfFunct == null) {
/* 121:163 */       return false;
/* 122:    */     }
/* 123:166 */     if (isInternalNamespace())
/* 124:    */     {
/* 125:167 */       Parser parser = getParser();
/* 126:168 */       this._isFunctionAvailable = parser.functionSupported(Util.getLocalName(this._nameOfFunct));
/* 127:    */     }
/* 128:171 */     return this._isFunctionAvailable;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private boolean isInternalNamespace()
/* 132:    */   {
/* 133:178 */     return (this._namespaceOfFunct == null) || (this._namespaceOfFunct.equals("")) || (this._namespaceOfFunct.equals("http://xml.apache.org/xalan/xsltc"));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 137:    */   {
/* 138:189 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 139:190 */     methodGen.getInstructionList().append(new PUSH(cpg, getResult()));
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FunctionAvailableCall
 * JD-Core Version:    0.7.0.1
 */