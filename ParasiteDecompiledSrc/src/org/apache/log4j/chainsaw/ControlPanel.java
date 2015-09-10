/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.GridBagConstraints;
/*   5:    */ import java.awt.GridBagLayout;
/*   6:    */ import java.awt.event.ActionEvent;
/*   7:    */ import java.awt.event.ActionListener;
/*   8:    */ import javax.swing.AbstractButton;
/*   9:    */ import javax.swing.BorderFactory;
/*  10:    */ import javax.swing.JButton;
/*  11:    */ import javax.swing.JComboBox;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JPanel;
/*  15:    */ import javax.swing.JTextField;
/*  16:    */ import javax.swing.event.DocumentEvent;
/*  17:    */ import javax.swing.event.DocumentListener;
/*  18:    */ import javax.swing.text.Document;
/*  19:    */ import javax.swing.text.JTextComponent;
/*  20:    */ import org.apache.log4j.Level;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.apache.log4j.Priority;
/*  23:    */ 
/*  24:    */ class ControlPanel
/*  25:    */   extends JPanel
/*  26:    */ {
/*  27: 42 */   private static final Logger LOG = Logger.getLogger(ControlPanel.class);
/*  28:    */   
/*  29:    */   ControlPanel(MyTableModel aModel)
/*  30:    */   {
/*  31: 51 */     setBorder(BorderFactory.createTitledBorder("Controls: "));
/*  32: 52 */     GridBagLayout gridbag = new GridBagLayout();
/*  33: 53 */     GridBagConstraints c = new GridBagConstraints();
/*  34: 54 */     setLayout(gridbag);
/*  35:    */     
/*  36:    */ 
/*  37: 57 */     c.ipadx = 5;
/*  38: 58 */     c.ipady = 5;
/*  39:    */     
/*  40:    */ 
/*  41: 61 */     c.gridx = 0;
/*  42: 62 */     c.anchor = 13;
/*  43:    */     
/*  44: 64 */     c.gridy = 0;
/*  45: 65 */     JLabel label = new JLabel("Filter Level:");
/*  46: 66 */     gridbag.setConstraints(label, c);
/*  47: 67 */     add(label);
/*  48:    */     
/*  49: 69 */     c.gridy += 1;
/*  50: 70 */     label = new JLabel("Filter Thread:");
/*  51: 71 */     gridbag.setConstraints(label, c);
/*  52: 72 */     add(label);
/*  53:    */     
/*  54: 74 */     c.gridy += 1;
/*  55: 75 */     label = new JLabel("Filter Logger:");
/*  56: 76 */     gridbag.setConstraints(label, c);
/*  57: 77 */     add(label);
/*  58:    */     
/*  59: 79 */     c.gridy += 1;
/*  60: 80 */     label = new JLabel("Filter NDC:");
/*  61: 81 */     gridbag.setConstraints(label, c);
/*  62: 82 */     add(label);
/*  63:    */     
/*  64: 84 */     c.gridy += 1;
/*  65: 85 */     label = new JLabel("Filter Message:");
/*  66: 86 */     gridbag.setConstraints(label, c);
/*  67: 87 */     add(label);
/*  68:    */     
/*  69:    */ 
/*  70: 90 */     c.weightx = 1.0D;
/*  71:    */     
/*  72: 92 */     c.gridx = 1;
/*  73: 93 */     c.anchor = 17;
/*  74:    */     
/*  75: 95 */     c.gridy = 0;
/*  76: 96 */     Level[] allPriorities = { Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE };
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:103 */     JComboBox priorities = new JComboBox(allPriorities);
/*  84:104 */     Level lowest = allPriorities[(allPriorities.length - 1)];
/*  85:105 */     priorities.setSelectedItem(lowest);
/*  86:106 */     aModel.setPriorityFilter(lowest);
/*  87:107 */     gridbag.setConstraints(priorities, c);
/*  88:108 */     add(priorities);
/*  89:109 */     priorities.setEditable(false);
/*  90:110 */     priorities.addActionListener(new ActionListener()
/*  91:    */     {
/*  92:    */       private final MyTableModel val$aModel;
/*  93:    */       private final JComboBox val$priorities;
/*  94:    */       
/*  95:    */       public void actionPerformed(ActionEvent aEvent)
/*  96:    */       {
/*  97:112 */         this.val$aModel.setPriorityFilter((Priority)this.val$priorities.getSelectedItem());
/*  98:    */       }
/*  99:117 */     });
/* 100:118 */     c.fill = 2;
/* 101:119 */     c.gridy += 1;
/* 102:120 */     JTextField threadField = new JTextField("");
/* 103:121 */     threadField.getDocument().addDocumentListener(new DocumentListener()
/* 104:    */     {
/* 105:    */       private final MyTableModel val$aModel;
/* 106:    */       private final JTextField val$threadField;
/* 107:    */       
/* 108:    */       public void insertUpdate(DocumentEvent aEvent)
/* 109:    */       {
/* 110:123 */         this.val$aModel.setThreadFilter(this.val$threadField.getText());
/* 111:    */       }
/* 112:    */       
/* 113:    */       public void removeUpdate(DocumentEvent aEvente)
/* 114:    */       {
/* 115:126 */         this.val$aModel.setThreadFilter(this.val$threadField.getText());
/* 116:    */       }
/* 117:    */       
/* 118:    */       public void changedUpdate(DocumentEvent aEvent)
/* 119:    */       {
/* 120:129 */         this.val$aModel.setThreadFilter(this.val$threadField.getText());
/* 121:    */       }
/* 122:131 */     });
/* 123:132 */     gridbag.setConstraints(threadField, c);
/* 124:133 */     add(threadField);
/* 125:    */     
/* 126:135 */     c.gridy += 1;
/* 127:136 */     JTextField catField = new JTextField("");
/* 128:137 */     catField.getDocument().addDocumentListener(new DocumentListener()
/* 129:    */     {
/* 130:    */       private final MyTableModel val$aModel;
/* 131:    */       private final JTextField val$catField;
/* 132:    */       
/* 133:    */       public void insertUpdate(DocumentEvent aEvent)
/* 134:    */       {
/* 135:139 */         this.val$aModel.setCategoryFilter(this.val$catField.getText());
/* 136:    */       }
/* 137:    */       
/* 138:    */       public void removeUpdate(DocumentEvent aEvent)
/* 139:    */       {
/* 140:142 */         this.val$aModel.setCategoryFilter(this.val$catField.getText());
/* 141:    */       }
/* 142:    */       
/* 143:    */       public void changedUpdate(DocumentEvent aEvent)
/* 144:    */       {
/* 145:145 */         this.val$aModel.setCategoryFilter(this.val$catField.getText());
/* 146:    */       }
/* 147:147 */     });
/* 148:148 */     gridbag.setConstraints(catField, c);
/* 149:149 */     add(catField);
/* 150:    */     
/* 151:151 */     c.gridy += 1;
/* 152:152 */     JTextField ndcField = new JTextField("");
/* 153:153 */     ndcField.getDocument().addDocumentListener(new DocumentListener()
/* 154:    */     {
/* 155:    */       private final MyTableModel val$aModel;
/* 156:    */       private final JTextField val$ndcField;
/* 157:    */       
/* 158:    */       public void insertUpdate(DocumentEvent aEvent)
/* 159:    */       {
/* 160:155 */         this.val$aModel.setNDCFilter(this.val$ndcField.getText());
/* 161:    */       }
/* 162:    */       
/* 163:    */       public void removeUpdate(DocumentEvent aEvent)
/* 164:    */       {
/* 165:158 */         this.val$aModel.setNDCFilter(this.val$ndcField.getText());
/* 166:    */       }
/* 167:    */       
/* 168:    */       public void changedUpdate(DocumentEvent aEvent)
/* 169:    */       {
/* 170:161 */         this.val$aModel.setNDCFilter(this.val$ndcField.getText());
/* 171:    */       }
/* 172:163 */     });
/* 173:164 */     gridbag.setConstraints(ndcField, c);
/* 174:165 */     add(ndcField);
/* 175:    */     
/* 176:167 */     c.gridy += 1;
/* 177:168 */     JTextField msgField = new JTextField("");
/* 178:169 */     msgField.getDocument().addDocumentListener(new DocumentListener()
/* 179:    */     {
/* 180:    */       private final MyTableModel val$aModel;
/* 181:    */       private final JTextField val$msgField;
/* 182:    */       
/* 183:    */       public void insertUpdate(DocumentEvent aEvent)
/* 184:    */       {
/* 185:171 */         this.val$aModel.setMessageFilter(this.val$msgField.getText());
/* 186:    */       }
/* 187:    */       
/* 188:    */       public void removeUpdate(DocumentEvent aEvent)
/* 189:    */       {
/* 190:174 */         this.val$aModel.setMessageFilter(this.val$msgField.getText());
/* 191:    */       }
/* 192:    */       
/* 193:    */       public void changedUpdate(DocumentEvent aEvent)
/* 194:    */       {
/* 195:177 */         this.val$aModel.setMessageFilter(this.val$msgField.getText());
/* 196:    */       }
/* 197:181 */     });
/* 198:182 */     gridbag.setConstraints(msgField, c);
/* 199:183 */     add(msgField);
/* 200:    */     
/* 201:    */ 
/* 202:186 */     c.weightx = 0.0D;
/* 203:187 */     c.fill = 2;
/* 204:188 */     c.anchor = 13;
/* 205:189 */     c.gridx = 2;
/* 206:    */     
/* 207:191 */     c.gridy = 0;
/* 208:192 */     JButton exitButton = new JButton("Exit");
/* 209:193 */     exitButton.setMnemonic('x');
/* 210:194 */     exitButton.addActionListener(ExitAction.INSTANCE);
/* 211:195 */     gridbag.setConstraints(exitButton, c);
/* 212:196 */     add(exitButton);
/* 213:    */     
/* 214:198 */     c.gridy += 1;
/* 215:199 */     JButton clearButton = new JButton("Clear");
/* 216:200 */     clearButton.setMnemonic('c');
/* 217:201 */     clearButton.addActionListener(new ActionListener()
/* 218:    */     {
/* 219:    */       private final MyTableModel val$aModel;
/* 220:    */       
/* 221:    */       public void actionPerformed(ActionEvent aEvent)
/* 222:    */       {
/* 223:203 */         this.val$aModel.clear();
/* 224:    */       }
/* 225:205 */     });
/* 226:206 */     gridbag.setConstraints(clearButton, c);
/* 227:207 */     add(clearButton);
/* 228:    */     
/* 229:209 */     c.gridy += 1;
/* 230:210 */     JButton toggleButton = new JButton("Pause");
/* 231:211 */     toggleButton.setMnemonic('p');
/* 232:212 */     toggleButton.addActionListener(new ActionListener()
/* 233:    */     {
/* 234:    */       private final MyTableModel val$aModel;
/* 235:    */       private final JButton val$toggleButton;
/* 236:    */       
/* 237:    */       public void actionPerformed(ActionEvent aEvent)
/* 238:    */       {
/* 239:214 */         this.val$aModel.toggle();
/* 240:215 */         this.val$toggleButton.setText(this.val$aModel.isPaused() ? "Resume" : "Pause");
/* 241:    */       }
/* 242:218 */     });
/* 243:219 */     gridbag.setConstraints(toggleButton, c);
/* 244:220 */     add(toggleButton);
/* 245:    */   }
/* 246:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.ControlPanel
 * JD-Core Version:    0.7.0.1
 */