/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.LocalVariableGen;
/*  4:   */ import org.apache.bcel.generic.Type;
/*  5:   */ 
/*  6:   */ final class SlotAllocator
/*  7:   */ {
/*  8:   */   private int _firstAvailableSlot;
/*  9:33 */   private int _size = 8;
/* 10:34 */   private int _free = 0;
/* 11:35 */   private int[] _slotsTaken = new int[this._size];
/* 12:   */   
/* 13:   */   public void initialize(LocalVariableGen[] vars)
/* 14:   */   {
/* 15:38 */     int length = vars.length;
/* 16:39 */     int slot = 0;
/* 17:41 */     for (int i = 0; i < length; i++)
/* 18:   */     {
/* 19:42 */       int size = vars[i].getType().getSize();
/* 20:43 */       int index = vars[i].getIndex();
/* 21:44 */       slot = Math.max(slot, index + size);
/* 22:   */     }
/* 23:46 */     this._firstAvailableSlot = slot;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int allocateSlot(Type type)
/* 27:   */   {
/* 28:50 */     int size = type.getSize();
/* 29:51 */     int limit = this._free;
/* 30:52 */     int slot = this._firstAvailableSlot;int where = 0;
/* 31:54 */     if (this._free + size > this._size)
/* 32:   */     {
/* 33:55 */       int[] array = new int[this._size *= 2];
/* 34:56 */       for (int j = 0; j < limit; j++) {
/* 35:57 */         array[j] = this._slotsTaken[j];
/* 36:   */       }
/* 37:58 */       this._slotsTaken = array;
/* 38:   */     }
/* 39:61 */     while (where < limit)
/* 40:   */     {
/* 41:62 */       if (slot + size <= this._slotsTaken[where])
/* 42:   */       {
/* 43:64 */         for (int j = limit - 1; j >= where; j--) {
/* 44:65 */           this._slotsTaken[(j + size)] = this._slotsTaken[j];
/* 45:   */         }
/* 46:66 */         break;
/* 47:   */       }
/* 48:69 */       slot = this._slotsTaken[(where++)] + 1;
/* 49:   */     }
/* 50:73 */     for (int j = 0; j < size; j++) {
/* 51:74 */       this._slotsTaken[(where + j)] = (slot + j);
/* 52:   */     }
/* 53:76 */     this._free += size;
/* 54:77 */     return slot;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void releaseSlot(LocalVariableGen lvg)
/* 58:   */   {
/* 59:81 */     int size = lvg.getType().getSize();
/* 60:82 */     int slot = lvg.getIndex();
/* 61:83 */     int limit = this._free;
/* 62:85 */     for (int i = 0; i < limit; i++) {
/* 63:86 */       if (this._slotsTaken[i] == slot)
/* 64:   */       {
/* 65:87 */         int j = i + size;
/* 66:88 */         while (j < limit) {
/* 67:89 */           this._slotsTaken[(i++)] = this._slotsTaken[(j++)];
/* 68:   */         }
/* 69:91 */         this._free -= size;
/* 70:92 */         return;
/* 71:   */       }
/* 72:   */     }
/* 73:95 */     String state = "Variable slot allocation error(size=" + size + ", slot=" + slot + ", limit=" + limit + ")";
/* 74:   */     
/* 75:97 */     ErrorMsg err = new ErrorMsg("INTERNAL_ERR", state);
/* 76:98 */     throw new Error(err.toString());
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.SlotAllocator
 * JD-Core Version:    0.7.0.1
 */