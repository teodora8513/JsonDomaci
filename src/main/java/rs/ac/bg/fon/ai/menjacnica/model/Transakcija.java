package rs.ac.bg.fon.ai.menjacnica.model;

import java.math.BigDecimal;

import java.util.Date;

public class Transakcija {

	private String izvornaValuta;
	private String krajnjaValuta;
	private BigDecimal pocetniIznos;
	private BigDecimal konvertovaniIznos;
	private Date datum;

	public Transakcija() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transakcija(String izvornaValuta, String krajnjaValuta, BigDecimal pocetniIznos,
			BigDecimal konvertovaniIznos, Date datum) {
		super();
		this.izvornaValuta = izvornaValuta;
		this.krajnjaValuta = krajnjaValuta;
		this.pocetniIznos = pocetniIznos;
		this.konvertovaniIznos = konvertovaniIznos;
		this.datum = datum;
	}

	public String getIzvornaValuta() {
		return izvornaValuta;
	}

	public void setIzvornaValuta(String izvornaValuta) {
		this.izvornaValuta = izvornaValuta;
	}

	public String getKrajnjaValuta() {
		return krajnjaValuta;
	}

	public void setKrajnjaValuta(String krajnjaValuta) {
		this.krajnjaValuta = krajnjaValuta;
	}

	public BigDecimal getPocetniIznos() {
		return pocetniIznos;
	}

	public void setPocetniIznos(BigDecimal pocetniIznos) {
		this.pocetniIznos = pocetniIznos;
	}

	public BigDecimal getKonvertovaniIznos() {
		return konvertovaniIznos;
	}

	public void setKonvertovaniIznos(BigDecimal konvertovaniIznos) {
		this.konvertovaniIznos = konvertovaniIznos;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Transakcija [izvornaValuta=" + izvornaValuta + ", krajnjaValuta=" + krajnjaValuta + ", pocetniIznos="
				+ pocetniIznos + ", konvertovaniIznos=" + konvertovaniIznos + ", datum=" + datum + "]";
	}

}
