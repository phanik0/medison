package com.medison.pacs.ai.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProcessedPrData {
//    GraphicData: 그래픽 객체의 좌표 데이터
//    GraphicType: 그래픽 객체의 타입 (예: POLYLINE)
//    LineStyleSequence: 그래픽 객체의 스타일 정보 (예: 색상, 두께 등)

    private List<PrContent.GraphicData> coordinate;//클래스 하나가 하나의 점
    private String graphicType; // POLYLINE: 여러 점으로 정의된 연결된 선분들입니다. 주로 경로나 윤곽선을 나타내는 데 사용.
    // ELLIPSE: 타원형으로, 경계 상자나 중심, 반장축, 반단축 등의 매개변수로 정의.
    private PrContent.CIELabValue color;
    private int numberOfGraphicPoints;

    public ProcessedPrData convertToProcessedPrData(PrContent prContent) {
        ProcessedPrData processedPrData = new ProcessedPrData();

        List<PrContent.GraphicObjectSequence> graphicObjectSequences = prContent.getGraphicObjectSequence();
        if (graphicObjectSequences != null && !graphicObjectSequences.isEmpty()) {
            PrContent.GraphicObjectSequence graphicObject = graphicObjectSequences.get(0);

            processedPrData.setCoordinate(graphicObject.getGraphicData());
            processedPrData.setGraphicType(graphicObject.getGraphicType());

            if (graphicObject.getLineStyleSequence() != null && !graphicObject.getLineStyleSequence().isEmpty()) {
                processedPrData.setColor(graphicObject.getLineStyleSequence().get(0).getPatternOnColorCIELabValue());
            }

            processedPrData.setNumberOfGraphicPoints(graphicObject.getNumberOfGraphicPoints());

        }

        return processedPrData;
    }
}
