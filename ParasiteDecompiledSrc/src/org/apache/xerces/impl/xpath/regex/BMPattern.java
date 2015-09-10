package org.apache.xerces.impl.xpath.regex;

import java.text.CharacterIterator;

public class BMPattern
{
  final char[] pattern;
  final int[] shiftTable;
  final boolean ignoreCase;
  
  public BMPattern(String paramString, boolean paramBoolean)
  {
    this(paramString, 256, paramBoolean);
  }
  
  public BMPattern(String paramString, int paramInt, boolean paramBoolean)
  {
    this.pattern = paramString.toCharArray();
    this.shiftTable = new int[paramInt];
    this.ignoreCase = paramBoolean;
    int i = this.pattern.length;
    for (int j = 0; j < this.shiftTable.length; j++) {
      this.shiftTable[j] = i;
    }
    for (int k = 0; k < i; k++)
    {
      int m = this.pattern[k];
      int i2 = i - k - 1;
      int i3 = m % this.shiftTable.length;
      if (i2 < this.shiftTable[i3]) {
        this.shiftTable[i3] = i2;
      }
      if (this.ignoreCase)
      {
        int n = Character.toUpperCase(m);
        i3 = n % this.shiftTable.length;
        if (i2 < this.shiftTable[i3]) {
          this.shiftTable[i3] = i2;
        }
        int i1 = Character.toLowerCase(n);
        i3 = i1 % this.shiftTable.length;
        if (i2 < this.shiftTable[i3]) {
          this.shiftTable[i3] = i2;
        }
      }
    }
  }
  
  public int matches(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2)
  {
    if (this.ignoreCase) {
      return matchesIgnoreCase(paramCharacterIterator, paramInt1, paramInt2);
    }
    int i = this.pattern.length;
    if (i == 0) {
      return paramInt1;
    }
    int j = paramInt1 + i;
    while (j <= paramInt2)
    {
      int k = i;
      int m = j + 1;
      int n;
      do
      {
        if ((n = paramCharacterIterator.setIndex(--j)) != this.pattern[(--k)]) {
          break;
        }
        if (k == 0) {
          return j;
        }
      } while (k > 0);
      j += this.shiftTable[(n % this.shiftTable.length)] + 1;
      if (j < m) {
        j = m;
      }
    }
    return -1;
  }
  
  public int matches(String paramString, int paramInt1, int paramInt2)
  {
    if (this.ignoreCase) {
      return matchesIgnoreCase(paramString, paramInt1, paramInt2);
    }
    int i = this.pattern.length;
    if (i == 0) {
      return paramInt1;
    }
    int j = paramInt1 + i;
    while (j <= paramInt2)
    {
      int k = i;
      int m = j + 1;
      int n;
      do
      {
        if ((n = paramString.charAt(--j)) != this.pattern[(--k)]) {
          break;
        }
        if (k == 0) {
          return j;
        }
      } while (k > 0);
      j += this.shiftTable[(n % this.shiftTable.length)] + 1;
      if (j < m) {
        j = m;
      }
    }
    return -1;
  }
  
  public int matches(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (this.ignoreCase) {
      return matchesIgnoreCase(paramArrayOfChar, paramInt1, paramInt2);
    }
    int i = this.pattern.length;
    if (i == 0) {
      return paramInt1;
    }
    int j = paramInt1 + i;
    while (j <= paramInt2)
    {
      int k = i;
      int m = j + 1;
      int n;
      do
      {
        if ((n = paramArrayOfChar[(--j)]) != this.pattern[(--k)]) {
          break;
        }
        if (k == 0) {
          return j;
        }
      } while (k > 0);
      j += this.shiftTable[(n % this.shiftTable.length)] + 1;
      if (j < m) {
        j = m;
      }
    }
    return -1;
  }
  
  int matchesIgnoreCase(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2)
  {
    int i = this.pattern.length;
    if (i == 0) {
      return paramInt1;
    }
    int j = paramInt1 + i;
    while (j <= paramInt2)
    {
      int k = i;
      int m = j + 1;
      int n;
      do
      {
        char c1 = n = paramCharacterIterator.setIndex(--j);
        char c2 = this.pattern[(--k)];
        if (c1 != c2)
        {
          c1 = Character.toUpperCase(c1);
          c2 = Character.toUpperCase(c2);
          if ((c1 != c2) && (Character.toLowerCase(c1) != Character.toLowerCase(c2))) {
            break;
          }
        }
        if (k == 0) {
          return j;
        }
      } while (k > 0);
      j += this.shiftTable[(n % this.shiftTable.length)] + 1;
      if (j < m) {
        j = m;
      }
    }
    return -1;
  }
  
  int matchesIgnoreCase(String paramString, int paramInt1, int paramInt2)
  {
    int i = this.pattern.length;
    if (i == 0) {
      return paramInt1;
    }
    int j = paramInt1 + i;
    while (j <= paramInt2)
    {
      int k = i;
      int m = j + 1;
      int n;
      do
      {
        char c1 = n = paramString.charAt(--j);
        char c2 = this.pattern[(--k)];
        if (c1 != c2)
        {
          c1 = Character.toUpperCase(c1);
          c2 = Character.toUpperCase(c2);
          if ((c1 != c2) && (Character.toLowerCase(c1) != Character.toLowerCase(c2))) {
            break;
          }
        }
        if (k == 0) {
          return j;
        }
      } while (k > 0);
      j += this.shiftTable[(n % this.shiftTable.length)] + 1;
      if (j < m) {
        j = m;
      }
    }
    return -1;
  }
  
  int matchesIgnoreCase(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = this.pattern.length;
    if (i == 0) {
      return paramInt1;
    }
    int j = paramInt1 + i;
    while (j <= paramInt2)
    {
      int k = i;
      int m = j + 1;
      int n;
      do
      {
        char c1 = n = paramArrayOfChar[(--j)];
        char c2 = this.pattern[(--k)];
        if (c1 != c2)
        {
          c1 = Character.toUpperCase(c1);
          c2 = Character.toUpperCase(c2);
          if ((c1 != c2) && (Character.toLowerCase(c1) != Character.toLowerCase(c2))) {
            break;
          }
        }
        if (k == 0) {
          return j;
        }
      } while (k > 0);
      j += this.shiftTable[(n % this.shiftTable.length)] + 1;
      if (j < m) {
        j = m;
      }
    }
    return -1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xpath.regex.BMPattern
 * JD-Core Version:    0.7.0.1
 */