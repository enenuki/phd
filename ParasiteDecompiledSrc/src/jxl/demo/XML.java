/*   1:    */ package jxl.demo;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.OutputStreamWriter;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ import jxl.Cell;
/*  10:    */ import jxl.CellType;
/*  11:    */ import jxl.Sheet;
/*  12:    */ import jxl.Workbook;
/*  13:    */ import jxl.format.Alignment;
/*  14:    */ import jxl.format.Border;
/*  15:    */ import jxl.format.BorderLineStyle;
/*  16:    */ import jxl.format.CellFormat;
/*  17:    */ import jxl.format.Colour;
/*  18:    */ import jxl.format.Font;
/*  19:    */ import jxl.format.Format;
/*  20:    */ import jxl.format.Orientation;
/*  21:    */ import jxl.format.Pattern;
/*  22:    */ import jxl.format.ScriptStyle;
/*  23:    */ import jxl.format.UnderlineStyle;
/*  24:    */ import jxl.format.VerticalAlignment;
/*  25:    */ 
/*  26:    */ public class XML
/*  27:    */ {
/*  28:    */   private OutputStream out;
/*  29:    */   private String encoding;
/*  30:    */   private Workbook workbook;
/*  31:    */   
/*  32:    */   public XML(Workbook w, OutputStream out, String enc, boolean f)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 75 */     this.encoding = enc;
/*  36: 76 */     this.workbook = w;
/*  37: 77 */     this.out = out;
/*  38: 79 */     if ((this.encoding == null) || (!this.encoding.equals("UnicodeBig"))) {
/*  39: 81 */       this.encoding = "UTF8";
/*  40:    */     }
/*  41: 84 */     if (f) {
/*  42: 86 */       writeFormattedXML();
/*  43:    */     } else {
/*  44: 90 */       writeXML();
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void writeXML()
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53:102 */       OutputStreamWriter osw = new OutputStreamWriter(this.out, this.encoding);
/*  54:103 */       BufferedWriter bw = new BufferedWriter(osw);
/*  55:    */       
/*  56:105 */       bw.write("<?xml version=\"1.0\" ?>");
/*  57:106 */       bw.newLine();
/*  58:107 */       bw.write("<!DOCTYPE workbook SYSTEM \"workbook.dtd\">");
/*  59:108 */       bw.newLine();
/*  60:109 */       bw.newLine();
/*  61:110 */       bw.write("<workbook>");
/*  62:111 */       bw.newLine();
/*  63:112 */       for (int sheet = 0; sheet < this.workbook.getNumberOfSheets(); sheet++)
/*  64:    */       {
/*  65:114 */         Sheet s = this.workbook.getSheet(sheet);
/*  66:    */         
/*  67:116 */         bw.write("  <sheet>");
/*  68:117 */         bw.newLine();
/*  69:118 */         bw.write("    <name><![CDATA[" + s.getName() + "]]></name>");
/*  70:119 */         bw.newLine();
/*  71:    */         
/*  72:121 */         Cell[] row = null;
/*  73:123 */         for (int i = 0; i < s.getRows(); i++)
/*  74:    */         {
/*  75:125 */           bw.write("    <row number=\"" + i + "\">");
/*  76:126 */           bw.newLine();
/*  77:127 */           row = s.getRow(i);
/*  78:129 */           for (int j = 0; j < row.length; j++) {
/*  79:131 */             if (row[j].getType() != CellType.EMPTY)
/*  80:    */             {
/*  81:133 */               bw.write("      <col number=\"" + j + "\">");
/*  82:134 */               bw.write("<![CDATA[" + row[j].getContents() + "]]>");
/*  83:135 */               bw.write("</col>");
/*  84:136 */               bw.newLine();
/*  85:    */             }
/*  86:    */           }
/*  87:139 */           bw.write("    </row>");
/*  88:140 */           bw.newLine();
/*  89:    */         }
/*  90:142 */         bw.write("  </sheet>");
/*  91:143 */         bw.newLine();
/*  92:    */       }
/*  93:146 */       bw.write("</workbook>");
/*  94:147 */       bw.newLine();
/*  95:    */       
/*  96:149 */       bw.flush();
/*  97:150 */       bw.close();
/*  98:    */     }
/*  99:    */     catch (UnsupportedEncodingException e)
/* 100:    */     {
/* 101:154 */       System.err.println(e.toString());
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void writeFormattedXML()
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:165 */       OutputStreamWriter osw = new OutputStreamWriter(this.out, this.encoding);
/* 111:166 */       BufferedWriter bw = new BufferedWriter(osw);
/* 112:    */       
/* 113:168 */       bw.write("<?xml version=\"1.0\" ?>");
/* 114:169 */       bw.newLine();
/* 115:170 */       bw.write("<!DOCTYPE workbook SYSTEM \"formatworkbook.dtd\">");
/* 116:171 */       bw.newLine();
/* 117:172 */       bw.newLine();
/* 118:173 */       bw.write("<workbook>");
/* 119:174 */       bw.newLine();
/* 120:175 */       for (int sheet = 0; sheet < this.workbook.getNumberOfSheets(); sheet++)
/* 121:    */       {
/* 122:177 */         Sheet s = this.workbook.getSheet(sheet);
/* 123:    */         
/* 124:179 */         bw.write("  <sheet>");
/* 125:180 */         bw.newLine();
/* 126:181 */         bw.write("    <name><![CDATA[" + s.getName() + "]]></name>");
/* 127:182 */         bw.newLine();
/* 128:    */         
/* 129:184 */         Cell[] row = null;
/* 130:185 */         CellFormat format = null;
/* 131:186 */         Font font = null;
/* 132:188 */         for (int i = 0; i < s.getRows(); i++)
/* 133:    */         {
/* 134:190 */           bw.write("    <row number=\"" + i + "\">");
/* 135:191 */           bw.newLine();
/* 136:192 */           row = s.getRow(i);
/* 137:194 */           for (int j = 0; j < row.length; j++) {
/* 138:197 */             if ((row[j].getType() != CellType.EMPTY) || (row[j].getCellFormat() != null))
/* 139:    */             {
/* 140:200 */               format = row[j].getCellFormat();
/* 141:201 */               bw.write("      <col number=\"" + j + "\">");
/* 142:202 */               bw.newLine();
/* 143:203 */               bw.write("        <data>");
/* 144:204 */               bw.write("<![CDATA[" + row[j].getContents() + "]]>");
/* 145:205 */               bw.write("</data>");
/* 146:206 */               bw.newLine();
/* 147:208 */               if (row[j].getCellFormat() != null)
/* 148:    */               {
/* 149:210 */                 bw.write("        <format wrap=\"" + format.getWrap() + "\"");
/* 150:211 */                 bw.newLine();
/* 151:212 */                 bw.write("                align=\"" + format.getAlignment().getDescription() + "\"");
/* 152:    */                 
/* 153:214 */                 bw.newLine();
/* 154:215 */                 bw.write("                valign=\"" + format.getVerticalAlignment().getDescription() + "\"");
/* 155:    */                 
/* 156:217 */                 bw.newLine();
/* 157:218 */                 bw.write("                orientation=\"" + format.getOrientation().getDescription() + "\"");
/* 158:    */                 
/* 159:220 */                 bw.write(">");
/* 160:221 */                 bw.newLine();
/* 161:    */                 
/* 162:    */ 
/* 163:224 */                 font = format.getFont();
/* 164:225 */                 bw.write("          <font name=\"" + font.getName() + "\"");
/* 165:226 */                 bw.newLine();
/* 166:227 */                 bw.write("                point_size=\"" + font.getPointSize() + "\"");
/* 167:    */                 
/* 168:229 */                 bw.newLine();
/* 169:230 */                 bw.write("                bold_weight=\"" + font.getBoldWeight() + "\"");
/* 170:    */                 
/* 171:232 */                 bw.newLine();
/* 172:233 */                 bw.write("                italic=\"" + font.isItalic() + "\"");
/* 173:234 */                 bw.newLine();
/* 174:235 */                 bw.write("                underline=\"" + font.getUnderlineStyle().getDescription() + "\"");
/* 175:    */                 
/* 176:237 */                 bw.newLine();
/* 177:238 */                 bw.write("                colour=\"" + font.getColour().getDescription() + "\"");
/* 178:    */                 
/* 179:240 */                 bw.newLine();
/* 180:241 */                 bw.write("                script=\"" + font.getScriptStyle().getDescription() + "\"");
/* 181:    */                 
/* 182:243 */                 bw.write(" />");
/* 183:244 */                 bw.newLine();
/* 184:248 */                 if ((format.getBackgroundColour() != Colour.DEFAULT_BACKGROUND) || (format.getPattern() != Pattern.NONE))
/* 185:    */                 {
/* 186:251 */                   bw.write("          <background colour=\"" + format.getBackgroundColour().getDescription() + "\"");
/* 187:    */                   
/* 188:253 */                   bw.newLine();
/* 189:254 */                   bw.write("                      pattern=\"" + format.getPattern().getDescription() + "\"");
/* 190:    */                   
/* 191:256 */                   bw.write(" />");
/* 192:257 */                   bw.newLine();
/* 193:    */                 }
/* 194:262 */                 if ((format.getBorder(Border.TOP) != BorderLineStyle.NONE) || (format.getBorder(Border.BOTTOM) != BorderLineStyle.NONE) || (format.getBorder(Border.LEFT) != BorderLineStyle.NONE) || (format.getBorder(Border.RIGHT) != BorderLineStyle.NONE))
/* 195:    */                 {
/* 196:268 */                   bw.write("          <border top=\"" + format.getBorder(Border.TOP).getDescription() + "\"");
/* 197:    */                   
/* 198:270 */                   bw.newLine();
/* 199:271 */                   bw.write("                  bottom=\"" + format.getBorder(Border.BOTTOM).getDescription() + "\"");
/* 200:    */                   
/* 201:    */ 
/* 202:274 */                   bw.newLine();
/* 203:275 */                   bw.write("                  left=\"" + format.getBorder(Border.LEFT).getDescription() + "\"");
/* 204:    */                   
/* 205:277 */                   bw.newLine();
/* 206:278 */                   bw.write("                  right=\"" + format.getBorder(Border.RIGHT).getDescription() + "\"");
/* 207:    */                   
/* 208:280 */                   bw.write(" />");
/* 209:281 */                   bw.newLine();
/* 210:    */                 }
/* 211:285 */                 if (!format.getFormat().getFormatString().equals(""))
/* 212:    */                 {
/* 213:287 */                   bw.write("          <format_string string=\"");
/* 214:288 */                   bw.write(format.getFormat().getFormatString());
/* 215:289 */                   bw.write("\" />");
/* 216:290 */                   bw.newLine();
/* 217:    */                 }
/* 218:293 */                 bw.write("        </format>");
/* 219:294 */                 bw.newLine();
/* 220:    */               }
/* 221:297 */               bw.write("      </col>");
/* 222:298 */               bw.newLine();
/* 223:    */             }
/* 224:    */           }
/* 225:301 */           bw.write("    </row>");
/* 226:302 */           bw.newLine();
/* 227:    */         }
/* 228:304 */         bw.write("  </sheet>");
/* 229:305 */         bw.newLine();
/* 230:    */       }
/* 231:308 */       bw.write("</workbook>");
/* 232:309 */       bw.newLine();
/* 233:    */       
/* 234:311 */       bw.flush();
/* 235:312 */       bw.close();
/* 236:    */     }
/* 237:    */     catch (UnsupportedEncodingException e)
/* 238:    */     {
/* 239:316 */       System.err.println(e.toString());
/* 240:    */     }
/* 241:    */   }
/* 242:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.demo.XML
 * JD-Core Version:    0.7.0.1
 */