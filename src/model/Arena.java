package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ludwi on 2016-12-11.
 */
public class Arena {
    private final StringProperty namn;
    private final StringProperty stad;
    private final IntegerProperty kapacitet;

    public Arena(String namn, String stad, int kap){
        this.namn = new SimpleStringProperty(namn);

        this.stad = new SimpleStringProperty(stad);
        this.kapacitet = new SimpleIntegerProperty(kap);
    }
    public Arena(){
        this(null,null,0);
    }

    public String getNamn(){
        return namn.get();
    }
    public String getStad(){
        return stad.get();
    }
    public int getKapacitet(){
        return kapacitet.get();
    }

    public void setNamn(String namn) {
        this.namn.set(namn);
    }
    public void setStad(String stad) {
        this.stad.set(stad);
    }
    public void setKapacitet(int kap) {
        this.kapacitet.set(kap);
    }

    public StringProperty namnProperty() {
        return namn;
    }
    public StringProperty stadProperty() {
        return stad;
    }
    public IntegerProperty kapacitetProperty() {
        return kapacitet;
    }
}
