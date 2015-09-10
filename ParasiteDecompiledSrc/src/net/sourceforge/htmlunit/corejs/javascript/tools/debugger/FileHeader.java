/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Color;
/*    4:     */ import java.awt.Dimension;
/*    5:     */ import java.awt.Font;
/*    6:     */ import java.awt.FontMetrics;
/*    7:     */ import java.awt.Graphics;
/*    8:     */ import java.awt.Polygon;
/*    9:     */ import java.awt.Rectangle;
/*   10:     */ import java.awt.event.MouseEvent;
/*   11:     */ import java.awt.event.MouseListener;
/*   12:     */ import javax.swing.JPanel;
/*   13:     */ import javax.swing.text.BadLocationException;
/*   14:     */ 
/*   15:     */ class FileHeader
/*   16:     */   extends JPanel
/*   17:     */   implements MouseListener
/*   18:     */ {
/*   19:     */   private static final long serialVersionUID = -2858905404778259127L;
/*   20:1935 */   private int pressLine = -1;
/*   21:     */   private FileWindow fileWindow;
/*   22:     */   
/*   23:     */   public FileHeader(FileWindow fileWindow)
/*   24:     */   {
/*   25:1946 */     this.fileWindow = fileWindow;
/*   26:1947 */     addMouseListener(this);
/*   27:1948 */     update();
/*   28:     */   }
/*   29:     */   
/*   30:     */   public void update()
/*   31:     */   {
/*   32:1955 */     FileTextArea textArea = this.fileWindow.textArea;
/*   33:1956 */     Font font = textArea.getFont();
/*   34:1957 */     setFont(font);
/*   35:1958 */     FontMetrics metrics = getFontMetrics(font);
/*   36:1959 */     int h = metrics.getHeight();
/*   37:1960 */     int lineCount = textArea.getLineCount() + 1;
/*   38:1961 */     String dummy = Integer.toString(lineCount);
/*   39:1962 */     if (dummy.length() < 2) {
/*   40:1963 */       dummy = "99";
/*   41:     */     }
/*   42:1965 */     Dimension d = new Dimension();
/*   43:1966 */     d.width = (metrics.stringWidth(dummy) + 16);
/*   44:1967 */     d.height = (lineCount * h + 100);
/*   45:1968 */     setPreferredSize(d);
/*   46:1969 */     setSize(d);
/*   47:     */   }
/*   48:     */   
/*   49:     */   public void paint(Graphics g)
/*   50:     */   {
/*   51:1977 */     super.paint(g);
/*   52:1978 */     FileTextArea textArea = this.fileWindow.textArea;
/*   53:1979 */     Font font = textArea.getFont();
/*   54:1980 */     g.setFont(font);
/*   55:1981 */     FontMetrics metrics = getFontMetrics(font);
/*   56:1982 */     Rectangle clip = g.getClipBounds();
/*   57:1983 */     g.setColor(getBackground());
/*   58:1984 */     g.fillRect(clip.x, clip.y, clip.width, clip.height);
/*   59:1985 */     int ascent = metrics.getMaxAscent();
/*   60:1986 */     int h = metrics.getHeight();
/*   61:1987 */     int lineCount = textArea.getLineCount() + 1;
/*   62:1988 */     String dummy = Integer.toString(lineCount);
/*   63:1989 */     if (dummy.length() < 2) {
/*   64:1990 */       dummy = "99";
/*   65:     */     }
/*   66:1992 */     int startLine = clip.y / h;
/*   67:1993 */     int endLine = (clip.y + clip.height) / h + 1;
/*   68:1994 */     int width = getWidth();
/*   69:1995 */     if (endLine > lineCount) {
/*   70:1995 */       endLine = lineCount;
/*   71:     */     }
/*   72:1996 */     for (int i = startLine; i < endLine; i++)
/*   73:     */     {
/*   74:1998 */       int pos = -2;
/*   75:     */       try
/*   76:     */       {
/*   77:2000 */         pos = textArea.getLineStartOffset(i);
/*   78:     */       }
/*   79:     */       catch (BadLocationException ignored) {}
/*   80:2003 */       boolean isBreakPoint = this.fileWindow.isBreakPoint(i + 1);
/*   81:2004 */       String text = Integer.toString(i + 1) + " ";
/*   82:2005 */       int y = i * h;
/*   83:2006 */       g.setColor(Color.blue);
/*   84:2007 */       g.drawString(text, 0, y + ascent);
/*   85:2008 */       int x = width - ascent;
/*   86:2009 */       if (isBreakPoint)
/*   87:     */       {
/*   88:2010 */         g.setColor(new Color(128, 0, 0));
/*   89:2011 */         int dy = y + ascent - 9;
/*   90:2012 */         g.fillOval(x, dy, 9, 9);
/*   91:2013 */         g.drawOval(x, dy, 8, 8);
/*   92:2014 */         g.drawOval(x, dy, 9, 9);
/*   93:     */       }
/*   94:2016 */       if (pos == this.fileWindow.currentPos)
/*   95:     */       {
/*   96:2017 */         Polygon arrow = new Polygon();
/*   97:2018 */         int dx = x;
/*   98:2019 */         y += ascent - 10;
/*   99:2020 */         int dy = y;
/*  100:2021 */         arrow.addPoint(dx, dy + 3);
/*  101:2022 */         arrow.addPoint(dx + 5, dy + 3);
/*  102:2023 */         for (x = dx + 5; x <= dx + 10; y++)
/*  103:     */         {
/*  104:2024 */           arrow.addPoint(x, y);x++;
/*  105:     */         }
/*  106:2026 */         for (x = dx + 9; x >= dx + 5; y++)
/*  107:     */         {
/*  108:2027 */           arrow.addPoint(x, y);x--;
/*  109:     */         }
/*  110:2029 */         arrow.addPoint(dx + 5, dy + 7);
/*  111:2030 */         arrow.addPoint(dx, dy + 7);
/*  112:2031 */         g.setColor(Color.yellow);
/*  113:2032 */         g.fillPolygon(arrow);
/*  114:2033 */         g.setColor(Color.black);
/*  115:2034 */         g.drawPolygon(arrow);
/*  116:     */       }
/*  117:     */     }
/*  118:     */   }
/*  119:     */   
/*  120:     */   public void mouseEntered(MouseEvent e) {}
/*  121:     */   
/*  122:     */   public void mousePressed(MouseEvent e)
/*  123:     */   {
/*  124:2051 */     Font font = this.fileWindow.textArea.getFont();
/*  125:2052 */     FontMetrics metrics = getFontMetrics(font);
/*  126:2053 */     int h = metrics.getHeight();
/*  127:2054 */     this.pressLine = (e.getY() / h);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public void mouseClicked(MouseEvent e) {}
/*  131:     */   
/*  132:     */   public void mouseExited(MouseEvent e) {}
/*  133:     */   
/*  134:     */   public void mouseReleased(MouseEvent e)
/*  135:     */   {
/*  136:2073 */     if ((e.getComponent() == this) && ((e.getModifiers() & 0x10) != 0))
/*  137:     */     {
/*  138:2075 */       int y = e.getY();
/*  139:2076 */       Font font = this.fileWindow.textArea.getFont();
/*  140:2077 */       FontMetrics metrics = getFontMetrics(font);
/*  141:2078 */       int h = metrics.getHeight();
/*  142:2079 */       int line = y / h;
/*  143:2080 */       if (line == this.pressLine) {
/*  144:2081 */         this.fileWindow.toggleBreakPoint(line + 1);
/*  145:     */       } else {
/*  146:2083 */         this.pressLine = -1;
/*  147:     */       }
/*  148:     */     }
/*  149:     */   }
/*  150:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.FileHeader
 * JD-Core Version:    0.7.0.1
 */