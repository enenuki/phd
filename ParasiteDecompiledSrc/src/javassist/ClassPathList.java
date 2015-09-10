/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ final class ClassPathList
/*  4:   */ {
/*  5:   */   ClassPathList next;
/*  6:   */   ClassPath path;
/*  7:   */   
/*  8:   */   ClassPathList(ClassPath p, ClassPathList n)
/*  9:   */   {
/* 10:29 */     this.next = n;
/* 11:30 */     this.path = p;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ClassPathList
 * JD-Core Version:    0.7.0.1
 */