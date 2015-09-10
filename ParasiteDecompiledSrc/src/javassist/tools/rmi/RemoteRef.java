/*  1:   */ package javassist.tools.rmi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class RemoteRef
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   public int oid;
/*  9:   */   public String classname;
/* 10:   */   
/* 11:   */   public RemoteRef(int i)
/* 12:   */   {
/* 13:27 */     this.oid = i;
/* 14:28 */     this.classname = null;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public RemoteRef(int i, String name)
/* 18:   */   {
/* 19:32 */     this.oid = i;
/* 20:33 */     this.classname = name;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.RemoteRef
 * JD-Core Version:    0.7.0.1
 */