package festival.client.gui;

public class TableRowData {

    private String coloana1;
    private String coloana2;
    private String coloana3;
    private String coloana4;
    private String coloana5;

    public TableRowData(String coloana1, String coloana2, String coloana3, String coloana4, String coloana5) {
        this.coloana1 = coloana1;
        this.coloana2 = coloana2;
        this.coloana3 = coloana3;
        this.coloana4 = coloana4;
        this.coloana5 = coloana5;
    }

    public String getColoana1() {
        return coloana1;
    }

    public String getColoana2() {
        return coloana2;
    }

    public String getColoana3() {
        return coloana3;
    }

    public String getColoana4() {
        return coloana4;
    }

    public String getColoana5() {
        return coloana5;
    }
}
