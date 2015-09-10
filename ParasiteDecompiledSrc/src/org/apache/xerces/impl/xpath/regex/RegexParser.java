package org.apache.xerces.impl.xpath.regex;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

class RegexParser
{
  static final int T_CHAR = 0;
  static final int T_EOF = 1;
  static final int T_OR = 2;
  static final int T_STAR = 3;
  static final int T_PLUS = 4;
  static final int T_QUESTION = 5;
  static final int T_LPAREN = 6;
  static final int T_RPAREN = 7;
  static final int T_DOT = 8;
  static final int T_LBRACKET = 9;
  static final int T_BACKSOLIDUS = 10;
  static final int T_CARET = 11;
  static final int T_DOLLAR = 12;
  static final int T_LPAREN2 = 13;
  static final int T_LOOKAHEAD = 14;
  static final int T_NEGATIVELOOKAHEAD = 15;
  static final int T_LOOKBEHIND = 16;
  static final int T_NEGATIVELOOKBEHIND = 17;
  static final int T_INDEPENDENT = 18;
  static final int T_SET_OPERATIONS = 19;
  static final int T_POSIX_CHARCLASS_START = 20;
  static final int T_COMMENT = 21;
  static final int T_MODIFIERS = 22;
  static final int T_CONDITION = 23;
  static final int T_XMLSCHEMA_CC_SUBTRACTION = 24;
  int offset;
  String regex;
  int regexlen;
  int options;
  ResourceBundle resources;
  int chardata;
  int nexttoken;
  protected static final int S_NORMAL = 0;
  protected static final int S_INBRACKETS = 1;
  protected static final int S_INXBRACKETS = 2;
  int context = 0;
  int parennumber = 1;
  boolean hasBackReferences;
  Vector references = null;
  
  public RegexParser()
  {
    setLocale(Locale.getDefault());
  }
  
  public RegexParser(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    try
    {
      this.resources = ResourceBundle.getBundle("org.apache.xerces.impl.xpath.regex.message", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      throw new RuntimeException("Installation Problem???  Couldn't load messages: " + localMissingResourceException.getMessage());
    }
  }
  
  final ParseException ex(String paramString, int paramInt)
  {
    return new ParseException(this.resources.getString(paramString), paramInt);
  }
  
  private final boolean isSet(int paramInt)
  {
    return (this.options & paramInt) == paramInt;
  }
  
  synchronized Token parse(String paramString, int paramInt)
    throws ParseException
  {
    this.options = paramInt;
    this.offset = 0;
    setContext(0);
    this.parennumber = 1;
    this.hasBackReferences = false;
    this.regex = paramString;
    if (isSet(16)) {
      this.regex = REUtil.stripExtendedComment(this.regex);
    }
    this.regexlen = this.regex.length();
    next();
    Token localToken = parseRegex();
    if (this.offset != this.regexlen) {
      throw ex("parser.parse.1", this.offset);
    }
    if (this.references != null)
    {
      for (int i = 0; i < this.references.size(); i++)
      {
        ReferencePosition localReferencePosition = (ReferencePosition)this.references.elementAt(i);
        if (this.parennumber <= localReferencePosition.refNumber) {
          throw ex("parser.parse.2", localReferencePosition.position);
        }
      }
      this.references.removeAllElements();
    }
    return localToken;
  }
  
  protected final void setContext(int paramInt)
  {
    this.context = paramInt;
  }
  
  final int read()
  {
    return this.nexttoken;
  }
  
  final void next()
  {
    if (this.offset >= this.regexlen)
    {
      this.chardata = -1;
      this.nexttoken = 1;
      return;
    }
    int j = this.regex.charAt(this.offset++);
    this.chardata = j;
    int i;
    if (this.context == 1)
    {
      switch (j)
      {
      case 92: 
        i = 10;
        if (this.offset >= this.regexlen) {
          throw ex("parser.next.1", this.offset - 1);
        }
        this.chardata = this.regex.charAt(this.offset++);
        break;
      case 45: 
        if ((isSet(512)) && (this.offset < this.regexlen) && (this.regex.charAt(this.offset) == '['))
        {
          this.offset += 1;
          i = 24;
        }
        else
        {
          i = 0;
        }
        break;
      case 91: 
        if ((!isSet(512)) && (this.offset < this.regexlen) && (this.regex.charAt(this.offset) == ':'))
        {
          this.offset += 1;
          i = 20;
        }
        break;
      }
      if ((REUtil.isHighSurrogate(j)) && (this.offset < this.regexlen))
      {
        int k = this.regex.charAt(this.offset);
        if (REUtil.isLowSurrogate(k))
        {
          this.chardata = REUtil.composeFromSurrogates(j, k);
          this.offset += 1;
        }
      }
      i = 0;
      this.nexttoken = i;
      return;
    }
    switch (j)
    {
    case 124: 
      i = 2;
      break;
    case 42: 
      i = 3;
      break;
    case 43: 
      i = 4;
      break;
    case 63: 
      i = 5;
      break;
    case 41: 
      i = 7;
      break;
    case 46: 
      i = 8;
      break;
    case 91: 
      i = 9;
      break;
    case 94: 
      if (isSet(512)) {
        i = 0;
      } else {
        i = 11;
      }
      break;
    case 36: 
      if (isSet(512)) {
        i = 0;
      } else {
        i = 12;
      }
      break;
    case 40: 
      i = 6;
      if ((this.offset < this.regexlen) && (this.regex.charAt(this.offset) == '?'))
      {
        if (++this.offset >= this.regexlen) {
          throw ex("parser.next.2", this.offset - 1);
        }
        j = this.regex.charAt(this.offset++);
        switch (j)
        {
        case 58: 
          i = 13;
          break;
        case 61: 
          i = 14;
          break;
        case 33: 
          i = 15;
          break;
        case 91: 
          i = 19;
          break;
        case 62: 
          i = 18;
          break;
        case 60: 
          if (this.offset >= this.regexlen) {
            throw ex("parser.next.2", this.offset - 3);
          }
          j = this.regex.charAt(this.offset++);
          if (j == 61) {
            i = 16;
          } else if (j == 33) {
            i = 17;
          } else {
            throw ex("parser.next.3", this.offset - 3);
          }
          break;
        case 35: 
          while (this.offset < this.regexlen)
          {
            j = this.regex.charAt(this.offset++);
            if (j == 41) {
              break;
            }
          }
          if (j != 41) {
            throw ex("parser.next.4", this.offset - 1);
          }
          i = 21;
          break;
        default: 
          if ((j == 45) || ((97 <= j) && (j <= 122)) || ((65 <= j) && (j <= 90)))
          {
            this.offset -= 1;
            i = 22;
          }
          else if (j == 40)
          {
            i = 23;
          }
          else
          {
            throw ex("parser.next.2", this.offset - 2);
          }
          break;
        }
      }
      break;
    case 92: 
      i = 10;
      if (this.offset >= this.regexlen) {
        throw ex("parser.next.1", this.offset - 1);
      }
      this.chardata = this.regex.charAt(this.offset++);
      break;
    default: 
      i = 0;
    }
    this.nexttoken = i;
  }
  
  Token parseRegex()
    throws ParseException
  {
    Object localObject = parseTerm();
    Token.UnionToken localUnionToken = null;
    while (read() == 2)
    {
      next();
      if (localUnionToken == null)
      {
        localUnionToken = Token.createUnion();
        localUnionToken.addChild((Token)localObject);
        localObject = localUnionToken;
      }
      ((Token)localObject).addChild(parseTerm());
    }
    return localObject;
  }
  
  Token parseTerm()
    throws ParseException
  {
    int i = read();
    if ((i == 2) || (i == 7) || (i == 1)) {
      return Token.createEmpty();
    }
    Object localObject = parseFactor();
    Token.UnionToken localUnionToken = null;
    while (((i = read()) != 2) && (i != 7) && (i != 1))
    {
      if (localUnionToken == null)
      {
        localUnionToken = Token.createConcat();
        localUnionToken.addChild((Token)localObject);
        localObject = localUnionToken;
      }
      localUnionToken.addChild(parseFactor());
    }
    return localObject;
  }
  
  Token processCaret()
    throws ParseException
  {
    next();
    return Token.token_linebeginning;
  }
  
  Token processDollar()
    throws ParseException
  {
    next();
    return Token.token_lineend;
  }
  
  Token processLookahead()
    throws ParseException
  {
    next();
    Token.ParenToken localParenToken = Token.createLook(20, parseRegex());
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processNegativelookahead()
    throws ParseException
  {
    next();
    Token.ParenToken localParenToken = Token.createLook(21, parseRegex());
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processLookbehind()
    throws ParseException
  {
    next();
    Token.ParenToken localParenToken = Token.createLook(22, parseRegex());
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processNegativelookbehind()
    throws ParseException
  {
    next();
    Token.ParenToken localParenToken = Token.createLook(23, parseRegex());
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processBacksolidus_A()
    throws ParseException
  {
    next();
    return Token.token_stringbeginning;
  }
  
  Token processBacksolidus_Z()
    throws ParseException
  {
    next();
    return Token.token_stringend2;
  }
  
  Token processBacksolidus_z()
    throws ParseException
  {
    next();
    return Token.token_stringend;
  }
  
  Token processBacksolidus_b()
    throws ParseException
  {
    next();
    return Token.token_wordedge;
  }
  
  Token processBacksolidus_B()
    throws ParseException
  {
    next();
    return Token.token_not_wordedge;
  }
  
  Token processBacksolidus_lt()
    throws ParseException
  {
    next();
    return Token.token_wordbeginning;
  }
  
  Token processBacksolidus_gt()
    throws ParseException
  {
    next();
    return Token.token_wordend;
  }
  
  Token processStar(Token paramToken)
    throws ParseException
  {
    next();
    if (read() == 5)
    {
      next();
      return Token.createNGClosure(paramToken);
    }
    return Token.createClosure(paramToken);
  }
  
  Token processPlus(Token paramToken)
    throws ParseException
  {
    next();
    if (read() == 5)
    {
      next();
      return Token.createConcat(paramToken, Token.createNGClosure(paramToken));
    }
    return Token.createConcat(paramToken, Token.createClosure(paramToken));
  }
  
  Token processQuestion(Token paramToken)
    throws ParseException
  {
    next();
    Token.UnionToken localUnionToken = Token.createUnion();
    if (read() == 5)
    {
      next();
      localUnionToken.addChild(Token.createEmpty());
      localUnionToken.addChild(paramToken);
    }
    else
    {
      localUnionToken.addChild(paramToken);
      localUnionToken.addChild(Token.createEmpty());
    }
    return localUnionToken;
  }
  
  boolean checkQuestion(int paramInt)
  {
    return (paramInt < this.regexlen) && (this.regex.charAt(paramInt) == '?');
  }
  
  Token processParen()
    throws ParseException
  {
    next();
    int i = this.parennumber++;
    Token.ParenToken localParenToken = Token.createParen(parseRegex(), i);
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processParen2()
    throws ParseException
  {
    next();
    Token.ParenToken localParenToken = Token.createParen(parseRegex(), 0);
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processCondition()
    throws ParseException
  {
    if (this.offset + 1 >= this.regexlen) {
      throw ex("parser.factor.4", this.offset);
    }
    int i = -1;
    Token localToken1 = null;
    int j = this.regex.charAt(this.offset);
    if ((49 <= j) && (j <= 57))
    {
      i = j - 48;
      this.hasBackReferences = true;
      if (this.references == null) {
        this.references = new Vector();
      }
      this.references.addElement(new ReferencePosition(i, this.offset));
      this.offset += 1;
      if (this.regex.charAt(this.offset) != ')') {
        throw ex("parser.factor.1", this.offset);
      }
      this.offset += 1;
    }
    else
    {
      if (j == 63) {
        this.offset -= 1;
      }
      next();
      localToken1 = parseFactor();
      switch (localToken1.type)
      {
      case 20: 
      case 21: 
      case 22: 
      case 23: 
        break;
      case 8: 
        if (read() != 7) {
          throw ex("parser.factor.1", this.offset - 1);
        }
        break;
      default: 
        throw ex("parser.factor.5", this.offset);
      }
    }
    next();
    Token localToken2 = parseRegex();
    Token localToken3 = null;
    if (localToken2.type == 2)
    {
      if (localToken2.size() != 2) {
        throw ex("parser.factor.6", this.offset);
      }
      localToken3 = localToken2.getChild(1);
      localToken2 = localToken2.getChild(0);
    }
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return Token.createCondition(i, localToken1, localToken2, localToken3);
  }
  
  Token processModifiers()
    throws ParseException
  {
    int i = 0;
    int j = 0;
    int k = -1;
    int m;
    while (this.offset < this.regexlen)
    {
      k = this.regex.charAt(this.offset);
      m = REUtil.getOptionValue(k);
      if (m == 0) {
        break;
      }
      i |= m;
      this.offset += 1;
    }
    if (this.offset >= this.regexlen) {
      throw ex("parser.factor.2", this.offset - 1);
    }
    if (k == 45)
    {
      for (this.offset += 1; this.offset < this.regexlen; this.offset += 1)
      {
        k = this.regex.charAt(this.offset);
        m = REUtil.getOptionValue(k);
        if (m == 0) {
          break;
        }
        j |= m;
      }
      if (this.offset >= this.regexlen) {
        throw ex("parser.factor.2", this.offset - 1);
      }
    }
    Token.ModifierToken localModifierToken;
    if (k == 58)
    {
      this.offset += 1;
      next();
      localModifierToken = Token.createModifierGroup(parseRegex(), i, j);
      if (read() != 7) {
        throw ex("parser.factor.1", this.offset - 1);
      }
      next();
    }
    else if (k == 41)
    {
      this.offset += 1;
      next();
      localModifierToken = Token.createModifierGroup(parseRegex(), i, j);
    }
    else
    {
      throw ex("parser.factor.3", this.offset);
    }
    return localModifierToken;
  }
  
  Token processIndependent()
    throws ParseException
  {
    next();
    Token.ParenToken localParenToken = Token.createLook(24, parseRegex());
    if (read() != 7) {
      throw ex("parser.factor.1", this.offset - 1);
    }
    next();
    return localParenToken;
  }
  
  Token processBacksolidus_c()
    throws ParseException
  {
    int i;
    if ((this.offset >= this.regexlen) || (((i = this.regex.charAt(this.offset++)) & 0xFFE0) != '@')) {
      throw ex("parser.atom.1", this.offset - 1);
    }
    next();
    return Token.createChar(i - 64);
  }
  
  Token processBacksolidus_C()
    throws ParseException
  {
    throw ex("parser.process.1", this.offset);
  }
  
  Token processBacksolidus_i()
    throws ParseException
  {
    Token.CharToken localCharToken = Token.createChar(105);
    next();
    return localCharToken;
  }
  
  Token processBacksolidus_I()
    throws ParseException
  {
    throw ex("parser.process.1", this.offset);
  }
  
  Token processBacksolidus_g()
    throws ParseException
  {
    next();
    return Token.getGraphemePattern();
  }
  
  Token processBacksolidus_X()
    throws ParseException
  {
    next();
    return Token.getCombiningCharacterSequence();
  }
  
  Token processBackreference()
    throws ParseException
  {
    int i = this.chardata - 48;
    Token.StringToken localStringToken = Token.createBackReference(i);
    this.hasBackReferences = true;
    if (this.references == null) {
      this.references = new Vector();
    }
    this.references.addElement(new ReferencePosition(i, this.offset - 2));
    next();
    return localStringToken;
  }
  
  Token parseFactor()
    throws ParseException
  {
    int i = read();
    switch (i)
    {
    case 11: 
      return processCaret();
    case 12: 
      return processDollar();
    case 14: 
      return processLookahead();
    case 15: 
      return processNegativelookahead();
    case 16: 
      return processLookbehind();
    case 17: 
      return processNegativelookbehind();
    case 21: 
      next();
      return Token.createEmpty();
    case 10: 
      switch (this.chardata)
      {
      case 65: 
        return processBacksolidus_A();
      case 90: 
        return processBacksolidus_Z();
      case 122: 
        return processBacksolidus_z();
      case 98: 
        return processBacksolidus_b();
      case 66: 
        return processBacksolidus_B();
      case 60: 
        return processBacksolidus_lt();
      case 62: 
        return processBacksolidus_gt();
      }
      break;
    }
    Object localObject = parseAtom();
    i = read();
    switch (i)
    {
    case 3: 
      return processStar((Token)localObject);
    case 4: 
      return processPlus((Token)localObject);
    case 5: 
      return processQuestion((Token)localObject);
    case 0: 
      if ((this.chardata == 123) && (this.offset < this.regexlen))
      {
        int j = this.offset;
        int k = 0;
        int m = -1;
        if (((i = this.regex.charAt(j++)) >= '0') && (i <= 57))
        {
          k = i - 48;
          do
          {
            k = k * 10 + i - 48;
            if (k < 0) {
              throw ex("parser.quantifier.5", this.offset);
            }
            if ((j >= this.regexlen) || ((i = this.regex.charAt(j++)) < '0')) {
              break;
            }
          } while (i <= 57);
        }
        else
        {
          throw ex("parser.quantifier.1", this.offset);
        }
        m = k;
        if (i == 44)
        {
          if (j >= this.regexlen) {
            throw ex("parser.quantifier.3", this.offset);
          }
          if (((i = this.regex.charAt(j++)) >= '0') && (i <= 57))
          {
            m = i - 48;
            while ((j < this.regexlen) && ((i = this.regex.charAt(j++)) >= '0') && (i <= 57))
            {
              m = m * 10 + i - 48;
              if (m < 0) {
                throw ex("parser.quantifier.5", this.offset);
              }
            }
            if (k > m) {
              throw ex("parser.quantifier.4", this.offset);
            }
          }
          else
          {
            m = -1;
          }
        }
        if (i != 125) {
          throw ex("parser.quantifier.2", this.offset);
        }
        if (checkQuestion(j))
        {
          localObject = Token.createNGClosure((Token)localObject);
          this.offset = (j + 1);
        }
        else
        {
          localObject = Token.createClosure((Token)localObject);
          this.offset = j;
        }
        ((Token)localObject).setMin(k);
        ((Token)localObject).setMax(m);
        next();
      }
      break;
    }
    return localObject;
  }
  
  Token parseAtom()
    throws ParseException
  {
    int i = read();
    Object localObject = null;
    switch (i)
    {
    case 6: 
      return processParen();
    case 13: 
      return processParen2();
    case 23: 
      return processCondition();
    case 22: 
      return processModifiers();
    case 18: 
      return processIndependent();
    case 8: 
      next();
      localObject = Token.token_dot;
      break;
    case 9: 
      return parseCharacterClass(true);
    case 19: 
      return parseSetOperations();
    case 10: 
      int j;
      switch (this.chardata)
      {
      case 68: 
      case 83: 
      case 87: 
      case 100: 
      case 115: 
      case 119: 
        localObject = getTokenForShorthand(this.chardata);
        next();
        return localObject;
      case 101: 
      case 102: 
      case 110: 
      case 114: 
      case 116: 
      case 117: 
      case 118: 
      case 120: 
        j = decodeEscaped();
        if (j < 65536) {
          localObject = Token.createChar(j);
        } else {
          localObject = Token.createString(REUtil.decomposeToSurrogates(j));
        }
        break;
      case 99: 
        return processBacksolidus_c();
      case 67: 
        return processBacksolidus_C();
      case 105: 
        return processBacksolidus_i();
      case 73: 
        return processBacksolidus_I();
      case 103: 
        return processBacksolidus_g();
      case 88: 
        return processBacksolidus_X();
      case 49: 
      case 50: 
      case 51: 
      case 52: 
      case 53: 
      case 54: 
      case 55: 
      case 56: 
      case 57: 
        return processBackreference();
      case 80: 
      case 112: 
        j = this.offset;
        localObject = processBacksolidus_pP(this.chardata);
        if (localObject == null) {
          throw ex("parser.atom.5", j);
        }
        break;
      case 58: 
      case 59: 
      case 60: 
      case 61: 
      case 62: 
      case 63: 
      case 64: 
      case 65: 
      case 66: 
      case 69: 
      case 70: 
      case 71: 
      case 72: 
      case 74: 
      case 75: 
      case 76: 
      case 77: 
      case 78: 
      case 79: 
      case 81: 
      case 82: 
      case 84: 
      case 85: 
      case 86: 
      case 89: 
      case 90: 
      case 91: 
      case 92: 
      case 93: 
      case 94: 
      case 95: 
      case 96: 
      case 97: 
      case 98: 
      case 104: 
      case 106: 
      case 107: 
      case 108: 
      case 109: 
      case 111: 
      case 113: 
      default: 
        localObject = Token.createChar(this.chardata);
      }
      next();
      break;
    case 0: 
      if ((this.chardata == 93) || (this.chardata == 123) || (this.chardata == 125)) {
        throw ex("parser.atom.4", this.offset - 1);
      }
      localObject = Token.createChar(this.chardata);
      int k = this.chardata;
      next();
      if ((REUtil.isHighSurrogate(k)) && (read() == 0) && (REUtil.isLowSurrogate(this.chardata)))
      {
        char[] arrayOfChar = new char[2];
        arrayOfChar[0] = ((char)k);
        arrayOfChar[1] = ((char)this.chardata);
        localObject = Token.createParen(Token.createString(new String(arrayOfChar)), 0);
        next();
      }
      break;
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 7: 
    case 11: 
    case 12: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 20: 
    case 21: 
    default: 
      throw ex("parser.atom.4", this.offset - 1);
    }
    return localObject;
  }
  
  protected RangeToken processBacksolidus_pP(int paramInt)
    throws ParseException
  {
    next();
    if ((read() != 0) || (this.chardata != 123)) {
      throw ex("parser.atom.2", this.offset - 1);
    }
    boolean bool = paramInt == 112;
    int i = this.offset;
    int j = this.regex.indexOf('}', i);
    if (j < 0) {
      throw ex("parser.atom.3", this.offset);
    }
    String str = this.regex.substring(i, j);
    this.offset = (j + 1);
    return Token.getRange(str, bool, isSet(512));
  }
  
  int processCIinCharacterClass(RangeToken paramRangeToken, int paramInt)
  {
    return decodeEscaped();
  }
  
  protected RangeToken parseCharacterClass(boolean paramBoolean)
    throws ParseException
  {
    setContext(1);
    next();
    int i = 0;
    RangeToken localRangeToken1 = null;
    RangeToken localRangeToken2;
    if ((read() == 0) && (this.chardata == 94))
    {
      i = 1;
      next();
      if (paramBoolean)
      {
        localRangeToken2 = Token.createNRange();
      }
      else
      {
        localRangeToken1 = Token.createRange();
        localRangeToken1.addRange(0, 1114111);
        localRangeToken2 = Token.createRange();
      }
    }
    else
    {
      localRangeToken2 = Token.createRange();
    }
    int k = 1;
    int j;
    while ((j = read()) != 1)
    {
      if ((j == 0) && (this.chardata == 93) && (k == 0)) {
        break;
      }
      k = 0;
      int m = this.chardata;
      int n = 0;
      int i1;
      if (j == 10)
      {
        switch (m)
        {
        case 68: 
        case 83: 
        case 87: 
        case 100: 
        case 115: 
        case 119: 
          localRangeToken2.mergeRanges(getTokenForShorthand(m));
          n = 1;
          break;
        case 67: 
        case 73: 
        case 99: 
        case 105: 
          m = processCIinCharacterClass(localRangeToken2, m);
          if (m >= 0) {
            break;
          }
          n = 1;
          break;
        case 80: 
        case 112: 
          i1 = this.offset;
          RangeToken localRangeToken3 = processBacksolidus_pP(m);
          if (localRangeToken3 == null) {
            throw ex("parser.atom.5", i1);
          }
          localRangeToken2.mergeRanges(localRangeToken3);
          n = 1;
          break;
        default: 
          m = decodeEscaped();
          break;
        }
      }
      else if (j == 20)
      {
        i1 = this.regex.indexOf(':', this.offset);
        if (i1 < 0) {
          throw ex("parser.cc.1", this.offset);
        }
        boolean bool = true;
        if (this.regex.charAt(this.offset) == '^')
        {
          this.offset += 1;
          bool = false;
        }
        String str = this.regex.substring(this.offset, i1);
        RangeToken localRangeToken4 = Token.getRange(str, bool, isSet(512));
        if (localRangeToken4 == null) {
          throw ex("parser.cc.3", this.offset);
        }
        localRangeToken2.mergeRanges(localRangeToken4);
        n = 1;
        if ((i1 + 1 >= this.regexlen) || (this.regex.charAt(i1 + 1) != ']')) {
          throw ex("parser.cc.1", i1);
        }
        this.offset = (i1 + 2);
      }
      next();
      if (n == 0) {
        if ((read() != 0) || (this.chardata != 45))
        {
          localRangeToken2.addRange(m, m);
        }
        else
        {
          next();
          if ((j = read()) == 1) {
            throw ex("parser.cc.2", this.offset);
          }
          if ((j == 0) && (this.chardata == 93))
          {
            localRangeToken2.addRange(m, m);
            localRangeToken2.addRange(45, 45);
          }
          else
          {
            i1 = this.chardata;
            if (j == 10) {
              i1 = decodeEscaped();
            }
            next();
            localRangeToken2.addRange(m, i1);
          }
        }
      }
      if ((isSet(1024)) && (read() == 0) && (this.chardata == 44)) {
        next();
      }
    }
    if (read() == 1) {
      throw ex("parser.cc.2", this.offset);
    }
    if ((!paramBoolean) && (i != 0))
    {
      localRangeToken1.subtractRanges(localRangeToken2);
      localRangeToken2 = localRangeToken1;
    }
    localRangeToken2.sortRanges();
    localRangeToken2.compactRanges();
    setContext(0);
    next();
    return localRangeToken2;
  }
  
  protected RangeToken parseSetOperations()
    throws ParseException
  {
    RangeToken localRangeToken1 = parseCharacterClass(false);
    int i;
    while ((i = read()) != 7)
    {
      int j = this.chardata;
      if (((i == 0) && ((j == 45) || (j == 38))) || (i == 4))
      {
        next();
        if (read() != 9) {
          throw ex("parser.ope.1", this.offset - 1);
        }
        RangeToken localRangeToken2 = parseCharacterClass(false);
        if (i == 4) {
          localRangeToken1.mergeRanges(localRangeToken2);
        } else if (j == 45) {
          localRangeToken1.subtractRanges(localRangeToken2);
        } else if (j == 38) {
          localRangeToken1.intersectRanges(localRangeToken2);
        } else {
          throw new RuntimeException("ASSERT");
        }
      }
      else
      {
        throw ex("parser.ope.2", this.offset - 1);
      }
    }
    next();
    return localRangeToken1;
  }
  
  Token getTokenForShorthand(int paramInt)
  {
    Token localToken;
    switch (paramInt)
    {
    case 100: 
      localToken = isSet(32) ? Token.getRange("Nd", true) : Token.token_0to9;
      break;
    case 68: 
      localToken = isSet(32) ? Token.getRange("Nd", false) : Token.token_not_0to9;
      break;
    case 119: 
      localToken = isSet(32) ? Token.getRange("IsWord", true) : Token.token_wordchars;
      break;
    case 87: 
      localToken = isSet(32) ? Token.getRange("IsWord", false) : Token.token_not_wordchars;
      break;
    case 115: 
      localToken = isSet(32) ? Token.getRange("IsSpace", true) : Token.token_spaces;
      break;
    case 83: 
      localToken = isSet(32) ? Token.getRange("IsSpace", false) : Token.token_not_spaces;
      break;
    default: 
      throw new RuntimeException("Internal Error: shorthands: \\u" + Integer.toString(paramInt, 16));
    }
    return localToken;
  }
  
  int decodeEscaped()
    throws ParseException
  {
    if (read() != 10) {
      throw ex("parser.next.1", this.offset - 1);
    }
    int i = this.chardata;
    int j;
    int k;
    switch (i)
    {
    case 101: 
      i = 27;
      break;
    case 102: 
      i = 12;
      break;
    case 110: 
      i = 10;
      break;
    case 114: 
      i = 13;
      break;
    case 116: 
      i = 9;
      break;
    case 120: 
      next();
      if (read() != 0) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      if (this.chardata == 123)
      {
        j = 0;
        for (k = 0;; k = k * 16 + j)
        {
          next();
          if (read() != 0) {
            throw ex("parser.descape.1", this.offset - 1);
          }
          if ((j = hexChar(this.chardata)) < 0) {
            break;
          }
          if (k > k * 16) {
            throw ex("parser.descape.2", this.offset - 1);
          }
        }
        if (this.chardata != 125) {
          throw ex("parser.descape.3", this.offset - 1);
        }
        if (k > 1114111) {
          throw ex("parser.descape.4", this.offset - 1);
        }
        i = k;
      }
      else
      {
        j = 0;
        if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
          throw ex("parser.descape.1", this.offset - 1);
        }
        k = j;
        next();
        if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
          throw ex("parser.descape.1", this.offset - 1);
        }
        k = k * 16 + j;
        i = k;
      }
      break;
    case 117: 
      j = 0;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      i = k;
      break;
    case 118: 
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      next();
      if ((read() != 0) || ((j = hexChar(this.chardata)) < 0)) {
        throw ex("parser.descape.1", this.offset - 1);
      }
      k = k * 16 + j;
      if (k > 1114111) {
        throw ex("parser.descappe.4", this.offset - 1);
      }
      i = k;
      break;
    case 65: 
    case 90: 
    case 122: 
      throw ex("parser.descape.5", this.offset - 2);
    }
    return i;
  }
  
  private static final int hexChar(int paramInt)
  {
    if (paramInt < 48) {
      return -1;
    }
    if (paramInt > 102) {
      return -1;
    }
    if (paramInt <= 57) {
      return paramInt - 48;
    }
    if (paramInt < 65) {
      return -1;
    }
    if (paramInt <= 70) {
      return paramInt - 65 + 10;
    }
    if (paramInt < 97) {
      return -1;
    }
    return paramInt - 97 + 10;
  }
  
  static class ReferencePosition
  {
    int refNumber;
    int position;
    
    ReferencePosition(int paramInt1, int paramInt2)
    {
      this.refNumber = paramInt1;
      this.position = paramInt2;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xpath.regex.RegexParser
 * JD-Core Version:    0.7.0.1
 */