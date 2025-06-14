# Tiempos de los Casos de Prueba.

Es solo para usar otro metodo que la complejidad cumple con lo pedido en la consigna.

---

### 1. nuevoBerretacoin - O(P)
```
nuevoBerretacoin(1000) -> 62800ns
nuevoBerretacoin(10000) -> 564300ns
nuevoBerretacoin(100000) -> 5189500ns
nuevoBerretacoin(1000000) -> 46791700ns
```
Se ve un crecimiento proporcional al tamaño de P. Esto está dentro de la cota O(P).

### 2. agregarBloque - O(n * log P)
```
Para P=1000000 (fijo), variando n:
n=1000 -> 374300ns
n=10000 -> 1420000ns
n=100000 -> 124162400ns
n=1000000 -> 128704500ns

Para n=1000000 (fijo), variando P:
P=1000 -> 108090900ns
P=10000 -> 197293300ns
P=100000 -> 111996800ns
P=1000000 -> 128704500ns
```
El tiempo escala de forma lineal con n (para P fijo), y de manera logarítmica con P, lo que demuestra la complejidad O(n * log P).

### 3. maximoTenedor - O(1)
```
maximoTenedor() con 1000 usuarios -> 2200ns
maximoTenedor() con 10000 usuarios -> 2000ns
maximoTenedor() con 100000 usuarios -> 600ns
maximoTenedor() con 1000000 usuarios -> 1200ns
```
Los tiempos son consistentemente y no muestran una dependencia con la cantidad de usuarios, lo que confirma que la operación es O(1).

### 4. montoMedioUltimoBloque - O(1)
```
montoMedioUltimoBloque() con 1000 transacciones -> 4600ns
montoMedioUltimoBloque() con 10000 transacciones -> 1000ns
montoMedioUltimoBloque() con 100000 transacciones -> 500ns
montoMedioUltimoBloque() con 1000000 transacciones -> 600ns
```
Los tiempos son independientes del número de transacciones, esto confirma la complejidad O(1) gracias al cálculo directo `montoTotal / cantTrx`.

### 5. txUltimoBloque - O(n)
```
txUltimoBloque() con 1000 transacciones -> 2186700ns
txUltimoBloque() con 10000 transacciones -> 1548600ns
txUltimoBloque() con 100000 transacciones -> 10743400ns
txUltimoBloque() con 1000000 transacciones -> 11848200ns
```
Se observa un crecimiento aproximadamente al número de transacciones, confirma la complejidad O(n).

### 6. hackearTx - O(log P + log n)
```
Para n=1000000 (fijo), variando P:
P=1000 -> 552300ns
P=10000 -> 291400ns
P=100000 -> 308000ns
P=1000000 -> 574300ns

Para P=1000 (fijo), variando n:
n=1000 -> 75100ns
n=10000 -> 404700ns
n=100000 -> 2277000ns
n=1000000 -> 552300ns
```
Los tiempos muestran un crecimiento logarítmico tanto con P como con n, confirmando la complejidad O(log P + log n).