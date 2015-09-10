/*  1:   */ package org.apache.bcel.verifier.structurals;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.Constants;
/*  4:   */ import org.apache.bcel.generic.ObjectType;
/*  5:   */ import org.apache.bcel.generic.ReferenceType;
/*  6:   */ 
/*  7:   */ public class UninitializedObjectType
/*  8:   */   extends ReferenceType
/*  9:   */   implements Constants
/* 10:   */ {
/* 11:   */   private ObjectType initialized;
/* 12:   */   
/* 13:   */   public UninitializedObjectType(ObjectType t)
/* 14:   */   {
/* 15:75 */     super((byte)15, "<UNINITIALIZED OBJECT OF TYPE '" + t.getClassName() + "'>");
/* 16:76 */     this.initialized = t;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ObjectType getInitialized()
/* 20:   */   {
/* 21:84 */     return this.initialized;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean equals(Object o)
/* 25:   */   {
/* 26:94 */     if (!(o instanceof UninitializedObjectType)) {
/* 27:94 */       return false;
/* 28:   */     }
/* 29:95 */     return this.initialized.equals(((UninitializedObjectType)o).initialized);
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.UninitializedObjectType
 * JD-Core Version:    0.7.0.1
 */