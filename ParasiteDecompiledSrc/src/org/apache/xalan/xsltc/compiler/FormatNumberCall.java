/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.bcel.generic.PUSH;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.RealType;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.StringType;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ 
/*  18:    */ final class FormatNumberCall
/*  19:    */   extends FunctionCall
/*  20:    */ {
/*  21:    */   private Expression _value;
/*  22:    */   private Expression _format;
/*  23:    */   private Expression _name;
/*  24: 47 */   private QName _resolvedQName = null;
/*  25:    */   
/*  26:    */   public FormatNumberCall(QName fname, Vector arguments)
/*  27:    */   {
/*  28: 50 */     super(fname, arguments);
/*  29: 51 */     this._value = argument(0);
/*  30: 52 */     this._format = argument(1);
/*  31: 53 */     this._name = (argumentCount() == 3 ? argument(2) : null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Type typeCheck(SymbolTable stable)
/*  35:    */     throws TypeCheckError
/*  36:    */   {
/*  37: 59 */     getStylesheet().numberFormattingUsed();
/*  38:    */     
/*  39: 61 */     Type tvalue = this._value.typeCheck(stable);
/*  40: 62 */     if (!(tvalue instanceof RealType)) {
/*  41: 63 */       this._value = new CastExpr(this._value, Type.Real);
/*  42:    */     }
/*  43: 65 */     Type tformat = this._format.typeCheck(stable);
/*  44: 66 */     if (!(tformat instanceof StringType)) {
/*  45: 67 */       this._format = new CastExpr(this._format, Type.String);
/*  46:    */     }
/*  47: 69 */     if (argumentCount() == 3)
/*  48:    */     {
/*  49: 70 */       Type tname = this._name.typeCheck(stable);
/*  50: 72 */       if ((this._name instanceof LiteralExpr))
/*  51:    */       {
/*  52: 73 */         LiteralExpr literal = (LiteralExpr)this._name;
/*  53: 74 */         this._resolvedQName = getParser().getQNameIgnoreDefaultNs(literal.getValue());
/*  54:    */       }
/*  55: 77 */       else if (!(tname instanceof StringType))
/*  56:    */       {
/*  57: 78 */         this._name = new CastExpr(this._name, Type.String);
/*  58:    */       }
/*  59:    */     }
/*  60: 81 */     return this._type = Type.String;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  64:    */   {
/*  65: 85 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  66: 86 */     InstructionList il = methodGen.getInstructionList();
/*  67:    */     
/*  68: 88 */     this._value.translate(classGen, methodGen);
/*  69: 89 */     this._format.translate(classGen, methodGen);
/*  70:    */     
/*  71: 91 */     int fn3arg = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "formatNumber", "(DLjava/lang/String;Ljava/text/DecimalFormat;)Ljava/lang/String;");
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76: 96 */     int get = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "getDecimalFormat", "(Ljava/lang/String;)Ljava/text/DecimalFormat;");
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:101 */     il.append(classGen.loadTranslet());
/*  82:102 */     if (this._name == null) {
/*  83:103 */       il.append(new PUSH(cpg, ""));
/*  84:105 */     } else if (this._resolvedQName != null) {
/*  85:106 */       il.append(new PUSH(cpg, this._resolvedQName.toString()));
/*  86:    */     } else {
/*  87:109 */       this._name.translate(classGen, methodGen);
/*  88:    */     }
/*  89:111 */     il.append(new INVOKEVIRTUAL(get));
/*  90:112 */     il.append(new INVOKESTATIC(fn3arg));
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FormatNumberCall
 * JD-Core Version:    0.7.0.1
 */