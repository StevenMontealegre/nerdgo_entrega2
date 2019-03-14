package nerdgo.practico1;

import java.util.ArrayList;

public class OperationsActivity {

    double numero1, numero2;
    ArrayList<String> operadores;

    public OperationsActivity() {
        operadores = new ArrayList<>();
        operadores.add("+");
        operadores.add("-");
        operadores.add("/");
        operadores.add("*");
    }

    public String generarOperaciones() {
        String operaciones = "";
        int operador = (int) (Math.random() * 3);
        double d1 = (Math.random() * 10000) + 1;
        double d2 = (Math.random() * 10000) + 1;
        operaciones = d1 + "," + operadores.get(operador) + "," + d2;
        return operaciones;
    }

    public boolean validarOperaciones(double result) {
        boolean val = false;
        String operacion = generarOperaciones();
        double resultado = 0.0;
        String[] parts = operacion.split(",");
        double op1 = Double.parseDouble(parts[0]);
        String op2 = parts[1];
        double op3 = Double.parseDouble(parts[2]);
        if (op2 == "+") {
            resultado = op1 + op3;
        } else if (op2 == "-") {
            resultado = op1 - op3;
        } else if (op2 == "*") {
            resultado = op1 * op3;
        } else {
            resultado = op1 / op3;
        }

        if (resultado == result)
            val = true;

        return val;
    }
}
