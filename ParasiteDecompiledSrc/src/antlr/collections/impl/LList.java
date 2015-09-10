/*   1:    */ package antlr.collections.impl;
/*   2:    */ 
/*   3:    */ import antlr.collections.List;
/*   4:    */ import antlr.collections.Stack;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ 
/*   8:    */ public class LList
/*   9:    */   implements List, Stack
/*  10:    */ {
/*  11: 22 */   protected LLCell tail = null;
/*  12: 22 */   protected LLCell head = null;
/*  13: 23 */   protected int length = 0;
/*  14:    */   
/*  15:    */   public void add(Object paramObject)
/*  16:    */   {
/*  17: 30 */     append(paramObject);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void append(Object paramObject)
/*  21:    */   {
/*  22: 37 */     LLCell localLLCell = new LLCell(paramObject);
/*  23: 38 */     if (this.length == 0)
/*  24:    */     {
/*  25: 39 */       this.head = (this.tail = localLLCell);
/*  26: 40 */       this.length = 1;
/*  27:    */     }
/*  28:    */     else
/*  29:    */     {
/*  30: 43 */       this.tail.next = localLLCell;
/*  31: 44 */       this.tail = localLLCell;
/*  32: 45 */       this.length += 1;
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected Object deleteHead()
/*  37:    */     throws NoSuchElementException
/*  38:    */   {
/*  39: 54 */     if (this.head == null) {
/*  40: 54 */       throw new NoSuchElementException();
/*  41:    */     }
/*  42: 55 */     Object localObject = this.head.data;
/*  43: 56 */     this.head = this.head.next;
/*  44: 57 */     this.length -= 1;
/*  45: 58 */     return localObject;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object elementAt(int paramInt)
/*  49:    */     throws NoSuchElementException
/*  50:    */   {
/*  51: 67 */     int i = 0;
/*  52: 68 */     for (LLCell localLLCell = this.head; localLLCell != null; localLLCell = localLLCell.next)
/*  53:    */     {
/*  54: 69 */       if (paramInt == i) {
/*  55: 69 */         return localLLCell.data;
/*  56:    */       }
/*  57: 70 */       i++;
/*  58:    */     }
/*  59: 72 */     throw new NoSuchElementException();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Enumeration elements()
/*  63:    */   {
/*  64: 77 */     return new LLEnumeration(this);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int height()
/*  68:    */   {
/*  69: 82 */     return this.length;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean includes(Object paramObject)
/*  73:    */   {
/*  74: 90 */     for (LLCell localLLCell = this.head; localLLCell != null; localLLCell = localLLCell.next) {
/*  75: 91 */       if (localLLCell.data.equals(paramObject)) {
/*  76: 91 */         return true;
/*  77:    */       }
/*  78:    */     }
/*  79: 93 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void insertHead(Object paramObject)
/*  83:    */   {
/*  84:101 */     LLCell localLLCell = this.head;
/*  85:102 */     this.head = new LLCell(paramObject);
/*  86:103 */     this.head.next = localLLCell;
/*  87:104 */     this.length += 1;
/*  88:105 */     if (this.tail == null) {
/*  89:105 */       this.tail = this.head;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int length()
/*  94:    */   {
/*  95:110 */     return this.length;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object pop()
/*  99:    */     throws NoSuchElementException
/* 100:    */   {
/* 101:118 */     Object localObject = deleteHead();
/* 102:119 */     return localObject;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void push(Object paramObject)
/* 106:    */   {
/* 107:127 */     insertHead(paramObject);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object top()
/* 111:    */     throws NoSuchElementException
/* 112:    */   {
/* 113:131 */     if (this.head == null) {
/* 114:131 */       throw new NoSuchElementException();
/* 115:    */     }
/* 116:132 */     return this.head.data;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.LList
 * JD-Core Version:    0.7.0.1
 */