/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Font;
/*    4:     */ import java.awt.event.KeyEvent;
/*    5:     */ import java.awt.event.KeyListener;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import java.util.Collections;
/*    8:     */ import java.util.List;
/*    9:     */ import javax.swing.JTextArea;
/*   10:     */ import javax.swing.event.DocumentEvent;
/*   11:     */ import javax.swing.event.DocumentListener;
/*   12:     */ import javax.swing.text.BadLocationException;
/*   13:     */ import javax.swing.text.Document;
/*   14:     */ import javax.swing.text.Segment;
/*   15:     */ 
/*   16:     */ class EvalTextArea
/*   17:     */   extends JTextArea
/*   18:     */   implements KeyListener, DocumentListener
/*   19:     */ {
/*   20:     */   private static final long serialVersionUID = -3918033649601064194L;
/*   21:     */   private SwingGui debugGui;
/*   22:     */   private List<String> history;
/*   23:1043 */   private int historyIndex = -1;
/*   24:     */   private int outputMark;
/*   25:     */   
/*   26:     */   public EvalTextArea(SwingGui debugGui)
/*   27:     */   {
/*   28:1054 */     this.debugGui = debugGui;
/*   29:1055 */     this.history = Collections.synchronizedList(new ArrayList());
/*   30:1056 */     Document doc = getDocument();
/*   31:1057 */     doc.addDocumentListener(this);
/*   32:1058 */     addKeyListener(this);
/*   33:1059 */     setLineWrap(true);
/*   34:1060 */     setFont(new Font("Monospaced", 0, 12));
/*   35:1061 */     append("% ");
/*   36:1062 */     this.outputMark = doc.getLength();
/*   37:     */   }
/*   38:     */   
/*   39:     */   public void select(int start, int end)
/*   40:     */   {
/*   41:1071 */     super.select(start, end);
/*   42:     */   }
/*   43:     */   
/*   44:     */   private synchronized void returnPressed()
/*   45:     */   {
/*   46:1078 */     Document doc = getDocument();
/*   47:1079 */     int len = doc.getLength();
/*   48:1080 */     Segment segment = new Segment();
/*   49:     */     try
/*   50:     */     {
/*   51:1082 */       doc.getText(this.outputMark, len - this.outputMark, segment);
/*   52:     */     }
/*   53:     */     catch (BadLocationException ignored)
/*   54:     */     {
/*   55:1084 */       ignored.printStackTrace();
/*   56:     */     }
/*   57:1086 */     String text = segment.toString();
/*   58:1087 */     if (this.debugGui.dim.stringIsCompilableUnit(text))
/*   59:     */     {
/*   60:1088 */       if (text.trim().length() > 0)
/*   61:     */       {
/*   62:1089 */         this.history.add(text);
/*   63:1090 */         this.historyIndex = this.history.size();
/*   64:     */       }
/*   65:1092 */       append("\n");
/*   66:1093 */       String result = this.debugGui.dim.eval(text);
/*   67:1094 */       if (result.length() > 0)
/*   68:     */       {
/*   69:1095 */         append(result);
/*   70:1096 */         append("\n");
/*   71:     */       }
/*   72:1098 */       append("% ");
/*   73:1099 */       this.outputMark = doc.getLength();
/*   74:     */     }
/*   75:     */     else
/*   76:     */     {
/*   77:1101 */       append("\n");
/*   78:     */     }
/*   79:     */   }
/*   80:     */   
/*   81:     */   public synchronized void write(String str)
/*   82:     */   {
/*   83:1109 */     insert(str, this.outputMark);
/*   84:1110 */     int len = str.length();
/*   85:1111 */     this.outputMark += len;
/*   86:1112 */     select(this.outputMark, this.outputMark);
/*   87:     */   }
/*   88:     */   
/*   89:     */   public void keyPressed(KeyEvent e)
/*   90:     */   {
/*   91:1121 */     int code = e.getKeyCode();
/*   92:1122 */     if ((code == 8) || (code == 37))
/*   93:     */     {
/*   94:1123 */       if (this.outputMark == getCaretPosition()) {
/*   95:1124 */         e.consume();
/*   96:     */       }
/*   97:     */     }
/*   98:1126 */     else if (code == 36)
/*   99:     */     {
/*  100:1127 */       int caretPos = getCaretPosition();
/*  101:1128 */       if (caretPos == this.outputMark)
/*  102:     */       {
/*  103:1129 */         e.consume();
/*  104:     */       }
/*  105:1130 */       else if ((caretPos > this.outputMark) && 
/*  106:1131 */         (!e.isControlDown()))
/*  107:     */       {
/*  108:1132 */         if (e.isShiftDown()) {
/*  109:1133 */           moveCaretPosition(this.outputMark);
/*  110:     */         } else {
/*  111:1135 */           setCaretPosition(this.outputMark);
/*  112:     */         }
/*  113:1137 */         e.consume();
/*  114:     */       }
/*  115:     */     }
/*  116:1140 */     else if (code == 10)
/*  117:     */     {
/*  118:1141 */       returnPressed();
/*  119:1142 */       e.consume();
/*  120:     */     }
/*  121:1143 */     else if (code == 38)
/*  122:     */     {
/*  123:1144 */       this.historyIndex -= 1;
/*  124:1145 */       if (this.historyIndex >= 0)
/*  125:     */       {
/*  126:1146 */         if (this.historyIndex >= this.history.size()) {
/*  127:1147 */           this.historyIndex = (this.history.size() - 1);
/*  128:     */         }
/*  129:1149 */         if (this.historyIndex >= 0)
/*  130:     */         {
/*  131:1150 */           String str = (String)this.history.get(this.historyIndex);
/*  132:1151 */           int len = getDocument().getLength();
/*  133:1152 */           replaceRange(str, this.outputMark, len);
/*  134:1153 */           int caretPos = this.outputMark + str.length();
/*  135:1154 */           select(caretPos, caretPos);
/*  136:     */         }
/*  137:     */         else
/*  138:     */         {
/*  139:1156 */           this.historyIndex += 1;
/*  140:     */         }
/*  141:     */       }
/*  142:     */       else
/*  143:     */       {
/*  144:1159 */         this.historyIndex += 1;
/*  145:     */       }
/*  146:1161 */       e.consume();
/*  147:     */     }
/*  148:1162 */     else if (code == 40)
/*  149:     */     {
/*  150:1163 */       int caretPos = this.outputMark;
/*  151:1164 */       if (this.history.size() > 0)
/*  152:     */       {
/*  153:1165 */         this.historyIndex += 1;
/*  154:1166 */         if (this.historyIndex < 0) {
/*  155:1166 */           this.historyIndex = 0;
/*  156:     */         }
/*  157:1167 */         int len = getDocument().getLength();
/*  158:1168 */         if (this.historyIndex < this.history.size())
/*  159:     */         {
/*  160:1169 */           String str = (String)this.history.get(this.historyIndex);
/*  161:1170 */           replaceRange(str, this.outputMark, len);
/*  162:1171 */           caretPos = this.outputMark + str.length();
/*  163:     */         }
/*  164:     */         else
/*  165:     */         {
/*  166:1173 */           this.historyIndex = this.history.size();
/*  167:1174 */           replaceRange("", this.outputMark, len);
/*  168:     */         }
/*  169:     */       }
/*  170:1177 */       select(caretPos, caretPos);
/*  171:1178 */       e.consume();
/*  172:     */     }
/*  173:     */   }
/*  174:     */   
/*  175:     */   public void keyTyped(KeyEvent e)
/*  176:     */   {
/*  177:1186 */     int keyChar = e.getKeyChar();
/*  178:1187 */     if (keyChar == 8)
/*  179:     */     {
/*  180:1188 */       if (this.outputMark == getCaretPosition()) {
/*  181:1189 */         e.consume();
/*  182:     */       }
/*  183:     */     }
/*  184:1191 */     else if (getCaretPosition() < this.outputMark) {
/*  185:1192 */       setCaretPosition(this.outputMark);
/*  186:     */     }
/*  187:     */   }
/*  188:     */   
/*  189:     */   public synchronized void keyReleased(KeyEvent e) {}
/*  190:     */   
/*  191:     */   public synchronized void insertUpdate(DocumentEvent e)
/*  192:     */   {
/*  193:1208 */     int len = e.getLength();
/*  194:1209 */     int off = e.getOffset();
/*  195:1210 */     if (this.outputMark > off) {
/*  196:1211 */       this.outputMark += len;
/*  197:     */     }
/*  198:     */   }
/*  199:     */   
/*  200:     */   public synchronized void removeUpdate(DocumentEvent e)
/*  201:     */   {
/*  202:1219 */     int len = e.getLength();
/*  203:1220 */     int off = e.getOffset();
/*  204:1221 */     if (this.outputMark > off) {
/*  205:1222 */       if (this.outputMark >= off + len) {
/*  206:1223 */         this.outputMark -= len;
/*  207:     */       } else {
/*  208:1225 */         this.outputMark = off;
/*  209:     */       }
/*  210:     */     }
/*  211:     */   }
/*  212:     */   
/*  213:     */   public synchronized void postUpdateUI()
/*  214:     */   {
/*  215:1235 */     setCaret(getCaret());
/*  216:1236 */     select(this.outputMark, this.outputMark);
/*  217:     */   }
/*  218:     */   
/*  219:     */   public synchronized void changedUpdate(DocumentEvent e) {}
/*  220:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.EvalTextArea
 * JD-Core Version:    0.7.0.1
 */