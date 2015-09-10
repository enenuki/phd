/*   1:    */ package org.apache.xalan.xsltc.cmdline.getopt;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.ListIterator;
/*   7:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   8:    */ 
/*   9:    */ public class GetOpt
/*  10:    */ {
/*  11:    */   public GetOpt(String[] args, String optString)
/*  12:    */   {
/*  13: 47 */     this.theOptions = new ArrayList();
/*  14: 48 */     int currOptIndex = 0;
/*  15: 49 */     this.theCmdArgs = new ArrayList();
/*  16: 50 */     this.theOptionMatcher = new OptionMatcher(optString);
/*  17: 52 */     for (int i = 0; i < args.length; i++)
/*  18:    */     {
/*  19: 53 */       String token = args[i];
/*  20: 54 */       int tokenLength = token.length();
/*  21: 55 */       if (token.equals("--"))
/*  22:    */       {
/*  23: 56 */         currOptIndex = i + 1;
/*  24: 57 */         break;
/*  25:    */       }
/*  26: 59 */       if ((token.startsWith("-")) && (tokenLength == 2))
/*  27:    */       {
/*  28: 61 */         this.theOptions.add(new Option(token.charAt(1)));
/*  29:    */       }
/*  30: 63 */       else if ((token.startsWith("-")) && (tokenLength > 2))
/*  31:    */       {
/*  32: 67 */         for (int j = 1; j < tokenLength; j++) {
/*  33: 68 */           this.theOptions.add(new Option(token.charAt(j)));
/*  34:    */         }
/*  35:    */       }
/*  36: 71 */       else if (!token.startsWith("-"))
/*  37:    */       {
/*  38: 74 */         if (this.theOptions.size() == 0)
/*  39:    */         {
/*  40: 75 */           currOptIndex = i;
/*  41: 76 */           break;
/*  42:    */         }
/*  43: 82 */         int indexoflast = 0;
/*  44: 83 */         indexoflast = this.theOptions.size() - 1;
/*  45: 84 */         Option op = (Option)this.theOptions.get(indexoflast);
/*  46: 85 */         char opLetter = op.getArgLetter();
/*  47: 86 */         if ((!op.hasArg()) && (this.theOptionMatcher.hasArg(opLetter)))
/*  48:    */         {
/*  49: 87 */           op.setArg(token);
/*  50:    */         }
/*  51:    */         else
/*  52:    */         {
/*  53: 95 */           currOptIndex = i;
/*  54: 96 */           break;
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:103 */     this.theOptionsIterator = this.theOptions.listIterator();
/*  59:106 */     for (int i = currOptIndex; i < args.length; i++)
/*  60:    */     {
/*  61:107 */       String token = args[i];
/*  62:108 */       this.theCmdArgs.add(token);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void printOptions()
/*  67:    */   {
/*  68:117 */     for (ListIterator it = this.theOptions.listIterator(); it.hasNext();)
/*  69:    */     {
/*  70:118 */       Option opt = (Option)it.next();
/*  71:119 */       System.out.print("OPT =" + opt.getArgLetter());
/*  72:120 */       String arg = opt.getArgument();
/*  73:121 */       if (arg != null) {
/*  74:122 */         System.out.print(" " + arg);
/*  75:    */       }
/*  76:124 */       System.out.println();
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getNextOption()
/*  81:    */     throws IllegalArgumentException, MissingOptArgException
/*  82:    */   {
/*  83:144 */     int retval = -1;
/*  84:145 */     if (this.theOptionsIterator.hasNext())
/*  85:    */     {
/*  86:146 */       this.theCurrentOption = ((Option)this.theOptionsIterator.next());
/*  87:147 */       char c = this.theCurrentOption.getArgLetter();
/*  88:148 */       boolean shouldHaveArg = this.theOptionMatcher.hasArg(c);
/*  89:149 */       String arg = this.theCurrentOption.getArgument();
/*  90:150 */       if (!this.theOptionMatcher.match(c))
/*  91:    */       {
/*  92:151 */         ErrorMsg msg = new ErrorMsg("ILLEGAL_CMDLINE_OPTION_ERR", new Character(c));
/*  93:    */         
/*  94:153 */         throw new IllegalArgumentException(msg.toString());
/*  95:    */       }
/*  96:155 */       if ((shouldHaveArg) && (arg == null))
/*  97:    */       {
/*  98:156 */         ErrorMsg msg = new ErrorMsg("CMDLINE_OPT_MISSING_ARG_ERR", new Character(c));
/*  99:    */         
/* 100:158 */         throw new MissingOptArgException(msg.toString());
/* 101:    */       }
/* 102:160 */       retval = c;
/* 103:    */     }
/* 104:162 */     return retval;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getOptionArg()
/* 108:    */   {
/* 109:172 */     String retval = null;
/* 110:173 */     String tmp = this.theCurrentOption.getArgument();
/* 111:174 */     char c = this.theCurrentOption.getArgLetter();
/* 112:175 */     if (this.theOptionMatcher.hasArg(c)) {
/* 113:176 */       retval = tmp;
/* 114:    */     }
/* 115:178 */     return retval;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String[] getCmdArgs()
/* 119:    */   {
/* 120:190 */     String[] retval = new String[this.theCmdArgs.size()];
/* 121:191 */     int i = 0;
/* 122:192 */     for (ListIterator it = this.theCmdArgs.listIterator(); it.hasNext();) {
/* 123:193 */       retval[(i++)] = ((String)it.next());
/* 124:    */     }
/* 125:195 */     return retval;
/* 126:    */   }
/* 127:    */   
/* 128:199 */   private Option theCurrentOption = null;
/* 129:    */   private ListIterator theOptionsIterator;
/* 130:201 */   private List theOptions = null;
/* 131:202 */   private List theCmdArgs = null;
/* 132:203 */   private OptionMatcher theOptionMatcher = null;
/* 133:    */   
/* 134:    */   class Option
/* 135:    */   {
/* 136:    */     private char theArgLetter;
/* 137:214 */     private String theArgument = null;
/* 138:    */     
/* 139:    */     public Option(char argLetter)
/* 140:    */     {
/* 141:215 */       this.theArgLetter = argLetter;
/* 142:    */     }
/* 143:    */     
/* 144:    */     public void setArg(String arg)
/* 145:    */     {
/* 146:217 */       this.theArgument = arg;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public boolean hasArg()
/* 150:    */     {
/* 151:219 */       return this.theArgument != null;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public char getArgLetter()
/* 155:    */     {
/* 156:220 */       return this.theArgLetter;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public String getArgument()
/* 160:    */     {
/* 161:221 */       return this.theArgument;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   class OptionMatcher
/* 166:    */   {
/* 167:    */     public OptionMatcher(String optString)
/* 168:    */     {
/* 169:230 */       this.theOptString = optString;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public boolean match(char c)
/* 173:    */     {
/* 174:233 */       boolean retval = false;
/* 175:234 */       if (this.theOptString.indexOf(c) != -1) {
/* 176:235 */         retval = true;
/* 177:    */       }
/* 178:237 */       return retval;
/* 179:    */     }
/* 180:    */     
/* 181:    */     public boolean hasArg(char c)
/* 182:    */     {
/* 183:240 */       boolean retval = false;
/* 184:241 */       int index = this.theOptString.indexOf(c) + 1;
/* 185:242 */       if (index == this.theOptString.length()) {
/* 186:244 */         retval = false;
/* 187:246 */       } else if (this.theOptString.charAt(index) == ':') {
/* 188:247 */         retval = true;
/* 189:    */       }
/* 190:249 */       return retval;
/* 191:    */     }
/* 192:    */     
/* 193:251 */     private String theOptString = null;
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.cmdline.getopt.GetOpt
 * JD-Core Version:    0.7.0.1
 */