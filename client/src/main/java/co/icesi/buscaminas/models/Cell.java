package co.icesi.buscaminas.models;

public class Cell {
    private boolean isLandMine;
    private int value;
    private boolean hide;
    private boolean isMarked;
    private boolean showAll;

    public Cell() {
        this.isLandMine = false;
        this.value = 0;
        this.hide = true;
        this.isMarked = false;
        this.showAll = false;
    }

    public Cell(boolean isLandMine, int value) {
        this.isLandMine = isLandMine;
        this.value = value;
        this.hide = true;
        this.isMarked = false;
        this.showAll = false;
    }

    public boolean isLandMine() { return isLandMine; }
    public void setLandMine(boolean landMine) { isLandMine = landMine; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public boolean isHide() { return hide; }
    public void setHide(boolean hide) { this.hide = hide; }

    public boolean isMarked() { return isMarked; }
    public void setMarked(boolean marked) { isMarked = marked; }

    public boolean isShowAll() { return showAll; }
    public void setShowAll(boolean showAll) { this.showAll = showAll; }

    @Override
    public String toString() {
        if (isMarked) return "M";
        if (hide && !showAll) return ".";
        if (isLandMine) return "*";
        if (value == 0) return " ";
        return String.valueOf(value);
    }
}
