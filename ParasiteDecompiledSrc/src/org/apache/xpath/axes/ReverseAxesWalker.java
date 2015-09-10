/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTM;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xpath.XPathContext;
/*   6:    */ 
/*   7:    */ public class ReverseAxesWalker
/*   8:    */   extends AxesWalker
/*   9:    */ {
/*  10:    */   static final long serialVersionUID = 2847007647832768941L;
/*  11:    */   protected DTMAxisIterator m_iterator;
/*  12:    */   
/*  13:    */   ReverseAxesWalker(LocPathIterator locPathIterator, int axis)
/*  14:    */   {
/*  15: 42 */     super(locPathIterator, axis);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void setRoot(int root)
/*  19:    */   {
/*  20: 53 */     super.setRoot(root);
/*  21: 54 */     this.m_iterator = getDTM(root).getAxisIterator(this.m_axis);
/*  22: 55 */     this.m_iterator.setStartNode(root);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void detach()
/*  26:    */   {
/*  27: 65 */     this.m_iterator = null;
/*  28: 66 */     super.detach();
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected int getNextNode()
/*  32:    */   {
/*  33: 76 */     if (this.m_foundLast) {
/*  34: 77 */       return -1;
/*  35:    */     }
/*  36: 79 */     int next = this.m_iterator.next();
/*  37: 81 */     if (this.m_isFresh) {
/*  38: 82 */       this.m_isFresh = false;
/*  39:    */     }
/*  40: 84 */     if (-1 == next) {
/*  41: 85 */       this.m_foundLast = true;
/*  42:    */     }
/*  43: 87 */     return next;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isReverseAxes()
/*  47:    */   {
/*  48: 98 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected int getProximityPosition(int predicateIndex)
/*  52:    */   {
/*  53:127 */     if (predicateIndex < 0) {
/*  54:128 */       return -1;
/*  55:    */     }
/*  56:130 */     int count = this.m_proximityPositions[predicateIndex];
/*  57:132 */     if (count <= 0)
/*  58:    */     {
/*  59:134 */       AxesWalker savedWalker = wi().getLastUsedWalker();
/*  60:    */       try
/*  61:    */       {
/*  62:138 */         ReverseAxesWalker clone = (ReverseAxesWalker)clone();
/*  63:    */         
/*  64:140 */         clone.setRoot(getRoot());
/*  65:    */         
/*  66:142 */         clone.setPredicateCount(predicateIndex);
/*  67:    */         
/*  68:144 */         clone.setPrevWalker(null);
/*  69:145 */         clone.setNextWalker(null);
/*  70:146 */         wi().setLastUsedWalker(clone);
/*  71:    */         
/*  72:    */ 
/*  73:149 */         count++;
/*  74:    */         int next;
/*  75:152 */         while (-1 != (next = clone.nextNode())) {
/*  76:154 */           count++;
/*  77:    */         }
/*  78:157 */         this.m_proximityPositions[predicateIndex] = count;
/*  79:    */       }
/*  80:    */       catch (CloneNotSupportedException cnse) {}finally
/*  81:    */       {
/*  82:166 */         wi().setLastUsedWalker(savedWalker);
/*  83:    */       }
/*  84:    */     }
/*  85:170 */     return count;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void countProximityPosition(int i)
/*  89:    */   {
/*  90:180 */     if (i < this.m_proximityPositions.length) {
/*  91:181 */       this.m_proximityPositions[i] -= 1;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getLastPos(XPathContext xctxt)
/*  96:    */   {
/*  97:196 */     int count = 0;
/*  98:197 */     AxesWalker savedWalker = wi().getLastUsedWalker();
/*  99:    */     try
/* 100:    */     {
/* 101:201 */       ReverseAxesWalker clone = (ReverseAxesWalker)clone();
/* 102:    */       
/* 103:203 */       clone.setRoot(getRoot());
/* 104:    */       
/* 105:205 */       clone.setPredicateCount(this.m_predicateIndex);
/* 106:    */       
/* 107:207 */       clone.setPrevWalker(null);
/* 108:208 */       clone.setNextWalker(null);
/* 109:209 */       wi().setLastUsedWalker(clone);
/* 110:    */       int next;
/* 111:215 */       while (-1 != (next = clone.nextNode())) {
/* 112:217 */         count++;
/* 113:    */       }
/* 114:    */     }
/* 115:    */     catch (CloneNotSupportedException cnse) {}finally
/* 116:    */     {
/* 117:227 */       wi().setLastUsedWalker(savedWalker);
/* 118:    */     }
/* 119:230 */     return count;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isDocOrdered()
/* 123:    */   {
/* 124:242 */     return false;
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.ReverseAxesWalker
 * JD-Core Version:    0.7.0.1
 */