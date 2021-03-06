package org.apache.xerces.impl.xs.models;

import java.util.Vector;
import org.apache.xerces.impl.xs.SubstitutionGroupHandler;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.xni.QName;

public class XSEmptyCM
  implements XSCMValidator
{
  private static final short STATE_START = 0;
  private static final Vector EMPTY = new Vector(0);
  
  public int[] startContentModel()
  {
    return new int[] { 0 };
  }
  
  public Object oneTransition(QName paramQName, int[] paramArrayOfInt, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    if (paramArrayOfInt[0] < 0)
    {
      paramArrayOfInt[0] = -2;
      return null;
    }
    paramArrayOfInt[0] = -1;
    return null;
  }
  
  public boolean endContentModel(int[] paramArrayOfInt)
  {
    int i = 0;
    int j = paramArrayOfInt[0];
    return j >= 0;
  }
  
  public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler paramSubstitutionGroupHandler)
    throws XMLSchemaException
  {
    return false;
  }
  
  public Vector whatCanGoHere(int[] paramArrayOfInt)
  {
    return EMPTY;
  }
  
  public boolean isCompactedForUPA()
  {
    return false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.XSEmptyCM
 * JD-Core Version:    0.7.0.1
 */