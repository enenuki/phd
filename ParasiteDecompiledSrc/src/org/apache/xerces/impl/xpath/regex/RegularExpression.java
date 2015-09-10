package org.apache.xerces.impl.xpath.regex;

import java.io.Serializable;
import java.text.CharacterIterator;

public class RegularExpression
  implements Serializable
{
  private static final long serialVersionUID = 6242499334195006401L;
  static final boolean DEBUG = false;
  String regex;
  int options;
  int nofparen;
  Token tokentree;
  boolean hasBackReferences = false;
  transient int minlength;
  transient Op operations = null;
  transient int numberOfClosures;
  transient Context context = null;
  transient RangeToken firstChar = null;
  transient String fixedString = null;
  transient int fixedStringOptions;
  transient BMPattern fixedStringTable = null;
  transient boolean fixedStringOnly = false;
  static final int IGNORE_CASE = 2;
  static final int SINGLE_LINE = 4;
  static final int MULTIPLE_LINES = 8;
  static final int EXTENDED_COMMENT = 16;
  static final int USE_UNICODE_CATEGORY = 32;
  static final int UNICODE_WORD_BOUNDARY = 64;
  static final int PROHIBIT_HEAD_CHARACTER_OPTIMIZATION = 128;
  static final int PROHIBIT_FIXED_STRING_OPTIMIZATION = 256;
  static final int XMLSCHEMA_MODE = 512;
  static final int SPECIAL_COMMA = 1024;
  private static final int WT_IGNORE = 0;
  private static final int WT_LETTER = 1;
  private static final int WT_OTHER = 2;
  static final int LINE_FEED = 10;
  static final int CARRIAGE_RETURN = 13;
  static final int LINE_SEPARATOR = 8232;
  static final int PARAGRAPH_SEPARATOR = 8233;
  
  private synchronized void compile(Token paramToken)
  {
    if (this.operations != null) {
      return;
    }
    this.numberOfClosures = 0;
    this.operations = compile(paramToken, null, false);
  }
  
  private Op compile(Token paramToken, Op paramOp, boolean paramBoolean)
  {
    Object localObject;
    switch (paramToken.type)
    {
    case 11: 
      localObject = Op.createDot();
      ((Op)localObject).next = paramOp;
      break;
    case 0: 
      localObject = Op.createChar(paramToken.getChar());
      ((Op)localObject).next = paramOp;
      break;
    case 8: 
      localObject = Op.createAnchor(paramToken.getChar());
      ((Op)localObject).next = paramOp;
      break;
    case 4: 
    case 5: 
      localObject = Op.createRange(paramToken);
      ((Op)localObject).next = paramOp;
      break;
    case 1: 
      localObject = paramOp;
      int i;
      if (!paramBoolean) {
        for (i = paramToken.size() - 1; i >= 0; i--) {
          localObject = compile(paramToken.getChild(i), (Op)localObject, false);
        }
      } else {
        for (i = 0; i < paramToken.size(); i++) {
          localObject = compile(paramToken.getChild(i), (Op)localObject, true);
        }
      }
      break;
    case 2: 
      Op.UnionOp localUnionOp = Op.createUnion(paramToken.size());
      for (int j = 0; j < paramToken.size(); j++) {
        localUnionOp.addElement(compile(paramToken.getChild(j), paramOp, paramBoolean));
      }
      localObject = localUnionOp;
      break;
    case 3: 
    case 9: 
      Token localToken = paramToken.getChild(0);
      int k = paramToken.getMin();
      int m = paramToken.getMax();
      int n;
      if ((k >= 0) && (k == m))
      {
        localObject = paramOp;
        for (n = 0; n < k; n++) {
          localObject = compile(localToken, (Op)localObject, paramBoolean);
        }
      }
      else
      {
        if ((k > 0) && (m > 0)) {
          m -= k;
        }
        if (m > 0)
        {
          localObject = paramOp;
          for (n = 0; n < m; n++)
          {
            Op.ChildOp localChildOp2 = Op.createQuestion(paramToken.type == 9);
            localChildOp2.next = paramOp;
            localChildOp2.setChild(compile(localToken, (Op)localObject, paramBoolean));
            localObject = localChildOp2;
          }
        }
        else
        {
          Op.ChildOp localChildOp1;
          if (paramToken.type == 9) {
            localChildOp1 = Op.createNonGreedyClosure();
          } else if (localToken.getMinLength() == 0) {
            localChildOp1 = Op.createClosure(this.numberOfClosures++);
          } else {
            localChildOp1 = Op.createClosure(-1);
          }
          localChildOp1.next = paramOp;
          localChildOp1.setChild(compile(localToken, localChildOp1, paramBoolean));
          localObject = localChildOp1;
        }
        if (k > 0) {
          for (int i1 = 0; i1 < k; i1++) {
            localObject = compile(localToken, (Op)localObject, paramBoolean);
          }
        }
      }
      break;
    case 7: 
      localObject = paramOp;
      break;
    case 10: 
      localObject = Op.createString(paramToken.getString());
      ((Op)localObject).next = paramOp;
      break;
    case 12: 
      localObject = Op.createBackReference(paramToken.getReferenceNumber());
      ((Op)localObject).next = paramOp;
      break;
    case 6: 
      if (paramToken.getParenNumber() == 0)
      {
        localObject = compile(paramToken.getChild(0), paramOp, paramBoolean);
      }
      else if (paramBoolean)
      {
        paramOp = Op.createCapture(paramToken.getParenNumber(), paramOp);
        paramOp = compile(paramToken.getChild(0), paramOp, paramBoolean);
        localObject = Op.createCapture(-paramToken.getParenNumber(), paramOp);
      }
      else
      {
        paramOp = Op.createCapture(-paramToken.getParenNumber(), paramOp);
        paramOp = compile(paramToken.getChild(0), paramOp, paramBoolean);
        localObject = Op.createCapture(paramToken.getParenNumber(), paramOp);
      }
      break;
    case 20: 
      localObject = Op.createLook(20, paramOp, compile(paramToken.getChild(0), null, false));
      break;
    case 21: 
      localObject = Op.createLook(21, paramOp, compile(paramToken.getChild(0), null, false));
      break;
    case 22: 
      localObject = Op.createLook(22, paramOp, compile(paramToken.getChild(0), null, true));
      break;
    case 23: 
      localObject = Op.createLook(23, paramOp, compile(paramToken.getChild(0), null, true));
      break;
    case 24: 
      localObject = Op.createIndependent(paramOp, compile(paramToken.getChild(0), null, paramBoolean));
      break;
    case 25: 
      localObject = Op.createModifier(paramOp, compile(paramToken.getChild(0), null, paramBoolean), ((Token.ModifierToken)paramToken).getOptions(), ((Token.ModifierToken)paramToken).getOptionsMask());
      break;
    case 26: 
      Token.ConditionToken localConditionToken = (Token.ConditionToken)paramToken;
      int i2 = localConditionToken.refNumber;
      Op localOp1 = localConditionToken.condition == null ? null : compile(localConditionToken.condition, null, paramBoolean);
      Op localOp2 = compile(localConditionToken.yes, paramOp, paramBoolean);
      Op localOp3 = localConditionToken.no == null ? null : compile(localConditionToken.no, paramOp, paramBoolean);
      localObject = Op.createCondition(paramOp, i2, localOp1, localOp2, localOp3);
      break;
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    default: 
      throw new RuntimeException("Unknown token type: " + paramToken.type);
    }
    return localObject;
  }
  
  public boolean matches(char[] paramArrayOfChar)
  {
    return matches(paramArrayOfChar, 0, paramArrayOfChar.length, (Match)null);
  }
  
  public boolean matches(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    return matches(paramArrayOfChar, paramInt1, paramInt2, (Match)null);
  }
  
  public boolean matches(char[] paramArrayOfChar, Match paramMatch)
  {
    return matches(paramArrayOfChar, 0, paramArrayOfChar.length, paramMatch);
  }
  
  public boolean matches(char[] paramArrayOfChar, int paramInt1, int paramInt2, Match paramMatch)
  {
    synchronized (this)
    {
      if (this.operations == null) {
        prepare();
      }
      if (this.context == null) {
        this.context = new Context();
      }
    }
    Context localContext1 = null;
    synchronized (this.context)
    {
      localContext1 = this.context.inuse ? new Context() : this.context;
      localContext1.reset(paramArrayOfChar, paramInt1, paramInt2, this.numberOfClosures);
    }
    if (paramMatch != null)
    {
      paramMatch.setNumberOfGroups(this.nofparen);
      paramMatch.setSource(paramArrayOfChar);
    }
    else if (this.hasBackReferences)
    {
      paramMatch = new Match();
      paramMatch.setNumberOfGroups(this.nofparen);
    }
    localContext1.match = paramMatch;
    if (isSet(this.options, 512))
    {
      i = matchCharArray(localContext1, this.operations, localContext1.start, 1, this.options);
      if (i == localContext1.limit)
      {
        if (localContext1.match != null)
        {
          localContext1.match.setBeginning(0, localContext1.start);
          localContext1.match.setEnd(0, i);
        }
        localContext1.inuse = false;
        return true;
      }
      return false;
    }
    if (this.fixedStringOnly)
    {
      i = this.fixedStringTable.matches(paramArrayOfChar, localContext1.start, localContext1.limit);
      if (i >= 0)
      {
        if (localContext1.match != null)
        {
          localContext1.match.setBeginning(0, i);
          localContext1.match.setEnd(0, i + this.fixedString.length());
        }
        localContext1.inuse = false;
        return true;
      }
      localContext1.inuse = false;
      return false;
    }
    if (this.fixedString != null)
    {
      i = this.fixedStringTable.matches(paramArrayOfChar, localContext1.start, localContext1.limit);
      if (i < 0)
      {
        localContext1.inuse = false;
        return false;
      }
    }
    int i = localContext1.limit - this.minlength;
    int k = -1;
    int j;
    int n;
    if ((this.operations != null) && (this.operations.type == 7) && (this.operations.getChild().type == 0))
    {
      if (isSet(this.options, 4))
      {
        j = localContext1.start;
        k = matchCharArray(localContext1, this.operations, localContext1.start, 1, this.options);
      }
      else
      {
        int m = 1;
        for (j = localContext1.start; j <= i; j++)
        {
          n = paramArrayOfChar[j];
          if (isEOLChar(n))
          {
            m = 1;
          }
          else
          {
            if ((m != 0) && (0 <= (k = matchCharArray(localContext1, this.operations, j, 1, this.options)))) {
              break;
            }
            m = 0;
          }
        }
      }
    }
    else if (this.firstChar != null)
    {
      RangeToken localRangeToken = this.firstChar;
      if (isSet(this.options, 2))
      {
        localRangeToken = this.firstChar.getCaseInsensitiveToken();
        for (j = localContext1.start; j <= i; j++)
        {
          n = paramArrayOfChar[j];
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < localContext1.limit))
          {
            n = REUtil.composeFromSurrogates(n, paramArrayOfChar[(j + 1)]);
            if (!localRangeToken.match(n)) {
              continue;
            }
          }
          else if (!localRangeToken.match(n))
          {
            char c = Character.toUpperCase((char)n);
            if ((!localRangeToken.match(c)) && (!localRangeToken.match(Character.toLowerCase(c)))) {
              continue;
            }
          }
          if (0 <= (k = matchCharArray(localContext1, this.operations, j, 1, this.options))) {
            break;
          }
        }
      }
      else
      {
        for (j = localContext1.start; j <= i; j++)
        {
          n = paramArrayOfChar[j];
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < localContext1.limit)) {
            n = REUtil.composeFromSurrogates(n, paramArrayOfChar[(j + 1)]);
          }
          if ((localRangeToken.match(n)) && (0 <= (k = matchCharArray(localContext1, this.operations, j, 1, this.options)))) {
            break;
          }
        }
      }
    }
    else
    {
      for (j = localContext1.start; j <= i; j++) {
        if (0 <= (k = matchCharArray(localContext1, this.operations, j, 1, this.options))) {
          break;
        }
      }
    }
    if (k >= 0)
    {
      if (localContext1.match != null)
      {
        localContext1.match.setBeginning(0, j);
        localContext1.match.setEnd(0, k);
      }
      localContext1.inuse = false;
      return true;
    }
    localContext1.inuse = false;
    return false;
  }
  
  private int matchCharArray(Context paramContext, Op paramOp, int paramInt1, int paramInt2, int paramInt3)
  {
    char[] arrayOfChar = paramContext.charTarget;
    for (;;)
    {
      if (paramOp == null) {
        return (isSet(paramInt3, 512)) && (paramInt1 != paramContext.limit) ? -1 : paramInt1;
      }
      if ((paramInt1 > paramContext.limit) || (paramInt1 < paramContext.start)) {
        return -1;
      }
      int i;
      int j;
      int k;
      int n;
      int i1;
      int m;
      int i2;
      switch (paramOp.type)
      {
      case 1: 
        if (isSet(paramInt3, 2))
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= paramContext.limit) || (!matchIgnoreCase(i, arrayOfChar[paramInt1]))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= paramContext.limit) || (j < 0) || (!matchIgnoreCase(i, arrayOfChar[j]))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        else
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= paramContext.limit) || (i != arrayOfChar[paramInt1])) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= paramContext.limit) || (j < 0) || (i != arrayOfChar[j])) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        paramOp = paramOp.next;
        break;
      case 0: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= paramContext.limit) {
            return -1;
          }
          i = arrayOfChar[paramInt1];
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
              paramInt1++;
            }
          }
          else
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
              i = REUtil.composeFromSurrogates(i, arrayOfChar[(++paramInt1)]);
            }
            if (isEOLChar(i)) {
              return -1;
            }
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= paramContext.limit) || (i < 0)) {
            return -1;
          }
          j = arrayOfChar[i];
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              i--;
            }
          }
          else
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              j = REUtil.composeFromSurrogates(arrayOfChar[(--i)], j);
            }
            if (!isEOLChar(j)) {
              return -1;
            }
          }
          paramInt1 = i;
        }
        paramOp = paramOp.next;
        break;
      case 3: 
      case 4: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= paramContext.limit) {
            return -1;
          }
          i = arrayOfChar[paramInt1];
          if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
            i = REUtil.composeFromSurrogates(i, arrayOfChar[(++paramInt1)]);
          }
          RangeToken localRangeToken1 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken1 = localRangeToken1.getCaseInsensitiveToken();
            if (!localRangeToken1.match(i))
            {
              if (i >= 65536) {
                return -1;
              }
              char c1;
              if ((!localRangeToken1.match(c1 = Character.toUpperCase((char)i))) && (!localRangeToken1.match(Character.toLowerCase(c1)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken1.match(i))
          {
            return -1;
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= paramContext.limit) || (i < 0)) {
            return -1;
          }
          k = arrayOfChar[i];
          if ((REUtil.isLowSurrogate(k)) && (i - 1 >= 0)) {
            k = REUtil.composeFromSurrogates(arrayOfChar[(--i)], k);
          }
          RangeToken localRangeToken2 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken2 = localRangeToken2.getCaseInsensitiveToken();
            if (!localRangeToken2.match(k))
            {
              if (k >= 65536) {
                return -1;
              }
              char c2;
              if ((!localRangeToken2.match(c2 = Character.toUpperCase((char)k))) && (!localRangeToken2.match(Character.toLowerCase(c2)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken2.match(k))
          {
            return -1;
          }
          paramInt1 = i;
        }
        paramOp = paramOp.next;
        break;
      case 5: 
        i = 0;
        switch (paramOp.getData())
        {
        case 94: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != paramContext.start) && ((paramInt1 <= paramContext.start) || (!isEOLChar(arrayOfChar[(paramInt1 - 1)])))) {
              return -1;
            }
          }
          else if (paramInt1 != paramContext.start) {
            return -1;
          }
          break;
        case 64: 
          if ((paramInt1 != paramContext.start) && ((paramInt1 <= paramContext.start) || (!isEOLChar(arrayOfChar[(paramInt1 - 1)])))) {
            return -1;
          }
          break;
        case 36: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != paramContext.limit) && ((paramInt1 >= paramContext.limit) || (!isEOLChar(arrayOfChar[paramInt1])))) {
              return -1;
            }
          }
          else if ((paramInt1 != paramContext.limit) && ((paramInt1 + 1 != paramContext.limit) || (!isEOLChar(arrayOfChar[paramInt1]))) && ((paramInt1 + 2 != paramContext.limit) || (arrayOfChar[paramInt1] != '\r') || (arrayOfChar[(paramInt1 + 1)] != '\n'))) {
            return -1;
          }
          break;
        case 65: 
          if (paramInt1 != paramContext.start) {
            return -1;
          }
          break;
        case 90: 
          if ((paramInt1 != paramContext.limit) && ((paramInt1 + 1 != paramContext.limit) || (!isEOLChar(arrayOfChar[paramInt1]))) && ((paramInt1 + 2 != paramContext.limit) || (arrayOfChar[paramInt1] != '\r') || (arrayOfChar[(paramInt1 + 1)] != '\n'))) {
            return -1;
          }
          break;
        case 122: 
          if (paramInt1 != paramContext.limit) {
            return -1;
          }
          break;
        case 98: 
          if (paramContext.length == 0) {
            return -1;
          }
          k = getWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3);
          if (k == 0) {
            return -1;
          }
          n = getPreviousWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3);
          if (k == n) {
            return -1;
          }
          break;
        case 66: 
          if (paramContext.length == 0)
          {
            i = 1;
          }
          else
          {
            k = getWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3);
            i = (k == 0) || (k == getPreviousWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3)) ? 1 : 0;
          }
          if (i == 0) {
            return -1;
          }
          break;
        case 60: 
          if ((paramContext.length == 0) || (paramInt1 == paramContext.limit)) {
            return -1;
          }
          if ((getWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 1) || (getPreviousWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 2)) {
            return -1;
          }
          break;
        case 62: 
          if ((paramContext.length == 0) || (paramInt1 == paramContext.start)) {
            return -1;
          }
          if ((getWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 2) || (getPreviousWordType(arrayOfChar, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 1)) {
            return -1;
          }
          break;
        }
        paramOp = paramOp.next;
        break;
      case 16: 
        k = paramOp.getData();
        if ((k <= 0) || (k >= this.nofparen)) {
          throw new RuntimeException("Internal Error: Reference number must be more than zero: " + k);
        }
        if ((paramContext.match.getBeginning(k) < 0) || (paramContext.match.getEnd(k) < 0)) {
          return -1;
        }
        n = paramContext.match.getBeginning(k);
        i1 = paramContext.match.getEnd(k) - n;
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(arrayOfChar, paramInt1, paramContext.limit, n, i1)) {
              return -1;
            }
            paramInt1 += i1;
          }
          else
          {
            if (!regionMatches(arrayOfChar, paramInt1 - i1, paramContext.limit, n, i1)) {
              return -1;
            }
            paramInt1 -= i1;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1, paramContext.limit, n, i1)) {
            return -1;
          }
          paramInt1 += i1;
        }
        else
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1 - i1, paramContext.limit, n, i1)) {
            return -1;
          }
          paramInt1 -= i1;
        }
        paramOp = paramOp.next;
        break;
      case 6: 
        String str = paramOp.getString();
        n = str.length();
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(arrayOfChar, paramInt1, paramContext.limit, str, n)) {
              return -1;
            }
            paramInt1 += n;
          }
          else
          {
            if (!regionMatches(arrayOfChar, paramInt1 - n, paramContext.limit, str, n)) {
              return -1;
            }
            paramInt1 -= n;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1, paramContext.limit, str, n)) {
            return -1;
          }
          paramInt1 += n;
        }
        else
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1 - n, paramContext.limit, str, n)) {
            return -1;
          }
          paramInt1 -= n;
        }
        paramOp = paramOp.next;
        break;
      case 7: 
        m = paramOp.getData();
        if (m >= 0)
        {
          n = paramContext.offsets[m];
          if ((n < 0) || (n != paramInt1))
          {
            paramContext.offsets[m] = paramInt1;
          }
          else
          {
            paramContext.offsets[m] = -1;
            paramOp = paramOp.next;
            continue;
          }
        }
        n = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          paramContext.offsets[m] = -1;
        }
        if (n >= 0) {
          return n;
        }
        paramOp = paramOp.next;
        break;
      case 9: 
        m = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.next;
        break;
      case 8: 
      case 10: 
        m = matchCharArray(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.getChild();
        break;
      case 11: 
        for (m = 0; m < paramOp.size(); m++)
        {
          n = matchCharArray(paramContext, paramOp.elementAt(m), paramInt1, paramInt2, paramInt3);
          if (n >= 0) {
            return n;
          }
        }
        return -1;
      case 15: 
        n = paramOp.getData();
        if ((paramContext.match != null) && (n > 0))
        {
          i1 = paramContext.match.getBeginning(n);
          paramContext.match.setBeginning(n, paramInt1);
          i2 = matchCharArray(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
          if (i2 < 0) {
            paramContext.match.setBeginning(n, i1);
          }
          return i2;
        }
        if ((paramContext.match != null) && (n < 0))
        {
          i1 = -n;
          i2 = paramContext.match.getEnd(i1);
          paramContext.match.setEnd(i1, paramInt1);
          int i3 = matchCharArray(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
          if (i3 < 0) {
            paramContext.match.setEnd(i1, i2);
          }
          return i3;
        }
        paramOp = paramOp.next;
        break;
      case 20: 
        if (0 > matchCharArray(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 21: 
        if (0 <= matchCharArray(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 22: 
        if (0 > matchCharArray(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 23: 
        if (0 <= matchCharArray(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 24: 
        i1 = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (i1 < 0) {
          return i1;
        }
        paramInt1 = i1;
        paramOp = paramOp.next;
        break;
      case 25: 
        i1 = paramInt3;
        i1 |= paramOp.getData();
        i1 &= (paramOp.getData2() ^ 0xFFFFFFFF);
        i2 = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, i1);
        if (i2 < 0) {
          return i2;
        }
        paramInt1 = i2;
        paramOp = paramOp.next;
        break;
      case 26: 
        Op.ConditionOp localConditionOp = (Op.ConditionOp)paramOp;
        i2 = 0;
        if (localConditionOp.refNumber > 0)
        {
          if (localConditionOp.refNumber >= this.nofparen) {
            throw new RuntimeException("Internal Error: Reference number must be more than zero: " + localConditionOp.refNumber);
          }
          i2 = (paramContext.match.getBeginning(localConditionOp.refNumber) >= 0) && (paramContext.match.getEnd(localConditionOp.refNumber) >= 0) ? 1 : 0;
        }
        else
        {
          i2 = 0 <= matchCharArray(paramContext, localConditionOp.condition, paramInt1, paramInt2, paramInt3) ? 1 : 0;
        }
        if (i2 != 0) {
          paramOp = localConditionOp.yes;
        } else if (localConditionOp.no != null) {
          paramOp = localConditionOp.no;
        } else {
          paramOp = localConditionOp.next;
        }
        break;
      }
    }
    throw new RuntimeException("Unknown operation type: " + paramOp.type);
  }
  
  private static final int getPreviousWordType(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = getWordType(paramArrayOfChar, paramInt1, paramInt2, --paramInt3, paramInt4); i == 0; i = getWordType(paramArrayOfChar, paramInt1, paramInt2, --paramInt3, paramInt4)) {}
    return i;
  }
  
  private static final int getWordType(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 < paramInt1) || (paramInt3 >= paramInt2)) {
      return 2;
    }
    return getWordType0(paramArrayOfChar[paramInt3], paramInt4);
  }
  
  private static final boolean regionMatches(char[] paramArrayOfChar, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0) {
      if (paramArrayOfChar[(paramInt1++)] != paramString.charAt(i++)) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatches(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0) {
      if (paramArrayOfChar[(paramInt1++)] != paramArrayOfChar[(i++)]) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(char[] paramArrayOfChar, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0)
    {
      char c1 = paramArrayOfChar[(paramInt1++)];
      char c2 = paramString.charAt(i++);
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0)
    {
      char c1 = paramArrayOfChar[(paramInt1++)];
      char c2 = paramArrayOfChar[(i++)];
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean matches(String paramString)
  {
    return matches(paramString, 0, paramString.length(), (Match)null);
  }
  
  public boolean matches(String paramString, int paramInt1, int paramInt2)
  {
    return matches(paramString, paramInt1, paramInt2, (Match)null);
  }
  
  public boolean matches(String paramString, Match paramMatch)
  {
    return matches(paramString, 0, paramString.length(), paramMatch);
  }
  
  public boolean matches(String paramString, int paramInt1, int paramInt2, Match paramMatch)
  {
    synchronized (this)
    {
      if (this.operations == null) {
        prepare();
      }
      if (this.context == null) {
        this.context = new Context();
      }
    }
    Context localContext1 = null;
    synchronized (this.context)
    {
      localContext1 = this.context.inuse ? new Context() : this.context;
      localContext1.reset(paramString, paramInt1, paramInt2, this.numberOfClosures);
    }
    if (paramMatch != null)
    {
      paramMatch.setNumberOfGroups(this.nofparen);
      paramMatch.setSource(paramString);
    }
    else if (this.hasBackReferences)
    {
      paramMatch = new Match();
      paramMatch.setNumberOfGroups(this.nofparen);
    }
    localContext1.match = paramMatch;
    if (isSet(this.options, 512))
    {
      i = matchString(localContext1, this.operations, localContext1.start, 1, this.options);
      if (i == localContext1.limit)
      {
        if (localContext1.match != null)
        {
          localContext1.match.setBeginning(0, localContext1.start);
          localContext1.match.setEnd(0, i);
        }
        localContext1.inuse = false;
        return true;
      }
      return false;
    }
    if (this.fixedStringOnly)
    {
      i = this.fixedStringTable.matches(paramString, localContext1.start, localContext1.limit);
      if (i >= 0)
      {
        if (localContext1.match != null)
        {
          localContext1.match.setBeginning(0, i);
          localContext1.match.setEnd(0, i + this.fixedString.length());
        }
        localContext1.inuse = false;
        return true;
      }
      localContext1.inuse = false;
      return false;
    }
    if (this.fixedString != null)
    {
      i = this.fixedStringTable.matches(paramString, localContext1.start, localContext1.limit);
      if (i < 0)
      {
        localContext1.inuse = false;
        return false;
      }
    }
    int i = localContext1.limit - this.minlength;
    int k = -1;
    int j;
    int n;
    if ((this.operations != null) && (this.operations.type == 7) && (this.operations.getChild().type == 0))
    {
      if (isSet(this.options, 4))
      {
        j = localContext1.start;
        k = matchString(localContext1, this.operations, localContext1.start, 1, this.options);
      }
      else
      {
        int m = 1;
        for (j = localContext1.start; j <= i; j++)
        {
          n = paramString.charAt(j);
          if (isEOLChar(n))
          {
            m = 1;
          }
          else
          {
            if ((m != 0) && (0 <= (k = matchString(localContext1, this.operations, j, 1, this.options)))) {
              break;
            }
            m = 0;
          }
        }
      }
    }
    else if (this.firstChar != null)
    {
      RangeToken localRangeToken = this.firstChar;
      if (isSet(this.options, 2))
      {
        localRangeToken = this.firstChar.getCaseInsensitiveToken();
        for (j = localContext1.start; j <= i; j++)
        {
          n = paramString.charAt(j);
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < localContext1.limit))
          {
            n = REUtil.composeFromSurrogates(n, paramString.charAt(j + 1));
            if (!localRangeToken.match(n)) {
              continue;
            }
          }
          else if (!localRangeToken.match(n))
          {
            char c = Character.toUpperCase((char)n);
            if ((!localRangeToken.match(c)) && (!localRangeToken.match(Character.toLowerCase(c)))) {
              continue;
            }
          }
          if (0 <= (k = matchString(localContext1, this.operations, j, 1, this.options))) {
            break;
          }
        }
      }
      else
      {
        for (j = localContext1.start; j <= i; j++)
        {
          n = paramString.charAt(j);
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < localContext1.limit)) {
            n = REUtil.composeFromSurrogates(n, paramString.charAt(j + 1));
          }
          if ((localRangeToken.match(n)) && (0 <= (k = matchString(localContext1, this.operations, j, 1, this.options)))) {
            break;
          }
        }
      }
    }
    else
    {
      for (j = localContext1.start; j <= i; j++) {
        if (0 <= (k = matchString(localContext1, this.operations, j, 1, this.options))) {
          break;
        }
      }
    }
    if (k >= 0)
    {
      if (localContext1.match != null)
      {
        localContext1.match.setBeginning(0, j);
        localContext1.match.setEnd(0, k);
      }
      localContext1.inuse = false;
      return true;
    }
    localContext1.inuse = false;
    return false;
  }
  
  private int matchString(Context paramContext, Op paramOp, int paramInt1, int paramInt2, int paramInt3)
  {
    String str1 = paramContext.strTarget;
    for (;;)
    {
      if (paramOp == null) {
        return (isSet(paramInt3, 512)) && (paramInt1 != paramContext.limit) ? -1 : paramInt1;
      }
      if ((paramInt1 > paramContext.limit) || (paramInt1 < paramContext.start)) {
        return -1;
      }
      int i;
      int j;
      int k;
      int n;
      int i1;
      int m;
      int i2;
      switch (paramOp.type)
      {
      case 1: 
        if (isSet(paramInt3, 2))
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= paramContext.limit) || (!matchIgnoreCase(i, str1.charAt(paramInt1)))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= paramContext.limit) || (j < 0) || (!matchIgnoreCase(i, str1.charAt(j)))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        else
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= paramContext.limit) || (i != str1.charAt(paramInt1))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= paramContext.limit) || (j < 0) || (i != str1.charAt(j))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        paramOp = paramOp.next;
        break;
      case 0: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= paramContext.limit) {
            return -1;
          }
          i = str1.charAt(paramInt1);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
              paramInt1++;
            }
          }
          else
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
              i = REUtil.composeFromSurrogates(i, str1.charAt(++paramInt1));
            }
            if (isEOLChar(i)) {
              return -1;
            }
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= paramContext.limit) || (i < 0)) {
            return -1;
          }
          j = str1.charAt(i);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              i--;
            }
          }
          else
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              j = REUtil.composeFromSurrogates(str1.charAt(--i), j);
            }
            if (!isEOLChar(j)) {
              return -1;
            }
          }
          paramInt1 = i;
        }
        paramOp = paramOp.next;
        break;
      case 3: 
      case 4: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= paramContext.limit) {
            return -1;
          }
          i = str1.charAt(paramInt1);
          if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
            i = REUtil.composeFromSurrogates(i, str1.charAt(++paramInt1));
          }
          RangeToken localRangeToken1 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken1 = localRangeToken1.getCaseInsensitiveToken();
            if (!localRangeToken1.match(i))
            {
              if (i >= 65536) {
                return -1;
              }
              char c1;
              if ((!localRangeToken1.match(c1 = Character.toUpperCase((char)i))) && (!localRangeToken1.match(Character.toLowerCase(c1)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken1.match(i))
          {
            return -1;
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= paramContext.limit) || (i < 0)) {
            return -1;
          }
          k = str1.charAt(i);
          if ((REUtil.isLowSurrogate(k)) && (i - 1 >= 0)) {
            k = REUtil.composeFromSurrogates(str1.charAt(--i), k);
          }
          RangeToken localRangeToken2 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken2 = localRangeToken2.getCaseInsensitiveToken();
            if (!localRangeToken2.match(k))
            {
              if (k >= 65536) {
                return -1;
              }
              char c2;
              if ((!localRangeToken2.match(c2 = Character.toUpperCase((char)k))) && (!localRangeToken2.match(Character.toLowerCase(c2)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken2.match(k))
          {
            return -1;
          }
          paramInt1 = i;
        }
        paramOp = paramOp.next;
        break;
      case 5: 
        i = 0;
        switch (paramOp.getData())
        {
        case 94: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != paramContext.start) && ((paramInt1 <= paramContext.start) || (!isEOLChar(str1.charAt(paramInt1 - 1))))) {
              return -1;
            }
          }
          else if (paramInt1 != paramContext.start) {
            return -1;
          }
          break;
        case 64: 
          if ((paramInt1 != paramContext.start) && ((paramInt1 <= paramContext.start) || (!isEOLChar(str1.charAt(paramInt1 - 1))))) {
            return -1;
          }
          break;
        case 36: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != paramContext.limit) && ((paramInt1 >= paramContext.limit) || (!isEOLChar(str1.charAt(paramInt1))))) {
              return -1;
            }
          }
          else if ((paramInt1 != paramContext.limit) && ((paramInt1 + 1 != paramContext.limit) || (!isEOLChar(str1.charAt(paramInt1)))) && ((paramInt1 + 2 != paramContext.limit) || (str1.charAt(paramInt1) != '\r') || (str1.charAt(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 65: 
          if (paramInt1 != paramContext.start) {
            return -1;
          }
          break;
        case 90: 
          if ((paramInt1 != paramContext.limit) && ((paramInt1 + 1 != paramContext.limit) || (!isEOLChar(str1.charAt(paramInt1)))) && ((paramInt1 + 2 != paramContext.limit) || (str1.charAt(paramInt1) != '\r') || (str1.charAt(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 122: 
          if (paramInt1 != paramContext.limit) {
            return -1;
          }
          break;
        case 98: 
          if (paramContext.length == 0) {
            return -1;
          }
          k = getWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3);
          if (k == 0) {
            return -1;
          }
          n = getPreviousWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3);
          if (k == n) {
            return -1;
          }
          break;
        case 66: 
          if (paramContext.length == 0)
          {
            i = 1;
          }
          else
          {
            k = getWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3);
            i = (k == 0) || (k == getPreviousWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3)) ? 1 : 0;
          }
          if (i == 0) {
            return -1;
          }
          break;
        case 60: 
          if ((paramContext.length == 0) || (paramInt1 == paramContext.limit)) {
            return -1;
          }
          if ((getWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 1) || (getPreviousWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 2)) {
            return -1;
          }
          break;
        case 62: 
          if ((paramContext.length == 0) || (paramInt1 == paramContext.start)) {
            return -1;
          }
          if ((getWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 2) || (getPreviousWordType(str1, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 1)) {
            return -1;
          }
          break;
        }
        paramOp = paramOp.next;
        break;
      case 16: 
        k = paramOp.getData();
        if ((k <= 0) || (k >= this.nofparen)) {
          throw new RuntimeException("Internal Error: Reference number must be more than zero: " + k);
        }
        if ((paramContext.match.getBeginning(k) < 0) || (paramContext.match.getEnd(k) < 0)) {
          return -1;
        }
        n = paramContext.match.getBeginning(k);
        i1 = paramContext.match.getEnd(k) - n;
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(str1, paramInt1, paramContext.limit, n, i1)) {
              return -1;
            }
            paramInt1 += i1;
          }
          else
          {
            if (!regionMatches(str1, paramInt1 - i1, paramContext.limit, n, i1)) {
              return -1;
            }
            paramInt1 -= i1;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1, paramContext.limit, n, i1)) {
            return -1;
          }
          paramInt1 += i1;
        }
        else
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1 - i1, paramContext.limit, n, i1)) {
            return -1;
          }
          paramInt1 -= i1;
        }
        paramOp = paramOp.next;
        break;
      case 6: 
        String str2 = paramOp.getString();
        n = str2.length();
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(str1, paramInt1, paramContext.limit, str2, n)) {
              return -1;
            }
            paramInt1 += n;
          }
          else
          {
            if (!regionMatches(str1, paramInt1 - n, paramContext.limit, str2, n)) {
              return -1;
            }
            paramInt1 -= n;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1, paramContext.limit, str2, n)) {
            return -1;
          }
          paramInt1 += n;
        }
        else
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1 - n, paramContext.limit, str2, n)) {
            return -1;
          }
          paramInt1 -= n;
        }
        paramOp = paramOp.next;
        break;
      case 7: 
        m = paramOp.getData();
        if (m >= 0)
        {
          n = paramContext.offsets[m];
          if ((n < 0) || (n != paramInt1))
          {
            paramContext.offsets[m] = paramInt1;
          }
          else
          {
            paramContext.offsets[m] = -1;
            paramOp = paramOp.next;
            continue;
          }
        }
        n = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          paramContext.offsets[m] = -1;
        }
        if (n >= 0) {
          return n;
        }
        paramOp = paramOp.next;
        break;
      case 9: 
        m = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.next;
        break;
      case 8: 
      case 10: 
        m = matchString(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.getChild();
        break;
      case 11: 
        for (m = 0; m < paramOp.size(); m++)
        {
          n = matchString(paramContext, paramOp.elementAt(m), paramInt1, paramInt2, paramInt3);
          if (n >= 0) {
            return n;
          }
        }
        return -1;
      case 15: 
        n = paramOp.getData();
        if ((paramContext.match != null) && (n > 0))
        {
          i1 = paramContext.match.getBeginning(n);
          paramContext.match.setBeginning(n, paramInt1);
          i2 = matchString(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
          if (i2 < 0) {
            paramContext.match.setBeginning(n, i1);
          }
          return i2;
        }
        if ((paramContext.match != null) && (n < 0))
        {
          i1 = -n;
          i2 = paramContext.match.getEnd(i1);
          paramContext.match.setEnd(i1, paramInt1);
          int i3 = matchString(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
          if (i3 < 0) {
            paramContext.match.setEnd(i1, i2);
          }
          return i3;
        }
        paramOp = paramOp.next;
        break;
      case 20: 
        if (0 > matchString(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 21: 
        if (0 <= matchString(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 22: 
        if (0 > matchString(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 23: 
        if (0 <= matchString(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 24: 
        i1 = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (i1 < 0) {
          return i1;
        }
        paramInt1 = i1;
        paramOp = paramOp.next;
        break;
      case 25: 
        i1 = paramInt3;
        i1 |= paramOp.getData();
        i1 &= (paramOp.getData2() ^ 0xFFFFFFFF);
        i2 = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, i1);
        if (i2 < 0) {
          return i2;
        }
        paramInt1 = i2;
        paramOp = paramOp.next;
        break;
      case 26: 
        Op.ConditionOp localConditionOp = (Op.ConditionOp)paramOp;
        i2 = 0;
        if (localConditionOp.refNumber > 0)
        {
          if (localConditionOp.refNumber >= this.nofparen) {
            throw new RuntimeException("Internal Error: Reference number must be more than zero: " + localConditionOp.refNumber);
          }
          i2 = (paramContext.match.getBeginning(localConditionOp.refNumber) >= 0) && (paramContext.match.getEnd(localConditionOp.refNumber) >= 0) ? 1 : 0;
        }
        else
        {
          i2 = 0 <= matchString(paramContext, localConditionOp.condition, paramInt1, paramInt2, paramInt3) ? 1 : 0;
        }
        if (i2 != 0) {
          paramOp = localConditionOp.yes;
        } else if (localConditionOp.no != null) {
          paramOp = localConditionOp.no;
        } else {
          paramOp = localConditionOp.next;
        }
        break;
      }
    }
    throw new RuntimeException("Unknown operation type: " + paramOp.type);
  }
  
  private static final int getPreviousWordType(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = getWordType(paramString, paramInt1, paramInt2, --paramInt3, paramInt4); i == 0; i = getWordType(paramString, paramInt1, paramInt2, --paramInt3, paramInt4)) {}
    return i;
  }
  
  private static final int getWordType(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 < paramInt1) || (paramInt3 >= paramInt2)) {
      return 2;
    }
    return getWordType0(paramString.charAt(paramInt3), paramInt4);
  }
  
  private static final boolean regionMatches(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3)
  {
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    return paramString1.regionMatches(paramInt1, paramString2, 0, paramInt3);
  }
  
  private static final boolean regionMatches(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    return paramString.regionMatches(paramInt1, paramString, paramInt3, paramInt4);
  }
  
  private static final boolean regionMatchesIgnoreCase(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3)
  {
    return paramString1.regionMatches(true, paramInt1, paramString2, 0, paramInt3);
  }
  
  private static final boolean regionMatchesIgnoreCase(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    return paramString.regionMatches(true, paramInt1, paramString, paramInt3, paramInt4);
  }
  
  public boolean matches(CharacterIterator paramCharacterIterator)
  {
    return matches(paramCharacterIterator, (Match)null);
  }
  
  public boolean matches(CharacterIterator paramCharacterIterator, Match paramMatch)
  {
    int i = paramCharacterIterator.getBeginIndex();
    int j = paramCharacterIterator.getEndIndex();
    synchronized (this)
    {
      if (this.operations == null) {
        prepare();
      }
      if (this.context == null) {
        this.context = new Context();
      }
    }
    Context localContext1 = null;
    synchronized (this.context)
    {
      localContext1 = this.context.inuse ? new Context() : this.context;
      localContext1.reset(paramCharacterIterator, i, j, this.numberOfClosures);
    }
    if (paramMatch != null)
    {
      paramMatch.setNumberOfGroups(this.nofparen);
      paramMatch.setSource(paramCharacterIterator);
    }
    else if (this.hasBackReferences)
    {
      paramMatch = new Match();
      paramMatch.setNumberOfGroups(this.nofparen);
    }
    localContext1.match = paramMatch;
    if (isSet(this.options, 512))
    {
      k = matchCharacterIterator(localContext1, this.operations, localContext1.start, 1, this.options);
      if (k == localContext1.limit)
      {
        if (localContext1.match != null)
        {
          localContext1.match.setBeginning(0, localContext1.start);
          localContext1.match.setEnd(0, k);
        }
        localContext1.inuse = false;
        return true;
      }
      return false;
    }
    if (this.fixedStringOnly)
    {
      k = this.fixedStringTable.matches(paramCharacterIterator, localContext1.start, localContext1.limit);
      if (k >= 0)
      {
        if (localContext1.match != null)
        {
          localContext1.match.setBeginning(0, k);
          localContext1.match.setEnd(0, k + this.fixedString.length());
        }
        localContext1.inuse = false;
        return true;
      }
      localContext1.inuse = false;
      return false;
    }
    if (this.fixedString != null)
    {
      k = this.fixedStringTable.matches(paramCharacterIterator, localContext1.start, localContext1.limit);
      if (k < 0)
      {
        localContext1.inuse = false;
        return false;
      }
    }
    int k = localContext1.limit - this.minlength;
    int n = -1;
    int m;
    int i2;
    if ((this.operations != null) && (this.operations.type == 7) && (this.operations.getChild().type == 0))
    {
      if (isSet(this.options, 4))
      {
        m = localContext1.start;
        n = matchCharacterIterator(localContext1, this.operations, localContext1.start, 1, this.options);
      }
      else
      {
        int i1 = 1;
        for (m = localContext1.start; m <= k; m++)
        {
          i2 = paramCharacterIterator.setIndex(m);
          if (isEOLChar(i2))
          {
            i1 = 1;
          }
          else
          {
            if ((i1 != 0) && (0 <= (n = matchCharacterIterator(localContext1, this.operations, m, 1, this.options)))) {
              break;
            }
            i1 = 0;
          }
        }
      }
    }
    else if (this.firstChar != null)
    {
      RangeToken localRangeToken = this.firstChar;
      if (isSet(this.options, 2))
      {
        localRangeToken = this.firstChar.getCaseInsensitiveToken();
        for (m = localContext1.start; m <= k; m++)
        {
          i2 = paramCharacterIterator.setIndex(m);
          if ((REUtil.isHighSurrogate(i2)) && (m + 1 < localContext1.limit))
          {
            i2 = REUtil.composeFromSurrogates(i2, paramCharacterIterator.setIndex(m + 1));
            if (!localRangeToken.match(i2)) {
              continue;
            }
          }
          else if (!localRangeToken.match(i2))
          {
            char c = Character.toUpperCase((char)i2);
            if ((!localRangeToken.match(c)) && (!localRangeToken.match(Character.toLowerCase(c)))) {
              continue;
            }
          }
          if (0 <= (n = matchCharacterIterator(localContext1, this.operations, m, 1, this.options))) {
            break;
          }
        }
      }
      else
      {
        for (m = localContext1.start; m <= k; m++)
        {
          i2 = paramCharacterIterator.setIndex(m);
          if ((REUtil.isHighSurrogate(i2)) && (m + 1 < localContext1.limit)) {
            i2 = REUtil.composeFromSurrogates(i2, paramCharacterIterator.setIndex(m + 1));
          }
          if ((localRangeToken.match(i2)) && (0 <= (n = matchCharacterIterator(localContext1, this.operations, m, 1, this.options)))) {
            break;
          }
        }
      }
    }
    else
    {
      for (m = localContext1.start; m <= k; m++) {
        if (0 <= (n = matchCharacterIterator(localContext1, this.operations, m, 1, this.options))) {
          break;
        }
      }
    }
    if (n >= 0)
    {
      if (localContext1.match != null)
      {
        localContext1.match.setBeginning(0, m);
        localContext1.match.setEnd(0, n);
      }
      localContext1.inuse = false;
      return true;
    }
    localContext1.inuse = false;
    return false;
  }
  
  private int matchCharacterIterator(Context paramContext, Op paramOp, int paramInt1, int paramInt2, int paramInt3)
  {
    CharacterIterator localCharacterIterator = paramContext.ciTarget;
    for (;;)
    {
      if (paramOp == null) {
        return (isSet(paramInt3, 512)) && (paramInt1 != paramContext.limit) ? -1 : paramInt1;
      }
      if ((paramInt1 > paramContext.limit) || (paramInt1 < paramContext.start)) {
        return -1;
      }
      int i;
      int j;
      int k;
      int n;
      int i1;
      int m;
      int i2;
      switch (paramOp.type)
      {
      case 1: 
        if (isSet(paramInt3, 2))
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= paramContext.limit) || (!matchIgnoreCase(i, localCharacterIterator.setIndex(paramInt1)))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= paramContext.limit) || (j < 0) || (!matchIgnoreCase(i, localCharacterIterator.setIndex(j)))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        else
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= paramContext.limit) || (i != localCharacterIterator.setIndex(paramInt1))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= paramContext.limit) || (j < 0) || (i != localCharacterIterator.setIndex(j))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        paramOp = paramOp.next;
        break;
      case 0: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= paramContext.limit) {
            return -1;
          }
          i = localCharacterIterator.setIndex(paramInt1);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
              paramInt1++;
            }
          }
          else
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
              i = REUtil.composeFromSurrogates(i, localCharacterIterator.setIndex(++paramInt1));
            }
            if (isEOLChar(i)) {
              return -1;
            }
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= paramContext.limit) || (i < 0)) {
            return -1;
          }
          j = localCharacterIterator.setIndex(i);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              i--;
            }
          }
          else
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              j = REUtil.composeFromSurrogates(localCharacterIterator.setIndex(--i), j);
            }
            if (!isEOLChar(j)) {
              return -1;
            }
          }
          paramInt1 = i;
        }
        paramOp = paramOp.next;
        break;
      case 3: 
      case 4: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= paramContext.limit) {
            return -1;
          }
          i = localCharacterIterator.setIndex(paramInt1);
          if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < paramContext.limit)) {
            i = REUtil.composeFromSurrogates(i, localCharacterIterator.setIndex(++paramInt1));
          }
          RangeToken localRangeToken1 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken1 = localRangeToken1.getCaseInsensitiveToken();
            if (!localRangeToken1.match(i))
            {
              if (i >= 65536) {
                return -1;
              }
              char c1;
              if ((!localRangeToken1.match(c1 = Character.toUpperCase((char)i))) && (!localRangeToken1.match(Character.toLowerCase(c1)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken1.match(i))
          {
            return -1;
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= paramContext.limit) || (i < 0)) {
            return -1;
          }
          k = localCharacterIterator.setIndex(i);
          if ((REUtil.isLowSurrogate(k)) && (i - 1 >= 0)) {
            k = REUtil.composeFromSurrogates(localCharacterIterator.setIndex(--i), k);
          }
          RangeToken localRangeToken2 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken2 = localRangeToken2.getCaseInsensitiveToken();
            if (!localRangeToken2.match(k))
            {
              if (k >= 65536) {
                return -1;
              }
              char c2;
              if ((!localRangeToken2.match(c2 = Character.toUpperCase((char)k))) && (!localRangeToken2.match(Character.toLowerCase(c2)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken2.match(k))
          {
            return -1;
          }
          paramInt1 = i;
        }
        paramOp = paramOp.next;
        break;
      case 5: 
        i = 0;
        switch (paramOp.getData())
        {
        case 94: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != paramContext.start) && ((paramInt1 <= paramContext.start) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1 - 1))))) {
              return -1;
            }
          }
          else if (paramInt1 != paramContext.start) {
            return -1;
          }
          break;
        case 64: 
          if ((paramInt1 != paramContext.start) && ((paramInt1 <= paramContext.start) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1 - 1))))) {
            return -1;
          }
          break;
        case 36: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != paramContext.limit) && ((paramInt1 >= paramContext.limit) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1))))) {
              return -1;
            }
          }
          else if ((paramInt1 != paramContext.limit) && ((paramInt1 + 1 != paramContext.limit) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1)))) && ((paramInt1 + 2 != paramContext.limit) || (localCharacterIterator.setIndex(paramInt1) != '\r') || (localCharacterIterator.setIndex(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 65: 
          if (paramInt1 != paramContext.start) {
            return -1;
          }
          break;
        case 90: 
          if ((paramInt1 != paramContext.limit) && ((paramInt1 + 1 != paramContext.limit) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1)))) && ((paramInt1 + 2 != paramContext.limit) || (localCharacterIterator.setIndex(paramInt1) != '\r') || (localCharacterIterator.setIndex(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 122: 
          if (paramInt1 != paramContext.limit) {
            return -1;
          }
          break;
        case 98: 
          if (paramContext.length == 0) {
            return -1;
          }
          k = getWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3);
          if (k == 0) {
            return -1;
          }
          n = getPreviousWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3);
          if (k == n) {
            return -1;
          }
          break;
        case 66: 
          if (paramContext.length == 0)
          {
            i = 1;
          }
          else
          {
            k = getWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3);
            i = (k == 0) || (k == getPreviousWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3)) ? 1 : 0;
          }
          if (i == 0) {
            return -1;
          }
          break;
        case 60: 
          if ((paramContext.length == 0) || (paramInt1 == paramContext.limit)) {
            return -1;
          }
          if ((getWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 1) || (getPreviousWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 2)) {
            return -1;
          }
          break;
        case 62: 
          if ((paramContext.length == 0) || (paramInt1 == paramContext.start)) {
            return -1;
          }
          if ((getWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 2) || (getPreviousWordType(localCharacterIterator, paramContext.start, paramContext.limit, paramInt1, paramInt3) != 1)) {
            return -1;
          }
          break;
        }
        paramOp = paramOp.next;
        break;
      case 16: 
        k = paramOp.getData();
        if ((k <= 0) || (k >= this.nofparen)) {
          throw new RuntimeException("Internal Error: Reference number must be more than zero: " + k);
        }
        if ((paramContext.match.getBeginning(k) < 0) || (paramContext.match.getEnd(k) < 0)) {
          return -1;
        }
        n = paramContext.match.getBeginning(k);
        i1 = paramContext.match.getEnd(k) - n;
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(localCharacterIterator, paramInt1, paramContext.limit, n, i1)) {
              return -1;
            }
            paramInt1 += i1;
          }
          else
          {
            if (!regionMatches(localCharacterIterator, paramInt1 - i1, paramContext.limit, n, i1)) {
              return -1;
            }
            paramInt1 -= i1;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1, paramContext.limit, n, i1)) {
            return -1;
          }
          paramInt1 += i1;
        }
        else
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1 - i1, paramContext.limit, n, i1)) {
            return -1;
          }
          paramInt1 -= i1;
        }
        paramOp = paramOp.next;
        break;
      case 6: 
        String str = paramOp.getString();
        n = str.length();
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(localCharacterIterator, paramInt1, paramContext.limit, str, n)) {
              return -1;
            }
            paramInt1 += n;
          }
          else
          {
            if (!regionMatches(localCharacterIterator, paramInt1 - n, paramContext.limit, str, n)) {
              return -1;
            }
            paramInt1 -= n;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1, paramContext.limit, str, n)) {
            return -1;
          }
          paramInt1 += n;
        }
        else
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1 - n, paramContext.limit, str, n)) {
            return -1;
          }
          paramInt1 -= n;
        }
        paramOp = paramOp.next;
        break;
      case 7: 
        m = paramOp.getData();
        if (m >= 0)
        {
          n = paramContext.offsets[m];
          if ((n < 0) || (n != paramInt1))
          {
            paramContext.offsets[m] = paramInt1;
          }
          else
          {
            paramContext.offsets[m] = -1;
            paramOp = paramOp.next;
            continue;
          }
        }
        n = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          paramContext.offsets[m] = -1;
        }
        if (n >= 0) {
          return n;
        }
        paramOp = paramOp.next;
        break;
      case 9: 
        m = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.next;
        break;
      case 8: 
      case 10: 
        m = matchCharacterIterator(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.getChild();
        break;
      case 11: 
        for (m = 0; m < paramOp.size(); m++)
        {
          n = matchCharacterIterator(paramContext, paramOp.elementAt(m), paramInt1, paramInt2, paramInt3);
          if (n >= 0) {
            return n;
          }
        }
        return -1;
      case 15: 
        n = paramOp.getData();
        if ((paramContext.match != null) && (n > 0))
        {
          i1 = paramContext.match.getBeginning(n);
          paramContext.match.setBeginning(n, paramInt1);
          i2 = matchCharacterIterator(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
          if (i2 < 0) {
            paramContext.match.setBeginning(n, i1);
          }
          return i2;
        }
        if ((paramContext.match != null) && (n < 0))
        {
          i1 = -n;
          i2 = paramContext.match.getEnd(i1);
          paramContext.match.setEnd(i1, paramInt1);
          int i3 = matchCharacterIterator(paramContext, paramOp.next, paramInt1, paramInt2, paramInt3);
          if (i3 < 0) {
            paramContext.match.setEnd(i1, i2);
          }
          return i3;
        }
        paramOp = paramOp.next;
        break;
      case 20: 
        if (0 > matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 21: 
        if (0 <= matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 22: 
        if (0 > matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 23: 
        if (0 <= matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = paramOp.next;
        break;
      case 24: 
        i1 = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (i1 < 0) {
          return i1;
        }
        paramInt1 = i1;
        paramOp = paramOp.next;
        break;
      case 25: 
        i1 = paramInt3;
        i1 |= paramOp.getData();
        i1 &= (paramOp.getData2() ^ 0xFFFFFFFF);
        i2 = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, i1);
        if (i2 < 0) {
          return i2;
        }
        paramInt1 = i2;
        paramOp = paramOp.next;
        break;
      case 26: 
        Op.ConditionOp localConditionOp = (Op.ConditionOp)paramOp;
        i2 = 0;
        if (localConditionOp.refNumber > 0)
        {
          if (localConditionOp.refNumber >= this.nofparen) {
            throw new RuntimeException("Internal Error: Reference number must be more than zero: " + localConditionOp.refNumber);
          }
          i2 = (paramContext.match.getBeginning(localConditionOp.refNumber) >= 0) && (paramContext.match.getEnd(localConditionOp.refNumber) >= 0) ? 1 : 0;
        }
        else
        {
          i2 = 0 <= matchCharacterIterator(paramContext, localConditionOp.condition, paramInt1, paramInt2, paramInt3) ? 1 : 0;
        }
        if (i2 != 0) {
          paramOp = localConditionOp.yes;
        } else if (localConditionOp.no != null) {
          paramOp = localConditionOp.no;
        } else {
          paramOp = localConditionOp.next;
        }
        break;
      }
    }
    throw new RuntimeException("Unknown operation type: " + paramOp.type);
  }
  
  private static final int getPreviousWordType(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = getWordType(paramCharacterIterator, paramInt1, paramInt2, --paramInt3, paramInt4); i == 0; i = getWordType(paramCharacterIterator, paramInt1, paramInt2, --paramInt3, paramInt4)) {}
    return i;
  }
  
  private static final int getWordType(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 < paramInt1) || (paramInt3 >= paramInt2)) {
      return 2;
    }
    return getWordType0(paramCharacterIterator.setIndex(paramInt3), paramInt4);
  }
  
  private static final boolean regionMatches(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0) {
      if (paramCharacterIterator.setIndex(paramInt1++) != paramString.charAt(i++)) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatches(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0) {
      if (paramCharacterIterator.setIndex(paramInt1++) != paramCharacterIterator.setIndex(i++)) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0)
    {
      char c1 = paramCharacterIterator.setIndex(paramInt1++);
      char c2 = paramString.charAt(i++);
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0)
    {
      char c1 = paramCharacterIterator.setIndex(paramInt1++);
      char c2 = paramCharacterIterator.setIndex(i++);
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  void prepare()
  {
    compile(this.tokentree);
    this.minlength = this.tokentree.getMinLength();
    this.firstChar = null;
    Object localObject;
    if ((!isSet(this.options, 128)) && (!isSet(this.options, 512)))
    {
      localObject = Token.createRange();
      int i = this.tokentree.analyzeFirstCharacter((RangeToken)localObject, this.options);
      if (i == 1)
      {
        ((RangeToken)localObject).compactRanges();
        this.firstChar = ((RangeToken)localObject);
      }
    }
    if ((this.operations != null) && ((this.operations.type == 6) || (this.operations.type == 1)) && (this.operations.next == null))
    {
      this.fixedStringOnly = true;
      if (this.operations.type == 6)
      {
        this.fixedString = this.operations.getString();
      }
      else if (this.operations.getData() >= 65536)
      {
        this.fixedString = REUtil.decomposeToSurrogates(this.operations.getData());
      }
      else
      {
        localObject = new char[1];
        localObject[0] = ((char)this.operations.getData());
        this.fixedString = new String((char[])localObject);
      }
      this.fixedStringOptions = this.options;
      this.fixedStringTable = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
    }
    else if ((!isSet(this.options, 256)) && (!isSet(this.options, 512)))
    {
      localObject = new Token.FixedStringContainer();
      this.tokentree.findFixedString((Token.FixedStringContainer)localObject, this.options);
      this.fixedString = (((Token.FixedStringContainer)localObject).token == null ? null : ((Token.FixedStringContainer)localObject).token.getString());
      this.fixedStringOptions = ((Token.FixedStringContainer)localObject).options;
      if ((this.fixedString != null) && (this.fixedString.length() < 2)) {
        this.fixedString = null;
      }
      if (this.fixedString != null) {
        this.fixedStringTable = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
      }
    }
  }
  
  private static final boolean isSet(int paramInt1, int paramInt2)
  {
    return (paramInt1 & paramInt2) == paramInt2;
  }
  
  public RegularExpression(String paramString)
    throws ParseException
  {
    setPattern(paramString, null);
  }
  
  public RegularExpression(String paramString1, String paramString2)
    throws ParseException
  {
    setPattern(paramString1, paramString2);
  }
  
  RegularExpression(String paramString, Token paramToken, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    this.regex = paramString;
    this.tokentree = paramToken;
    this.nofparen = paramInt1;
    this.options = paramInt2;
    this.hasBackReferences = paramBoolean;
  }
  
  public void setPattern(String paramString)
    throws ParseException
  {
    setPattern(paramString, this.options);
  }
  
  private void setPattern(String paramString, int paramInt)
    throws ParseException
  {
    this.regex = paramString;
    this.options = paramInt;
    RegexParser localRegexParser = isSet(this.options, 512) ? new ParserForXMLSchema() : new RegexParser();
    this.tokentree = localRegexParser.parse(this.regex, this.options);
    this.nofparen = localRegexParser.parennumber;
    this.hasBackReferences = localRegexParser.hasBackReferences;
    this.operations = null;
    this.context = null;
  }
  
  public void setPattern(String paramString1, String paramString2)
    throws ParseException
  {
    setPattern(paramString1, REUtil.parseOptions(paramString2));
  }
  
  public String getPattern()
  {
    return this.regex;
  }
  
  public String toString()
  {
    return this.tokentree.toString(this.options);
  }
  
  public String getOptions()
  {
    return REUtil.createOptionString(this.options);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof RegularExpression)) {
      return false;
    }
    RegularExpression localRegularExpression = (RegularExpression)paramObject;
    return (this.regex.equals(localRegularExpression.regex)) && (this.options == localRegularExpression.options);
  }
  
  boolean equals(String paramString, int paramInt)
  {
    return (this.regex.equals(paramString)) && (this.options == paramInt);
  }
  
  public int hashCode()
  {
    return (this.regex + "/" + getOptions()).hashCode();
  }
  
  public int getNumberOfGroups()
  {
    return this.nofparen;
  }
  
  private static final int getWordType0(char paramChar, int paramInt)
  {
    if (!isSet(paramInt, 64))
    {
      if (isSet(paramInt, 32)) {
        return Token.getRange("IsWord", true).match(paramChar) ? 1 : 2;
      }
      return isWordChar(paramChar) ? 1 : 2;
    }
    switch (Character.getType(paramChar))
    {
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
      return 1;
    case 6: 
    case 7: 
    case 16: 
      return 0;
    case 15: 
      switch (paramChar)
      {
      case '\t': 
      case '\n': 
      case '\013': 
      case '\f': 
      case '\r': 
        return 2;
      }
      return 0;
    }
    return 2;
  }
  
  private static final boolean isEOLChar(int paramInt)
  {
    return (paramInt == 10) || (paramInt == 13) || (paramInt == 8232) || (paramInt == 8233);
  }
  
  private static final boolean isWordChar(int paramInt)
  {
    if (paramInt == 95) {
      return true;
    }
    if (paramInt < 48) {
      return false;
    }
    if (paramInt > 122) {
      return false;
    }
    if (paramInt <= 57) {
      return true;
    }
    if (paramInt < 65) {
      return false;
    }
    if (paramInt <= 90) {
      return true;
    }
    return paramInt >= 97;
  }
  
  private static final boolean matchIgnoreCase(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {
      return true;
    }
    if ((paramInt1 > 65535) || (paramInt2 > 65535)) {
      return false;
    }
    char c1 = Character.toUpperCase((char)paramInt1);
    char c2 = Character.toUpperCase((char)paramInt2);
    if (c1 == c2) {
      return true;
    }
    return Character.toLowerCase(c1) == Character.toLowerCase(c2);
  }
  
  static final class Context
  {
    CharacterIterator ciTarget;
    String strTarget;
    char[] charTarget;
    int start;
    int limit;
    int length;
    Match match;
    boolean inuse = false;
    int[] offsets;
    
    private void resetCommon(int paramInt)
    {
      this.length = (this.limit - this.start);
      this.inuse = true;
      this.match = null;
      if ((this.offsets == null) || (this.offsets.length != paramInt)) {
        this.offsets = new int[paramInt];
      }
      for (int i = 0; i < paramInt; i++) {
        this.offsets[i] = -1;
      }
    }
    
    void reset(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3)
    {
      this.ciTarget = paramCharacterIterator;
      this.start = paramInt1;
      this.limit = paramInt2;
      resetCommon(paramInt3);
    }
    
    void reset(String paramString, int paramInt1, int paramInt2, int paramInt3)
    {
      this.strTarget = paramString;
      this.start = paramInt1;
      this.limit = paramInt2;
      resetCommon(paramInt3);
    }
    
    void reset(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
    {
      this.charTarget = paramArrayOfChar;
      this.start = paramInt1;
      this.limit = paramInt2;
      resetCommon(paramInt3);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xpath.regex.RegularExpression
 * JD-Core Version:    0.7.0.1
 */