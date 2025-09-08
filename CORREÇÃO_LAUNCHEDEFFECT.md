# Correção do LaunchedEffect - EmissionCalculatorScreen

## ❌ Problema Identificado

O `LaunchedEffect` estava sendo usado incorretamente dentro do `onClick` do botão, o que causava erro de compilação porque:

1. **LaunchedEffect não pode ser usado dentro de callbacks** - Ele deve ser usado no nível do Composable
2. **Falta de função Composable** - O LaunchedEffect precisa estar em um contexto Composable válido

## ✅ Solução Implementada

### **Antes (Incorreto):**
```kotlin
Button(
    onClick = {
        // ... validações ...
        LaunchedEffect(Unit) { // ❌ ERRO: LaunchedEffect dentro de onClick
            // ... cálculos ...
        }
    }
)
```

### **Depois (Correto):**
```kotlin
@Composable
fun EmissionCalculatorScreen(...) {
    var shouldCalculate by remember { mutableStateOf(false) }
    
    // ✅ LaunchedEffect no nível do Composable
    LaunchedEffect(shouldCalculate) {
        if (shouldCalculate && selectedTransport != null && distance.isNotBlank()) {
            // ... cálculos assíncronos ...
        }
    }
    
    Button(
        onClick = {
            // ... validações ...
            shouldCalculate = true // ✅ Apenas define o estado
        }
    )
}
```

## 🔧 Mudanças Implementadas

### 1. **Adicionada Variável de Estado**
```kotlin
var shouldCalculate by remember { mutableStateOf(false) }
```

### 2. **LaunchedEffect Movido para o Nível Correto**
```kotlin
LaunchedEffect(shouldCalculate) {
    if (shouldCalculate && selectedTransport != null && distance.isNotBlank()) {
        // Cálculos assíncronos aqui
    }
}
```

### 3. **onClick Simplificado**
```kotlin
onClick = {
    if (selectedTransport != null && distance.isNotBlank()) {
        val distanceValue = distance.toDoubleOrNull()
        if (distanceValue != null && distanceValue > 0) {
            isLoading = true
            errorMessage = null
            shouldCalculate = true // ✅ Apenas define o estado
        } else {
            errorMessage = "Por favor, insira uma distância válida"
        }
    } else {
        errorMessage = "Por favor, selecione um transporte e insira a distância"
    }
}
```

## 🎯 Benefícios da Correção

1. **✅ Compilação Correta**: LaunchedEffect agora está no contexto Composable adequado
2. **✅ Funcionalidade Mantida**: Cálculos assíncronos funcionam corretamente
3. **✅ Código Limpo**: Separação clara entre UI e lógica de negócio
4. **✅ Performance**: LaunchedEffect só executa quando necessário
5. **✅ Tratamento de Erros**: Mantido o sistema robusto de tratamento de erros

## 🚀 Como Funciona Agora

1. **Usuário clica no botão** → Validações são feitas
2. **Se válido** → `shouldCalculate = true` e `isLoading = true`
3. **LaunchedEffect detecta mudança** → Executa cálculos assíncronos
4. **Cálculos completos** → `shouldCalculate = false` e `isLoading = false`
5. **Resultados exibidos** → Navega para tela de resultados

## 📝 Arquivos Modificados

- `app/src/main/java/br/com/fiap/ecotrack/ui/screens/EmissionCalculatorScreen.kt`

## ✅ Status

**PROBLEMA CORRIGIDO COM SUCESSO!**

O LaunchedEffect agora está implementado corretamente e o código deve compilar sem erros relacionados ao Composable.
