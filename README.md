# Tiempos de los Casos de Prueba.

Es solo para usar otro metodo que la complejidad cumple con lo pedido en la consigna.

---

### 1. nuevoBerretacoin - O(P)
```
nuevoBerretacoin(1000) -> 43300ns
nuevoBerretacoin(10000) -> 448100ns
nuevoBerretacoin(100000) -> 4488200ns
nuevoBerretacoin(1000000) -> 42828000ns
```
Se ve un crecimiento proporcional al tamaño de P. Esto está dentro de la cota O(P).

### 2. agregarBloque - O(n * log P)
```
Para P=1000000 (fijo), variando n:
n=1000 -> 190800ns
n=10000 -> 1114300ns
n=100000 -> 10443400ns
n=1000000 -> 121695300ns

Para n=1000000 (fijo), variando P:
P=1000 -> 194687800ns
P=10000 -> 226503000ns
P=100000 -> 113525100ns
P=1000000 -> 121695300ns
```
El tiempo escala de forma lineal con n (para P fijo), y de manera logarítmica con P, lo que demuestra la complejidad O(n * log P).

### 3. maximoTenedor - O(1)
```
maximoTenedor() con 1000 usuarios -> 3100ns
maximoTenedor() con 10000 usuarios -> 4300ns
maximoTenedor() con 100000 usuarios -> 800ns
maximoTenedor() con 1000000 usuarios -> 500ns
```
Los tiempos son consistentemente y no muestran una dependencia con la cantidad de usuarios, lo que confirma que la operación es O(1).

### 4. montoMedioUltimoBloque - O(1)
```
montoMedioUltimoBloque() con 1000 transacciones -> 14400ns
montoMedioUltimoBloque() con 10000 transacciones -> 1800ns
montoMedioUltimoBloque() con 100000 transacciones -> 500ns
montoMedioUltimoBloque() con 1000000 transacciones -> 600ns
```
Los tiempos son independientes del número de transacciones, esto confirma la complejidad O(1) gracias al cálculo directo `montoTotal / cantTrx`.

### 5. txUltimoBloque - O(n)
```
txUltimoBloque() con 1000 transacciones -> 1073600ns
txUltimoBloque() con 10000 transacciones -> 1429000ns
txUltimoBloque() con 100000 transacciones -> 9960200ns
txUltimoBloque() con 1000000 transacciones -> 12961400ns
```
Se observa un crecimiento aproximadamente al número de transacciones, confirma la complejidad O(n).

### 6. hackearTx - O(log P + log n)
```
Para n=1000000 (fijo), variando P:
P=1000 -> 665300ns
P=10000 -> 358400ns
P=100000 -> 758500ns
P=1000000 -> 307300ns

Para P=1000 (fijo), variando n:
n=1000 -> 165600ns
n=10000 -> 662300ns
n=100000 -> 2318800ns
n=1000000 -> 665300ns
```
Los tiempos muestran un crecimiento logarítmico tanto con P como con n, confirmando la complejidad O(log P + log n).