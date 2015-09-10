/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javassist.CtClass;
/*   5:    */ import javassist.CtMethod;
/*   6:    */ import javassist.Modifier;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ import javassist.bytecode.BadBytecode;
/*   9:    */ import javassist.bytecode.CodeAttribute;
/*  10:    */ import javassist.bytecode.CodeIterator;
/*  11:    */ import javassist.bytecode.ConstPool;
/*  12:    */ import javassist.bytecode.Descriptor;
/*  13:    */ import javassist.bytecode.InstructionPrinter;
/*  14:    */ import javassist.bytecode.MethodInfo;
/*  15:    */ 
/*  16:    */ public final class FramePrinter
/*  17:    */ {
/*  18:    */   private final PrintStream stream;
/*  19:    */   
/*  20:    */   public FramePrinter(PrintStream stream)
/*  21:    */   {
/*  22: 44 */     this.stream = stream;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static void print(CtClass clazz, PrintStream stream)
/*  26:    */   {
/*  27: 51 */     new FramePrinter(stream).print(clazz);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void print(CtClass clazz)
/*  31:    */   {
/*  32: 58 */     CtMethod[] methods = clazz.getDeclaredMethods();
/*  33: 59 */     for (int i = 0; i < methods.length; i++) {
/*  34: 60 */       print(methods[i]);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   private String getMethodString(CtMethod method)
/*  39:    */   {
/*  40:    */     try
/*  41:    */     {
/*  42: 66 */       return Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + Descriptor.toString(method.getSignature()) + ";";
/*  43:    */     }
/*  44:    */     catch (NotFoundException e)
/*  45:    */     {
/*  46: 70 */       throw new RuntimeException(e);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void print(CtMethod method)
/*  51:    */   {
/*  52: 78 */     this.stream.println("\n" + getMethodString(method));
/*  53: 79 */     MethodInfo info = method.getMethodInfo2();
/*  54: 80 */     ConstPool pool = info.getConstPool();
/*  55: 81 */     CodeAttribute code = info.getCodeAttribute();
/*  56: 82 */     if (code == null) {
/*  57:    */       return;
/*  58:    */     }
/*  59:    */     Frame[] frames;
/*  60:    */     try
/*  61:    */     {
/*  62: 87 */       frames = new Analyzer().analyze(method.getDeclaringClass(), info);
/*  63:    */     }
/*  64:    */     catch (BadBytecode e)
/*  65:    */     {
/*  66: 89 */       throw new RuntimeException(e);
/*  67:    */     }
/*  68: 92 */     int spacing = String.valueOf(code.getCodeLength()).length();
/*  69:    */     
/*  70: 94 */     CodeIterator iterator = code.iterator();
/*  71: 95 */     while (iterator.hasNext())
/*  72:    */     {
/*  73:    */       int pos;
/*  74:    */       try
/*  75:    */       {
/*  76: 98 */         pos = iterator.next();
/*  77:    */       }
/*  78:    */       catch (BadBytecode e)
/*  79:    */       {
/*  80:100 */         throw new RuntimeException(e);
/*  81:    */       }
/*  82:103 */       this.stream.println(pos + ": " + InstructionPrinter.instructionString(iterator, pos, pool));
/*  83:    */       
/*  84:105 */       addSpacing(spacing + 3);
/*  85:106 */       Frame frame = frames[pos];
/*  86:107 */       if (frame == null)
/*  87:    */       {
/*  88:108 */         this.stream.println("--DEAD CODE--");
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:111 */         printStack(frame);
/*  93:    */         
/*  94:113 */         addSpacing(spacing + 3);
/*  95:114 */         printLocals(frame);
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void printStack(Frame frame)
/* 101:    */   {
/* 102:120 */     this.stream.print("stack [");
/* 103:121 */     int top = frame.getTopIndex();
/* 104:122 */     for (int i = 0; i <= top; i++)
/* 105:    */     {
/* 106:123 */       if (i > 0) {
/* 107:124 */         this.stream.print(", ");
/* 108:    */       }
/* 109:125 */       Type type = frame.getStack(i);
/* 110:126 */       this.stream.print(type);
/* 111:    */     }
/* 112:128 */     this.stream.println("]");
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void printLocals(Frame frame)
/* 116:    */   {
/* 117:132 */     this.stream.print("locals [");
/* 118:133 */     int length = frame.localsLength();
/* 119:134 */     for (int i = 0; i < length; i++)
/* 120:    */     {
/* 121:135 */       if (i > 0) {
/* 122:136 */         this.stream.print(", ");
/* 123:    */       }
/* 124:137 */       Type type = frame.getLocal(i);
/* 125:138 */       this.stream.print(type == null ? "empty" : type.toString());
/* 126:    */     }
/* 127:140 */     this.stream.println("]");
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void addSpacing(int count)
/* 131:    */   {
/* 132:144 */     while (count-- > 0) {
/* 133:145 */       this.stream.print(' ');
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.FramePrinter
 * JD-Core Version:    0.7.0.1
 */