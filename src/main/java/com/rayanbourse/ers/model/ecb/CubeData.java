package com.rayanbourse.ers.model.ecb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CubeData {
    @XmlAttribute
    private String currency;

    @XmlAttribute
    private double rate;


}
