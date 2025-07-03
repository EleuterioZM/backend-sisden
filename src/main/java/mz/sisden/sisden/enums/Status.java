package mz.sisden.sisden.enums;

public enum Status {
    OPEN("Aberto"),
    IN_PROGRESS("Em progresso"),
    RESOLVED("Resolvido");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
