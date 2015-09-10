/*  1:   */ package javassist.bytecode.analysis;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Set;
/*  8:   */ 
/*  9:   */ public class Subroutine
/* 10:   */ {
/* 11:30 */   private List callers = new ArrayList();
/* 12:31 */   private Set access = new HashSet();
/* 13:   */   private int start;
/* 14:   */   
/* 15:   */   public Subroutine(int start, int caller)
/* 16:   */   {
/* 17:35 */     this.start = start;
/* 18:36 */     this.callers.add(new Integer(caller));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addCaller(int caller)
/* 22:   */   {
/* 23:40 */     this.callers.add(new Integer(caller));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int start()
/* 27:   */   {
/* 28:44 */     return this.start;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void access(int index)
/* 32:   */   {
/* 33:48 */     this.access.add(new Integer(index));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean isAccessed(int index)
/* 37:   */   {
/* 38:52 */     return this.access.contains(new Integer(index));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Collection accessed()
/* 42:   */   {
/* 43:56 */     return this.access;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Collection callers()
/* 47:   */   {
/* 48:60 */     return this.callers;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String toString()
/* 52:   */   {
/* 53:64 */     return "start = " + this.start + " callers = " + this.callers.toString();
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.Subroutine
 * JD-Core Version:    0.7.0.1
 */