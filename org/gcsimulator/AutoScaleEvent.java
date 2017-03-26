 package org.gcsimulator;
 
 import java.util.EventObject;
 
 
 
 
 public class AutoScaleEvent
   extends EventObject
 {
   private static final long serialVersionUID = 1L;
   private boolean m_bAutoScaleXState;
   private boolean m_bAutoScaleYState;
   
   public AutoScaleEvent(Object source, boolean bAutoScaleXState, boolean bAutoScaleYState)
   {
/* 17 */     super(source);
/* 18 */     this.m_bAutoScaleXState = bAutoScaleXState;
/* 19 */     this.m_bAutoScaleYState = bAutoScaleYState;
   }
   
   public boolean getAutoScaleXState()
   {
/* 24 */     return this.m_bAutoScaleXState;
   }
   
   public boolean getAutoScaleYState()
   {
/* 29 */     return this.m_bAutoScaleYState;
   }
 }
