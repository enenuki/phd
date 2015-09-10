/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ final class SuperBlock
/*    4:     */ {
/*    5:     */   private int index;
/*    6:     */   private int start;
/*    7:     */   private int end;
/*    8:     */   private int[] locals;
/*    9:     */   private int[] stack;
/*   10:     */   private boolean isInitialized;
/*   11:     */   private boolean isInQueue;
/*   12:     */   
/*   13:     */   SuperBlock(int index, int start, int end, int[] initialLocals)
/*   14:     */   {
/*   15:4796 */     this.index = index;
/*   16:4797 */     this.start = start;
/*   17:4798 */     this.end = end;
/*   18:4799 */     this.locals = new int[initialLocals.length];
/*   19:4800 */     System.arraycopy(initialLocals, 0, this.locals, 0, initialLocals.length);
/*   20:4801 */     this.stack = new int[0];
/*   21:4802 */     this.isInitialized = false;
/*   22:4803 */     this.isInQueue = false;
/*   23:     */   }
/*   24:     */   
/*   25:     */   int getIndex()
/*   26:     */   {
/*   27:4807 */     return this.index;
/*   28:     */   }
/*   29:     */   
/*   30:     */   int[] getLocals()
/*   31:     */   {
/*   32:4811 */     int[] copy = new int[this.locals.length];
/*   33:4812 */     System.arraycopy(this.locals, 0, copy, 0, this.locals.length);
/*   34:4813 */     return copy;
/*   35:     */   }
/*   36:     */   
/*   37:     */   int[] getTrimmedLocals()
/*   38:     */   {
/*   39:4826 */     int last = this.locals.length - 1;
/*   40:4828 */     while ((last >= 0) && (this.locals[last] == 0) && (!TypeInfo.isTwoWords(this.locals[(last - 1)]))) {
/*   41:4830 */       last--;
/*   42:     */     }
/*   43:4832 */     last++;
/*   44:     */     
/*   45:4834 */     int size = last;
/*   46:4835 */     for (int i = 0; i < last; i++) {
/*   47:4836 */       if (TypeInfo.isTwoWords(this.locals[i])) {
/*   48:4837 */         size--;
/*   49:     */       }
/*   50:     */     }
/*   51:4840 */     int[] copy = new int[size];
/*   52:4841 */     int i = 0;
/*   53:4841 */     for (int j = 0; i < size; j++)
/*   54:     */     {
/*   55:4842 */       copy[i] = this.locals[j];
/*   56:4843 */       if (TypeInfo.isTwoWords(this.locals[j])) {
/*   57:4844 */         j++;
/*   58:     */       }
/*   59:4841 */       i++;
/*   60:     */     }
/*   61:4847 */     return copy;
/*   62:     */   }
/*   63:     */   
/*   64:     */   int[] getStack()
/*   65:     */   {
/*   66:4851 */     int[] copy = new int[this.stack.length];
/*   67:4852 */     System.arraycopy(this.stack, 0, copy, 0, this.stack.length);
/*   68:4853 */     return copy;
/*   69:     */   }
/*   70:     */   
/*   71:     */   boolean merge(int[] locals, int localsTop, int[] stack, int stackTop, ConstantPool pool)
/*   72:     */   {
/*   73:4858 */     if (!this.isInitialized)
/*   74:     */     {
/*   75:4859 */       System.arraycopy(locals, 0, this.locals, 0, localsTop);
/*   76:4860 */       this.stack = new int[stackTop];
/*   77:4861 */       System.arraycopy(stack, 0, this.stack, 0, stackTop);
/*   78:4862 */       this.isInitialized = true;
/*   79:4863 */       return true;
/*   80:     */     }
/*   81:4864 */     if ((this.locals.length == localsTop) && (this.stack.length == stackTop))
/*   82:     */     {
/*   83:4866 */       boolean localsChanged = mergeState(this.locals, locals, localsTop, pool);
/*   84:     */       
/*   85:4868 */       boolean stackChanged = mergeState(this.stack, stack, stackTop, pool);
/*   86:     */       
/*   87:4870 */       return (localsChanged) || (stackChanged);
/*   88:     */     }
/*   89:4879 */     throw new IllegalArgumentException("bad merge attempt");
/*   90:     */   }
/*   91:     */   
/*   92:     */   private boolean mergeState(int[] current, int[] incoming, int size, ConstantPool pool)
/*   93:     */   {
/*   94:4893 */     boolean changed = false;
/*   95:4894 */     for (int i = 0; i < size; i++)
/*   96:     */     {
/*   97:4895 */       int currentType = current[i];
/*   98:     */       
/*   99:4897 */       current[i] = TypeInfo.merge(current[i], incoming[i], pool);
/*  100:4898 */       if (currentType != current[i]) {
/*  101:4899 */         changed = true;
/*  102:     */       }
/*  103:     */     }
/*  104:4902 */     return changed;
/*  105:     */   }
/*  106:     */   
/*  107:     */   int getStart()
/*  108:     */   {
/*  109:4906 */     return this.start;
/*  110:     */   }
/*  111:     */   
/*  112:     */   int getEnd()
/*  113:     */   {
/*  114:4910 */     return this.end;
/*  115:     */   }
/*  116:     */   
/*  117:     */   public String toString()
/*  118:     */   {
/*  119:4915 */     return "sb " + this.index;
/*  120:     */   }
/*  121:     */   
/*  122:     */   boolean isInitialized()
/*  123:     */   {
/*  124:4919 */     return this.isInitialized;
/*  125:     */   }
/*  126:     */   
/*  127:     */   void setInitialized(boolean b)
/*  128:     */   {
/*  129:4923 */     this.isInitialized = b;
/*  130:     */   }
/*  131:     */   
/*  132:     */   boolean isInQueue()
/*  133:     */   {
/*  134:4927 */     return this.isInQueue;
/*  135:     */   }
/*  136:     */   
/*  137:     */   void setInQueue(boolean b)
/*  138:     */   {
/*  139:4931 */     this.isInQueue = b;
/*  140:     */   }
/*  141:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.SuperBlock
 * JD-Core Version:    0.7.0.1
 */