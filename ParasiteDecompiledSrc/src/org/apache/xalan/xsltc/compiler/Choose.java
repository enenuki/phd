/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.bcel.generic.BranchHandle;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFEQ;
/*  10:    */ import org.apache.bcel.generic.InstructionConstants;
/*  11:    */ import org.apache.bcel.generic.InstructionHandle;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.MethodGen;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  20:    */ 
/*  21:    */ final class Choose
/*  22:    */   extends Instruction
/*  23:    */ {
/*  24:    */   public void display(int indent)
/*  25:    */   {
/*  26: 53 */     indent(indent);
/*  27: 54 */     Util.println("Choose");
/*  28: 55 */     indent(indent + 4);
/*  29: 56 */     displayContents(indent + 4);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  33:    */   {
/*  34: 64 */     List whenElements = new ArrayList();
/*  35: 65 */     Otherwise otherwise = null;
/*  36: 66 */     Enumeration elements = elements();
/*  37:    */     
/*  38:    */ 
/*  39: 69 */     ErrorMsg error = null;
/*  40: 70 */     int line = getLineNumber();
/*  41: 73 */     while (elements.hasMoreElements())
/*  42:    */     {
/*  43: 74 */       Object element = elements.nextElement();
/*  44: 76 */       if ((element instanceof When))
/*  45:    */       {
/*  46: 77 */         whenElements.add(element);
/*  47:    */       }
/*  48: 80 */       else if ((element instanceof Otherwise))
/*  49:    */       {
/*  50: 81 */         if (otherwise == null)
/*  51:    */         {
/*  52: 82 */           otherwise = (Otherwise)element;
/*  53:    */         }
/*  54:    */         else
/*  55:    */         {
/*  56: 85 */           error = new ErrorMsg("MULTIPLE_OTHERWISE_ERR", this);
/*  57: 86 */           getParser().reportError(3, error);
/*  58:    */         }
/*  59:    */       }
/*  60: 89 */       else if ((element instanceof Text))
/*  61:    */       {
/*  62: 90 */         ((Text)element).ignore();
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66: 94 */         error = new ErrorMsg("WHEN_ELEMENT_ERR", this);
/*  67: 95 */         getParser().reportError(3, error);
/*  68:    */       }
/*  69:    */     }
/*  70:100 */     if (whenElements.size() == 0)
/*  71:    */     {
/*  72:101 */       error = new ErrorMsg("MISSING_WHEN_ERR", this);
/*  73:102 */       getParser().reportError(3, error);
/*  74:103 */       return;
/*  75:    */     }
/*  76:106 */     InstructionList il = methodGen.getInstructionList();
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:110 */     BranchHandle nextElement = null;
/*  81:111 */     List exitHandles = new ArrayList();
/*  82:112 */     InstructionHandle exit = null;
/*  83:    */     
/*  84:114 */     Iterator whens = whenElements.iterator();
/*  85:115 */     while (whens.hasNext())
/*  86:    */     {
/*  87:116 */       When when = (When)whens.next();
/*  88:117 */       Expression test = when.getTest();
/*  89:    */       
/*  90:119 */       InstructionHandle truec = il.getEnd();
/*  91:121 */       if (nextElement != null) {
/*  92:122 */         nextElement.setTarget(il.append(InstructionConstants.NOP));
/*  93:    */       }
/*  94:123 */       test.translateDesynthesized(classGen, methodGen);
/*  95:125 */       if ((test instanceof FunctionCall))
/*  96:    */       {
/*  97:126 */         FunctionCall call = (FunctionCall)test;
/*  98:    */         try
/*  99:    */         {
/* 100:128 */           Type type = call.typeCheck(getParser().getSymbolTable());
/* 101:129 */           if (type != Type.Boolean) {
/* 102:130 */             test._falseList.add(il.append(new IFEQ(null)));
/* 103:    */           }
/* 104:    */         }
/* 105:    */         catch (TypeCheckError e) {}
/* 106:    */       }
/* 107:138 */       truec = il.getEnd();
/* 108:142 */       if (!when.ignore()) {
/* 109:142 */         when.translateContents(classGen, methodGen);
/* 110:    */       }
/* 111:145 */       exitHandles.add(il.append(new GOTO(null)));
/* 112:146 */       if ((whens.hasNext()) || (otherwise != null))
/* 113:    */       {
/* 114:147 */         nextElement = il.append(new GOTO(null));
/* 115:148 */         test.backPatchFalseList(nextElement);
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:151 */         test.backPatchFalseList(exit = il.append(InstructionConstants.NOP));
/* 120:    */       }
/* 121:152 */       test.backPatchTrueList(truec.getNext());
/* 122:    */     }
/* 123:156 */     if (otherwise != null)
/* 124:    */     {
/* 125:157 */       nextElement.setTarget(il.append(InstructionConstants.NOP));
/* 126:158 */       otherwise.translateContents(classGen, methodGen);
/* 127:159 */       exit = il.append(InstructionConstants.NOP);
/* 128:    */     }
/* 129:163 */     Iterator exitGotos = exitHandles.iterator();
/* 130:164 */     while (exitGotos.hasNext())
/* 131:    */     {
/* 132:165 */       BranchHandle gotoExit = (BranchHandle)exitGotos.next();
/* 133:166 */       gotoExit.setTarget(exit);
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Choose
 * JD-Core Version:    0.7.0.1
 */