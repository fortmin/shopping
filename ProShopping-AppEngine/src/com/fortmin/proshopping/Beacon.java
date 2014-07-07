package com.fortmin.proshopping;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name="TIPO", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("BEACON")
public class Beacon extends ElementoRF {

	private String uuid;
	private int major;
	private int minor;
	private int rssi;

	public Beacon(String id) {
		super(id);
	}
	
	public Beacon(String id, String ubicacion) {
		super(id, ubicacion);
	}

	public Beacon(String id, String ubicacion, String uuid, int major, int minor) {
		super(id, ubicacion);
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
	}

	public Beacon(String id, String ubicacion, String uuid, int major, int minor, int rssi) {
		super(id, ubicacion);
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.setRssi(rssi);
	}

	@Override
	public String getTipo() {
		return "BEACON";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	
}
