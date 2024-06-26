package com.medison.pacs.image.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Table(name = "IMAGETAB", schema = "PACSPLUS")
@Entity
@IdClass(ImageId.class)
public class Image {
    @Id
    private long studykey;

    @Id
    private long serieskey;

    @Id
    private long imagekey;

    private String path;
    private String fname;

    private String studyinsuid;
    private String seriesinsuid;
    private String sopinstanceuid;
    private String sopclassuid;
    private Long seriesnumber;
    private String instancenum;
    private Long curseqnum;
    private Long window;
    private Long lev;
    private String contentdate;
    private String contenttime;
    private String acqdate;
    private String acqtime;
    private String studyid;
    private String viewposition;
    private String laterality;
    private String imagetype;
    private String fmxdata;
    private String imagecomments;
    private String additionaldesc;
    private String imageorientation;
    private String imageposition;
    private String pixelspacing;
    private Long pixelrows;
    private Long pixelcolumns;
    private Long bitsallocated;
    private String specificcharacterset;
    private String transfersyntaxuid;
    private String sourceapplicationentitytitle;
    private String lossyimagecompression;
    private Long samplesperpixel;
    private String photometricinterpretation;
    private Long bitsstored;
    private Long highbit;
    private Long pixelrepresentation;
    private Long planarconfiguration;
    private Long framecnt;
    private Long geomstatus;
    private Long archstatus;
    private String archpath;
    private Long delflag;
    private Long verifyflag;
    private Long hideflag;
    private Long keyflag;
    private Long compstatus;
    private String presentationstatedata;
    private Long sharpenvalue;
    private String lutdata;
    private Long imagesize;
    private Long compsize;
    private String movpath;
    private String movfname;
    private Long movieflag;
    private String codectype;
    private Float framerate;
    private Float frametime;
    private String recstartdate;
    private String recstarttime;
    private String recenddate;
    private String recendtime;
    private Long movststorageid;
    private Long ltstorageid;
    private Long ststorageid;
    private Long webstorageid;
    private String insertdate;
    private String inserttime;
    private String inserted;
    private String updated;
    private Long hospitalid;
    private Long reserved1;
    private Long reserved2;
    private Long reserved3;
    private String reserved4;
    private String reserved5;
    private String reserved6;
    private String reserved7;
    private String reserved8;
    private String reserved9;
    private String reserved10;
    private Long photometric;
    private String patientorientation;
    private String presentationlutshape;
    private String instancecreationdate;
    private String instancecreationtime;
    private String sourceaetitle;
    private Double ai_score;
    private Long ai_finding_count;
    private String reportstatus;
}
