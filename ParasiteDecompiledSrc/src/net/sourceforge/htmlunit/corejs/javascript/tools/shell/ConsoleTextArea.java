/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import java.awt.event.KeyEvent;
/*   5:    */ import java.awt.event.KeyListener;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.PipedInputStream;
/*   9:    */ import java.io.PipedOutputStream;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.io.PrintWriter;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.swing.JTextArea;
/*  15:    */ import javax.swing.event.DocumentEvent;
/*  16:    */ import javax.swing.event.DocumentListener;
/*  17:    */ import javax.swing.text.BadLocationException;
/*  18:    */ import javax.swing.text.Document;
/*  19:    */ import javax.swing.text.Segment;
/*  20:    */ 
/*  21:    */ public class ConsoleTextArea
/*  22:    */   extends JTextArea
/*  23:    */   implements KeyListener, DocumentListener
/*  24:    */ {
/*  25:    */   static final long serialVersionUID = 8557083244830872961L;
/*  26:    */   private ConsoleWriter console1;
/*  27:    */   private ConsoleWriter console2;
/*  28:    */   private PrintStream out;
/*  29:    */   private PrintStream err;
/*  30:    */   private PrintWriter inPipe;
/*  31:    */   private PipedInputStream in;
/*  32:    */   private List<String> history;
/*  33:119 */   private int historyIndex = -1;
/*  34:120 */   private int outputMark = 0;
/*  35:    */   
/*  36:    */   public void select(int start, int end)
/*  37:    */   {
/*  38:124 */     requestFocus();
/*  39:125 */     super.select(start, end);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public ConsoleTextArea(String[] argv)
/*  43:    */   {
/*  44:130 */     this.history = new ArrayList();
/*  45:131 */     this.console1 = new ConsoleWriter(this);
/*  46:132 */     this.console2 = new ConsoleWriter(this);
/*  47:133 */     this.out = new PrintStream(this.console1, true);
/*  48:134 */     this.err = new PrintStream(this.console2, true);
/*  49:135 */     PipedOutputStream outPipe = new PipedOutputStream();
/*  50:136 */     this.inPipe = new PrintWriter(outPipe);
/*  51:137 */     this.in = new PipedInputStream();
/*  52:    */     try
/*  53:    */     {
/*  54:139 */       outPipe.connect(this.in);
/*  55:    */     }
/*  56:    */     catch (IOException exc)
/*  57:    */     {
/*  58:141 */       exc.printStackTrace();
/*  59:    */     }
/*  60:143 */     getDocument().addDocumentListener(this);
/*  61:144 */     addKeyListener(this);
/*  62:145 */     setLineWrap(true);
/*  63:146 */     setFont(new Font("Monospaced", 0, 12));
/*  64:    */   }
/*  65:    */   
/*  66:    */   synchronized void returnPressed()
/*  67:    */   {
/*  68:151 */     Document doc = getDocument();
/*  69:152 */     int len = doc.getLength();
/*  70:153 */     Segment segment = new Segment();
/*  71:    */     try
/*  72:    */     {
/*  73:155 */       doc.getText(this.outputMark, len - this.outputMark, segment);
/*  74:    */     }
/*  75:    */     catch (BadLocationException ignored)
/*  76:    */     {
/*  77:157 */       ignored.printStackTrace();
/*  78:    */     }
/*  79:159 */     if (segment.count > 0) {
/*  80:160 */       this.history.add(segment.toString());
/*  81:    */     }
/*  82:162 */     this.historyIndex = this.history.size();
/*  83:163 */     this.inPipe.write(segment.array, segment.offset, segment.count);
/*  84:164 */     append("\n");
/*  85:165 */     this.outputMark = doc.getLength();
/*  86:166 */     this.inPipe.write("\n");
/*  87:167 */     this.inPipe.flush();
/*  88:168 */     this.console1.flush();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void eval(String str)
/*  92:    */   {
/*  93:172 */     this.inPipe.write(str);
/*  94:173 */     this.inPipe.write("\n");
/*  95:174 */     this.inPipe.flush();
/*  96:175 */     this.console1.flush();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void keyPressed(KeyEvent e)
/* 100:    */   {
/* 101:179 */     int code = e.getKeyCode();
/* 102:180 */     if ((code == 8) || (code == 37))
/* 103:    */     {
/* 104:181 */       if (this.outputMark == getCaretPosition()) {
/* 105:182 */         e.consume();
/* 106:    */       }
/* 107:    */     }
/* 108:184 */     else if (code == 36)
/* 109:    */     {
/* 110:185 */       int caretPos = getCaretPosition();
/* 111:186 */       if (caretPos == this.outputMark)
/* 112:    */       {
/* 113:187 */         e.consume();
/* 114:    */       }
/* 115:188 */       else if ((caretPos > this.outputMark) && 
/* 116:189 */         (!e.isControlDown()))
/* 117:    */       {
/* 118:190 */         if (e.isShiftDown()) {
/* 119:191 */           moveCaretPosition(this.outputMark);
/* 120:    */         } else {
/* 121:193 */           setCaretPosition(this.outputMark);
/* 122:    */         }
/* 123:195 */         e.consume();
/* 124:    */       }
/* 125:    */     }
/* 126:198 */     else if (code == 10)
/* 127:    */     {
/* 128:199 */       returnPressed();
/* 129:200 */       e.consume();
/* 130:    */     }
/* 131:201 */     else if (code == 38)
/* 132:    */     {
/* 133:202 */       this.historyIndex -= 1;
/* 134:203 */       if (this.historyIndex >= 0)
/* 135:    */       {
/* 136:204 */         if (this.historyIndex >= this.history.size()) {
/* 137:205 */           this.historyIndex = (this.history.size() - 1);
/* 138:    */         }
/* 139:207 */         if (this.historyIndex >= 0)
/* 140:    */         {
/* 141:208 */           String str = (String)this.history.get(this.historyIndex);
/* 142:209 */           int len = getDocument().getLength();
/* 143:210 */           replaceRange(str, this.outputMark, len);
/* 144:211 */           int caretPos = this.outputMark + str.length();
/* 145:212 */           select(caretPos, caretPos);
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:214 */           this.historyIndex += 1;
/* 150:    */         }
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:217 */         this.historyIndex += 1;
/* 155:    */       }
/* 156:219 */       e.consume();
/* 157:    */     }
/* 158:220 */     else if (code == 40)
/* 159:    */     {
/* 160:221 */       int caretPos = this.outputMark;
/* 161:222 */       if (this.history.size() > 0)
/* 162:    */       {
/* 163:223 */         this.historyIndex += 1;
/* 164:224 */         if (this.historyIndex < 0) {
/* 165:224 */           this.historyIndex = 0;
/* 166:    */         }
/* 167:225 */         int len = getDocument().getLength();
/* 168:226 */         if (this.historyIndex < this.history.size())
/* 169:    */         {
/* 170:227 */           String str = (String)this.history.get(this.historyIndex);
/* 171:228 */           replaceRange(str, this.outputMark, len);
/* 172:229 */           caretPos = this.outputMark + str.length();
/* 173:    */         }
/* 174:    */         else
/* 175:    */         {
/* 176:231 */           this.historyIndex = this.history.size();
/* 177:232 */           replaceRange("", this.outputMark, len);
/* 178:    */         }
/* 179:    */       }
/* 180:235 */       select(caretPos, caretPos);
/* 181:236 */       e.consume();
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void keyTyped(KeyEvent e)
/* 186:    */   {
/* 187:241 */     int keyChar = e.getKeyChar();
/* 188:242 */     if (keyChar == 8)
/* 189:    */     {
/* 190:243 */       if (this.outputMark == getCaretPosition()) {
/* 191:244 */         e.consume();
/* 192:    */       }
/* 193:    */     }
/* 194:246 */     else if (getCaretPosition() < this.outputMark) {
/* 195:247 */       setCaretPosition(this.outputMark);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public synchronized void keyReleased(KeyEvent e) {}
/* 200:    */   
/* 201:    */   public synchronized void write(String str)
/* 202:    */   {
/* 203:255 */     insert(str, this.outputMark);
/* 204:256 */     int len = str.length();
/* 205:257 */     this.outputMark += len;
/* 206:258 */     select(this.outputMark, this.outputMark);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public synchronized void insertUpdate(DocumentEvent e)
/* 210:    */   {
/* 211:262 */     int len = e.getLength();
/* 212:263 */     int off = e.getOffset();
/* 213:264 */     if (this.outputMark > off) {
/* 214:265 */       this.outputMark += len;
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public synchronized void removeUpdate(DocumentEvent e)
/* 219:    */   {
/* 220:270 */     int len = e.getLength();
/* 221:271 */     int off = e.getOffset();
/* 222:272 */     if (this.outputMark > off) {
/* 223:273 */       if (this.outputMark >= off + len) {
/* 224:274 */         this.outputMark -= len;
/* 225:    */       } else {
/* 226:276 */         this.outputMark = off;
/* 227:    */       }
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public synchronized void postUpdateUI()
/* 232:    */   {
/* 233:283 */     requestFocus();
/* 234:284 */     setCaret(getCaret());
/* 235:285 */     select(this.outputMark, this.outputMark);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public synchronized void changedUpdate(DocumentEvent e) {}
/* 239:    */   
/* 240:    */   public InputStream getIn()
/* 241:    */   {
/* 242:293 */     return this.in;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public PrintStream getOut()
/* 246:    */   {
/* 247:297 */     return this.out;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public PrintStream getErr()
/* 251:    */   {
/* 252:301 */     return this.err;
/* 253:    */   }
/* 254:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.ConsoleTextArea
 * JD-Core Version:    0.7.0.1
 */