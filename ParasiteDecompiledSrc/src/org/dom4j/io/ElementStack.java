/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.ElementHandler;
/*   5:    */ import org.dom4j.ElementPath;
/*   6:    */ 
/*   7:    */ class ElementStack
/*   8:    */   implements ElementPath
/*   9:    */ {
/*  10:    */   protected Element[] stack;
/*  11: 30 */   protected int lastElementIndex = -1;
/*  12: 32 */   private DispatchHandler handler = null;
/*  13:    */   
/*  14:    */   public ElementStack()
/*  15:    */   {
/*  16: 35 */     this(50);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ElementStack(int defaultCapacity)
/*  20:    */   {
/*  21: 39 */     this.stack = new Element[defaultCapacity];
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setDispatchHandler(DispatchHandler dispatchHandler)
/*  25:    */   {
/*  26: 43 */     this.handler = dispatchHandler;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public DispatchHandler getDispatchHandler()
/*  30:    */   {
/*  31: 47 */     return this.handler;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void clear()
/*  35:    */   {
/*  36: 55 */     this.lastElementIndex = -1;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Element peekElement()
/*  40:    */   {
/*  41: 65 */     if (this.lastElementIndex < 0) {
/*  42: 66 */       return null;
/*  43:    */     }
/*  44: 69 */     return this.stack[this.lastElementIndex];
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Element popElement()
/*  48:    */   {
/*  49: 78 */     if (this.lastElementIndex < 0) {
/*  50: 79 */       return null;
/*  51:    */     }
/*  52: 82 */     return this.stack[(this.lastElementIndex--)];
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void pushElement(Element element)
/*  56:    */   {
/*  57: 92 */     int length = this.stack.length;
/*  58: 94 */     if (++this.lastElementIndex >= length) {
/*  59: 95 */       reallocate(length * 2);
/*  60:    */     }
/*  61: 98 */     this.stack[this.lastElementIndex] = element;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void reallocate(int size)
/*  65:    */   {
/*  66:108 */     Element[] oldStack = this.stack;
/*  67:109 */     this.stack = new Element[size];
/*  68:110 */     System.arraycopy(oldStack, 0, this.stack, 0, oldStack.length);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int size()
/*  72:    */   {
/*  73:116 */     return this.lastElementIndex + 1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Element getElement(int depth)
/*  77:    */   {
/*  78:    */     Element element;
/*  79:    */     try
/*  80:    */     {
/*  81:123 */       element = this.stack[depth];
/*  82:    */     }
/*  83:    */     catch (ArrayIndexOutOfBoundsException e)
/*  84:    */     {
/*  85:    */       Element element;
/*  86:125 */       element = null;
/*  87:    */     }
/*  88:128 */     return element;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getPath()
/*  92:    */   {
/*  93:132 */     if (this.handler == null) {
/*  94:133 */       setDispatchHandler(new DispatchHandler());
/*  95:    */     }
/*  96:136 */     return this.handler.getPath();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Element getCurrent()
/* 100:    */   {
/* 101:140 */     return peekElement();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void addHandler(String path, ElementHandler elementHandler)
/* 105:    */   {
/* 106:144 */     this.handler.addHandler(getHandlerPath(path), elementHandler);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void removeHandler(String path)
/* 110:    */   {
/* 111:148 */     this.handler.removeHandler(getHandlerPath(path));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean containsHandler(String path)
/* 115:    */   {
/* 116:161 */     return this.handler.containsHandler(path);
/* 117:    */   }
/* 118:    */   
/* 119:    */   private String getHandlerPath(String path)
/* 120:    */   {
/* 121:167 */     if (this.handler == null) {
/* 122:168 */       setDispatchHandler(new DispatchHandler());
/* 123:    */     }
/* 124:    */     String handlerPath;
/* 125:    */     String handlerPath;
/* 126:171 */     if (path.startsWith("/"))
/* 127:    */     {
/* 128:172 */       handlerPath = path;
/* 129:    */     }
/* 130:    */     else
/* 131:    */     {
/* 132:    */       String handlerPath;
/* 133:173 */       if (getPath().equals("/")) {
/* 134:174 */         handlerPath = getPath() + path;
/* 135:    */       } else {
/* 136:176 */         handlerPath = getPath() + "/" + path;
/* 137:    */       }
/* 138:    */     }
/* 139:179 */     return handlerPath;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.ElementStack
 * JD-Core Version:    0.7.0.1
 */