package org.apache.xerces.impl.xpath.regex;

import java.text.CharacterIterator;

public class Match
  implements Cloneable
{
  int[] beginpos = null;
  int[] endpos = null;
  int nofgroups = 0;
  CharacterIterator ciSource = null;
  String strSource = null;
  char[] charSource = null;
  
  public synchronized Object clone()
  {
    Match localMatch = new Match();
    if (this.nofgroups > 0)
    {
      localMatch.setNumberOfGroups(this.nofgroups);
      if (this.ciSource != null) {
        localMatch.setSource(this.ciSource);
      }
      if (this.strSource != null) {
        localMatch.setSource(this.strSource);
      }
      for (int i = 0; i < this.nofgroups; i++)
      {
        localMatch.setBeginning(i, getBeginning(i));
        localMatch.setEnd(i, getEnd(i));
      }
    }
    return localMatch;
  }
  
  protected void setNumberOfGroups(int paramInt)
  {
    int i = this.nofgroups;
    this.nofgroups = paramInt;
    if ((i <= 0) || (i < paramInt) || (paramInt * 2 < i))
    {
      this.beginpos = new int[paramInt];
      this.endpos = new int[paramInt];
    }
    for (int j = 0; j < paramInt; j++)
    {
      this.beginpos[j] = -1;
      this.endpos[j] = -1;
    }
  }
  
  protected void setSource(CharacterIterator paramCharacterIterator)
  {
    this.ciSource = paramCharacterIterator;
    this.strSource = null;
    this.charSource = null;
  }
  
  protected void setSource(String paramString)
  {
    this.ciSource = null;
    this.strSource = paramString;
    this.charSource = null;
  }
  
  protected void setSource(char[] paramArrayOfChar)
  {
    this.ciSource = null;
    this.strSource = null;
    this.charSource = paramArrayOfChar;
  }
  
  protected void setBeginning(int paramInt1, int paramInt2)
  {
    this.beginpos[paramInt1] = paramInt2;
  }
  
  protected void setEnd(int paramInt1, int paramInt2)
  {
    this.endpos[paramInt1] = paramInt2;
  }
  
  public int getNumberOfGroups()
  {
    if (this.nofgroups <= 0) {
      throw new IllegalStateException("A result is not set.");
    }
    return this.nofgroups;
  }
  
  public int getBeginning(int paramInt)
  {
    if (this.beginpos == null) {
      throw new IllegalStateException("A result is not set.");
    }
    if ((paramInt < 0) || (this.nofgroups <= paramInt)) {
      throw new IllegalArgumentException("The parameter must be less than " + this.nofgroups + ": " + paramInt);
    }
    return this.beginpos[paramInt];
  }
  
  public int getEnd(int paramInt)
  {
    if (this.endpos == null) {
      throw new IllegalStateException("A result is not set.");
    }
    if ((paramInt < 0) || (this.nofgroups <= paramInt)) {
      throw new IllegalArgumentException("The parameter must be less than " + this.nofgroups + ": " + paramInt);
    }
    return this.endpos[paramInt];
  }
  
  public String getCapturedText(int paramInt)
  {
    if (this.beginpos == null) {
      throw new IllegalStateException("match() has never been called.");
    }
    if ((paramInt < 0) || (this.nofgroups <= paramInt)) {
      throw new IllegalArgumentException("The parameter must be less than " + this.nofgroups + ": " + paramInt);
    }
    int i = this.beginpos[paramInt];
    int j = this.endpos[paramInt];
    if ((i < 0) || (j < 0)) {
      return null;
    }
    String str;
    if (this.ciSource != null) {
      str = REUtil.substring(this.ciSource, i, j);
    } else if (this.strSource != null) {
      str = this.strSource.substring(i, j);
    } else {
      str = new String(this.charSource, i, j - i);
    }
    return str;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xpath.regex.Match
 * JD-Core Version:    0.7.0.1
 */