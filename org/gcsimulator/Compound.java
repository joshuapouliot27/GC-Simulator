 package org.gcsimulator;
 
 import java.io.IOException;
 import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
 import java.io.Serializable;
 import java.util.Random;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 class Compound
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   String strCompoundName;
   double dConcentration;
   InterpolationFunction InterpolatedLogkvsT;
   double dMolarVolume;
   int iStationaryPhase;
   double dRetentionTime;
   double dSigma;
   double dW;
   int iCompoundIndex;
   
   public void createRandomCompound(String strName, double dMinConcentration, double dMaxConcentration)
   {
/* 131 */     Random rand = new Random();
     
/* 133 */     this.iCompoundIndex = -1;
/* 134 */     this.strCompoundName = strName;
/* 135 */     this.dMolarVolume = (rand.nextDouble() * 100.0D + 90.0D);
     
 
 
 
 
 
 
 
 
 
 
 
 
 
/* 150 */     double dLogConcentration = rand.nextDouble() * Math.log10(dMaxConcentration / dMinConcentration) + Math.log10(dMinConcentration);
/* 151 */     this.dConcentration = Math.pow(10.0D, dLogConcentration);
   }
   
   public boolean loadCompoundInfo(int iStationaryPhase, int iIndex)
   {
/* 156 */     this.iCompoundIndex = iIndex;
/* 157 */     this.iStationaryPhase = iStationaryPhase;
     
/* 159 */     this.strCompoundName = Globals.CompoundNameArray[iStationaryPhase][iIndex];
     
/* 161 */     this.InterpolatedLogkvsT = new InterpolationFunction(Globals.CompoundIsothermalDataArray[0][iIndex]);
     
 
 
/* 165 */     return true;
   }
   
   private void writeObject(ObjectOutputStream out) throws IOException
   {
/* 170 */     out.writeLong(1L);
     
/* 172 */     out.writeObject(this.strCompoundName);
/* 173 */     out.writeDouble(this.dConcentration);
     
/* 175 */     out.writeObject(this.InterpolatedLogkvsT);
     
/* 177 */     out.writeDouble(this.dMolarVolume);
     
/* 179 */     out.writeInt(this.iStationaryPhase);
/* 180 */     out.writeInt(this.iCompoundIndex);
   }
   
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
   {
/* 185 */     long lVersion = in.readLong();
     
/* 187 */     if (lVersion >= 1L)
     {
/* 189 */       this.strCompoundName = ((String)in.readObject());
/* 190 */       this.dConcentration = in.readDouble();
       
/* 192 */       this.InterpolatedLogkvsT = ((InterpolationFunction)in.readObject());
       
/* 194 */       this.dMolarVolume = in.readDouble();
       
/* 196 */       this.iStationaryPhase = in.readInt();
/* 197 */       this.iCompoundIndex = in.readInt();
     }
   }
 }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/Compound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */