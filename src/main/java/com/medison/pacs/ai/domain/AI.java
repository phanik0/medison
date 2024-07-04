package com.medison.pacs.ai.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "PRESENTATION_STATE", schema = "PACSPLUS")
public class AI {
    @Id
    private String prId;
    private Long studyKey;
    private Long seriesKey;
    private Long imageKey;
    private String seriesInstanceUid;
    private String sopClassUid;
    private String sopInstanceUid;
    private Long prSeriesKey;
    private Long prNonImageKey;
    private String prModule;
    private String prGraphicLayer;
    private Long prGraphicLayerOrder;
    private String prRecommendedGrayscale;
    private String prRecommendedColor;
    private String prGraphicLayerDescription;

    @Lob
    private String prContent;
    private Boolean delFlag;
    private String inserted;
    private String updated;

    @Transient
    private PrContent dicomContent;
    @Transient
    private List<PrContent.TextObjectSequence> textObjectSequences;
    @Transient
    private List<PrContent.GraphicObjectSequence> graphicObjectSequences;

}




