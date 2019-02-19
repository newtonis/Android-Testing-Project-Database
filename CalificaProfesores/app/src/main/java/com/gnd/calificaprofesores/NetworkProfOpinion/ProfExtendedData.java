package com.gnd.calificaprofesores.NetworkProfOpinion;

/** Contiene ademas de la informacion basica de un profesor
 *  su calificación
 */
public class ProfExtendedData {
    Long id;
    String name;
    Float conocimiento , amabildiad, clases;

    public ProfExtendedData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(Float conocimiento) {
        this.conocimiento = conocimiento;
    }

    public Float getAmabildiad() {
        return amabildiad;
    }

    public void setAmabildiad(Float amabildiad) {
        this.amabildiad = amabildiad;
    }

    public Float getClases() {
        return clases;
    }

    public void setClases(Float clases) {
        this.clases = clases;
    }
}
