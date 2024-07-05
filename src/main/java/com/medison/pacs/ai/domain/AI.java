package com.medison.pacs.ai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @JsonIgnore
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

    @PrePersist
    public void prePersist() {
        this.prContent = convertPrContentToString(this.dicomContent);

    }


    @Transient
    private PrContent prContentData;

    @PostLoad
    public void postLoad() {
        this.prContentData = convertStringToPrContent(this.prContent);
        if (this.prContentData != null) {
            this.textObjectSequences = prContentData.getTextObjectSequence();
            this.graphicObjectSequences = prContentData.getGraphicObjectSequence();
        }
    }

    private String convertPrContentToString(PrContent prContent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(prContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrContent convertStringToPrContent(String prContent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(prContent, PrContent.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}




