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
private int NumberOfGraphicPoints;


    public List<ProcessedPrData> convertToProcessedPrData(PrContent prContent) {
        List<ProcessedPrData> processedPrDataList = new ArrayList<>();

        if (prContent.getGraphicObjectSequence() != null) {
            for (PrContent.GraphicObjectSequence graphicObject : prContent.getGraphicObjectSequence()) {
                ProcessedPrData processedPrData = getProcessedPrData(graphicObject);

                processedPrDataList.add(processedPrData);
            }
        }

        return processedPrDataList;
    }

    private static ProcessedPrData getProcessedPrData(PrContent.GraphicObjectSequence graphicObject) {
        ProcessedPrData processedPrData = new ProcessedPrData();
        processedPrData.setCoordinate(graphicObject.getGraphicData());
        processedPrData.setGraphicType(graphicObject.getGraphicType());
        processedPrData.setColor(graphicObject.getLineStyleSequence() != null && !graphicObject.getLineStyleSequence().isEmpty() ?
                graphicObject.getLineStyleSequence().get(0).getPatternOnColorCIELabValue() : null);
        processedPrData.setNumberOfGraphicPoints(graphicObject.getNumberOfGraphicPoints());
        return processedPrData;
    }
}
