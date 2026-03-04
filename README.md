# 🔐 The Password Overlord

**Disciplina:** Soluções Mobile
**Professor:** Thyerri Mezzari
**Instituição:** Centro Universitário SATC — Engenharia de Software

---

## 📌 Sobre o Projeto

Motor de validação de senhas dinâmico e interativo desenvolvido em **Kotlin**, utilizando conceitos de **Orientação a Objetos (POO)** e **Programação Funcional (Lambdas)**.

O sistema desafia o usuário a criar uma "senha absoluta", validando requisitos complexos um de cada vez — simulando a lógica de um validador de frontend moderno.

---

## 🛠 Regras de Validação

### Obrigatórias
| # | Regra |
|---|-------|
| 1 | Mínimo de **5 caracteres** |
| 2 | Pelo menos uma **letra maiúscula** |
| 3 | Pelo menos um **número** |
| 4 | Deve conter a palavra **"SATC"** (case-insensitive) |
| 5 | Deve conter o ano **"2026"** (ano do Hexa) |
| 6 | Deve conter pelo menos um **emoji** (ex: ❄ 🔥 ⭐) |

### Criativas (extras)
| # | Regra |
|---|-------|
| 7 | Deve conter o **dia atual em algarismos romanos** (ex: hoje é dia 4 → `IV`) |
| 8 | A **soma de todos os dígitos** da senha deve ser ≥ 20 |
| 9 | A senha deve conter o **próprio comprimento** como número (auto-referencial!) |
| 10 | A senha deve conter um **palíndromo** de pelo menos 3 caracteres (ex: `aba`, `121`) |

---

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/p-afonso/password-overlord.git
   ```
2. Abra no **IntelliJ IDEA**
3. Execute `src/Main.kt`

---

## 💡 Exemplo de Senha Válida

```
SatcHexa2026🔥IV9988aba
```

- Tem maiúscula ✅
- Tem número ✅
- Contém "SATC" ✅
- Contém "2026" ✅
- Tem emoji 🔥 ✅
- Contém "IV" (dia 4 em romano) ✅
- Soma dos dígitos: 2+0+2+6+9+9+8+8 = 44 ≥ 20 ✅
- Contém "24" (comprimento da senha) ✅
- Contém "aba" (palíndromo) ✅

---

## 🏗 Estrutura do Código

```
src/
└── Main.kt   # Toda a lógica: data class Requisito, engine de validação e main()
```

### Conceitos Utilizados
- **Data Class** → `Requisito(mensagemErro, validacao)`
- **Lambdas** → cada regra é uma função `(String) -> Boolean`
- **do...while** → loop até senha ser aprovada
- **break** → para no primeiro erro (foco em UX)
- **Null Safety / Elvis operator** → `readLine() ?: ""`
