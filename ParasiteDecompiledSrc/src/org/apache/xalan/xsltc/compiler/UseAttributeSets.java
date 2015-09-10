/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  14:    */ 
/*  15:    */ final class UseAttributeSets
/*  16:    */   extends Instruction
/*  17:    */ {
/*  18:    */   private static final String ATTR_SET_NOT_FOUND = "";
/*  19: 48 */   private final Vector _sets = new Vector(2);
/*  20:    */   
/*  21:    */   public UseAttributeSets(String setNames, Parser parser)
/*  22:    */   {
/*  23: 54 */     setParser(parser);
/*  24: 55 */     addAttributeSets(setNames);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addAttributeSets(String setNames)
/*  28:    */   {
/*  29: 64 */     if ((setNames != null) && (!setNames.equals("")))
/*  30:    */     {
/*  31: 65 */       StringTokenizer tokens = new StringTokenizer(setNames);
/*  32: 66 */       while (tokens.hasMoreTokens())
/*  33:    */       {
/*  34: 67 */         QName qname = getParser().getQNameIgnoreDefaultNs(tokens.nextToken());
/*  35:    */         
/*  36: 69 */         this._sets.add(qname);
/*  37:    */       }
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Type typeCheck(SymbolTable stable)
/*  42:    */     throws TypeCheckError
/*  43:    */   {
/*  44: 78 */     return Type.Void;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  48:    */   {
/*  49: 86 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  50: 87 */     InstructionList il = methodGen.getInstructionList();
/*  51: 88 */     SymbolTable symbolTable = getParser().getSymbolTable();
/*  52: 91 */     for (int i = 0; i < this._sets.size(); i++)
/*  53:    */     {
/*  54: 93 */       QName name = (QName)this._sets.elementAt(i);
/*  55:    */       
/*  56: 95 */       AttributeSet attrs = symbolTable.lookupAttributeSet(name);
/*  57: 97 */       if (attrs != null)
/*  58:    */       {
/*  59: 98 */         String methodName = attrs.getMethodName();
/*  60: 99 */         il.append(classGen.loadTranslet());
/*  61:100 */         il.append(methodGen.loadDOM());
/*  62:101 */         il.append(methodGen.loadIterator());
/*  63:102 */         il.append(methodGen.loadHandler());
/*  64:103 */         int method = cpg.addMethodref(classGen.getClassName(), methodName, Constants.ATTR_SET_SIG);
/*  65:    */         
/*  66:105 */         il.append(new INVOKESPECIAL(method));
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70:109 */         Parser parser = getParser();
/*  71:110 */         String atrs = name.toString();
/*  72:111 */         reportError(this, parser, "ATTRIBSET_UNDEF_ERR", atrs);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.UseAttributeSets
 * JD-Core Version:    0.7.0.1
 */