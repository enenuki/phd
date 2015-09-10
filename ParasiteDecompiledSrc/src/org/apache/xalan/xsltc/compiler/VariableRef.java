/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.CHECKCAST;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.GETFIELD;
/*  7:   */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  8:   */ import org.apache.bcel.generic.InstructionConstants;
/*  9:   */ import org.apache.bcel.generic.InstructionList;
/* 10:   */ import org.apache.bcel.generic.MethodGen;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/* 14:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 15:   */ 
/* 16:   */ final class VariableRef
/* 17:   */   extends VariableRefBase
/* 18:   */ {
/* 19:   */   public VariableRef(Variable variable)
/* 20:   */   {
/* 21:42 */     super(variable);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 25:   */   {
/* 26:46 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 27:47 */     InstructionList il = methodGen.getInstructionList();
/* 28:50 */     if (this._type.implementedAsMethod()) {
/* 29:50 */       return;
/* 30:   */     }
/* 31:52 */     String name = this._variable.getEscapedName();
/* 32:53 */     String signature = this._type.toSignature();
/* 33:55 */     if (this._variable.isLocal())
/* 34:   */     {
/* 35:56 */       if (classGen.isExternal())
/* 36:   */       {
/* 37:57 */         Closure variableClosure = this._closure;
/* 38:58 */         while (variableClosure != null)
/* 39:   */         {
/* 40:59 */           if (variableClosure.inInnerClass()) {
/* 41:   */             break;
/* 42:   */           }
/* 43:60 */           variableClosure = variableClosure.getParentClosure();
/* 44:   */         }
/* 45:63 */         if (variableClosure != null)
/* 46:   */         {
/* 47:64 */           il.append(InstructionConstants.ALOAD_0);
/* 48:65 */           il.append(new GETFIELD(cpg.addFieldref(variableClosure.getInnerClassName(), name, signature)));
/* 49:   */         }
/* 50:   */         else
/* 51:   */         {
/* 52:70 */           il.append(this._variable.loadInstruction());
/* 53:   */         }
/* 54:   */       }
/* 55:   */       else
/* 56:   */       {
/* 57:74 */         il.append(this._variable.loadInstruction());
/* 58:   */       }
/* 59:   */     }
/* 60:   */     else
/* 61:   */     {
/* 62:78 */       String className = classGen.getClassName();
/* 63:79 */       il.append(classGen.loadTranslet());
/* 64:80 */       if (classGen.isExternal()) {
/* 65:81 */         il.append(new CHECKCAST(cpg.addClass(className)));
/* 66:   */       }
/* 67:83 */       il.append(new GETFIELD(cpg.addFieldref(className, name, signature)));
/* 68:   */     }
/* 69:86 */     if ((this._variable.getType() instanceof NodeSetType))
/* 70:   */     {
/* 71:88 */       int clone = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "cloneIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 72:   */       
/* 73:   */ 
/* 74:   */ 
/* 75:92 */       il.append(new INVOKEINTERFACE(clone, 1));
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.VariableRef
 * JD-Core Version:    0.7.0.1
 */