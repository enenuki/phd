/*  1:   */ package com.gargoylesoftware.htmlunit.html.applets;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlApplet;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  5:   */ import java.applet.AppletContext;
/*  6:   */ import java.applet.AppletStub;
/*  7:   */ import java.net.URL;
/*  8:   */ 
/*  9:   */ public class AppletStubImpl
/* 10:   */   implements AppletStub
/* 11:   */ {
/* 12:   */   private final AppletContextImpl appletContextImpl_;
/* 13:   */   
/* 14:   */   public AppletStubImpl(HtmlApplet htmlApplet)
/* 15:   */   {
/* 16:39 */     this.appletContextImpl_ = new AppletContextImpl((HtmlPage)htmlApplet.getPage());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void appletResize(int width, int height) {}
/* 20:   */   
/* 21:   */   public AppletContext getAppletContext()
/* 22:   */   {
/* 23:53 */     return this.appletContextImpl_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public URL getCodeBase()
/* 27:   */   {
/* 28:60 */     throw new RuntimeException("Not yet implemented!");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public URL getDocumentBase()
/* 32:   */   {
/* 33:67 */     throw new RuntimeException("Not yet implemented!");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getParameter(String name)
/* 37:   */   {
/* 38:74 */     throw new RuntimeException("Not yet implemented!");
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean isActive()
/* 42:   */   {
/* 43:81 */     throw new RuntimeException("Not yet implemented!");
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.applets.AppletStubImpl
 * JD-Core Version:    0.7.0.1
 */