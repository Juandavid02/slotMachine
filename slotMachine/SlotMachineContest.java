import java.util.ArrayList;
import java.util.List;

/**
 * Resuelve el Problem I (Slot Machine) del ICPC World Finals 2025
 * usando SlotMachine unicamente como testing tool.
 *
 * La unica informacion que se usa de la maquina es distinctSymbols(), es decir
 * cuantos simbolos diferentes se ven en este momento, nunca cual es cada uno.
 *
 * @author Juan David Rojas and Cesar Morales
 * @version 2.1 (September 2026)
 */
public class SlotMachineContest
{
    /**
     * Resuelve una maquina de n ruedas sin mostrarla en pantalla.
     *
     * @param n el numero de ruedas (y de simbolos) de la maquina
     * @return la secuencia de movimientos {rueda, pasos} aplicados
     */
    public int[][] solve(int n){
        SlotMachine maquina = new SlotMachine(n);
        return resolver(maquina);
    }

    /**
     * Resuelve una maquina de n ruedas mostrando el proceso en pantalla.
     *
     * @param n el numero de ruedas (y de simbolos) de la maquina
     */
    public void simulate(int n){
        SlotMachine maquina = new SlotMachine(n);
        maquina.makeVisible();
        resolver(maquina);
    }

    /**
     * Lleva la maquina recibida hasta el jackpot y devuelve los movimientos
     * usados. El procedimiento tiene tres fases:
     *
     * Fase 1: dejar las n ruedas mostrando n simbolos distintos.
     * Fase 2: descubrir la permutacion usando la rueda 1 como sonda.
     * Fase 3: alinear todas las ruedas al simbolo de la rueda 1.
     *
     * @param maquina la maquina tragamonedas que se desea resolver
     * @return un arreglo con los movimientos {rueda, pasos} en el orden aplicado
     */
    private int[][] resolver(SlotMachine maquina){
        List<int[]> acciones = new ArrayList<int[]>();
        // El constructor SlotMachine(int) recorta n al rango [1, 8], por eso se
        // consulta el tamano real de la maquina y no el parametro recibido.
        int n = maquina.symbols().length;
        if (n < 2){
            return new int[0][0];
        }

        // ---------- Fase 1: dejar todas las ruedas con simbolos distintos ----------
        // Al rotar una rueda con las demas fijas, el conteo es |S|+1 si su simbolo
        // no esta entre los de las otras ruedas y |S| si si lo esta. Por eso,
        // quedarse en el maximo equivale a llevarla a un simbolo que ninguna otra
        // rueda tiene, y ese simbolo siempre existe porque hay n simbolos y solo
        // n-1 ruedas ajenas. Una rueda ya colocada nunca se rompe despues, asi que
        // basta una pasada.
        for (int t = 2; t <= n; t++){
            int mejorValor = maquina.distinctSymbols();
            int mejorPaso = 0;
            for (int p = 1; p <= n; p++){
                maquina.spin(t, 1);
                acciones.add(new int[]{t, 1});
                int actual = maquina.distinctSymbols();
                if (actual > mejorValor){
                    mejorValor = actual;
                    mejorPaso = p;
                }
            }
            // Tras n pasos de a 1 la rueda volvio a su posicion inicial, por lo
            // que aplicar mejorPaso la deja en la mejor posicion encontrada.
            if (mejorPaso != 0){
                maquina.spin(t, mejorPaso);
                acciones.add(new int[]{t, mejorPaso});
            }
        }

        // ---------- Fase 2: descubrir la permutacion usando la rueda 1 como sonda ----------
        // posicion[w] guarda donde esta la rueda w medida como desplazamiento
        // respecto del simbolo con el que la rueda 1 empezo esta fase.
        int[] posicion = new int[n + 1];
        boolean[] identificada = new boolean[n + 1];
        identificada[1] = true;
        int offsetRueda1 = 0;

        for (int s = 1; s <= n - 1; s++){
            // La rueda 1 avanza de s-1 a s: deja libre el simbolo s-1 y queda
            // duplicada con la unica rueda que muestra el simbolo s.
            maquina.spin(1, 1);
            acciones.add(new int[]{1, 1});
            offsetRueda1 = s;
            posicion[1] = s;

            for (int w = 2; w <= n; w++){
                if (identificada[w]){
                    continue;
                }
                maquina.spin(w, -1);
                acciones.add(new int[]{w, -1});
                if (maquina.distinctSymbols() == n){
                    // Solo la rueda que mostraba el simbolo s puede caer en el
                    // simbolo libre s-1; cualquier otra aterriza sobre uno ya
                    // ocupado y baja el conteo. El movimiento NO se deshace: al
                    // dejarla ahi la configuracion vuelve a ser una permutacion
                    // y el mismo razonamiento sirve en el paso siguiente.
                    posicion[w] = s - 1;
                    identificada[w] = true;
                    break;
                }
                // No era: se devuelve la rueda a donde estaba.
                maquina.spin(w, 1);
                acciones.add(new int[]{w, 1});
            }
        }

        // ---------- Fase 3: alinear todas las ruedas al simbolo de la rueda 1 ----------
        for (int w = 2; w <= n; w++){
            int pasos = (((offsetRueda1 - posicion[w]) % n) + n) % n;
            if (pasos != 0){
                maquina.spin(w, pasos);
                acciones.add(new int[]{w, pasos});
            }
        }
        return acciones.toArray(new int[acciones.size()][]);
    }
}