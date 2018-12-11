package com.example.daniel.integradormovil.model;

public class Inversion {

    private String gananciatotal;
    private String isr;
    private String interesbonddia;
    private String interesbruto;
    private String inversionbonddia;
    private String inversioncetes;
    private Long monto;
    private String periodo;
    private String remanente;
    private String tasabonddia;
    private String tasacetes;
    private Long titbonddia;
    private Long titcetes;




    public Inversion(){

    }




    public Inversion (String gananciatotal, String isr, String interesbonddia, String interesbruto, String inversionbonddia, String inversioncetes, Long monto,
                      String periodo, String remanente, String tasabonddia, String tasacetes, Long titbonddia, Long titcetes) {
        this.gananciatotal = gananciatotal;
        this.isr=isr;
        this.interesbonddia=interesbonddia;
        this.interesbruto=interesbruto;
        this.inversionbonddia=inversionbonddia;
        this.inversioncetes=inversioncetes;
        this.monto=monto;
        this.periodo=periodo;
        this.remanente=remanente;
        this.tasabonddia=tasabonddia;
        this.tasacetes=tasacetes;
        this.titbonddia=titbonddia;
        this.titcetes=titcetes;
    }


    public String getGananciatotal() {
        return gananciatotal;
    }

    public void setGananciatotal(String gananciatotal) {
        this.gananciatotal = gananciatotal;
    }

    public String getIsr() {
        return isr;
    }

    public void setIsr(String isr) {
        this.isr = isr;
    }

    public String getInteresbonddia() {
        return interesbonddia;
    }

    public void setInteresbonddia(String interesbonddia) {
        this.interesbonddia = interesbonddia;
    }

    public String getInteresbruto() {
        return interesbruto;
    }

    public void setInteresbruto(String interesbruto) {
        this.interesbruto = interesbruto;
    }

    public String getInversionbonddia() {
        return inversionbonddia;
    }

    public void setInversionbonddia(String inversionbonddia) {
        this.inversionbonddia = inversionbonddia;
    }

    public String getInversioncetes() {
        return inversioncetes;
    }

    public void setInversioncetes(String inversioncetes) {
        this.inversioncetes = inversioncetes;
    }

    public String getTasabonddia() {
        return tasabonddia;
    }

    public void setTasabonddia(String tasabonddia) {
        this.tasabonddia = tasabonddia;
    }

    public String getTasacetes() {
        return tasacetes;
    }

    public void setTasacetes(String tasacetes) {
        this.tasacetes = tasacetes;
    }

    public Long getTitbonddia() {
        return titbonddia;
    }

    public void setTitbonddia(Long titbonddia) {
        this.titbonddia = titbonddia;
    }

    public Long getTitcetes() {
        return titcetes;
    }

    public void setTitcetes(Long titcetes) {
        this.titcetes = titcetes;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getRemanente() {
        return remanente;
    }

    public void setRemanente(String remanente) {
        this.remanente = remanente;
    }


    @Override
    public String toString() {
        return "Inversiones{" +
                "gananciatotal='" + gananciatotal + '\'' +
                ", isr='" + isr + '\'' +
                ", interesbonddia='" + interesbonddia + '\'' +
                ", interesbruto='" + interesbruto+ '\'' +
                ", inversionbonddia='" + inversionbonddia + '\'' +
                ", inversioncetes'" + inversioncetes + '\'' +
                ", tasabonddia='" + tasabonddia + '\'' +
                ", tasacetes='" + tasacetes + '\'' +
                ", titbonddia='" + titbonddia + '\'' +
                ", titcetes ='" + titcetes + '\'' +
                ", monto='" + monto + '\'' +
                ", periodo='" + periodo + '\'' +
                ", remanente='" + remanente + '\'' +
                '}';
    }


}
