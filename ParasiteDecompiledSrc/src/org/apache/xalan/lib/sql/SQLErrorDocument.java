/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.sql.SQLException;
/*   4:    */ import java.sql.SQLWarning;
/*   5:    */ import org.apache.xml.dtm.DTMManager;
/*   6:    */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   7:    */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   8:    */ 
/*   9:    */ public class SQLErrorDocument
/*  10:    */   extends DTMDocument
/*  11:    */ {
/*  12:    */   private static final String S_EXT_ERROR = "ext-error";
/*  13:    */   private static final String S_SQL_ERROR = "sql-error";
/*  14:    */   private static final String S_MESSAGE = "message";
/*  15:    */   private static final String S_CODE = "code";
/*  16:    */   private static final String S_STATE = "state";
/*  17:    */   private static final String S_SQL_WARNING = "sql-warning";
/*  18: 74 */   private int m_ErrorExt_TypeID = -1;
/*  19: 77 */   private int m_Message_TypeID = -1;
/*  20: 80 */   private int m_Code_TypeID = -1;
/*  21: 84 */   private int m_State_TypeID = -1;
/*  22: 88 */   private int m_SQLWarning_TypeID = -1;
/*  23: 92 */   private int m_SQLError_TypeID = -1;
/*  24: 96 */   private int m_rootID = -1;
/*  25: 99 */   private int m_extErrorID = -1;
/*  26:102 */   private int m_MainMessageID = -1;
/*  27:    */   
/*  28:    */   public SQLErrorDocument(DTMManager mgr, int ident, SQLException error)
/*  29:    */   {
/*  30:113 */     super(mgr, ident);
/*  31:    */     
/*  32:115 */     createExpandedNameTable();
/*  33:116 */     buildBasicStructure(error);
/*  34:    */     
/*  35:118 */     int sqlError = addElement(2, this.m_SQLError_TypeID, this.m_extErrorID, this.m_MainMessageID);
/*  36:119 */     int element = -1;
/*  37:    */     
/*  38:121 */     element = addElementWithData(new Integer(error.getErrorCode()), 3, this.m_Code_TypeID, sqlError, element);
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:125 */     element = addElementWithData(error.getLocalizedMessage(), 3, this.m_Message_TypeID, sqlError, element);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public SQLErrorDocument(DTMManager mgr, int ident, Exception error)
/*  46:    */   {
/*  47:141 */     super(mgr, ident);
/*  48:142 */     createExpandedNameTable();
/*  49:143 */     buildBasicStructure(error);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public SQLErrorDocument(DTMManager mgr, int ident, Exception error, SQLWarning warning, boolean full)
/*  53:    */   {
/*  54:154 */     super(mgr, ident);
/*  55:155 */     createExpandedNameTable();
/*  56:156 */     buildBasicStructure(error);
/*  57:    */     
/*  58:158 */     SQLException se = null;
/*  59:159 */     int prev = this.m_MainMessageID;
/*  60:160 */     boolean inWarnings = false;
/*  61:162 */     if ((error != null) && ((error instanceof SQLException)))
/*  62:    */     {
/*  63:163 */       se = (SQLException)error;
/*  64:    */     }
/*  65:164 */     else if ((full) && (warning != null))
/*  66:    */     {
/*  67:166 */       se = warning;
/*  68:167 */       inWarnings = true;
/*  69:    */     }
/*  70:170 */     while (se != null)
/*  71:    */     {
/*  72:172 */       int sqlError = addElement(2, inWarnings ? this.m_SQLWarning_TypeID : this.m_SQLError_TypeID, this.m_extErrorID, prev);
/*  73:173 */       prev = sqlError;
/*  74:174 */       int element = -1;
/*  75:    */       
/*  76:176 */       element = addElementWithData(new Integer(se.getErrorCode()), 3, this.m_Code_TypeID, sqlError, element);
/*  77:    */       
/*  78:    */ 
/*  79:    */ 
/*  80:180 */       element = addElementWithData(se.getLocalizedMessage(), 3, this.m_Message_TypeID, sqlError, element);
/*  81:184 */       if (full)
/*  82:    */       {
/*  83:186 */         String state = se.getSQLState();
/*  84:187 */         if ((state != null) && (state.length() > 0)) {
/*  85:188 */           element = addElementWithData(state, 3, this.m_State_TypeID, sqlError, element);
/*  86:    */         }
/*  87:192 */         if (inWarnings) {
/*  88:193 */           se = ((SQLWarning)se).getNextWarning();
/*  89:    */         } else {
/*  90:195 */           se = se.getNextException();
/*  91:    */         }
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:198 */         se = null;
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void buildBasicStructure(Exception e)
/* 101:    */   {
/* 102:209 */     this.m_rootID = addElement(0, this.m_Document_TypeID, -1, -1);
/* 103:210 */     this.m_extErrorID = addElement(1, this.m_ErrorExt_TypeID, this.m_rootID, -1);
/* 104:211 */     this.m_MainMessageID = addElementWithData(e != null ? e.getLocalizedMessage() : "SQLWarning", 2, this.m_Message_TypeID, this.m_extErrorID, -1);
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void createExpandedNameTable()
/* 108:    */   {
/* 109:223 */     super.createExpandedNameTable();
/* 110:    */     
/* 111:225 */     this.m_ErrorExt_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "ext-error", 1);
/* 112:    */     
/* 113:    */ 
/* 114:228 */     this.m_SQLError_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "sql-error", 1);
/* 115:    */     
/* 116:    */ 
/* 117:231 */     this.m_Message_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "message", 1);
/* 118:    */     
/* 119:    */ 
/* 120:234 */     this.m_Code_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "code", 1);
/* 121:    */     
/* 122:    */ 
/* 123:237 */     this.m_State_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "state", 1);
/* 124:    */     
/* 125:    */ 
/* 126:240 */     this.m_SQLWarning_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "sql-warning", 1);
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.SQLErrorDocument
 * JD-Core Version:    0.7.0.1
 */