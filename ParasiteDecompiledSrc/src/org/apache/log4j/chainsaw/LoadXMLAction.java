/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import javax.swing.AbstractAction;
/*   8:    */ import javax.swing.JFileChooser;
/*   9:    */ import javax.swing.JFrame;
/*  10:    */ import javax.swing.JOptionPane;
/*  11:    */ import javax.xml.parsers.ParserConfigurationException;
/*  12:    */ import javax.xml.parsers.SAXParser;
/*  13:    */ import javax.xml.parsers.SAXParserFactory;
/*  14:    */ import org.apache.log4j.Category;
/*  15:    */ import org.apache.log4j.Logger;
/*  16:    */ import org.xml.sax.InputSource;
/*  17:    */ import org.xml.sax.SAXException;
/*  18:    */ import org.xml.sax.XMLReader;
/*  19:    */ 
/*  20:    */ class LoadXMLAction
/*  21:    */   extends AbstractAction
/*  22:    */ {
/*  23: 44 */   private static final Logger LOG = Logger.getLogger(LoadXMLAction.class);
/*  24:    */   private final JFrame mParent;
/*  25: 53 */   private final JFileChooser mChooser = new JFileChooser();
/*  26:    */   private final XMLReader mParser;
/*  27:    */   private final XMLFileHandler mHandler;
/*  28:    */   
/*  29:    */   LoadXMLAction(JFrame aParent, MyTableModel aModel)
/*  30:    */     throws SAXException, ParserConfigurationException
/*  31:    */   {
/*  32: 55 */     this.mChooser.setMultiSelectionEnabled(false);
/*  33: 56 */     this.mChooser.setFileSelectionMode(0);
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 76 */     this.mParent = aParent;
/*  54: 77 */     this.mHandler = new XMLFileHandler(aModel);
/*  55: 78 */     this.mParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
/*  56: 79 */     this.mParser.setContentHandler(this.mHandler);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void actionPerformed(ActionEvent aIgnore)
/*  60:    */   {
/*  61: 87 */     LOG.info("load file called");
/*  62: 88 */     if (this.mChooser.showOpenDialog(this.mParent) == 0)
/*  63:    */     {
/*  64: 89 */       LOG.info("Need to load a file");
/*  65: 90 */       File chosen = this.mChooser.getSelectedFile();
/*  66: 91 */       LOG.info("loading the contents of " + chosen.getAbsolutePath());
/*  67:    */       try
/*  68:    */       {
/*  69: 93 */         int num = loadFile(chosen.getAbsolutePath());
/*  70: 94 */         JOptionPane.showMessageDialog(this.mParent, "Loaded " + num + " events.", "CHAINSAW", 1);
/*  71:    */       }
/*  72:    */       catch (Exception e)
/*  73:    */       {
/*  74:100 */         LOG.warn("caught an exception loading the file", e);
/*  75:101 */         JOptionPane.showMessageDialog(this.mParent, "Error parsing file - " + e.getMessage(), "CHAINSAW", 0);
/*  76:    */       }
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   private int loadFile(String aFile)
/*  81:    */     throws SAXException, IOException
/*  82:    */   {
/*  83:121 */     synchronized (this.mParser)
/*  84:    */     {
/*  85:123 */       StringBuffer buf = new StringBuffer();
/*  86:124 */       buf.append("<?xml version=\"1.0\" standalone=\"yes\"?>\n");
/*  87:125 */       buf.append("<!DOCTYPE log4j:eventSet ");
/*  88:126 */       buf.append("[<!ENTITY data SYSTEM \"file:///");
/*  89:127 */       buf.append(aFile);
/*  90:128 */       buf.append("\">]>\n");
/*  91:129 */       buf.append("<log4j:eventSet xmlns:log4j=\"Claira\">\n");
/*  92:130 */       buf.append("&data;\n");
/*  93:131 */       buf.append("</log4j:eventSet>\n");
/*  94:    */       
/*  95:133 */       InputSource is = new InputSource(new StringReader(buf.toString()));
/*  96:    */       
/*  97:135 */       this.mParser.parse(is);
/*  98:136 */       return this.mHandler.getNumEvents();
/*  99:    */     }
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.LoadXMLAction
 * JD-Core Version:    0.7.0.1
 */