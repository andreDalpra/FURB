# Lista de Exercícios de Revisão – U3 – 2026/1

As respostas seguem os algoritmos dos materiais fornecidos: Bubble otimizado, QuickSort
com o primeiro elemento como pivô, MergeSort, busca binária, árvore binária de busca e
tabela de dispersão com endereçamento separado.

## 1. Ordenação Bolha

### 1.1 Execução em `V = [40, 20, 50, 10, 30]`

| Passagem | Estado do vetor |
|---|---|
| Inicial | `[40, 20, 50, 10, 30]` |
| 1 | `[20, 40, 10, 30, 50]` |
| 2 | `[20, 10, 30, 40, 50]` |
| 3 | `[10, 20, 30, 40, 50]` |
| 4 | `[10, 20, 30, 40, 50]` |

Na primeira passagem, o maior elemento (50) chega ao final. A cada passagem seguinte,
mais uma posição final fica ordenada.

### 1.2 Comparações para 10 elementos

O Bubble tradicional faz:

`9 + 8 + 7 + ... + 1 = 10 × 9 / 2 = 45 comparações`.

### 1.3 Vantagem do Bubble otimizado

Uma variável booleana `trocou` registra se houve alguma troca durante a passagem. Se a
passagem terminar sem trocas, o vetor já está ordenado e o método retorna imediatamente.
A implementação está em `AlgoritmosOrdenacao.bubbleSort`.

### 1.4 Complexidade

- Bubble tradicional: `O(n²)` no melhor, médio e pior caso.
- Bubble otimizado: `O(n)` no melhor caso e `O(n²)` nos casos médio e pior.

### 1.5 Vetor já ordenado

Para `[5, 8, 15, 22, 34, 44, 50]`, são feitas zero trocas e apenas uma passagem.
`trocou` permanece `false`, fazendo o algoritmo terminar após as seis comparações da
primeira passagem. Portanto, a variável tem efeito prático: evita as outras passagens.

## 2. Quicksort

### 2.1 Primeiro particionamento

Vetor inicial: `[40, 20, 50, 10, 30]`; pivô: `40`; `a = 0`; `b = 5`.

1. `a` avança: passa pelo 20 e para no índice 2 (valor 50).
2. `b` recua para o índice 4 (valor 30).
3. Como `a < b`, troca 50 com 30: `[40, 20, 30, 10, 50]`.
4. `a` avança: passa pelo 10 e para no índice 4 (valor 50).
5. `b` recua para o índice 3 (valor 10).
6. Como `a >= b`, os índices se cruzaram.
7. Troca final entre o pivô e a posição `b`: `[10, 20, 30, 40, 50]`.

O pivô termina no índice 3.

### 2.2 Subvetores recursivos

Os subvetores são `[10, 20, 30]` (índices 0 a 2) e `[50]` (índice 4). O pivô 40 já
está na posição definitiva e não participa das próximas chamadas.

### 2.3 Complexidade

No caso médio e no melhor caso, as partições são equilibradas e a complexidade é
`O(n log n)`. No pior caso, partições muito desequilibradas levam a `O(n²)`. Com o
primeiro elemento como pivô, um vetor já ordenado é um caso desfavorável. O algoritmo
usa pilha de recursão e faz a ordenação no próprio vetor.

## 3. Mergesort

### 3.1 Árvore de divisões

```text
[40, 20, 50, 10, 30]
├── [40, 20, 50]
│   ├── [40, 20]
│   │   ├── [40]
│   │   └── [20]
│   └── [50]
└── [10, 30]
    ├── [10]
    └── [30]
```

### 3.2 Intercalações

1. `[40] + [20]` resulta em `[20, 40]`.
2. `[20, 40] + [50]` resulta em `[20, 40, 50]`.
3. `[10] + [30]` resulta em `[10, 30]`.
4. `[20, 40, 50] + [10, 30]` resulta em `[10, 20, 30, 40, 50]`.

### 3.3 Complexidade

O MergeSort sempre possui complexidade `O(n log n)`. Ele precisa de memória auxiliar
`O(n)` para realizar a intercalação e é estável quando, em caso de igualdade, o
elemento da esquerda é escolhido primeiro.

## 4. Implementação e `Comparable`

### 4.1 Uso da interface

`Comparable<T>` define a ordem natural de objetos por meio de `compareTo`. Um
`Integer` já implementa a interface. Para uma classe própria, é necessário definir o
critério; a classe `Produto` deste projeto ordena primeiro pelo preço e depois pelo nome.

```java
Integer[] numeros = { 3, 1, 2 };
Produto[] produtos = {
    new Produto("Teclado", 150.0),
    new Produto("Mouse", 80.0)
};

AlgoritmosOrdenacao.bubbleSort(numeros);
AlgoritmosOrdenacao.mergeSort(produtos);
```

A expressão:

```java
info[j].compareTo(info[j + 1]) > 0
```

significa que o elemento da posição `j` é maior que o elemento seguinte segundo sua
ordem natural. No Bubble, essa condição indica que os dois elementos devem ser trocados.

### Intercalação de `[3, 27, 38, 43]` e `[9, 10, 82]`

As escolhas sucessivas são: 3 (esquerda), 9 (direita), 10 (direita), 27, 38 e 43
(esquerda), e por fim 82 (direita). Resultado:

`[3, 9, 10, 27, 38, 43, 82]`.

O trecho apresentado pertence ao método `merge`: compara os elementos apontados por
`cEsq` e `cDir`, copia o menor para `info[i]` e avança o contador correspondente.

## 5. Tabela de dispersão

A função é `hash(chave) = chave mod 13`.

### 5.1 Inserções

| Chave | Cálculo | Índice |
|---|---|---|
| 10 | `10 mod 13` | 10 |
| 30 | `30 mod 13` | 4 |
| 22 | `22 mod 13` | 9 |
| 33 | `33 mod 13` | 7 |
| 4 | `4 mod 13` | 4 |
| 17 | `17 mod 13` | 4 |

```text
 0: vazio
 1: vazio
 2: vazio
 3: vazio
 4: 30 -> 4 -> 17
 5: vazio
 6: vazio
 7: 33
 8: vazio
 9: 22
10: 10
11: vazio
12: vazio
```

As colisões ocorreram no índice 4. O endereçamento separado manteve os três elementos
em uma lista encadeada nessa posição.

### 5.2 Busca da chave 17

Calcula-se `17 mod 13 = 4`. A busca acessa a lista do índice 4 e compara, em ordem, as
chaves 30, 4 e 17. Na terceira comparação encontra a chave.

### 5.3 Fator de carga

`α = quantidade / tamanho = 6 / 13 ≈ 0,4615`.

O valor baixo indica que há menos de um elemento por posição em média e que a busca
tende a ser eficiente, desde que a função de hash distribua bem as chaves.

### 5.4 Tamanho primo

Um tamanho primo reduz padrões causados por fatores comuns entre as chaves e o tamanho
da tabela. Com a função de resto, isso tende a distribuir melhor as chaves e diminuir
colisões.

### 5.5 Colisão total

Se quase todas as chaves forem para a mesma posição, a lista dessa posição terá tamanho
próximo de `n`. A busca deixa de ser `O(1)` em média e se aproxima de uma busca linear,
`O(n)`.

## 6. Busca binária

### 6.1 Busca por 70

| Iteração | início | fim | meio | valor no meio | Ação |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 8 | 4 | 50 | `início = 5` |
| 2 | 5 | 8 | 6 | 70 | encontrado |

O método retorna o índice 6.

### 6.2 Busca por 25

| Iteração | início | fim | meio | valor no meio | Ação |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 8 | 4 | 50 | `fim = 3` |
| 2 | 0 | 3 | 1 | 20 | `início = 2` |
| 3 | 2 | 3 | 2 | 30 | `fim = 1` |

O algoritmo termina quando `início = 2` e `fim = 1`, pois `início > fim`, retornando
`-1`.

## 7. Árvores binárias de busca

### 7.1 Inserção

1. 50 vira a raiz.
2. 30 é menor que 50 e vai à esquerda.
3. 70 é maior que 50 e vai à direita.
4. 20 percorre 50 → 30 e vai à esquerda de 30.
5. 40 percorre 50 → 30 e vai à direita de 30.
6. 60 percorre 50 → 70 e vai à esquerda de 70.
7. 80 percorre 50 → 70 e vai à direita de 70.

```text
        50
       /  \
     30    70
    / \    / \
   20 40  60 80
```

### 7.2 Busca por 60

O caminho é `50 → 70 → 60`: 60 é maior que 50, portanto segue à direita; depois é
menor que 70, portanto segue à esquerda.

### 7.3 Inserção em ordem crescente

```text
20
  \
   30
     \
      40
        \
         50
           \
            60
              \
               70
                 \
                  80
```

A árvore vira uma lista inclinada para a direita. A altura passa a ser `n - 1`, e a
busca no pior caso passa de `O(log n)` para `O(n)`.

## 8. Exercícios adicionais

### 8.1 Bubble otimizado

Implementado em `AlgoritmosOrdenacao.bubbleSort`, usando `trocou` e retorno antecipado.

### 8.2 Primeira ocorrência

Implementado em `Buscas.buscarPrimeiraOcorrencia`. Ao encontrar o valor, o índice é
guardado e a busca continua à esquerda com `fim = meio - 1`.

### 8.3 Remoção na tabela

Implementada em `TabelaDispersao.remover`. O método calcula o hash, percorre somente a
lista daquela posição e corrige o encadeamento ao remover o primeiro nó ou um nó interno.
Retorna `false` para lista vazia ou chave inexistente.

### 8.4 Impressão em ordem

Implementada em `ArvoreBinariaBusca.imprimirEmOrdem`. A ordem
`esquerda → nó → direita` imprime os valores em ordem crescente.

### 8.5 Verificação de ordenação

Implementada em `AlgoritmosOrdenacao.estaOrdenado`. O método compara cada elemento com
seu sucessor e retorna `false` ao encontrar a primeira inversão. É útil para evitar uma
ordenação desnecessária ou para escolher um algoritmo adaptativo, como Bubble otimizado,
quando os dados já estão ordenados ou quase ordenados. Seu custo é `O(n)`.

### 8.6 Vetor ordenado para árvore balanceada

Implementado em `ArvoreBinariaBusca.deVetorOrdenado`. O elemento central vira a raiz e
o processo é repetido recursivamente nas metades esquerda e direita. O custo é `O(n)` e
a altura resultante é `O(log n)`.

## Execução

Execute `listaextra.Principal`. A classe demonstra todos os algoritmos e estruturas.
Os testes automatizados estão em `src/main/Teste.java` e utilizam JUnit 5. No Eclipse,
clique com o botão direito na classe e selecione **Run As → JUnit Test**.
