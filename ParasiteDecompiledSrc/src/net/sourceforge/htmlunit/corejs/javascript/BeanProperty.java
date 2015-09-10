/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ class BeanProperty
/*   4:    */ {
/*   5:    */   MemberBox getter;
/*   6:    */   MemberBox setter;
/*   7:    */   NativeJavaMethod setters;
/*   8:    */   
/*   9:    */   BeanProperty(MemberBox getter, MemberBox setter, NativeJavaMethod setters)
/*  10:    */   {
/*  11:903 */     this.getter = getter;
/*  12:904 */     this.setter = setter;
/*  13:905 */     this.setters = setters;
/*  14:    */   }
/*  15:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.BeanProperty
 * JD-Core Version:    0.7.0.1
 */