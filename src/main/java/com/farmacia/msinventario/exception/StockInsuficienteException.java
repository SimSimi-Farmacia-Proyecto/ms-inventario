package com.farmacia.msinventario.exception;
public class StockInsuficienteException
        extends RuntimeException {

    public StockInsuficienteException(
            String mensaje) {

        super(mensaje);
    }
}
