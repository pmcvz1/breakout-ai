# Breakout AI - Rede Neural Evolutiva

## 🧠 Descrição

Projeto da unidade curricular de **Inteligência Artificial** para a Licenciatura de Engenharia de Telecomunicações e Informática.  
Implementa uma **rede neuronal feed-forward evolutiva** que controla o jogo **Breakout**.  

O agente aprende a jogar através de um **algoritmo genético** que evolui a rede neural ao longo de várias gerações, maximizando o **fitness** (pontuação e tempo de sobrevivência no jogo).

---

## 🔹 Estrutura do projeto

BreakoutAI/

├─ src/ # Código-fonte Java

├─ .settings/ # Configurações da IDE Eclipse

├─ .classpath # Ficheiro de configuração do Eclipse

├─ .gitignore # Ficheiros ignorados pelo Git

├─ .project # Ficheiro de projeto Eclipse

└─ README.md


- **src/**: Implementação da rede neural, algoritmo genético e controlador do Breakout  
- **.settings/**, **.classpath**, **.project**: Ficheiros de configuração do Eclipse  
- **.gitignore**: Ficheiros ignorados pelo Git, como resultados gerados em runtime  

---

## ▶️ Como executar

1. Clone o repositório:  
```bash
git clone https://github.com/pmcvz1/breakout-ai.git
```
2. Abra o projeto no Eclipse ou outra IDE compatível com Java
3. Compile e execute a classe principal (Main.java)
4. Observe o agente a jogar Breakout automaticamente e os valores de fitness das redes neuronais evoluídas na consola.

## 🛠 Funcionalidades implementadas
- Rede neuronal feed-forward para controlar o agente do Breakout
- Algoritmo genético para evoluir populações de redes neuronais
- Mutação, crossover e seleção por torneio
- Registo do fitness das redes ao longo das gerações
- Escolha da rede com melhor desempenho para jogar o jogo
- Possibilidade de aplicação da rede e do algoritmo em outros jogos ou sistemas do mundo real (simulação, otimização, previsão de padrões)

## 👥 Autores

Grupo 34

Pedro Vaz – Nº111322

Rodrigo Diogo – Nº111516
