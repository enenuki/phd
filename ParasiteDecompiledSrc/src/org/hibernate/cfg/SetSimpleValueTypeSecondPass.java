/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.cfg.annotations.SimpleValueBinder;
/*  6:   */ 
/*  7:   */ public class SetSimpleValueTypeSecondPass
/*  8:   */   implements SecondPass
/*  9:   */ {
/* 10:   */   SimpleValueBinder binder;
/* 11:   */   
/* 12:   */   public SetSimpleValueTypeSecondPass(SimpleValueBinder val)
/* 13:   */   {
/* 14:38 */     this.binder = val;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void doSecondPass(Map persistentClasses)
/* 18:   */     throws MappingException
/* 19:   */   {
/* 20:42 */     this.binder.fillSimpleValue();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.SetSimpleValueTypeSecondPass
 * JD-Core Version:    0.7.0.1
 */