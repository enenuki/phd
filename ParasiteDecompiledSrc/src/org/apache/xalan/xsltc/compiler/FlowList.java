/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.BranchHandle;
/*   6:    */ import org.apache.bcel.generic.InstructionHandle;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ 
/*   9:    */ public final class FlowList
/*  10:    */ {
/*  11:    */   private Vector _elements;
/*  12:    */   
/*  13:    */   public FlowList()
/*  14:    */   {
/*  15: 39 */     this._elements = null;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public FlowList(InstructionHandle bh)
/*  19:    */   {
/*  20: 43 */     this._elements = new Vector();
/*  21: 44 */     this._elements.addElement(bh);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public FlowList(FlowList list)
/*  25:    */   {
/*  26: 48 */     this._elements = list._elements;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public FlowList add(InstructionHandle bh)
/*  30:    */   {
/*  31: 52 */     if (this._elements == null) {
/*  32: 53 */       this._elements = new Vector();
/*  33:    */     }
/*  34: 55 */     this._elements.addElement(bh);
/*  35: 56 */     return this;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public FlowList append(FlowList right)
/*  39:    */   {
/*  40: 60 */     if (this._elements == null)
/*  41:    */     {
/*  42: 61 */       this._elements = right._elements;
/*  43:    */     }
/*  44:    */     else
/*  45:    */     {
/*  46: 64 */       Vector temp = right._elements;
/*  47: 65 */       if (temp != null)
/*  48:    */       {
/*  49: 66 */         int n = temp.size();
/*  50: 67 */         for (int i = 0; i < n; i++) {
/*  51: 68 */           this._elements.addElement(temp.elementAt(i));
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55: 72 */     return this;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void backPatch(InstructionHandle target)
/*  59:    */   {
/*  60: 79 */     if (this._elements != null)
/*  61:    */     {
/*  62: 80 */       int n = this._elements.size();
/*  63: 81 */       for (int i = 0; i < n; i++)
/*  64:    */       {
/*  65: 82 */         BranchHandle bh = (BranchHandle)this._elements.elementAt(i);
/*  66: 83 */         bh.setTarget(target);
/*  67:    */       }
/*  68: 85 */       this._elements.clear();
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public FlowList copyAndRedirect(InstructionList oldList, InstructionList newList)
/*  73:    */   {
/*  74: 96 */     FlowList result = new FlowList();
/*  75: 97 */     if (this._elements == null) {
/*  76: 98 */       return result;
/*  77:    */     }
/*  78:101 */     int n = this._elements.size();
/*  79:102 */     Iterator oldIter = oldList.iterator();
/*  80:103 */     Iterator newIter = newList.iterator();
/*  81:    */     int i;
/*  82:105 */     for (; oldIter.hasNext(); i < n)
/*  83:    */     {
/*  84:106 */       InstructionHandle oldIh = (InstructionHandle)oldIter.next();
/*  85:107 */       InstructionHandle newIh = (InstructionHandle)newIter.next();
/*  86:    */       
/*  87:109 */       i = 0; continue;
/*  88:110 */       if (this._elements.elementAt(i) == oldIh) {
/*  89:111 */         result.add(newIh);
/*  90:    */       }
/*  91:109 */       i++;
/*  92:    */     }
/*  93:115 */     return result;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FlowList
 * JD-Core Version:    0.7.0.1
 */