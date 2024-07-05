
package com.medison.pacs.ai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PrContent {
    @JsonProperty("TextObjectSequence")
    private List<TextObjectSequence> textObjectSequence;

    @JsonProperty("GraphicObjectSequence")
    private List<GraphicObjectSequence> graphicObjectSequence;

    @JsonProperty("DisplayedAreaTopLeftHandCorner")
    private BoundingBox displayedAreaTopLeftHandCorner;

    @JsonProperty("DisplayedAreaBottomRightHandCorner")
    private BoundingBox displayedAreaBottomRightHandCorner;

    @JsonProperty("PixelOriginInterpretation")
    private String pixelOriginInterpretation;

    @JsonProperty("PresentationSizeMode")
    private String presentationSizeMode;

    @JsonProperty("PresentationPixelSpacing")
    private String presentationPixelSpacing;

    @JsonProperty("PresentationPixelAspectRatio")
    private String presentationPixelAspectRatio;

    @JsonProperty("PresentationPixelMagnificationRatio")
    private String presentationPixelMagnificationRatio;

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TextObjectSequence {
        @JsonProperty("BoundingBoxAnnotationUnits")
        private String boundingBoxAnnotationUnits;

        @JsonProperty("AnchorPointAnnotationUnits")
        private String anchorPointAnnotationUnits;

        @JsonProperty("UnformattedTextValue")
        private String unformattedTextValue;

        @JsonProperty("TextStyleSequence")
        private Object textStyleSequence;

        @JsonProperty("BoundingBoxTopLeftHandCorner")
        private BoundingBox boundingBoxTopLeftHandCorner;

        @JsonProperty("BoundingBoxBottomRightHandCorner")
        private BoundingBox boundingBoxBottomRightHandCorner;

        @JsonProperty("BoundingBoxTextHorizontalJustification")
        private String boundingBoxTextHorizontalJustification;

        @JsonProperty("AnchorPoint")
        private Object anchorPoint;

        @JsonProperty("AnchorPointVisibility")
        private Object anchorPointVisibility;

        @JsonProperty("CompoundGraphicInstanceID")
        private Object compoundGraphicInstanceId;

        @JsonProperty("GraphicGroupID")
        private Object graphicGroupId;

        @JsonProperty("TrackingID")
        private Object trackingId;

        @JsonProperty("TrackingUID")
        private Object trackingUid;
    }

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphicObjectSequence {
        @JsonProperty("GraphicAnnotationUnits")
        private String graphicAnnotationUnits;

        @JsonProperty("GraphicDimensions")
        private int graphicDimensions;

        @JsonProperty("NumberOfGraphicPoints")
        private int numberOfGraphicPoints;

        @JsonProperty("GraphicData")
        private List<GraphicData> graphicData;

        @JsonProperty("GraphicType")
        private String graphicType;

        @JsonProperty("LineStyleSequence")
        private List<LineStyleSequence> lineStyleSequence;

        @JsonProperty("GraphicFilled")
        private String graphicFilled;

        @JsonProperty("FillStyleSequence")
        private Object fillStyleSequence;

        @JsonProperty("CompoundGraphicInstanceID")
        private Object compoundGraphicInstanceId;

        @JsonProperty("GraphicGroupID")
        private Object graphicGroupId;

        @JsonProperty("TrackingID")
        private Object trackingId;

        @JsonProperty("TrackingUID")
        private Object trackingUid;

        @JsonProperty("CompoundGraphicSequence")
        private Object compoundGraphicSequence;
    }

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BoundingBox {
        @JsonProperty("Column")
        private double column;

        @JsonProperty("Row")
        private double row;
    }

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphicData {
        @JsonProperty("Column")
        private double column;

        @JsonProperty("Row")
        private double row;
    }

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LineStyleSequence {
        @JsonProperty("PatternOnColorCIELabValue")
        private CIELabValue patternOnColorCIELabValue;

        @JsonProperty("PatternOffColorCIELabValue")
        private Object patternOffColorCIELabValue;

        @JsonProperty("PatternOnOpacity")
        private Object patternOnOpacity;

        @JsonProperty("PatternOffOpacity")
        private Object patternOffOpacity;

        @JsonProperty("LineThickness")
        private int lineThickness;

        @JsonProperty("LineDashingStyle")
        private Object lineDashingStyle;

        @JsonProperty("LinePattern")
        private Object linePattern;

        @JsonProperty("ShadowStyle")
        private Object shadowStyle;

        @JsonProperty("ShadowOffsetX")
        private Object shadowOffsetX;

        @JsonProperty("ShadowOffsetY")
        private Object shadowOffsetY;

        @JsonProperty("ShadowColorCIELabValue")
        private Object shadowColorCIELabValue;

        @JsonProperty("ShadowOpacity")
        private Object shadowOpacity;
    }

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CIELabValue {
        @JsonProperty("L")
        private int l;

        @JsonProperty("a")
        private int a;

        @JsonProperty("b")
        private int b;
    }
}
