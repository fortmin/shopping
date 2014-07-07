package com.fortmin.proshopping;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name="TIPO", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("NFCTAG")
public class Tag extends ElementoRF {

	private String tipConten;

	public Tag(String id) {
		super(id);
	}
	
	public Tag(String id, String ubicacion) {
		super(id, ubicacion);
	}

	public Tag(String id, String ubicacion, String tipConten) {
		super(id, ubicacion);
		this.tipConten = tipConten;
	}

	@Override
	public String getTipo() {
		return "NFCTAG";
	}

	public String getTipConten() {
		return tipConten;
	}

	public void setTipConten(String tipConten) {
		this.tipConten = tipConten;
	}
	
	
}
