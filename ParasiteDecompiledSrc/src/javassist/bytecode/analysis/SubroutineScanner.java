/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ import javassist.bytecode.BadBytecode;
/*   8:    */ import javassist.bytecode.CodeAttribute;
/*   9:    */ import javassist.bytecode.CodeIterator;
/*  10:    */ import javassist.bytecode.ExceptionTable;
/*  11:    */ import javassist.bytecode.MethodInfo;
/*  12:    */ import javassist.bytecode.Opcode;
/*  13:    */ 
/*  14:    */ public class SubroutineScanner
/*  15:    */   implements Opcode
/*  16:    */ {
/*  17:    */   private Subroutine[] subroutines;
/*  18: 37 */   Map subTable = new HashMap();
/*  19: 38 */   Set done = new HashSet();
/*  20:    */   
/*  21:    */   public Subroutine[] scan(MethodInfo method)
/*  22:    */     throws BadBytecode
/*  23:    */   {
/*  24: 42 */     CodeAttribute code = method.getCodeAttribute();
/*  25: 43 */     CodeIterator iter = code.iterator();
/*  26:    */     
/*  27: 45 */     this.subroutines = new Subroutine[code.getCodeLength()];
/*  28: 46 */     this.subTable.clear();
/*  29: 47 */     this.done.clear();
/*  30:    */     
/*  31: 49 */     scan(0, iter, null);
/*  32:    */     
/*  33: 51 */     ExceptionTable exceptions = code.getExceptionTable();
/*  34: 52 */     for (int i = 0; i < exceptions.size(); i++)
/*  35:    */     {
/*  36: 53 */       int handler = exceptions.handlerPc(i);
/*  37:    */       
/*  38:    */ 
/*  39: 56 */       scan(handler, iter, this.subroutines[exceptions.startPc(i)]);
/*  40:    */     }
/*  41: 59 */     return this.subroutines;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void scan(int pos, CodeIterator iter, Subroutine sub)
/*  45:    */     throws BadBytecode
/*  46:    */   {
/*  47: 64 */     if (this.done.contains(new Integer(pos))) {
/*  48: 65 */       return;
/*  49:    */     }
/*  50: 67 */     this.done.add(new Integer(pos));
/*  51:    */     
/*  52: 69 */     int old = iter.lookAhead();
/*  53: 70 */     iter.move(pos);
/*  54:    */     boolean next;
/*  55:    */     do
/*  56:    */     {
/*  57: 74 */       pos = iter.next();
/*  58: 75 */       next = (scanOp(pos, iter, sub)) && (iter.hasNext());
/*  59: 76 */     } while (next);
/*  60: 78 */     iter.move(old);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private boolean scanOp(int pos, CodeIterator iter, Subroutine sub)
/*  64:    */     throws BadBytecode
/*  65:    */   {
/*  66: 82 */     this.subroutines[pos] = sub;
/*  67:    */     
/*  68: 84 */     int opcode = iter.byteAt(pos);
/*  69: 86 */     if (opcode == 170)
/*  70:    */     {
/*  71: 87 */       scanTableSwitch(pos, iter, sub);
/*  72:    */       
/*  73: 89 */       return false;
/*  74:    */     }
/*  75: 92 */     if (opcode == 171)
/*  76:    */     {
/*  77: 93 */       scanLookupSwitch(pos, iter, sub);
/*  78:    */       
/*  79: 95 */       return false;
/*  80:    */     }
/*  81: 99 */     if ((Util.isReturn(opcode)) || (opcode == 169) || (opcode == 191)) {
/*  82:100 */       return false;
/*  83:    */     }
/*  84:102 */     if (Util.isJumpInstruction(opcode))
/*  85:    */     {
/*  86:103 */       int target = Util.getJumpTarget(pos, iter);
/*  87:104 */       if ((opcode == 168) || (opcode == 201))
/*  88:    */       {
/*  89:105 */         Subroutine s = (Subroutine)this.subTable.get(new Integer(target));
/*  90:106 */         if (s == null)
/*  91:    */         {
/*  92:107 */           s = new Subroutine(target, pos);
/*  93:108 */           this.subTable.put(new Integer(target), s);
/*  94:109 */           scan(target, iter, s);
/*  95:    */         }
/*  96:    */         else
/*  97:    */         {
/*  98:111 */           s.addCaller(pos);
/*  99:    */         }
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:114 */         scan(target, iter, sub);
/* 104:117 */         if (Util.isGoto(opcode)) {
/* 105:118 */           return false;
/* 106:    */         }
/* 107:    */       }
/* 108:    */     }
/* 109:122 */     return true;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void scanLookupSwitch(int pos, CodeIterator iter, Subroutine sub)
/* 113:    */     throws BadBytecode
/* 114:    */   {
/* 115:126 */     int index = (pos & 0xFFFFFFFC) + 4;
/* 116:    */     
/* 117:128 */     scan(pos + iter.s32bitAt(index), iter, sub);
/* 118:129 */     index += 4;int npairs = iter.s32bitAt(index);
/* 119:130 */     index += 4;int end = npairs * 8 + index;
/* 120:133 */     for (index += 4; index < end; index += 8)
/* 121:    */     {
/* 122:134 */       int target = iter.s32bitAt(index) + pos;
/* 123:135 */       scan(target, iter, sub);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void scanTableSwitch(int pos, CodeIterator iter, Subroutine sub)
/* 128:    */     throws BadBytecode
/* 129:    */   {
/* 130:141 */     int index = (pos & 0xFFFFFFFC) + 4;
/* 131:    */     
/* 132:143 */     scan(pos + iter.s32bitAt(index), iter, sub);
/* 133:144 */     index += 4;int low = iter.s32bitAt(index);
/* 134:145 */     index += 4;int high = iter.s32bitAt(index);
/* 135:146 */     index += 4;int end = (high - low + 1) * 4 + index;
/* 136:149 */     for (; index < end; index += 4)
/* 137:    */     {
/* 138:150 */       int target = iter.s32bitAt(index) + pos;
/* 139:151 */       scan(target, iter, sub);
/* 140:    */     }
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.SubroutineScanner
 * JD-Core Version:    0.7.0.1
 */