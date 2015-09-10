/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.CHECKCAST;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.GETFIELD;
/*   7:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*  16:    */ 
/*  17:    */ final class ParameterRef
/*  18:    */   extends VariableRefBase
/*  19:    */ {
/*  20: 45 */   QName _name = null;
/*  21:    */   
/*  22:    */   public ParameterRef(Param param)
/*  23:    */   {
/*  24: 48 */     super(param);
/*  25: 49 */     this._name = param._name;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String toString()
/*  29:    */   {
/*  30: 54 */     return "parameter-ref(" + this._variable.getName() + '/' + this._variable.getType() + ')';
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  34:    */   {
/*  35: 58 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  36: 59 */     InstructionList il = methodGen.getInstructionList();
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43: 66 */     String name = BasisLibrary.mapQNameToJavaName(this._name.toString());
/*  44: 67 */     String signature = this._type.toSignature();
/*  45: 69 */     if (this._variable.isLocal())
/*  46:    */     {
/*  47: 70 */       if (classGen.isExternal())
/*  48:    */       {
/*  49: 71 */         Closure variableClosure = this._closure;
/*  50: 72 */         while (variableClosure != null)
/*  51:    */         {
/*  52: 73 */           if (variableClosure.inInnerClass()) {
/*  53:    */             break;
/*  54:    */           }
/*  55: 74 */           variableClosure = variableClosure.getParentClosure();
/*  56:    */         }
/*  57: 77 */         if (variableClosure != null)
/*  58:    */         {
/*  59: 78 */           il.append(InstructionConstants.ALOAD_0);
/*  60: 79 */           il.append(new GETFIELD(cpg.addFieldref(variableClosure.getInnerClassName(), name, signature)));
/*  61:    */         }
/*  62:    */         else
/*  63:    */         {
/*  64: 84 */           il.append(this._variable.loadInstruction());
/*  65:    */         }
/*  66:    */       }
/*  67:    */       else
/*  68:    */       {
/*  69: 88 */         il.append(this._variable.loadInstruction());
/*  70:    */       }
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 92 */       String className = classGen.getClassName();
/*  75: 93 */       il.append(classGen.loadTranslet());
/*  76: 94 */       if (classGen.isExternal()) {
/*  77: 95 */         il.append(new CHECKCAST(cpg.addClass(className)));
/*  78:    */       }
/*  79: 97 */       il.append(new GETFIELD(cpg.addFieldref(className, name, signature)));
/*  80:    */     }
/*  81:100 */     if ((this._variable.getType() instanceof NodeSetType))
/*  82:    */     {
/*  83:102 */       int clone = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "cloneIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  84:    */       
/*  85:    */ 
/*  86:    */ 
/*  87:106 */       il.append(new INVOKEINTERFACE(clone, 1));
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ParameterRef
 * JD-Core Version:    0.7.0.1
 */