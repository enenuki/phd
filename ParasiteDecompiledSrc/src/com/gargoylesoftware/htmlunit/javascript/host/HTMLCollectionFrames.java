/*    1:     */ package com.gargoylesoftware.htmlunit.javascript.host;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*    6:     */ import com.gargoylesoftware.htmlunit.html.BaseFrame;
/*    7:     */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*    8:     */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*    9:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   10:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*   11:     */ import java.util.List;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   13:     */ import org.apache.commons.logging.Log;
/*   14:     */ import org.apache.commons.logging.LogFactory;
/*   15:     */ 
/*   16:     */ class HTMLCollectionFrames
/*   17:     */   extends HTMLCollection
/*   18:     */ {
/*   19:1748 */   private static final Log LOG = LogFactory.getLog(HTMLCollectionFrames.class);
/*   20:     */   
/*   21:     */   public HTMLCollectionFrames(HtmlPage page)
/*   22:     */   {
/*   23:1751 */     super(page, false, "Window.frames");
/*   24:     */   }
/*   25:     */   
/*   26:     */   protected boolean isMatching(DomNode node)
/*   27:     */   {
/*   28:1756 */     return node instanceof BaseFrame;
/*   29:     */   }
/*   30:     */   
/*   31:     */   protected Scriptable getScriptableForElement(Object obj)
/*   32:     */   {
/*   33:     */     WebWindow window;
/*   34:     */     WebWindow window;
/*   35:1762 */     if ((obj instanceof BaseFrame)) {
/*   36:1763 */       window = ((BaseFrame)obj).getEnclosedWindow();
/*   37:     */     } else {
/*   38:1766 */       window = ((FrameWindow)obj).getFrameElement().getEnclosedWindow();
/*   39:     */     }
/*   40:1769 */     return Window.getProxy(window);
/*   41:     */   }
/*   42:     */   
/*   43:     */   protected Object getWithPreemption(String name)
/*   44:     */   {
/*   45:1774 */     List<Object> elements = getElements();
/*   46:1776 */     for (Object next : elements)
/*   47:     */     {
/*   48:1777 */       BaseFrame frameElt = (BaseFrame)next;
/*   49:1778 */       WebWindow window = frameElt.getEnclosedWindow();
/*   50:1779 */       if (name.equals(window.getName()))
/*   51:     */       {
/*   52:1780 */         if (LOG.isDebugEnabled()) {
/*   53:1781 */           LOG.debug("Property \"" + name + "\" evaluated (by name) to " + window);
/*   54:     */         }
/*   55:1783 */         return getScriptableForElement(window);
/*   56:     */       }
/*   57:1785 */       if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_47)) && (frameElt.getAttribute("id").equals(name)))
/*   58:     */       {
/*   59:1787 */         if (LOG.isDebugEnabled()) {
/*   60:1788 */           LOG.debug("Property \"" + name + "\" evaluated (by id) to " + window);
/*   61:     */         }
/*   62:1790 */         return getScriptableForElement(window);
/*   63:     */       }
/*   64:     */     }
/*   65:1794 */     return NOT_FOUND;
/*   66:     */   }
/*   67:     */   
/*   68:     */   protected void addElementIds(List<String> idList, List<Object> elements)
/*   69:     */   {
/*   70:1799 */     for (Object next : elements)
/*   71:     */     {
/*   72:1800 */       BaseFrame frameElt = (BaseFrame)next;
/*   73:1801 */       WebWindow window = frameElt.getEnclosedWindow();
/*   74:1802 */       String windowName = window.getName();
/*   75:1803 */       if (windowName != null) {
/*   76:1804 */         idList.add(windowName);
/*   77:     */       }
/*   78:     */     }
/*   79:     */   }
/*   80:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.HTMLCollectionFrames
 * JD-Core Version:    0.7.0.1
 */