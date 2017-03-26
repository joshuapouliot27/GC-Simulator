/*      */ package org.gcsimulator;
/*      */ 
/*      */ import com.jogamp.opengl.util.awt.TextRenderer;
/*      */ import java.awt.Color;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.media.opengl.GL;
/*      */ import javax.media.opengl.GL2;
/*      */ import javax.media.opengl.GLAutoDrawable;
/*      */ import javax.media.opengl.GLCapabilities;
/*      */ import javax.media.opengl.GLEventListener;
/*      */ import javax.media.opengl.awt.GLCanvas;
/*      */ import javax.media.opengl.glu.GLU;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GraphControl
/*      */   extends GLCanvas
/*      */   implements GLEventListener, MouseListener, MouseMotionListener
/*      */ {
/*      */   public class LineMarker
/*      */   {
/*   44 */     String strMarkerName = "";
/*   45 */     double dTime = 0.0D;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public LineMarker() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public class DataSeries
/*      */   {
/*      */     int iIndex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*   66 */     String strName = "";
/*   67 */     Color clrLineColor = new Color(0, 0, 0);
/*   68 */     int iLineThickness = 1;
/*   69 */     Vector<GraphControl.DPoint> vectDataArray = new Vector();
/*   70 */     boolean bOnlyMarkers = false;
/*   71 */     boolean bUseSecondScale = false;
/*      */     double dXMin;
/*      */     double dYMin;
/*      */     double dXMax;
/*      */     double dYMax;
/*      */     
/*      */     public DataSeries() {} }
/*      */   
/*      */   public class DRect { double left;
/*      */     double right;
/*      */     double bottom;
/*      */     double top;
/*      */     
/*   84 */     public DRect() { this.left = 0.0D;
/*   85 */       this.right = 0.0D;
/*   86 */       this.bottom = 0.0D;
/*   87 */       this.top = 0.0D;
/*      */     }
/*      */     
/*      */     public DRect(DRect r)
/*      */     {
/*   92 */       this.left = r.left;
/*   93 */       this.right = r.right;
/*   94 */       this.top = r.top;
/*   95 */       this.bottom = r.bottom;
/*      */     }
/*      */     
/*      */     public double getHeight()
/*      */     {
/*  100 */       return Math.abs(this.top - this.bottom);
/*      */     }
/*      */     
/*      */     public double getWidth()
/*      */     {
/*  105 */       return Math.abs(this.right - this.left);
/*      */     }
/*      */   }
/*      */   
/*  109 */   static double NANOSECONDS = 1.0D;
/*  110 */   static double MICROSECONDS = 1000.0D;
/*  111 */   static double MILLISECONDS = 1000000.0D;
/*  112 */   static double SECONDS = 1.0E9D;
/*  113 */   static double MINUTES = 60.0D * SECONDS;
/*  114 */   static double HOURS = 60.0D * MINUTES;
/*  115 */   static double DAYS = 24.0D * HOURS;
/*  116 */   static double YEARS = 365.0D * DAYS;
/*      */   
/*  118 */   static double NANOUNITS = 1.0D;
/*  119 */   static double MICROUNITS = 1000.0D * NANOUNITS;
/*  120 */   static double MILLIUNITS = 1000.0D * MICROUNITS;
/*  121 */   static double UNITS = 1000.0D * MILLIUNITS;
/*  122 */   static double KILOUNITS = 1000.0D * UNITS;
/*  123 */   static double MEGAUNITS = 1000.0D * KILOUNITS;
/*      */   
/*  125 */   boolean m_bCopyImage = false;
/*  126 */   ByteBuffer m_ByteBuffer = null;
/*      */   
/*  128 */   DRect m_drectView = new DRect();
/*  129 */   DRect m_rectGraph = new DRect();
/*      */   
/*      */   double m_dXMultiplier;
/*      */   double m_dYMultiplier;
/*      */   double m_dSecondYMultiplier;
/*      */   double m_dInvXMultiplier;
/*      */   double m_dInvYMultiplier;
/*      */   double m_dSecondInvYMultiplier;
/*      */   int m_iMajorUnitTypeX;
/*      */   double m_dMajorUnitXTypeValue;
/*      */   double m_dNextMajorUnitXTypeValue;
/*      */   double m_dNextNextMajorUnitXTypeValue;
/*  141 */   String m_strXAxisLabel = "";
/*  142 */   String m_strXAxisLabelShort = "";
/*  143 */   boolean m_bXAxisRangeIndicatorsVisible = true;
/*      */   
/*      */   int m_iMajorUnitTypeY;
/*      */   double m_dMajorUnitYTypeValue;
/*      */   double m_dNextMajorUnitYTypeValue;
/*      */   double m_dNextNextMajorUnitYTypeValue;
/*  149 */   String m_strYAxisLabel = "";
/*  150 */   String m_strYAxisLabelShort = "";
/*  151 */   String m_strYAxisTitle = "Signal";
/*  152 */   String m_strYAxisBaseUnit = "units";
/*  153 */   String m_strYAxisBaseUnitShort = "U";
/*  154 */   boolean m_bYAxisRangeIndicatorsVisible = true;
/*  155 */   boolean m_bControlsEnabled = true;
/*      */   
/*  157 */   double m_dYAxisUpperLimit = 9.0D * MEGAUNITS;
/*  158 */   double m_dYAxisLowerLimit = -9.0D * MEGAUNITS;
/*  159 */   double m_dSecondYAxisUpperLimit = 9.0D * MEGAUNITS;
/*  160 */   double m_dSecondYAxisLowerLimit = -9.0D * MEGAUNITS;
/*      */   
/*      */   int m_iSecondMajorUnitTypeY;
/*      */   double m_dSecondMajorUnitYTypeValue;
/*      */   double m_dSecondNextMajorUnitYTypeValue;
/*      */   double m_dSecondNextNextMajorUnitYTypeValue;
/*  166 */   String m_strSecondYAxisLabel = "";
/*  167 */   String m_strSecondYAxisLabelShort = "";
/*  168 */   boolean m_bSecondYAxisVisible = false;
/*  169 */   String m_strSecondYAxisTitle = "Solvent B Fraction";
/*  170 */   String m_strSecondYAxisBaseUnit = "% v/v";
/*  171 */   String m_strSecondYAxisBaseUnitShort = "%";
/*      */   
/*  173 */   Vector<DataSeries> m_vectDataSeries = new Vector();
/*  174 */   Vector<LineMarker> m_vectLineMarkers = new Vector();
/*      */   
/*  176 */   static GLU glu = new GLU();
/*      */   
/*  178 */   static Font m_fontXAxisLabel = new Font("Dialog", 1, 12);
/*  179 */   static Font m_fontYAxisLabel = new Font("Dialog", 1, 12);
/*  180 */   static Font m_fontXAxisDivision = new Font("Dialog", 0, 12);
/*  181 */   static Font m_fontYAxisDivision = new Font("Dialog", 0, 12);
/*      */   
/*  183 */   static int JUSTIFY_LEFT = 0;
/*  184 */   static int JUSTIFY_CENTER = 1;
/*  185 */   static int JUSTIFY_RIGHT = 2;
/*      */   
/*      */   TextRenderer m_rendererXAxisLabel;
/*      */   
/*      */   TextRenderer m_rendererYAxisLabel;
/*      */   TextRenderer m_rendererXAxisDivision;
/*      */   TextRenderer m_rendererYAxisDivision;
/*  192 */   boolean m_bTranslating = false;
/*  193 */   boolean m_bZooming = false;
/*  194 */   boolean m_bResizing = false;
/*  195 */   boolean m_bZoomToolTracking = false;
/*  196 */   DRect m_ZoomSelRect = new DRect();
/*  197 */   boolean m_bLeftButtonDown = false;
/*  198 */   boolean m_bRightButtonDown = false;
/*      */   
/*  200 */   Point m_pointLastCursorPos = new Point();
/*  201 */   DRect m_drectLastViewPos = new DRect();
/*      */   
/*  203 */   int m_iMode = 0;
/*      */   Cursor m_curOpenHand;
/*      */   Cursor m_curClosedHand;
/*      */   Cursor m_curZoomIn;
/*      */   Cursor m_curZoomOut;
/*  208 */   boolean m_bAutoScaleX = true;
/*  209 */   boolean m_bAutoScaleY = true;
/*  210 */   private ArrayList<AutoScaleListener> _listeners = new ArrayList();
/*      */   
/*  212 */   Object lockObject = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 1L;
/*      */   
/*      */ 
/*      */ 
/*      */   public GraphControl(GLCapabilities caps)
/*      */   {
/*  222 */     super(caps);
/*      */     
/*  224 */     setVisible(true);
/*  225 */     addGLEventListener(this);
/*  226 */     addMouseListener(this);
/*  227 */     addMouseMotionListener(this);
/*  228 */     setAutoSwapBufferMode(false);
/*      */     
/*  230 */     this.m_drectView.left = 0.0D;
/*  231 */     this.m_drectView.top = (800.0D * MILLIUNITS);
/*  232 */     this.m_drectView.right = (15.0D * SECONDS);
/*  233 */     this.m_drectView.bottom = (-800.0D * MILLIUNITS);
/*      */     
/*  235 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  236 */     Image imgOpenHand = toolkit.getImage(getClass().getResource("/org/gcsimulator/images/openhand.gif"));
/*  237 */     this.m_curOpenHand = toolkit.createCustomCursor(imgOpenHand, new Point(7, 7), "openhand");
/*  238 */     Image imgClosedHand = toolkit.getImage(getClass().getResource("/org/gcsimulator/images/closedhand.gif"));
/*  239 */     this.m_curClosedHand = toolkit.createCustomCursor(imgClosedHand, new Point(7, 7), "closedhand");
/*  240 */     Image imgZoomIn = toolkit.getImage(getClass().getResource("/org/gcsimulator/images/zoomin.gif"));
/*  241 */     this.m_curZoomIn = toolkit.createCustomCursor(imgZoomIn, new Point(0, 0), "zoomin");
/*  242 */     Image imgZoomOut = toolkit.getImage(getClass().getResource("/org/gcsimulator/images/zoomout.gif"));
/*  243 */     this.m_curZoomOut = toolkit.createCustomCursor(imgZoomOut, new Point(5, 5), "zoomout");
/*      */     
/*  245 */     if (this.m_bControlsEnabled) {
/*  246 */       setCursor(this.m_curOpenHand);
/*      */     }
/*      */   }
/*      */   
/*      */   public void init(GLAutoDrawable drawable) {
/*  251 */     GL2 gl2 = drawable.getGL().getGL2();
/*      */     
/*  253 */     gl2.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/*  254 */     gl2.glMatrixMode(5889);
/*  255 */     gl2.glLoadIdentity();
/*  256 */     gl2.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, -1.0D, 1.0D);
/*      */     
/*  258 */     gl2.glBlendFunc(770, 771);
/*  259 */     gl2.glEnable(3042);
/*  260 */     gl2.glEnable(2848);
/*  261 */     gl2.glHint(3154, 4354);
/*      */     
/*  263 */     this.m_rendererXAxisLabel = new TextRenderer(m_fontXAxisLabel, true, false);
/*  264 */     this.m_rendererYAxisLabel = new TextRenderer(m_fontYAxisLabel, true, false);
/*  265 */     this.m_rendererXAxisDivision = new TextRenderer(m_fontXAxisDivision, true, false);
/*  266 */     this.m_rendererYAxisDivision = new TextRenderer(m_fontYAxisDivision, true, false);
/*      */   }
/*      */   
/*      */   public void display(GLAutoDrawable drawable)
/*      */   {
/*  271 */     synchronized (this.lockObject)
/*      */     {
/*  273 */       GL2 gl2 = drawable.getGL().getGL2();
/*      */       
/*  275 */       gl2.glClear(16384);
/*      */       
/*  277 */       gl2.glLineWidth(1.0F);
/*      */       
/*  279 */       DrawGraph();
/*      */       
/*      */ 
/*  282 */       double[] upperXBoundPlane = { 1.0D, 0.0D, 0.0D, -this.m_rectGraph.left - 1.0D };
/*  283 */       double[] lowerXBoundPlane = { -1.0D, 0.0D, 0.0D, this.m_rectGraph.right };
/*  284 */       double[] upperYBoundPlane = { 0.0D, -1.0D, 0.0D, this.m_rectGraph.top };
/*  285 */       double[] lowerYBoundPlane = { 0.0D, 1.0D, 0.0D, -this.m_rectGraph.bottom - 1.0D };
/*      */       
/*  287 */       gl2.glClipPlane(12288, DoubleBuffer.wrap(upperXBoundPlane));
/*  288 */       gl2.glEnable(12288);
/*  289 */       gl2.glClipPlane(12289, DoubleBuffer.wrap(lowerXBoundPlane));
/*  290 */       gl2.glEnable(12289);
/*  291 */       gl2.glClipPlane(12290, DoubleBuffer.wrap(upperYBoundPlane));
/*  292 */       gl2.glEnable(12290);
/*  293 */       gl2.glClipPlane(12291, DoubleBuffer.wrap(lowerYBoundPlane));
/*  294 */       gl2.glEnable(12291);
/*      */       
/*  296 */       DrawChannelLines();
/*  297 */       drawLineLabels();
/*  298 */       drawZoomBox();
/*      */       
/*      */ 
/*  301 */       gl2.glDisable(12288);
/*  302 */       gl2.glDisable(12289);
/*  303 */       gl2.glDisable(12290);
/*  304 */       gl2.glDisable(12291);
/*      */       
/*      */ 
/*  307 */       if (this.m_bCopyImage)
/*      */       {
/*  309 */         int w = getWidth();
/*  310 */         int h = getHeight();
/*      */         
/*      */ 
/*  313 */         if (w % 4 > 0) {
/*  314 */           w += 4 - w % 4;
/*      */         }
/*  316 */         byte[] bytes = new byte[w * h * 4];
/*  317 */         this.m_ByteBuffer = ByteBuffer.wrap(bytes);
/*  318 */         gl2.glReadPixels(0, 0, w, h, 6408, 5121, this.m_ByteBuffer);
/*  319 */         this.m_ByteBuffer.rewind();
/*      */       }
/*      */       
/*  322 */       swapBuffers();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
/*      */   {
/*  338 */     setMinimumSize(new Dimension(10, 10));
/*      */     
/*  340 */     GL2 gl2 = drawable.getGL().getGL2();
/*      */     
/*  342 */     gl2.glViewport(0, 0, width, height);
/*  343 */     gl2.glMatrixMode(5889);
/*  344 */     gl2.glLoadIdentity();
/*  345 */     glu.gluOrtho2D(0.0F, width, 0.0F, height);
/*  346 */     gl2.glMatrixMode(5888);
/*  347 */     gl2.glLoadIdentity();
/*  348 */     gl2.glTranslatef(0.375F, 0.375F, 0.0F);
/*      */   }
/*      */   
/*      */   protected void processMouseEvent(MouseEvent e)
/*      */   {
/*  353 */     double MINWIDTH = 1.0D;
/*  354 */     double MINHEIGHT = 20.0D;
/*      */     
/*  356 */     if (e.getButton() == 1)
/*      */     {
/*  358 */       if (e.getID() == 501)
/*      */       {
/*      */ 
/*  361 */         this.m_bLeftButtonDown = true;
/*      */         
/*  363 */         if (this.m_iMode == 0)
/*      */         {
/*  365 */           if ((!this.m_bTranslating) && (!this.m_bZooming) && (!this.m_bResizing))
/*      */           {
/*  367 */             this.m_pointLastCursorPos = e.getPoint();
/*      */             
/*  369 */             this.m_drectLastViewPos.top = this.m_drectView.top;
/*  370 */             this.m_drectLastViewPos.bottom = this.m_drectView.bottom;
/*  371 */             this.m_drectLastViewPos.left = this.m_drectView.left;
/*  372 */             this.m_drectLastViewPos.right = this.m_drectView.right;
/*      */             
/*  374 */             this.m_bTranslating = true;
/*      */           }
/*      */         }
/*  377 */         else if (this.m_iMode == 1)
/*      */         {
/*  379 */           if (!this.m_bZoomToolTracking) {
/*  380 */             this.m_bZoomToolTracking = true;
/*      */           }
/*  382 */           this.m_ZoomSelRect.left = e.getPoint().x;
/*  383 */           this.m_ZoomSelRect.right = e.getPoint().x;
/*      */           
/*  385 */           int iWindowHeight = getHeight();
/*  386 */           this.m_ZoomSelRect.top = (iWindowHeight - e.getPoint().y);
/*  387 */           this.m_ZoomSelRect.bottom = (iWindowHeight - e.getPoint().y);
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */       }
/*  394 */       else if (e.getID() == 502)
/*      */       {
/*      */ 
/*  397 */         this.m_bLeftButtonDown = false;
/*      */         
/*  399 */         if (this.m_iMode == 0)
/*      */         {
/*  401 */           this.m_bTranslating = false;
/*      */         }
/*  403 */         else if (this.m_iMode == 1)
/*      */         {
/*  405 */           turnOffAutoScale();
/*      */           
/*  407 */           this.m_bZoomToolTracking = false;
/*      */           
/*  409 */           if ((this.m_ZoomSelRect.right == this.m_ZoomSelRect.left) || 
/*  410 */             (this.m_ZoomSelRect.top == this.m_ZoomSelRect.bottom))
/*      */           {
/*      */ 
/*  413 */             DRect dNewViewRect = new DRect();
/*      */             
/*  415 */             double dWidth = this.m_drectView.right - this.m_drectView.left;
/*  416 */             double dHeight = this.m_drectView.top - this.m_drectView.bottom;
/*      */             
/*  418 */             double dMouseXPos = this.m_drectView.left + (this.m_ZoomSelRect.left - this.m_rectGraph.left) * this.m_dXMultiplier;
/*  419 */             double dMouseYPos = this.m_drectView.bottom + (this.m_ZoomSelRect.bottom - this.m_rectGraph.bottom) * this.m_dYMultiplier;
/*      */             
/*  421 */             if (dWidth * 0.5D > MINWIDTH)
/*      */             {
/*  423 */               dNewViewRect.left = (dMouseXPos - dWidth / 4.0D);
/*  424 */               dNewViewRect.right = (dMouseXPos + dWidth / 4.0D);
/*      */             }
/*      */             else
/*      */             {
/*  428 */               dNewViewRect.left = (dMouseXPos - MINWIDTH / 2.0D);
/*  429 */               dNewViewRect.right = (dMouseXPos + MINWIDTH / 2.0D);
/*      */               
/*      */ 
/*  432 */               if (dNewViewRect.right > 104.0D * DAYS)
/*      */               {
/*  434 */                 dNewViewRect.right = (104.0D * DAYS);
/*  435 */                 dNewViewRect.left = (dNewViewRect.right - 1.0D);
/*      */               }
/*  437 */               if (dNewViewRect.left < -104.0D * DAYS)
/*      */               {
/*  439 */                 dNewViewRect.left = (-104.0D * DAYS);
/*  440 */                 dNewViewRect.left += 1.0D;
/*      */               }
/*      */             }
/*      */             
/*  444 */             if (dHeight * 0.5D > MINHEIGHT)
/*      */             {
/*  446 */               dNewViewRect.top = (dMouseYPos + dHeight / 4.0D);
/*  447 */               dNewViewRect.bottom = (dMouseYPos - dHeight / 4.0D);
/*      */             }
/*      */             else
/*      */             {
/*  451 */               dNewViewRect.top = (dMouseYPos + MINHEIGHT / 2.0D);
/*  452 */               dNewViewRect.bottom = (dMouseYPos - MINHEIGHT / 2.0D);
/*      */               
/*      */ 
/*  455 */               if (dNewViewRect.top > this.m_dYAxisUpperLimit)
/*      */               {
/*  457 */                 dNewViewRect.top = this.m_dYAxisUpperLimit;
/*  458 */                 dNewViewRect.bottom = (dNewViewRect.top - 1.0D);
/*      */               }
/*  460 */               if (dNewViewRect.bottom < this.m_dYAxisLowerLimit)
/*      */               {
/*  462 */                 dNewViewRect.bottom = this.m_dYAxisLowerLimit;
/*  463 */                 dNewViewRect.top = (dNewViewRect.bottom + 1.0D);
/*      */               }
/*      */             }
/*      */             
/*  467 */             this.m_drectView.left = dNewViewRect.left;
/*  468 */             this.m_drectView.right = dNewViewRect.right;
/*  469 */             this.m_drectView.top = dNewViewRect.top;
/*  470 */             this.m_drectView.bottom = dNewViewRect.bottom;
/*      */             
/*  472 */             repaint();
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  477 */             DRect normalizedRect = new DRect();
/*  478 */             DRect dNewViewRect = new DRect();
/*      */             
/*  480 */             if (this.m_ZoomSelRect.top > this.m_ZoomSelRect.bottom)
/*      */             {
/*  482 */               normalizedRect.top = this.m_ZoomSelRect.top;
/*  483 */               normalizedRect.bottom = this.m_ZoomSelRect.bottom;
/*      */             }
/*      */             else
/*      */             {
/*  487 */               normalizedRect.top = this.m_ZoomSelRect.bottom;
/*  488 */               normalizedRect.bottom = this.m_ZoomSelRect.top;
/*      */             }
/*      */             
/*  491 */             if (this.m_ZoomSelRect.right > this.m_ZoomSelRect.left)
/*      */             {
/*  493 */               normalizedRect.right = this.m_ZoomSelRect.right;
/*  494 */               normalizedRect.left = this.m_ZoomSelRect.left;
/*      */             }
/*      */             else
/*      */             {
/*  498 */               normalizedRect.right = this.m_ZoomSelRect.left;
/*  499 */               normalizedRect.left = this.m_ZoomSelRect.right;
/*      */             }
/*      */             
/*  502 */             dNewViewRect.left = (this.m_drectView.left + (normalizedRect.left - this.m_rectGraph.left) * this.m_dXMultiplier);
/*  503 */             dNewViewRect.right = (this.m_drectView.left + (normalizedRect.right - this.m_rectGraph.left) * this.m_dXMultiplier);
/*  504 */             dNewViewRect.top = (this.m_drectView.bottom + (normalizedRect.top - this.m_rectGraph.bottom) * this.m_dYMultiplier);
/*  505 */             dNewViewRect.bottom = (this.m_drectView.bottom + (normalizedRect.bottom - this.m_rectGraph.bottom) * this.m_dYMultiplier);
/*      */             
/*  507 */             if (dNewViewRect.getWidth() < MINWIDTH)
/*      */             {
/*  509 */               double centerx = dNewViewRect.left + dNewViewRect.getWidth() / 2.0D;
/*  510 */               dNewViewRect.left = (centerx - MINWIDTH * 0.5D);
/*  511 */               dNewViewRect.right = (dNewViewRect.left + MINWIDTH);
/*      */             }
/*  513 */             if (dNewViewRect.getHeight() < MINHEIGHT)
/*      */             {
/*  515 */               double centery = dNewViewRect.bottom + dNewViewRect.getHeight() / 2.0D;
/*  516 */               dNewViewRect.bottom = (centery - MINHEIGHT * 0.5D);
/*  517 */               dNewViewRect.top = (dNewViewRect.bottom + MINHEIGHT);
/*      */             }
/*      */             
/*  520 */             double dWidth = dNewViewRect.getWidth();
/*  521 */             double dHeight = dNewViewRect.getHeight();
/*      */             
/*      */ 
/*  524 */             if (dNewViewRect.right > 104.0D * DAYS)
/*      */             {
/*  526 */               dNewViewRect.right = (104.0D * DAYS);
/*  527 */               dNewViewRect.left = (dNewViewRect.right - dWidth);
/*      */             }
/*  529 */             if (dNewViewRect.left < -104.0D * DAYS)
/*      */             {
/*  531 */               dNewViewRect.left = (-104.0D * DAYS);
/*  532 */               dNewViewRect.left += dWidth;
/*      */             }
/*      */             
/*  535 */             if (dNewViewRect.top > this.m_dYAxisUpperLimit)
/*      */             {
/*  537 */               dNewViewRect.top = this.m_dYAxisUpperLimit;
/*  538 */               dNewViewRect.bottom = (dNewViewRect.top - dHeight);
/*      */             }
/*  540 */             if (dNewViewRect.bottom < this.m_dYAxisLowerLimit)
/*      */             {
/*  542 */               dNewViewRect.bottom = this.m_dYAxisLowerLimit;
/*  543 */               dNewViewRect.top = (dNewViewRect.bottom + dHeight);
/*      */             }
/*      */             
/*      */ 
/*  547 */             this.m_drectView.left = dNewViewRect.left;
/*  548 */             this.m_drectView.right = dNewViewRect.right;
/*  549 */             this.m_drectView.top = dNewViewRect.top;
/*  550 */             this.m_drectView.bottom = dNewViewRect.bottom;
/*      */             
/*  552 */             repaint();
/*      */           }
/*      */         }
/*  555 */         else if (this.m_iMode == 2)
/*      */         {
/*  557 */           turnOffAutoScale();
/*      */           
/*      */ 
/*  560 */           DRect dNewViewRect = new DRect();
/*      */           
/*  562 */           double dWidth = this.m_drectView.right - this.m_drectView.left;
/*  563 */           double dHeight = this.m_drectView.top - this.m_drectView.bottom;
/*      */           
/*  565 */           int iWindowHeight = getHeight();
/*      */           
/*  567 */           double dMouseXPos = this.m_drectView.left + (e.getPoint().x - this.m_rectGraph.left) * this.m_dXMultiplier;
/*  568 */           double dMouseYPos = this.m_drectView.bottom + (iWindowHeight - e.getPoint().y - this.m_rectGraph.bottom) * this.m_dYMultiplier;
/*      */           
/*  570 */           dNewViewRect.left = (dMouseXPos - dWidth);
/*  571 */           dNewViewRect.right = (dMouseXPos + dWidth);
/*  572 */           dNewViewRect.top = (dMouseYPos + dHeight);
/*  573 */           dNewViewRect.bottom = (dMouseYPos - dHeight);
/*      */           
/*  575 */           if (dNewViewRect.left < -104.0D * DAYS) {
/*  576 */             dNewViewRect.left = (-104.0D * DAYS);
/*      */           }
/*  578 */           if (dNewViewRect.right > 104.0D * DAYS) {
/*  579 */             dNewViewRect.right = (104.0D * DAYS);
/*      */           }
/*  581 */           if (dNewViewRect.bottom < this.m_dYAxisLowerLimit) {
/*  582 */             dNewViewRect.bottom = this.m_dYAxisLowerLimit;
/*      */           }
/*  584 */           if (dNewViewRect.top > this.m_dYAxisUpperLimit) {
/*  585 */             dNewViewRect.top = this.m_dYAxisUpperLimit;
/*      */           }
/*      */           
/*  588 */           this.m_drectView.left = dNewViewRect.left;
/*  589 */           this.m_drectView.right = dNewViewRect.right;
/*  590 */           this.m_drectView.top = dNewViewRect.top;
/*  591 */           this.m_drectView.bottom = dNewViewRect.bottom;
/*      */           
/*  593 */           repaint();
/*      */         }
/*      */       }
/*      */     }
/*  597 */     else if (e.getButton() == 3)
/*      */     {
/*  599 */       if (e.getID() == 501)
/*      */       {
/*      */ 
/*  602 */         this.m_bRightButtonDown = true;
/*      */         
/*  604 */         if (this.m_iMode == 0)
/*      */         {
/*  606 */           if ((!this.m_bZooming) && (!this.m_bTranslating) && (!this.m_bResizing))
/*      */           {
/*  608 */             this.m_pointLastCursorPos = e.getPoint();
/*      */             
/*  610 */             this.m_drectLastViewPos.top = this.m_drectView.top;
/*  611 */             this.m_drectLastViewPos.bottom = this.m_drectView.bottom;
/*  612 */             this.m_drectLastViewPos.left = this.m_drectView.left;
/*  613 */             this.m_drectLastViewPos.right = this.m_drectView.right;
/*      */             
/*  615 */             if (e.isShiftDown()) {
/*  616 */               this.m_bResizing = true;
/*      */             } else {
/*  618 */               this.m_bZooming = true;
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*  625 */       else if (e.getID() == 502)
/*      */       {
/*      */ 
/*  628 */         this.m_bRightButtonDown = false;
/*      */         
/*  630 */         this.m_bZooming = false;
/*  631 */         this.m_bResizing = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  636 */     super.processMouseEvent(e);
/*      */   }
/*      */   
/*      */   protected void turnOnAutoScaleX()
/*      */   {
/*  641 */     if (!this.m_bAutoScaleX)
/*      */     {
/*  643 */       this.m_bAutoScaleX = true;
/*  644 */       fireAutoScaleChangedEvent();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void turnOnAutoScaleY()
/*      */   {
/*  650 */     if (!this.m_bAutoScaleY)
/*      */     {
/*  652 */       this.m_bAutoScaleY = true;
/*  653 */       fireAutoScaleChangedEvent();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void turnOffAutoScale()
/*      */   {
/*  659 */     if ((this.m_bAutoScaleX) || (this.m_bAutoScaleY))
/*      */     {
/*  661 */       this.m_bAutoScaleX = false;
/*  662 */       this.m_bAutoScaleY = false;
/*  663 */       fireAutoScaleChangedEvent();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void processMouseMotionEvent(MouseEvent e)
/*      */   {
/*  669 */     Point pointCursor = e.getPoint();
/*  670 */     if (this.m_bControlsEnabled)
/*      */     {
/*  672 */       if (this.m_iMode == 0)
/*      */       {
/*      */ 
/*  675 */         if (this.m_bTranslating)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  680 */           turnOffAutoScale();
/*      */           
/*  682 */           if ((this.m_drectLastViewPos.top + (pointCursor.y - this.m_pointLastCursorPos.y) * this.m_dYMultiplier <= this.m_dYAxisUpperLimit) && 
/*  683 */             (this.m_drectLastViewPos.top + (pointCursor.y - this.m_pointLastCursorPos.y) * this.m_dYMultiplier - this.m_drectLastViewPos.getHeight() >= this.m_dYAxisLowerLimit))
/*      */           {
/*  685 */             this.m_drectView.top = (this.m_drectLastViewPos.top + this.m_dYMultiplier * (pointCursor.y - this.m_pointLastCursorPos.y));
/*  686 */             this.m_drectView.bottom = (this.m_drectView.top - this.m_drectLastViewPos.getHeight());
/*      */           }
/*  688 */           else if (this.m_drectLastViewPos.top + (pointCursor.y - this.m_pointLastCursorPos.y) * this.m_dYMultiplier > this.m_dYAxisUpperLimit)
/*      */           {
/*  690 */             this.m_drectView.top = this.m_dYAxisUpperLimit;
/*  691 */             this.m_drectView.bottom = (this.m_drectView.top - this.m_drectLastViewPos.getHeight());
/*      */           }
/*  693 */           else if (this.m_drectLastViewPos.top + (pointCursor.y - this.m_pointLastCursorPos.y) * this.m_dYMultiplier - this.m_drectLastViewPos.getHeight() < this.m_dYAxisLowerLimit)
/*      */           {
/*  695 */             this.m_drectView.bottom = this.m_dYAxisLowerLimit;
/*  696 */             this.m_drectView.top = (this.m_drectView.bottom + this.m_drectLastViewPos.getHeight());
/*      */           }
/*      */           
/*  699 */           if ((this.m_drectLastViewPos.left - (pointCursor.x - this.m_pointLastCursorPos.x) * this.m_dXMultiplier >= -104.0D * DAYS) && 
/*  700 */             (this.m_drectLastViewPos.left - (pointCursor.x - this.m_pointLastCursorPos.x) * this.m_dXMultiplier + this.m_drectLastViewPos.getWidth() <= 104.0D * DAYS))
/*      */           {
/*  702 */             this.m_drectView.left = (this.m_drectLastViewPos.left - this.m_dXMultiplier * (pointCursor.x - this.m_pointLastCursorPos.x));
/*  703 */             this.m_drectView.right = (this.m_drectView.left + this.m_drectLastViewPos.getWidth());
/*      */           }
/*  705 */           else if (this.m_drectLastViewPos.left - (pointCursor.x - this.m_pointLastCursorPos.x) * this.m_dXMultiplier < -104.0D * DAYS)
/*      */           {
/*  707 */             this.m_drectView.left = (-104.0D * DAYS);
/*  708 */             this.m_drectView.right = (this.m_drectView.left + this.m_drectLastViewPos.getWidth());
/*      */           }
/*  710 */           else if (this.m_drectLastViewPos.left - (pointCursor.x - this.m_pointLastCursorPos.x) * this.m_dXMultiplier + this.m_drectLastViewPos.getWidth() > 104.0D * DAYS)
/*      */           {
/*  712 */             this.m_drectView.right = (104.0D * DAYS);
/*  713 */             this.m_drectView.left = (this.m_drectView.right - this.m_drectLastViewPos.getWidth());
/*      */           }
/*      */           
/*  716 */           repaint();
/*      */         }
/*  718 */         else if (this.m_bZooming)
/*      */         {
/*  720 */           double dfactor = Math.pow(1.05D, this.m_pointLastCursorPos.y - pointCursor.y);
/*  721 */           double MINWIDTH = 1.0D;
/*  722 */           double MINHEIGHT = 20.0D;
/*      */           
/*  724 */           turnOffAutoScale();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  729 */           if (this.m_drectLastViewPos.right - this.m_drectLastViewPos.getWidth() * 0.5D + this.m_drectLastViewPos.getWidth() * dfactor * 0.5D - (this.m_drectLastViewPos.left + this.m_drectLastViewPos.getWidth() * 0.5D - this.m_drectLastViewPos.getWidth() * dfactor * 0.5D) > MINWIDTH)
/*      */           {
/*  731 */             if (this.m_drectLastViewPos.left + this.m_drectLastViewPos.getWidth() * 0.5D - this.m_drectLastViewPos.getWidth() * dfactor * 0.5D < -104.0D * DAYS) {
/*  732 */               this.m_drectView.left = (-104.0D * DAYS);
/*      */             } else {
/*  734 */               this.m_drectView.left = (this.m_drectLastViewPos.left + this.m_drectLastViewPos.getWidth() * 0.5D - this.m_drectLastViewPos.getWidth() * dfactor * 0.5D);
/*      */             }
/*  736 */             if (this.m_drectLastViewPos.right - this.m_drectLastViewPos.getWidth() * 0.5D + this.m_drectLastViewPos.getWidth() * dfactor * 0.5D > 104.0D * DAYS) {
/*  737 */               this.m_drectView.right = (104.0D * DAYS);
/*      */             } else {
/*  739 */               this.m_drectView.right = (this.m_drectLastViewPos.right - this.m_drectLastViewPos.getWidth() * 0.5D + this.m_drectLastViewPos.getWidth() * dfactor * 0.5D);
/*      */             }
/*      */           }
/*      */           else {
/*  743 */             double centerh = this.m_drectView.left + (this.m_drectView.right - this.m_drectView.left) * 0.5D;
/*  744 */             this.m_drectView.left = (centerh - MINWIDTH * 0.5D);
/*  745 */             this.m_drectView.right = (centerh + MINWIDTH * 0.5D);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  751 */           if (this.m_drectLastViewPos.top - this.m_drectLastViewPos.getHeight() * 0.5D + this.m_drectLastViewPos.getHeight() * dfactor * 0.5D - (this.m_drectLastViewPos.bottom + this.m_drectLastViewPos.getHeight() * 0.5D - this.m_drectLastViewPos.getHeight() * dfactor * 0.5D) > MINHEIGHT)
/*      */           {
/*  753 */             if (this.m_drectLastViewPos.top - this.m_drectLastViewPos.getHeight() * 0.5D + this.m_drectLastViewPos.getHeight() * dfactor * 0.5D > this.m_dYAxisUpperLimit) {
/*  754 */               this.m_drectView.top = this.m_dYAxisUpperLimit;
/*      */             } else {
/*  756 */               this.m_drectView.top = (this.m_drectLastViewPos.top - this.m_drectLastViewPos.getHeight() * 0.5D + this.m_drectLastViewPos.getHeight() * dfactor * 0.5D);
/*      */             }
/*  758 */             if (this.m_drectLastViewPos.bottom + this.m_drectLastViewPos.getHeight() * 0.5D - this.m_drectLastViewPos.getHeight() * dfactor * 0.5D < this.m_dYAxisLowerLimit) {
/*  759 */               this.m_drectView.bottom = this.m_dYAxisLowerLimit;
/*      */             } else {
/*  761 */               this.m_drectView.bottom = (this.m_drectLastViewPos.bottom + this.m_drectLastViewPos.getHeight() * 0.5D - this.m_drectLastViewPos.getHeight() * dfactor * 0.5D);
/*      */             }
/*      */           }
/*      */           else {
/*  765 */             double centerv = this.m_drectView.bottom + (this.m_drectView.top - this.m_drectView.bottom) * 0.5D;
/*  766 */             this.m_drectView.top = (centerv + MINHEIGHT * 0.5D);
/*  767 */             this.m_drectView.bottom = (centerv - MINHEIGHT * 0.5D);
/*      */           }
/*      */           
/*  770 */           repaint();
/*      */         }
/*  772 */         else if (this.m_bResizing)
/*      */         {
/*  774 */           double dfactory = Math.pow(1.05D, this.m_pointLastCursorPos.y - pointCursor.y);
/*  775 */           double dfactorx = Math.pow(1.05D, this.m_pointLastCursorPos.x - pointCursor.x);
/*  776 */           double MINWIDTH = 1.0D;
/*  777 */           double MINHEIGHT = 20.0D;
/*      */           
/*  779 */           turnOffAutoScale();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  784 */           if (this.m_drectLastViewPos.right - this.m_drectLastViewPos.getWidth() * 0.5D + this.m_drectLastViewPos.getWidth() * dfactorx * 0.5D - (this.m_drectLastViewPos.left + this.m_drectLastViewPos.getWidth() * 0.5D - this.m_drectLastViewPos.getWidth() * dfactorx * 0.5D) > MINWIDTH)
/*      */           {
/*  786 */             if (this.m_drectLastViewPos.left + this.m_drectLastViewPos.getWidth() * 0.5D - this.m_drectLastViewPos.getWidth() * dfactorx * 0.5D < -104.0D * DAYS) {
/*  787 */               this.m_drectView.left = (-104.0D * DAYS);
/*      */             } else {
/*  789 */               this.m_drectView.left = (this.m_drectLastViewPos.left + this.m_drectLastViewPos.getWidth() * 0.5D - this.m_drectLastViewPos.getWidth() * dfactorx * 0.5D);
/*      */             }
/*  791 */             if (this.m_drectLastViewPos.right - this.m_drectLastViewPos.getWidth() * 0.5D + this.m_drectLastViewPos.getWidth() * dfactorx * 0.5D > 104.0D * DAYS) {
/*  792 */               this.m_drectView.right = (104.0D * DAYS);
/*      */             } else {
/*  794 */               this.m_drectView.right = (this.m_drectLastViewPos.right - this.m_drectLastViewPos.getWidth() * 0.5D + this.m_drectLastViewPos.getWidth() * dfactorx * 0.5D);
/*      */             }
/*      */           }
/*      */           else {
/*  798 */             double centerh = this.m_drectView.left + (this.m_drectView.right - this.m_drectView.left) * 0.5D;
/*  799 */             this.m_drectView.left = (centerh - MINWIDTH * 0.5D);
/*  800 */             this.m_drectView.right = (centerh + MINWIDTH * 0.5D);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  806 */           if (this.m_drectLastViewPos.top - this.m_drectLastViewPos.getHeight() * 0.5D + this.m_drectLastViewPos.getHeight() * dfactory * 0.5D - (this.m_drectLastViewPos.bottom + this.m_drectLastViewPos.getHeight() * 0.5D - this.m_drectLastViewPos.getHeight() * dfactory * 0.5D) > MINHEIGHT)
/*      */           {
/*  808 */             if (this.m_drectLastViewPos.top - this.m_drectLastViewPos.getHeight() * 0.5D + this.m_drectLastViewPos.getHeight() * dfactory * 0.5D > this.m_dYAxisUpperLimit) {
/*  809 */               this.m_drectView.top = this.m_dYAxisUpperLimit;
/*      */             } else {
/*  811 */               this.m_drectView.top = (this.m_drectLastViewPos.top - this.m_drectLastViewPos.getHeight() * 0.5D + this.m_drectLastViewPos.getHeight() * dfactory * 0.5D);
/*      */             }
/*  813 */             if (this.m_drectLastViewPos.bottom + this.m_drectLastViewPos.getHeight() * 0.5D - this.m_drectLastViewPos.getHeight() * dfactory * 0.5D < this.m_dYAxisLowerLimit) {
/*  814 */               this.m_drectView.bottom = this.m_dYAxisLowerLimit;
/*      */             } else {
/*  816 */               this.m_drectView.bottom = (this.m_drectLastViewPos.bottom + this.m_drectLastViewPos.getHeight() * 0.5D - this.m_drectLastViewPos.getHeight() * dfactory * 0.5D);
/*      */             }
/*      */           }
/*      */           else {
/*  820 */             double centerv = this.m_drectView.bottom + (this.m_drectView.top - this.m_drectView.bottom) * 0.5D;
/*  821 */             this.m_drectView.top = (centerv + MINHEIGHT * 0.5D);
/*  822 */             this.m_drectView.bottom = (centerv - MINHEIGHT * 0.5D);
/*      */           }
/*      */           
/*  825 */           repaint();
/*      */         }
/*      */       }
/*  828 */       else if (this.m_iMode == 1)
/*      */       {
/*  830 */         if (this.m_bZoomToolTracking)
/*      */         {
/*  832 */           this.m_ZoomSelRect.right = pointCursor.x;
/*      */           
/*  834 */           int iWindowHeight = getHeight();
/*  835 */           this.m_ZoomSelRect.bottom = (iWindowHeight - pointCursor.y);
/*      */           
/*  837 */           repaint();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  846 */     this.m_pointLastCursorPos.x = e.getPoint().x;
/*  847 */     this.m_pointLastCursorPos.y = e.getPoint().y;
/*      */     
/*  849 */     this.m_drectLastViewPos.top = this.m_drectView.top;
/*  850 */     this.m_drectLastViewPos.bottom = this.m_drectView.bottom;
/*  851 */     this.m_drectLastViewPos.left = this.m_drectView.left;
/*  852 */     this.m_drectLastViewPos.right = this.m_drectView.right;
/*      */     
/*  854 */     super.processMouseMotionEvent(e);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void DrawGraph()
/*      */   {
/*  869 */     GL2 gl2 = getGL().getGL2();
/*      */     
/*  871 */     int MINOR_LINE_LENGTH = 2;
/*  872 */     int MAJOR_LINE_LENGTH = 6;
/*      */     
/*      */ 
/*  875 */     String strNextXAxisLabel = "";
/*  876 */     String strNextYAxisLabel = "";
/*  877 */     String strSecondNextYAxisLabel = "";
/*      */     
/*  879 */     DRect rectWindow = new DRect();
/*  880 */     rectWindow.bottom = 0.0D;
/*  881 */     rectWindow.top = getHeight();
/*  882 */     rectWindow.left = 0.0D;
/*  883 */     rectWindow.right = getWidth();
/*      */     
/*  885 */     Graphics graphics = getGraphics();
/*      */     
/*  887 */     FontMetrics metricsXAxisLabel = graphics.getFontMetrics(m_fontYAxisLabel);
/*  888 */     FontMetrics metricsYAxisLabel = graphics.getFontMetrics(m_fontXAxisLabel);
/*  889 */     FontMetrics metricsXAxisDivision = graphics.getFontMetrics(m_fontXAxisDivision);
/*  890 */     FontMetrics metricsYAxisDivision = graphics.getFontMetrics(m_fontYAxisDivision);
/*      */     
/*  892 */     int GREAT_LINE_LENGTH = MAJOR_LINE_LENGTH + metricsXAxisDivision.getAscent();
/*      */     
/*  894 */     DecimalFormat decformat = new DecimalFormat("#.#");
/*      */     
/*      */ 
/*  897 */     if (this.m_bYAxisRangeIndicatorsVisible) {
/*  898 */       this.m_rectGraph.left = (rectWindow.left + 2.0D + metricsYAxisLabel.getHeight() + 2 * metricsYAxisDivision.getHeight() + GREAT_LINE_LENGTH);
/*      */     } else {
/*  900 */       this.m_rectGraph.left = (rectWindow.left + 2.0D + metricsYAxisLabel.getHeight() + 1 * metricsYAxisDivision.getHeight() + GREAT_LINE_LENGTH);
/*      */     }
/*  902 */     if (this.m_bXAxisRangeIndicatorsVisible) {
/*  903 */       this.m_rectGraph.bottom = (rectWindow.bottom + 3 * metricsXAxisDivision.getHeight() + 2.0D + GREAT_LINE_LENGTH);
/*      */     } else {
/*  905 */       this.m_rectGraph.bottom = (rectWindow.bottom + 2 * metricsXAxisDivision.getHeight() + 2.0D + GREAT_LINE_LENGTH);
/*      */     }
/*  907 */     if (this.m_bSecondYAxisVisible) {
/*  908 */       this.m_rectGraph.right = (rectWindow.right - 2.0D - metricsYAxisLabel.getHeight() - 1 * metricsYAxisDivision.getHeight() - GREAT_LINE_LENGTH);
/*      */     } else {
/*  910 */       this.m_rectGraph.right = (rectWindow.right - 5.0D);
/*      */     }
/*  912 */     this.m_rectGraph.top = (rectWindow.top - 5.0D);
/*      */     
/*      */ 
/*  915 */     this.m_dXMultiplier = ((this.m_drectView.right - this.m_drectView.left) / (this.m_rectGraph.right - this.m_rectGraph.left));
/*  916 */     this.m_dYMultiplier = ((this.m_drectView.top - this.m_drectView.bottom) / (this.m_rectGraph.top - this.m_rectGraph.bottom));
/*  917 */     this.m_dSecondYMultiplier = ((this.m_dSecondYAxisUpperLimit - this.m_dSecondYAxisLowerLimit) / (this.m_rectGraph.top - this.m_rectGraph.bottom));
/*  918 */     this.m_dInvXMultiplier = (1.0D / this.m_dXMultiplier);
/*  919 */     this.m_dInvYMultiplier = (1.0D / this.m_dYMultiplier);
/*  920 */     this.m_dSecondInvYMultiplier = (1.0D / this.m_dSecondYMultiplier);
/*      */     
/*  922 */     double dFrameRefX = this.m_dXMultiplier * 20.0D;
/*      */     
/*  924 */     double dDifferenceX = this.m_drectView.right - this.m_drectView.left;
/*      */     
/*  926 */     double dMajorUnitX = 0.0D;
/*  927 */     if (dFrameRefX <= 50.0D * NANOSECONDS)
/*      */     {
/*  929 */       if (1.0D >= dFrameRefX) {
/*  930 */         dMajorUnitX = 1.0D;
/*  931 */       } else if (2.0D >= dFrameRefX) {
/*  932 */         dMajorUnitX = 2.0D;
/*  933 */       } else if (5.0D >= dFrameRefX) {
/*  934 */         dMajorUnitX = 5.0D;
/*  935 */       } else if (10.0D >= dFrameRefX) {
/*  936 */         dMajorUnitX = 10.0D;
/*  937 */       } else if (20.0D >= dFrameRefX) {
/*  938 */         dMajorUnitX = 20.0D;
/*      */       } else {
/*  940 */         dMajorUnitX = 50.0D;
/*      */       }
/*  942 */       this.m_iMajorUnitTypeX = 1;
/*  943 */       this.m_dMajorUnitXTypeValue = 1.0D;
/*  944 */       this.m_dNextMajorUnitXTypeValue = MICROSECONDS;
/*  945 */       this.m_dNextNextMajorUnitXTypeValue = MILLISECONDS;
/*  946 */       this.m_strXAxisLabel = "nanosec";
/*  947 */       this.m_strXAxisLabelShort = "ns";
/*  948 */       strNextXAxisLabel = "μs";
/*      */     }
/*  950 */     else if (dFrameRefX <= 50.0D * MICROSECONDS)
/*      */     {
/*  952 */       if (0.1D * MICROSECONDS >= dFrameRefX) {
/*  953 */         dMajorUnitX = 0.1D * MICROSECONDS;
/*  954 */       } else if (0.2D * MICROSECONDS >= dFrameRefX) {
/*  955 */         dMajorUnitX = 0.2D * MICROSECONDS;
/*  956 */       } else if (0.5D * MICROSECONDS >= dFrameRefX) {
/*  957 */         dMajorUnitX = 0.5D * MICROSECONDS;
/*  958 */       } else if (1.0D * MICROSECONDS >= dFrameRefX) {
/*  959 */         dMajorUnitX = 1.0D * MICROSECONDS;
/*  960 */       } else if (2.0D * MICROSECONDS >= dFrameRefX) {
/*  961 */         dMajorUnitX = 2.0D * MICROSECONDS;
/*  962 */       } else if (5.0D * MICROSECONDS >= dFrameRefX) {
/*  963 */         dMajorUnitX = 5.0D * MICROSECONDS;
/*  964 */       } else if (10.0D * MICROSECONDS >= dFrameRefX) {
/*  965 */         dMajorUnitX = 10.0D * MICROSECONDS;
/*  966 */       } else if (20.0D * MICROSECONDS >= dFrameRefX) {
/*  967 */         dMajorUnitX = 20.0D * MICROSECONDS;
/*      */       } else {
/*  969 */         dMajorUnitX = 50.0D * MICROSECONDS;
/*      */       }
/*  971 */       this.m_iMajorUnitTypeX = 2;
/*  972 */       this.m_dMajorUnitXTypeValue = MICROSECONDS;
/*  973 */       this.m_dNextMajorUnitXTypeValue = MILLISECONDS;
/*  974 */       this.m_dNextNextMajorUnitXTypeValue = SECONDS;
/*  975 */       this.m_strXAxisLabel = "microsec";
/*  976 */       this.m_strXAxisLabelShort = "μs";
/*  977 */       strNextXAxisLabel = "ms";
/*      */     }
/*  979 */     else if (dFrameRefX <= 50.0D * MILLISECONDS)
/*      */     {
/*  981 */       if (0.1D * MILLISECONDS >= dFrameRefX) {
/*  982 */         dMajorUnitX = 0.1D * MILLISECONDS;
/*  983 */       } else if (0.2D * MILLISECONDS >= dFrameRefX) {
/*  984 */         dMajorUnitX = 0.2D * MILLISECONDS;
/*  985 */       } else if (0.5D * MILLISECONDS >= dFrameRefX) {
/*  986 */         dMajorUnitX = 0.5D * MILLISECONDS;
/*  987 */       } else if (1.0D * MILLISECONDS >= dFrameRefX) {
/*  988 */         dMajorUnitX = 1.0D * MILLISECONDS;
/*  989 */       } else if (2.0D * MILLISECONDS >= dFrameRefX) {
/*  990 */         dMajorUnitX = 2.0D * MILLISECONDS;
/*  991 */       } else if (5.0D * MILLISECONDS >= dFrameRefX) {
/*  992 */         dMajorUnitX = 5.0D * MILLISECONDS;
/*  993 */       } else if (10.0D * MILLISECONDS >= dFrameRefX) {
/*  994 */         dMajorUnitX = 10.0D * MILLISECONDS;
/*  995 */       } else if (20.0D * MILLISECONDS >= dFrameRefX) {
/*  996 */         dMajorUnitX = 20.0D * MILLISECONDS;
/*      */       } else {
/*  998 */         dMajorUnitX = 50.0D * MILLISECONDS;
/*      */       }
/* 1000 */       this.m_iMajorUnitTypeX = 3;
/* 1001 */       this.m_dMajorUnitXTypeValue = MILLISECONDS;
/* 1002 */       this.m_dNextMajorUnitXTypeValue = SECONDS;
/* 1003 */       this.m_dNextNextMajorUnitXTypeValue = MINUTES;
/* 1004 */       this.m_strXAxisLabel = "millisec";
/* 1005 */       this.m_strXAxisLabelShort = "ms";
/* 1006 */       strNextXAxisLabel = "s";
/*      */     }
/* 1008 */     else if (dFrameRefX <= 5.0D * SECONDS)
/*      */     {
/* 1010 */       if (0.1D * SECONDS >= dFrameRefX) {
/* 1011 */         dMajorUnitX = 0.1D * SECONDS;
/* 1012 */       } else if (0.2D * SECONDS >= dFrameRefX) {
/* 1013 */         dMajorUnitX = 0.2D * SECONDS;
/* 1014 */       } else if (0.5D * SECONDS >= dFrameRefX) {
/* 1015 */         dMajorUnitX = 0.5D * SECONDS;
/* 1016 */       } else if (1.0D * SECONDS >= dFrameRefX) {
/* 1017 */         dMajorUnitX = 1.0D * SECONDS;
/* 1018 */       } else if (2.0D * SECONDS >= dFrameRefX) {
/* 1019 */         dMajorUnitX = 2.0D * SECONDS;
/*      */       } else {
/* 1021 */         dMajorUnitX = 5.0D * SECONDS;
/*      */       }
/* 1023 */       this.m_iMajorUnitTypeX = 4;
/* 1024 */       this.m_dMajorUnitXTypeValue = SECONDS;
/* 1025 */       this.m_dNextMajorUnitXTypeValue = MINUTES;
/* 1026 */       this.m_dNextNextMajorUnitXTypeValue = HOURS;
/* 1027 */       this.m_strXAxisLabel = "sec";
/* 1028 */       this.m_strXAxisLabelShort = "s";
/* 1029 */       strNextXAxisLabel = "min";
/*      */     }
/* 1031 */     else if (dFrameRefX <= 5.0D * MINUTES)
/*      */     {
/* 1033 */       if (0.2D * MINUTES >= dFrameRefX) {
/* 1034 */         dMajorUnitX = 0.2D * MINUTES;
/* 1035 */       } else if (0.5D * MINUTES >= dFrameRefX) {
/* 1036 */         dMajorUnitX = 0.5D * MINUTES;
/* 1037 */       } else if (1.0D * MINUTES >= dFrameRefX) {
/* 1038 */         dMajorUnitX = 1.0D * MINUTES;
/* 1039 */       } else if (2.0D * MINUTES >= dFrameRefX) {
/* 1040 */         dMajorUnitX = 2.0D * MINUTES;
/*      */       } else {
/* 1042 */         dMajorUnitX = 5.0D * MINUTES;
/*      */       }
/* 1044 */       this.m_iMajorUnitTypeX = 5;
/* 1045 */       this.m_dMajorUnitXTypeValue = MINUTES;
/* 1046 */       this.m_dNextMajorUnitXTypeValue = HOURS;
/* 1047 */       this.m_dNextNextMajorUnitXTypeValue = DAYS;
/* 1048 */       this.m_strXAxisLabel = "min";
/* 1049 */       this.m_strXAxisLabelShort = "min";
/* 1050 */       strNextXAxisLabel = "hr";
/*      */     }
/* 1052 */     else if (dFrameRefX <= 2.0D * HOURS)
/*      */     {
/* 1054 */       if (0.2D * HOURS >= dFrameRefX) {
/* 1055 */         dMajorUnitX = 0.2D * HOURS;
/* 1056 */       } else if (0.5D * HOURS >= dFrameRefX) {
/* 1057 */         dMajorUnitX = 0.5D * HOURS;
/* 1058 */       } else if (1.0D * HOURS >= dFrameRefX) {
/* 1059 */         dMajorUnitX = 1.0D * HOURS;
/*      */       } else {
/* 1061 */         dMajorUnitX = 2.0D * HOURS;
/*      */       }
/* 1063 */       this.m_iMajorUnitTypeX = 6;
/* 1064 */       this.m_dMajorUnitXTypeValue = HOURS;
/* 1065 */       this.m_dNextMajorUnitXTypeValue = DAYS;
/* 1066 */       this.m_dNextNextMajorUnitXTypeValue = YEARS;
/* 1067 */       this.m_strXAxisLabel = "hour";
/* 1068 */       this.m_strXAxisLabelShort = "hr";
/* 1069 */       strNextXAxisLabel = "day";
/*      */     }
/* 1071 */     else if (dFrameRefX <= 73.0D * DAYS)
/*      */     {
/* 1073 */       if (0.2D * DAYS >= dFrameRefX) {
/* 1074 */         dMajorUnitX = 0.2D * DAYS;
/* 1075 */       } else if (0.5D * DAYS >= dFrameRefX) {
/* 1076 */         dMajorUnitX = 0.5D * DAYS;
/* 1077 */       } else if (1.0D * DAYS >= dFrameRefX) {
/* 1078 */         dMajorUnitX = 1.0D * DAYS;
/* 1079 */       } else if (5.0D * DAYS >= dFrameRefX) {
/* 1080 */         dMajorUnitX = 5.0D * DAYS;
/*      */       } else {
/* 1082 */         dMajorUnitX = 73.0D * DAYS;
/*      */       }
/* 1084 */       this.m_iMajorUnitTypeX = 7;
/* 1085 */       this.m_dMajorUnitXTypeValue = DAYS;
/* 1086 */       this.m_dNextMajorUnitXTypeValue = YEARS;
/* 1087 */       this.m_strXAxisLabel = "day";
/* 1088 */       this.m_strXAxisLabelShort = "day";
/* 1089 */       strNextXAxisLabel = "yr";
/*      */     }
/*      */     else
/*      */     {
/* 1093 */       if (0.5D * YEARS >= dFrameRefX) {
/* 1094 */         dMajorUnitX = 0.5D * YEARS;
/* 1095 */       } else if (1.0D * YEARS >= dFrameRefX) {
/* 1096 */         dMajorUnitX = 1.0D * YEARS;
/* 1097 */       } else if (2.0D * YEARS >= dFrameRefX) {
/* 1098 */         dMajorUnitX = 2.0D * YEARS;
/* 1099 */       } else if (5.0D * YEARS >= dFrameRefX) {
/* 1100 */         dMajorUnitX = 5.0D * YEARS;
/* 1101 */       } else if (10.0D * YEARS >= dFrameRefX) {
/* 1102 */         dMajorUnitX = 10.0D * YEARS;
/* 1103 */       } else if (20.0D * YEARS >= dFrameRefX) {
/* 1104 */         dMajorUnitX = 20.0D * YEARS;
/*      */       } else {
/* 1106 */         dMajorUnitX = 50.0D * YEARS;
/*      */       }
/* 1108 */       this.m_iMajorUnitTypeX = 8;
/* 1109 */       this.m_dMajorUnitXTypeValue = YEARS;
/* 1110 */       this.m_strXAxisLabel = "year";
/* 1111 */       this.m_strXAxisLabelShort = "yr";
/*      */     }
/*      */     
/* 1114 */     int iNumXDivisions = (int)(dDifferenceX / dMajorUnitX) + 2;
/*      */     
/*      */ 
/*      */     double xstart;
/*      */     
/*      */     double xstart;
/*      */     
/* 1121 */     if (this.m_drectView.left / dMajorUnitX <= Floor(this.m_drectView.left / dMajorUnitX))
/*      */     {
/* 1123 */       xstart = Floor(this.m_drectView.left / dMajorUnitX) * dMajorUnitX - dMajorUnitX;
/*      */     }
/*      */     else
/*      */     {
/* 1127 */       xstart = Floor(this.m_drectView.left / dMajorUnitX) * dMajorUnitX;
/*      */     }
/*      */     
/* 1130 */     int pixelMajorUnitX = (int)(dMajorUnitX / this.m_dXMultiplier) + 1;
/* 1131 */     int iMajorUnitsPerLabelX = metricsXAxisDivision.stringWidth("8888.8") / pixelMajorUnitX + 1;
/*      */     
/*      */ 
/* 1134 */     for (int i = 0; i < iNumXDivisions; i++)
/*      */     {
/* 1136 */       int xpos = (int)(this.m_rectGraph.left + (int)((xstart - this.m_drectView.left + dMajorUnitX * i) * this.m_dInvXMultiplier));
/*      */       
/* 1138 */       if ((xpos <= this.m_rectGraph.right) && 
/* 1139 */         (xpos >= this.m_rectGraph.left))
/*      */       {
/* 1141 */         double dValue = xstart + dMajorUnitX * i;
/*      */         
/*      */         double dNextBaseValue;
/*      */         double dBaseValue;
/*      */         double dNextBaseValue;
/* 1146 */         if (this.m_iMajorUnitTypeX == 8)
/*      */         {
/* 1148 */           double dBaseValue = 0.0D;
/* 1149 */           dNextBaseValue = 0.0D;
/*      */         } else { double dNextBaseValue;
/* 1151 */           if (this.m_iMajorUnitTypeX == 7)
/*      */           {
/* 1153 */             double dBaseValue = Floor(dValue / this.m_dNextMajorUnitXTypeValue) * this.m_dNextMajorUnitXTypeValue;
/* 1154 */             dNextBaseValue = 0.0D;
/*      */           }
/*      */           else
/*      */           {
/* 1158 */             dBaseValue = Floor(dValue / this.m_dNextMajorUnitXTypeValue) * this.m_dNextMajorUnitXTypeValue;
/* 1159 */             dNextBaseValue = Floor(dValue / this.m_dNextNextMajorUnitXTypeValue) * this.m_dNextNextMajorUnitXTypeValue;
/*      */           }
/*      */         }
/* 1162 */         double dDisplayValue = (dValue - dBaseValue) / this.m_dMajorUnitXTypeValue;
/* 1163 */         double dNextDisplayValue = (dValue - dNextBaseValue) / this.m_dNextMajorUnitXTypeValue;
/*      */         
/*      */ 
/* 1166 */         if (dDisplayValue == 0.0D)
/*      */         {
/*      */ 
/*      */ 
/* 1170 */           gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 1171 */           gl2.glBegin(1);
/* 1172 */           gl2.glVertex2i(xpos, (int)(this.m_rectGraph.bottom - GREAT_LINE_LENGTH));
/* 1173 */           gl2.glVertex2i(xpos, (int)this.m_rectGraph.bottom);
/* 1174 */           gl2.glEnd();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1179 */           String str = decformat.format(dNextDisplayValue);
/*      */           
/* 1181 */           if (dValue != 0.0D) {
/* 1182 */             str = str + " " + strNextXAxisLabel;
/*      */           }
/* 1184 */           printGL(this.m_rendererXAxisDivision, xpos, (int)this.m_rectGraph.bottom - GREAT_LINE_LENGTH - metricsXAxisDivision.getAscent() - 2, JUSTIFY_CENTER, 0.0F, str);
/*      */         }
/*      */         else
/*      */         {
/* 1188 */           double x2 = dValue / dMajorUnitX / iMajorUnitsPerLabelX;
/* 1189 */           double x3 = Floor(x2);
/*      */           
/* 1191 */           if (x2 == x3)
/*      */           {
/*      */ 
/* 1194 */             String str = decformat.format(dDisplayValue);
/* 1195 */             printGL(this.m_rendererXAxisDivision, xpos, (int)this.m_rectGraph.bottom - MAJOR_LINE_LENGTH - metricsXAxisDivision.getAscent() - 2, JUSTIFY_CENTER, 0.0F, str);
/*      */           }
/*      */           
/*      */ 
/* 1199 */           gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 1200 */           gl2.glBegin(1);
/* 1201 */           gl2.glVertex2i(xpos, (int)(this.m_rectGraph.bottom - MAJOR_LINE_LENGTH));
/* 1202 */           gl2.glVertex2i(xpos, (int)this.m_rectGraph.bottom);
/* 1203 */           gl2.glEnd();
/*      */         }
/*      */         
/*      */ 
/* 1207 */         gl2.glColor3f(0.8627451F, 0.8627451F, 0.8627451F);
/* 1208 */         gl2.glBegin(1);
/* 1209 */         gl2.glVertex2i(xpos, (int)this.m_rectGraph.bottom);
/* 1210 */         gl2.glVertex2i(xpos, (int)this.m_rectGraph.top);
/* 1211 */         gl2.glEnd();
/*      */       }
/*      */       
/*      */ 
/* 1215 */       gl2.glColor3f(0.2F, 0.2F, 0.2F);
/*      */       
/* 1217 */       for (int j = 1; j < 5; j++)
/*      */       {
/* 1219 */         int extraxpos = (int)(dMajorUnitX * (j / 5.0D) * this.m_dInvXMultiplier);
/*      */         
/* 1221 */         if ((xpos + extraxpos <= this.m_rectGraph.right) && 
/* 1222 */           (xpos + extraxpos >= this.m_rectGraph.left))
/*      */         {
/* 1224 */           gl2.glBegin(1);
/* 1225 */           gl2.glVertex2i(xpos + extraxpos, (int)(this.m_rectGraph.bottom - MINOR_LINE_LENGTH));
/* 1226 */           gl2.glVertex2i(xpos + extraxpos, (int)this.m_rectGraph.bottom);
/* 1227 */           gl2.glEnd();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1238 */     boolean leftneg = false;
/* 1239 */     boolean rightneg = false;
/*      */     
/* 1241 */     String strTopLeft = "";
/* 1242 */     String strTopRight = "";
/* 1243 */     String strBottomLeft = "";
/* 1244 */     String strBottomRight = "";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1249 */     switch (this.m_iMajorUnitTypeX)
/*      */     {
/*      */ 
/*      */     case 1: 
/* 1253 */       int leftvalue = (int)((this.m_drectView.left - Floor(this.m_drectView.left / MILLISECONDS) * MILLISECONDS) / MICROSECONDS);
/* 1254 */       int rightvalue = (int)((this.m_drectView.right - Floor(this.m_drectView.right / MILLISECONDS) * MILLISECONDS) / MICROSECONDS);
/* 1255 */       if (leftvalue < 0)
/*      */       {
/* 1257 */         leftvalue = Math.abs(leftvalue);
/* 1258 */         leftneg = true;
/*      */       }
/* 1260 */       if (rightvalue < 0)
/*      */       {
/* 1262 */         rightvalue = Math.abs(rightvalue);
/* 1263 */         rightneg = true;
/*      */       }
/* 1265 */       strBottomLeft = String.format(".%03d", new Object[] { Integer.valueOf(leftvalue) });
/* 1266 */       strBottomRight = String.format(".%03d", new Object[] { Integer.valueOf(rightvalue) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 2: 
/* 1272 */       int leftvalue = (int)((this.m_drectView.left - Floor(this.m_drectView.left / SECONDS) * SECONDS) / MILLISECONDS);
/* 1273 */       int rightvalue = (int)((this.m_drectView.right - Floor(this.m_drectView.right / SECONDS) * SECONDS) / MILLISECONDS);
/* 1274 */       if (leftvalue < 0)
/*      */       {
/* 1276 */         leftvalue = Math.abs(leftvalue);
/* 1277 */         leftneg = true;
/*      */       }
/* 1279 */       if (rightvalue < 0)
/*      */       {
/* 1281 */         rightvalue = Math.abs(rightvalue);
/* 1282 */         rightneg = true;
/*      */       }
/*      */       
/* 1285 */       strBottomLeft = String.format(".%03d", new Object[] { Integer.valueOf(leftvalue) }) + strBottomLeft;
/* 1286 */       strBottomRight = String.format(".%03d", new Object[] { Integer.valueOf(rightvalue) }) + strBottomRight;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 3: 
/* 1292 */       int leftvalue = (int)((this.m_drectView.left - Floor(this.m_drectView.left / MINUTES) * MINUTES) / SECONDS);
/* 1293 */       int rightvalue = (int)((this.m_drectView.right - Floor(this.m_drectView.right / MINUTES) * MINUTES) / SECONDS);
/* 1294 */       if (leftvalue < 0)
/*      */       {
/* 1296 */         leftvalue = Math.abs(leftvalue);
/* 1297 */         leftneg = true;
/*      */       }
/* 1299 */       if (rightvalue < 0)
/*      */       {
/* 1301 */         rightvalue = Math.abs(rightvalue);
/* 1302 */         rightneg = true;
/*      */       }
/*      */       
/* 1305 */       strBottomLeft = String.format(":%02d", new Object[] { Integer.valueOf(leftvalue) }) + strBottomLeft;
/* 1306 */       strBottomRight = String.format(":%02d", new Object[] { Integer.valueOf(rightvalue) }) + strBottomRight;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 4: 
/* 1312 */       int leftvalue = (int)((this.m_drectView.left - Floor(this.m_drectView.left / HOURS) * HOURS) / MINUTES);
/* 1313 */       int rightvalue = (int)((this.m_drectView.right - Floor(this.m_drectView.right / HOURS) * HOURS) / MINUTES);
/* 1314 */       if (leftvalue < 0)
/*      */       {
/* 1316 */         leftvalue = Math.abs(leftvalue);
/* 1317 */         leftneg = true;
/*      */       }
/* 1319 */       if (rightvalue < 0)
/*      */       {
/* 1321 */         rightvalue = Math.abs(rightvalue);
/* 1322 */         rightneg = true;
/*      */       }
/* 1324 */       strBottomLeft = String.format(":%02d", new Object[] { Integer.valueOf(leftvalue) }) + strBottomLeft;
/* 1325 */       strBottomRight = String.format(":%02d", new Object[] { Integer.valueOf(rightvalue) }) + strBottomRight;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 5: 
/* 1331 */       int leftvalue = (int)((this.m_drectView.left - Floor(this.m_drectView.left / DAYS) * DAYS) / HOURS);
/* 1332 */       int rightvalue = (int)((this.m_drectView.right - Floor(this.m_drectView.right / DAYS) * DAYS) / HOURS);
/* 1333 */       if (this.m_iMajorUnitTypeX == 5)
/*      */       {
/* 1335 */         strBottomLeft = String.format("Hour %d", new Object[] { Integer.valueOf(leftvalue) });
/* 1336 */         strBottomRight = String.format("Hour %d", new Object[] { Integer.valueOf(rightvalue) });
/*      */       }
/*      */       else
/*      */       {
/* 1340 */         if (leftvalue < 0)
/*      */         {
/* 1342 */           leftvalue = Math.abs(leftvalue);
/* 1343 */           leftneg = true;
/*      */         }
/* 1345 */         if (rightvalue < 0)
/*      */         {
/* 1347 */           rightvalue = Math.abs(rightvalue);
/* 1348 */           rightneg = true;
/*      */         }
/* 1350 */         strBottomLeft = String.format("%02d", new Object[] { Integer.valueOf(leftvalue) }) + strBottomLeft;
/* 1351 */         strBottomRight = String.format("%02d", new Object[] { Integer.valueOf(rightvalue) }) + strBottomRight;
/* 1352 */         if (leftneg)
/* 1353 */           strBottomLeft = "-" + strBottomLeft;
/* 1354 */         if (rightneg) {
/* 1355 */           strBottomRight = "-" + strBottomRight;
/*      */         }
/*      */       }
/*      */     
/*      */ 
/*      */ 
/*      */     case 6: 
/* 1362 */       int leftvalue = (int)((this.m_drectView.left - Floor(this.m_drectView.left / YEARS) * YEARS) / DAYS);
/* 1363 */       int rightvalue = (int)((this.m_drectView.right - Floor(this.m_drectView.right / YEARS) * YEARS) / DAYS);
/* 1364 */       strTopLeft = String.format("Day %d", new Object[] { Integer.valueOf(leftvalue) });
/* 1365 */       strTopRight = String.format("Day %d", new Object[] { Integer.valueOf(rightvalue) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 7: 
/* 1371 */       int leftvalue = (int)Floor(this.m_drectView.left / YEARS);
/* 1372 */       int rightvalue = (int)Floor(this.m_drectView.right / YEARS);
/* 1373 */       if (this.m_iMajorUnitTypeX == 7)
/*      */       {
/*      */ 
/*      */ 
/* 1377 */         strTopLeft = "";
/* 1378 */         strTopRight = "";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */     
/*      */ 
/*      */ 
/* 1389 */     if (this.m_bXAxisRangeIndicatorsVisible)
/*      */     {
/* 1391 */       printGL(this.m_rendererXAxisDivision, (int)this.m_rectGraph.left, (int)(rectWindow.bottom + metricsXAxisDivision.getHeight() + 2.0D), JUSTIFY_LEFT, 0.0F, strTopLeft);
/* 1392 */       printGL(this.m_rendererXAxisDivision, (int)this.m_rectGraph.left, (int)(rectWindow.bottom + 2.0D), JUSTIFY_LEFT, 0.0F, strBottomLeft);
/* 1393 */       printGL(this.m_rendererXAxisDivision, (int)this.m_rectGraph.right, (int)(rectWindow.bottom + metricsXAxisDivision.getHeight() + 2.0D), JUSTIFY_RIGHT, 0.0F, strTopRight);
/* 1394 */       printGL(this.m_rendererXAxisDivision, (int)this.m_rectGraph.right, (int)(rectWindow.bottom + 2.0D), JUSTIFY_RIGHT, 0.0F, strBottomRight);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1401 */     double dFrameRefY = 20.0D * this.m_dYMultiplier;
/*      */     
/* 1403 */     double dDifferenceY = this.m_drectView.top - this.m_drectView.bottom;
/*      */     
/*      */     double dMajorUnitY;
/* 1406 */     if (dFrameRefY <= 50.0D * NANOUNITS) { double dMajorUnitY;
/*      */       double dMajorUnitY;
/* 1408 */       if (1.0D >= dFrameRefY) {
/* 1409 */         dMajorUnitY = 1.0D; } else { double dMajorUnitY;
/* 1410 */         if (2.0D >= dFrameRefY) {
/* 1411 */           dMajorUnitY = 2.0D; } else { double dMajorUnitY;
/* 1412 */           if (5.0D >= dFrameRefY) {
/* 1413 */             dMajorUnitY = 5.0D; } else { double dMajorUnitY;
/* 1414 */             if (10.0D >= dFrameRefY) {
/* 1415 */               dMajorUnitY = 10.0D; } else { double dMajorUnitY;
/* 1416 */               if (20.0D >= dFrameRefY) {
/* 1417 */                 dMajorUnitY = 20.0D;
/*      */               } else
/* 1419 */                 dMajorUnitY = 50.0D;
/*      */             } } } }
/* 1421 */       this.m_iMajorUnitTypeY = 1;
/* 1422 */       this.m_dMajorUnitYTypeValue = NANOUNITS;
/* 1423 */       this.m_dNextMajorUnitYTypeValue = MICROUNITS;
/* 1424 */       this.m_dNextNextMajorUnitYTypeValue = MILLIUNITS;
/* 1425 */       this.m_strYAxisLabel = ("nano" + this.m_strYAxisBaseUnit);
/* 1426 */       this.m_strYAxisLabelShort = ("n" + this.m_strYAxisBaseUnitShort);
/* 1427 */       strNextYAxisLabel = "μ" + this.m_strYAxisBaseUnitShort;
/*      */     }
/* 1429 */     else if (dFrameRefY <= 50.0D * MICROUNITS) { double dMajorUnitY;
/*      */       double dMajorUnitY;
/* 1431 */       if (0.1D * MICROUNITS >= dFrameRefY) {
/* 1432 */         dMajorUnitY = 0.1D * MICROUNITS; } else { double dMajorUnitY;
/* 1433 */         if (0.2D * MICROUNITS >= dFrameRefY) {
/* 1434 */           dMajorUnitY = 0.2D * MICROUNITS; } else { double dMajorUnitY;
/* 1435 */           if (0.5D * MICROUNITS >= dFrameRefY) {
/* 1436 */             dMajorUnitY = 0.5D * MICROUNITS; } else { double dMajorUnitY;
/* 1437 */             if (1.0D * MICROUNITS >= dFrameRefY) {
/* 1438 */               dMajorUnitY = 1.0D * MICROUNITS; } else { double dMajorUnitY;
/* 1439 */               if (2.0D * MICROUNITS >= dFrameRefY) {
/* 1440 */                 dMajorUnitY = 2.0D * MICROUNITS; } else { double dMajorUnitY;
/* 1441 */                 if (5.0D * MICROUNITS >= dFrameRefY) {
/* 1442 */                   dMajorUnitY = 5.0D * MICROUNITS; } else { double dMajorUnitY;
/* 1443 */                   if (10.0D * MICROUNITS >= dFrameRefY) {
/* 1444 */                     dMajorUnitY = 10.0D * MICROUNITS; } else { double dMajorUnitY;
/* 1445 */                     if (20.0D * MICROUNITS >= dFrameRefY) {
/* 1446 */                       dMajorUnitY = 20.0D * MICROUNITS;
/*      */                     } else
/* 1448 */                       dMajorUnitY = 50.0D * MICROUNITS;
/*      */                   } } } } } } }
/* 1450 */       this.m_iMajorUnitTypeY = 2;
/* 1451 */       this.m_dMajorUnitYTypeValue = MICROUNITS;
/* 1452 */       this.m_dNextMajorUnitYTypeValue = MILLIUNITS;
/* 1453 */       this.m_dNextNextMajorUnitYTypeValue = UNITS;
/* 1454 */       this.m_strYAxisLabel = ("micro" + this.m_strYAxisBaseUnit);
/* 1455 */       this.m_strYAxisLabelShort = ("μ" + this.m_strYAxisBaseUnitShort);
/* 1456 */       strNextYAxisLabel = "m" + this.m_strYAxisBaseUnitShort;
/*      */     }
/* 1458 */     else if (dFrameRefY <= 50.0D * MILLIUNITS) { double dMajorUnitY;
/*      */       double dMajorUnitY;
/* 1460 */       if (0.1D * MILLIUNITS >= dFrameRefY) {
/* 1461 */         dMajorUnitY = 0.1D * MILLIUNITS; } else { double dMajorUnitY;
/* 1462 */         if (0.2D * MILLIUNITS >= dFrameRefY) {
/* 1463 */           dMajorUnitY = 0.2D * MILLIUNITS; } else { double dMajorUnitY;
/* 1464 */           if (0.5D * MILLIUNITS >= dFrameRefY) {
/* 1465 */             dMajorUnitY = 0.5D * MILLIUNITS; } else { double dMajorUnitY;
/* 1466 */             if (1.0D * MILLIUNITS >= dFrameRefY) {
/* 1467 */               dMajorUnitY = 1.0D * MILLIUNITS; } else { double dMajorUnitY;
/* 1468 */               if (2.0D * MILLIUNITS >= dFrameRefY) {
/* 1469 */                 dMajorUnitY = 2.0D * MILLIUNITS; } else { double dMajorUnitY;
/* 1470 */                 if (5.0D * MILLIUNITS >= dFrameRefY) {
/* 1471 */                   dMajorUnitY = 5.0D * MILLIUNITS; } else { double dMajorUnitY;
/* 1472 */                   if (10.0D * MILLIUNITS >= dFrameRefY) {
/* 1473 */                     dMajorUnitY = 10.0D * MILLIUNITS; } else { double dMajorUnitY;
/* 1474 */                     if (20.0D * MILLIUNITS >= dFrameRefY) {
/* 1475 */                       dMajorUnitY = 20.0D * MILLIUNITS;
/*      */                     } else
/* 1477 */                       dMajorUnitY = 50.0D * MILLIUNITS;
/*      */                   } } } } } } }
/* 1479 */       this.m_iMajorUnitTypeY = 3;
/* 1480 */       this.m_dMajorUnitYTypeValue = MILLIUNITS;
/* 1481 */       this.m_dNextMajorUnitYTypeValue = UNITS;
/* 1482 */       this.m_dNextNextMajorUnitYTypeValue = KILOUNITS;
/* 1483 */       this.m_strYAxisLabel = ("milli" + this.m_strYAxisBaseUnit);
/* 1484 */       this.m_strYAxisLabelShort = ("m" + this.m_strYAxisBaseUnitShort);
/* 1485 */       strNextYAxisLabel = this.m_strYAxisBaseUnitShort;
/*      */     }
/* 1487 */     else if (dFrameRefY <= 50.0D * UNITS) { double dMajorUnitY;
/*      */       double dMajorUnitY;
/* 1489 */       if (0.1D * UNITS >= dFrameRefY) {
/* 1490 */         dMajorUnitY = 0.1D * UNITS; } else { double dMajorUnitY;
/* 1491 */         if (0.2D * UNITS >= dFrameRefY) {
/* 1492 */           dMajorUnitY = 0.2D * UNITS; } else { double dMajorUnitY;
/* 1493 */           if (0.5D * UNITS >= dFrameRefY) {
/* 1494 */             dMajorUnitY = 0.5D * UNITS; } else { double dMajorUnitY;
/* 1495 */             if (1.0D * UNITS >= dFrameRefY) {
/* 1496 */               dMajorUnitY = 1.0D * UNITS; } else { double dMajorUnitY;
/* 1497 */               if (2.0D * UNITS >= dFrameRefY) {
/* 1498 */                 dMajorUnitY = 2.0D * UNITS; } else { double dMajorUnitY;
/* 1499 */                 if (5.0D * UNITS >= dFrameRefY) {
/* 1500 */                   dMajorUnitY = 5.0D * UNITS; } else { double dMajorUnitY;
/* 1501 */                   if (10.0D * UNITS >= dFrameRefY) {
/* 1502 */                     dMajorUnitY = 10.0D * UNITS; } else { double dMajorUnitY;
/* 1503 */                     if (20.0D * UNITS >= dFrameRefY) {
/* 1504 */                       dMajorUnitY = 20.0D * UNITS;
/*      */                     } else
/* 1506 */                       dMajorUnitY = 50.0D * UNITS;
/*      */                   } } } } } } }
/* 1508 */       this.m_iMajorUnitTypeY = 4;
/* 1509 */       this.m_dMajorUnitYTypeValue = UNITS;
/* 1510 */       this.m_dNextMajorUnitYTypeValue = KILOUNITS;
/* 1511 */       this.m_dNextNextMajorUnitYTypeValue = MEGAUNITS;
/* 1512 */       this.m_strYAxisLabel = this.m_strYAxisBaseUnit;
/* 1513 */       this.m_strYAxisLabelShort = this.m_strYAxisBaseUnitShort;
/* 1514 */       strNextYAxisLabel = "k" + this.m_strYAxisBaseUnitShort;
/*      */     }
/* 1516 */     else if (dFrameRefY <= 50.0D * KILOUNITS) { double dMajorUnitY;
/*      */       double dMajorUnitY;
/* 1518 */       if (0.1D * KILOUNITS >= dFrameRefY) {
/* 1519 */         dMajorUnitY = 0.1D * KILOUNITS; } else { double dMajorUnitY;
/* 1520 */         if (0.2D * KILOUNITS >= dFrameRefY) {
/* 1521 */           dMajorUnitY = 0.2D * KILOUNITS; } else { double dMajorUnitY;
/* 1522 */           if (0.5D * KILOUNITS >= dFrameRefY) {
/* 1523 */             dMajorUnitY = 0.5D * KILOUNITS; } else { double dMajorUnitY;
/* 1524 */             if (1.0D * KILOUNITS >= dFrameRefY) {
/* 1525 */               dMajorUnitY = 1.0D * KILOUNITS; } else { double dMajorUnitY;
/* 1526 */               if (2.0D * KILOUNITS >= dFrameRefY) {
/* 1527 */                 dMajorUnitY = 2.0D * KILOUNITS; } else { double dMajorUnitY;
/* 1528 */                 if (5.0D * KILOUNITS >= dFrameRefY) {
/* 1529 */                   dMajorUnitY = 5.0D * KILOUNITS; } else { double dMajorUnitY;
/* 1530 */                   if (10.0D * KILOUNITS >= dFrameRefY) {
/* 1531 */                     dMajorUnitY = 10.0D * KILOUNITS; } else { double dMajorUnitY;
/* 1532 */                     if (20.0D * KILOUNITS >= dFrameRefY) {
/* 1533 */                       dMajorUnitY = 20.0D * KILOUNITS;
/*      */                     } else
/* 1535 */                       dMajorUnitY = 50.0D * KILOUNITS;
/*      */                   } } } } } } }
/* 1537 */       this.m_iMajorUnitTypeY = 5;
/* 1538 */       this.m_dMajorUnitYTypeValue = KILOUNITS;
/* 1539 */       this.m_dNextMajorUnitYTypeValue = MEGAUNITS;
/* 1540 */       this.m_strYAxisLabel = ("kilo" + this.m_strYAxisBaseUnit);
/* 1541 */       this.m_strYAxisLabelShort = ("k" + this.m_strYAxisBaseUnitShort);
/* 1542 */       strNextYAxisLabel = "M" + this.m_strYAxisBaseUnitShort;
/*      */     }
/*      */     else {
/*      */       double dMajorUnitY;
/* 1546 */       if (0.1D * MEGAUNITS >= dFrameRefY) {
/* 1547 */         dMajorUnitY = 0.1D * MEGAUNITS; } else { double dMajorUnitY;
/* 1548 */         if (0.2D * MEGAUNITS >= dFrameRefY) {
/* 1549 */           dMajorUnitY = 0.2D * MEGAUNITS; } else { double dMajorUnitY;
/* 1550 */           if (0.5D * MEGAUNITS >= dFrameRefY) {
/* 1551 */             dMajorUnitY = 0.5D * MEGAUNITS; } else { double dMajorUnitY;
/* 1552 */             if (1.0D * MEGAUNITS >= dFrameRefY) {
/* 1553 */               dMajorUnitY = 1.0D * MEGAUNITS; } else { double dMajorUnitY;
/* 1554 */               if (2.0D * MEGAUNITS >= dFrameRefY) {
/* 1555 */                 dMajorUnitY = 2.0D * MEGAUNITS; } else { double dMajorUnitY;
/* 1556 */                 if (5.0D * MEGAUNITS >= dFrameRefY) {
/* 1557 */                   dMajorUnitY = 5.0D * MEGAUNITS; } else { double dMajorUnitY;
/* 1558 */                   if (10.0D * MEGAUNITS >= dFrameRefY) {
/* 1559 */                     dMajorUnitY = 10.0D * MEGAUNITS; } else { double dMajorUnitY;
/* 1560 */                     if (20.0D * MEGAUNITS >= dFrameRefY) {
/* 1561 */                       dMajorUnitY = 20.0D * MEGAUNITS;
/*      */                     } else
/* 1563 */                       dMajorUnitY = 50.0D * MEGAUNITS;
/*      */                   } } } } } } }
/* 1565 */       this.m_iMajorUnitTypeY = 6;
/* 1566 */       this.m_dMajorUnitYTypeValue = MEGAUNITS;
/* 1567 */       this.m_strYAxisLabelShort = ("M" + this.m_strYAxisBaseUnitShort);
/* 1568 */       this.m_strYAxisLabel = ("mega" + this.m_strYAxisBaseUnit);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1575 */     int iNumYDivisions = (int)(dDifferenceY / dMajorUnitY) + 2;
/*      */     double ystart;
/*      */     double ystart;
/* 1578 */     if (this.m_drectView.bottom / dMajorUnitY <= Floor(this.m_drectView.bottom / dMajorUnitY))
/*      */     {
/* 1580 */       ystart = Floor(this.m_drectView.bottom / dMajorUnitY) * dMajorUnitY - dMajorUnitY;
/*      */     }
/*      */     else
/*      */     {
/* 1584 */       ystart = Floor(this.m_drectView.bottom / dMajorUnitY) * dMajorUnitY;
/*      */     }
/*      */     
/* 1587 */     int pixelMajorUnitY = (int)(dMajorUnitY / this.m_dYMultiplier) + 1;
/* 1588 */     int iMajorUnitsPerLabelY = metricsYAxisDivision.stringWidth("8888.8") / pixelMajorUnitY + 1;
/*      */     
/* 1590 */     for (i = 0; i < iNumYDivisions; i++)
/*      */     {
/* 1592 */       int ypos = (int)this.m_rectGraph.bottom + (int)((ystart - this.m_drectView.bottom + dMajorUnitY * i) * this.m_dInvYMultiplier);
/*      */       
/* 1594 */       if ((ypos >= this.m_rectGraph.bottom) && 
/* 1595 */         (ypos <= this.m_rectGraph.top))
/*      */       {
/* 1597 */         double dValue = ystart + dMajorUnitY * i;
/*      */         
/*      */         double dNextBaseValue;
/*      */         double dBaseValue;
/*      */         double dNextBaseValue;
/* 1602 */         if (this.m_iMajorUnitTypeY == 6)
/*      */         {
/* 1604 */           double dBaseValue = 0.0D;
/* 1605 */           dNextBaseValue = 0.0D;
/*      */         } else { double dNextBaseValue;
/* 1607 */           if (this.m_iMajorUnitTypeY == 5)
/*      */           {
/* 1609 */             double dBaseValue = Floor(dValue / this.m_dNextMajorUnitYTypeValue) * this.m_dNextMajorUnitYTypeValue;
/* 1610 */             dNextBaseValue = 0.0D;
/*      */           }
/*      */           else
/*      */           {
/* 1614 */             dBaseValue = Floor(dValue / this.m_dNextMajorUnitYTypeValue) * this.m_dNextMajorUnitYTypeValue;
/* 1615 */             dNextBaseValue = Floor(dValue / this.m_dNextNextMajorUnitYTypeValue) * this.m_dNextNextMajorUnitYTypeValue;
/*      */           }
/*      */         }
/* 1618 */         double dDisplayValue = (dValue - dBaseValue) / this.m_dMajorUnitYTypeValue;
/* 1619 */         double dNextDisplayValue = (dValue - dNextBaseValue) / this.m_dNextMajorUnitYTypeValue;
/*      */         
/*      */ 
/* 1622 */         if (dDisplayValue == 0.0D)
/*      */         {
/*      */ 
/*      */ 
/* 1626 */           gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 1627 */           gl2.glBegin(1);
/* 1628 */           gl2.glVertex2i((int)(this.m_rectGraph.left - GREAT_LINE_LENGTH), ypos);
/* 1629 */           gl2.glVertex2i((int)this.m_rectGraph.left, ypos);
/* 1630 */           gl2.glEnd();
/*      */           
/*      */ 
/* 1633 */           String str = decformat.format(dNextDisplayValue);
/*      */           
/*      */ 
/* 1636 */           if (dValue != 0.0D) {
/* 1637 */             str = str + strNextYAxisLabel;
/*      */           }
/* 1639 */           printGL(this.m_rendererYAxisDivision, (int)(this.m_rectGraph.left - GREAT_LINE_LENGTH - 2.0D), ypos, JUSTIFY_CENTER, 90.0F, str);
/*      */         }
/*      */         else
/*      */         {
/* 1643 */           double x2 = dValue / dMajorUnitY / iMajorUnitsPerLabelY;
/* 1644 */           double x3 = Floor(x2);
/*      */           
/* 1646 */           if (x2 == x3)
/*      */           {
/*      */ 
/* 1649 */             String str = decformat.format(dDisplayValue);
/*      */             
/* 1651 */             printGL(this.m_rendererYAxisDivision, (int)(this.m_rectGraph.left - MAJOR_LINE_LENGTH - 2.0D), ypos, JUSTIFY_CENTER, 90.0F, str);
/*      */           }
/*      */           
/*      */ 
/* 1655 */           gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 1656 */           gl2.glBegin(1);
/* 1657 */           gl2.glVertex2i((int)(this.m_rectGraph.left - MAJOR_LINE_LENGTH), ypos);
/* 1658 */           gl2.glVertex2i((int)this.m_rectGraph.left, ypos);
/* 1659 */           gl2.glEnd();
/*      */         }
/*      */         
/*      */ 
/* 1663 */         gl2.glColor3f(0.8627451F, 0.8627451F, 0.8627451F);
/* 1664 */         gl2.glBegin(1);
/* 1665 */         gl2.glVertex2i((int)this.m_rectGraph.left, ypos);
/* 1666 */         gl2.glVertex2i((int)this.m_rectGraph.right, ypos);
/* 1667 */         gl2.glEnd();
/*      */       }
/*      */       
/*      */ 
/* 1671 */       gl2.glColor3f(0.2F, 0.2F, 0.2F);
/*      */       
/* 1673 */       for (int j = 1; j < 5; j++)
/*      */       {
/* 1675 */         int extraypos = (int)(dMajorUnitY * (j / 5.0D) * this.m_dInvYMultiplier);
/* 1676 */         if ((ypos + extraypos >= this.m_rectGraph.bottom) && 
/* 1677 */           (ypos + extraypos <= this.m_rectGraph.top))
/*      */         {
/* 1679 */           gl2.glBegin(1);
/* 1680 */           gl2.glVertex2i((int)(this.m_rectGraph.left - MINOR_LINE_LENGTH), ypos + extraypos);
/* 1681 */           gl2.glVertex2i((int)this.m_rectGraph.left, ypos + extraypos);
/* 1682 */           gl2.glEnd();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1688 */     boolean topneg = false;
/* 1689 */     boolean bottomneg = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1694 */     String strTop = "";
/* 1695 */     String strBottom = "";
/*      */     
/*      */ 
/*      */ 
/* 1699 */     switch (this.m_iMajorUnitTypeY)
/*      */     {
/*      */ 
/*      */     case 1: 
/* 1703 */       int topvalue = (int)((this.m_drectView.top - Floor(this.m_drectView.top / MILLIUNITS) * MILLIUNITS) / MICROUNITS);
/* 1704 */       int bottomvalue = (int)((this.m_drectView.bottom - Floor(this.m_drectView.bottom / MILLIUNITS) * MILLIUNITS) / MICROUNITS);
/* 1705 */       if (topvalue < 0)
/*      */       {
/* 1707 */         topvalue = Math.abs(topvalue);
/* 1708 */         topneg = true;
/*      */       }
/* 1710 */       if (bottomvalue < 0)
/*      */       {
/* 1712 */         bottomvalue = Math.abs(bottomvalue);
/* 1713 */         bottomneg = true;
/*      */       }
/*      */       
/* 1716 */       strTop = String.format(" %03d", new Object[] { Integer.valueOf(topvalue) }) + strTop;
/* 1717 */       strBottom = String.format(" %03d", new Object[] { Integer.valueOf(bottomvalue) }) + strBottom;
/*      */     
/*      */ 
/*      */     case 2: 
/* 1721 */       int topvalue = (int)((this.m_drectView.top - Floor(this.m_drectView.top / UNITS) * UNITS) / MILLIUNITS);
/* 1722 */       int bottomvalue = (int)((this.m_drectView.bottom - Floor(this.m_drectView.bottom / UNITS) * UNITS) / MILLIUNITS);
/* 1723 */       if (topvalue < 0)
/*      */       {
/* 1725 */         topvalue = Math.abs(topvalue);
/* 1726 */         topneg = true;
/*      */       }
/* 1728 */       if (bottomvalue < 0)
/*      */       {
/* 1730 */         bottomvalue = Math.abs(bottomvalue);
/* 1731 */         bottomneg = true;
/*      */       }
/*      */       
/* 1734 */       strTop = String.format(".%03d", new Object[] { Integer.valueOf(topvalue) }) + strTop;
/* 1735 */       strBottom = String.format(".%03d", new Object[] { Integer.valueOf(bottomvalue) }) + strBottom;
/*      */     
/*      */ 
/*      */     case 3: 
/* 1739 */       int topvalue = (int)Floor(this.m_drectView.top / UNITS);
/* 1740 */       int bottomvalue = (int)Floor(this.m_drectView.bottom / UNITS);
/* 1741 */       if (topvalue < 0)
/*      */       {
/* 1743 */         topvalue = Math.abs(topvalue);
/* 1744 */         topneg = true;
/*      */       }
/* 1746 */       if (bottomvalue < 0)
/*      */       {
/* 1748 */         bottomvalue = Math.abs(bottomvalue);
/* 1749 */         bottomneg = true;
/*      */       }
/*      */       
/* 1752 */       strTop = String.format("%d", new Object[] { Integer.valueOf(topvalue) }) + strTop + " " + this.m_strYAxisBaseUnitShort;
/* 1753 */       strBottom = String.format("%d", new Object[] { Integer.valueOf(bottomvalue) }) + strBottom + " " + this.m_strYAxisBaseUnitShort;
/*      */       
/* 1755 */       if (topneg)
/* 1756 */         strTop = "-" + strTop;
/* 1757 */       if (bottomneg) {
/* 1758 */         strBottom = "-" + strBottom;
/*      */       }
/* 1760 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/* 1764 */       int topvalue = (int)Floor(this.m_drectView.top / KILOUNITS);
/* 1765 */       int bottomvalue = (int)Floor(this.m_drectView.bottom / KILOUNITS);
/*      */       
/* 1767 */       strTop = String.format("%d", new Object[] { Integer.valueOf(topvalue) }) + strTop + " k" + this.m_strYAxisBaseUnitShort;
/* 1768 */       strBottom = String.format("%d", new Object[] { Integer.valueOf(bottomvalue) }) + strBottom + " k" + this.m_strYAxisBaseUnitShort;
/* 1769 */       break;
/*      */     
/*      */ 
/*      */     case 5: 
/* 1773 */       int topvalue = (int)Floor(this.m_drectView.top / MEGAUNITS);
/* 1774 */       int bottomvalue = (int)Floor(this.m_drectView.bottom / MEGAUNITS);
/*      */       
/* 1776 */       strTop = String.format("%d", new Object[] { Integer.valueOf(topvalue) }) + strTop + " M" + this.m_strYAxisBaseUnitShort;
/* 1777 */       strBottom = String.format("%d", new Object[] { Integer.valueOf(bottomvalue) }) + strBottom + " M" + this.m_strYAxisBaseUnitShort;
/*      */     }
/*      */     
/*      */     
/*      */ 
/*      */ 
/* 1783 */     if (this.m_bYAxisRangeIndicatorsVisible)
/*      */     {
/* 1785 */       printGL(this.m_rendererYAxisDivision, (int)(rectWindow.left + 2.0D + metricsYAxisLabel.getHeight() + metricsYAxisDivision.getHeight()), (int)this.m_rectGraph.bottom, JUSTIFY_LEFT, 90.0F, strBottom);
/* 1786 */       printGL(this.m_rendererYAxisDivision, (int)(rectWindow.left + 2.0D + metricsYAxisLabel.getHeight() + metricsYAxisDivision.getHeight()), (int)this.m_rectGraph.top, JUSTIFY_RIGHT, 90.0F, strTop);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1792 */     if (this.m_bSecondYAxisVisible)
/*      */     {
/*      */ 
/*      */ 
/* 1796 */       double dSecondFrameRefY = 20.0D * this.m_dSecondYMultiplier;
/*      */       
/* 1798 */       double dSecondDifferenceY = this.m_dSecondYAxisUpperLimit - this.m_dSecondYAxisLowerLimit;
/*      */       
/*      */       double dSecondMajorUnitY;
/* 1801 */       if (dSecondFrameRefY <= 50.0D * NANOUNITS) { double dSecondMajorUnitY;
/*      */         double dSecondMajorUnitY;
/* 1803 */         if (1.0D >= dSecondFrameRefY) {
/* 1804 */           dSecondMajorUnitY = 1.0D; } else { double dSecondMajorUnitY;
/* 1805 */           if (2.0D >= dSecondFrameRefY) {
/* 1806 */             dSecondMajorUnitY = 2.0D; } else { double dSecondMajorUnitY;
/* 1807 */             if (5.0D >= dSecondFrameRefY) {
/* 1808 */               dSecondMajorUnitY = 5.0D; } else { double dSecondMajorUnitY;
/* 1809 */               if (10.0D >= dSecondFrameRefY) {
/* 1810 */                 dSecondMajorUnitY = 10.0D; } else { double dSecondMajorUnitY;
/* 1811 */                 if (20.0D >= dSecondFrameRefY) {
/* 1812 */                   dSecondMajorUnitY = 20.0D;
/*      */                 } else
/* 1814 */                   dSecondMajorUnitY = 50.0D;
/*      */               } } } }
/* 1816 */         this.m_iSecondMajorUnitTypeY = 1;
/* 1817 */         this.m_dSecondMajorUnitYTypeValue = NANOUNITS;
/* 1818 */         this.m_dSecondNextMajorUnitYTypeValue = MICROUNITS;
/* 1819 */         this.m_dSecondNextNextMajorUnitYTypeValue = MILLIUNITS;
/* 1820 */         this.m_strSecondYAxisLabel = ("nano" + this.m_strSecondYAxisBaseUnit);
/* 1821 */         this.m_strSecondYAxisLabelShort = ("n" + this.m_strSecondYAxisBaseUnitShort);
/* 1822 */         strSecondNextYAxisLabel = "μ" + this.m_strSecondYAxisBaseUnitShort;
/*      */       }
/* 1824 */       else if (dSecondFrameRefY <= 50.0D * MICROUNITS) { double dSecondMajorUnitY;
/*      */         double dSecondMajorUnitY;
/* 1826 */         if (0.1D * MICROUNITS >= dSecondFrameRefY) {
/* 1827 */           dSecondMajorUnitY = 0.1D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1828 */           if (0.2D * MICROUNITS >= dSecondFrameRefY) {
/* 1829 */             dSecondMajorUnitY = 0.2D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1830 */             if (0.5D * MICROUNITS >= dSecondFrameRefY) {
/* 1831 */               dSecondMajorUnitY = 0.5D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1832 */               if (1.0D * MICROUNITS >= dSecondFrameRefY) {
/* 1833 */                 dSecondMajorUnitY = 1.0D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1834 */                 if (2.0D * MICROUNITS >= dSecondFrameRefY) {
/* 1835 */                   dSecondMajorUnitY = 2.0D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1836 */                   if (5.0D * MICROUNITS >= dSecondFrameRefY) {
/* 1837 */                     dSecondMajorUnitY = 5.0D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1838 */                     if (10.0D * MICROUNITS >= dSecondFrameRefY) {
/* 1839 */                       dSecondMajorUnitY = 10.0D * MICROUNITS; } else { double dSecondMajorUnitY;
/* 1840 */                       if (20.0D * MICROUNITS >= dSecondFrameRefY) {
/* 1841 */                         dSecondMajorUnitY = 20.0D * MICROUNITS;
/*      */                       } else
/* 1843 */                         dSecondMajorUnitY = 50.0D * MICROUNITS;
/*      */                     } } } } } } }
/* 1845 */         this.m_iSecondMajorUnitTypeY = 2;
/* 1846 */         this.m_dSecondMajorUnitYTypeValue = MICROUNITS;
/* 1847 */         this.m_dSecondNextMajorUnitYTypeValue = MILLIUNITS;
/* 1848 */         this.m_dSecondNextNextMajorUnitYTypeValue = UNITS;
/* 1849 */         this.m_strSecondYAxisLabel = ("micro" + this.m_strSecondYAxisBaseUnit);
/* 1850 */         this.m_strSecondYAxisLabelShort = ("μ" + this.m_strSecondYAxisBaseUnitShort);
/* 1851 */         strSecondNextYAxisLabel = "m" + this.m_strSecondYAxisBaseUnitShort;
/*      */       }
/* 1853 */       else if (dSecondFrameRefY <= 50.0D * MILLIUNITS) { double dSecondMajorUnitY;
/*      */         double dSecondMajorUnitY;
/* 1855 */         if (0.1D * MILLIUNITS >= dSecondFrameRefY) {
/* 1856 */           dSecondMajorUnitY = 0.1D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1857 */           if (0.2D * MILLIUNITS >= dSecondFrameRefY) {
/* 1858 */             dSecondMajorUnitY = 0.2D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1859 */             if (0.5D * MILLIUNITS >= dSecondFrameRefY) {
/* 1860 */               dSecondMajorUnitY = 0.5D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1861 */               if (1.0D * MILLIUNITS >= dSecondFrameRefY) {
/* 1862 */                 dSecondMajorUnitY = 1.0D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1863 */                 if (2.0D * MILLIUNITS >= dSecondFrameRefY) {
/* 1864 */                   dSecondMajorUnitY = 2.0D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1865 */                   if (5.0D * MILLIUNITS >= dSecondFrameRefY) {
/* 1866 */                     dSecondMajorUnitY = 5.0D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1867 */                     if (10.0D * MILLIUNITS >= dSecondFrameRefY) {
/* 1868 */                       dSecondMajorUnitY = 10.0D * MILLIUNITS; } else { double dSecondMajorUnitY;
/* 1869 */                       if (20.0D * MILLIUNITS >= dSecondFrameRefY) {
/* 1870 */                         dSecondMajorUnitY = 20.0D * MILLIUNITS;
/*      */                       } else
/* 1872 */                         dSecondMajorUnitY = 50.0D * MILLIUNITS;
/*      */                     } } } } } } }
/* 1874 */         this.m_iSecondMajorUnitTypeY = 3;
/* 1875 */         this.m_dSecondMajorUnitYTypeValue = MILLIUNITS;
/* 1876 */         this.m_dSecondNextMajorUnitYTypeValue = UNITS;
/* 1877 */         this.m_dSecondNextNextMajorUnitYTypeValue = KILOUNITS;
/* 1878 */         this.m_strSecondYAxisLabel = ("milli" + this.m_strSecondYAxisBaseUnit);
/* 1879 */         this.m_strSecondYAxisLabelShort = ("m" + this.m_strSecondYAxisBaseUnitShort);
/* 1880 */         strSecondNextYAxisLabel = this.m_strSecondYAxisBaseUnitShort;
/*      */       }
/* 1882 */       else if (dSecondFrameRefY <= 50.0D * UNITS) { double dSecondMajorUnitY;
/*      */         double dSecondMajorUnitY;
/* 1884 */         if (0.1D * UNITS >= dSecondFrameRefY) {
/* 1885 */           dSecondMajorUnitY = 0.1D * UNITS; } else { double dSecondMajorUnitY;
/* 1886 */           if (0.2D * UNITS >= dSecondFrameRefY) {
/* 1887 */             dSecondMajorUnitY = 0.2D * UNITS; } else { double dSecondMajorUnitY;
/* 1888 */             if (0.5D * UNITS >= dSecondFrameRefY) {
/* 1889 */               dSecondMajorUnitY = 0.5D * UNITS; } else { double dSecondMajorUnitY;
/* 1890 */               if (1.0D * UNITS >= dSecondFrameRefY) {
/* 1891 */                 dSecondMajorUnitY = 1.0D * UNITS; } else { double dSecondMajorUnitY;
/* 1892 */                 if (2.0D * UNITS >= dSecondFrameRefY) {
/* 1893 */                   dSecondMajorUnitY = 2.0D * UNITS; } else { double dSecondMajorUnitY;
/* 1894 */                   if (5.0D * UNITS >= dSecondFrameRefY) {
/* 1895 */                     dSecondMajorUnitY = 5.0D * UNITS; } else { double dSecondMajorUnitY;
/* 1896 */                     if (10.0D * UNITS >= dSecondFrameRefY) {
/* 1897 */                       dSecondMajorUnitY = 10.0D * UNITS; } else { double dSecondMajorUnitY;
/* 1898 */                       if (20.0D * UNITS >= dSecondFrameRefY) {
/* 1899 */                         dSecondMajorUnitY = 20.0D * UNITS;
/*      */                       } else
/* 1901 */                         dSecondMajorUnitY = 50.0D * UNITS;
/*      */                     } } } } } } }
/* 1903 */         this.m_iSecondMajorUnitTypeY = 4;
/* 1904 */         this.m_dSecondMajorUnitYTypeValue = UNITS;
/* 1905 */         this.m_dSecondNextMajorUnitYTypeValue = KILOUNITS;
/* 1906 */         this.m_dSecondNextNextMajorUnitYTypeValue = MEGAUNITS;
/* 1907 */         this.m_strSecondYAxisLabel = this.m_strSecondYAxisBaseUnit;
/* 1908 */         this.m_strSecondYAxisLabelShort = this.m_strSecondYAxisBaseUnitShort;
/* 1909 */         strSecondNextYAxisLabel = "k" + this.m_strSecondYAxisBaseUnitShort;
/*      */       }
/* 1911 */       else if (dSecondFrameRefY <= 50.0D * KILOUNITS) { double dSecondMajorUnitY;
/*      */         double dSecondMajorUnitY;
/* 1913 */         if (0.1D * KILOUNITS >= dSecondFrameRefY) {
/* 1914 */           dSecondMajorUnitY = 0.1D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1915 */           if (0.2D * KILOUNITS >= dSecondFrameRefY) {
/* 1916 */             dSecondMajorUnitY = 0.2D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1917 */             if (0.5D * KILOUNITS >= dSecondFrameRefY) {
/* 1918 */               dSecondMajorUnitY = 0.5D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1919 */               if (1.0D * KILOUNITS >= dSecondFrameRefY) {
/* 1920 */                 dSecondMajorUnitY = 1.0D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1921 */                 if (2.0D * KILOUNITS >= dSecondFrameRefY) {
/* 1922 */                   dSecondMajorUnitY = 2.0D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1923 */                   if (5.0D * KILOUNITS >= dSecondFrameRefY) {
/* 1924 */                     dSecondMajorUnitY = 5.0D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1925 */                     if (10.0D * KILOUNITS >= dSecondFrameRefY) {
/* 1926 */                       dSecondMajorUnitY = 10.0D * KILOUNITS; } else { double dSecondMajorUnitY;
/* 1927 */                       if (20.0D * KILOUNITS >= dSecondFrameRefY) {
/* 1928 */                         dSecondMajorUnitY = 20.0D * KILOUNITS;
/*      */                       } else
/* 1930 */                         dSecondMajorUnitY = 50.0D * KILOUNITS;
/*      */                     } } } } } } }
/* 1932 */         this.m_iSecondMajorUnitTypeY = 5;
/* 1933 */         this.m_dSecondMajorUnitYTypeValue = KILOUNITS;
/* 1934 */         this.m_dSecondNextMajorUnitYTypeValue = MEGAUNITS;
/* 1935 */         this.m_strSecondYAxisLabel = ("kilo" + this.m_strSecondYAxisBaseUnit);
/* 1936 */         this.m_strSecondYAxisLabelShort = ("k" + this.m_strSecondYAxisBaseUnitShort);
/* 1937 */         strSecondNextYAxisLabel = "M" + this.m_strSecondYAxisBaseUnitShort;
/*      */       }
/*      */       else {
/*      */         double dSecondMajorUnitY;
/* 1941 */         if (0.1D * MEGAUNITS >= dSecondFrameRefY) {
/* 1942 */           dSecondMajorUnitY = 0.1D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1943 */           if (0.2D * MEGAUNITS >= dSecondFrameRefY) {
/* 1944 */             dSecondMajorUnitY = 0.2D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1945 */             if (0.5D * MEGAUNITS >= dSecondFrameRefY) {
/* 1946 */               dSecondMajorUnitY = 0.5D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1947 */               if (1.0D * MEGAUNITS >= dSecondFrameRefY) {
/* 1948 */                 dSecondMajorUnitY = 1.0D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1949 */                 if (2.0D * MEGAUNITS >= dSecondFrameRefY) {
/* 1950 */                   dSecondMajorUnitY = 2.0D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1951 */                   if (5.0D * MEGAUNITS >= dSecondFrameRefY) {
/* 1952 */                     dSecondMajorUnitY = 5.0D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1953 */                     if (10.0D * MEGAUNITS >= dSecondFrameRefY) {
/* 1954 */                       dSecondMajorUnitY = 10.0D * MEGAUNITS; } else { double dSecondMajorUnitY;
/* 1955 */                       if (20.0D * MEGAUNITS >= dSecondFrameRefY) {
/* 1956 */                         dSecondMajorUnitY = 20.0D * MEGAUNITS;
/*      */                       } else
/* 1958 */                         dSecondMajorUnitY = 50.0D * MEGAUNITS;
/*      */                     } } } } } } }
/* 1960 */         this.m_iSecondMajorUnitTypeY = 6;
/* 1961 */         this.m_dSecondMajorUnitYTypeValue = MEGAUNITS;
/* 1962 */         this.m_strSecondYAxisLabelShort = ("M" + this.m_strSecondYAxisBaseUnitShort);
/* 1963 */         this.m_strSecondYAxisLabel = ("mega" + this.m_strSecondYAxisBaseUnit);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1968 */       int iSecondNumYDivisions = (int)(dSecondDifferenceY / dSecondMajorUnitY) + 2;
/*      */       
/*      */ 
/* 1971 */       if (this.m_dSecondYAxisLowerLimit / dSecondMajorUnitY <= Floor(this.m_dSecondYAxisLowerLimit / dSecondMajorUnitY))
/*      */       {
/* 1973 */         ystart = Floor(this.m_dSecondYAxisLowerLimit / dSecondMajorUnitY) * dSecondMajorUnitY - dSecondMajorUnitY;
/*      */       }
/*      */       else
/*      */       {
/* 1977 */         ystart = Floor(this.m_dSecondYAxisLowerLimit / dSecondMajorUnitY) * dSecondMajorUnitY;
/*      */       }
/*      */       
/* 1980 */       pixelMajorUnitY = (int)(dSecondMajorUnitY / this.m_dSecondYMultiplier) + 1;
/* 1981 */       iMajorUnitsPerLabelY = metricsYAxisDivision.stringWidth("8888.8") / pixelMajorUnitY + 1;
/*      */       
/* 1983 */       for (i = 0; i < iSecondNumYDivisions; i++)
/*      */       {
/* 1985 */         int ypos = (int)this.m_rectGraph.bottom + (int)((ystart - this.m_dSecondYAxisLowerLimit + dSecondMajorUnitY * i) * this.m_dSecondInvYMultiplier);
/*      */         
/* 1987 */         if ((ypos >= this.m_rectGraph.bottom) && 
/* 1988 */           (ypos <= this.m_rectGraph.top))
/*      */         {
/* 1990 */           double dValue = ystart + dSecondMajorUnitY * i;
/*      */           
/*      */           double dNextBaseValue;
/*      */           double dBaseValue;
/*      */           double dNextBaseValue;
/* 1995 */           if (this.m_iSecondMajorUnitTypeY == 6)
/*      */           {
/* 1997 */             double dBaseValue = 0.0D;
/* 1998 */             dNextBaseValue = 0.0D;
/*      */           } else { double dNextBaseValue;
/* 2000 */             if (this.m_iSecondMajorUnitTypeY == 5)
/*      */             {
/* 2002 */               double dBaseValue = Floor(dValue / this.m_dSecondNextMajorUnitYTypeValue) * this.m_dSecondNextMajorUnitYTypeValue;
/* 2003 */               dNextBaseValue = 0.0D;
/*      */             }
/*      */             else
/*      */             {
/* 2007 */               dBaseValue = Floor(dValue / this.m_dSecondNextMajorUnitYTypeValue) * this.m_dSecondNextMajorUnitYTypeValue;
/* 2008 */               dNextBaseValue = Floor(dValue / this.m_dSecondNextNextMajorUnitYTypeValue) * this.m_dSecondNextNextMajorUnitYTypeValue;
/*      */             }
/*      */           }
/* 2011 */           double dDisplayValue = (dValue - dBaseValue) / this.m_dSecondMajorUnitYTypeValue;
/* 2012 */           double dNextDisplayValue = (dValue - dNextBaseValue) / this.m_dSecondNextMajorUnitYTypeValue;
/*      */           
/*      */ 
/* 2015 */           if (dDisplayValue == 0.0D)
/*      */           {
/*      */ 
/*      */ 
/* 2019 */             gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 2020 */             gl2.glBegin(1);
/* 2021 */             gl2.glVertex2i((int)(this.m_rectGraph.right + GREAT_LINE_LENGTH), ypos);
/* 2022 */             gl2.glVertex2i((int)this.m_rectGraph.right, ypos);
/* 2023 */             gl2.glEnd();
/*      */             
/*      */ 
/* 2026 */             String str = decformat.format(dNextDisplayValue);
/*      */             
/*      */ 
/* 2029 */             if (dValue != 0.0D) {
/* 2030 */               str = str + strSecondNextYAxisLabel;
/*      */             }
/* 2032 */             printGL(this.m_rendererYAxisDivision, (int)(this.m_rectGraph.right + GREAT_LINE_LENGTH + 2.0D), ypos, JUSTIFY_CENTER, 270.0F, str);
/*      */           }
/*      */           else
/*      */           {
/* 2036 */             double x2 = dValue / dSecondMajorUnitY / iMajorUnitsPerLabelY;
/* 2037 */             double x3 = Floor(x2);
/*      */             
/* 2039 */             if (x2 == x3)
/*      */             {
/*      */ 
/* 2042 */               String str = decformat.format(dDisplayValue);
/*      */               
/* 2044 */               printGL(this.m_rendererYAxisDivision, (int)(this.m_rectGraph.right + MAJOR_LINE_LENGTH + 2.0D), ypos, JUSTIFY_CENTER, 270.0F, str);
/*      */             }
/*      */             
/*      */ 
/* 2048 */             gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 2049 */             gl2.glBegin(1);
/* 2050 */             gl2.glVertex2i((int)(this.m_rectGraph.right + MAJOR_LINE_LENGTH), ypos);
/* 2051 */             gl2.glVertex2i((int)this.m_rectGraph.right, ypos);
/* 2052 */             gl2.glEnd();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2064 */         gl2.glColor3f(0.2F, 0.2F, 0.2F);
/*      */         
/* 2066 */         for (int j = 1; j < 5; j++)
/*      */         {
/* 2068 */           int extraypos = (int)(dSecondMajorUnitY * (j / 5.0D) * this.m_dSecondInvYMultiplier);
/* 2069 */           if ((ypos + extraypos >= this.m_rectGraph.bottom) && 
/* 2070 */             (ypos + extraypos <= this.m_rectGraph.top))
/*      */           {
/* 2072 */             gl2.glBegin(1);
/* 2073 */             gl2.glVertex2i((int)(this.m_rectGraph.right + MINOR_LINE_LENGTH), ypos + extraypos);
/* 2074 */             gl2.glVertex2i((int)this.m_rectGraph.right, ypos + extraypos);
/* 2075 */             gl2.glEnd();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */     gl2.glColor3f(0.2F, 0.2F, 0.2F);
/* 2183 */     gl2.glBegin(3);
/* 2184 */     gl2.glVertex2i((int)this.m_rectGraph.left, (int)this.m_rectGraph.top);
/* 2185 */     gl2.glVertex2i((int)this.m_rectGraph.left, (int)this.m_rectGraph.bottom);
/* 2186 */     gl2.glVertex2i((int)this.m_rectGraph.right, (int)this.m_rectGraph.bottom);
/* 2187 */     if (this.m_bSecondYAxisVisible)
/* 2188 */       gl2.glVertex2i((int)this.m_rectGraph.right, (int)this.m_rectGraph.top);
/* 2189 */     gl2.glEnd();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2194 */     String str = String.format("%s (%s)", new Object[] { this.m_strYAxisTitle, this.m_strYAxisLabel });
/* 2195 */     printGL(this.m_rendererYAxisLabel, (int)(rectWindow.left + metricsYAxisLabel.getHeight()), (int)(this.m_rectGraph.bottom + Math.abs(this.m_rectGraph.top - this.m_rectGraph.bottom) / 2.0D), JUSTIFY_CENTER, 90.0F, str);
/*      */     
/*      */ 
/* 2198 */     if (this.m_bSecondYAxisVisible)
/*      */     {
/* 2200 */       str = String.format("%s (%s)", new Object[] { this.m_strSecondYAxisTitle, this.m_strSecondYAxisLabel });
/* 2201 */       printGL(this.m_rendererYAxisLabel, (int)(rectWindow.right - metricsYAxisLabel.getHeight()), (int)(this.m_rectGraph.bottom + Math.abs(this.m_rectGraph.top - this.m_rectGraph.bottom) / 2.0D), JUSTIFY_CENTER, 270.0F, str);
/*      */     }
/*      */     
/*      */ 
/* 2205 */     str = String.format("Time (%s)", new Object[] { this.m_strXAxisLabel });
/* 2206 */     int iBottomOfXMarker = (int)(this.m_rectGraph.bottom - (metricsXAxisDivision.getHeight() + GREAT_LINE_LENGTH));
/* 2207 */     int iSpaceOnBottom = (int)(iBottomOfXMarker - rectWindow.bottom);
/* 2208 */     int iTextPos = 0;
/* 2209 */     if (this.m_bXAxisRangeIndicatorsVisible) {
/* 2210 */       iTextPos = (int)(this.m_rectGraph.bottom - (metricsXAxisDivision.getHeight() + GREAT_LINE_LENGTH) - (iSpaceOnBottom / 2 + metricsXAxisLabel.getHeight() / 2));
/*      */     } else {
/* 2212 */       iTextPos = (int)(this.m_rectGraph.bottom - (metricsXAxisDivision.getHeight() + GREAT_LINE_LENGTH) - 3.0D - metricsXAxisLabel.getHeight() / 2);
/*      */     }
/* 2214 */     printGL(this.m_rendererXAxisLabel, (int)(this.m_rectGraph.left + (this.m_rectGraph.right - this.m_rectGraph.left) / 2.0D), iTextPos, JUSTIFY_CENTER, 0.0F, str);
/*      */   }
/*      */   
/*      */   public void DrawChannelLines()
/*      */   {
/* 2219 */     GL2 gl2 = getGL().getGL2();
/*      */     
/* 2221 */     DPoint dLastPoint = new DPoint();
/* 2222 */     DPoint dCurrentPoint = new DPoint();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2229 */     int x1 = 0;
/* 2230 */     int x2 = 0;
/* 2231 */     int y1 = 0;
/* 2232 */     int y2 = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2238 */     int i = 0;
/*      */     
/* 2240 */     for (i = 0; i < this.m_vectDataSeries.size(); i++)
/*      */     {
/*      */ 
/* 2243 */       gl2.glLineWidth(((DataSeries)this.m_vectDataSeries.get(i)).iLineThickness);
/*      */       
/*      */ 
/*      */ 
/* 2247 */       float linercolor = ((DataSeries)this.m_vectDataSeries.get(i)).clrLineColor.getRed() / 255.0F;
/* 2248 */       float linegcolor = ((DataSeries)this.m_vectDataSeries.get(i)).clrLineColor.getGreen() / 255.0F;
/* 2249 */       float linebcolor = ((DataSeries)this.m_vectDataSeries.get(i)).clrLineColor.getBlue() / 255.0F;
/*      */       
/* 2251 */       float markerrcolor = ((DataSeries)this.m_vectDataSeries.get(i)).clrLineColor.getRed() / 255.0F;
/* 2252 */       float markergcolor = ((DataSeries)this.m_vectDataSeries.get(i)).clrLineColor.getGreen() / 255.0F;
/* 2253 */       float markerbcolor = ((DataSeries)this.m_vectDataSeries.get(i)).clrLineColor.getBlue() / 255.0F;
/*      */       
/* 2255 */       int j = 0;
/*      */       
/*      */       double dRectViewBottom;
/*      */       double dInvYMultiplier;
/*      */       double dRectViewBottom;
/* 2260 */       if (!((DataSeries)this.m_vectDataSeries.get(i)).bUseSecondScale)
/*      */       {
/* 2262 */         double dInvYMultiplier = this.m_dInvYMultiplier;
/*      */         
/* 2264 */         dRectViewBottom = this.m_drectView.bottom;
/*      */       }
/*      */       else
/*      */       {
/* 2268 */         dInvYMultiplier = this.m_dSecondInvYMultiplier;
/*      */         
/* 2270 */         dRectViewBottom = this.m_dSecondYAxisLowerLimit;
/*      */       }
/*      */       
/* 2273 */       for (j = 0; j < ((DataSeries)this.m_vectDataSeries.get(i)).vectDataArray.size(); j++)
/*      */       {
/* 2275 */         if (j == 0)
/*      */         {
/* 2277 */           DPoint bdLastPoint = (DPoint)((DataSeries)this.m_vectDataSeries.get(i)).vectDataArray.get(0);
/* 2278 */           dLastPoint.x = (this.m_rectGraph.left + this.m_dInvXMultiplier * (bdLastPoint.x - this.m_drectView.left));
/* 2279 */           dLastPoint.y = (this.m_rectGraph.bottom + dInvYMultiplier * (bdLastPoint.y - dRectViewBottom));
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2284 */           DPoint bdCurrentPoint = (DPoint)((DataSeries)this.m_vectDataSeries.get(i)).vectDataArray.get(j);
/* 2285 */           dCurrentPoint.x = (this.m_rectGraph.left + this.m_dInvXMultiplier * (bdCurrentPoint.x - this.m_drectView.left));
/* 2286 */           dCurrentPoint.y = (this.m_rectGraph.bottom + dInvYMultiplier * (bdCurrentPoint.y - dRectViewBottom));
/*      */           DPoint dRightPoint;
/* 2288 */           DPoint dLeftPoint; DPoint dRightPoint; if (dCurrentPoint.x > dLastPoint.x)
/*      */           {
/* 2290 */             DPoint dLeftPoint = dLastPoint;
/* 2291 */             dRightPoint = dCurrentPoint;
/*      */           }
/*      */           else
/*      */           {
/* 2295 */             dLeftPoint = dCurrentPoint;
/* 2296 */             dRightPoint = dLastPoint;
/*      */           }
/*      */           
/*      */ 
/* 2300 */           if (((dLeftPoint.x >= this.m_rectGraph.left) || (dRightPoint.x >= this.m_rectGraph.left)) && 
/* 2301 */             ((dLeftPoint.x <= this.m_rectGraph.right) || (dRightPoint.x <= this.m_rectGraph.right)) && 
/* 2302 */             ((dLeftPoint.y >= this.m_rectGraph.bottom) || (dRightPoint.y >= this.m_rectGraph.bottom)) && (
/* 2303 */             (dLeftPoint.y <= this.m_rectGraph.top) || (dRightPoint.y <= this.m_rectGraph.top)))
/*      */           {
/*      */ 
/* 2306 */             if ((dLeftPoint.x >= this.m_rectGraph.left) && (dRightPoint.x >= this.m_rectGraph.left) && 
/* 2307 */               (dLeftPoint.x <= this.m_rectGraph.right) && (dRightPoint.x <= this.m_rectGraph.right) && 
/* 2308 */               (dLeftPoint.y >= this.m_rectGraph.bottom) && (dRightPoint.y >= this.m_rectGraph.bottom) && 
/* 2309 */               (dLeftPoint.y <= this.m_rectGraph.top) && (dRightPoint.y <= this.m_rectGraph.top))
/*      */             {
/* 2311 */               x1 = (int)dLeftPoint.x;
/* 2312 */               y1 = (int)dLeftPoint.y;
/* 2313 */               x2 = (int)dRightPoint.x;
/* 2314 */               y2 = (int)dRightPoint.y;
/*      */ 
/*      */             }
/* 2317 */             else if ((dLeftPoint.x >= this.m_rectGraph.left) && 
/* 2318 */               (dLeftPoint.x <= this.m_rectGraph.right) && 
/* 2319 */               (dLeftPoint.y >= this.m_rectGraph.bottom) && 
/* 2320 */               (dLeftPoint.y <= this.m_rectGraph.top) && (
/* 2321 */               (dRightPoint.y < this.m_rectGraph.bottom) || 
/* 2322 */               (dRightPoint.y > this.m_rectGraph.top) || 
/* 2323 */               (dRightPoint.x < this.m_rectGraph.left) || 
/* 2324 */               (dRightPoint.x > this.m_rectGraph.right)))
/*      */             {
/* 2326 */               double dSlope = (dRightPoint.y - dLeftPoint.y) / (dRightPoint.x - dLeftPoint.x);
/*      */               
/* 2328 */               double dRightVal = dSlope * (this.m_rectGraph.right - dLeftPoint.x) + dLeftPoint.y;
/*      */               
/* 2330 */               if (this.m_rectGraph.top < dRightVal)
/*      */               {
/* 2332 */                 x2 = (int)((this.m_rectGraph.top - dLeftPoint.y) / dSlope + dLeftPoint.x);
/* 2333 */                 y2 = (int)this.m_rectGraph.top;
/*      */               }
/* 2335 */               else if (this.m_rectGraph.bottom > dRightVal)
/*      */               {
/* 2337 */                 x2 = (int)((this.m_rectGraph.bottom - dLeftPoint.y) / dSlope + dLeftPoint.x);
/* 2338 */                 y2 = (int)this.m_rectGraph.bottom;
/*      */               }
/*      */               else
/*      */               {
/* 2342 */                 x2 = (int)this.m_rectGraph.right;
/* 2343 */                 y2 = (int)dRightVal;
/*      */               }
/*      */               
/* 2346 */               x1 = (int)dLeftPoint.x;
/* 2347 */               y1 = (int)dLeftPoint.y;
/*      */ 
/*      */             }
/* 2350 */             else if ((dRightPoint.x >= this.m_rectGraph.left) && 
/* 2351 */               (dRightPoint.x <= this.m_rectGraph.right) && 
/* 2352 */               (dRightPoint.y >= this.m_rectGraph.bottom) && 
/* 2353 */               (dRightPoint.y <= this.m_rectGraph.top) && (
/* 2354 */               (dLeftPoint.y < this.m_rectGraph.bottom) || 
/* 2355 */               (dLeftPoint.y > this.m_rectGraph.top) || 
/* 2356 */               (dLeftPoint.x < this.m_rectGraph.left) || 
/* 2357 */               (dLeftPoint.x > this.m_rectGraph.right)))
/*      */             {
/* 2359 */               double dSlope = (dRightPoint.y - dLeftPoint.y) / (dRightPoint.x - dLeftPoint.x);
/*      */               
/* 2361 */               double dLeftVal = dSlope * (this.m_rectGraph.left - dRightPoint.x) + dRightPoint.y;
/*      */               
/* 2363 */               if (this.m_rectGraph.top < dLeftVal)
/*      */               {
/* 2365 */                 x1 = (int)((this.m_rectGraph.top - dRightPoint.y) / dSlope + dRightPoint.x);
/* 2366 */                 y1 = (int)this.m_rectGraph.top;
/*      */               }
/* 2368 */               else if (this.m_rectGraph.bottom > dLeftVal)
/*      */               {
/* 2370 */                 x1 = (int)((this.m_rectGraph.bottom - dRightPoint.y) / dSlope + dRightPoint.x);
/* 2371 */                 y1 = (int)this.m_rectGraph.bottom;
/*      */               }
/*      */               else
/*      */               {
/* 2375 */                 x1 = (int)this.m_rectGraph.left;
/* 2376 */                 y1 = (int)dLeftVal;
/*      */               }
/*      */               
/* 2379 */               x2 = (int)dRightPoint.x;
/* 2380 */               y2 = (int)dRightPoint.y;
/*      */ 
/*      */             }
/* 2383 */             else if (((dRightPoint.x < this.m_rectGraph.left) || 
/* 2384 */               (dRightPoint.x > this.m_rectGraph.right) || 
/* 2385 */               (dRightPoint.y < this.m_rectGraph.bottom) || 
/* 2386 */               (dRightPoint.y > this.m_rectGraph.top)) && (
/* 2387 */               (dLeftPoint.x < this.m_rectGraph.left) || 
/* 2388 */               (dLeftPoint.x > this.m_rectGraph.right) || 
/* 2389 */               (dLeftPoint.y < this.m_rectGraph.bottom) || 
/* 2390 */               (dLeftPoint.y > this.m_rectGraph.top)))
/*      */             {
/* 2392 */               double dSlope = (dRightPoint.y - dLeftPoint.y) / (dRightPoint.x - dLeftPoint.x);
/*      */               
/*      */ 
/*      */ 
/* 2396 */               double dRightVal = dSlope * (this.m_rectGraph.right - dRightPoint.x) + dRightPoint.y;
/*      */               
/* 2398 */               double dLeftVal = dSlope * (this.m_rectGraph.left - dRightPoint.x) + dRightPoint.y;
/*      */               
/* 2400 */               if (this.m_rectGraph.top < dRightVal)
/*      */               {
/* 2402 */                 x2 = (int)((this.m_rectGraph.top - dRightPoint.y) / dSlope + dRightPoint.x);
/* 2403 */                 y2 = (int)this.m_rectGraph.top;
/*      */ 
/*      */               }
/* 2406 */               else if (this.m_rectGraph.bottom > dRightVal)
/*      */               {
/* 2408 */                 x2 = (int)((this.m_rectGraph.bottom - dRightPoint.y) / dSlope + dRightPoint.x);
/* 2409 */                 y2 = (int)this.m_rectGraph.bottom;
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 2414 */                 x2 = (int)this.m_rectGraph.right;
/* 2415 */                 y2 = (int)dRightVal;
/*      */               }
/*      */               
/*      */ 
/* 2419 */               if (this.m_rectGraph.top < dLeftVal)
/*      */               {
/* 2421 */                 x1 = (int)((this.m_rectGraph.top - dRightPoint.y) / dSlope + dRightPoint.x);
/* 2422 */                 y1 = (int)this.m_rectGraph.top;
/*      */ 
/*      */               }
/* 2425 */               else if (this.m_rectGraph.bottom > dLeftVal)
/*      */               {
/* 2427 */                 x1 = (int)((this.m_rectGraph.bottom - dRightPoint.y) / dSlope + dRightPoint.x);
/* 2428 */                 y1 = (int)this.m_rectGraph.bottom;
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 2433 */                 x1 = (int)this.m_rectGraph.left;
/* 2434 */                 y1 = (int)dLeftVal;
/*      */               }
/*      */             }
/*      */             
/* 2438 */             if (!((DataSeries)this.m_vectDataSeries.get(i)).bOnlyMarkers)
/*      */             {
/*      */ 
/* 2441 */               gl2.glColor3f(linercolor, linegcolor, linebcolor);
/* 2442 */               gl2.glBegin(1);
/* 2443 */               gl2.glVertex2i(x1, y1);
/* 2444 */               gl2.glVertex2i(x2, y2);
/* 2445 */               gl2.glEnd();
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 2450 */               gl2.glColor3f(markerrcolor, markergcolor, markerbcolor);
/* 2451 */               gl2.glBegin(6);
/* 2452 */               gl2.glVertex2i(x1, y1);
/* 2453 */               for (double dAngle = 0.0D; dAngle <= 360.0D; dAngle += 5.0D)
/*      */               {
/* 2455 */                 gl2.glVertex2d(x1 + Math.sin(dAngle) * 2.0D, y1 + Math.cos(dAngle) * 2.0D);
/*      */               }
/* 2457 */               gl2.glEnd();
/* 2458 */               if (j == ((DataSeries)this.m_vectDataSeries.get(i)).vectDataArray.size() - 1)
/*      */               {
/* 2460 */                 gl2.glBegin(6);
/* 2461 */                 gl2.glVertex2i(x2, y2);
/* 2462 */                 for (double dAngle = 0.0D; dAngle <= 360.0D; dAngle += 5.0D)
/*      */                 {
/* 2464 */                   gl2.glVertex2d(x2 + Math.sin(dAngle) * 2.0D, y2 + Math.cos(dAngle) * 2.0D);
/*      */                 }
/* 2466 */                 gl2.glEnd();
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 2471 */           DPoint bdLastPoint = bdCurrentPoint;
/* 2472 */           dLastPoint.x = dCurrentPoint.x;
/* 2473 */           dLastPoint.y = dCurrentPoint.y;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2480 */     gl2.glLineWidth(1.0F);
/*      */   }
/*      */   
/*      */   private void drawZoomBox()
/*      */   {
/* 2485 */     GL2 gl2 = getGL().getGL2();
/*      */     
/* 2487 */     if ((this.m_iMode != 1) && (this.m_iMode != 2)) {
/* 2488 */       return;
/*      */     }
/* 2490 */     if (this.m_bZoomToolTracking)
/*      */     {
/*      */ 
/*      */ 
/* 2494 */       gl2.glColor3f(0.3F, 0.3F, 0.3F);
/* 2495 */       gl2.glBegin(2);
/* 2496 */       gl2.glVertex2d(this.m_ZoomSelRect.left, this.m_ZoomSelRect.top);
/* 2497 */       gl2.glVertex2d(this.m_ZoomSelRect.right, this.m_ZoomSelRect.top);
/* 2498 */       gl2.glVertex2d(this.m_ZoomSelRect.right, this.m_ZoomSelRect.bottom);
/* 2499 */       gl2.glVertex2d(this.m_ZoomSelRect.left, this.m_ZoomSelRect.bottom);
/* 2500 */       gl2.glEnd();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void drawLineLabels()
/*      */   {
/* 2508 */     GL2 gl2 = getGL().getGL2();
/*      */     
/* 2510 */     gl2.glEnable(2852);
/* 2511 */     gl2.glLineStipple(1, (short)61680);
/* 2512 */     gl2.glColor3f(0.3F, 0.3F, 0.3F);
/* 2513 */     for (int i = 0; i < this.m_vectLineMarkers.size(); i++)
/*      */     {
/* 2515 */       gl2.glBegin(1);
/* 2516 */       gl2.glVertex2d(this.m_rectGraph.left + this.m_dInvXMultiplier * (((LineMarker)this.m_vectLineMarkers.get(i)).dTime * MINUTES - this.m_drectView.left), this.m_rectGraph.top);
/* 2517 */       gl2.glVertex2d(this.m_rectGraph.left + this.m_dInvXMultiplier * (((LineMarker)this.m_vectLineMarkers.get(i)).dTime * MINUTES - this.m_drectView.left), this.m_rectGraph.bottom);
/* 2518 */       gl2.glEnd();
/*      */       
/* 2520 */       printGLColor(this.m_rendererYAxisDivision, (int)(this.m_rectGraph.left + this.m_dInvXMultiplier * (((LineMarker)this.m_vectLineMarkers.get(i)).dTime * MINUTES - this.m_drectView.left)) - 1, (int)(this.m_rectGraph.bottom + 3.0D), JUSTIFY_LEFT, 90.0F, ((LineMarker)this.m_vectLineMarkers.get(i)).strMarkerName, 0.3F, 0.3F, 0.3F, 1.0F);
/*      */     }
/* 2522 */     gl2.glDisable(2852);
/*      */   }
/*      */   
/*      */ 
/*      */   private void printGL(TextRenderer renderer, int x, int y, int iJustification, float fAngle, String str)
/*      */   {
/* 2528 */     printGLColor(renderer, x, y, iJustification, fAngle, str, 0.2F, 0.2F, 0.2F, 1.0F);
/*      */   }
/*      */   
/*      */   private void printGLColor(TextRenderer renderer, int x, int y, int iJustification, float fAngle, String str, float R, float G, float B, float A)
/*      */   {
/* 2533 */     GL2 gl2 = getGL().getGL2();
/*      */     
/* 2535 */     Graphics graphics = getGraphics();
/*      */     
/* 2537 */     FontMetrics metrics = graphics.getFontMetrics(renderer.getFont());
/*      */     
/* 2539 */     renderer.beginRendering(getWidth(), getHeight());
/* 2540 */     gl2.glMatrixMode(5888);
/*      */     
/* 2542 */     renderer.setColor(R, G, B, A);
/*      */     
/* 2544 */     gl2.glLoadIdentity();
/* 2545 */     gl2.glTranslatef(x, y, 0.0F);
/* 2546 */     gl2.glRotatef(fAngle, 0.0F, 0.0F, 1.0F);
/*      */     
/* 2548 */     if (iJustification == JUSTIFY_CENTER)
/*      */     {
/* 2550 */       gl2.glTranslatef(0 - metrics.stringWidth(str) / 2, 0.0F, 0.0F);
/*      */     }
/* 2552 */     else if (iJustification == JUSTIFY_RIGHT)
/*      */     {
/* 2554 */       gl2.glTranslatef(0 - metrics.stringWidth(str), 0.0F, 0.0F);
/*      */     }
/*      */     
/* 2557 */     renderer.draw(str, 0, 0);
/*      */     
/* 2559 */     renderer.endRendering();
/*      */   }
/*      */   
/*      */   private double Floor(double dNumber)
/*      */   {
/* 2564 */     if (dNumber > 0.0D)
/* 2565 */       return Math.floor(dNumber);
/* 2566 */     if (dNumber < 0.0D) {
/* 2567 */       return Math.ceil(dNumber);
/*      */     }
/* 2569 */     return 0.0D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mouseDragged(MouseEvent arg0) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mouseMoved(MouseEvent arg0) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mouseClicked(MouseEvent arg0) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mouseEntered(MouseEvent arg0) {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void mouseExited(MouseEvent arg0) {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void mousePressed(MouseEvent arg0)
/*      */   {
/* 2600 */     if (!this.m_bControlsEnabled) {
/* 2601 */       return;
/*      */     }
/* 2603 */     if (this.m_iMode == 0)
/*      */     {
/* 2605 */       setCursor(this.m_curClosedHand);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void mouseReleased(MouseEvent arg0)
/*      */   {
/* 2612 */     if (!this.m_bControlsEnabled) {
/* 2613 */       return;
/*      */     }
/* 2615 */     if (this.m_iMode == 0)
/*      */     {
/* 2617 */       setCursor(this.m_curOpenHand);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setYAxisTitle(String strTitle)
/*      */   {
/* 2623 */     this.m_strYAxisTitle = strTitle;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSecondYAxisTitle(String strTitle)
/*      */   {
/* 2629 */     this.m_strSecondYAxisTitle = strTitle;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setYAxisBaseUnit(String strBaseUnit, String strBaseUnitAbbreviated)
/*      */   {
/* 2635 */     this.m_strYAxisBaseUnit = strBaseUnit;
/* 2636 */     this.m_strYAxisBaseUnitShort = strBaseUnitAbbreviated;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSecondYAxisBaseUnit(String strBaseUnit, String strBaseUnitAbbreviated)
/*      */   {
/* 2642 */     this.m_strSecondYAxisBaseUnit = strBaseUnit;
/* 2643 */     this.m_strSecondYAxisBaseUnitShort = strBaseUnitAbbreviated;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean setYAxisRangeLimits(double dMinimum, double dMaximum)
/*      */   {
/* 2649 */     if (dMaximum * UNITS > 9.0D * MEGAUNITS)
/* 2650 */       return false;
/* 2651 */     if (dMinimum * UNITS < -9.0D * MEGAUNITS)
/* 2652 */       return false;
/* 2653 */     if (dMaximum <= dMinimum) {
/* 2654 */       return false;
/*      */     }
/* 2656 */     this.m_dYAxisUpperLimit = (dMaximum * UNITS);
/* 2657 */     this.m_dYAxisLowerLimit = (dMinimum * UNITS);
/*      */     
/* 2659 */     return true;
/*      */   }
/*      */   
/*      */   public boolean setSecondYAxisRangeLimits(double dMinimum, double dMaximum)
/*      */   {
/* 2664 */     if (dMaximum * UNITS > 9.0D * MEGAUNITS)
/* 2665 */       return false;
/* 2666 */     if (dMinimum * UNITS < -9.0D * MEGAUNITS)
/* 2667 */       return false;
/* 2668 */     if (dMaximum <= dMinimum) {
/* 2669 */       return false;
/*      */     }
/* 2671 */     this.m_dSecondYAxisUpperLimit = (dMaximum * UNITS);
/* 2672 */     this.m_dSecondYAxisLowerLimit = (dMinimum * UNITS);
/*      */     
/* 2674 */     return true;
/*      */   }
/*      */   
/*      */   public void setYAxisRangeIndicatorsVisible(Boolean bVisible)
/*      */   {
/* 2679 */     this.m_bYAxisRangeIndicatorsVisible = bVisible.booleanValue();
/*      */   }
/*      */   
/*      */   public void setXAxisRangeIndicatorsVisible(Boolean bVisible)
/*      */   {
/* 2684 */     this.m_bXAxisRangeIndicatorsVisible = bVisible.booleanValue();
/*      */   }
/*      */   
/*      */   public void setSecondYAxisVisible(Boolean bVisible)
/*      */   {
/* 2689 */     this.m_bSecondYAxisVisible = bVisible.booleanValue();
/*      */   }
/*      */   
/*      */   public int AddSeries(String strSeriesName, Color clrLineColor, int iLineThickness, boolean bOnlyMarkers, boolean bUseSecondScale)
/*      */   {
/* 2694 */     DataSeries dataSeries = new DataSeries();
/*      */     
/* 2696 */     dataSeries.strName = strSeriesName;
/* 2697 */     dataSeries.clrLineColor = clrLineColor;
/* 2698 */     dataSeries.iLineThickness = iLineThickness;
/* 2699 */     dataSeries.bOnlyMarkers = bOnlyMarkers;
/* 2700 */     dataSeries.bUseSecondScale = bUseSecondScale;
/*      */     
/*      */ 
/* 2703 */     for (int i = 0; i <= this.m_vectDataSeries.size(); i++)
/*      */     {
/* 2705 */       boolean bFound = false;
/*      */       
/* 2707 */       for (int j = 0; j < this.m_vectDataSeries.size(); j++)
/*      */       {
/* 2709 */         if (((DataSeries)this.m_vectDataSeries.get(j)).iIndex == i)
/*      */         {
/* 2711 */           bFound = true;
/* 2712 */           break;
/*      */         }
/*      */       }
/*      */       
/* 2716 */       if (!bFound)
/*      */       {
/* 2718 */         dataSeries.iIndex = i;
/*      */       }
/*      */     }
/*      */     
/* 2722 */     this.m_vectDataSeries.add(dataSeries);
/*      */     
/* 2724 */     return dataSeries.iIndex;
/*      */   }
/*      */   
/*      */   public boolean RemoveSeries(int iSeriesIndex)
/*      */   {
/* 2729 */     int iIndex = getSeriesFromIndex(iSeriesIndex);
/*      */     
/* 2731 */     if (iIndex == -1) {
/* 2732 */       return false;
/*      */     }
/* 2734 */     this.m_vectDataSeries.remove(iIndex);
/*      */     
/* 2736 */     return true;
/*      */   }
/*      */   
/*      */   private int getSeriesFromIndex(int iIndex)
/*      */   {
/* 2741 */     for (int i = 0; i < this.m_vectDataSeries.size(); i++)
/*      */     {
/* 2743 */       if (((DataSeries)this.m_vectDataSeries.get(i)).iIndex == iIndex) {
/* 2744 */         return i;
/*      */       }
/*      */     }
/* 2747 */     return -1;
/*      */   }
/*      */   
/*      */   public void RemoveAllSeries()
/*      */   {
/* 2752 */     this.m_vectDataSeries.removeAllElements();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean AddDataPoint(int iSeriesIndex, double dXVal, double dYVal)
/*      */   {
/* 2759 */     int iIndex = getSeriesFromIndex(iSeriesIndex);
/*      */     
/* 2761 */     if (iIndex == -1) {
/* 2762 */       return false;
/*      */     }
/* 2764 */     DataSeries dataSeries = (DataSeries)this.m_vectDataSeries.get(iIndex);
/*      */     
/* 2766 */     if (dataSeries == null) {
/* 2767 */       return false;
/*      */     }
/* 2769 */     DPoint dPoint = new DPoint();
/* 2770 */     dPoint.x = (dXVal * SECONDS);
/* 2771 */     dPoint.y = (dYVal * UNITS);
/*      */     
/* 2773 */     if (dataSeries.vectDataArray.size() == 0)
/*      */     {
/* 2775 */       dataSeries.dXMax = dPoint.x;
/* 2776 */       dataSeries.dXMin = dPoint.x;
/* 2777 */       dataSeries.dYMax = dPoint.y;
/* 2778 */       dataSeries.dYMin = dPoint.y;
/*      */     }
/*      */     else
/*      */     {
/* 2782 */       if (dPoint.x > dataSeries.dXMax)
/* 2783 */         dataSeries.dXMax = dPoint.x;
/* 2784 */       if (dPoint.x < dataSeries.dXMin)
/* 2785 */         dataSeries.dXMin = dPoint.x;
/* 2786 */       if (dPoint.y > dataSeries.dYMax)
/* 2787 */         dataSeries.dYMax = dPoint.y;
/* 2788 */       if (dPoint.y < dataSeries.dYMin) {
/* 2789 */         dataSeries.dYMin = dPoint.y;
/*      */       }
/*      */     }
/* 2792 */     dataSeries.vectDataArray.add(dPoint);
/*      */     
/* 2794 */     return true;
/*      */   }
/*      */   
/*      */   public void addLineMarker(double dTimeInMinutes, String strLabel)
/*      */   {
/* 2799 */     LineMarker lineMarker = new LineMarker();
/*      */     
/* 2801 */     lineMarker.dTime = dTimeInMinutes;
/* 2802 */     lineMarker.strMarkerName = strLabel;
/*      */     
/* 2804 */     this.m_vectLineMarkers.add(lineMarker);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void removeAllLineMarkers()
/*      */   {
/* 2811 */     this.m_vectLineMarkers.removeAllElements();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean AutoScaleToSeries(int iSeriesIndex)
/*      */   {
/* 2818 */     int iIndex = getSeriesFromIndex(iSeriesIndex);
/*      */     
/* 2820 */     if (iIndex == -1) {
/* 2821 */       return false;
/*      */     }
/* 2823 */     DataSeries dataSeries = (DataSeries)this.m_vectDataSeries.get(iIndex);
/*      */     
/* 2825 */     if (dataSeries == null) {
/* 2826 */       return false;
/*      */     }
/* 2828 */     if (dataSeries.vectDataArray.size() < 2) {
/* 2829 */       return false;
/*      */     }
/*      */     
/* 2832 */     this.m_drectView.bottom = 0.0D;
/* 2833 */     this.m_drectView.top = dataSeries.dYMax;
/* 2834 */     this.m_drectView.right = dataSeries.dXMax;
/* 2835 */     this.m_drectView.left = dataSeries.dXMin;
/*      */     
/* 2837 */     return true;
/*      */   }
/*      */   
/*      */   public boolean AutoScaleX()
/*      */   {
/* 2842 */     double dXMin = 0.0D;
/* 2843 */     double dXMax = 0.0D;
/* 2844 */     boolean bUninitialized = true;
/*      */     
/* 2846 */     if (this.m_vectDataSeries.size() == 0) {
/* 2847 */       return false;
/*      */     }
/* 2849 */     DataSeries dataSeries = null;
/*      */     
/* 2851 */     for (int i = 0; i < this.m_vectDataSeries.size(); i++)
/*      */     {
/* 2853 */       dataSeries = (DataSeries)this.m_vectDataSeries.get(i);
/*      */       
/* 2855 */       if ((dataSeries.vectDataArray.size() > 1) && (!dataSeries.bUseSecondScale))
/*      */       {
/*      */ 
/* 2858 */         if ((dataSeries.dXMin < dXMin) || (bUninitialized))
/* 2859 */           dXMin = dataSeries.dXMin;
/* 2860 */         if ((dataSeries.dXMax > dXMax) || (bUninitialized)) {
/* 2861 */           dXMax = dataSeries.dXMax;
/*      */         }
/* 2863 */         bUninitialized = false;
/*      */       }
/*      */     }
/* 2866 */     this.m_drectView.right = dXMax;
/* 2867 */     this.m_drectView.left = dXMin;
/*      */     
/* 2869 */     return true;
/*      */   }
/*      */   
/*      */   public boolean AutoScaleY()
/*      */   {
/* 2874 */     double dYMin = 0.0D;
/* 2875 */     double dYMax = 0.0D;
/* 2876 */     boolean bUninitialized = true;
/*      */     
/* 2878 */     if (this.m_vectDataSeries.size() == 0) {
/* 2879 */       return false;
/*      */     }
/* 2881 */     DataSeries dataSeries = null;
/*      */     
/* 2883 */     for (int i = 0; i < this.m_vectDataSeries.size(); i++)
/*      */     {
/* 2885 */       dataSeries = (DataSeries)this.m_vectDataSeries.get(i);
/*      */       
/* 2887 */       if ((dataSeries.vectDataArray.size() > 1) && (!dataSeries.bUseSecondScale))
/*      */       {
/*      */ 
/* 2890 */         if ((dataSeries.dYMin < dYMin) || (bUninitialized))
/* 2891 */           dYMin = dataSeries.dYMin;
/* 2892 */         if ((dataSeries.dYMax > dYMax) || (bUninitialized)) {
/* 2893 */           dYMax = dataSeries.dYMax;
/*      */         }
/* 2895 */         bUninitialized = false;
/*      */       }
/*      */     }
/* 2898 */     double dAverage = (dYMin + dYMax) / 2.0D;
/* 2899 */     if (dYMax - dYMin < dAverage * 0.001D)
/*      */     {
/* 2901 */       dYMax = dAverage + dAverage * 0.01D;
/* 2902 */       dYMin = dAverage - dAverage * 0.01D;
/*      */     }
/*      */     
/* 2905 */     this.m_drectView.bottom = dYMin;
/* 2906 */     this.m_drectView.top = dYMax;
/*      */     
/* 2908 */     return true;
/*      */   }
/*      */   
/*      */   void selectPanMode()
/*      */   {
/* 2913 */     this.m_iMode = 0;
/* 2914 */     setCursor(this.m_curOpenHand);
/*      */   }
/*      */   
/*      */   void selectZoomInMode()
/*      */   {
/* 2919 */     this.m_iMode = 1;
/* 2920 */     setCursor(this.m_curZoomIn);
/*      */   }
/*      */   
/*      */   void selectZoomOutMode()
/*      */   {
/* 2925 */     this.m_iMode = 2;
/* 2926 */     setCursor(this.m_curZoomOut);
/*      */   }
/*      */   
/*      */   public void setVisibleWindow(double dStartTime, double dEndTime, double dBottom, double dTop)
/*      */   {
/* 2931 */     this.m_drectView.left = (dStartTime * SECONDS);
/* 2932 */     this.m_drectView.top = (dTop * UNITS);
/* 2933 */     this.m_drectView.right = (dEndTime * SECONDS);
/* 2934 */     this.m_drectView.bottom = (dBottom * UNITS);
/*      */   }
/*      */   
/*      */   void setAutoScaleX(boolean bAutoScaleX)
/*      */   {
/* 2939 */     this.m_bAutoScaleX = bAutoScaleX;
/* 2940 */     if (bAutoScaleX)
/*      */     {
/* 2942 */       AutoScaleX();
/*      */     }
/* 2944 */     fireAutoScaleChangedEvent();
/*      */   }
/*      */   
/*      */   void setAutoScaleY(boolean bAutoScaleY)
/*      */   {
/* 2949 */     this.m_bAutoScaleY = bAutoScaleY;
/* 2950 */     if (bAutoScaleY)
/*      */     {
/* 2952 */       AutoScaleY();
/*      */     }
/* 2954 */     fireAutoScaleChangedEvent();
/*      */   }
/*      */   
/*      */   boolean getAutoScaleX()
/*      */   {
/* 2959 */     return this.m_bAutoScaleX;
/*      */   }
/*      */   
/*      */   boolean getAutoScaleY()
/*      */   {
/* 2964 */     return this.m_bAutoScaleY;
/*      */   }
/*      */   
/*      */   public synchronized void addAutoScaleListener(AutoScaleListener l)
/*      */   {
/* 2969 */     this._listeners.add(l);
/*      */   }
/*      */   
/*      */   public synchronized void removeMoodListener(AutoScaleListener l)
/*      */   {
/* 2974 */     this._listeners.remove(l);
/*      */   }
/*      */   
/*      */   private synchronized void fireAutoScaleChangedEvent()
/*      */   {
/* 2979 */     AutoScaleEvent autoScaleEvent = new AutoScaleEvent(this, this.m_bAutoScaleX, this.m_bAutoScaleY);
/* 2980 */     Iterator<AutoScaleListener> listeners = this._listeners.iterator();
/* 2981 */     while (listeners.hasNext())
/*      */     {
/* 2983 */       ((AutoScaleListener)listeners.next()).autoScaleChanged(autoScaleEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setControlsEnabled(boolean bEnabled)
/*      */   {
/* 2989 */     this.m_bControlsEnabled = bEnabled;
/*      */     
/* 2991 */     if (!bEnabled) {
/* 2992 */       setCursor(Cursor.getDefaultCursor());
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteBuffer getPixels() {
/* 2997 */     this.m_bCopyImage = true;
/*      */     
/* 2999 */     paint(getGraphics());
/*      */     
/* 3001 */     return this.m_ByteBuffer;
/*      */   }
/*      */   
/*      */   public void dispose(GLAutoDrawable arg0) {}
/*      */   
/*      */   public class DPoint
/*      */   {
/*      */     double x;
/*      */     double y;
/*      */     
/*      */     public DPoint() {}
/*      */   }
/*      */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/GraphControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */