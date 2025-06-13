# Tiempos de los Casos de Prueba.

Es solo para usar otro metodo que la complejidad cumple con lo pedido en la consigna.

---

### 1. nuevoBerretacoin - O(P)
```
nuevoBerretacoin(1000) -> 0ms
nuevoBerretacoin(10000) -> 1ms
nuevoBerretacoin(100000) -> 1ms
nuevoBerretacoin(1000000) -> 32ms
```
El salto de 100k a 1M (10x de tamaño) en el tiempo muestra un corportamiento lineal.

### 2. agregarBloque - O(n * log P)
```
Para P=1000000 (fijo), variando n:
n=1000 -> 900500ms
n=10000 -> 1266400ms
n=100000 -> 7556800ms
n=1000000 -> 97469600ms

Para n=1000000 (fijo), variando P:
P=1000 -> 82178500ms
P=10000 -> 74110000ms
P=100000 -> 81203500ms
P=1000000 -> 97469600ms
```
El tiempo escala de forma lineal con n (para P fijo), y de manera logarítmica con P, lo que demuestra la complejidad O(n * log P).

### 3. maximoTenedor - O(1)
```
maximoTenedor() con 1000 usuarios -> 1100ns
maximoTenedor() con 10000 usuarios -> 1300ns
maximoTenedor() con 100000 usuarios -> 200ns
maximoTenedor() con 1000000 usuarios -> 29400ns
```
Vemos que el maximoTenedor, con el ejemplo de 100k usuarios, no depende de la cantidad de usuarios. Mi teoria el porque con 1M de usuarios hace ese salto en el tiempo es por el manejo de memoria que tiene Java.

### 4. montoMedioUltimoBloque - O(1)
```
montoMedioUltimoBloque() con 1000 transacciones -> 4800ns
montoMedioUltimoBloque() con 10000 transacciones -> 2100ns
montoMedioUltimoBloque() con 100000 transacciones -> 200ns
montoMedioUltimoBloque() con 1000000 transacciones -> 24300ns
```
Bajo el mismo criterio de maximoTenedor, montoMedioUltimoBloque no depende de la cantidad de bloques de la cadena, es dado en forma de O(1) gracias a la operación dentro de Bloque `montoTotal / cantTrx`.

### 5. txUltimoBloque - O(n)
```
txUltimoBloque() con 1000 transacciones -> 3600ns
txUltimoBloque() con 10000 transacciones -> 17600ns
txUltimoBloque() con 100000 transacciones -> 469300ns
txUltimoBloque() con 1000000 transacciones -> 4915200ns
```
El crecimiento es proporcional al tamaño de n.

### 6. hackearTx - O(log P + log n)
```
Para n=1000000 (fijo), variando P:
P=1000 -> 8541800ns
P=10000 -> 1977900ns
P=100000 -> 2361200ns
P=1000000 -> 1846100ns

Para P=1000 (fijo), variando n:
n=1000 -> 280400ns
n=10000 -> 1040900ns
n=100000 -> 5557600ns
n=1000000 -> 8541800ns
```
Se muestra comportamientos logarítmicos respecto a ambas variables P y n.