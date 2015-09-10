/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.mapping.JoinedSubclass;
/*  6:   */ import org.hibernate.mapping.RootClass;
/*  7:   */ 
/*  8:   */ public class CreateKeySecondPass
/*  9:   */   implements SecondPass
/* 10:   */ {
/* 11:   */   private RootClass rootClass;
/* 12:   */   private JoinedSubclass joinedSubClass;
/* 13:   */   
/* 14:   */   public CreateKeySecondPass(RootClass rootClass)
/* 15:   */   {
/* 16:39 */     this.rootClass = rootClass;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public CreateKeySecondPass(JoinedSubclass joinedSubClass)
/* 20:   */   {
/* 21:43 */     this.joinedSubClass = joinedSubClass;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void doSecondPass(Map persistentClasses)
/* 25:   */     throws MappingException
/* 26:   */   {
/* 27:47 */     if (this.rootClass != null)
/* 28:   */     {
/* 29:48 */       this.rootClass.createPrimaryKey();
/* 30:   */     }
/* 31:50 */     else if (this.joinedSubClass != null)
/* 32:   */     {
/* 33:51 */       this.joinedSubClass.createPrimaryKey();
/* 34:52 */       this.joinedSubClass.createForeignKey();
/* 35:   */     }
/* 36:   */     else
/* 37:   */     {
/* 38:55 */       throw new AssertionError("rootClass and joinedSubClass are null");
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.CreateKeySecondPass
 * JD-Core Version:    0.7.0.1
 */