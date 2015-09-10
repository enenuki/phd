/*   1:    */ package com.gargoylesoftware.htmlunit.html.applets;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   5:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   6:    */ import java.applet.Applet;
/*   7:    */ import java.applet.AppletContext;
/*   8:    */ import java.applet.AudioClip;
/*   9:    */ import java.awt.Image;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStream;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.util.Collections;
/*  14:    */ import java.util.Enumeration;
/*  15:    */ import java.util.Iterator;
/*  16:    */ 
/*  17:    */ public class AppletContextImpl
/*  18:    */   implements AppletContext
/*  19:    */ {
/*  20: 39 */   private static final Enumeration<Applet> EMPTY_ENUMERATION = Collections.enumeration(Collections.emptyList());
/*  21:    */   private HtmlPage htmlPage_;
/*  22:    */   
/*  23:    */   AppletContextImpl(HtmlPage page)
/*  24:    */   {
/*  25: 44 */     this.htmlPage_ = page;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Applet getApplet(String name)
/*  29:    */   {
/*  30: 51 */     return null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Enumeration<Applet> getApplets()
/*  34:    */   {
/*  35: 58 */     return EMPTY_ENUMERATION;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AudioClip getAudioClip(URL url)
/*  39:    */   {
/*  40: 65 */     throw new RuntimeException("Not yet implemented!");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Image getImage(URL url)
/*  44:    */   {
/*  45: 72 */     throw new RuntimeException("Not yet implemented!");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public InputStream getStream(String key)
/*  49:    */   {
/*  50: 79 */     throw new RuntimeException("Not yet implemented!");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Iterator<String> getStreamKeys()
/*  54:    */   {
/*  55: 86 */     throw new RuntimeException("Not yet implemented!");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setStream(String key, InputStream stream)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 93 */     throw new RuntimeException("Not yet implemented!");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void showDocument(URL url)
/*  65:    */   {
/*  66:100 */     throw new RuntimeException("Not yet implemented!");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void showDocument(URL url, String target)
/*  70:    */   {
/*  71:107 */     throw new RuntimeException("Not yet implemented!");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void showStatus(String status)
/*  75:    */   {
/*  76:116 */     Window window = ((SimpleScriptable)this.htmlPage_.getScriptObject()).getWindow();
/*  77:117 */     window.jsxSet_status(status);
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.applets.AppletContextImpl
 * JD-Core Version:    0.7.0.1
 */