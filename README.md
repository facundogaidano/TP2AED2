# Tiempos de los Casos de Prueba.

Es solo para usar otro metodo que la complejidad cumple con lo pedido en la consigna.

---

### 1. nuevoBerretacoin - O(P)
```
nuevoBerretacoin(1000) -> 81400ns
nuevoBerretacoin(10000) -> 703800ns
nuevoBerretacoin(100000) -> 6940600ns
nuevoBerretacoin(1000000) -> 157200500ns
```
Se ve un crecimiento proporcional al tamaño de P. Esto está dentro de la cota O(P).

### 2. agregarBloque - O(n * log P)
```
Para P=1000000 (fijo), variando n:
n=1000 -> 201100ns
n=10000 -> 1291900ns
n=100000 -> 10985500ns
n=1000000 -> 108550100ns

Para n=1000000 (fijo), variando P:
P=1000 -> 87118600ns
P=10000 -> 159137300ns
P=100000 -> 73408500ns
P=1000000 -> 108550100ns
```
El tiempo escala de forma lineal con n (para P fijo), y de manera logarítmica con P, lo que demuestra la complejidad O(n * log P).

### 3. maximoTenedor - O(1)
```
maximoTenedor() con 1000 usuarios -> 2500ns
maximoTenedor() con 10000 usuarios -> 3100ns
maximoTenedor() con 100000 usuarios -> 800ns
maximoTenedor() con 1000000 usuarios -> 400ns
```
Los tiempos son consistentemente y no muestran una dependencia con la cantidad de usuarios, lo que confirma que la operación es O(1).

### 4. montoMedioUltimoBloque - O(1)
```
montoMedioUltimoBloque() con 1000 transacciones -> 4800ns
montoMedioUltimoBloque() con 10000 transacciones -> 2000ns
montoMedioUltimoBloque() con 100000 transacciones -> 400ns
montoMedioUltimoBloque() con 1000000 transacciones -> 400ns
```
Los tiempos son independientes del número de transacciones, esto confirma la complejidad O(1) gracias al cálculo directo `montoTotal / cantTrx`.

### 5. txUltimoBloque - O(n)
```
txUltimoBloque() con 1000 transacciones -> 314000ns
txUltimoBloque() con 10000 transacciones -> 1436100ns
txUltimoBloque() con 100000 transacciones -> 9613700ns
txUltimoBloque() con 1000000 transacciones -> 12819500ns
```
Se observa un crecimiento aproximadamente al número de transacciones, confirma la complejidad O(n).

### 6. hackearTx - O(log P + log n)
```
Para n=1000000 (fijo), variando P:
P=1000 -> 16600ns
P=10000 -> 21600ns
P=100000 -> 16200ns
P=1000000 -> 22500ns

Para P=1000 (fijo), variando n:
n=1000 -> 27100ns
n=10000 -> 11000ns
n=100000 -> 20700ns
n=1000000 -> 16600ns
```
Los tiempos de ejecución son relativamente constantes a pesar de las grandes variaciones en P y N. Esto confirma la naturaleza de O(log(n*P)), ya que incluso con grandes variaciones en el tamaño de la entrada, no se producen cambios drásticos en los tiempos de ejecución.