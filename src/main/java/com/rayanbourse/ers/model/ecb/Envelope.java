package com.rayanbourse.ers.model.ecb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Envelope {
    @XmlElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
    private Cube cube;

}
