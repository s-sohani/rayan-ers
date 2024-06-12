package com.rayanbourse.ers.model.ecb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CubeTime {

    @XmlAttribute
    private String time;

    @XmlElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
    private List<CubeData> cubeData;

}
