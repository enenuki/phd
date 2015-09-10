/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.text.Format;
/*   6:    */ import java.text.MessageFormat;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.EventObject;
/*   9:    */ import javax.swing.BorderFactory;
/*  10:    */ import javax.swing.JComponent;
/*  11:    */ import javax.swing.JEditorPane;
/*  12:    */ import javax.swing.JPanel;
/*  13:    */ import javax.swing.JScrollPane;
/*  14:    */ import javax.swing.JTable;
/*  15:    */ import javax.swing.ListSelectionModel;
/*  16:    */ import javax.swing.event.ListSelectionEvent;
/*  17:    */ import javax.swing.event.ListSelectionListener;
/*  18:    */ import javax.swing.text.JTextComponent;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ class DetailPanel
/*  22:    */   extends JPanel
/*  23:    */   implements ListSelectionListener
/*  24:    */ {
/*  25: 42 */   private static final Logger LOG = Logger.getLogger(DetailPanel.class);
/*  26: 46 */   private static final MessageFormat FORMATTER = new MessageFormat("<b>Time:</b> <code>{0,time,medium}</code>&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code><br><b>Logger:</b> <code>{4}</code><br><b>Location:</b> <code>{5}</code><br><b>Message:</b><pre>{6}</pre><b>Throwable:</b><pre>{7}</pre>");
/*  27:    */   private final MyTableModel mModel;
/*  28:    */   private final JEditorPane mDetails;
/*  29:    */   
/*  30:    */   DetailPanel(JTable aTable, MyTableModel aModel)
/*  31:    */   {
/*  32: 70 */     this.mModel = aModel;
/*  33: 71 */     setLayout(new BorderLayout());
/*  34: 72 */     setBorder(BorderFactory.createTitledBorder("Details: "));
/*  35:    */     
/*  36: 74 */     this.mDetails = new JEditorPane();
/*  37: 75 */     this.mDetails.setEditable(false);
/*  38: 76 */     this.mDetails.setContentType("text/html");
/*  39: 77 */     add(new JScrollPane(this.mDetails), "Center");
/*  40:    */     
/*  41: 79 */     ListSelectionModel rowSM = aTable.getSelectionModel();
/*  42: 80 */     rowSM.addListSelectionListener(this);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void valueChanged(ListSelectionEvent aEvent)
/*  46:    */   {
/*  47: 86 */     if (aEvent.getValueIsAdjusting()) {
/*  48: 87 */       return;
/*  49:    */     }
/*  50: 90 */     ListSelectionModel lsm = (ListSelectionModel)aEvent.getSource();
/*  51: 91 */     if (lsm.isSelectionEmpty())
/*  52:    */     {
/*  53: 92 */       this.mDetails.setText("Nothing selected");
/*  54:    */     }
/*  55:    */     else
/*  56:    */     {
/*  57: 94 */       int selectedRow = lsm.getMinSelectionIndex();
/*  58: 95 */       EventDetails e = this.mModel.getEventDetails(selectedRow);
/*  59: 96 */       Object[] args = { new Date(e.getTimeStamp()), e.getPriority(), escape(e.getThreadName()), escape(e.getNDC()), escape(e.getCategoryName()), escape(e.getLocationDetails()), escape(e.getMessage()), escape(getThrowableStrRep(e)) };
/*  60:    */       
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:107 */       this.mDetails.setText(FORMATTER.format(args));
/*  71:108 */       this.mDetails.setCaretPosition(0);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static String getThrowableStrRep(EventDetails aEvent)
/*  76:    */   {
/*  77:123 */     String[] strs = aEvent.getThrowableStrRep();
/*  78:124 */     if (strs == null) {
/*  79:125 */       return null;
/*  80:    */     }
/*  81:128 */     StringBuffer sb = new StringBuffer();
/*  82:129 */     for (int i = 0; i < strs.length; i++) {
/*  83:130 */       sb.append(strs[i]).append("\n");
/*  84:    */     }
/*  85:133 */     return sb.toString();
/*  86:    */   }
/*  87:    */   
/*  88:    */   private String escape(String aStr)
/*  89:    */   {
/*  90:143 */     if (aStr == null) {
/*  91:144 */       return null;
/*  92:    */     }
/*  93:147 */     StringBuffer buf = new StringBuffer();
/*  94:148 */     for (int i = 0; i < aStr.length(); i++)
/*  95:    */     {
/*  96:149 */       char c = aStr.charAt(i);
/*  97:150 */       switch (c)
/*  98:    */       {
/*  99:    */       case '<': 
/* 100:152 */         buf.append("&lt;");
/* 101:153 */         break;
/* 102:    */       case '>': 
/* 103:155 */         buf.append("&gt;");
/* 104:156 */         break;
/* 105:    */       case '"': 
/* 106:158 */         buf.append("&quot;");
/* 107:159 */         break;
/* 108:    */       case '&': 
/* 109:161 */         buf.append("&amp;");
/* 110:162 */         break;
/* 111:    */       default: 
/* 112:164 */         buf.append(c);
/* 113:    */       }
/* 114:    */     }
/* 115:168 */     return buf.toString();
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.DetailPanel
 * JD-Core Version:    0.7.0.1
 */