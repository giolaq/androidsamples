package de.vogella.android.asynctask;

/**
 * Created by joaobiriba on 10/10/14.
 */
public class Attivita {

    private  String url;
    private  String label;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Attività " + this.url + " " + this.label;
    }
}
