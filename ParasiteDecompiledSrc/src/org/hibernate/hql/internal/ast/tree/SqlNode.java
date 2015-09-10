/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.Type;
/*  4:   */ 
/*  5:   */ public class SqlNode
/*  6:   */   extends Node
/*  7:   */ {
/*  8:   */   private String originalText;
/*  9:   */   private Type dataType;
/* 10:   */   
/* 11:   */   public void setText(String s)
/* 12:   */   {
/* 13:45 */     super.setText(s);
/* 14:46 */     if ((s != null) && (s.length() > 0) && (this.originalText == null)) {
/* 15:47 */       this.originalText = s;
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getOriginalText()
/* 20:   */   {
/* 21:52 */     return this.originalText;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Type getDataType()
/* 25:   */   {
/* 26:56 */     return this.dataType;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setDataType(Type dataType)
/* 30:   */   {
/* 31:60 */     this.dataType = dataType;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SqlNode
 * JD-Core Version:    0.7.0.1
 */