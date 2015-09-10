/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ import jxl.WorkbookSettings;
/*   5:    */ import jxl.biff.IntegerHelper;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ class VariableArgFunction
/*   9:    */   extends Operator
/*  10:    */   implements ParsedThing
/*  11:    */ {
/*  12: 38 */   private static Logger logger = Logger.getLogger(VariableArgFunction.class);
/*  13:    */   private Function function;
/*  14:    */   private int arguments;
/*  15:    */   private boolean readFromSheet;
/*  16:    */   private WorkbookSettings settings;
/*  17:    */   
/*  18:    */   public VariableArgFunction(WorkbookSettings ws)
/*  19:    */   {
/*  20: 66 */     this.readFromSheet = true;
/*  21: 67 */     this.settings = ws;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public VariableArgFunction(Function f, int a, WorkbookSettings ws)
/*  25:    */   {
/*  26: 78 */     this.function = f;
/*  27: 79 */     this.arguments = a;
/*  28: 80 */     this.readFromSheet = false;
/*  29: 81 */     this.settings = ws;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int read(byte[] data, int pos)
/*  33:    */     throws FormulaException
/*  34:    */   {
/*  35: 94 */     this.arguments = data[pos];
/*  36: 95 */     int index = IntegerHelper.getInt(data[(pos + 1)], data[(pos + 2)]);
/*  37: 96 */     this.function = Function.getFunction(index);
/*  38: 98 */     if (this.function == Function.UNKNOWN) {
/*  39:100 */       throw new FormulaException(FormulaException.UNRECOGNIZED_FUNCTION, index);
/*  40:    */     }
/*  41:104 */     return 3;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void getOperands(Stack s)
/*  45:    */   {
/*  46:113 */     ParseItem[] items = new ParseItem[this.arguments];
/*  47:115 */     for (int i = this.arguments - 1; i >= 0; i--)
/*  48:    */     {
/*  49:117 */       ParseItem pi = (ParseItem)s.pop();
/*  50:    */       
/*  51:119 */       items[i] = pi;
/*  52:    */     }
/*  53:122 */     for (int i = 0; i < this.arguments; i++) {
/*  54:124 */       add(items[i]);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void getString(StringBuffer buf)
/*  59:    */   {
/*  60:130 */     buf.append(this.function.getName(this.settings));
/*  61:131 */     buf.append('(');
/*  62:133 */     if (this.arguments > 0)
/*  63:    */     {
/*  64:135 */       ParseItem[] operands = getOperands();
/*  65:136 */       if (this.readFromSheet)
/*  66:    */       {
/*  67:139 */         operands[0].getString(buf);
/*  68:141 */         for (int i = 1; i < this.arguments; i++)
/*  69:    */         {
/*  70:143 */           buf.append(',');
/*  71:144 */           operands[i].getString(buf);
/*  72:    */         }
/*  73:    */       }
/*  74:    */       else
/*  75:    */       {
/*  76:151 */         operands[(this.arguments - 1)].getString(buf);
/*  77:153 */         for (int i = this.arguments - 2; i >= 0; i--)
/*  78:    */         {
/*  79:155 */           buf.append(',');
/*  80:156 */           operands[i].getString(buf);
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:161 */     buf.append(')');
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/*  88:    */   {
/*  89:173 */     ParseItem[] operands = getOperands();
/*  90:175 */     for (int i = 0; i < operands.length; i++) {
/*  91:177 */       operands[i].adjustRelativeCellReferences(colAdjust, rowAdjust);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   void columnInserted(int sheetIndex, int col, boolean currentSheet)
/*  96:    */   {
/*  97:193 */     ParseItem[] operands = getOperands();
/*  98:194 */     for (int i = 0; i < operands.length; i++) {
/*  99:196 */       operands[i].columnInserted(sheetIndex, col, currentSheet);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/* 104:    */   {
/* 105:212 */     ParseItem[] operands = getOperands();
/* 106:213 */     for (int i = 0; i < operands.length; i++) {
/* 107:215 */       operands[i].columnRemoved(sheetIndex, col, currentSheet);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   void rowInserted(int sheetIndex, int row, boolean currentSheet)
/* 112:    */   {
/* 113:231 */     ParseItem[] operands = getOperands();
/* 114:232 */     for (int i = 0; i < operands.length; i++) {
/* 115:234 */       operands[i].rowInserted(sheetIndex, row, currentSheet);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   void rowRemoved(int sheetIndex, int row, boolean currentSheet)
/* 120:    */   {
/* 121:250 */     ParseItem[] operands = getOperands();
/* 122:251 */     for (int i = 0; i < operands.length; i++) {
/* 123:253 */       operands[i].rowRemoved(sheetIndex, row, currentSheet);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   void handleImportedCellReferences()
/* 128:    */   {
/* 129:264 */     ParseItem[] operands = getOperands();
/* 130:265 */     for (int i = 0; i < operands.length; i++) {
/* 131:267 */       operands[i].handleImportedCellReferences();
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   Function getFunction()
/* 136:    */   {
/* 137:276 */     return this.function;
/* 138:    */   }
/* 139:    */   
/* 140:    */   byte[] getBytes()
/* 141:    */   {
/* 142:286 */     handleSpecialCases();
/* 143:    */     
/* 144:    */ 
/* 145:289 */     ParseItem[] operands = getOperands();
/* 146:290 */     byte[] data = new byte[0];
/* 147:292 */     for (int i = 0; i < operands.length; i++)
/* 148:    */     {
/* 149:294 */       byte[] opdata = operands[i].getBytes();
/* 150:    */       
/* 151:    */ 
/* 152:297 */       byte[] newdata = new byte[data.length + opdata.length];
/* 153:298 */       System.arraycopy(data, 0, newdata, 0, data.length);
/* 154:299 */       System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
/* 155:300 */       data = newdata;
/* 156:    */     }
/* 157:304 */     byte[] newdata = new byte[data.length + 4];
/* 158:305 */     System.arraycopy(data, 0, newdata, 0, data.length);
/* 159:306 */     newdata[data.length] = (!useAlternateCode() ? Token.FUNCTIONVARARG.getCode() : Token.FUNCTIONVARARG.getCode2());
/* 160:    */     
/* 161:308 */     newdata[(data.length + 1)] = ((byte)this.arguments);
/* 162:309 */     IntegerHelper.getTwoBytes(this.function.getCode(), newdata, data.length + 2);
/* 163:    */     
/* 164:311 */     return newdata;
/* 165:    */   }
/* 166:    */   
/* 167:    */   int getPrecedence()
/* 168:    */   {
/* 169:322 */     return 3;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private void handleSpecialCases()
/* 173:    */   {
/* 174:332 */     if (this.function == Function.SUMPRODUCT)
/* 175:    */     {
/* 176:335 */       ParseItem[] operands = getOperands();
/* 177:337 */       for (int i = operands.length - 1; i >= 0; i--) {
/* 178:339 */         if ((operands[i] instanceof Area)) {
/* 179:341 */           operands[i].setAlternateCode();
/* 180:    */         }
/* 181:    */       }
/* 182:    */     }
/* 183:    */   }
/* 184:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.VariableArgFunction
 * JD-Core Version:    0.7.0.1
 */