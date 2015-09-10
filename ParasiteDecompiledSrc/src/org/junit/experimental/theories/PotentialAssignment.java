/* 1:  */ package org.junit.experimental.theories;
/* 2:  */ 
/* 3:  */ public abstract class PotentialAssignment
/* 4:  */ {
/* 5:  */   public static PotentialAssignment forValue(final String name, Object value)
/* 6:  */   {
/* 7:9 */     new PotentialAssignment()
/* 8:  */     {
/* 9:  */       public Object getValue()
/* ::  */         throws PotentialAssignment.CouldNotGenerateValueException
/* ;:  */       {
/* <:< */         return this.val$value;
/* =:  */       }
/* >:  */       
/* ?:  */       public String toString()
/* @:  */       {
/* A:A */         return String.format("[%s]", new Object[] { this.val$value });
/* B:  */       }
/* C:  */       
/* D:  */       public String getDescription()
/* E:  */         throws PotentialAssignment.CouldNotGenerateValueException
/* F:  */       {
/* G:G */         return name;
/* H:  */       }
/* I:  */     };
/* J:  */   }
/* K:  */   
/* L:  */   public abstract Object getValue()
/* M:  */     throws PotentialAssignment.CouldNotGenerateValueException;
/* N:  */   
/* O:  */   public abstract String getDescription()
/* P:  */     throws PotentialAssignment.CouldNotGenerateValueException;
/* Q:  */   
/* R:  */   public static class CouldNotGenerateValueException
/* S:  */     extends Exception
/* T:  */   {
/* U:  */     private static final long serialVersionUID = 1L;
/* V:  */   }
/* W:  */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.PotentialAssignment
 * JD-Core Version:    0.7.0.1
 */