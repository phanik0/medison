package com.medison.pacs.ai.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class PrContent {
    @Getter
    @Setter
    @ToString
    public class TextObjectSequence {
        private String boundingBoxAnnotationUnits;
        private String anchorPointAnnotationUnits;
        private String unformattedTextValue;
        private Object textStyleSequence;
        private BoundingBox boundingBoxTopLeftHandCorner;
        private BoundingBox boundingBoxBottomRightHandCorner;
        private String boundingBoxTextHorizontalJustification;
        private Object anchorPoint;
        private Object anchorPointVisibility;
        private Object compoundGraphicInstanceId;
        private Object graphicGroupId;
        private Object trackingId;
        private Object trackingUid;
    }

    @Getter
    @Setter
    @ToString
    public class GraphicObjectSequence {
        private String graphicAnnotationUnits;
        private int graphicDimensions;
        private int numberOfGraphicPoints;
        private List<GraphicData> graphicData;
        private String graphicType;
        private List<LineStyleSequence> lineStyleSequence;
        private String graphicFilled;
        private Object fillStyleSequence;
        private Object compoundGraphicInstanceId;
        private Object graphicGroupId;
        private Object trackingId;
        private Object trackingUid;
        private Object compoundGraphicSequence;
    }

    @Getter
    @Setter
    @ToString
    public class BoundingBox {
        private double column;
        private double row;
    }

    @Getter
    @Setter
    @ToString
    public class GraphicData {
        private double column;
        private double row;
    }

    @Getter
    @Setter
    @ToString
    public class LineStyleSequence {
        private CIELabValue patternOnColorCIELabValue;
        private Object patternOffColorCIELabValue;
        private Object patternOnOpacity;
        private Object patternOffOpacity;
        private int lineThickness;
        private Object lineDashingStyle;
        private Object linePattern;
        private Object shadowStyle;
        private Object shadowOffsetX;
        private Object shadowOffsetY;
        private Object shadowColorCIELabValue;
        private Object shadowOpacity;
    }

    @Getter
    @Setter
    @ToString
    public class CIELabValue {
        private int L;
        private int a;
        private int b;
    }
}
