/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Dictionary;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.GOTO_W;
/*   6:    */ import org.apache.bcel.generic.InstructionHandle;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  10:    */ 
/*  11:    */ final class TestSeq
/*  12:    */ {
/*  13:    */   private int _kernelType;
/*  14: 60 */   private Vector _patterns = null;
/*  15: 65 */   private Mode _mode = null;
/*  16: 70 */   private Template _default = null;
/*  17:    */   private InstructionList _instructionList;
/*  18: 80 */   private InstructionHandle _start = null;
/*  19:    */   
/*  20:    */   public TestSeq(Vector patterns, Mode mode)
/*  21:    */   {
/*  22: 86 */     this(patterns, -2, mode);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TestSeq(Vector patterns, int kernelType, Mode mode)
/*  26:    */   {
/*  27: 90 */     this._patterns = patterns;
/*  28: 91 */     this._kernelType = kernelType;
/*  29: 92 */     this._mode = mode;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String toString()
/*  33:    */   {
/*  34:101 */     int count = this._patterns.size();
/*  35:102 */     StringBuffer result = new StringBuffer();
/*  36:104 */     for (int i = 0; i < count; i++)
/*  37:    */     {
/*  38:105 */       LocationPathPattern pattern = (LocationPathPattern)this._patterns.elementAt(i);
/*  39:108 */       if (i == 0) {
/*  40:109 */         result.append("Testseq for kernel " + this._kernelType).append('\n');
/*  41:    */       }
/*  42:112 */       result.append("   pattern " + i + ": ").append(pattern.toString()).append('\n');
/*  43:    */     }
/*  44:116 */     return result.toString();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public InstructionList getInstructionList()
/*  48:    */   {
/*  49:123 */     return this._instructionList;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public double getPriority()
/*  53:    */   {
/*  54:132 */     Template template = this._patterns.size() == 0 ? this._default : ((Pattern)this._patterns.elementAt(0)).getTemplate();
/*  55:    */     
/*  56:134 */     return template.getPriority();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getPosition()
/*  60:    */   {
/*  61:142 */     Template template = this._patterns.size() == 0 ? this._default : ((Pattern)this._patterns.elementAt(0)).getTemplate();
/*  62:    */     
/*  63:144 */     return template.getPosition();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void reduce()
/*  67:    */   {
/*  68:153 */     Vector newPatterns = new Vector();
/*  69:    */     
/*  70:155 */     int count = this._patterns.size();
/*  71:156 */     for (int i = 0; i < count; i++)
/*  72:    */     {
/*  73:157 */       LocationPathPattern pattern = (LocationPathPattern)this._patterns.elementAt(i);
/*  74:    */       
/*  75:    */ 
/*  76:    */ 
/*  77:161 */       pattern.reduceKernelPattern();
/*  78:164 */       if (pattern.isWildcard())
/*  79:    */       {
/*  80:165 */         this._default = pattern.getTemplate();
/*  81:166 */         break;
/*  82:    */       }
/*  83:169 */       newPatterns.addElement(pattern);
/*  84:    */     }
/*  85:172 */     this._patterns = newPatterns;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void findTemplates(Dictionary templates)
/*  89:    */   {
/*  90:181 */     if (this._default != null) {
/*  91:182 */       templates.put(this._default, this);
/*  92:    */     }
/*  93:184 */     for (int i = 0; i < this._patterns.size(); i++)
/*  94:    */     {
/*  95:185 */       LocationPathPattern pattern = (LocationPathPattern)this._patterns.elementAt(i);
/*  96:    */       
/*  97:187 */       templates.put(pattern.getTemplate(), this);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private InstructionHandle getTemplateHandle(Template template)
/* 102:    */   {
/* 103:198 */     return this._mode.getTemplateInstructionHandle(template);
/* 104:    */   }
/* 105:    */   
/* 106:    */   private LocationPathPattern getPattern(int n)
/* 107:    */   {
/* 108:205 */     return (LocationPathPattern)this._patterns.elementAt(n);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public InstructionHandle compile(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle continuation)
/* 112:    */   {
/* 113:219 */     if (this._start != null) {
/* 114:220 */       return this._start;
/* 115:    */     }
/* 116:224 */     int count = this._patterns.size();
/* 117:225 */     if (count == 0) {
/* 118:226 */       return this._start = getTemplateHandle(this._default);
/* 119:    */     }
/* 120:230 */     InstructionHandle fail = this._default == null ? continuation : getTemplateHandle(this._default);
/* 121:234 */     for (int n = count - 1; n >= 0; n--)
/* 122:    */     {
/* 123:235 */       LocationPathPattern pattern = getPattern(n);
/* 124:236 */       Template template = pattern.getTemplate();
/* 125:237 */       InstructionList il = new InstructionList();
/* 126:    */       
/* 127:    */ 
/* 128:240 */       il.append(methodGen.loadCurrentNode());
/* 129:    */       
/* 130:    */ 
/* 131:243 */       InstructionList ilist = methodGen.getInstructionList(pattern);
/* 132:244 */       if (ilist == null)
/* 133:    */       {
/* 134:245 */         ilist = pattern.compile(classGen, methodGen);
/* 135:246 */         methodGen.addInstructionList(pattern, ilist);
/* 136:    */       }
/* 137:250 */       InstructionList copyOfilist = ilist.copy();
/* 138:    */       
/* 139:252 */       FlowList trueList = pattern.getTrueList();
/* 140:253 */       if (trueList != null) {
/* 141:254 */         trueList = trueList.copyAndRedirect(ilist, copyOfilist);
/* 142:    */       }
/* 143:256 */       FlowList falseList = pattern.getFalseList();
/* 144:257 */       if (falseList != null) {
/* 145:258 */         falseList = falseList.copyAndRedirect(ilist, copyOfilist);
/* 146:    */       }
/* 147:261 */       il.append(copyOfilist);
/* 148:    */       
/* 149:    */ 
/* 150:264 */       InstructionHandle gtmpl = getTemplateHandle(template);
/* 151:265 */       InstructionHandle success = il.append(new GOTO_W(gtmpl));
/* 152:267 */       if (trueList != null) {
/* 153:268 */         trueList.backPatch(success);
/* 154:    */       }
/* 155:270 */       if (falseList != null) {
/* 156:271 */         falseList.backPatch(fail);
/* 157:    */       }
/* 158:275 */       fail = il.getStart();
/* 159:278 */       if (this._instructionList != null) {
/* 160:279 */         il.append(this._instructionList);
/* 161:    */       }
/* 162:283 */       this._instructionList = il;
/* 163:    */     }
/* 164:285 */     return this._start = fail;
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.TestSeq
 * JD-Core Version:    0.7.0.1
 */