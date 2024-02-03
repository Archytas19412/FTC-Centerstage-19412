/*package org.firstinspires.ftc.teamcode.auto.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
public class BluePipeline extends OpenCvPipeline {
    public enum PropPosition
    {
        LEFT,
        CENTER,
        RIGHT
    }
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);

    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0,200);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(225,175);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(540,200);
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0,150);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(225,125);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(540,150);
    static final int REGION_WIDTH = 100;
    static final int REGION_HEIGHT = 75;

    @@ -229,5 +229,9 @@ public void onViewportTapped() {
        // This method is executed from the UI thread, so be careful to not
        // perform any sort heavy processing here! Your app might hang otherwise
    }
    public PropPosition returnPos()
    {
        return position;
    }

}
  15 changes: 6 additions & 9 deletions15
          TeamCode/src/main/java/org/firstinspires/ftc/teamcode/auto/pipelines/rPropPL.java
@@ -1,14 +1,13 @@
        package org.firstinspires.ftc.teamcode.auto.pipelines;
        import org.opencv.core.Core;
        import org.opencv.core.Mat;
        import org.opencv.core.Point;
        import org.opencv.core.Rect;
        import org.opencv.core.Scalar;
        import org.opencv.imgproc.Imgproc;
        import org.openftc.easyopencv.OpenCvPipeline;


public class rPropPL extends OpenCvPipeline {

    public enum PropPosition
    @@ -21,9 +20,9 @@ public enum PropPosition
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);

    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0,200);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(225,175);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(540,200);
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0,150);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(225,125);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(540,150);
    static final int REGION_WIDTH = 100;
    static final int REGION_HEIGHT = 75;

    @@ -223,11 +222,9 @@ else if(max == avg3) // Was it from region 3?
            return input;
}

    @Override
    public void onViewportTapped() {
        // Executed when the image display is clicked by the mouse or tapped
        // This method is executed from the UI thread, so be careful to not
        // perform any sort heavy processing here! Your app might hang otherwise
        public PropPosition returnPos()
        {
            return position;
        }

    }*/