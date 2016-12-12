package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ludwi on 2016-12-11.
 */
public class Turnering {
    private final StringProperty namn;
    private final StringProperty fran;
    private final StringProperty till;

    public Turnering(String namn, String fran, String till){
        this.namn = new SimpleStringProperty(namn);

        this.fran = new SimpleStringProperty(fran);
        this.till = new SimpleStringProperty(till);
    }

    public String getNamn(){
        return namn.get();
    }
    public String getFran(){
        return fran.get();
    }
    public String getTill(){
        return till.get();
    }

    public void setNamn(String namn) {
        this.namn.set(namn);
    }
    public void setFran(String fran) {
        this.fran.set(fran);
    }
    public void setTill(String till) {
        this.till.set(till);
    }

    public StringProperty namnProperty() {
        return namn;
    }
    public StringProperty franProperty() {
        return fran;
    }
    public StringProperty tillProperty() {
        return till;
    }
}
