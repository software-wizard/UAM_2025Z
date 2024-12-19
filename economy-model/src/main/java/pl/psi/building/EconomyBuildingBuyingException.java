package pl.psi.building;

public class EconomyBuildingBuyingException extends RuntimeException {

    EconomyBuildingBuyingException(String message, Throwable cause) {
        super(message, cause);
    }

    EconomyBuildingBuyingException(String message) {
        super(message);
    }
}