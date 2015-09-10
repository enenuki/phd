/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Component;
/*    4:     */ import java.awt.event.ActionEvent;
/*    5:     */ import java.awt.event.ActionListener;
/*    6:     */ import javax.swing.JComponent;
/*    7:     */ import javax.swing.JInternalFrame;
/*    8:     */ import javax.swing.JScrollPane;
/*    9:     */ import javax.swing.text.BadLocationException;
/*   10:     */ import javax.swing.text.Document;
/*   11:     */ 
/*   12:     */ class FileWindow
/*   13:     */   extends JInternalFrame
/*   14:     */   implements ActionListener
/*   15:     */ {
/*   16:     */   private static final long serialVersionUID = -6212382604952082370L;
/*   17:     */   private SwingGui debugGui;
/*   18:     */   private Dim.SourceInfo sourceInfo;
/*   19:     */   FileTextArea textArea;
/*   20:     */   private FileHeader fileHeader;
/*   21:     */   private JScrollPane p;
/*   22:     */   int currentPos;
/*   23:     */   
/*   24:     */   void load()
/*   25:     */   {
/*   26:2133 */     String url = getUrl();
/*   27:2134 */     if (url != null)
/*   28:     */     {
/*   29:2135 */       RunProxy proxy = new RunProxy(this.debugGui, 2);
/*   30:2136 */       proxy.fileName = url;
/*   31:2137 */       proxy.text = this.sourceInfo.source();
/*   32:2138 */       new Thread(proxy).start();
/*   33:     */     }
/*   34:     */   }
/*   35:     */   
/*   36:     */   public int getPosition(int line)
/*   37:     */   {
/*   38:2146 */     int result = -1;
/*   39:     */     try
/*   40:     */     {
/*   41:2148 */       result = this.textArea.getLineStartOffset(line);
/*   42:     */     }
/*   43:     */     catch (BadLocationException exc) {}
/*   44:2151 */     return result;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public boolean isBreakPoint(int line)
/*   48:     */   {
/*   49:2158 */     return (this.sourceInfo.breakableLine(line)) && (this.sourceInfo.breakpoint(line));
/*   50:     */   }
/*   51:     */   
/*   52:     */   public void toggleBreakPoint(int line)
/*   53:     */   {
/*   54:2165 */     if (!isBreakPoint(line)) {
/*   55:2166 */       setBreakPoint(line);
/*   56:     */     } else {
/*   57:2168 */       clearBreakPoint(line);
/*   58:     */     }
/*   59:     */   }
/*   60:     */   
/*   61:     */   public void setBreakPoint(int line)
/*   62:     */   {
/*   63:2176 */     if (this.sourceInfo.breakableLine(line))
/*   64:     */     {
/*   65:2177 */       boolean changed = this.sourceInfo.breakpoint(line, true);
/*   66:2178 */       if (changed) {
/*   67:2179 */         this.fileHeader.repaint();
/*   68:     */       }
/*   69:     */     }
/*   70:     */   }
/*   71:     */   
/*   72:     */   public void clearBreakPoint(int line)
/*   73:     */   {
/*   74:2188 */     if (this.sourceInfo.breakableLine(line))
/*   75:     */     {
/*   76:2189 */       boolean changed = this.sourceInfo.breakpoint(line, false);
/*   77:2190 */       if (changed) {
/*   78:2191 */         this.fileHeader.repaint();
/*   79:     */       }
/*   80:     */     }
/*   81:     */   }
/*   82:     */   
/*   83:     */   public FileWindow(SwingGui debugGui, Dim.SourceInfo sourceInfo)
/*   84:     */   {
/*   85:2200 */     super(SwingGui.getShortName(sourceInfo.url()), true, true, true, true);
/*   86:     */     
/*   87:2202 */     this.debugGui = debugGui;
/*   88:2203 */     this.sourceInfo = sourceInfo;
/*   89:2204 */     updateToolTip();
/*   90:2205 */     this.currentPos = -1;
/*   91:2206 */     this.textArea = new FileTextArea(this);
/*   92:2207 */     this.textArea.setRows(24);
/*   93:2208 */     this.textArea.setColumns(80);
/*   94:2209 */     this.p = new JScrollPane();
/*   95:2210 */     this.fileHeader = new FileHeader(this);
/*   96:2211 */     this.p.setViewportView(this.textArea);
/*   97:2212 */     this.p.setRowHeaderView(this.fileHeader);
/*   98:2213 */     setContentPane(this.p);
/*   99:2214 */     pack();
/*  100:2215 */     updateText(sourceInfo);
/*  101:2216 */     this.textArea.select(0);
/*  102:     */   }
/*  103:     */   
/*  104:     */   private void updateToolTip()
/*  105:     */   {
/*  106:2225 */     int n = getComponentCount() - 1;
/*  107:2226 */     if (n > 1) {
/*  108:2227 */       n = 1;
/*  109:2228 */     } else if (n < 0) {
/*  110:2229 */       return;
/*  111:     */     }
/*  112:2231 */     Component c = getComponent(n);
/*  113:2233 */     if ((c != null) && ((c instanceof JComponent))) {
/*  114:2234 */       ((JComponent)c).setToolTipText(getUrl());
/*  115:     */     }
/*  116:     */   }
/*  117:     */   
/*  118:     */   public String getUrl()
/*  119:     */   {
/*  120:2242 */     return this.sourceInfo.url();
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void updateText(Dim.SourceInfo sourceInfo)
/*  124:     */   {
/*  125:2249 */     this.sourceInfo = sourceInfo;
/*  126:2250 */     String newText = sourceInfo.source();
/*  127:2251 */     if (!this.textArea.getText().equals(newText))
/*  128:     */     {
/*  129:2252 */       this.textArea.setText(newText);
/*  130:2253 */       int pos = 0;
/*  131:2254 */       if (this.currentPos != -1) {
/*  132:2255 */         pos = this.currentPos;
/*  133:     */       }
/*  134:2257 */       this.textArea.select(pos);
/*  135:     */     }
/*  136:2259 */     this.fileHeader.update();
/*  137:2260 */     this.fileHeader.repaint();
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void setPosition(int pos)
/*  141:     */   {
/*  142:2267 */     this.textArea.select(pos);
/*  143:2268 */     this.currentPos = pos;
/*  144:2269 */     this.fileHeader.repaint();
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void select(int start, int end)
/*  148:     */   {
/*  149:2276 */     int docEnd = this.textArea.getDocument().getLength();
/*  150:2277 */     this.textArea.select(docEnd, docEnd);
/*  151:2278 */     this.textArea.select(start, end);
/*  152:     */   }
/*  153:     */   
/*  154:     */   public void dispose()
/*  155:     */   {
/*  156:2286 */     this.debugGui.removeWindow(this);
/*  157:2287 */     super.dispose();
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void actionPerformed(ActionEvent e)
/*  161:     */   {
/*  162:2296 */     String cmd = e.getActionCommand();
/*  163:2297 */     if (!cmd.equals("Cut")) {
/*  164:2299 */       if (cmd.equals("Copy")) {
/*  165:2300 */         this.textArea.copy();
/*  166:2301 */       } else if (!cmd.equals("Paste")) {}
/*  167:     */     }
/*  168:     */   }
/*  169:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.FileWindow
 * JD-Core Version:    0.7.0.1
 */